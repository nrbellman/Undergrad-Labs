import java.util.NoSuchElementException;

/**
 * A class that divides a string containing an arithmetic expression into individual tokens.
 *   (See the Token class for details about what tokens are allowable.)
 * Requires that numbers that contain a decimal point have at least one digit on both sides.
 * Does not require whitespace between tokens.
 * 
 * @author Beth Katz, Chad Hogg
 *
 */
public class TokenScanner {
	/* Class invariant:
	 * source is a null-terminated String which will provide Tokens
	 *      when they are requested
	 * null is added to end of source as a sentinel 
	 * loc is the index into source where next Token may start
	 */

	/** A special symbol that will indicate the end of the expression. */
	public static final char NULL = '\0';
	/** The String that contains the arithmetic expression to be tokenized. */
	private String source;
	/** The location within the source where the next token starts. */
	private int loc;

	/**
	 * Constructs a TokenScanner from a String.
	 * 
	 * @param str A String containing an arithmetic expression.
	 * @postcondition The source is that string with a terminating character, the location is the
	 *   beginning of the source.
	 */
	public TokenScanner(String str) {
		source = str + NULL;
		loc = 0;
	}

	/**
	 * Gets whether or not there is another Token available.
	 * 
	 * @return True if there is a Token available, false if the end has been reached or the next
	 *   character is not a valid part of an arithmetic expression.
	 */
	public boolean hasNextToken() {
		boolean result = false;
		skipWhiteSpace();
		if(source.charAt(loc) != NULL) {
			char ch = source.charAt(loc);
			result = (Character.isDigit(ch) || isValidSymbol(ch));
		}
		return result;
	}

	/**
	 * Provides the next Token from source.
	 * 
	 * @return The next Token.
	 * @postcondition The location has been moved past the returned Token.
	 * @throws NoSuchElementException If the next character is not part of a valid token or
	 *   there are no more tokens.
	 */
	public Token nextToken() {
		Token result = null;
		skipWhiteSpace();
		if(source.charAt(loc) == NULL) {
			throw new NoSuchElementException("end of input");
		}
		char ch = source.charAt(loc);
		if(isValidSymbol(ch)) {
			loc++;
			result = new Token(source.substring(loc - 1,loc));
		}
		else if(!Character.isDigit(ch)) {
			throw new NoSuchElementException("character not recognized: " + ch);
		}
		else {
			result = getNumberToken();		
		}
		return result;
	}

	/**
	 * Gets the next Token, assuming that it is a number.
	 * 
	 * @precondition There is a next Token, and it begins with a digit.
	 * @return The next Token, which is a number.
	 * @postcondition The location is after the returned Token.
	 * @throws IllegalStateException If there is no next token beginning with a digit.
	 */
	private Token getNumberToken() {
		if(loc >= source.length() || !Character.isDigit(source.charAt(loc))) {
			throw new IllegalStateException("next token was not a number");
		}
		int start = loc;
		int stop;
		while(Character.isDigit(source.charAt(loc))) {
			loc++;
		}
		if(source.charAt(loc) == '.' && Character.isDigit(source.charAt(loc + 1))) {
			loc++; // move past decimal point
			while(Character.isDigit(source.charAt(loc))) {
				loc++;
			}
		}
		stop = loc;
		return new Token(source.substring(start, stop));
	}

	/**
	 * Gets whether or not a character is a valid symbol.
	 * 
	 * @param ch The character to check
	 * @return True if ch is a valid Token symbol, or false if not.
	 */
	private static boolean isValidSymbol(char ch) {
		boolean found = false;
		int index = 0;
		while(index < Token.VALID_SYMBOLS.length() && !found) {
			if(Token.VALID_SYMBOLS.charAt(index) == ch) {
				found = true;
			}
			index++;
		}
		return found;
	}

	/**
	 * Moves loc past any white space in source.
	 * 
	 * @postcondition Loc has been moved past any spaces, tabs, newlines, etc.
	 */
	private void skipWhiteSpace() {
		while(Character.isWhitespace(source.charAt(loc))) {
			loc++;
		}
	}

	/**
	 * Gets whether or not the entire source was tokenized.
	 * 
	 * @return True if all of source has been tokenized and there is no more data, false if not.
	 */
	public boolean reachedEnd() {
		return loc == source.length( ) - 1;
	}
}
