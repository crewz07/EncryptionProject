package Project4;

import Chess.Pawn;

import javax.xml.soap.Node;

public class DoubleLinkedList<E>  {
	protected NodeD<E> top;      // The first NodeD<E> in the list

    // This instance variable is not required, however if you
    // find it useful, then you are welcome to use it
	protected NodeD<E> cursor;  // The current NodeD<E> in the list

	public DoubleLinkedList() {
		top = null;
		cursor = null;
	}

	public E get(int position) {
		cursor = top;
		for (int i = 0; i < position; i++)
			cursor = cursor.getNext();
		return cursor.getData();

	}

	public String toString() {
		String retVal = "";
		NodeD<E> cur = top;
		while (cur != null) {
			retVal += cur.getData();
			cur = cur.getNext();
		}

		return retVal;
	}

	// Create the rest of the needed methods.

	public void addFirst(E data){

		if (isEmpty()){

			top = new NodeD<>(data, null, null);
		}

		else {
			top = new NodeD<>(data, top, null);
			top.getNext().setPrev(top);
		}
	}

	public void addLast(E data){

		// Empty list
		if (isEmpty()){

			addFirst(data);
		}

		else if (size() == 1){

			NodeD newNode = new NodeD(data, null, top);
			top.setNext(newNode);
		}

		else {
			// Getting to the last spot
			cursor = top;
			while (cursor.getNext() != null){
				cursor = cursor.getNext();
			}

			// At the last spot
			cursor = new NodeD<>(data, null, cursor);
			cursor.getPrev().setNext(cursor);
		}
	}

	public void add(int index, E data){

		if (index == 0){

			addFirst(data);
			return;
		}

		else if (index < 0 || index > size()){

			throw new IndexOutOfBoundsException("Index " +
					"is out of Range.");
		}

		else{

			cursor = top;

			// getting to the nexted spot
			for (int i = 0; i < index; i++){
				cursor = cursor.getNext();
			}

			// currently at index
			cursor = new NodeD<>(data,cursor,
					cursor.getPrev());
			cursor.getNext().setPrev(cursor);
			cursor.getPrev().setNext(cursor);
		}
	}

	public void addClip(int index, NodeD firstNode,
						NodeD lastNode){

//
//		while (index != start){
//			current = current.getNext();
//			index++;
//		}
	}

	public NodeD removeFirst(){

		NodeD remove = top;

		if (isEmpty()){

			throw new IndexOutOfBoundsException("Empty" +
					"list");
		}

		top = top.getNext();
		top.setPrev(null);

		return remove;
	}

	public NodeD removeLast(){

		NodeD remove;

		if (isEmpty()){

			throw new IndexOutOfBoundsException("Empty " +
					"list");
		}

		// getting to the last spot
		// Getting to the last spot
		cursor = top;
		while (cursor.getNext() != null){

			cursor = cursor.getNext();
		}

		// At the last spot
		remove = cursor;

		if (cursor.getPrev() != null){
			cursor.getPrev().setNext(null);
		}
        remove.setPrev(null);

        return remove;
	}

	public NodeD removeIndex(int index){

		NodeD remove;

		if (isEmpty()){

		    throw new IndexOutOfBoundsException("Empty " +
                    "list");
        }

        else if (index < 0 || index > size()){

            throw new IndexOutOfBoundsException("Index is" +
                    "not within range");
        }


        if (index == 0){

        	remove = removeFirst();
		}

		else if (size() - 1 == index){

			remove = removeLast();
		}

		else {
			cursor = top;
			for (int i = 0; i < index; i++) {

				cursor = cursor.getNext();
			}

			// cursor is at the spot of the index
			remove = cursor;

			if (cursor.getNext() != null) {

				cursor.getPrev().setNext(remove.getNext());
				cursor.getNext().setPrev(remove.getPrev());
				remove.setPrev(null);

			}

//        if (size() > 1)
//        	cursor.getPrev().setNext(cursor.getNext());
		}
        return remove;
	}

	public NodeD removeClip(int start, int stop){

		NodeD removeStart = top;
		NodeD current = top;
		int index = 0;

		// error check
		if (isEmpty()){

			throw new IndexOutOfBoundsException();
		}

		else if (start > stop || start < 0 || start > size() -1){

			throw new IndexOutOfBoundsException();
		}

		else if (stop < 0 || stop > size() - 1){

			throw new IndexOutOfBoundsException();
		}

		// find first point
		while (index != start){
			current = current.getNext();
			index++;
		}

		removeStart = current;

		while (index != stop){
			current = current.getNext();
			index++;
		}

		// current is at the last spot
		if (removeStart.getPrev() != null){

			removeStart.getPrev().setNext(current.getNext());
		}

		else {
			top = current;
		}

		if (current.getNext() != null)
			current.getNext().setPrev(removeStart.getPrev());

		removeStart.setPrev(null);

		return removeStart;
	}

	private boolean isEmpty(){

		if (top == null){

			return true;
		}

		return false;
	}

	private int size(){

		cursor = top;
		int total = 0;

		while (cursor != null){

			cursor = cursor.getNext();
			total++;
		}

		return total;
	}

}
