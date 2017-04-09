import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Allows you to model a simple square 2D percolation system.
 *
 * @author Aydin
 */
public class Percolation {

    private final int n;  // Width and height of the grid.
    private final boolean[] openSites;
    private final WeightedQuickUnionUF connectedSitesPerc;  // For percolation testing. Can contain backwash problems.
    private final WeightedQuickUnionUF connectedSitesFull;  // For full testing. Avoids backwash problem.

    /**
     * Initialise n-by-n grid. All real sites begin closed and unconnected.
     *
     * @param n Width/height of the grid.
     */
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException(
                    String.format("%d is less than one. Grid must contain at least one site.", n));
        }

        this.n = n;
        connectedSitesFull = new WeightedQuickUnionUF(n * n + 1);
        connectedSitesPerc = new WeightedQuickUnionUF(n * n + 2);

        openSites = new boolean[n * n + 2];

        // Virtual sites are open always.
        openSites[n * n] = true;
        openSites[n * n + 1] = true;
    }

    /**
     * Translate a site's {row, col} co-ordinates to index value.
     *
     * @param row Row of site of interest ({1,..,n}).
     * @param col Column of site of interest ({1,..,n}).
     */
    private int getSiteIndex(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    /**
     * Get left neighbour's index value.
     *
     * @param row Row of site of interest ({1,..,n}).
     * @param col Column of site of interest ({1,..,n}).
     */
    private int getLeftSiteIndex(int row, int col) {
        return (row - 1) * n + (col - 2);
    }

    /**
     * Get right neighbour's index value.
     *
     * @param row Row of site of interest ({1,..,n}).
     * @param col Column of site of interest ({1,..,n}).
     */
    private int getRightSiteIndex(int row, int col) {
        return (row - 1) * n + col;
    }

    /**
     * Get above neighbour's index value.
     *
     * @param row Row of site of interest ({1,..,n}).
     * @param col Column of site of interest ({1,..,n}).
     */
    private int getAboveSiteIndex(int row, int col) {
        return (row - 2) * n + (col - 1);
    }

    /**
     * Get below neighbour's index value.
     *
     * @param row Row of site of interest ({1,..,n}).
     * @param col Column of site of interest ({1,..,n}).
     */
    private int getBelowSiteIndex(int row, int col) {
        return row * n + (col - 1);
    }

    /**
     * Check if row and column is within grid bounds.
     *
     * @param row Row of site of interest ({1,..,n}).
     * @param col Column of site of interest ({1,..,n}).
     */
    private boolean inBounds(int row, int col) {
        return (row >= 1) && (row <= n) && (col >= 1) && (col <= n);
    }

    /**
     * Open site {row, col} if it is not open already.
     *
     * @param row Row of site to open ({1,..,n}).
     * @param col Column of site to open ({1,..,n}).
     */
    public void open(int row, int col) {
        if (!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(String.format("{row: %d, col: %d} is outside of the grid.", row, col));
        }

        // No need to try open a site that is already open...
        if (isOpen(row, col)) {
            return;
        }

        int siteIndex = getSiteIndex(row, col);
        openSites[siteIndex] = true;

        // Connect any real top row site with top virtual site.
        // Must be considered by itself in the special case that n==1.
        if (row == 1) {
            connectedSitesFull.union(siteIndex, n * n);
            connectedSitesPerc.union(siteIndex, n * n);
        }

        // Connect any real bottom row site with bottom virtual site.
        // Must be considered by itself in the special case that n==1.
        if (row == n) {
            connectedSitesPerc.union(siteIndex, n * n + 1);
        }

        // Connect with open above neighbour.
        if (row > 1 && isOpen(row - 1, col)) {
            connectedSitesFull.union(siteIndex, getAboveSiteIndex(row, col));
            connectedSitesPerc.union(siteIndex, getAboveSiteIndex(row, col));
        }

        // Connect with open below neighbour.
        if (row < n && isOpen(row + 1, col)) {
            connectedSitesFull.union(siteIndex, getBelowSiteIndex(row, col));
            connectedSitesPerc.union(siteIndex, getBelowSiteIndex(row, col));
        }

        // Connect with open left neighbour.
        if (col > 1 && isOpen(row, col - 1)) {
            connectedSitesFull.union(siteIndex, getLeftSiteIndex(row, col));
            connectedSitesPerc.union(siteIndex, getLeftSiteIndex(row, col));
        }

        // Connect with open right neighbour.
        if (col < n && isOpen(row, col + 1)) {
            connectedSitesFull.union(siteIndex, getRightSiteIndex(row, col));
            connectedSitesPerc.union(siteIndex, getRightSiteIndex(row, col));
        }
    }

    /**
     * Is site {row, col} open?
     *
     * @param row Row of site to check ({1,..,n}).
     * @param col Column of site to check ({1,..,n}).
     */
    public boolean isOpen(int row, int col) {
        if (!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(String.format("{row: %d, col: %d} is outside of the grid.", row, col));
        }
        return openSites[getSiteIndex(row, col)];
    }

    /**
     * Is site {row, col} full?
     *
     * @param row Row of site to check ({1,..,n}).
     * @param col Column of site to check ({1,..,n}).
     */
    public boolean isFull(int row, int col) {
        if (!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(String.format("{row: %d, col: %d} is outside of the grid.", row, col));
        }
        return connectedSitesFull.connected(getSiteIndex(row, col), n * n);
    }

    /**
     * Number of open sites.
     */
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < openSites.length - 2; i++) {
            if (openSites[i]) {
                count++;
            }
        }
        return count;
    }

    /**
     * Does the system percolate?
     */
    public boolean percolates() {
        return this.connectedSitesPerc.connected(n * n, n * n + 1);
    }
}
