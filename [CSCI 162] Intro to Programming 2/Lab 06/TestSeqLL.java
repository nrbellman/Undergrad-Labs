import org.junit.Test;
import static org.junit.Assert.*;

public class TestSeqLL {

    // 2 pts
	@Test
	public void constructDouble( ) {
		System.out.println("==constructDouble==");
		LinkedSequence<Double> s = new LinkedSequence<Double>( );
		
		assertTrue("New seq is empty", s.size( ) == 0);
		assertTrue("New seq has no current", !s.isCurrent( ));
	}
	
    // 2 pts
	@Test
	public void constructString( ) {
		System.out.println("==constructString==");
		LinkedSequence<String> s = new LinkedSequence<String>( );
		
		assertTrue("New seq<String> is empty", s.size( ) == 0);
		assertTrue("New seq has no current", !s.isCurrent( ));
	}
	
    // 2 pts
	@Test(expected=IllegalStateException.class)
	public void noCurrentEmpty( ) {
		System.out.println("==noCurrentEmpty==");
		LinkedSequence<String> s = new LinkedSequence<String>( );
		s.getCurrent( );
		assertTrue("new seq should have no current to get - throw IllegalStateException", false);
	}
	
    // 2 pts
	@Test(expected=IllegalStateException.class)
	public void noAdvanceEmpty( ) {
		System.out.println("==noAdvanceEmpty==");
		LinkedSequence<String> s = new LinkedSequence<String>( );
		s.advance( );
		assertTrue("new seq should not advance - throw IllegalStateException", false);
	}

    // 2 pts
	@Test
	public void cloneEmpty( ) {
		System.out.println("==cloneEmpty==");
		LinkedSequence<Double> s = new LinkedSequence<Double>( );
		LinkedSequence<Double> c = s.clone( );
		
		assertTrue("Cloned empty seq is empty", c.size( ) == 0);
		assertTrue("Cloned empty seq has no current", !c.isCurrent( ));
	}
	
    // 4 pts
	@Test
	public void addOneBefore( ) {
		System.out.println("==addOneBefore==");
		String str = "spring";
		LinkedSequence<String> s = new LinkedSequence<String>( );
		s.addBefore(str);
		
		assertTrue("Should have one element", s.size( ) == 1);
		assertTrue("That element is current", s.isCurrent( ));
		assertTrue("Current should be " + str, str.equals(s.getCurrent( )));
		
		s.advance( );
		assertTrue("Should be no current", !s.isCurrent( ));		
	}
	
    // 4 pts
	@Test
	public void addOneAfter( ) {
		System.out.println("==addOneAfter==");
		String str = "fall";
		LinkedSequence<String> s = new LinkedSequence<String>( );
		s.addAfter(str);
		
		assertTrue("Should have one element", s.size( ) == 1);
		assertTrue("That element is current", s.isCurrent( ));
		assertTrue("Current should be " + str, str.equals(s.getCurrent( )));
		
		s.advance( );
		assertTrue("Should be no current", !s.isCurrent( ));		
	}

    // 6 pts
	@Test
	public void mixtureLoop( ) {
		System.out.println("==mixtureLoop==");
		LinkedSequence<Double> s = new LinkedSequence<Double>( );
		
		s.addBefore((Double)23.0);
		s.addBefore((Double)14.0);
		s.addBefore((Double)(-15.2));
		s.addBefore((Double)17.0);
		
		System.out.println("s is: " + s.toString( ));
		assertEquals("Four addBefores", "(17.0) -15.2 14.0 23.0 ", s.toString( ));
		
		assertEquals("Size is four", 4, s.size( ));
		
		s.addAfter((Double)9.0);
		s.addAfter((Double)34.0);
		assertEquals("Two addAfters", "17.0 9.0 (34.0) -15.2 14.0 23.0 ", s.toString( ));
		
		s.advance( );
		assertEquals("Advance", "17.0 9.0 34.0 (-15.2) 14.0 23.0 ", s.toString( ));

		System.out.println("Inside loop, current should move from start to end");
		for(s.start( ); s.isCurrent( ); s.advance( )) {
			System.out.println("in loop: " + s.toString( ));		
		}
		
		assertTrue("No current after print loop", !s.isCurrent( ));
		
		s.start( );
		assertTrue("Current after start", s.isCurrent( ));
		assertEquals("Current value after start", 17, s.getCurrent( ), 0.0001);
		
		s.advance( );
		assertEquals("Current value after advance", 9, s.getCurrent( ), 0.0001);
	}

