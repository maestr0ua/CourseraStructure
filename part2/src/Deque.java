import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newFirst = new Node();
        newFirst.item = item;

        if (first == null) {
            first = newFirst;
            last = first;
        } else {
            Node oldFirst = first;

            first = new Node();
            first.item = item;
            first.next = oldFirst;
            oldFirst.previous = first;
        }
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (last == null) {
            addFirst(item);
        } else {
            Node newLast = new Node();
            newLast.item = item;

            Node oldLast = last;

            last = newLast;
            last.previous = oldLast;
            oldLast.next = last;
            size++;
        }

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = first.item;
        first = first.next;
        size--;
        if (first != null) {
            first.previous = null;
        } else {
            last = null;
        }
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = last.item;
        last = last.previous;
        size--;

        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();

        deque.addFirst("A");
//        deque.addFirst("B");
//        deque.addFirst("B");
//        deque.addFirst("C");
//        deque.addFirst("D");
//        deque.addFirst("E");
//        deque.addFirst("F");

        deque.removeLast();

        for (String aDeque : deque) {
            System.out.print(aDeque + "");
        }

    }
}