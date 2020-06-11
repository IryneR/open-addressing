package org.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class OpenAddressing {
    private static final int INIT_CAPACITY = 4;

    private int numberOfElements;
    private int tableSize; // it needs to be a power of 2
    private Node[] nodes;

    public OpenAddressing() {
        this(INIT_CAPACITY);
    }

    public OpenAddressing(int capacity) {
        if (capacity <= 0){
            tableSize = INIT_CAPACITY;
        }
        else {
            tableSize = nextPowerOf2(capacity);
        }
        numberOfElements = 0;
        nodes = new Node[tableSize];
    }

    // compute power of two greater than or equal to n
    private static int nextPowerOf2(int n)
    {
        // decrement n (to handle cases when n itself is a power of 2)
        n = n - 1;
        // do till only one bit is left
        while ((n & n - 1) != 0) {
            n = n & n - 1;	// unset rightmost bit
        }

        // n is now a power of two (less than n)
        // return next power of 2
        return n << 1;
    }

    public int size() {
        return numberOfElements;
    }

    public int hashFunctionFirst(int key) {
        return (key & 0x7fffffff) % tableSize;
    }

    public int hashFunctionSecond(int key) {
        return 5 - ((key & 0x7fffffff) % 5);
    }

    private void resize(int capacity) {
        OpenAddressing temp = new OpenAddressing(capacity);
        for (int i = 0; i < tableSize; i++) {
            if (nodes[i] != null) {
                temp.put(nodes[i].getKey(), nodes[i].getValue());
            }
        }
        nodes = temp.nodes;
        tableSize = temp.tableSize;
    }

    public void put(int key, long val) {

        // double table size if 50% full
        if (numberOfElements >= tableSize / 2) resize(2 * tableSize);

        int i, j;
        int hashValue = hashFunctionFirst(key); // hash the key
        int stepSize = hashFunctionSecond(key); // get step size

        for (i = hashValue, j=0; nodes[i] != null && j>=0; i = (hashValue + j * stepSize) % tableSize, j++) {
            if (nodes[i].getKey()==(key)) {
                nodes[i].setValue(val);
                return;
            }
        }
        nodes[i] = new Node(key, val);
        numberOfElements++;
    }

     public Node get(int key) {
        int i, j;
        int hashValue = hashFunctionFirst(key); // hash the key
        int stepSize = hashFunctionSecond(key); // get step size

        for (i = hashValue, j=0; nodes[i] != null && j>=0; i = (hashValue + j * stepSize) % tableSize, j++) {
            if (nodes[i].getKey() == key)
                return nodes[i];
        }
           return null;
    }

    public int getCapacity() {
        return tableSize;
    }

    public int getIndexOfKey(int key) {
        int i, j;
        int hashValue = hashFunctionFirst(key); // hash the key
        int stepSize = hashFunctionSecond(key); // get step size

        for (i = hashValue, j=0; nodes[i] != null && j>=0; i = (hashValue + j * stepSize) % tableSize, j++)
            if (nodes[i].getKey() == key)
                return i;
        return -1;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Node {
        private int key;
        private long value;
    }

}