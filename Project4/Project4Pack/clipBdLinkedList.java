package Project4Pack;

public class clipBdLinkedList {
    private NodeCB top;
    private NodeCB tail;

    public clipBdLinkedList() {
        tail = top = null;
    }

    public void addFirst(NodeD<Character> data, int num){


        // Nothing is in the list yet
        if (isEmpty()){

            top = new NodeCB(num, data,
                    null);
            tail = top;
        }

        else{

            NodeCB current = top;

            top = new NodeCB(num, data,
                    current);
            tail = top;
        }
    }

    public void addLast(NodeD<Character> data, int num){

        // Nothing is in the list yet
        if (isEmpty()){

            addFirst(data, num);
        }

        else{

            NodeCB newNode = new NodeCB(num, data,null);

            tail.setNext(newNode);
            tail = newNode;
        }
    }

    public NodeCB removeFirst(){

        // error if empty
        if (isEmpty()){

            throw new IndexOutOfBoundsException("" +
                    "Empty list");
        }

        NodeCB remove;

        remove = top;

        if (top.getNext() != null)
            top = top.getNext();

        else
            top = null;

        return remove;
    }

    public NodeCB removeIndex(int clipNum){

        NodeCB remove;

        // Error Check
        if (isEmpty()){

            throw new IndexOutOfBoundsException("Empty" +
                    "List");
        }

        NodeCB prev = top;

        // element is at top
        if (prev.getClipBoardNumber() == clipNum){
            return removeFirst();
        }

        else {

            while (prev != null) {

                // Found index
                if (prev.getNext().getClipBoardNumber() == clipNum) {

                    remove = prev.getNext();

                    if (remove.getNext() != null) {

                        prev.setNext(remove.getNext());
                    } else {

                        prev.setNext(null);
                    }

                    return remove;

                } else {

                    prev = prev.getNext();
                }
            }
        }

        return null;
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

    public boolean indexExist(int index){

        NodeCB data = top;

        while (data != null){

            if (data.getClipBoardNumber() == index){

                return true;
            }

            else{

                data = data.getNext();
            }
        }

        return false;
    }
}
