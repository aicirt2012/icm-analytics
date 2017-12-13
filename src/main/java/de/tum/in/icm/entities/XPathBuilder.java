package de.tum.in.icm.entities;

import javax.swing.text.html.HTML;

public class XPathBuilder {

    private XPath currentXPath = new XPath();
    private String lastClosedTag = null;
    private Integer lastClosedTagCount = -1;

    public void addOpeningTag(String openingTag) {
        if (openingTag.equals(lastClosedTag)) {
            currentXPath.add(openingTag, lastClosedTagCount + 1);
        } else {
            currentXPath.add(openingTag);
        }
        lastClosedTag = null;
        lastClosedTagCount = -1;
    }

    public void addClosingTag(String closingTag) {
        if (currentXPath.toString().isEmpty()) {
            return;
        }
        if (!closingTag.equals(currentXPath.getLastTag())) {
            throw new UnsupportedOperationException("Malformed HTML. Cannot close a tag that is not the last open tag.");
        }
        lastClosedTag = currentXPath.getLastTag();
        lastClosedTagCount = currentXPath.getLastTagCount();
        currentXPath.removeLastTag();
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
