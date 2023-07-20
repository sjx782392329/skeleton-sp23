import java.util.ArrayList;
import java.util.List;

public class ArrayDeque<T> implements Deque<T>{

    private Object[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private final double threshold = 0.25;
    private final int minArrayLength = 8;

    public ArrayDeque() {
        items = new Object[minArrayLength];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    public ArrayDeque(int nextFirst, int nextLast) {
        items = new Object[minArrayLength];
        size = 0;
        this.nextFirst = nextFirst;
        this.nextLast = nextLast;
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length || (nextFirst == nextLast && size + 1 < items.length)) {
            resize(items.length << 1);
        }
        items[nextFirst] = x;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        size++;
    }

    public void resize(int len) {
        Object[] temp = new Object[len];
        boolean flag = false;
        int in = 0;
        for (int i = 0; i < items.length; i++) {
            int index = (nextFirst + 1 + i) % items.length;
            final Object item = items[index];
            if (item != null) {
                temp[in] = item;
                in++;
            }
            if (index == nextFirst + 1 && flag) {
                break;
            }
            flag = true;
        }
        nextFirst = temp.length - 1;
        items = temp;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length || (nextLast == nextFirst && size + 1 < items.length)) {
            resize(items.length << 1);
            nextLast = size;
        }
        items[nextLast] = x;
        nextLast = (nextLast + 1) % items.length;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> res = new ArrayList<>();
        if (items.length == 0) {
            return res;
        }
        boolean flag = false;
        for (int i = 0; i < items.length; i++) {
            int index = (nextFirst + 1 + i) % items.length;
            final Object item = items[index];
            if (item != null) {
                res.add((T) item);
            }
            if (index == nextFirst + 1 && flag) {
                break;
            }
            flag = true;
        }
        return res;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size <= 0) {
            return null;
        }
        int newNextFirst = (nextFirst + 1) % items.length;
        Object item = items[newNextFirst];
        items[newNextFirst] = null;
        nextFirst = newNextFirst;
        size--;
        resizeDown();
        return (T) item;
    }

    private void resizeDown() {
        double factor = (double) size / items.length;
        // items length < 8 don't resize down
        if (items.length > minArrayLength && factor < threshold) {
            resize(items.length >> 1);
        }
    }

    @Override
    public T removeLast() {
        if (size <= 0) {
            return null;
        }
        int newNextLast = (nextLast - 1 + items.length) % items.length;
        Object item = items[newNextLast];
        items[newNextLast] = null;
        nextLast = newNextLast;
        size--;
        resizeDown();
        return (T) item;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return (T) items[index];
    }

    public int getItemsLength() {
        return items.length;
    }

    public static void main(String[] args) {
        ArrayDeque<String> ad = new ArrayDeque<>();
        System.out.println(ad.nextFirst);
//        ad.addLast("a");
//        ad.addLast("b");
        ad.addFirst("c");
        ad.addLast("d");
        ad.addLast("e");
        ad.addFirst("f");
        ad.addLast("g");
//        ad.addLast("h");
        ad.addFirst("Z");
//        ad.addFirst("y");
//        ad.addFirst("x");
//        ad.addFirst("w");
//        ad.addFirst("v");
//        ad.addFirst("u");
//        ad.addFirst("t");
        System.out.println(ad.toList());
        System.out.println(ad.toList().size());
    }
}