    // 6 pts
	@Test
	public void onlyAddBefore( ) {
		System.out.println("==onlyAddBefore==");
		LinkedSequence<Double> s = new LinkedSequence<Double>();

		assertEquals("Size of new is zero", 0, s.size( ));
		assertTrue("No current without elements", !s.isCurrent( ));

		s.addBefore((Double)345.0);
		assertEquals("Size after add is one", 1, s.size( ));
		assertEquals("Current value after adding 345", 345, s.getCurrent( ), 0.0001);

		s.addBefore((Double)12.0);
		assertEquals("Size after add is two", 2, s.size( ));
		assertEquals("Current value after adding 12", 12, s.getCurrent( ), 0.0001);

		s.addBefore((Double)789.0);
		assertEquals("Size after add is three", 3, s.size( ));
		assertEquals("Current value after adding 789", 789, s.getCurrent( ), 0.0001);
	
		System.out.println("s is: " + s.toString( ));
		assertEquals("Three addBefores", "(789.0) 12.0 345.0 ", s.toString( ));
		
		s.advance( );
		s.advance( );
		
		System.out.println("s is: " + s.toString( ));
		assertEquals("Looking at last", "789.0 12.0 (345.0) ", s.toString( ));
		assertEquals("Current last value", 345, s.getCurrent( ), 0.0001);

		s.advance( );
		assertTrue("No current after advancing past end", !s.isCurrent( ));
		
		System.out.println("s has no current: " + s.toString( ));
		assertEquals("At end with no current", "789.0 12.0 345.0 ", s.toString( ));

		s.addBefore((Double)17.35);
		assertTrue("Current at front", s.isCurrent( ));
		assertEquals("Current value after adding 17.35", 17.35, s.getCurrent( ), 0.0001);
		assertEquals("Size after add is four", 4, s.size( ));
		System.out.println("s is: " + s.toString( ));
		assertEquals("Added at front", "(17.35) 789.0 12.0 345.0 ", s.toString( ));		

		s.advance( ); // move to second element
		
		System.out.println("s is: " + s.toString( ));
		assertEquals("Looking at second", "17.35 (789.0) 12.0 345.0 ", s.toString( ));
		assertEquals("Current second value", 789, s.getCurrent( ), 0.0001);
		
		s.addBefore((Double)3.14);
		System.out.println("s is: " + s.toString( ));
		assertEquals("Looking at new second", "17.35 (3.14) 789.0 12.0 345.0 ", s.toString( ));
		assertEquals("New second value", 3.14, s.getCurrent( ), 0.0001);		
	}


