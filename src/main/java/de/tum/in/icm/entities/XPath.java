package de.tum.in.icm.entities;

import java.util.LinkedList;
import java.util.ListIterator;

public class XPath {

    private LinkedList<String> tags = new LinkedList<>();
    private LinkedList<Integer> tagCounts = new LinkedList<>();

    public void add(String tag) {
        this.add(tag, 1);
    }

    public void add(String tag, int count) {
        tags.add(tag);
        tagCounts.add(count);
    }

    public void removeLast() {
        tags.pollLast();
        tagCounts.pollLast();
    }

    public String getLastTag() {
        return tags.peekLast();
    }

    @Override
    public String toString() {
        StringBuilder xPath = new StringBuilder();
        ListIterator<String> tagIterator = tags.listIterator();
        ListIterator<Integer> tagCountIterator = tagCounts.listIterator();
        while (tagIterator.hasNext()) {
            xPath.append("/");
            xPath.append(tagIterator.next());
            xPath.append("[");
            xPath.append(tagCountIterator.next());
            xPath.append("]");
        }
        return xPath.toString();
    }
}
