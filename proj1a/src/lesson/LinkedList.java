package lesson;

public class LinkedList {
    private Node head;

    private static class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public void addLast(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            addLastRecursive(head, newNode);
        }
    }

    private void addLastRecursive(Node current, Node newNode) {
        if (current.next == null) {
            current.next = newNode;
        } else {
            addLastRecursive(current.next, newNode);
        }
    }

    // 其他方法...

    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        System.out.println(list.head.data);

        // 打印链表元素
        Node current = list.head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        // 输出: 1 2 3
    }
}

