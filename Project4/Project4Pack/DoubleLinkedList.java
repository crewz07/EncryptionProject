package Project4Pack;

/***********************************************************************
 * This is a double linked list for the the message that is going to
 * be encrypted.
 *
 * @param <E> The data type for the double linked list.
 * @author Andrew Kruse & Wayne Chen
 * @version 4-8-19
 **********************************************************************/
public class DoubleLinkedList<E>  {

    /*******************************************************************
     * The first NodeD<E> in the list.
     ******************************************************************/
	protected NodeD<E> top;

    /*******************************************************************
     * The current NodeD in the list.
     ******************************************************************/
    protected NodeD<E> cursor;

    /*******************************************************************
     * This method is the constructor for the double linked list. In
     * addition, it will set top and cursor to null;
     ******************************************************************/
	public DoubleLinkedList() {
		top = null;
		cursor = null;
	}

    /*******************************************************************
     * This method gets the data<E> at position.
	 *
     * @param position The position at the given data.
     * @return The character/data at position.
     ******************************************************************/
	public E get(int position) {
		cursor = top;
		for (int i = 0; i < position; i++)
			cursor = cursor.getNext();
		return cursor.getData();

	}

    /*******************************************************************
     * This method returns a string from the data of <E>. The works
     * best when the data<E> is either characters or string.
     *
     * @return The string of the double linked list.
     ******************************************************************/
	public String toString() {
		String retVal = "";
		NodeD<E> cur = top;
		while (cur != null) {
			retVal += cur.getData();
			cur = cur.getNext();
		}

		return retVal;
	}

    /*******************************************************************
     * This method adds a new data to the linked list.
     *
     * @param data The data that is being added.
     ******************************************************************/
	public void addFirst(E data){

		if (isEmpty()){

			top = new NodeD<>(data, null, null);
		}

		else {
			top = new NodeD<>(data, top, null);
			top.getNext().setPrev(top);
		}
	}

