import java.util.EmptyStackException;

/**
 * A class used to evaluate postfix mathematical expressions.
 * 
 * @author Nicholas Bellman
 *
 */
public class PostfixEvaluator {

	/**
	 * Evaluates a postfix mathematical expression.
	 * 
	 * @param input A String containing the expression.
	 * 
	 * @return A String containing the value of the expression, or an error message if there was a problem.
	 */
	public static String evaluate(String input) {

		MUStack<Double> stack = new MULinkedListStack<Double>();
		TokenScanner tokenScanner = new TokenScanner(input);
		
		String finalAnswer = "";
		boolean invalidToken = false;

		while (!tokenScanner.reachedEnd() && invalidToken == false) {

			if(tokenScanner.hasNextToken()) {

				Token token = tokenScanner.nextToken();

				if (token.isNumber()) {

					stack.push(token.getNumberValue());

				}
				else if (token.isOperator()){

					try {

						double numOne = stack.pop();
						double numTwo = stack.pop();

						char symbol = token.getSymbol();

						double result = math(numOne, numTwo, symbol);

						stack.push(result);

					}
					catch (EmptyStackException exception) {

						finalAnswer = "Stack underflow.  Not enough operands on stack.";
						invalidToken = true;

					}

				}
				else if (token.isLeftParen() || token.isRightParen()) {

					finalAnswer = token.getSymbol() + " has no meaning here.";
					invalidToken = true;

				}

			}
			else {

				finalAnswer = "Computed answer, but not all input used.";
				invalidToken = true;

			}

		}

		if (invalidToken == false){

			try {

				String answer = stack.pop().toString();

				if (stack.isEmpty()) {

					finalAnswer = answer;

				}
				else {

					finalAnswer = "Computed answer, but values remain on stack.";

				}

			}
			catch (EmptyStackException exception) {

				finalAnswer = "No input.";

			}

		}

		return finalAnswer;

	}

	/**
	 * Performs a mathematical operation on two numbers. The operation is determined by a given character.
	 * 
	 * @param numOne The first value to be operated on.
	 * @param numTwo The second value to be operated on.
	 * @param symbol The mathematical operator to be applied.
	 * 
	 * @return The result of the mathematical operation.
	 */
	private static double math(double numOne, double numTwo, char symbol) {

		double result;

		if (symbol == '+') {

			result = numTwo + numOne;

		}
		else if (symbol == '-') {

			result = numTwo - numOne;

		}
		else if(symbol == '*') {

			result = numTwo * numOne;

		}
		else {

			result = numTwo / numOne;

		}

		return result;

	}

}