/**
 * A symbol that could appear in a mathematical expression.
 * 
 * Symbols belong to one of four categories:
 *   - Non-negative numbers.
 *   - Operators, which include '+', '-', '*', and '/'.
 *   - Open and closing parenthesis (two separated categories).
 *   
 * @author Beth Katz, Chad Hogg
 */
public class Token {

	/*
	 * Class invariant:
	 * - a token's type is either an operator, a number, or a parentheses
	 * - a token's type is stored in the type instance variable
	 * - the token's value instance variable contains a Double for numbers
	 *     and a Character for operators
	 * - operators have precedence level of 0 for low (+ -) and 1 for high (/ *) 
	 * - non-operators have precedence 0 that is ignored 
	 */

	/** The four types of tokens. */
	private enum TokenType {OPERATOR, NUMBER, LEFT_PAREN, RIGHT_PAREN};
	/** A String containing each of the legal non-number symbols. */
	public static final String VALID_SYMBOLS = "+-/*()";
	/** The type of this token. */
	private TokenType type;
	/** The value of this token. */
	private Object value;
	/** The precendence of this token in an expression; 1 for multiplication and division otherwise 0. */
	private int precedence;

	/**
	 * Builds a Token from a String.
	 * 
	 * @param str A String containing a potential Token, which must be either a non-negative number
	 *   or a valid symbol.
	 * @throws IllegalArgumentException If str does not contain a non-negative number of valid symbol.
	 * @postcondition The type, value, and precendence are set to make this token be whatever was in
	 *   the string.
	 */
	public Token(String str) {
		precedence = 0;
		if(str.length() <= 0) {
			throw new IllegalArgumentException("Empty token");
		}
		else if(str.length() == 1 && VALID_SYMBOLS.contains(str)) {
			type = TokenType.OPERATOR;
			value = (Character)(str.charAt(0));
			if(value.equals('*') || value.equals('/')) {
				precedence = 1;
			}
			else if (value.equals('(')) {
				type = TokenType.LEFT_PAREN;
			}
			else if (value.equals(')')) {
				type = TokenType.RIGHT_PAREN;
			}
		}
		else if(Character.isDigit(str.charAt(0))) {
			type = TokenType.NUMBER;
			try {
				value = Double.parseDouble(str); 
			}
			catch (IllegalArgumentException exception) {
				throw new IllegalArgumentException("Cannot create number from " + str);
			}
		}
		else {
			throw new IllegalArgumentException("Cannot understand token: " + str);			
		}
	}

	/**
	 * Builds a number Token from a Double.
	 * 
	 * @param num A Double containing a Token.
	 * @postcondition The type is number, value is num, and precedence is 0.
	 */
	public Token(Double num) {
		type = TokenType.NUMBER;
		value = num;
		precedence = 0;
	}

	/**
	 * Gets whether or not this Token is a number.
	 * 
	 * @return True if this is a number, false if not.
	 */
	public boolean isNumber() {
		return type == TokenType.NUMBER;
	}

	/**
	 * Gets whether or not this Token is an operator. 
	 * 
	 * @return True if this is an operator, false if not.
	 */
	public boolean isOperator() {
		return type == TokenType.OPERATOR;
	}

	/**
	 * Gets whether or not this Token is a left parentheses.
	 * 
	 * @return True if this is a left parentheses, false if not.
	 */
	public boolean isLeftParen( ) {
		return type == TokenType.LEFT_PAREN;
	}

	/**
	 * Gets whether or not this Token is a right parentheses.
	 * 
	 * @return True if this is a right parentheses, false if not.
	 */
	public boolean isRightParen( ) {
		return type == TokenType.RIGHT_PAREN;
	}

	/**
	 * Gets whether or not this Token has higher precedence than another Token.
	 * 
	 * @param other Another Token.
	 * @return True if this has higher precedence than other, false if not.
	 */
	public boolean hasHigherPrecedenceThan(Token other) {
		return precedence > other.precedence;
	}

	/**
	 * Gets the numeric value of the Token.
	 * 
	 * @precondition This Token is a number.
	 * @return This Token's Double value.
	 * @throws IllegalStateException If this Token is not a number.
	 */
	public Double getNumberValue() {
		if(type != TokenType.NUMBER) {
			throw new IllegalStateException("Operators do not have numeric values");
		}
		return (Double)value;
	}

	/**
	 * Gets the symbol stored in this Token.
	 * 
	 * @precondition This Token is an operator or parenthesis.
	 * @return The specific symbol ('+', '-', ...) in this Token.
	 * @throws IllegalStateException If this Token is a number.
	 */
	public Character getSymbol() {
		if(type == TokenType.NUMBER) {
			throw new IllegalStateException("Numbers do not have character values.");
		}
		return (Character)value;
	}

	/**
	 * Gets a String representation of the Token, which is just the number or symbol.
	 * 
	 * @return A String representation of the Token, like "5.3" or "+".
	 */
	public String toString() {
		return value.toString();
	}
}
