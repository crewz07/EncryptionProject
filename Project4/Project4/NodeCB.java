package Project4;

/***********************************************************************
 * This class is for the Nodes in the clipboard link list.
 *
 * @author Andrew & Wayne Chen
 * @version 4-1-19
 **********************************************************************/
public class NodeCB {

    /*******************************************************************
     * The variable associated with the clipboard number of the NodeCB.
     ******************************************************************/
    private int clipBoardNumber;

    /*******************************************************************
     * This is the NodeD at the top of the clipboard.
     ******************************************************************/
    private NodeD<Character> topOfClipBoard;

    /*******************************************************************
     * This is the NodeCB after this NodeCB.
     ******************************************************************/
    private NodeCB next;

    /*******************************************************************
     * This method is the construoctor of the NodeCB. This should not
     * be used since it does nothing.
     ******************************************************************/
    public NodeCB() {
    }

    /*******************************************************************
     * This method is the constructor of NodeCB. THis constructor will
     * set the clipboard number, the NodeD at the top of this Node, and
     * the NodeCB that is next in the linked list.
     *
     * @param clipBoardNumber The clipboard number of this NodeCB.
     * @param top The NodeD at the top of this NodeCB.
     * @param next
     ******************************************************************/
    public NodeCB(int clipBoardNumber, NodeD<Character> top,
                  NodeCB next) {

        this.clipBoardNumber = clipBoardNumber;
        this.topOfClipBoard = top;
        this.next = next;
    }

    /*******************************************************************
     * This method gets the clipboard number of this NodeCB.
     *
     * @return  The clipboard number of this NodeCB.
     ******************************************************************/
    public int getClipBoardNumber() {
        return clipBoardNumber;
    }

    /*******************************************************************
     * This method sets the clipboard number of this NodeCB.
     *
     * @param clipBoardNumber The clipboard number of this NodeCB.
     ******************************************************************/
    public void setClipBoardNumber(int clipBoardNumber) {
        this.clipBoardNumber = clipBoardNumber;
    }

    /*******************************************************************
     * This method gets the NodeD at the top of this NodeCB.
     *
     * @return The NodeD at the top of this NodeCB.
     ******************************************************************/
    public NodeD getTopOfClipBoard() {
        return topOfClipBoard;
    }

    /*******************************************************************
     * This method sets the NodeD at the top of the clipboard.
     *
     * @param topOfClipBoard The NodeD at the top of this clipboard.
     ******************************************************************/
    public void setTopOfClipBoard(NodeD topOfClipBoard) {
        this.topOfClipBoard = topOfClipBoard;
    }

    /*******************************************************************
     * This method gets the NodeCB that is next in the linked list
     *
     * @return The NodeCB that is next in the list.
     ******************************************************************/
    public NodeCB getNext() {
        return next;
    }

    /*******************************************************************
     * This method sets the NodeCB that is next in the linked list
     *
     * @param next The NodeCB that is next in the list.
     ******************************************************************/
    public void setNext(NodeCB next) {
        this.next = next;
    }

}
