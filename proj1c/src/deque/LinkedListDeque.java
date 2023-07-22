package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class LinkedListDeque<T> implements Deque<T>{

    private final Node first;
    private final Node last;
    private int size;

    private class Node {
        public Node prev;
        public T value;
        public Node next;

        public Node(Node prev, T value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    public LinkedListDeque() {
        Node nodeFirst = new Node(null, null, null);
        Node nodeLast = new Node(null, null, null);
        nodeFirst.next = nodeLast;
        nodeLast.prev = nodeFirst;
        first = nodeFirst;
        last = nodeLast;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        size++;
        Node node = new Node(null, x, null);
        Node reversed = first.next;
        first.next = node;
        node.next = reversed;
        reversed.prev = node;
        node.prev = first;
    }

    @Override
    public void addLast(T x) {
        size++;
        Node node = new Node(null, x, null);
        Node reversed = last.prev;
        reversed.next = node;
        node.next = last;
        last.prev = node;
        node.prev = reversed;
    }

    @Override
    public List<T> toList() {
        List<T> res = new ArrayList<>();
        if (first.next == last) {
            return res;
        }
        Node node = first.next;
        while (node != last) {
            res.add(node.value);
            node = node.next;
        }
        return res;
    }

    @Override
    public boolean isEmpty() {
        return first.next == last && last.prev == first;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size() == 0) {
            return null;
        }
        Node deleted = first.next;
        first.next = deleted.next;
        deleted.next.prev = first;
        return deleted.value;
    }

    @Override
    public T removeLast() {
        if (size() == 0) {
            return null;
        }
        Node deleted = last.prev;
        deleted.prev.next = last;
        last.prev = deleted.prev;
        return deleted.value;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        Node node = first.next;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.value;
    }

    @Override
    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        Node current = first.next;
        return helper(current, index);
    }

    public T helper(Node current, int index) {
        if (index == 0) {
            return current.value;
        }
        index--;
        return helper(current.next, index);
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node current;

        public LinkedListDequeIterator() {
            current = first.next;
        }
        @Override
        public boolean hasNext() {
            return current.value != null;
        }

        @Override
        public T next() {
            T value = current.value;
            current = current.next;
            return value;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        LinkedListDeque<T> deque = (LinkedListDeque)other;
        if (this.size != deque.size) {
            return false;
        }
        Node currentThis = this.first;
        Node currentOther = deque.first;
        while (currentThis != null && currentOther != null) {
            if (currentThis.value != currentOther.value) {
                return false;
            }
            currentThis = currentThis.next;
            currentOther = currentOther.next;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, last, size);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int length = size;
        int i = 0;
        Node current = this.first.next;
        while (current.value != null) {
            sb.append(current.value);
            i++;
            if (i == length) {
                return sb.append("]").toString();
            }
            sb.append(", ");
            current = current.next;
        }
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new LinkedListDeque<>();
        deque.addFirst(5);
        deque.addLast(10);
        deque.addLast(15);
        System.out.println(deque.toList());
        System.out.println(deque.get(0));
        System.out.println(deque.get(1));
        System.out.println(deque.get(2));
        System.out.println(deque.getRecursive(0));
        System.out.println(deque.getRecursive(1));
        System.out.println(deque.getRecursive(2));
        deque.removeFirst();
        deque.removeLast();
        System.out.println(deque.toList());
    }


}

