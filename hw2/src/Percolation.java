import edu.princeton.cs.algs4.WeightedQuickUnionUF;
// TODO: Add any other necessary imports.

public class Percolation {
    // TODO: Add any necessary instance variables.

    private final boolean[][] grids;
    private final WeightedQuickUnionUF wqu;
    private final int width;
    private int openedCount;

    public Percolation(int N) {
        // TODO: Fill in this constructor.
        grids = new boolean[N][N];
        wqu = new WeightedQuickUnionUF(N * N + 2);
        width = N;
        openedCount = 0;
    }

    public int xyTo1D(int r, int c) {
        return r * width + c;
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        validate(row, col);
        if (isOpen(row, col)) {
            return;
        }
        grids[row][col] = true;
        if (row == 0) {
            wqu.union(wqu.count() - 2, xyTo1D(row, col));
        }
        if (row == width - 1) {
            wqu.union(wqu.count() - 1, xyTo1D(row, col));
        }
        unionNeighbor(row, col);
        openedCount++;
    }

    public void unionNeighbor(int row, int col) {
        int index = xyTo1D(row, col);
        // left
        if (col - 1 >= 0 && grids[row][col - 1]) {
            int left = xyTo1D(row, col - 1);
            wqu.union(index, left);
        }
        // right
        if (col + 1 < width && grids[row][col + 1]) {
            int right = xyTo1D(row, col + 1);
            wqu.union(index, right);
        }
        // up
        if (row - 1 >= 0 && grids[row - 1][col]) {
            int up = xyTo1D(row - 1, col);
            wqu.union(index, up);
        }
        // down
        if (row + 1 < width && grids[row + 1][col]) {
            int down = xyTo1D(row + 1, col);
            wqu.union(index, down);
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        validate(row, col);
        return grids[row][col];
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        validate(row, col);
        if (!grids[row][col]) {
            return false;
        }
        return wqu.connected(xyTo1D(row, col), wqu.count() - 2);
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return openedCount;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        return wqu.connected(wqu.count() - 2, wqu.count() - 1);
    }

    public void validate(int row, int col) {
        if (row < 0 || row >= width || col < 0 || col >= width) {
           throw new java.lang.IndexOutOfBoundsException(row + col);
        }
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        System.out.println(percolation.xyTo1D(2, 4));
        percolation.open(3, 4);
        percolation.open(2, 4);

    }
}