    // 6 pts
	@Test
	public void onlyAddAfter( ) {
		System.out.println("==onlyAddAfter==");
		LinkedSequence<Double> s = new LinkedSequence<Double>( );

		assertEquals("Size of new is zero", 0, s.size( ));
		assertTrue("No current without elements", !s.isCurrent( ));

		s.addAfter((Double)13.6);
		assertEquals("Size after add is one", 1, s.size( ));
			assertEquals("Current value after adding 13.6", 13.6, s.getCurrent( ), 0.0001);

		s.addAfter((Double)97.0);
		assertEquals("Size after add is two", 2, s.size( ));
		assertEquals("Current value after adding 97", 97, s.getCurrent( ), 0.0001);

		s.addAfter((Double)(-225.0));
		assertEquals("Current value after adding -225", -225, s.getCurrent( ), 0.0001);
	
		System.out.println("s is: " + s.toString( ));
		assertEquals("Three addAfters", "13.6 97.0 (-225.0) ", s.toString( ));
		
		assertTrue("Current should exist", s.isCurrent( ));
		
		s.advance( );
		assertTrue("No current after advancing past end", !s.isCurrent( ));
		
		System.out.println("s has no current: " + s.toString( ));
		assertEquals("At end with no current", "13.6 97.0 -225.0 ", s.toString( ));

		s.addAfter((Double)17.35);
		assertTrue("Current should exist after add", s.isCurrent( ));
		assertEquals("Current value after adding 17.35", 17.35, s.getCurrent( ), 0.0001);
		assertEquals("Size after add is four", 4, s.size( ));
		System.out.println("s is: " + s.toString( ));
		assertEquals("At last item should be current", "13.6 97.0 -225.0 (17.35) ", s.toString( ));
		
		s.start( );
		s.addAfter((Double)3.14);
		System.out.println("s is: " + s.toString( ));
		assertEquals("Added second item", "13.6 (3.14) 97.0 -225.0 17.35 ", s.toString( ));
		assertEquals("Current value after adding 3.14", 3.14, s.getCurrent( ), 0.0001);		
	}

    // 6 pts
	@Test
	public void noCurrent( ) {
		System.out.println("==noCurrent==");
		LinkedSequence<Double> s = new LinkedSequence<Double>( );
		
		try {
			s.advance( );
			assertTrue("Should have thrown exception in advance", false);
		} catch (IllegalStateException e) {
			
		}
		
		try {
			s.addBefore((Double)42.0);
			s.advance( );
			s.getCurrent( );
			assertTrue("Should have thrown exception in getCurrent", false);
		} catch (IllegalStateException e) {
			
		}
				
		try {
			s.removeCurrent( );
			assertTrue("Should have thrown exception in removeCurrent", false);
		} catch (IllegalStateException e) {
			
		}
	
	}

    // 6 pts
	@Test
	public void removeAddBefore( ) {
		System.out.println("==removeAddBefore==");
		LinkedSequence<String> s = new LinkedSequence<String>( );
		
		s.addBefore("car");
		s.addBefore("the");
		s.addBefore("drove");
		s.addBefore("he");
		System.out.println("s is: " + s.toString( ));
		assertEquals("he is current", "(he) drove the car ", s.toString( ));
		
		s.removeCurrent( );   // he  (first)
		System.out.println("s is: " + s.toString( ));
		assertEquals("drove is current", "(drove) the car ", s.toString( ));
		
		s.advance( );
		s.removeCurrent( );   // the  (middle)
		System.out.println("s is: " + s.toString( ));
		assertEquals("car is current", "drove (car) ", s.toString( ));
		
		s.removeCurrent( );   // car  (last)
		System.out.println("s is: " + s.toString( ));
		assertEquals("no current", "drove ", s.toString( ));
		assertTrue(!s.isCurrent( ));
		
		s.addBefore("She");
		System.out.println("s is: " + s.toString( ));
		assertEquals("Added at front", "(She) drove ", s.toString( ));		
	}

    // 6 pts
	@Test
	public void removeAddAfter( ) {
		System.out.println("==removeAddAfter==");	
		LinkedSequence<String> s = new LinkedSequence<String>( );
		
		s.addAfter("the");
		s.addAfter("cow");
		s.addAfter("jumped");
		s.addAfter("high");
		System.out.println("s is: " + s.toString( ));
		assertEquals("high is current", "the cow jumped (high) ", s.toString( ));
		
		s.removeCurrent( );   // high  (last)
		System.out.println("s is: " + s.toString( ));
		assertEquals("no current", "the cow jumped ", s.toString( ));
		assertTrue(!s.isCurrent( ));
		
		s.start( );
		s.removeCurrent( );   // the  (first)
		System.out.println("s is: " + s.toString( ));
		assertEquals("cow current", "(cow) jumped ", s.toString( ));
		
		s.addAfter("gracefully");
		s.addAfter("and");
		s.addAfter("carefully");
		s.start( );
		s.advance( );
		System.out.println("s is: " + s.toString( ));
		assertEquals("gracefully current", "cow (gracefully) and carefully jumped ", s.toString( ));
		
		s.removeCurrent( );   // gracefully  (middle)
		s.removeCurrent( );   // and  (middle)
		
		System.out.println("s is: " + s.toString( ));
		assertEquals("carefully current", "cow (carefully) jumped ", s.toString( ));
	}
	
