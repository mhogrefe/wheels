package mho.haskellesque.ordering.comparators;

import mho.haskellesque.ordering.Ordering;

import java.util.Comparator;

import static mho.haskellesque.ordering.Ordering.*;

public class StringShortlexComparator implements Comparator<String> {
    private final Comparator<Character> characterComparator;

    public StringShortlexComparator() {
        this.characterComparator = null;
    }

    public StringShortlexComparator(Comparator<Character> characterComparator) {
        this.characterComparator = characterComparator;
    }

    @Override
    public int compare(String s, String t) {
        if (s.length() > t.length()) return GT.toInt();
        if (s.length() < t.length()) return LT.toInt();
        for (int i = 0; i < s.length(); i++) {
            char sc = s.charAt(i);
            char tc = t.charAt(i);
            Ordering characterOrdering;
            if (characterComparator == null) {
                characterOrdering = Ordering.compare(sc, tc);
            } else {
                characterOrdering = Ordering.compare(characterComparator, sc, tc);
            }
            if (characterOrdering != EQ) return characterOrdering.toInt();
        }
        return EQ.toInt();
    }
}
