package de.tum.in.icm.entities;

import javax.swing.text.html.HTML;
import java.util.LinkedList;
import java.util.ListIterator;

public class XPathBuilder {

    private LinkedList<String> tags = new LinkedList<>();
    private LinkedList<Integer> tagCounts = new LinkedList<>();
    private String lastClosedTag = null;
    private Integer lastClosedTagCount = -1;

    @Deprecated
    public void addOpeningTag(HTML.Tag openingTag, int htmlIndex) {
        this.addOpeningTag(openingTag.toString(), htmlIndex);
    }

    public void addOpeningTag(String openingTag, int htmlIndex) {
        if (openingTag.equals(lastClosedTag)) {
            tags.add(openingTag);
            tagCounts.add(lastClosedTagCount + 1);
        } else {
            tags.add(openingTag);
            tagCounts.add(1);
        }
        lastClosedTag = null;
        lastClosedTagCount = -1;
    }

    @Deprecated
    public void addClosingTag(HTML.Tag closingTag) {
        this.addClosingTag(closingTag.toString());
    }

    public void addClosingTag(String closingTag) {
        if (tags.isEmpty()) {
            return;
        }
        if (!closingTag.equals(tags.peekLast())) {
            throw new UnsupportedOperationException("Malformed HTML. Cannot close a tag that is not the last open tag.");
        }
        lastClosedTag = tags.pollLast();
        lastClosedTagCount = tagCounts.pollLast();
    }

    /**
     * remove offset handling when jsoup parsing is established
     */
    @Deprecated
    public void addTextTag(int htmlIndex) {
        // do nothing, just for compilation
    }

    /**
     * remove offset handling when jsoup parsing is established
     */
    @Deprecated
    public int getOffsetFromInnerHtml(int htmlIndex) {
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ListIterator<String> tagIterator = tags.listIterator();
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
