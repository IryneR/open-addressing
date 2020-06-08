package org.test;

public class OpenAddressing {
    private static final int INIT_CAPACITY = 4;

    private int n;           // number of key-value pairs
    private int m;           // size of the  table
    private Node[] nodes;

    public OpenAddressing() {
        this(INIT_CAPACITY);
    }

    public OpenAddressing(int capacity) {
        m = capacity;
        n = 0;
        nodes = new Node[m];
    }

    public int size() {
        return n;
    }

    private int hash(int key) {
        return Math.abs(key) % m;
    }

    private void resize(int capacity) {
        OpenAddressing temp = new OpenAddressing(capacity);
        for (int i = 0; i < m; i++) {
            if (nodes[i] != null) {
                temp.put(nodes[i].getKey(), nodes[i].getValue());
            }
        }
        nodes = temp.nodes;
        m = temp.m;
    }

    public void put(int key, long val) {

        // double table size if 50% full
        if (n >= m / 2) resize(2 * m);

        int i;
        for (i = hash(key); nodes[i] != null; i = (i + 1) % m) {
            if (nodes[i].getKey()==(key)) {
                nodes[i].setValue(val);
                return;
            }
        }
        nodes[i] = new Node(key, val);
        n++;
    }

    public Node get(int key) {
        for (int i = hash(key); nodes[i] != null; i = (i + 1) % m){
            if (nodes[i].getKey() == key)
                return nodes[i];
        }
           return null;
    }

    public int getCapacity() {
        return m;
    }

    public int getIndexOfKey(int key) {
                for (int i = hash(key); nodes[i] != null; i = (i + 1) % m)
            if (nodes[i].getKey() == key)
                return i;
        return -1;
    }
}