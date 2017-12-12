import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }
//        String[] strs = StdIn.readAllStrings();
//        StdOut.println(strs.length);

/*
        for (int i = 0; i < k; i++) {
            rq.enqueue(strs[i]);
        }
*/
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}