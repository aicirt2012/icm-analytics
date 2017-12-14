package de.tum.in.icm.entities;

import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XPathBuilder {

    private XPath currentXPath = new XPath();
    private Map<Integer, ArrayList<String>> lastClosedTags = new HashMap<>();
    private Map<Integer, ArrayList<Integer>> lastClosedTagCounts = new HashMap<>();

    public void addOpeningTag(String openingTag) {
        if (getLastClosedTags(currentXPath.getDepth() + 1).contains(openingTag)) {
            currentXPath.add(openingTag, getLastClosedTagCount(currentXPath.getDepth() + 1, openingTag) + 1);
        } else {
            currentXPath.add(openingTag);
        }
    }

    private List<String> getLastClosedTags(int depth) {
        return lastClosedTags.getOrDefault(depth, new ArrayList<>());
    }

    private Integer getLastClosedTagCount(int depth, String tagName) {
        List<String> tags = lastClosedTags.get(depth);
        int tagIndex = tags.indexOf(tagName);
        return lastClosedTagCounts.get(depth).get(tagIndex);
    }

    public void addClosingTag(String closingTag) {
        if (currentXPath.toString().isEmpty()) {
            return;
        }
        if (!closingTag.equals(currentXPath.getLastTag())) {
            //TODO can maybe be removed as jsoup only outputs valid html...
            throw new UnsupportedOperationException("Malformed HTML. Cannot close a tag that is not the last open tag.");
        }
        resetLastClosedTags(currentXPath.getDepth() + 1);
        addLastClosedTag(currentXPath.getDepth());
        currentXPath.removeLastTag();
    }

    private void addLastClosedTag(int depth) {
        ArrayList<String> lastClosedTags = this.lastClosedTags.getOrDefault(depth, new ArrayList<>());
        ArrayList<Integer> lastClosedTagCounts = this.lastClosedTagCounts.getOrDefault(depth, new ArrayList<>());
        if (lastClosedTags.contains(currentXPath.getLastTag())) {
            int index = lastClosedTags.indexOf(currentXPath.getLastTag());
            lastClosedTagCounts.set(index, currentXPath.getLastTagCount());
        } else {
            lastClosedTags.add(currentXPath.getLastTag());
            lastClosedTagCounts.add(currentXPath.getLastTagCount());
        }
        this.lastClosedTags.put(depth, lastClosedTags);
        this.lastClosedTagCounts.put(depth, lastClosedTagCounts);
    }

    private void resetLastClosedTags(int depth) {
        this.lastClosedTags.put(depth, new ArrayList<>());
        this.lastClosedTagCounts.put(depth, new ArrayList<>());
    }

    public XPath toXPath() {
        return XPath.copy(currentXPath);
    }

    @Override
    public String toString() {
        return currentXPath.toString();
    }

    @Deprecated
    public void addClosingTag(HTML.Tag closingTag) {
        this.addClosingTag(closingTag.toString());
    }

    @Deprecated
    public void addOpeningTag(HTML.Tag openingTag, int htmlIndex) {
        this.addOpeningTag(openingTag.toString());
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
}
