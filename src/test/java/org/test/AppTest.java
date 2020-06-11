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
        OpenAddressing.Node value = openAddressingHashTable.get(1234567890);
        assertEquals(1234567891234567891L, value.getValue());

    }

    @Test
    public void putGetRewriteTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(1234567890, 1234567891234567891L);
        openAddressingHashTable.put(1234567890, 1000000000000000000L);
        OpenAddressing.Node value = openAddressingHashTable.get(1234567890);
        assertEquals(1000000000000000000L,value.getValue());
    }

    @Test
    public void putGetSimpleNegativeKeyTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(-1234567890, 1234567891234567891L);
        OpenAddressing.Node value = openAddressingHashTable.get(-1234567890);
        assertEquals(1234567891234567891L, value.getValue());
    }

    @Test
    public void putGetSimpleNegativeValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(1234567890, -1234567891234567891L);
        OpenAddressing.Node value = openAddressingHashTable.get(1234567890);
        assertEquals(-1234567891234567891L,value.getValue());
    }

    @Test
    public void putGetSimpleNegativeKeyValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(-1234567890, -1234567891234567891L);
        OpenAddressing.Node value = openAddressingHashTable.get(-1234567890);
        assertEquals(-1234567891234567891L, value.getValue());
    }

    @Test
    public void putGetSimpleZeroKeyPositiveValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(0, 1234567891234567891L);
        OpenAddressing.Node value = openAddressingHashTable.get(0);
        assertEquals(1234567891234567891L, value.getValue());
    }

    @Test
    public void putGetSimpleZeroKeyNegativeValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(0, -1234567891234567891L);
        OpenAddressing.Node value = openAddressingHashTable.get(0);
        assertEquals(-1234567891234567891L,value.getValue());
    }

    @Test
    public void putGetSimpleZeroKeyZeroValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(0, 0L);
        OpenAddressing.Node value = openAddressingHashTable.get(0);
        assertEquals(0L,value.getValue());
    }

    @Test
    public void putGetSimplePositiveKeyZeroValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(1234567890, 0L);
        OpenAddressing.Node value = openAddressingHashTable.get(1234567890);
        assertEquals(0L,value.getValue());
    }

    @Test
    public void putGetSimpleNegativeKeyZeroValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(-1234567890, 0L);
        OpenAddressing.Node value = openAddressingHashTable.get(-1234567890);
        assertEquals(0L,value.getValue());
    }

    @Test
    public void putGetSimpleMinIntKeyPositiveValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(Integer.MIN_VALUE, 1234567890L);
        OpenAddressing.Node value = openAddressingHashTable.get(Integer.MIN_VALUE);
        assertEquals(1234567890L,value.getValue());
    }

    @Test
    public void putGetSimpleMaxIntKeyPositiveValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing();

        openAddressingHashTable.put(Integer.MAX_VALUE, 1234567890L);
        OpenAddressing.Node value = openAddressingHashTable.get(Integer.MAX_VALUE);
        assertEquals(1234567890L,value.getValue());
    }

    @Test
    public void checkPowerOfTwoPositiveTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing(5);

        openAddressingHashTable.put(Integer.MAX_VALUE, 1234567890L);
        OpenAddressing.Node value = openAddressingHashTable.get(Integer.MAX_VALUE);
        assertEquals(1234567890L,value.getValue());
        assertEquals(8, openAddressingHashTable.getCapacity());
    }

    @Test
    public void checkPowerOfTwoNegativeTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing(-2);

        openAddressingHashTable.put(Integer.MAX_VALUE, 1234567890L);
        OpenAddressing.Node value = openAddressingHashTable.get(Integer.MAX_VALUE);
        assertEquals(1234567890L,value.getValue());
        assertEquals(4, openAddressingHashTable.getCapacity());
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
    public void putFirstProbingTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing(8);

        openAddressingHashTable.put(28, 1500L);
        int bucketNumber= (28 % 8);

        assertEquals(bucketNumber,openAddressingHashTable.getIndexOfKey(28));
    }

    @Test
    public void putKeyToTheNextNodeTest()
    {
        // Try to put 2 different keys with identical hash numbers and check if second key
        //is on the position  (hash1 +1*hash2)%m
        OpenAddressing openAddressingHashTable = new OpenAddressing(8);

        openAddressingHashTable.put(12, 1000L); //node 4
        openAddressingHashTable.put(28, 1500L);// node 4 -> node 6
        int bucketNumber= (28 % 8)+1*(5-(28 % 5)) % 8;

        OpenAddressing.Node value1 = openAddressingHashTable.get(12);
        OpenAddressing.Node value2 = openAddressingHashTable.get(28);

        assertEquals(1000L, value1.getValue());
        assertEquals(1500L, value2.getValue());
        assertEquals(bucketNumber,openAddressingHashTable.getIndexOfKey(28));
    }

    @Test
    public void putKeySecondProbingTest()
    {
        // Try to put 2 different keys with identical hash numbers and check if second key
        //is on the position  (hash1 +2*hash2)%m
        OpenAddressing openAddressingHashTable = new OpenAddressing(16);

        openAddressingHashTable.put(14, 1000L); // node 14
        openAddressingHashTable.put(3, 1500L); //node 3
        openAddressingHashTable.put(30, -1500L); //node 14 -> node 8

        int bucketNumber = ((30 % 16)+2*(5-(30 % 5))) % 16; // node 8

        OpenAddressing.Node value = openAddressingHashTable.get(30);

        assertEquals(-1500L, value.getValue());
        assertEquals(bucketNumber,openAddressingHashTable.getIndexOfKey(30));

    }

    @Test
    public void putKeyToTheFirstFreeNodeTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing(8);

        openAddressingHashTable.put(6, 1000L); //node 6
        openAddressingHashTable.put(3, 1500L); //node 3
        openAddressingHashTable.put(30, -1500L); //node 6 - > node 0

        int bucketNumber = ((30 % 8)+2*(5-(30 % 5))) % 8; // node 0

        OpenAddressing.Node value = openAddressingHashTable.get(30);

        assertEquals(-1500L, value.getValue());
        assertEquals(0,openAddressingHashTable.getIndexOfKey(30));
    }

    @Test
    public void putKeySecondProbingRewriteValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing(8);

        openAddressingHashTable.put(6, 1000L); //node 6
        openAddressingHashTable.put(3, 1500L); //node 3
        openAddressingHashTable.put(30, -1500L); //node 6 - > node 0

        openAddressingHashTable.put(30, 1500L); //node 6 - > node 0

        int bucketNumber = ((30 % 8)+2*(5-(30 % 5))) % 8; // node 0

        OpenAddressing.Node value = openAddressingHashTable.get(30);

        assertEquals(1500L, value.getValue());
        assertEquals(0, openAddressingHashTable.getIndexOfKey(30));
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
    public void getExistedValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing(32);

        openAddressingHashTable.put(53, 1000L); // node 21
        openAddressingHashTable.put(25, 1000L); //node 25
        openAddressingHashTable.put(29, 0L); //node 29
        openAddressingHashTable.put(1, 0L); //node 1

        openAddressingHashTable.put(21, -1500L); //node 21 -> node 5

        int bucketNumber = ((21 % 32)+4*(5-(21 % 5))) % 32; //node 5

        OpenAddressing.Node value = openAddressingHashTable.get(21);
        assertEquals(-1500L, value.getValue());
        assertEquals(bucketNumber, openAddressingHashTable.getIndexOfKey(21));
    }

    @Test
    public void getNonExistedValueTest()
    {
        OpenAddressing openAddressingHashTable = new OpenAddressing(23);


        openAddressingHashTable.put(53, 1000L); // node 21
        openAddressingHashTable.put(25, 1000L); //node 25
        openAddressingHashTable.put(29, 0L); //node 29
        openAddressingHashTable.put(1, 0L); //node 1

        OpenAddressing.Node value = openAddressingHashTable.get(21);
        assertNull(value);
        assertEquals(-1, openAddressingHashTable.getIndexOfKey(21));
    }


}