    // 6 pts
	@Test
	public void removeMix( ) {
		System.out.println("==removeMix==");
		LinkedSequence<Double> s = new LinkedSequence<Double>( );

		s.addBefore((Double)42.0);
		assertEquals("Size is one after addBefore", 1, s.size( ));
		assertTrue("Item exists", s.isCurrent( ));
		s.removeCurrent( );
		assertEquals("Size is zero", 0, s.size( ));
		assertTrue("Item no longer exists", !s.isCurrent( ));
		
		s.addAfter((Double)17.0);
		assertEquals("Size is one after addAfter", 1, s.size( ));
		assertTrue("Item exists", s.isCurrent( ));
		s.removeCurrent( );
		assertEquals("Size is zero", 0, s.size( ));
		assertTrue("Item no longer exists", !s.isCurrent( ));
		
		s.addAfter((Double)23.0);
		s.addAfter((Double)79.56);
		s.addAfter((Double)893.23);
		s.addAfter((Double)1024.0);
		s.addAfter((Double)4096.0);
		s.addAfter((Double)24356.0);
		s.addAfter((Double)34251.0);
		s.addAfter((Double)42516.0);

		s.start( );
		assertEquals("Size is eight after several adds", 8, s.size( ));
		s.removeCurrent( );
		assertEquals("New current after remove", 79.56, s.getCurrent( ), 0.0001);
		s.advance( );
		s.advance( );
		assertEquals("After advancing", 1024, s.getCurrent( ), 0.0001);
		s.removeCurrent( );
		assertEquals("After removing in middle", 4096, s.getCurrent( ), 0.0001);
		s.advance( );
		s.advance( );
		s.advance( );
		s.removeCurrent( );
		assertTrue("After removing last", !s.isCurrent( ));
		assertEquals("Size is five after several removes", 5, s.size( ));
		
		s.start( );
		while (s.isCurrent( )) {
			s.removeCurrent( );
		}
		assertTrue("After removing all no current", !s.isCurrent( ));		
		assertEquals("Size is zero after removing all", 0, s.size( ));
		s.addAfter((Double)42.42);
		assertTrue("Add after removing all has current", s.isCurrent( ));		
	}

    // 6 pts
	@Test
	public void clonePlay( ) {
		System.out.println("==clonePlay==");
		LinkedSequence<Integer> A = new LinkedSequence<Integer>( );
		LinkedSequence<Integer> B = new LinkedSequence<Integer>( );
		LinkedSequence<Integer> C, D;	
		
		C = A.clone( );
		assertTrue("Clone of empty has empty size", C.size( ) == 0);
		assertTrue("Clone of empty has no current", !C.isCurrent( ));
		
		B.addBefore(17);
		D = B.clone( );
		assertTrue("Clone has same size", D.size( ) == 1);
		assertTrue("Clone has current", D.isCurrent( ));
		assertTrue("Clone has same current object value", 
				D.getCurrent( ).intValue( ) ==  B.getCurrent( ).intValue( ));
	}

