import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Helps find the percolation threshold using Monte Carlo simulation.
 *
 * @author Aydin
 */
public class PercolationStats {
    private int n;  // Width/height of the grids.
    private int trials;  // Number of percolation experiments.
    private double[] percThresholds;  // List of all percolation thresholds found from experiments.

    /**
     * Perform a specified number independent experiments on an n-by-n grid.
     *
     * @param n      Width/height of percolation grids.
     * @param trials Number of experiments to perform.
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.trials = trials;
        percThresholds = new double[trials];
        runExperiments();
    }

    /**
     * Runs percolation experiments.
     */
    private void runExperiments() {
        for (int t = 0; t < trials; t++) {
            Percolation perc = new Percolation(n);
            do {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                perc.open(row, col);
            } while (!perc.percolates());
            this.percThresholds[t] = (double) perc.numberOfOpenSites() / (double) (n * n);
        }
    }

    /**
     * Low endpoint of 95% confidence interval.
     */
    public double confidenceLo() {
        return mean() - delX();
    }

    /**
     * High endpoint of 95% confidence interval.
     */
    public double confidenceHi() {
        return mean() + delX();
    }

    /**
     * Helps calculate 95% confidence interval.
     */
    private double delX() {
        return (1.96 * stddev()) / Math.sqrt(trials);
    }

    /**
     * Sample mean of percolation threshold.
     */
    public double mean() {
        return StdStats.mean(percThresholds);
    }

    /**
     * Sample standard deviation of percolation threshold.
     */
    public double stddev() {
        return StdStats.stddev(percThresholds);
    }

    /**
     * Test client.
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percStats = new PercolationStats(n, trials);
        StdOut.printf("mean                    = %f\n", percStats.mean());
        StdOut.printf("stddev                  = %f\n", percStats.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n", percStats.confidenceLo(), percStats.confidenceHi());
    }
}
