package Project4Pack;
import java.io.*;
import java.util.Scanner;

public class UnMix {
	private DoubleLinkedList<Character> message;

	public UnMix() {
		message = new DoubleLinkedList<Character>();
	}

	public static void main(String[] args) {
	    UnMix v = new UnMix();
		v.unMixture(args[0], args[1]);
	}

	public String processCommand(String command) {
		Scanner scan = new Scanner(command);
		char charInput;

		try {
			command = scan.next();
			switch (command.charAt(0)) {

			    //if
                case 'b':
                    insertBefore(scan.next(), scan.nextInt());
                    break;
                case 'r':
                    remove(scan.nextInt(),scan.nextInt());
                    break;

			}
		} catch (Exception e) {
			System.out.println("Error in command!  Problem!!!! in undo commands");
			System.exit(0);
		}
		finally {
			scan.close();
		}

		return message.toString();
	}

	private void unMixture(String filename, String userMessage) {
		String original = UnMixUsingFile (filename, userMessage);
		System.out.println ("The Original message was: " + original);
	}


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

    private void insertBefore(String token, int index) {

        for (int i = token.length() - 1; i >= 0; i--){
            message.add(index, token.charAt(i));
        }
    }

    private void remove(int start, int stop) {

        for (int i = start; i <= stop; i++) {

            message.removeIndex(start);
        }
    }

    public void setMessage(String userMessage){

        for (int i = 0; i < userMessage.length(); i++){

            message.addLast(userMessage.charAt(i));
        }
    }

}
