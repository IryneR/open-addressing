package org.test;

import org.junit.Test;
import org.test.OpenAddressing;

import static org.junit.Assert.*;

public class AppTest 
{
    @Test
    public void putGetSimplePositiveKeyValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(1234567890, 1234567891234567891L);
        Node value = openAddressingHashTable.get(1234567890);
        assertEquals(1234567891234567891L, value.getValue());

    }

    @Test
    public void putGetRewriteTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(1234567890, 1234567891234567891L);
        openAddressingHashTable.put(1234567890, 1000000000000000000L);
        Node value = openAddressingHashTable.get(1234567890);
        assertEquals(1000000000000000000L,value.getValue());
    }

    @Test
    public void putGetSimpleNegativeKeyTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(-1234567890, 1234567891234567891L);
        Node value = openAddressingHashTable.get(-1234567890);
        assertEquals(1234567891234567891L, value.getValue());
    }

    @Test
    public void putGetSimpleNegativeValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(1234567890, -1234567891234567891L);
        Node value = openAddressingHashTable.get(1234567890);
        assertEquals(-1234567891234567891L,value.getValue());
    }

    @Test
    public void putGetSimpleNegativeKeyValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(-1234567890, -1234567891234567891L);
        Node value = openAddressingHashTable.get(-1234567890);
        assertEquals(-1234567891234567891L, value.getValue());
    }

    @Test
    public void putGetSimpleZeroKeyPositiveValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(0, 1234567891234567891L);
        Node value = openAddressingHashTable.get(0);
        assertEquals(1234567891234567891L, value.getValue());
    }

    @Test
    public void putGetSimpleZeroKeyNegativeValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(0, -1234567891234567891L);
        Node value = openAddressingHashTable.get(0);
        assertEquals(-1234567891234567891L,value.getValue());
    }

    @Test
    public void putGetSimpleZeroKeyZeroValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(0, 0L);
        Node value = openAddressingHashTable.get(0);
        assertEquals(0L,value.getValue());
    }

    @Test
    public void putGetSimplePositiveKeyZeroValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(1234567890, 0L);
        Node value = openAddressingHashTable.get(1234567890);
        assertEquals(0L,value.getValue());
    }

    @Test
    public void putGetSimpleNegativeKeyZeroValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(-1234567890, 0L);
        Node value = openAddressingHashTable.get(-1234567890);
        assertEquals(0L,value.getValue());
    }


    @Test
    public void putGetWithCapacityFourTest()
    {

        OpenAddressing openAddressingHashTable = new OpenAddressing(4);

        openAddressingHashTable.put(-1234567890, 0L);
        openAddressingHashTable.put(1234567890, 12345L);

        int capacity = openAddressingHashTable.getCapacity();
        assertEquals(4,capacity);
    }

    @Test
    public void putGetWithCapacityEightTest()
    {

        OpenAddressing openAddressingHashTable = new OpenAddressing(4);

        openAddressingHashTable.put(-1234567890, 0L);
        openAddressingHashTable.put(1234567890, 12345L);
        openAddressingHashTable.put(1000000000, -12345L);

        int capacity = openAddressingHashTable.getCapacity();
        assertEquals(8, capacity);
    }

    @Test
    public void putGetWithCapacityFiveTest()
    {

        OpenAddressing openAddressingHashTable = new OpenAddressing(5);

        openAddressingHashTable.put(-1234567890, 0L);
        openAddressingHashTable.put(1234567890, 12345L);

        int capacity = openAddressingHashTable.getCapacity();
        assertEquals(5,capacity);
    }

    @Test
    public void putGetWithCapacityTenTest()
    {

        OpenAddressing openAddressingHashTable = new OpenAddressing(5);

        openAddressingHashTable.put(-1234567890, 0L);
        openAddressingHashTable.put(1234567890, 12345L);
        openAddressingHashTable.put(1000000000, -12345L);

        int capacity = openAddressingHashTable.getCapacity();
        assertEquals(10,capacity);
    }

    @Test
    public void putKeyToTheNextNodeTest()
    {
        // Try to put 2 different keys with identical hash numbers and check if second key
        //is on the position  (hash number +1)
        OpenAddressing openAddressingHashTable = new OpenAddressing(8);

        openAddressingHashTable.put(12, 1000L);
        openAddressingHashTable.put(28, 1500L);
        int bucketNumber= (28 % 8)+1;

        assertEquals(bucketNumber,openAddressingHashTable.getIndexOfKey(28));
    }

    @Test
    public void putKeyToTheFirstFreeNodeTest()
    {
        // Try to put 2 different keys with identical hash numbers and check if second key
        //is in the first node, if there are no free nodes till the end of the hashtable
        OpenAddressing openAddressingHashTable = new OpenAddressing(8);

        openAddressingHashTable.put(14, 1000L); //node 6
        openAddressingHashTable.put(15, 1000L); //node 7
        openAddressingHashTable.put(22, 1500L);

        assertEquals(0,openAddressingHashTable.getIndexOfKey(22));
    }

    @Test
    public void sizeTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing(10);

        openAddressingHashTable.put(14, 1000L);
        openAddressingHashTable.put(15, 1000L);
        openAddressingHashTable.put(22, 1500L);

        assertEquals(3,openAddressingHashTable.size());
    }

    @Test
    public void getExistedValueFromTheClusterTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing(23);

        openAddressingHashTable.put(28, 1000L); // node 5
        openAddressingHashTable.put(74, 1000L); //node 5 -> node 6
        openAddressingHashTable.put(51, 0L); //node 5 -> node 7
        openAddressingHashTable.put(31, -1500L); //node 8

        Node value = openAddressingHashTable.get(51);
        assertEquals(0L, value.getValue());
        assertEquals(7, openAddressingHashTable.getIndexOfKey(51));
    }

    @Test
    public void getNonExistedValueFromTheClusterTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing(23);

        openAddressingHashTable.put(28, 1000L);
        openAddressingHashTable.put(74, 1000L);
        openAddressingHashTable.put(51, 0L);
        openAddressingHashTable.put(31, -1500L);

        Node value = openAddressingHashTable.get(5);
        assertNull(value);
        assertEquals(-1, openAddressingHashTable.getIndexOfKey(5));
    }

    @Test
    public void getExistedValueShiftedToBeginTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing(8);

        openAddressingHashTable.put(14, 1000L); //node 6
        openAddressingHashTable.put(15, 1000L); //node 7
        openAddressingHashTable.put(0, 1500L); // node 0
        openAddressingHashTable.put(22, -3000L); // node 6 -> node 1

        Node value = openAddressingHashTable.get(22);
        assertEquals(-3000L, value.getValue());
        assertEquals(1, openAddressingHashTable.getIndexOfKey(22));
    }

    @Test
    public void getNonExistedValueShiftedToBeginTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing(8);

        openAddressingHashTable.put(14, 1000L); //node 6
        openAddressingHashTable.put(15, 1000L); //node 7
        openAddressingHashTable.put(0, 1500L); // node 0
        openAddressingHashTable.put(22, -3000L); // node 6 -> node 1

        Node value = openAddressingHashTable.get(30);
        assertNull(value);
        assertEquals(-1, openAddressingHashTable.getIndexOfKey(30));
    }

}
