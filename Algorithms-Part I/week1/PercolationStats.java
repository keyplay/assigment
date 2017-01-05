/******************************************************************************
 *  Compilation:  javac-algs4 PercolationStats.java
 *  Execution:    java-algs4 PercolationStats.java n trials
 *  Dependencies: PercolationStats.java Percolation.java
 *
 *  This program takes the grid size n and times of trial as command-line arguments.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    
    private double[] threshold;
    
    public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid
        // check if the arguments is valid 
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trials out of bound");
        }
        
        threshold = new double[trials];  // store every trial's threshold
        int row;
        int col;
        
        for (int i = 0; i < trials; i++) {
            Percolation perco = new Percolation(n);
            while (!perco.percolates()) {
                // randomly select site from (1,1) to (n,n) to open
                row = StdRandom.uniform(1, n+1);
                col = StdRandom.uniform(1, n+1);
                perco.open(row, col);
            }
            
            threshold[i] = (double) perco.numberOfOpenSites()/(n*n);
        }
    }
    
    public double mean() {                         // sample mean of percolation threshold
        return StdStats.mean(threshold);
    }
    
    public double stddev() {                        // sample standard deviation of percolation threshold
        return StdStats.stddev(threshold);
    }
    
    public double confidenceLo() {                  // low  endpoint of 95% confidence interval
        return mean() - 1.96 * stddev() / Math.sqrt(threshold.length);
    }
    
    public double confidenceHi() {                  // high endpoint of 95% confidence interval
        return mean() + 1.96 * stddev() / Math.sqrt(threshold.length);
    }
    
    public static void main(String[] args) {        // test client (described below)
        PercolationStats percoS = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        
        System.out.printf("mean                    = %f\n", percoS.mean());
        System.out.printf("stddev                  = %f\n", percoS.stddev());
        System.out.printf("95%% confidence interval = %f, %f\n", percoS.confidenceLo(), percoS.confidenceHi());
    }
}
