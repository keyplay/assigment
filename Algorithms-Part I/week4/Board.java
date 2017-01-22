public class Board {
    private int[][] puzzleBlock;

    public Board(int[][] blocks) {               // construct a board from an n-by-n array of blocks
        puzzleBlock = new int[blocks.length][];  // (where blocks[i][j] = block in row i, column j)
        for (int i = 0; i < blocks.length; i++) {
            puzzleBlock[i] = blocks[i].clone();
        }
    }
    
    public int dimension() {                // board dimension n
        return puzzleBlock.length;
    }
    
    public int hamming()  {                 // number of blocks out of place
        int valueHam = -1;
        for (int i = 0; i < puzzleBlock.length; i++) {
            for (int j = 0; j < puzzleBlock[i].length; j++) {
                if (puzzleBlock[i][j] != i*puzzleBlock+j+1) {
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
                if (temp != i*puzzleBlock+j+1 && temp != 0) {
                    valueMan += Math.abs(temp / dimension() - i) + Math.abs(temp % dimension() - j);
                }
            }
        }
        
        return valueMan;
    }
    
    public boolean isGoal() {               // is this board the goal board?
        return hamming() == 0;
    }
    
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y)        // does this board equal y?
    public Iterable<Board> neighbors()     // all neighboring boards
    public String toString()               // string representation of this board (in the output format specified below)

    public static void main(String[] args) // unit tests (not graded)
}
