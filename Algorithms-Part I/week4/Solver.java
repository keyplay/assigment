/******************************************************************************
 *  Compilation:  javac-algs4 Solver.java
 *  Execution:    java-algs4 Solver textfile
 *  Dependencies: Board.java
 *  
 *  An immutable data type Slover.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Solver {
    private boolean flagSolved;
    private List<Board> solutionBoard = new ArrayList<>();
    
    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        MinPQ<SolutionStep> priorizedSteps = new MinPQ<>(new SolutionStepComparator());
        priorizedSteps.insert(new SolutionStep(initial, 0, null));
        
        // To detect such situations, use the fact that boards are divided into two equivalence classes 
        // with respect to reachability: (i) those that lead to the goal board and 
        // (ii) those that lead to the goal board if we modify the initial board by swapping any pair of blocks
        MinPQ<SolutionStep> priorizedStepsTwin = new MinPQ<>(new SolutionStepComparator());
        priorizedStepsTwin.insert(new SolutionStep(initial.twin(), 0, null));
        
        SolutionStep step;
        while (!priorizedSteps.min().getBoard().isGoal() && !priorizedStepsTwin.min().getBoard().isGoal()) {
            step = priorizedSteps.delMin();
            for (Board neighbor : step.getBoard().neighbors()) {
                // if the neighbor is not in the solution step, add it to the priority queue
                if (!isInSolution(step, neighbor)) {
                    priorizedSteps.insert(new SolutionStep(neighbor, step.getMoves()+1, step));
                }
            }
            
            step = priorizedStepsTwin.delMin();
            for (Board neighbor : step.getBoard().neighbors()) {
                if (!isInSolution(step, neighbor)) {
                    priorizedStepsTwin.insert(new SolutionStep(neighbor, step.getMoves()+1, step));
                }
            }

        }
        
        step = priorizedSteps.delMin();
        flagSolved = step.getBoard().isGoal();
  
        if (isSolvable()) {
            solutionBoard.add(step.getBoard());
            while (step.getPreStep() != null) {
                step = step.getPreStep();
                solutionBoard.add(0, step.getBoard());
            }
        }
    }
    
    // detect if the neighbor is not in the solution step
    private boolean isInSolution(SolutionStep laststep, Board board) {
        SolutionStep tempstep = laststep;
        while (tempstep != null) {
            if (tempstep.getBoard().equals(board)) {
                return true;
            }
            tempstep = tempstep.getPreStep();
        }
        return false;
    }
    
    public boolean isSolvable() {            // is the initial board solvable?
        return flagSolved;
    }
    
    public int moves() {                     // min number of moves to solve initial board; -1 if unsolvable
        if (isSolvable()) {
            return solutionBoard.size() - 1;
        } else {
            return -1;
        }
    }
    
    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        if (isSolvable()) {
            return new Iterable<Board>() {
                public Iterator<Board> iterator() {
                    return new SolutionIterator();
                }
            };
        }
        return null;
    }
    
    private class SolutionIterator implements Iterator<Board> {
        private int index = 0;
        
        @Override
        public boolean hasNext() {
            return index < solutionBoard.size();
        }
        
        @Override
        public Board next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } 
            return solutionBoard.get(index++);
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    // a data type for priority queue to store the board, moves already has and previous step
    private static class SolutionStep {
        private Board board;
        private int moves;
        private SolutionStep preStep;
        
        private SolutionStep(Board board, int moves, SolutionStep preStep) {
            this.board = board;
            this.moves = moves;
            this.preStep = preStep;
        }
        
        public int getMoves() {
            return moves;
        } 
        
        public int getPriority() {
            return board.manhattan() + moves;
        }
        
        public Board getBoard() {
            return board;
        }
        
        public SolutionStep getPreStep() {
            return preStep;
        }
    }
    
    private static class SolutionStepComparator implements Comparator<SolutionStep> {
        @Override
        public int compare(SolutionStep step1, SolutionStep step2) {
            return step1.getPriority() - step2.getPriority();
        }
    }
    
    public static void main(String[] args) {// solve a slider puzzle (given below)
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
