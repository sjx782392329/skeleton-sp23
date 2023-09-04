import edu.princeton.cs.algs4.BST;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{

    private BST<K, V> bst;
    private Set<K> set;

    public BSTMap() {
        bst = new BST();
        set = new HashSet<>();
    }
    @Override
    public void put(K key, V value) {
        bst.put(key, value);
        set.add(key);
    }

    @Override
    public V get(K key) {
        return bst.get(key);
    }

    @Override
    public boolean containsKey(K key) {
        return set.contains(key);
    }

    @Override
    public int size() {
        return bst.size();
    }

    @Override
    public void clear() {
        bst = new BST();
        set = new HashSet<>();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public void printInOrder() {

    }
}
