import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import static java.lang.System.exit;

/**
 * Client that uses RandomizedQueue to print out a permutation of length k using a standard input String containing n
 * items. Note that the argument k must satisfy 0<=k<=n
 *
 * @author Aydin
 */
public class Permutation {
    public static void main(String[] args) {
        if (args.length != 1) {
            StdOut.println("Typical Usage: java Permutation [k] < [string.txt]");
            StdOut.println("Require ONE integer argument k such that 0<=k<=n.");
            StdOut.println("Note, n is the number of string items given in the standard input (string.txt).");
            exit(1);
        }

        RandomizedQueue<String> randQueue = new RandomizedQueue<String>();
        String[] inputStrItems = StdIn.readAllStrings();
        int k;
        int n;
        n = inputStrItems.length;
        try {
            k = Integer.parseInt(args[0]);  // Must be in try block in case args[0] is not a passable integer.
            if ((k < 0) || (k > n)) {  // Not 0<=k<=n?
                throw new IllegalArgumentException();
            }
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("\nGiven argument \"" + args[0] + "\" is invalid." +
                    " Argument k must be an integer such that 0<=k<=n."
            );
        }

        // Fill randomized queue with all items.
        for (String item: inputStrItems) {
            randQueue.enqueue(item);
        }

        // Empty out all items and print.
        for (int i = 0; i < k; i++) {
            StdOut.println(randQueue.dequeue());
        }
    }
}
