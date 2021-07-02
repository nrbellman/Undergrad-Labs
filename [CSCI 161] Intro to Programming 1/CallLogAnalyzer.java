import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Reads and analyzes phone call metadata from an external file.
 * 
 * @author Nicholas Bellman
 *
 */
public class CallLogAnalyzer {

	/**
	 * Runs the program.
	 * 
	 * @param args Unknown
	 * @throws FileNotFoundException It's possible that the file path that gets inputted does not actually point to
	 * a file that exists.
	 */
	public static void main(String[] args) throws FileNotFoundException {

		Scanner input = new Scanner(System.in);
		
		System.out.print("File path: ");
		String filePath = input.next();
		
		System.out.print("Phone Number: ");
		String caller = input.next();

		
		File file = new File(filePath);
		Scanner fileScanner = new Scanner(file);
		
		
		System.out.println(findLongestCall(fileScanner, caller) );
		
		input.close();
		
	}
	
	/**
	 * Calculates the total number of minutes a phone number was on a call.
	 * 
	 * @param fileScanner Scanner of a file containing phone call metadata.
	 * @param phoneNumber Phone number to be analyzed.
	 * @return The total number of minutes the phone number was on a call.
	 */
	public static int totalMinutesUsed(Scanner fileScanner, String phoneNumber) {
		
		int totalMinutes = 0;
		
		while (fileScanner.hasNextLine()) {
			
			String line = fileScanner.nextLine();
			Scanner lineScanner = new Scanner(line);
			
			while (lineScanner.hasNext()) {
				
				String caller = lineScanner.next();
				String receiver = lineScanner.next();
				int minutes = lineScanner.nextInt();
						
				if (caller.equals(phoneNumber) || receiver.equals(phoneNumber)) {
					
					totalMinutes += minutes;
					
				} 	
				
			}
			
			lineScanner.close();
			
		}
		
		return totalMinutes;
		
	}
	
	/**
	 * Counts the number of calls between two phone numbers.
	 * 
	 * @param fileScanner Scanner of a file containing phone call metadata.
	 * @param caller Phone number of the caller.
	 * @param receiver Phone number of the receiver.
	 * @return The total number of calls between the two phone numbers.
	 */
	public static int countCalls(Scanner fileScanner, String caller, String receiver) {
		
		int totalCalls = 0;
		
		while (fileScanner.hasNext()) {
			
			String line = fileScanner.nextLine();
			Scanner lineScanner = new Scanner(line);
			
			while (lineScanner.hasNext()) {
				
				String tempCaller = lineScanner.next();
				String tempReceiver = lineScanner.next();
				lineScanner.next();
				
				if ((tempCaller.equals(caller) && tempReceiver.equals(receiver)) || (tempCaller.equals(receiver) 
						&& tempReceiver.equals(caller))) {
					
					totalCalls++;
					
				}
				
			}
			
			lineScanner.close();
			
		}
		
		return totalCalls;
		
	}

	/**
	 * Checks whether or not two numbers had contact.
	 * 
	 * @param fileScanner Scanner of a file containing phone call metadata.
	 * @param caller Phone number of the caller.
	 * @param receiver Phone number of the receiver.
	 * @return true if the two numbers had contact. Returns false otherwise.
	 */
	public static boolean hadContact(Scanner fileScanner, String caller, String receiver) {
		
		boolean contact = false;
		
		while (fileScanner.hasNext() && contact == false) {
			
			String line = fileScanner.nextLine();
			Scanner lineScanner = new Scanner(line);
			
			while (lineScanner.hasNext()) {
				
				String tempCaller = lineScanner.next();
				String tempReceiver = lineScanner.next();
				lineScanner.next();
				
				if ((tempCaller.equals(caller) && tempReceiver.equals(receiver)) || (tempCaller.equals(receiver) 
						&& tempReceiver.equals(caller))) {
					
					contact = true;
					
				}
			}
			
			lineScanner.close();
			
		}
		
		return contact;
		
	}
	
	/**
	 * Finds the longest call that a specific phone number was on.
	 * 
	 * @param fileScanner Scanner of a file containing phone call metadata.
	 * @param phoneNumber Phone number to be analyzed.
	 * @return The phone number that the sepcific phone number had the longest call with.
	 */
	public static String findLongestCall(Scanner fileScanner, String phoneNumber) {
		
		String otherPhoneNumber = "(none)";
		int longestCall = 0;
		
		while (fileScanner.hasNext()) {
			
			String line = fileScanner.nextLine();
			Scanner lineScanner = new Scanner(line);
			
			while (lineScanner.hasNext()) {
				
				String firstCaller = lineScanner.next();
				String secondCaller = lineScanner.next();
				int currentCall = lineScanner.nextInt();
				
				if (firstCaller.equals(phoneNumber) && currentCall > longestCall) {
					
					longestCall = currentCall;
					
					otherPhoneNumber = secondCaller;
					
				} else if (secondCaller.equals(phoneNumber) && currentCall > longestCall) {
					
					longestCall = currentCall;
					
					otherPhoneNumber = firstCaller;
					
				}
				
			}
			
			lineScanner.close();
			
		}
		
		return otherPhoneNumber;
		
	}
	
}