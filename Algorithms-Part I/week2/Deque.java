/******************************************************************************
 *  Compilation:  javac-algs4 Deque.java
 *  Execution:    java-algs4 Deque
 *  Dependencies: Deque.java                
 *
 *  This program takes no command-line argument.
 *  It is a generalization of a queue that 
 *  supports adding and removing items from either the front or 
 *  the back of the data structure.
 *
 ******************************************************************************/

import java.util.*;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int sizeCount;
    
    private class Node {
        private Item item;
        private Node pre;
        private Node next;
    }
    
    public Deque() {                          // construct an empty deque
        sizeCount = 0;
        first = null;
        last = null;
    }
    
    public boolean isEmpty() {                // is the deque empty?
       return sizeCount == 0;
    }
    
    public int size() {                       // return the number of items on the deque
        return sizeCount;
    }
    
    public void addFirst(Item item) {         // add the item to the front
        if (item == null) {
            throw new NullPointerException("can not add a null item");
        }
        
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst; 
        if (isEmpty()) {
            last = first;
        } else {
            oldfirst.pre = first;
        } 

        sizeCount += 1;        
    }
    
    public void addLast(Item item) {          // add the item to the end
        if (item == null) {
            throw new NullPointerException("can not add a null item");
        }
        
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.pre = oldlast;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
        }
        
        sizeCount += 1;
    }
    
    public Item removeFirst() {               // remove and return the item from the front
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        
        Item item = first.item;
        first = first.next;
        sizeCount -= 1;
        if (isEmpty()) {
            last = first;
        } else {
            first.pre = null;
        }
        
        return item;
    }
    
    public Item removeLast() {                // remove and return the item from the end
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        
        Item item = last.item;
        last = last.pre;
        sizeCount -= 1;
        if (isEmpty()) {
            first = last;
        } else {
            last.next = null;
        }
        
        return item;
    }
    
    public Iterator<Item> iterator() {        // return an iterator over items in order from front to end
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    public static void main(String[] args) {  // unit testing
        Deque<Integer> deque = new Deque<Integer>();
        
        deque.addFirst(1);
        deque.addLast(2);
        if (deque.size() != 2) StdOut.println("Errors in size");
        if (deque.removeFirst() != 1) StdOut.println("Errors");
        if (deque.removeLast() != 2) StdOut.println("Errors");
        if (!deque.isEmpty()) StdOut.println("Errors in empty");
    }
}
