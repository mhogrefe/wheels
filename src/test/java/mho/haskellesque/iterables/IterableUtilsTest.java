package mho.haskellesque.iterables;

import mho.haskellesque.ordering.NullHandlingComparator;
import org.junit.Test;

import java.util.*;

import static mho.haskellesque.iterables.IterableUtils.*;
import static org.junit.Assert.*;

public class IterableUtilsTest {
    @Test
    public void testAddTo_Iterable_Collection() {
        Set<Integer> set = new LinkedHashSet<>();
        set.addAll(Arrays.asList(4, 1, 5, 9, 2));
        ArrayList<Integer> iArrayList = new ArrayList<>();
        addTo(set, iArrayList);
        assertEquals(iArrayList.size(), 5);
        assertEquals(iArrayList.get(0), Integer.valueOf(4));
        assertEquals(iArrayList.get(1), Integer.valueOf(1));
        assertEquals(iArrayList.get(2), Integer.valueOf(5));
        assertEquals(iArrayList.get(3), Integer.valueOf(9));
        assertEquals(iArrayList.get(4), Integer.valueOf(2));
        LinkedList<Integer> iLinkedList = new LinkedList<>();
        addTo(set, iLinkedList);
        assertEquals(iLinkedList.size(), 5);
        assertEquals(iLinkedList.get(0), Integer.valueOf(4));
        assertEquals(iLinkedList.get(1), Integer.valueOf(1));
        assertEquals(iLinkedList.get(2), Integer.valueOf(5));
        assertEquals(iLinkedList.get(3), Integer.valueOf(9));
        assertEquals(iLinkedList.get(4), Integer.valueOf(2));
        HashSet<Integer> iHashSet = new HashSet<>();
        addTo(set, iHashSet);
        assertEquals(iHashSet.size(), 5);
        assertTrue(iHashSet.contains(4));
        assertTrue(iHashSet.contains(1));
        assertTrue(iHashSet.contains(5));
        assertTrue(iHashSet.contains(9));
        assertTrue(iHashSet.contains(2));
        TreeSet<Integer> iTreeSet = new TreeSet<>();
        addTo(set, iTreeSet);
        assertEquals(iTreeSet.size(), 5);
        assertTrue(iTreeSet.contains(4));
        assertTrue(iTreeSet.contains(1));
        assertTrue(iTreeSet.contains(5));
        assertTrue(iTreeSet.contains(9));
        assertTrue(iTreeSet.contains(2));
        LinkedHashSet<Integer> iLinkedHashSet = new LinkedHashSet<>();
        addTo(set, iLinkedHashSet);
        assertEquals(iLinkedHashSet.size(), 5);
        assertTrue(iLinkedHashSet.contains(4));
        assertTrue(iLinkedHashSet.contains(1));
        assertTrue(iLinkedHashSet.contains(5));
        assertTrue(iLinkedHashSet.contains(9));
        assertTrue(iLinkedHashSet.contains(2));

        set = new HashSet<>();
        iArrayList = new ArrayList<>();
        addTo(set, iArrayList);
        assertTrue(iArrayList.isEmpty());
        iLinkedList = new LinkedList<>();
        assertTrue(iLinkedList.isEmpty());
        iHashSet = new HashSet<>();
        assertTrue(iHashSet.isEmpty());
        iTreeSet = new TreeSet<>();
        assertTrue(iTreeSet.isEmpty());
        iLinkedHashSet = new LinkedHashSet<>();
        assertTrue(iLinkedHashSet.isEmpty());

        LinkedList<Float> lList = new LinkedList<>();
        lList.add(0.2f);
        lList.add(-5f);
        lList.add(null);
        lList.add(1e30f);
        ArrayList<Float> fArrayList = new ArrayList<>();
        addTo(lList, fArrayList);
        assertEquals(fArrayList.size(), 4);
        assertEquals(fArrayList.get(0), Float.valueOf(0.2f));
        assertEquals(fArrayList.get(1), Float.valueOf(-5f));
        assertNull(fArrayList.get(2));
        assertEquals(fArrayList.get(3), Float.valueOf(1e30f));
        LinkedList<Float> fLinkedList = new LinkedList<>();
        addTo(lList, fLinkedList);
        assertEquals(fLinkedList.size(), 4);
        assertEquals(fLinkedList.get(0), Float.valueOf(0.2f));
        assertEquals(fLinkedList.get(1), Float.valueOf(-5f));
        assertNull(fLinkedList.get(2));
        assertEquals(fLinkedList.get(3), Float.valueOf(1e30f));
        HashSet<Float> fHashSet = new HashSet<>();
        addTo(lList, fHashSet);
        assertEquals(fHashSet.size(), 4);
        assertTrue(fHashSet.contains(Float.valueOf(0.2f)));
        assertTrue(fHashSet.contains(Float.valueOf(-5f)));
        assertTrue(fHashSet.contains(null));
        assertTrue(fHashSet.contains(Float.valueOf(1e30f)));
        TreeSet<Float> fTreeSet = new TreeSet<>(new NullHandlingComparator<Float>());
        addTo(lList, fTreeSet);
        assertEquals(fTreeSet.size(), 4);
        assertTrue(fTreeSet.contains(Float.valueOf(0.2f)));
        assertTrue(fTreeSet.contains(Float.valueOf(-5f)));
        assertTrue(fTreeSet.contains(null));
        assertTrue(fTreeSet.contains(Float.valueOf(1e30f)));
        LinkedHashSet<Float> fLinkedHashSet = new LinkedHashSet<>();
        addTo(lList, fLinkedHashSet);
        assertEquals(fLinkedHashSet.size(), 4);
        assertTrue(fLinkedHashSet.contains(Float.valueOf(0.2f)));
        assertTrue(fLinkedHashSet.contains(Float.valueOf(-5f)));
        assertTrue(fLinkedHashSet.contains(null));
        assertTrue(fLinkedHashSet.contains(Float.valueOf(1e30f)));
    }

    @Test
    public void testToList_Iterable() {
        Set<Integer> set = new LinkedHashSet<>();
        set.addAll(Arrays.asList(4, 1, 5, 9, 2));
        List<Integer> iList = toList(set);
        assertEquals(iList.size(), 5);
        assertEquals(iList.get(0), Integer.valueOf(4));
        assertEquals(iList.get(1), Integer.valueOf(1));
        assertEquals(iList.get(2), Integer.valueOf(5));
        assertEquals(iList.get(3), Integer.valueOf(9));
        assertEquals(iList.get(4), Integer.valueOf(2));

        set = new HashSet<>();
        iList = toList(set);
        assertTrue(iList.isEmpty());

        LinkedList<Float> lList = new LinkedList<>();
        lList.add(0.2f);
        lList.add(-5f);
        lList.add(null);
        lList.add(1e30f);
        List<Float> fList = toList(lList);
        assertEquals(fList.size(), 4);
        assertEquals(fList.get(0), Float.valueOf(0.2f));
        assertEquals(fList.get(1), Float.valueOf(-5f));
        assertNull(fList.get(2));
        assertEquals(fList.get(3), Float.valueOf(1e30f));
    }
}
