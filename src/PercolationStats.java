import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] threshold;
    private double T;

    public PercolationStats(int N, int T) {

        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        threshold = new double[T];
        this.T = T;
        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);

            while (!percolation.percolates()) {
                int randx = StdRandom.uniform(1, N + 1);
                int randy = StdRandom.uniform(1, N + 1);
                percolation.open(randx, randy);
            }

            threshold[i] = ((double) percolation.numberOfOpenSites()) / (N * N);
        }
    }

    public double mean() {
        return StdStats.mean(threshold);
    }

    public double stddev() {
        return StdStats.stddev(threshold);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(T);
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(T);
    }

    public static void main(String[] args) {
        int T = Integer.parseInt(args[0]);
        int N = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(T, N);

        StdOut.println("% java PercolationStats " + T + " " + N);
        StdOut.println("mean " + percolationStats.mean());
        StdOut.println("stddev " + percolationStats.stddev());
        StdOut.println("[" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }

}