    // 6 pts
	@Test
	public void cloneEquals( ) {
		System.out.println("==cloneEquals==");
		LinkedSequence<Double> A = new LinkedSequence<Double>( );
		LinkedSequence<Double> B = new LinkedSequence<Double>( );
		LinkedSequence<Double> C;
		
		A.addAfter((Double)13.0);
		A.addAfter((Double)24.0);
		A.addAfter((Double)95.0);
		A.addAfter((Double)134.0);
		A.addAfter((Double)158.0);
		A.start( );

		B.addBefore((Double)158.0);
		B.addBefore((Double)134.0);
		B.addBefore((Double)95.0);
		B.addBefore((Double)24.0);
		B.addBefore((Double)13.0);
	
		assertTrue("A and B are not same object", !A.equals(B));
		assertTrue("B and A are not same object", !B.equals(A));
		assertTrue("A is equivalent to itself", A.equals(A));
		
		System.out.println("A is: " + A.toString( ));
		assertEquals("A's data", "(13.0) 24.0 95.0 134.0 158.0 ", A.toString( ));
		assertEquals("B's data", "(13.0) 24.0 95.0 134.0 158.0 ", B.toString( ));
		
		C = A.clone( );
		assertTrue("A and C are not same object", !A.equals(C));
		assertTrue("A and C are similar", A.isCurrent( ) == C.isCurrent());
		assertTrue("A and C are same size", A.size( ) == C.size( ));
		assertTrue("A and C have same current", A.getCurrent( ) == C.getCurrent( ));		
		assertEquals("C's data", "(13.0) 24.0 95.0 134.0 158.0 ", C.toString( ));
		
		A.addAfter((Double)17.25);
		assertEquals("C's data shouldn't change", "(13.0) 24.0 95.0 134.0 158.0 ", C.toString( ));
		assertEquals("A's data should change", "13.0 (17.25) 24.0 95.0 134.0 158.0 ", A.toString( ));
	}

    // 6 pts
	@Test
	public void charElements( ) {
		System.out.println("==charElements==");
		LinkedSequence<Character> s = new LinkedSequence<Character>( );
		
		s.addBefore((Character)'t');
		s.addBefore((Character)('a'));
		s.addBefore((Character)'p');
		
		System.out.println("s is: " + s.toString( ));
		assertEquals("Used addBefores 3 times", "(p) a t ", s.toString( ));
		
		assertEquals("Size is three", 3, s.size( ));
		
		s.addAfter((Character)'l');
		s.addAfter((Character)'e');
		assertEquals("Two addAfters", "p l (e) a t ", s.toString( ));
		
		s.advance( );
		assertEquals("Advance", "p l e (a) t ", s.toString( ));
	}

    // 6 pts
	@Test
	public void addAll( ) {
		System.out.println("==addAll==");
		LinkedSequence<Character> s = new LinkedSequence<Character>( );
		LinkedSequence<Character> t = new LinkedSequence<Character>( );
		LinkedSequence<Character> u = new LinkedSequence<Character>( );
		
		s.addAfter((Character)'t');
		s.addAfter((Character)'i');
		s.addAfter((Character)'p');
		
		t.addBefore((Character)'e');
		t.addBefore((Character)'o');
		t.addBefore((Character)'t');
		
		System.out.println("s is: " + s.toString( ));
		assertEquals("Insert tip", "t i (p) ", s.toString( ));
		
		System.out.println("t is: " + t.toString( ));
		assertEquals("Insert toe", "(t) o e ", t.toString( ));
		
		s.addAll(t);
		assertEquals("After addAll", "t i (p) t o e ", s.toString( ));
		
		s.advance( );
		s.advance( );
		s.advance( );
		s.addAfter((Character)'s');
		assertEquals("After adding s to end", "t i p t o e (s) ", s.toString( ));

		// t's last item should have been separate and not affected by
		// the addAfter in s above
		t.advance( );   
		t.advance( );
		assertEquals("t not affected by add in s", "t o (e) ", t.toString( ));

		assertEquals("u is empty", 0, u.size( ));				
		u.addAll(s);
		assertEquals("After adding to empty", "t i p t o e s ", u.toString( ));
		assertTrue("u should have no current item", !u.isCurrent( ));
		u.addAll(t);
		assertTrue("u should still have no current item", !u.isCurrent( ));
		assertEquals("a lot of items in u", "t i p t o e s t o e ", u.toString( ));		
	}
}
