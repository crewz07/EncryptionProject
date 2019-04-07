package Project4Pack;

import java.io.*;
import java.util.Hashtable;
import java.util.Scanner;

public class Mix {

	/*******************************************************************
	 * This the message represented as a linked list.
	 ******************************************************************/
	private DoubleLinkedList<Character> message;

	/*******************************************************************
	 * The clipboard that is represented as a linked list.
	 ******************************************************************/
	private clipBdLinkedList clipboard;

	/*******************************************************************
	 * The String that contains the list of undo commands.
	 ******************************************************************/
	private String undoCommands;

	/*******************************************************************
	 * The message the user wants to encrypt.
	 ******************************************************************/
	private String userMessage;

	/*******************************************************************
	 * This is the scanner to output a file.
	 ******************************************************************/
	private Scanner scan;

	/*******************************************************************
	 * This method is a constructor for the mix class.
	 ******************************************************************/
	public Mix() {

		scan = new Scanner(System.in);
		message = new DoubleLinkedList<Character>();
		clipboard = new clipBdLinkedList();
		undoCommands = "";
	}

	/*******************************************************************
	 * This method sets the message that was entered in by the user.
	 ******************************************************************/
	public void setMessage(){

		for (int i = 0; i < userMessage.length(); i++){

			message.addLast(userMessage.charAt(i));
		}
	}

	/*******************************************************************
	 * This runs the mix class.
	 *
	 * @param args The message from the user
	 ******************************************************************/
	public static void main(String[] args) {
		Mix mix = new Mix();
		mix.userMessage = args[0];
		mix.setMessage();

		System.out.println (mix.userMessage);
		mix.mixture();
	}

	/*******************************************************************
	 * This method mixes the message based on the list of commands
	 * fro the user.
	 ******************************************************************/
	private void mixture() {
		do {
			DisplayMessage();
			System.out.print("Command: ");

			// save state
			DoubleLinkedList<Character> currMessage =  message;
			String currUndoCommands = undoCommands;

			try {
				String command = scan.next("[Qbrpcxhd]");

				switch (command) {
					case "Q":
						save(scan.next());
						System.out.println ("Final mixed up message: \"" +
								message+"\"");
						System.exit(0);
					case "b":
						insertbefore(scan.next(), scan.nextInt());
						break;
					case "r":
						remove(scan.nextInt(), scan.nextInt());
						break;
					case "c":
						copy(scan.nextInt(), scan.nextInt(), scan.nextInt());
						break;
					case "x":
						cut(scan.nextInt(), scan.nextInt(), scan.nextInt());
						break;
					case "p":
						paste(scan.nextInt(), scan.nextInt());
						break;
					case "d":
						char input = scan.next().charAt(0);
						deleteChar(input);
						break;
					case "h":
						helpPage();
						break;

					// all the rest of the commands have not been done
					// No "real" error checking has been done.
				}
				scan.nextLine();   // should flush the buffer
				System.out.println("For demonstration purposes only:" +
						"\n" + undoCommands);
			}

			catch (Exception e ) {
				System.out.println ("Error on input, previous state " +
						"restored.");

				// should completely flush the buffer
				scan = new Scanner(System.in);

				// restore state;
				undoCommands = currUndoCommands;
				message = currMessage ;
			}

		} while (true);
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

		String removeStr;
		error(start,stop);

		removeStr = message.toString().substring(start, stop + 1);

		for (int i = start; i <= stop; i++){

			message.removeIndex(start);
		}

		undoCommands = "b " + removeStr + " " + start +
				"\n" + undoCommands;
	}

	/*******************************************************************
	 * This method removes a portion of the message and adds it to
	 * the clipboard.
	 *
	 * @param start The start character index where the message is
	 *              being cut.
	 * @param stop The last character index that is being cut.
	 * @param clipNum The clipboard number that is associated with the
	 *                cut message.
	 ******************************************************************/
	private void cut(int start, int stop, int clipNum) {

		// Error Checks
		error(start,stop);

		if (clipboard.indexExist(clipNum)){

			throw new IllegalArgumentException("Clipboard Number " +
					"exist");
		}

		String removed = message.getStringClip(start, stop);

		clipboard.addLast(message.removeClip(start, stop), clipNum);
		undoCommands = "b " + removed + " " + (start) + "\n" +
				undoCommands;
	}

