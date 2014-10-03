package mho.haskellesque.iterables;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static mho.haskellesque.iterables.IterableUtils.*;

public class IterableUtilsTest {
    @Test
    public void testToList_Iterable() {
        Set<Integer> set = new LinkedHashSet<>();
        set.addAll(Arrays.asList(4, 1, 5, 9, 2));
        List<Integer> iList = toList(set);
        Assert.assertEquals(iList.size(), 5);
        Assert.assertEquals(iList.get(0), Integer.valueOf(4));
        Assert.assertEquals(iList.get(1), Integer.valueOf(1));
        Assert.assertEquals(iList.get(2), Integer.valueOf(5));
        Assert.assertEquals(iList.get(3), Integer.valueOf(9));
        Assert.assertEquals(iList.get(4), Integer.valueOf(2));

        set = new HashSet<>();
        iList = toList(set);
        Assert.assertTrue(iList.isEmpty());

        LinkedList<Float> lList = new LinkedList<>();
        lList.add(0.2f);
        lList.add(-5f);
        lList.add(null);
        lList.add(1e30f);
        List<Float> fList = toList(lList);
        Assert.assertEquals(fList.size(), 4);
        Assert.assertEquals(fList.get(0), Float.valueOf(0.2f));
        Assert.assertEquals(fList.get(1), Float.valueOf(-5f));
        Assert.assertNull(fList.get(2));
        Assert.assertEquals(fList.get(3), Float.valueOf(1e30f));
    }
}
