import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {

    private final int[][] tiles;
    private final int manhattan;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        tiles = copy(blocks);
        manhattan = calcManhattan();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of blocks out of place
    public int hamming() {

        int hamming = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                }

                int supposedBlock = j + i * tiles[i].length + 1;
                if (tiles[i][j] != supposedBlock) {
                    hamming++;
                }
            }
        }

        return hamming;
    }

    private int calcManhattan() {
        int sum = 0;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                }

                label:
                for (int k = 0; k < tiles.length; k++) {
                    for (int p = 0; p < tiles[k].length; p++) {
                        int supposedBlock = p + k * tiles[k].length + 1;

                        if (tiles[i][j] == supposedBlock) {
                            sum += Math.abs(i - k) + Math.abs(j - p);
                            break label;
                        }
                    }
                }
            }
        }
        return sum;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {

        Board board = new Board(copy(tiles));

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length - 1; j++) {
                if (tiles[i][j] != 0 && tiles[i][j + 1] != 0) {
                    board.swap(i, j, i, j + 1);
                    return board;
                }
            }
        }
        return board;
    }

    private boolean swap(int i, int j, int it, int jt) {
        if (it < 0 || it >= tiles.length || jt < 0 || jt >= tiles.length) {
            return false;
        }
        int temp = tiles[i][j];
        tiles[i][j] = tiles[it][jt];
        tiles[it][jt] = temp;
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        return Arrays.deepEquals(tiles, that.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        Stack<Board> queue = new Stack<>();

        label:
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] == 0) {
                    queue = getNeighbors(i, j);
                    break label;
                }
            }
        }
        return queue;
    }

    private Stack<Board> getNeighbors(int tilei, int tilej) {
        Stack<Board> queue = new Stack<>();

        if (tilei > 0) {
            queue.push(change(tilei, tilej, tilei - 1, tilej));
        }

        if (tilei < tiles.length - 1) {
            queue.push(change(tilei, tilej, tilei + 1, tilej));
        }

        if (tilej > 0) {
            queue.push(change(tilei, tilej, tilei, tilej - 1));
        }

        if (tilej < tiles.length - 1) {
            queue.push(change(tilei, tilej, tilei, tilej + 1));
        }

        return queue;
    }

    private Board change(int zeroI, int zeroJ, int i, int j) {
        int[][] copy = copy(tiles);
        copy[zeroI][zeroJ] = copy[i][j];
        copy[i][j] = 0;
        return new Board(copy);
    }


    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder output = new StringBuilder(tiles.length + "\n");
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                output.append(String.format("%2d ", tiles[i][j]));
                if (j < tiles[i].length - 1) {
                    output.append(" ");
                }
            }
            if (i < tiles.length - 1) {
                output.append("\n");
            }
        }
        return output.toString();
    }

    private int[][] copy(int[][] array) {
        int[][] copy = new int[array.length][array.length];
        for (int i = 0; i < array.length; i++) {
            System.arraycopy(array[i], 0, copy[i], 0, array[i].length);
        }
        return copy;
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println(initial.toString());
        StdOut.println("hamming = " + initial.hamming());
        StdOut.println("manhattan = " + initial.manhattan());

        Board twin = initial.twin();
        StdOut.println(twin.toString());
    }

}
