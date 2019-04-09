package Project4Pack;
import java.io.*;
import java.util.Scanner;

/***********************************************************************
 * This method will unencrypted a encrypted message based on a set of
 * undo commands.
 *
 * @author Andrew Kruse & Wayne Chen
 * @version 4-8-19
 **********************************************************************/
public class UnMix {

	/*******************************************************************
	 * The encrypted message from the user.
	 ******************************************************************/
	private DoubleLinkedList<Character> message;

	/*******************************************************************
	 * This method is the constructor for the unmix class. This just
	 * sets up the double link list for message.
	 ******************************************************************/
	public UnMix() {
		message = new DoubleLinkedList<Character>();
	}

	/*******************************************************************
	 * This method runs the unmix method.
	 *
	 * @param args A string with the filename and encrypted message.
	 *             The filename and message should be separated by a
	 *             space.
	 ******************************************************************/
	public static void main(String[] args) {
		UnMix v = new UnMix();
		v.unMixture(args[0], args[1]);
	}

	/*******************************************************************
	 * This method reveals the encrypted message.
	 *
	 * @param command The filename that contains the encrypted message.
	 * @return This will return the original message that was encrypted.
	 ******************************************************************/
	public String processCommand(String command) {
		Scanner scan = new Scanner(command);

		try {
			command = scan.next();
			switch (command.charAt(0)) {

				// if
				case 'b':
					insertBefore(scan.next(), scan.nextInt());
					break;
				case 'r':
					remove(scan.nextInt(),scan.nextInt());
					break;

			}

			// Error caught
		} catch (Exception e) {
			System.out.println("Error in command!  Problem!!!! in " +
					"undo commands");
			System.exit(0);
		}

		finally {
			scan.close();
		}

		// return the original message
		return message.toString();
	}

	/*******************************************************************
	 * This method unencrypted a message.
	 *
	 * @param filename The filename containing the undo commands.
	 * @param userMessage The encrypted message.
	 ******************************************************************/
	private void unMixture(String filename, String userMessage) {
		String original = UnMixUsingFile (filename, userMessage);
		System.out.println ("The Original message was: " + original);
	}

	/*******************************************************************
	 * This method unencrypted a message based on a set of undo commands
	 * from a file.
	 *
	 * @param filename The filename containing the undo commands.
	 * @param userMessage The encrypted message.
	 ******************************************************************/
	public String UnMixUsingFile(String filename, String userMessage) {
		Scanner scanner = null;
		setMessage(userMessage);
		try {
			scanner = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (scanner.hasNext()) {
			String command = scanner.nextLine();
			userMessage = processCommand(command);
		}
		return userMessage;
	}

	/*******************************************************************
	 * This method will insert a string at a given index.
	 *
	 * @param token The string that is inserted
	 * @param index The index that token is inserted at.
	 ******************************************************************/
	private void insertBefore(String token, int index) {

		String input = convertString(token);

		for (int i = input.length() - 1; i >= 0; i--){
			message.add(index, input.charAt(i));
		}
	}

	/*******************************************************************
	 * This method removes a portion of the message from start to stop
	 * and including the stop character.
	 *
	 * @param start Where the remove message starts at.
	 * @param stop Where the remove message stops at including that
	 *             character.
	 ******************************************************************/
	private void remove(int start, int stop) {

		for (int i = start; i <= stop; i++) {

			message.removeIndex(start);
		}
	}

	/*******************************************************************
	 * This method sets the user message based on the input.
	 *
	 * @param userMessage The encrypted message.
	 ******************************************************************/
	public void setMessage(String userMessage){

		for (int i = 0; i < userMessage.length(); i++){

			message.addLast(userMessage.charAt(i));
		}
	}

	/*******************************************************************
	 * This method converts any spaces to ~ or from ~ to spaces.
	 *
	 * @param str The string the is being swapped.
	 * @return A string with the appropriate character swaps.
	 ******************************************************************/
	private String convertString(String str){

		char[] out = str.toCharArray();

		for (int i = 0; i < out.length; i++){

			if (out[i] == '~'){
				out[i] = ' ';
			}
		}

		return String.valueOf(out);
	}
}
