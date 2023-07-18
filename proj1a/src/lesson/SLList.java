package lesson;

public class SLList {
    private IntNode first;
    private int size;
    public static class IntNode {
        public int val;
        public IntNode next;

        public IntNode(int val, IntNode next) {
            this.val = val;
            this.next = next;
        }

    }

    public int size(IntNode p) {
        if (p.next == null) {
            return 1;
        }
        return 1 + size(p.next);
    }


//    public void addLast(int x) {
//        IntNode node = new IntNode(x, null);
//        if (first == null) {
//            first = node;
//        } else {
//            addLast(first, node);
//        }
//    }
//
//    private void addLast(IntNode first, IntNode node) {
//        if (first.next == null) {
//            first.next = node;
//            return;
//        }
//        addLast(first.next, node);
//    }

    public void addLast(int x) {
        if (first == null) {
            first = new IntNode(x, null);
            return;
        }
        IntNode p = first;
        while (p.next != null) {
            p = p.next;
        }
        p.next = new IntNode(x, null);
    }

    public SLList(int x) {
        first = new IntNode(x, null);
    }

    public SLList() {
        first = null;
    }

    public void addFirst(int x) {
        first = new IntNode(x, first);
    }

    public int getFirst() {
        return first.val;
    }

    public static void main(String[] args) {
        SLList list = new SLList();
//        list.addFirst(10);
//        list.addFirst(5);
//        list.addFirst(3);
        list.addLast(15);
        System.out.println(list.size(list.first));
        System.out.println("first: " + list.getFirst());
        while (list.first != null) {
            System.out.println(list.first.val);
            list.first = list.first.next;
        }
    }
}
