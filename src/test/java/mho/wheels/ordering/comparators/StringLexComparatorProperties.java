package mho.wheels.ordering.comparators;

import mho.wheels.iterables.IterableProvider;
import mho.wheels.testing.TestProperties;

import static mho.wheels.iterables.IterableUtils.*;
import static mho.wheels.testing.Testing.TINY_LIMIT;
import static mho.wheels.testing.Testing.propertiesCompareToHelper;

public class StringLexComparatorProperties extends TestProperties {
    public StringLexComparatorProperties() {
        super("StringLexComparator");
    }

    @Override
    protected void testBothModes() {
        propertiesConstructor();
        propertiesConstructor_Comparator();
    }

    private void propertiesConstructor() {
        initialize("new StringLexComparator()");
        propertiesCompareToHelper(LIMIT, P, new StringLexComparator(), IterableProvider::strings);
    }

    private void propertiesConstructor_Comparator() {
        initialize("new StringLexComparator(Comparator<Character>)");
        for (String s : take(LIMIT, P.stringsAtLeast(1))) {
            StringLexComparator comparator = new StringLexComparator(new StringBasedComparator(s));
            propertiesCompareToHelper(TINY_LIMIT, P, comparator, p -> p.strings(s));
        }
    }
}
