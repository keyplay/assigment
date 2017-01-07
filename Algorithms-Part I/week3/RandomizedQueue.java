/*******************************************************************************
 *  Compilation:  javac-algs4 RandomizedQueue.java
 *  Execution:    java-algs4 RandomizedQueue
 *  Dependencies: RandomizedQueue.java                
 *
 *  This program takes no command-line argument.
 *  It is a randomized queue which is similar to a stack or queue, 
 *  except that the item removed is chosen uniformly at random 
 *  from items in the data structure. 
 *
 ******************************************************************************/

import java.util.*;
import edu.princeton.cs.algs4.*;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int MIN_SIZE = 2;
    
    private int first;          // index of the queue
    private Item[] items;       // store the value in queue
   
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < first; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }
    
    public RandomizedQueue() {                // construct an empty randomized queue
        first = 0;
        items = (Item[]) new Object[MIN_SIZE];
    }
    
    public boolean isEmpty() {                // is the queue empty?
        return first == 0;
    }
    
    public int size() {                       // return the number of items on the queue
        return first;
    }
    
    public void enqueue(Item item) {          // add the item
        if (item == null) {
            throw new NullPointerException();
        }
        
        if (first == items.length) {          // if the queue is full, double the size of queue
            resize(2* items.length);
        }
        items[first++] = item;
    }
    
    public Item dequeue() {                   // remove and return a random item
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        
        int idx = StdRandom.uniform(first);
        Item item = items[idx];
        items[idx] = items[--first];
        items[first] = null;
        
        if (4*first <= items.length && items.length > MIN_SIZE) {   // if the queue is quarter of total size, halve the size of queue
            resize(items.length / 2);
        }
        
        return item;
    }
    
    public Item sample() {                    // return (but do not remove) a random item
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        
        int idx = StdRandom.uniform(first);
        Item item = items[idx];
        
        return item;
    }
    
    public Iterator<Item> iterator() {        // return an independent iterator over items in random order
        return new RandomIterator();
    }
    
    private class RandomIterator implements Iterator<Item> {
        private int idx;
        private Item[] randomItems;
        
        public RandomIterator() {
            idx = first;
            randomItems = (Item[]) new Object[first];
            
            for (int i=0; i < first; i++) {
                randomItems[i] = items[i];
            } 
            
            StdRandom.shuffle(randomItems);
        }
        
        public boolean hasNext() {
            return idx > 0;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (hasNext()) {
                return randomItems[--idx];
            } else {
                throw new NoSuchElementException();
            }
        }
    }
    
    public static void main(String[] args) {  // unit testing
        RandomizedQueue<Integer> nums = new RandomizedQueue<Integer>();
        nums.enqueue(1);
        nums.enqueue(2);
        nums.enqueue(3);
        StdOut.println(nums.sample());
        StdOut.println(nums.size());
        for (int i=0; i < 3; i++) StdOut.println(nums.dequeue());
    }
}
