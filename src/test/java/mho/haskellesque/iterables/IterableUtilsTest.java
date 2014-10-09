package mho.haskellesque.iterables;

import mho.haskellesque.ordering.NullHandlingComparator;
import org.junit.Test;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;

import static mho.haskellesque.iterables.IterableUtils.*;
import static mho.haskellesque.iterables.IterableUtils.take;
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
        Iterator<Integer> iLinkedHashSetIterator = iLinkedHashSet.iterator();
        assertEquals(iLinkedHashSetIterator.next(), Integer.valueOf(4));
        assertEquals(iLinkedHashSetIterator.next(), Integer.valueOf(1));
        assertEquals(iLinkedHashSetIterator.next(), Integer.valueOf(5));
        assertEquals(iLinkedHashSetIterator.next(), Integer.valueOf(9));
        assertEquals(iLinkedHashSetIterator.next(), Integer.valueOf(2));

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
        Iterator<Float> fLinkedHashSetIterator = fLinkedHashSet.iterator();
        assertEquals(fLinkedHashSetIterator.next(), Float.valueOf(0.2f));
        assertEquals(fLinkedHashSetIterator.next(), Float.valueOf(-5f));
        assertNull(fLinkedHashSetIterator.next());
        assertEquals(fLinkedHashSetIterator.next(), Float.valueOf(1e30f));
    }

    @Test
    public void testAddTo_String_Collection() {
        ArrayList<Character> arrayList = new ArrayList<>();
        addTo("hello", arrayList);
        assertEquals(arrayList.size(), 5);
        assertEquals(arrayList.get(0), Character.valueOf('h'));
        assertEquals(arrayList.get(1), Character.valueOf('e'));
        assertEquals(arrayList.get(2), Character.valueOf('l'));
        assertEquals(arrayList.get(3), Character.valueOf('l'));
        assertEquals(arrayList.get(4), Character.valueOf('o'));
        LinkedList<Character> linkedList = new LinkedList<>();
        addTo("hello", linkedList);
        assertEquals(linkedList.size(), 5);
        assertEquals(linkedList.get(0), Character.valueOf('h'));
        assertEquals(linkedList.get(1), Character.valueOf('e'));
        assertEquals(linkedList.get(2), Character.valueOf('l'));
        assertEquals(linkedList.get(3), Character.valueOf('l'));
        assertEquals(linkedList.get(4), Character.valueOf('o'));
        HashSet<Character> hashSet = new HashSet<>();
        addTo("hello", hashSet);
        assertEquals(hashSet.size(), 4);
        assertTrue(hashSet.contains(Character.valueOf('h')));
        assertTrue(hashSet.contains(Character.valueOf('e')));
        assertTrue(hashSet.contains(Character.valueOf('l')));
        assertTrue(hashSet.contains(Character.valueOf('o')));
        TreeSet<Character> treeSet = new TreeSet<>();
        addTo("hello", treeSet);
        assertEquals(treeSet.size(), 4);
        assertTrue(treeSet.contains(Character.valueOf('h')));
        assertTrue(treeSet.contains(Character.valueOf('e')));
        assertTrue(treeSet.contains(Character.valueOf('l')));
        assertTrue(treeSet.contains(Character.valueOf('o')));
        LinkedHashSet<Character> linkedHashSet = new LinkedHashSet<>();
        addTo("hello", linkedHashSet);
        assertEquals(treeSet.size(), 4);
        Iterator<Character> linkedHashSetIterator = linkedHashSet.iterator();
        assertEquals(linkedHashSetIterator.next(), Character.valueOf('h'));
        assertEquals(linkedHashSetIterator.next(), Character.valueOf('e'));
        assertEquals(linkedHashSetIterator.next(), Character.valueOf('l'));
        assertEquals(linkedHashSetIterator.next(), Character.valueOf('o'));

        arrayList = new ArrayList<>();
        addTo("", arrayList);
        assertTrue(arrayList.isEmpty());
        linkedList = new LinkedList<>();
        addTo("", linkedList);
        assertTrue(linkedList.isEmpty());
        hashSet = new HashSet<>();
        addTo("", hashSet);
        assertTrue(hashSet.isEmpty());
        treeSet = new TreeSet<>();
        addTo("", treeSet);
        assertTrue(treeSet.isEmpty());
        linkedHashSet = new LinkedHashSet<>();
        addTo("", linkedHashSet);
        assertTrue(linkedHashSet.isEmpty());
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

    @Test
    public void testToList_String() {
        List<Character> list = toList("hello");
        assertEquals(list.size(), 5);
        assertEquals(list.get(0), Character.valueOf('h'));
        assertEquals(list.get(1), Character.valueOf('e'));
        assertEquals(list.get(2), Character.valueOf('l'));
        assertEquals(list.get(3), Character.valueOf('l'));
        assertEquals(list.get(4), Character.valueOf('o'));

        list = toList("");
        assertTrue(list.isEmpty());
    }

    @Test
    public void testToString() {
        assertEquals(IterableUtils.toString(Arrays.asList(4, 1, 5, 9, 2)), "[4, 1, 5, 9, 2]");
        assertEquals(IterableUtils.toString(new HashSet<Integer>()), "[]");
        LinkedList<Float> lList = new LinkedList<>();
        lList.add(0.2f);
        lList.add(-5f);
        lList.add(null);
        lList.add(1e30f);
        assertEquals(IterableUtils.toString(lList), "[0.2, -5.0, null, 1.0E30]");
    }

    @Test
    public void testFromString() {
        aeq(fromString("hello"), "[h, e, l, l, o]");
        aeq(fromString(""), "[]");
    }

    @Test
    public void testCharsToString() {
        aeq(charsToString(Arrays.asList('h', 'e', 'l', 'l', 'o')), "hello");
        aeq(charsToString(new ArrayList<>()), "");
        try {
            charsToString(Arrays.asList('h', null, 'l', 'l', 'o'));
            fail();
        } catch (NullPointerException e) {}
    }

    @Test
    public void testCons_Iterable() {
        aeq(cons(5, Arrays.asList(1, 2, 3, 4, 5)), "[5, 1, 2, 3, 4, 5]");
        aeq(cons(null, Arrays.asList(1, 2, 3, 4, 5)), "[null, 1, 2, 3, 4, 5]");
        aeq(cons(5, Arrays.asList(1, 2, null, 4, 5)), "[5, 1, 2, null, 4, 5]");
        aeq(cons(5, new ArrayList<Integer>()), "[5]");
        aeq(take(10, (Iterable<Integer>) cons(5, repeat(1))), "[5, 1, 1, 1, 1, 1, 1, 1, 1, 1]");
    }

    @Test
    public void testCons_String() {
        aeq(cons('A', " SMALL CAT"), "A SMALL CAT");
        aeq(cons('A', ""), "A");
    }

    @Test
    public void testConcat_Iterable_Iterable() {
        aeq(concat(Arrays.asList(1, 2, 3), Arrays.asList(90, 80, 70)), "[1, 2, 3, 90, 80, 70]");
        aeq(concat(Arrays.asList(1, null, 3), Arrays.asList(90, 80, 70)), "[1, null, 3, 90, 80, 70]");
        aeq(concat(Arrays.asList(1, 2, 3), Arrays.asList(90, null, 70)), "[1, 2, 3, 90, null, 70]");
        aeq(concat(new ArrayList<Integer>(), Arrays.asList(90, 80, 70)), "[90, 80, 70]");
        aeq(concat(Arrays.asList(1, 2, 3), new ArrayList<Integer>()), "[1, 2, 3]");
        aeq(concat(new ArrayList<Integer>(), new ArrayList<Integer>()), "[]");
        aeq(
                take(10, (Iterable<Integer>) concat(Arrays.asList(1, 2, 3), repeat(5))),
                "[1, 2, 3, 5, 5, 5, 5, 5, 5, 5]"
        );
        aeq(
                take(10, (Iterable<Integer>) concat(repeat(5), Arrays.asList(1, 2, 3))),
                "[5, 5, 5, 5, 5, 5, 5, 5, 5, 5]"
        );
        aeq(
                take(10, (Iterable<Integer>) concat(repeat(5), repeat(1))),
                "[5, 5, 5, 5, 5, 5, 5, 5, 5, 5]"
        );
    }

    @Test
    public void testConcat_String_String() {
        aeq(concat("Hello ", "World"), "Hello World");
        aeq(concat("Hello", ""), "Hello");
        aeq(concat("", "World"), "World");
        aeq(concat("", ""), "");
    }

    @Test
    public void testHead_Iterable() {
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.addAll(Arrays.asList(5, 4, 3, 2, 1));
        aeq(head(linkedHashSet), 5);

        linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.addAll(Arrays.asList(5));
        aeq(head(linkedHashSet), 5);

        linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.addAll(Arrays.asList(null, 4, 3, 2, 1));
        assertNull(head(linkedHashSet));

        linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add(null);
        assertNull(head(linkedHashSet));

        aeq(head((Iterable<Integer>) repeat(5)), 5);

        linkedHashSet = new LinkedHashSet<>();
        try {
            assertNull(head(linkedHashSet));
            fail();
        } catch (NoSuchElementException e) {}
    }

    @Test
    public void testHead_List() {
        aeq(head((List<Integer>) Arrays.asList(5, 4, 3, 2, 1)), 5);
        assertNull(head((List<Integer>) Arrays.asList(null, 4, 3, 2, 1)));
        aeq(head((List<Integer>) Arrays.asList(5)), 5);
        List<Integer> nullList = new ArrayList<>();
        nullList.add(null);
        assertNull(head(nullList));
        try {
            head(new ArrayList<Integer>());
            fail();
        } catch (IndexOutOfBoundsException e) {}
    }

    @Test
    public void testHead_SortedSet() {
        SortedSet<Integer> sortedSet = new TreeSet<>();
        sortedSet.addAll(Arrays.asList(1, 2, 3, 4, 5));
        aeq(head(sortedSet), 1);

        sortedSet = new TreeSet<>(new NullHandlingComparator<Integer>());
        sortedSet.addAll(Arrays.asList(null, 2, 3, 4, 5));
        assertNull(head(sortedSet));

        sortedSet = new TreeSet<>();
        sortedSet.addAll(Arrays.asList(1));
        aeq(head(sortedSet), 1);

        sortedSet = new TreeSet<>(new NullHandlingComparator<Integer>());
        sortedSet.add(null);
        assertNull(head(sortedSet));

        sortedSet = new TreeSet<>();
        try {
            head(sortedSet);
            fail();
        } catch (NoSuchElementException e) {}
    }

    @Test
    public void testHead_String() {
        aeq(head("hello"), 'h');
        aeq(head("h"), 'h');
        try {
            head("");
        } catch (StringIndexOutOfBoundsException e) {}
    }

    @Test
    public void testLast_Iterable() {
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.addAll(Arrays.asList(5, 4, 3, 2, 1));
        aeq(last(linkedHashSet), 1);

        linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.addAll(Arrays.asList(5, 4, 3, 2, null));
        assertNull(last(linkedHashSet));

        linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.addAll(Arrays.asList(1));
        aeq(last(linkedHashSet), 1);

        linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add(null);
        assertNull(last(linkedHashSet));

        linkedHashSet = new LinkedHashSet<>();
        try {
            assertNull(last(linkedHashSet));
            fail();
        } catch (NoSuchElementException e) {}
    }

    @Test
    public void testLast_List() {
        aeq(last((List<Integer>) Arrays.asList(5, 4, 3, 2, 1)), 1);
        assertNull(last((List<Integer>) Arrays.asList(5, 4, 3, 2, null)));
        aeq(last((List<Integer>) Arrays.asList(1)), 1);
        List<Integer> nullList = new ArrayList<>();
        nullList.add(null);
        assertNull(last(nullList));
        try {
            last(new ArrayList<Integer>());
            fail();
        } catch (IndexOutOfBoundsException e) {}
    }

    @Test
    public void testLast_SortedSet() {
        SortedSet<Integer> sortedSet = new TreeSet<>();
        sortedSet.addAll(Arrays.asList(1, 2, 3, 4, 5));
        aeq(last(sortedSet), 5);

        sortedSet = new TreeSet<>(new NullHandlingComparator<Integer>());
        sortedSet.add(null);
        assertNull(last(sortedSet));

        sortedSet = new TreeSet<>();
        sortedSet.addAll(Arrays.asList(5));
        aeq(last(sortedSet), 5);

        sortedSet = new TreeSet<>();
        try {
            last(sortedSet);
            fail();
        } catch (NoSuchElementException e) {}
    }

    @Test
    public void testLast_String() {
        aeq(last("o"), 'o');
        try {
            last("");
        } catch (StringIndexOutOfBoundsException e) {}
    }

    @Test
    public void testTail_Iterable() {
        aeq(tail(Arrays.asList(5, 4, 3, 2, 1)), "[4, 3, 2, 1]");
        aeq(tail(Arrays.asList(5, 4, null, 2, 1)), "[4, null, 2, 1]");
        aeq(tail(Arrays.asList(5)), "[]");
        List<Integer> nullList = new ArrayList<>();
        nullList.add(null);
        aeq(tail(nullList), "[]");
        try {
            tail(new ArrayList<Integer>());
            fail();
        } catch (NoSuchElementException e) {}
    }

    @Test
    public void testTail_String() {
        aeq(tail("hello"), "ello");
        aeq(tail("h"), "");
        try {
            toList(tail(""));
            fail();
        } catch (StringIndexOutOfBoundsException e) {}
    }

    @Test
    public void testInit_Iterable() {
        aeq(init(Arrays.asList(5, 4, 3, 2, 1)), "[5, 4, 3, 2]");
        aeq(init(Arrays.asList(5, 4, null, 2, 1)), "[5, 4, null, 2]");
        aeq(init(Arrays.asList(5)), "[]");
        List<Integer> nullList = new ArrayList<>();
        nullList.add(null);
        aeq(init(nullList), "[]");
        aeq(take(10, (Iterable<Integer>) init(repeat(5))), "[5, 5, 5, 5, 5, 5, 5, 5, 5, 5]");
        try {
            init(new ArrayList<Integer>());
            fail();
        } catch (NoSuchElementException e) {}
    }

    @Test
    public void testInit_String() {
        aeq(init("hello"), "hell");
        aeq(init("h"), "");
        try {
            toList(init(""));
            fail();
        } catch (StringIndexOutOfBoundsException e) {}
    }

    @Test
    public void testIsEmpty_Iterable() {
        assertFalse(isEmpty((Iterable<Integer>) cons(6, Arrays.asList(5, 4, 3, 2, 1))));
        assertFalse(isEmpty((Iterable<Integer>) cons(null, Arrays.asList(null, 2, 1))));
        assertFalse(isEmpty(repeat(5)));
        assertTrue(isEmpty((Iterable<Integer>) tail(Arrays.asList(2))));
    }

    @Test
    public void testIsEmpty_Collection() {
        assertFalse(isEmpty(Arrays.asList(5, 4, 3, 2, 1)));
        assertFalse(isEmpty(Arrays.asList(5, 4, null, 2, 1)));
        assertFalse(isEmpty(Arrays.asList(5)));
        List<Integer> nullList = new ArrayList<>();
        nullList.add(null);
        assertFalse(isEmpty(nullList));
        assertTrue(isEmpty(new ArrayList<Integer>()));
    }

    @Test
    public void testIsEmpty_String() {
        assertFalse(isEmpty("hello"));
        assertFalse(isEmpty("h"));
        assertTrue(isEmpty(""));
    }

    @Test
    public void testLength_Iterable() {
        assertEquals(length((Iterable<Integer>) cons(6, Arrays.asList(5, 4, 3, 2, 1))), 6);
        assertEquals(length((Iterable<Integer>) cons(null, Arrays.asList(null, 2, 1))), 4);
        assertEquals(length((Iterable<Integer>) tail(Arrays.asList(2))), 0);
    }

    @Test
    public void testBigIntegerLength() {
        aeq(bigIntegerLength((Iterable<Integer>) cons(6, Arrays.asList(5, 4, 3, 2, 1))), 6);
        aeq(bigIntegerLength((Iterable<Integer>) cons(null, Arrays.asList(null, 2, 1))), 4);
        aeq(bigIntegerLength((Iterable<Integer>) tail(Arrays.asList(2))), 0);
    }

    @Test
    public void testLength_Collection() {
        assertEquals(length(Arrays.asList(5, 4, 3, 2, 1)), 5);
        assertEquals(length(Arrays.asList(5, 4, null, 2, 1)), 5);
        assertEquals(length(Arrays.asList(5)), 1);
        List<Integer> nullList = new ArrayList<>();
        nullList.add(null);
        assertEquals(length(nullList), 1);
        assertEquals(length(new ArrayList<Integer>()), 0);
    }

    @Test
    public void testLength_String() {
        assertFalse(isEmpty("hello"));
        assertFalse(isEmpty("h"));
        assertTrue(isEmpty(""));
    }

    @Test
    public void testMap_Iterable() {
        aeq(map(i -> i + 3, Arrays.asList(1, 5, 3, 1, 6)), "[4, 8, 6, 4, 9]");
        aeq(map(i -> i == null ? -1 : i + 3, Arrays.asList(1, 5, null, 1, 6)), "[4, 8, -1, 4, 9]");
        aeq(take(10, (Iterable<Integer>) map(i -> i + 3, repeat(5))), "[8, 8, 8, 8, 8, 8, 8, 8, 8, 8]");
        aeq(map(s -> s + "!", Arrays.asList("BIFF", "BANG", "POW")), "[BIFF!, BANG!, POW!]");
        aeq(map(s -> s + "!", new ArrayList<String>()), "[]");
        try {
            IterableUtils.toList((Iterable<Integer>) map(i -> i + 3, Arrays.asList(1, 5, null, 1, 6)));
            fail();
        } catch (NullPointerException e) {}
    }

    @Test
    public void testMap_String() {
        aeq(map(Character::toUpperCase, "hello"), "HELLO");
        aeq(map(Character::toUpperCase, ""), "");
        Function<Character, Character> f = c -> {
            if (c == 'l')
                throw new IllegalArgumentException("L exception");
            return c;
        };
        try {
            map(f, "hello");
            fail();
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void testReverse_Iterable() {
        aeq(reverse(Arrays.asList(5, 4, 3, 2, 1)), "[1, 2, 3, 4, 5]");
        aeq(reverse(new ArrayList<Integer>()), "[]");
    }

    @Test
    public void testReverse_String() {
        aeq(reverse("hello"), "olleh");
        aeq(reverse("thanks"), "sknaht");
        aeq(reverse(""), "");
    }

    @Test
    public void testIntersperse_Iterable() {
        aeq(intersperse(0, Arrays.asList(1, 2, 3, 4, 5)), "[1, 0, 2, 0, 3, 0, 4, 0, 5]");
        aeq(intersperse(0, new ArrayList<Integer>()), "[]");
    }

    @Test
    public void testIntersperse_String() {
        aeq(intersperse(',', "abcde"), "a,b,c,d,e");
        aeq(intersperse('*', "MASH"), "M*A*S*H");
        aeq(intersperse('*', ""), "");
    }

    @Test
    public void testIntercalate_Iterable() {
        aeq(intercalate(Arrays.asList(0, 0, 0), Arrays.asList(
                (Iterable<Integer>) Arrays.asList(1, 2, 3),
                (Iterable<Integer>) Arrays.asList(4, 5, 6),
                (Iterable<Integer>) Arrays.asList(7, 8, 9))), "[1, 2, 3, 0, 0, 0, 4, 5, 6, 0, 0, 0, 7, 8, 9]");
        aeq((Iterable<Integer>) intercalate(new ArrayList<Integer>(), Arrays.asList(
                (Iterable<Integer>) Arrays.asList(1, 2, 3),
                (Iterable<Integer>) Arrays.asList(4, 5, 6),
                (Iterable<Integer>) Arrays.asList(7, 8, 9))), "[1, 2, 3, 4, 5, 6, 7, 8, 9]");
        aeq(take(10, (Iterable<Integer>) intercalate(repeat(5), Arrays.asList(
                (Iterable<Integer>) Arrays.asList(1, 2, 3),
                (Iterable<Integer>) Arrays.asList(4, 5, 6),
                (Iterable<Integer>) Arrays.asList(7, 8, 9)))), "[1, 2, 3, 5, 5, 5, 5, 5, 5, 5]");
        aeq(take(10, (Iterable<Integer>) intercalate(Arrays.asList(0, 0, 0), Arrays.asList(
                (Iterable<Integer>) repeat(1),
                (Iterable<Integer>) repeat(2),
                (Iterable<Integer>) repeat(3)))), "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1]");
        aeq(take(10, (Iterable<Integer>) intercalate(new ArrayList<Integer>(), Arrays.asList(
                (Iterable<Integer>) repeat(1),
                (Iterable<Integer>) repeat(2),
                (Iterable<Integer>) repeat(3)))), "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1]");
        aeq(intercalate(Arrays.asList(0, 0, 0), new ArrayList<Iterable<Integer>>()), "[]");
        aeq((Iterable<Integer>) intercalate(new ArrayList<>(), new ArrayList<Iterable<Integer>>()), "[]");
        aeq(intercalate(repeat(5), new ArrayList<Iterable<Integer>>()), "[]");
        aeq(
                take(10, (Iterable<Integer>) intercalate(
                        Arrays.asList(0, 0, 0),
                        repeat((Iterable<Integer>) Arrays.asList(1, 2, 3))
                )),
                "[1, 2, 3, 0, 0, 0, 1, 2, 3, 0]"
        );
        aeq(
                (Iterable<Integer>) take(10, (Iterable<Integer>) intercalate(
                        new ArrayList<>(),
                        repeat((Iterable<Integer>) Arrays.asList(1, 2, 3))
                )),
                "[1, 2, 3, 1, 2, 3, 1, 2, 3, 1]"
        );
        aeq(
                take(10, (Iterable<Integer>) intercalate(
                        repeat(5),
                        repeat((Iterable<Integer>) Arrays.asList(1, 2, 3))
                )),
                "[1, 2, 3, 5, 5, 5, 5, 5, 5, 5]"
        );
    }

    @Test
    public void testIntercalate_String() {
        aeq(intercalate(", ", (Iterable<String>) Arrays.asList("bread", "milk", "eggs")), "bread, milk, eggs");
        aeq(intercalate("", (Iterable<String>) Arrays.asList("bread", "milk", "eggs")), "breadmilkeggs");
        aeq(intercalate(", ", new ArrayList<String>()), "");
        aeq(intercalate("", new ArrayList<String>()), "");
    }

    @Test
    public void testTranspose() {
        aeq(transpose(Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        )), "[[1, 4, 7], [2, 5, 8], [3, 6, 9]]");
        aeq(transpose(Arrays.asList(
                Arrays.asList(1, 2, 3, -1, -1),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9, -2)
        )), "[[1, 4, 7], [2, 5, 8], [3, 6, 9], [-1, -2], [-1]]");
        aeq(transpose(Arrays.asList(
                Arrays.asList(1, 2, 3, -1, -1),
                new ArrayList<Integer>(),
                Arrays.asList(7, 8, 9, -2)
        )), "[[1, 7], [2, 8], [3, 9], [-1, -2], [-1]]");
        aeq(transpose(Arrays.asList(
                new ArrayList<Integer>(),
                new ArrayList<Integer>(),
                new ArrayList<>()
        )), "[]");
        aeq(transpose(new ArrayList<Iterable<Integer>>()), "[]");
        aeq(take(10, transpose(Arrays.asList(
                (Iterable<Integer>) Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                repeat(7)
        ))), "[[1, 4, 7], [2, 5, 7], [3, 6, 7], [7], [7], [7], [7], [7], [7], [7]]");
    }

    @Test
    public void testTransposeStrings() {
        aeq(transposeStrings(Arrays.asList("cat", "dog", "pen")), "[cdp, aoe, tgn]");
        aeq(transposeStrings(Arrays.asList("cater", "doghouse", "pen")), "[cdp, aoe, tgn, eh, ro, u, s, e]");
        aeq(transposeStrings(Arrays.asList("cater", "", "pen")), "[cp, ae, tn, e, r]");
        aeq(transposeStrings(Arrays.asList("", "", "")), "[]");
        aeq(transposeStrings(new ArrayList<String>()), "[]");
    }

    @Test
    public void testTransposeTruncating() {
        aeq(transposeTruncating(Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        )), "[[1, 4, 7], [2, 5, 8], [3, 6, 9]]");
        aeq(transposeTruncating(Arrays.asList(
                Arrays.asList(1, 2, 3, -1, -1),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9, -2)
        )), "[[1, 4, 7], [2, 5, 8], [3, 6, 9]]");
        aeq(transposeTruncating(Arrays.asList(
                Arrays.asList(1, 2, 3, -1, -1),
                new ArrayList<Integer>(),
                Arrays.asList(7, 8, 9, -2)
        )), "[]");
        aeq(transposeTruncating(Arrays.asList(
                new ArrayList<Integer>(),
                new ArrayList<Integer>(),
                new ArrayList<>()
        )), "[]");
        aeq(transposeTruncating(new ArrayList<Iterable<Integer>>()), "[]");
        aeq(transposeTruncating(Arrays.asList(
                (Iterable<Integer>) Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                repeat(7)
        )), "[[1, 4, 7], [2, 5, 7], [3, 6, 7]]");
    }

    @Test
    public void testTransposeStringsTruncating() {
        aeq(transposeStringsTruncating(Arrays.asList("cat", "dog", "pen")), "[cdp, aoe, tgn]");
        aeq(transposeStringsTruncating(Arrays.asList("cater", "doghouse", "pen")), "[cdp, aoe, tgn]");
        aeq(transposeStringsTruncating(Arrays.asList("cater", "", "pen")), "[]");
        aeq(transposeStringsTruncating(Arrays.asList("", "", "")), "[]");
        aeq(transposeStringsTruncating(new ArrayList<String>()), "[]");
    }

    @Test
    public void testTransposePadded() {
        aeq(transposePadded(0, Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        )), "[[1, 4, 7], [2, 5, 8], [3, 6, 9]]");
        aeq(transposePadded(0, Arrays.asList(
                Arrays.asList(1, 2, 3, -1, -1),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9, -2)
        )), "[[1, 4, 7], [2, 5, 8], [3, 6, 9], [-1, 0, -2], [-1, 0, 0]]");
        aeq(transposePadded(null, Arrays.asList(
                Arrays.asList(1, 2, 3, -1, -1),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9, -2)
        )), "[[1, 4, 7], [2, 5, 8], [3, 6, 9], [-1, null, -2], [-1, null, null]]");
        aeq(transposePadded(0, Arrays.asList(
                Arrays.asList(1, 2, 3, -1, -1),
                new ArrayList<Integer>(),
                Arrays.asList(7, 8, 9, -2)
        )), "[[1, 0, 7], [2, 0, 8], [3, 0, 9], [-1, 0, -2], [-1, 0, 0]]");
        aeq(transposePadded(0, Arrays.asList(
                new ArrayList<Integer>(),
                new ArrayList<Integer>(),
                new ArrayList<Integer>()
        )), "[]");
        aeq(transposePadded(0, new ArrayList<Iterable<Integer>>()), "[]");
        aeq(take(10, transposePadded(0, Arrays.asList(
                (Iterable<Integer>) Arrays.asList(1, 2, 3),
                (Iterable<Integer>) Arrays.asList(4, 5, 6),
                repeat(7)
        ))), "[[1, 4, 7], [2, 5, 7], [3, 6, 7], [0, 0, 7], [0, 0, 7]," +
             " [0, 0, 7], [0, 0, 7], [0, 0, 7], [0, 0, 7], [0, 0, 7]]");
    }

    @Test
    public void testTransposeStringsPadded() {
        aeq(transposeStringsPadded('_', Arrays.asList("cat", "dog", "pen")), "[cdp, aoe, tgn]");
        aeq(
                transposeStringsPadded('_', Arrays.asList("cater", "doghouse", "pen")),
                "[cdp, aoe, tgn, eh_, ro_, _u_, _s_, _e_]"
        );
        aeq(transposeStringsPadded('_', Arrays.asList("cater", "", "pen")), "[c_p, a_e, t_n, e__, r__]");
        aeq(transposeStringsPadded('_', Arrays.asList("", "", "")), "[]");
        aeq(transposeStringsPadded('_', new ArrayList<String>()), "[]");
    }

    private static void aeq(Iterable<?> a, Object b) {
        assertEquals(IterableUtils.toString(a), b.toString());
    }

    private static void aeq(Object a, Object b) {
        assertEquals(a.toString(), b.toString());
    }
}
