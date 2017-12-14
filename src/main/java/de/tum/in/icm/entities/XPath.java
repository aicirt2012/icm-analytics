package de.tum.in.icm.entities;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;

public class XPath {

    private LinkedList<String> tags = new LinkedList<>();
    private LinkedList<Integer> tagCounts = new LinkedList<>();

    public static XPath copy(XPath xPath) {
        XPath copy = new XPath();
        copy.tags = new LinkedList<>(xPath.tags);
        copy.tagCounts = new LinkedList<>(xPath.tagCounts);
        return copy;
    }

    public void add(String tag) {
        this.add(tag, 1);
    }

    public void add(String tag, int count) {
        tags.add(tag);
        tagCounts.add(count);
    }

    public void removeLastTag() {
        tags.pollLast();
        tagCounts.pollLast();
    }

    public String getLastTag() {
        return tags.peekLast();
    }

    public int getLastTagCount() {
        return tagCounts.peekLast();
    }

    public int getDepth() {
        return tags.size() - 1;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XPath xPath = (XPath) o;
        return Objects.equals(this.toString(), xPath.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.toString());
    }
}
