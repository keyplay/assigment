/******************************************************************************
 *  Compilation:  javac-algs4 Percolation.java
 *  Execution:    java-algs4 Percolation.java
 *  Dependencies: Percolation.java                
 *
 *  This program takes no command-line argument.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] state;
    private int gridn;
    private int countOpen;
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF gridnoLastSite;   // in case backwash problem
   
    public Percolation(int n) {                // create n-by-n grid, with all sites blocked
        if (n <= 0) {
            throw new IllegalArgumentException("n should be larger than 0");
        }
       
        grid = new WeightedQuickUnionUF(n*n+2);
        gridnoLastSite = new WeightedQuickUnionUF(n*n+1);
        state = new boolean[n*n+2];
        gridn = n;
        countOpen = 0;
        
        // initialize the state array
        for (int i = 1; i < state.length-1; i++) {
            state[i] = false;           
        }
        state[0] = true;
        state[state.length-1] = true;
    }
   
    private int getIndex(int row, int col) {
        if (row <= 0 || row > gridn) {
            throw new IndexOutOfBoundsException("row out of bound"); 
        }
        if (col <= 0 || col > gridn) {
            throw new IndexOutOfBoundsException("col out of bound"); 
        }
   
        return gridn * (row - 1) + col;
    }
       
    public void open(int row, int col) {    // open site (row, col) if it is not open already
        int index = getIndex(row, col);
        
        // in case repeat open one site
        if (state[index]) {
            return;
        }
        
        state[index] = true;
        countOpen += 1;

        if (row != 1 && isOpen(row-1, col)) {
            grid.union(index, getIndex(row-1, col));
            gridnoLastSite.union(index, getIndex(row-1, col));
        }
        if (row != gridn && isOpen(row+1, col)) {
            grid.union(index, getIndex(row+1, col));
            gridnoLastSite.union(index, getIndex(row+1, col));
        }
        if (col != 1 && isOpen(row, col-1)) {
            grid.union(index, getIndex(row, col-1));
            gridnoLastSite.union(index, getIndex(row, col-1));
        }
        if (col != gridn && isOpen(row, col+1)) {
            grid.union(index, getIndex(row, col+1));
            gridnoLastSite.union(index, getIndex(row, col+1));
        }
        // check the situation of boundary
        if (row == 1) {        
            grid.union(0, index);
            gridnoLastSite.union(0, index);
        }
        if (row == gridn) {
            grid.union(state.length-1, index);
        }
    }
       
    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        int index = getIndex(row, col);
        return state[index];
    }
       
    public boolean isFull(int row, int col) {  // is site (row, col) full?
        int index = getIndex(row, col);
        return grid.connected(0, index) && gridnoLastSite.connected(0, index);
    }
    
    public int numberOfOpenSites() {       // number of open sites       
        return countOpen;
    }
    
    public boolean percolates() {              // does the system percolate?
        return grid.connected(0, state.length-1);
    }

    public static void main(String[] args) {   // test client (optional)       
        // test backwash problem
        Percolation p = new Percolation(3);
        p.open(1, 3);
        p.open(2, 3);
        p.open(3, 3);
        p.open(3, 1);

        System.out.printf("%b", p.isFull(3, 1));
    }
}
