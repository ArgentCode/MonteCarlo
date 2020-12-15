//imports from the class, can be easily redone with a random number generator and math functions
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/*
    A basic class to calculate some statistice of the Monte Carlo simulator
    The goal is to find the average number of open sites needed to join the top and bottom
        of the 2d array.
    @Author cworman@iastate.edu
 */
public class PercolationStats {

    private int[] results;
    private double[] open;

    /*
    Runs the simulation trials times on an n by n grid.
    @parameter n
        Gives the number of rows and columns
    @parameter trials
        How many trials to run
     */
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        results = new int[trials];
        open = new double[trials];
        for (int i = 0; i < trials; i++) {
            int count = 0;
            Percolation test = new Percolation(n);
            while (!test.percolates()) {


                int row;
                int col;
                // need a loop to ensure random numbers are unique each time
                do {
                    row = StdRandom.uniform(n);
                    col = StdRandom.uniform(n);
                } while (test.isOpen(row, col));
                test.open(row, col);
                count++;
            }
            open[i] = ((double) test.numberOfOpenSites() / (double) (n * n));
            results[i] = count;
        } //end for loop
    }

    /*
    Basic method to aid with trouble shooting.
     */
    private void printResults() {

        for (int i = 0; i < results.length; i++) {
            System.out.print(results[i] + " ");
        }
        System.out.println(" ");
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(open);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(open);
    }


    // test client (see below)
    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(57, 1000); //creates a 5x5 board with 10 trials
        //test.printResults();
        System.out.println("Mean : " + test.mean());
        System.out.println("stv dev: " + test.stddev());


    }

}
