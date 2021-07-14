////////////////////////////////////////////////////////////////////////////////////
// Name: Nicholas Bellman
// Date: 2020-02-25
// Assignment: Josephus
// Description: Simulates the Josephus problem using a circular linked list.
//
////////////////////////////////////////////////////////////////////////////////////

#include <list>
// TODO: add additional includes
#include <iostream>

#include "Josephus.h"

/*  COMPLEXITY WRITEUP
    
    T(N,k) = (N-1)k = Nk-k
    
    N = 5, k = 3
    T(5,3) = (5-1)3 = 15 - 3 = 12



*/

/* Simulate the Josephus problem modeled as a std::list.
 * This function will modify the passed list with only a
 * single survivor remaining.
 *
 * @param circle -- the linked list of people
 * @param k -- skip amount. NOTE: k > 0
 *
 * @return a list of those who are executed in chronological order
 */
template<typename T>
std::list<T>
execute (std::list<T>& circle, int k)
{
  
  std::list<T> killed;

  auto current = circle.begin ();
  int count = 0; // Counter used to keep track of link traversals.

  // Repeatedy executes nodes in 'circle' until only one remains.
  while (circle.size () > 1) // N - 1 traversals
  {

    // Increments the current pointer k spaces, then checks if 'circle' has looped back onto itself.
    for (int i = 1; i < k; ++i)  // k traversals
    {
      ++count; // Increases the link traversal counter by k number of steps 
      
      ++current;
      if (current == circle.end ())
      {
        current = circle.begin ();
      }

    }

    // Adds the current victim to the 'killed' list.
    killed.push_back (*current);

    // Removes the current victim from the 'circle' list, then checks to see if 'circle' looped back on itself.
    current = circle.erase (current);

    ++count; // Increases the link traversal counter by 1 for the incrementation by 'circle.erase()'.
    if (current == circle.end ())
    {
      current = circle.begin ();
    }

  }

  return killed;

}

/* entry point to the Josephus problem from the autograder / main
 *
 * @param n -- number of people in the circle
 * @param k -- skip amount. NOTE: k > 0
 */
int
josephus (int n, int k)
{

  // 1. make a list
  // 2. populate it with values [1, 2, 3, ... , N]
  // 3. call execute
  // 4. return the lone survivor

  // HINT: While working on this lab, you may also find
  //       it useful to print out the "kill" order.

  std::list<int> circle;

  // Populates 'circle' with numbers from 1 to k.
  for (int i = 1; i <= n; ++i)
  {
    circle.push_back (i);
  }

  execute (circle, k);
  
  // Extracts the survivor from the list.
  int survivor = *circle.begin ();

  return survivor;

}
