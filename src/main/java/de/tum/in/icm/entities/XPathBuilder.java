package de.tum.in.icm.entities;

import javax.swing.text.html.HTML;
import java.util.LinkedList;

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
        if (closingTag.equals(tags.peekLast())) {
            Integer tagCount = tagCounts.pollLast() - 1;
            tagCounts.add(tagCount);
        } else {
            tags.add(closingTag);
            tagCounts.add(1);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        while (!tags.isEmpty()) {
            stringBuilder.append("/");
            stringBuilder.append(tags.pollFirst());
            stringBuilder.append("[");
            stringBuilder.append(tagCounts.pollFirst());
            stringBuilder.append("]");
        }
        return stringBuilder.toString();
    }
}
