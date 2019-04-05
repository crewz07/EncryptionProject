package Project4Pack;

import java.io.Serializable;

/***********************************************************************
 * This class is the node for each character that is stored. This is
 * used in the Double link list.
 *
 * @param <E> The data type that is stored.
 * @author Andrew & Wayne
 * @version 3-31-19
 **********************************************************************/
public class NodeD<E> implements Serializable {

    /*******************************************************************
     * The data type of Node D.
     ******************************************************************/
    public E data;

    /*******************************************************************
     * The reference the next Node D in the linked list.
     ******************************************************************/
    public NodeD next;

    /*******************************************************************
     * The reference the previous NodeD in the linked list.
     ******************************************************************/
    public NodeD prev;

    /*******************************************************************
     * This method is the basic constructor that will create the class.
     ******************************************************************/
    public NodeD() {
        super();
    }

    /*******************************************************************
     * This method is the constructor that will set the data of this
     * NodeD and reference to the next and previous NodeD.
     *
     * @param data The data of this NodeD of data type E.
     * @param next The reference to the next NodeD.
     * @param prev The reference to the previous NodeD.
     ******************************************************************/
    public NodeD(E data, NodeD next, NodeD prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    /*******************************************************************
     * The method gets the reference of this data in this Node D.
     * @return This method gets the data of type declared type of this
     *         NodeD.
     ******************************************************************/
    public E getData() {
        return data;
    }

    /*******************************************************************
     * This method will set the data of this node of type E.
     *
     * @param data2 The data type that is being stored in this node.
     ******************************************************************/
    public void setData(E data2) {
        this.data = data2;
    }

    /*******************************************************************
     * This method will get the reference to the next NodeD. This is
     * used in a linked list.
     *
     * @return  The reference of the previous NodeD.
     ******************************************************************/
    public NodeD getNext() {
        return next;
    }

    /*******************************************************************
     * This method will set the reference to the next NodeD. This is
     * used in a linked list.
     *
     * @param next The reference of the previous NodeD.
     ******************************************************************/
    public void setNext(NodeD next) {
        this.next = next;
    }

    /*******************************************************************
     * This method will get the reference to the previous NodeD. This is
     * used in a linked list.
     *
     * @return  The reference of the previous NodeD.
     ******************************************************************/
    public NodeD getPrev() {
        return prev;
    }

    /*******************************************************************
     * This method will set the reference to the previous NodeD. This is
     * used in a linked list.
     *
     * @param prev The reference of the previous NodeD.
     ******************************************************************/
    public void setPrev(NodeD prev) {
        this.prev = prev;
    }
}
