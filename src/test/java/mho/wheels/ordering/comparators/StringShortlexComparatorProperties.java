package mho.wheels.ordering.comparators;

import mho.wheels.iterables.IterableProvider;
import mho.wheels.testing.TestProperties;

import static mho.wheels.iterables.IterableUtils.take;
import static mho.wheels.testing.Testing.TINY_LIMIT;
import static mho.wheels.testing.Testing.propertiesCompareToHelper;

public class StringShortlexComparatorProperties extends TestProperties {
    public StringShortlexComparatorProperties() {
        super("StringShortlexComparator");
    }

    @Override
    protected void testBothModes() {
        propertiesConstructor();
        propertiesConstructor_Comparator();
    }

    private void propertiesConstructor() {
        initialize("new StringShortlexComparator()");
        propertiesCompareToHelper(LIMIT, P, new StringShortlexComparator(), IterableProvider::strings);
    }

    private void propertiesConstructor_Comparator() {
        initialize("new StringShortlexComparator(Comparator<Character>)");
        for (String s : take(LIMIT, P.stringsAtLeast(1))) {
            StringShortlexComparator comparator = new StringShortlexComparator(new StringBasedComparator(s));
            propertiesCompareToHelper(TINY_LIMIT, P, comparator, p -> p.strings(s));
        }
    }
}
