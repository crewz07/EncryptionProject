package Project4Pack;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

/***********************************************************************
 * This class encrypts a message based on a set of commands inputted by
 * the user.
 *
 * @author Andrew Kruse & Wayne Chen
 * @version 4-7-19
 **********************************************************************/
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
				String command = scan.next("[Qbrpcxshdz]");

				switch (command) {
					case "Q":
						save(scan.next());

						System.out.println ("Final mixed up message:" +
								" \"" + message+"\"");
						System.exit(0);
					case "b":
						// Modify string before input
						insertbefore(scan.next(), scan.nextInt());
						break;
					case "r":
						remove(scan.nextInt(), scan.nextInt());
						break;
					case "c":
						copy(scan.nextInt(), scan.nextInt(),
								scan.nextInt());
						break;
					case "x":
						cut(scan.nextInt(), scan.nextInt(),
								scan.nextInt());
						break;
					case "p":
						paste(scan.nextInt(), scan.nextInt());
						break;
					case "d":
						char input = scan.next().charAt(0);
						deleteChar(input);
						break;
					case "z":
						randomize();
						break;
					case "s":
						String userInput = scan.next();
						String userInput2 = scan.next();

						//if either input is larger than 1
						if(userInput.length() != 1 ||
								userInput2.length() != 1){
							throw new IllegalArgumentException();
						}

						//check that input string is a letter
						else if(((userInput.charAt(0) > 64 &&
								userInput.charAt(0) < 91 )||
								(userInput.charAt(0) > 96 &&
										userInput.charAt(0) < 123 )) &&
								((userInput2.charAt(0) > 64 &&
										userInput2.charAt(0) < 91 )||
										(userInput2.charAt(0) > 96 &&
										userInput2.charAt(0) < 123 ))){
							substitute(userInput,userInput2);
						}

						//input is not a letter
						else{
							throw new IllegalArgumentException();
						}
						break;
					case "h":
						helpPage();
						break;

					// all the rest of the commands have not been done
					// No "real" error checking has been done.
				}
				scan.nextLine();   // should flush the buffer
				System.out.println("For demonstration purposes only:" +
						"\n");
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

		undoCommands = "b " + convertString(removeStr,false) +
				" " + start + "\n" + undoCommands;
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
		undoCommands = "b " + convertString(removed,false) +
						" " + (start) + "\n" + undoCommands;
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

		String input = convertString(token,true);

		undoCommands = "r " + index + " " + (token.length() +
				index - 1) + "\n" + undoCommands;

		for (int i = token.length() - 1; i >= 0; i--){
			message.add(index, input.charAt(i));
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
		System.out.println("\tx means to cut a section out from start" +
				" to stop and save at a clipboard number.");
		System.out.println("\tc means to copy a section out from " +
				"start to stop and save at a clipboard number.");
		System.out.println("\tp means paste a string at index _ from " +
				"clipboard number _.");
		System.out.println("\td means delete all instances of char_.");
		System.out.println("\tb means insert a string_ at index_");
		System.out.println("\tr means remove a section of the message" +
				" from start to stop, including stop");
		// Add replace
		System.out.println("\tz randomizes the message");
		System.out.println("\th means to show this help page");
		System.out.println("\t ~ is used for a space character" );
		System.out.println("\t insert a space before each command");
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
		int currentSize = message.size();
		char deleted;
		if (data == '~'){

			deleted = ' ';
		}

		else {
			deleted = data;
		}

		// The message string is empty
		if (message.size() == 0){

			throw new IllegalArgumentException("Empty Message");
		}

		// Going through and deleting characters
		for(int i = 0; i <= currentSize; i++){

			index = message.delete(deleted);

			// Deletable character was found
			if (index != -1 && index <= message.size()){

				// Adding to the undoCommand if anything was found
				undoCommands = "b " + convertString("" + deleted,
						false) + " " + index + "\n" +
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
	 * This method will randomize the message based on a random set of
	 * commands. The commands will run up to 5 times.
	 ******************************************************************/
	private void randomize(){

		Random rand = new Random();

		// place holder for a random number
		int randNum;

		// Random number for the amount of times this method is ran
		int randAction = rand.nextInt(5) + 1;

		for (int i = 0; i < randAction; i++){

			// Determining random action
			randNum = rand.nextInt(3);

			// Adding a random string
			if (randNum == 0){
				randomB();
			}

			// removing a random section
			else if (randNum == 1){
				randomRemove();
			}

			// deleting random character
			else if (randNum == 2){

				randomDelete();
			}

//			else if (randNum == 3){
//				randomReplace();
//			}
		}
	}

	/*******************************************************************
	 * This method randomly adds a string to the character. The max is
	 * a string of 5 random characters.
	 ******************************************************************/
	private void randomB(){

		Random rand = new Random();

		char randChar;
		String output = "";

		// Random number for the amount of characters that is inserted
		int randAction = rand.nextInt(5) + 1;

		// last character is 94
		for (int i = 0; i < randAction; i++){

			// Add to the string
			randChar = (char)(rand.nextInt(64) + 31);
			output = output + randChar;
		}

		insertbefore(output, rand.nextInt(message.size() + 1));
	}

	/*******************************************************************
	 * This method will remove a random section of the a string.
	 ******************************************************************/
	private void randomRemove(){

		Random rand = new Random();
		int stop = 0;
		int start;

		// Make sure the size is not 0 before removing
		if (message.size() != 0) {

			start = rand.nextInt(message.size());

			if (start == message.size() - 1)
				stop = start;
			else{

				while (stop < start)
					stop = rand.nextInt(message.size());
			}

			error(start,stop);
			remove(start, stop);
		}
	}

	/*******************************************************************
	 * This method will randomly choose a character in the user message.
	 * Then, it will delete all the characters of the randomly selected
	 * character.
	 ******************************************************************/
	private void randomDelete(){

		Random rand = new Random();

		if (message.size() != 0) {
			int positionChar = rand.nextInt(message.size());

			// Finding character and deleting character
			char delete = message.get(positionChar);
			deleteChar(delete);
		}
	}

	/*******************************************************************
	 * This method will randomly replace a character with another
	 * character in the message.
	 ******************************************************************/
	private void randomReplace(){

		Random rand = new Random();
		char replace;
		char newChar;

		// find random character to replace with
		if (message.size() != 0) {
			int positionChar = rand.nextInt(message.size());

			// Finding character and deleting character
			replace = message.get(positionChar);
			newChar = (char)(rand.nextInt(64) + 31);
			//replace(replace, newChar);
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

	/*******************************************************************
	 * This method converts any spaces to ~ or from ~ to spaces.
	 *
	 * @param str The string the is being swapped.
	 * @param space True if the conversion is from ~ to space.
	 * @return A string with the appropriate character swaps.
	 ******************************************************************/
	private String convertString(String str, boolean space){

		char[] out = str.toCharArray();

		for (int i = 0; i < out.length; i++){

			if (space && out[i] == '~'){
				out[i] = ' ';
			}

			else if (!space && out[i] == ' '){
				out[i] = '~';
			}
		}

		return String.valueOf(out);
	}

	/******************************************************************
	 * This method removes a character from all instances of it in a
	 * list and replaces it another character provided by the user.
	 * @param subOut character that will be removed from list
	 * @param subIn character that will be replaced in list
	 *****************************************************************/
	private void substitute(String subOut,String subIn){
		for(int i  = 0; i < message.size(); i++){
			if(message.get(i).charValue() == subOut.charAt(0)){
				message.removeIndex(i);
				message.add(i,subIn.charAt(0));
			}
		}
	}
}
