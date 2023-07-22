package deque;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class MaxArrayDeque<T> implements Deque<T>{

    private Object[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private final double threshold = 0.25;
    private final int minArrayLength = 8;
    private Comparator<T> c;

    public MaxArrayDeque() {
        items = new Object[minArrayLength];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    public MaxArrayDeque(int nextFirst, int nextLast) {
        items = new Object[minArrayLength];
        size = 0;
        this.nextFirst = nextFirst;
        this.nextLast = nextLast;
    }

    public MaxArrayDeque(Comparator<T> c) {
        items = new Object[minArrayLength];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        this.c = c;
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

    @Override
    public T getRecursive(int index) {
        return get(index);
    }

    public int getItemsLength() {
        return items.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new MaxArrayDequeIterator();
    }

    private class MaxArrayDequeIterator implements Iterator<T> {
        private int pos;
        private int count;

        public MaxArrayDequeIterator() {
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
        MaxArrayDeque<T> deque = (MaxArrayDeque) other;
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

    public T max() {
        Object max = items[nextFirst + 1];
        for (Object item : items) {
            if (item == null) {
                continue;
            }
            if (this.c.compare((T)max, (T)item) < 0) {
                max = item;
            }
        }
        return (T) max;
    }

    public T max(Comparator<T> c) {
        Object max = items[nextFirst + 1];
        for (Object item : items) {
            if (item == null) {
                continue;
            }
            if (c.compare((T)max, (T)item) < 0) {
                max = item;
            }
        }
        return (T) max;
    }

    public static void main(String[] args) {
//        MaxArrayDeque<String> ad = new MaxArrayDeque<>(String::compareTo);
        MaxArrayDeque<String> ad = new MaxArrayDeque<>(Comparator.comparingInt(String::length));
        ad.addFirst("abbc");
        ad.addFirst("cb");
        ad.addFirst("bbb");
        System.out.println(ad.max());
        System.out.println(ad.toList());
        System.out.println(ad.toList().size());
    }
}
