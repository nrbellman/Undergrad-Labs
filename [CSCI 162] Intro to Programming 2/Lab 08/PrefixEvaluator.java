import java.util.NoSuchElementException;

/**
 * Evaluates a given prefix mathematical expression using recursion.
 * @author Nicholas Bellman
 *
 */
public class PrefixEvaluator {
	
	/**
	 * Evaluates a prefix mathematical expression.
	 * 
	 * @param input A String containing the value of the expression. 	 
	 * @return A String containing the value of the expression, or an error message if there was a problem.
	 */
	public static String evaluate(String input) {
		
		TokenScanner scanner = new TokenScanner(input);
		String finalAnswer = "";
		
		if (input.equals("")) {
			
			finalAnswer = "No input.";
			
		}
		else {
			
			try {
				
				Double result = evaluateSub(scanner);
				
				if (!scanner.reachedEnd()) {
				
					finalAnswer = "Computed answer, but not all input used.";

				}
				else {
				
					finalAnswer = result.toString();
				
				}
			
			}
			catch (IllegalArgumentException exception) {
			
				finalAnswer = exception.getMessage();
			
			}
			catch (NoSuchElementException exception) {
				
				finalAnswer = "Not enough operands.";
				
			}
		
		}
		
		return finalAnswer;
		
	}
	
	/**
	 * Evaluates the next sub-expression found in a scanner.
	 * 
	 * @param scanner A TokenScanner containing at least one prefix (sub)-expression.
	 * @return The result of the first sub-expression found in the scanner.
	 */
	private static Double evaluateSub(TokenScanner scanner) {
		
		double result = 0.0;
		Token token = scanner.nextToken();
		
		if (token.isNumber()) {
		
			result = token.getNumberValue();
		
		}
		else {
			
			if (token.isOperator()) {
				
				char symbol = token.getSymbol();
				
				if (symbol == '+') {
				
					result = evaluateSub(scanner) + evaluateSub(scanner);
				
				}
				else if(symbol == '-') {
					
					result = evaluateSub(scanner) - evaluateSub(scanner);
				
				}
				else if(symbol == '*') {
					
					result = evaluateSub(scanner) * evaluateSub(scanner);
				
				}
				else if(symbol == '/') {
					
					result = evaluateSub(scanner) / evaluateSub(scanner);
				
				}
			}
			
			if (token.isLeftParen() || token.isRightParen()) {
				
				char type = token.getSymbol();
				throw new IllegalArgumentException(type + " has no meaning here.");
				
			}
			
		}
		
		return result;
		
	}
	
}
