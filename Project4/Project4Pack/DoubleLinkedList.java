package Project4Pack;

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

	public DoubleLinkedList getClip(int start,int stop){

		DoubleLinkedList<Character> copied = new DoubleLinkedList<>();

		//iterate over distance of start and stop
		String thisNodeStr = this.toString();
		Character startNode;

		for (int i = start; i <= stop; i++){

			startNode = thisNodeStr.charAt(i);
			copied.addLast(startNode);
		}

		//make the last node hold the reference for null
//		copied.findNodeD(j-1,copied).setNext(null);

		return copied;
	}

	public String getStringClip(int start, int stop){

		return this.toString().substring(start, stop + 1);
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

        else if (index < 0 || index > size() - 1){

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

		NodeD removeStart;
		NodeD current = top;
		int index = 0;

		// error check
		if (isEmpty()){

			throw new IndexOutOfBoundsException();
		}

		else if (start > stop || start < 0 || start > size()){

			throw new IndexOutOfBoundsException();
		}

		else if (stop < 0 || stop > size()){

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
		else if (current.getNext() != null && removeStart.getPrev() != null){

			removeStart.getPrev().setNext(current.getNext());
			current.getNext().setPrev(removeStart.getPrev());

			removeStart.setPrev(null);
			current.setNext(null);
		}

		return removeStart;
	}

	private boolean isEmpty(){

		if (top == null){

			return true;
		}

		return false;
	}

	public int size(){

		cursor = top;
		int total = 0;

		while (cursor != null){

			cursor = cursor.getNext();
			total++;
		}

		return total;
	}


	private NodeD findNodeD(int index, DoubleLinkedList list){
		NodeD data = list.top;

		for (int i = 0; i < index; i++){
			data = data.getNext();
		}

		return data;

	}

	public int delete (char data) {

		int index = 0;
		NodeD cursor = top;

		if (top == null)
			return -1;

		if (cursor.getData().equals(data)) {
			cursor = top.getNext();
			if (cursor != null)
				cursor.setPrev(null);

			return index;
		}

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
