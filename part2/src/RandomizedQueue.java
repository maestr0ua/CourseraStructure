import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
    }

    private void resize(int capacity, int removed) {
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            if (removed >= 0 && i >= removed) {
                copy[i] = s[i + 1];
            } else {
                copy[i] = s[i];
            }
        }
        s = copy;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (size == s.length) {
            resize(s.length * 2, -1);
        }
        s[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        int randi = StdRandom.uniform(0, size);
        Item item = s[randi];
        size--;
        if (size > 0 && size == s.length / 4) {
            resize(s.length / 2, randi);
        } else {
            for (int i = randi; i < size; i++) {
                s[i] = s[i + 1];
            }
            s[size] = null;
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        return s[StdRandom.uniform(0, size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {

        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {

        private int [] arr;
        private int sizeIterator = size;

        RandomIterator() {
            arr = new int[sizeIterator];
            for (int i = 0; i < sizeIterator; i++) {
                arr[i] = i;
            }
        }

        @Override
        public boolean hasNext() {
            return sizeIterator > 0;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            int randi = StdRandom.uniform(0, sizeIterator);
            int si = arr[randi];
            for (int i = randi; i < sizeIterator - 1; i++) {
                arr[i] = arr[i + 1];
            }
            arr[--sizeIterator] = -1;
            return s[si];
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {

        RandomizedQueue<String> queue = new RandomizedQueue<>();
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        queue.enqueue("D");
        queue.enqueue("E");
        queue.enqueue("F");
        queue.enqueue("G");

        for (String string : queue) {
            System.out.print(string + " ");
        }
/*
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
*/
    }
}