package Project4Pack;

public class clipBdLinkedList {
    private NodeCB top;
    private NodeCB tail;

    public clipBdLinkedList() {
        tail = top = null;
    }

    // create methods you need.
    public void addFirst(NodeD<Character> data){


        // Nothing is in the list yet
        if (isEmpty()){

            top = new NodeCB(1, data,
                    null);
            tail = top;
        }

        else{

            NodeCB current = top;

            top = new NodeCB(1, data,
                    current);

            resetClipboardNum();
            setTail();
        }
    }

    public void addLast(NodeD<Character> data){

        // Nothing is in the list yet
        if (isEmpty()){

            addFirst(data);
        }

        else{

            NodeCB prevTail = tail;
            NodeCB newNode = new NodeCB(prevTail.
                    getClipBoardNumber() + 1, data,null);

            tail = newNode;
            prevTail.setNext(tail);
        }

        resetClipboardNum();
        setTail();
    }

    public void add(NodeD<Character> data, int index){

        if (isEmpty() && index == 0){

            addFirst(data);
        }

        else if (index > size() || index < 1){

            throw new IndexOutOfBoundsException("Index" +
                    "out of bounds");
        }

        else {

            NodeCB current = findNodeCB(index);
            NodeCB prev = findNodeCB(index - 1);

            // Set up references
            NodeCB next = new NodeCB(99999, data,
                    current.getNext());
            next.setNext(current);
            prev.setNext(next);
        }

        resetClipboardNum();
    }

    public NodeCB removeFirst(){

        // error if empty
        if (isEmpty()){

            throw new IndexOutOfBoundsException("" +
                    "Empty list");
        }

        NodeCB remove;

        remove = top;
        top = top.getNext();

        resetClipboardNum();
        return remove;
    }

    public NodeCB removeLast(){

        // error if empty
        if (isEmpty()){

            throw new IndexOutOfBoundsException("" +
                    "Empty list");
        }

        NodeCB remove = tail;
        int lastNum = tail.getClipBoardNumber();

        NodeCB prev = findNodeCB(lastNum - 1);
        prev.setNext(null);
        tail = prev;

        resetClipboardNum();
        return remove;
    }

    public NodeCB removeIndex(int index){

        NodeCB remove;

        if (isEmpty()){

            throw new IndexOutOfBoundsException("Empty" +
                    "List");
        }

        if (index > size() || index < 0){

            throw new IndexOutOfBoundsException("Index" +
                    "out of bounds");
        }

        if (index == 0){

            remove = removeFirst();
        }

        else if (index == size() - 1){

            remove = removeLast();
        }

        else {
            NodeCB prev = findNodeCB(index - 1);
            remove = findNodeCB(index);

            prev.setNext(remove.getNext());
        }

        resetClipboardNum();
        return remove;
    }


    public int size(){

        int count = 0;

        NodeCB current = top;
        while(current != null){

            current = current.getNext();
            count++;
        }

        return count;
    }

    private boolean isEmpty(){

        if (size() == 0){

            return true;
        }

        return false;
    }

    private void setTail(){

        NodeCB current = top;

        while (current != null){

            current = current.getNext();
        }

        tail = current;
    }

    private void resetClipboardNum(){

        NodeCB current = top;
        int index = 0;

        while (current != null){

            current.setClipBoardNumber(index);
            current = current.getNext();
            index++;
        }
    }

    private NodeCB findNodeCB(int index){

        NodeCB data = top;

        for (int i = 0; i < index; i++){

            data = data.getNext();
        }

        return data;
    }
}
