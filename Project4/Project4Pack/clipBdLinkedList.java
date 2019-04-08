package Project4Pack;

/***********************************************************************
 * This is the class for a single linked list for the clipboard.
 *
 * @author Andrew Kruse & Wayne Chen
 * @version 4-8-19
 **********************************************************************/
public class clipBdLinkedList {

    /*******************************************************************
     * This is the node at the top of the list.
     ******************************************************************/
    private NodeCB top;

    /*******************************************************************
     * This is the node at the bottom of the list.
     ******************************************************************/
    private NodeCB tail;

    /*******************************************************************
     * This method is the constructor for the linked list.
     ******************************************************************/
    public clipBdLinkedList() {
        tail = top = null;
    }

    /*******************************************************************
     * This method adds a new clipboard at first slot of the linked
     * list.
     *
     * @param data The NodeD at the top of the linked list that is being
     *             copied over.
     * @param num The clipboard associated with this clipboard.
     ******************************************************************/
    public void addFirst(NodeD<Character> data, int num){

        // Nothing is in the list yet
        if (isEmpty()){

            top = new NodeCB(num, data,
                    null);
            tail = top;
        }

        // At least one Item in the list
        else{

            NodeCB current = top;

            top = new NodeCB(num, data,
                    current);
            tail = top;
        }
    }

    /*******************************************************************
     * This method adds a new clipboard at last slot of the linked list.
     *
     * @param data The NodeD at the top of the linked list that is being
     *             copied over.
     * @param num The clipboard associated with this clipboard.
     ******************************************************************/
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

    /*******************************************************************
     * This method removes the first Node in the clipboard linked list.
     *
     * @return The first Node.
     ******************************************************************/
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

    /*******************************************************************
     * This method searches through the clipboard list and removes
     * the specific clipboard number.
     *
     * @param clipNum The clipboard number that is associated with the
     *                NodeCB that is being removed.
     * @return The remove NodeCB.
     ******************************************************************/
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

    /*******************************************************************
     * This method gets the size of the the clipboard linked list.
     * 
     * @return The size of the list.
     ******************************************************************/
    private int size(){

        int count = 0;

        NodeCB current = top;
        while(current != null){

            current = current.getNext();
            count++;
        }

        return count;
    }

    /*******************************************************************
     * This method checks if the list is empty.
     * 
     * @return True if the list is empty.
     ******************************************************************/
    private boolean isEmpty(){

        if (size() == 0){

            return true;
        }

        return false;
    }

    /*******************************************************************
     * This method determines if the clipboard number exist.
     * 
     * @param index The clipboard number that is being checked.
     * @return True if the clipboard number exist.
     ******************************************************************/
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
