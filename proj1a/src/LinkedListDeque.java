import java.util.ArrayList;
import java.util.List;

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
