package hashmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;

    // You should probably define some more!

    private int DEFAULT_INITIAL_CAPACITY = 16;
    private double DEFAULT_LOAD_FACTOR = 0.75;
    private int capacity;
    private double loadFactor;
    private int items;

    /** Constructors */
    public MyHashMap() {
        this.capacity = DEFAULT_INITIAL_CAPACITY;
        this.buckets = new Collection[capacity];
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.items = 0;
    }

    public MyHashMap(int initialCapacity) {
        this.capacity = initialCapacity;
        buckets = new Collection[capacity];
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.items = 0;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.capacity = initialCapacity;
        this.loadFactor = loadFactor;
        buckets = new Collection[initialCapacity];
        this.items = 0;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return null;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    @Override
    public void put(K key, V value) {
        final int index = Math.floorMod(key.hashCode(), capacity);
        Collection<Node> nodes = buckets[index];
        if (nodes == null) {
            nodes = createBucket();
            nodes.add(createNode(key, value));
            items++;
        } else {
            boolean updateFlag = false;
            for (Node node : nodes) {
               // update node not add
               if (node.key.hashCode() == key.hashCode()
                    && (node.key == key || node.key.equals(key))) {
                   node.value = value;
                   updateFlag = true;
                   break;
               }
            }
            if (!updateFlag) {
               items++;
               nodes.add(createNode(key, value));
            }
        }
        buckets[index] = nodes;
        if (1.0 * items / capacity >= loadFactor) {
            resize(capacity << 1);
        }
    }

    public void resize(int capacity) {
        final Collection<Node>[] newBucket = new Collection[capacity];
        for (Collection<Node> nodes : buckets) {
            if (nodes == null) {
                continue;
            }
            for (Node oldNode : nodes) {
                final int index = Math.floorMod(oldNode.key.hashCode(), capacity);
                Collection<Node> newNodes = newBucket[index];
                if (newNodes == null) {
                    newNodes = createBucket();
                }
                newNodes.add(createNode(oldNode.key, oldNode.value));
                newBucket[index] = newNodes;
            }
        }
        buckets = newBucket;
        this.capacity = capacity;
    }

    @Override
    public V get(K key) {
        final int index = Math.floorMod(key.hashCode(), capacity);
        final Collection<Node> nodes = buckets[index];
        if (nodes == null) {
            return null;
        } else {
            for (Node node : nodes) {
                if (node.key.hashCode() == key.hashCode()
                    && ((node.key == key) || node.key.equals(key))) {
                    return node.value;
                }
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        final int index = Math.floorMod(key.hashCode(), capacity);
        final Collection<Node> nodes = buckets[index];
        if (nodes == null) {
            return false;
        } else {
            for (Node node : nodes) {
                if (node.key.hashCode() == key.hashCode()
                        && ((node.key == key) || node.key.equals(key))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int size() {
        return items;
    }

    @Override
    public void clear() {
        buckets = new Collection[this.capacity];
        this.items = 0;
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

}
