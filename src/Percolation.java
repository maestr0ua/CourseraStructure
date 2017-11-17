import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int sites[][];
    private int openSites = 0;
    private WeightedQuickUnionUF uf;
    private int size;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        sites = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sites[i][j] = 0;
            }
        }

        size = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
    }

    public void open(int row, int col) {
        if (row > size || row < 1 || col < 1 || col > size) {
            throw new IllegalArgumentException();
        }

        if (!isOpen(row, col)) {
            openSites++;
            sites[row - 1][col - 1] = 1;
            unionWith(row, col);
        }
    }

    private int translateIndex(int row, int col) {
        return col + (row - 1) * size;
    }

    private void unionWith(int row, int col) {
        if (row == 1) {
            uf.union(translateIndex(row, col), 0);
        }

        if (row == size) {
            uf.union(translateIndex(row, col), size * size + 1);
        }

        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(translateIndex(row - 1, col), translateIndex(row, col));
        }
        if (row < size && isOpen(row + 1, col)) {
            uf.union(translateIndex(row + 1, col), translateIndex(row, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(translateIndex(row, col - 1), translateIndex(row, col));
        }
        if (col < size && isOpen(row, col + 1)) {
            uf.union(translateIndex(row, col + 1), translateIndex(row, col));
        }
    }

    public boolean isOpen(int row, int col) {

        if (row > size || row < 1 || col < 1 || col > size) {
            throw new IllegalArgumentException();
        }

        return sites[row-1][col-1] == 1;
    }

    public boolean isFull(int row, int col) {
        if (row > size || row < 1 || col < 1 || col > size) {
            throw new IllegalArgumentException();
        }

        return uf.connected(0, translateIndex(row, col));
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return uf.connected(0, size * size + 1);
    }

    public static void main(String[] args) {
/*
        String filePath = args[0];
        if (filePath == null) {
            throw new IllegalArgumentException("Specify a file name");
        }

        System.out.println("file = " + args[0]);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF8"))) {
            String line = br.readLine();
            if (line != null && !line.isEmpty()) {
                Percolation percolation = new Percolation(Integer.parseInt(line));
                System.out.println("size = " + line);

                while ((line = br.readLine()) != null) {
                    if (line.length() > 2) {
                        String REGEX = "\\s+";
                        String[] coord = line.trim().split(REGEX);
                        int row = Integer.parseInt(coord[0].trim());
                        int col = Integer.parseInt(coord[1].trim());
                        percolation.open(row, col);
                    }
                }
                System.out.println("open sites = " + percolation.openSites);
                System.out.println("percolates = " + percolation.percolates());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
    }

}
