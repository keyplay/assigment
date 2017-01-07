/******************************************************************************
 *  Compilation:  javac-algs4 Permutation.java
 *  Execution:    java-algs4 Permutation
 *  Dependencies: Permutation.java              
 *
 *  This program takes the number of output k and input filename as command-line argument.
 *  Prints exactly k of input, uniformly at random. 
 *  Print each item from the sequence at most once
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    
    public static void main(String[] args) {
        RandomizedQueue<String> s = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        
        while (!StdIn.isEmpty()) {
            s.enqueue(StdIn.readString());
        }
        
        for (int i=0; i<k; i++) {
            StdOut.println(s.dequeue());
        }
    }
}
