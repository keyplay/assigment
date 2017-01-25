import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Board {
    private int[][] puzzleBlock;
    private Board[] arrNeighbors;
    
    private void exchangBlock(int[][] block, int row1, int col1, int row2, int col2) {
        int temp = block[row1][col1];
        block[row1][col1] = block[row2][col2];
        block[row2][col2] = temp;
    }
    
    private int[][] blockCopy(int[][] blocks) {
        int[][] copy = new int[blocks.length][];  // (where blocks[i][j] = block in row i, column j)
        for (int i = 0; i < blocks.length; i++) {
            copy[i] = blocks[i].clone();
        }
        return copy;
    }
    
    public Board(int[][] blocks) {               // construct a board from an n-by-n array of blocks
        puzzleBlock = blockCopy(blocks);
    }
    
    public int dimension() {                // board dimension n
        return puzzleBlock.length;
    }
    
    public int hamming()  {                 // number of blocks out of place
        int valueHam = -1;
        for (int i = 0; i < puzzleBlock.length; i++) {
            for (int j = 0; j < puzzleBlock[i].length; j++) {
                if (puzzleBlock[i][j] != i*puzzleBlock.length+j+1) {
                    valueHam += 1;
                }
            }
        }
        return valueHam;
    }
    
    public int manhattan() {                 // sum of Manhattan distances between blocks and goal
        int valueMan = 0;
        
        for (int i = 0; i < puzzleBlock.length; i++) {
            for (int j = 0; j < puzzleBlock[i].length; j++) {
                int temp = puzzleBlock[i][j];
                if (temp != i*puzzleBlock.length+j+1 && temp != 0) {
                    temp -= 1;                                  // calculation reason
                    valueMan += Math.abs(temp / dimension() - i) + Math.abs(temp % dimension() - j);
                }
            }
        }
        
        return valueMan;
    }
    
    public boolean isGoal() {               // is this board the goal board?
        return hamming() == 0;
    }
    
    public Board twin() {                   // a board that is obtained by exchanging any pair of blocks
        int[][] twinBlocks = blockCopy(puzzleBlock);
        int i = 0;
        int j = 0;
        
        while (twinBlocks[i][j] == 0 || twinBlocks[i][j+1] == 0) {
            j += 1;
            if (j >= twinBlocks.length - 1) {
                j = 0;
                i += 1;
            }
        }
        exchangBlock(twinBlocks, i, j, i, j+1);
        return new Board(twinBlocks);
    }
    
    public boolean equals(Object y) {       // does this board equal y?
        // in case the type of y is different
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        
        Board temp = (Board) y;
        
        // in case the size of y is different
        if (temp.puzzleBlock.length != puzzleBlock.length) {
            return false;
        }
        
        for (int i = 0; i < puzzleBlock.length; i++) {
            for (int j = 0; j < puzzleBlock[i].length; j++) {
                if (puzzleBlock[i][j] !=  temp.puzzleBlock[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public Iterable<Board> neighbors() {    // all neighboring boards
        return new Iterable<Board>() {
            public Iterator<Board> iterator() {
                if (arrNeighbors == null) {
                    getNeighbor();
                }
                return new NeighborIterator();
            }
        };
    }
    
    private class NeighborIterator implements Iterator<Board> {
        private int index = 0;
        
        public boolean hasNext() {
            return index < arrNeighbors.length;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Board next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } 
            
            return arrNeighbors[index++];
        }
    }
    
    private void getNeighbor() {
        List<Board> getNeighbors = new ArrayList<>();
        int i = 0;
        int j = 0;
        
        while (puzzleBlock[i][j] != 0) {
            j += 1;
            if (j >= dimension()) {
                j = 0;
                i += 1;
            }
        }
        
        if (i > 0) {
            int[][] tempNeighbor = blockCopy(puzzleBlock);
            exchangBlock(tempNeighbor, i-1, j, i, j);
            getNeighbors.add(new Board(tempNeighbor));
        }
        if (i < dimension() - 1) {
            int[][] tempNeighbor = blockCopy(puzzleBlock);
            exchangBlock(tempNeighbor, i, j, i+1, j);
            getNeighbors.add(new Board(tempNeighbor));
        }
        if (j > 0) {
            int[][] tempNeighbor = blockCopy(puzzleBlock);
            exchangBlock(tempNeighbor, i, j-1, i, j);
            getNeighbors.add(new Board(tempNeighbor));
        }
        if (j < dimension() - 1) {
            int[][] tempNeighbor = blockCopy(puzzleBlock);
            exchangBlock(tempNeighbor, i, j, i, j+1);
            getNeighbors.add(new Board(tempNeighbor));
        }
        
        arrNeighbors = getNeighbors.toArray(new Board[getNeighbors.size()]);
    }
    
    public String toString() {              // string representation of this board (in the output format specified below)
        StringBuilder stringBoard = new StringBuilder(puzzleBlock.length+"\n");
        
        for (int[] row : puzzleBlock) {
            for (int block : row) {
                stringBoard.append(" ");
                stringBoard.append(block);
            }
            stringBoard.append("\n");
        }
        
        return stringBoard.toString();
    }
    
    public static void main(String[] args) { // unit tests (not graded)
        return;
    }
}
