
/*
    Creates a 2d array representing a grid, with the goal of 'opening' spots on the grid
    until there is a connnected open top and bottom grid square.
    @Author cworman@iastate.edu
 */
public class Percolation {

    //creating the 2-D array and needed variables
    private int open;
    private int closed;
    private int[][] board;
    private int id[];

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        //set the whole board closed.
        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = 0;
            }
        }

        //set some bonus parameters
        closed = n * n;
        open = 0;

        //set up the IDs
        id = new int[closed];
        for (int i = 0; i < closed; i++) {
            id[i] = i;
        }


    }

    //this is the key...
    private void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid;
            }
        }
    }

    /*
        method to aid the open method.
        this method takes a newly opened square, and connects it to any other open squares in range
        @parameter row
        @parameter col
     */
    private void checkConnections(int row, int col) {
        int n = board.length;
        int currentId = (row * n) + col;

        if (col - 1 >= 0) {
            if (isOpen(row, col - 1)) {
                int followingId = (row * n) + (col - 1);
                union(currentId, followingId);

                //union between current position and the one above it
            }
        }
        if (col + 1 <= board.length - 1) {
            if (isOpen(row, col + 1)) {
                int followingId = (row * n) + (col + 1);
                union(currentId, followingId);
                //union between current position and the one above it
            }
        }
        if (row + 1 <= board.length - 1) {
            if (isOpen(row + 1, col)) {
                int followingId = ((row + 1) * n) + (col);
                union(currentId, followingId);
                //union between current position and the one above it
            }
        }
        if (row - 1 >= 0) {
            if (isOpen(row - 1, col)) {
                int followingId = ((row - 1) * n) + (col);
                union(currentId, followingId);
                //union between current position and the one above it
            }
        }
    }

    /*
        Opens a particular cell on the board and unions in with adjacent cells
        @parameter row
        @parameter col
     */
    public void open(int row, int col) {

        if (row <= 0 || row >= board.length) {
            throw new IllegalArgumentException();
        }
        if (col <= 0 || col >= board.length) {
            throw new IllegalArgumentException();
        }

        board[row][col] = 1;
        checkConnections(row, col);
        open++;
        closed--;
    }

    /*
    checks if the given cell is open or closed.
        @parameter row
        @parameter col
     */
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row >= board.length) {
            throw new IllegalArgumentException();
        }
        if (col <= 0 || col >= board.length) {
            throw new IllegalArgumentException();
        }
        return (board[row][col] == 1);
    }


    // returns the number of open sites
    public int numberOfOpenSites() {
        return open;
    }

    /*
    Method to check if two different cells are connected
    This is seen via their IDs being the same
    @parameter p
    @parameter q
     */
    private boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    /*
       checks if any given cell on the top row is connected with any given cell
       on the bottom row
     */
    public boolean percolates() {
        int bottomRow = board.length * (board.length - 1);
        int bottomRowLength = (board.length * board.length) - 1;

        for (int i = 0; i < board.length; i++) {
            for (int j = bottomRow; j < bottomRowLength; j++) {
                if (connected(id[i], id[j])) {
                    return true;
                } //else nothing
            }
        }

        return false;
    }

    /*
    Helper method to print the board, mostly for trouble shooting and visual aid.
     */
    private void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println("");
        }

        for (int i = 0; i < id.length; i++) {
            System.out.print(id[i] + " ");
        }

        System.out.println("");
    }

//     test client (optional)
//    public static void main(String[] args) {
//        Scanner userInput = new Scanner(System.in);
//        int n = 2;
//        Percolation test = new Percolation(n);
//        int input = 1;
//        while(!test.percolates()) {
//            Random rand = new Random();
//            int row = rand.nextInt(n);
//            int col = rand.nextInt(n);
//            test.open(row, col);
//            test.printBoard();
//
//        }
//        System.out.println("Finished Successfully");
//
//    }
}