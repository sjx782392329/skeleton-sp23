package lesson;

public class IntList {
    public int val;
    public IntList next;

    public IntList(int val, IntList next) {
        this.val = val;
        this.next = next;
    }

    public int size() {
        if (this.next == null) {
            return 1;
        }
        return 1 + this.next.size();
    }

    public int get(int i) {
        if (i == 0) {
            return this.val;
        }
        return this.next.get(i - 1);
    }

    public static void main(String[] args) {
        IntList list = new IntList(15, null);
        list = new IntList(10, list);
        list = new IntList(5, list);
        System.out.println(list.size());
        System.out.println("==========");
        System.out.println(list.get(1));
        System.out.println("==========");
        while (list != null) {
            System.out.println(list.val);
            list = list.next;
        }
    }
}
