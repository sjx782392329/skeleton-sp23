package deque;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ArrayDeque<T> implements Deque<T>{

    public Object[] getItems() {
        return items;
    }

    public Object[] items;
    private int size;

    public int getNextFirst() {
        return nextFirst;
    }

    public void setNextFirst(int nextFirst) {
        this.nextFirst = nextFirst;
    }

    private int nextFirst = 4;
    private int nextLast = 5;
    private final double threshold = 0.25;
    private final int minArrayLength = 8;

    public ArrayDeque() {
        items = new Object[minArrayLength];
        size = 0;
    }

    public ArrayDeque(int nextFirst, int nextLast) {
        items = new Object[minArrayLength];
        size = 0;
        this.nextFirst = nextFirst;
        this.nextLast = nextLast;
    }

    public ArrayDeque(int minArrayLength) {
        items = new Object[minArrayLength];
        size = 0;
        this.nextFirst = minArrayLength - 1;
        this.nextLast = 0;
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length || (nextFirst != 0 && nextFirst == nextLast && size + 1 < items.length)) {
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
//        items[newNextFirst] = null;
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
        if (index > items.length|| index < 0) {
            return null;
        }
        return (T) items[index];
    }

    @Override
    public T getRecursive(int index) {
        return get(index);
    }

    public int getItemsLength() {
        return items.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int pos;
        private int count;

        public ArrayDequeIterator() {
            pos = 0;
            count = 0;
        }
        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public T next() {
            int index = pos;
            T returnItem = (T) items[index];
            while (returnItem == null) {
                index++;
                returnItem = (T) items[index];
            }
            pos = index + 1;
            count++;
            return returnItem;
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
        ArrayDeque<T> deque = (ArrayDeque) other;
        return size == deque.size && Arrays.equals(items, this.items);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(items);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int length = size;
        int i = 0;
        for (Object item : items) {
            if (item == null) {
                continue;
            }
            sb.append(item);
            i++;
            if (i == length) {
                return sb.append("]").toString();
            }
            sb.append(", ");
        }
        return sb.append("]").toString();
    }

    public void clear(int minArrayLength) {
        items = new Object[minArrayLength];
        size = 0;
        this.nextFirst = minArrayLength - 1;
        this.nextLast = 0;
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

