package de.tum.in.icm.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class TextNodeMap {

    private static final List<String> breakingHTMLTags = new ArrayList<>();

    private List<String> values;
    private List<XPath> parentLocators;
    private List<Integer> parentOffsets;

    public void add(String value, XPath parentLocator, int parentOffset) {
        this.values.add(value);
        this.parentLocators.add(parentLocator);
        this.parentOffsets.add(parentOffset);
    }

    public String toPlainText() {
        StringBuilder plainText = new StringBuilder();
        ListIterator<String> valueIterator = values.listIterator();
        ListIterator<XPath> locatorIterator = parentLocators.listIterator();
        while (valueIterator.hasNext()) {
            plainText.append(valueIterator.next());
            if (breakingHTMLTags.contains(locatorIterator.next().getLastTag())) {
                plainText.append("\n");
            }
        }
        return plainText.toString();
    }

}
