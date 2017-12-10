package de.tum.in.icm.entities;

import javax.swing.text.html.HTML;
import java.util.LinkedList;
import java.util.ListIterator;

public class XPathBuilder {

    private LinkedList<HTML.Tag> tags = new LinkedList<>();
    private LinkedList<Integer> tagCounts = new LinkedList<>();

    public void addOpeningTag(HTML.Tag openingTag) {
        if (openingTag.equals(tags.peekLast())) {
            Integer tagCount = tagCounts.pollLast() + 1;
            tagCounts.add(tagCount);
        } else {
            tags.add(openingTag);
            tagCounts.add(1);
        }
    }

    public void addClosingTag(HTML.Tag closingTag) {
        if (tags.isEmpty()) {
            return;
        }
        if (!closingTag.equals(tags.peekLast())) {
            throw new UnsupportedOperationException("Malformed HTML. Cannot close a tag that is not the last open tag.");
        }
        if (tagCounts.peekLast() == 1) {
            tags.pollLast();
            tagCounts.pollLast();
        } else {
            Integer tagCount = tagCounts.pollLast() - 1;
            tagCounts.add(tagCount);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ListIterator<HTML.Tag> tagIterator = tags.listIterator();
        ListIterator<Integer> tagCountIterator = tagCounts.listIterator();
        while (tagIterator.hasNext()) {
            stringBuilder.append("/");
            stringBuilder.append(tagIterator.next());
            stringBuilder.append("[");
            stringBuilder.append(tagCountIterator.next());
            stringBuilder.append("]");
        }
        return stringBuilder.toString();
    }
}