	/*******************************************************************
	 * This method copies a portion of the current message and adds it
	 * to the clipboard.
	 *
	 * @param start Where the copy message starts.
	 * @param stop Where the copy message stops at including that
	 *             that character.
	 * @param clipNum The clipboard number that is associated with the
	 *                message.
	 ******************************************************************/
	private void copy(int start, int stop, int clipNum) {

		error(start,stop);
		if (clipboard.indexExist(clipNum)){

			throw new IllegalArgumentException("Clipboard Number " +
					"exist");
		}

		DoubleLinkedList temp = message.getClip(start, stop);
		clipboard.addLast(temp.top,clipNum);
	}

	/*******************************************************************
	 * This method paste a message from a the clipboard and then removes
	 * that message from the clipboard.
	 *
	 * @param index The index the message is added to.
	 * @param clipNum The clip number from the clipboard that the
	 *                message is getting pasted to.
	 ******************************************************************/
	private void paste(int index, int clipNum) {

		int length = message.size();

		// Number does not exist
		if (!clipboard.indexExist(clipNum)){

			throw new IllegalArgumentException("Clipboard Number " +
					"exist");
		}

		// Find the top NodeD of the clipboard
		NodeD input = clipboard.removeIndex(clipNum).
				getTopOfClipBoard();
		message.addClip(index, input);


		length = message.size() - length;
		undoCommands = "r " + index + " " + (length + index - 1) +
				"\n" + undoCommands;
	}

	/*******************************************************************
	 * This method will insert a string at a given index.
	 * @param token The string that is inserted
	 * @param index The index that token is inserted at.
	 ******************************************************************/
	private void insertbefore(String token, int index) {

		undoCommands = "r " + index + " " + (token.length() + index) +
				"\n" + undoCommands;

		for (int i = token.length() - 1; i >= 0; i--){
			message.add(index, token.charAt(i));
		}
	}



	/*******************************************************************
	 * This method will display what the current message is.
	 ******************************************************************/
	private void DisplayMessage() {
		System.out.print ("Message:\n");
		userMessage = message.toString();

		for (int i = 0; i < userMessage.length(); i++)
			System.out.format ("%3d", i);
		System.out.format ("\n");

		for (char c : userMessage.toCharArray())
			System.out.format("%3c",c);
		System.out.format ("\n");
	}

	/*******************************************************************
	 * This method saves all the undoCommands to a file.
	 *
	 * @param filename The name of the file that is saved to.
	 ******************************************************************/
	public void save(String filename) {

		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(
					filename)));

		} catch (IOException e) {
			e.printStackTrace();
		}

		out.println(undoCommands);
		out.close();
	}

	/*******************************************************************
	 * This method will display a help message of instructions for the
	 * user.
	 ******************************************************************/
	private void helpPage() {
		System.out.println("Commands:");
		System.out.println("\tQ filename	means, quit! " + " save to" +
				" filename" );
		System.out.println("\t  ~ is used for a space character" );
		System.out.println("\t .... etc" );
		System.out.println("\th\tmeans to show this help page");
	}

	/*******************************************************************
	 * This method deletes all the characters that the user wants to
	 * delete from the message.
	 *
	 * @param data The character that the user wants to delete
	 ******************************************************************/
	private void deleteChar(char data){

		int index;
		boolean deleteOnce = false;

		// The message string is empty
		if (message.size() == 0){

			throw new IllegalArgumentException("Empty Message");
		}

		// Going through and deleting characters
		for(int i = 0; i < message.size(); i++){

			index = message.delete(data);

			// Deletable character was found
			if (index != -1 && index <= message.size()){

				// Adding to the undoCommand if anything was found
				undoCommands = "b " + data + " " + index + "\n" +
						undoCommands;
				deleteOnce = true;
			}
		}

		// Throw error if nothing can be deleted
		if (!deleteOnce){

			throw new IllegalArgumentException("No character was " +
					"found");
		}
	}

	/*******************************************************************
	 * This method determines if the an error can come from start or
	 * stop or both.
	 *
	 * @param start The starting index.
	 * @param stop The stopping index.
	 ******************************************************************/
	private void error(int start, int stop){

		int maxlength = message.toString().length();

		// error checking for start
		if (start < 0 || start > stop || start > maxlength){

			throw new IndexOutOfBoundsException("Start index is " +
					"incorrect");
		}

		if (stop < 0  || stop > maxlength){

			throw new IndexOutOfBoundsException("Stop index is " +
					"incorrect");
		}
	}
}