	/*******************************************************************
	 * This method adds a data value at the last spot in the index.
	 *
	 * @param data The data that is being added to the last spot.
	 ******************************************************************/
	public void addLast(E data){

		// Empty list
		if (isEmpty()){

			addFirst(data);
		}

		// Only 1 element in the list
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

	/*******************************************************************
	 * This method adds a data value at a specific index.
	 *
	 * @param index The index the data is begin added at.
	 * @param data The data that is begin added at index.
	 ******************************************************************/
	public void add(int index, E data){

		if (index == 0){

			addFirst(data);
			return;
		}

		// would be inserting at last slot
		else if (index == size()){

			addLast(data);
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

	/*******************************************************************
	 * This method adds a set of NodeD to this double linked list.
	 *
	 * @param index The index the set is being added to.
	 * @param firstNode The first Node in the set of Node D.
	 ******************************************************************/
	public void addClip(int index, NodeD firstNode){

		// Last node in the clipboard
	    NodeD last = firstNode;

	    NodeD prev;
	    NodeD firstList = top;
		NodeD lastList = top;
	    int i = 0;

		// Checking if index is correct
		if (index < 0 || index > size()){

			throw new IndexOutOfBoundsException("Index " +
					"is out of Range.");
		}

		// Getting to last element in the the clipboard
		// link list
		while(last.getNext() != null){

		    last = last.getNext();
        }

        // Finding the Node at the index
        while(index != i){

        	firstList = firstList.getNext();
        	i++;
		}

		prev = firstList.getPrev();

        // Getting to the last element in the current
		// Double link list
        while(lastList.getNext() != null){

        	lastList = lastList.getNext();
		}

        // Adding at first index
		if (index == 0) {

		    top.setPrev(last);
		    last.setNext(top);
            top = firstNode;
        }

        // Node is added to the last slot
        else if (index == size() - 1){

        	lastList.setNext(firstNode);
        	firstNode.setPrev(lastList);
		}

		// Adding in the middle
		else {

			prev.setNext(firstNode);
			firstNode.setPrev(prev);
			firstList.setPrev(last);
			last.setNext(firstList);
		}
	}

	/*******************************************************************
	 * The method gets a copy of a double linked list from the start
     * index to the stop index.
     *
	 * @param start The start index.
	 * @param stop The stop index.
	 * @return A double linked list of the clip from the start index to
     *         the stop index. The list will also include the stop
     *         index.
	 ******************************************************************/
	public DoubleLinkedList getClip(int start,int stop){

		DoubleLinkedList<Character> copied = new DoubleLinkedList<>();

		//iterate over distance of start and stop
		String thisNodeStr = this.toString();
		Character startNode;

		for (int i = start; i <= stop; i++){

			startNode = thisNodeStr.charAt(i);
			copied.addLast(startNode);
		}

		return copied;
	}

    /*******************************************************************
     * This method gets the string from start index string to the
     * index of stop.
     *
     * @param start The start index.
     * @param stop The stop index.
     * @return The string from the start to index of stop.
     ******************************************************************/
	public String getStringClip(int start, int stop){

		return this.toString().substring(start, stop + 1);
	}

    /*******************************************************************
     * This method removes the first Node in the double linked list.
     *
     * @return The Node that was removed from the list.
     ******************************************************************/
	public NodeD removeFirst(){

		NodeD remove = top;

		if (isEmpty()){

			throw new IndexOutOfBoundsException("Empty" +
					"list");
		}

		if (top.getNext() != null){

			top = top.getNext();
			top.setPrev(null);
		}

		else{

			top = null;
		}

		return remove;
	}

    /*******************************************************************
     * This method removes the last NodeD in the double linked list.
     *
     * @return The NodeD that is removed.
     ******************************************************************/
	public NodeD removeLast(){

		NodeD remove;

		if (isEmpty()){

			throw new IndexOutOfBoundsException("Empty " +
					"list");
		}

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

    /*******************************************************************
     * This method removes a node at a specific index. This will be
     * valid as long as the index is a valid index.
     *
     * @param index The index that is being removed.
     * @return The NodeD that was removed.
     ******************************************************************/
	public NodeD removeIndex(int index){

		NodeD remove;

		// Error Checks
		if (isEmpty()){

		    throw new IndexOutOfBoundsException("Empty " +
                    "list");
        }

        else if (index < 0 || index > size() - 1){

            throw new IndexOutOfBoundsException("Index is" +
                    "not within range");
        }

        // Removing at the start
        if (index == 0){

        	remove = removeFirst();
		}

		// Removing last element
		else if (size() - 1 == index){

			remove = removeLast();
		}

		// Removing element in the middle
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
		}
        return remove;
	}

    /*******************************************************************
     * The method removes a section from the this double linked list
     * from start to stop. This will also include the Node at stop.
     *
     * @param start The start index.
     * @param stop The stop index.
     * @return The NodeD that is from the start index.
     ******************************************************************/
	public NodeD removeClip(int start, int stop){

		NodeD removeStart;
		NodeD current = top;
		int index = 0;

		// error check
		if (isEmpty()){

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

		// Start is the first index
		if (removeStart.getPrev() == null){

			// In event the entire list was removed
			if (current.getNext() != null){

				current.getNext().setPrev(null);
				top = current.getNext();
				current.setNext(null);
			}
		}

		// Stop is the last index
		else if (current.getNext() == null){

			// In event the entire list was removed
			if (removeStart.getPrev() != null) {

				removeStart.getPrev().setNext(null);
				removeStart.setPrev(null);
			}
		}

		// Index are right next to each other and in middle
		else if (current.getPrev() == removeStart){

			removeStart.getPrev().setNext(current.getNext());
			current.getNext().setPrev(removeStart.getPrev());
		}

		// Index is in the middle that is not next to each other
		else if (current.getNext() != null && removeStart.getPrev() !=
				null){

			removeStart.getPrev().setNext(current.getNext());
			current.getNext().setPrev(removeStart.getPrev());

			removeStart.setPrev(null);
			current.setNext(null);
		}

		return removeStart;
	}

    /*******************************************************************
     * This method will determine if this double linked list is empty.
     *
     * @return True if this linked list is empty.
     ******************************************************************/
	private boolean isEmpty(){

		if (top == null){

			return true;
		}

		return false;
	}

    /*******************************************************************
     * The method gets the size of the this double linked list
     *
     * @return The size of the double linked list.
     ******************************************************************/
	public int size(){

		cursor = top;
		int total = 0;

		while (cursor != null){

			cursor = cursor.getNext();
			total++;
		}

		return total;
	}

    /*******************************************************************
     * This method deletes a character from the double linked list.
     *
     * @param data The character that is being deleted.
     * @return The index value the character was deleted from.
     ******************************************************************/
	public int delete (char data) {

		int index = 0;
		NodeD cursor = top;

		if (top == null)
			return -1;

		// deleting first data
		if (cursor.getData().equals(data)) {
			cursor = top.getNext();
			if (cursor != null) {
				cursor.setPrev(null);
				top = cursor;
			}

			else {

				top = null;
			}
			return index;
		}

		// Deleting data that is not the first data
		NodeD temp = top;
		while (temp.getNext() != null) {

			if (temp.getData().equals(data)) {
				temp.getPrev().setNext(temp.getNext());
				temp.getNext().setPrev(temp.getPrev());
				return index;
			}
			index++;
			temp = temp.getNext();
		}

		if (temp.getData().equals(data)) {
			temp.getPrev().setNext(null);
			return index;
		}

		return -1;
	}
}
