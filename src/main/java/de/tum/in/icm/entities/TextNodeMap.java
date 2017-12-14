package de.tum.in.icm.entities;

import java.util.*;

public class TextNodeMap {

    private List<String> values = new ArrayList<>();
    private List<XPath> parentLocators = new ArrayList<>();
    private List<Integer> parentOffsets = new ArrayList<>();

    public void add(String value, XPath parentLocator, int parentOffset) {
        this.values.add(value);
        this.parentLocators.add(parentLocator);
        this.parentOffsets.add(parentOffset);
    }

    public String toPlainText() {
        StringBuilder plainText = new StringBuilder();
        for (String value : values) {
            plainText.append(value);
        }
        return plainText.toString();
    }

    public Map<Integer, Integer> getTextToNodeIndexMap() {
        Map<Integer, Integer> textToNodeIndexMap = new HashMap<>();
        StringBuilder plainText = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            String value = values.get(i);
            textToNodeIndexMap.put(plainText.length(), i);
            plainText.append(value);
        }
        return textToNodeIndexMap;
    }

    public boolean lastTagEquals(String tagName) {
        return values.get(values.size() - 1).equals(tagName);
    }

    public List<String> getValues() {
        return values;
    }

    public List<XPath> getParentLocators() {
        return parentLocators;
    }

    public List<Integer> getParentOffsets() {
        return parentOffsets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextNodeMap that = (TextNodeMap) o;
        return Objects.equals(values, that.values) &&
                Objects.equals(parentLocators, that.parentLocators) &&
                Objects.equals(parentOffsets, that.parentOffsets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values, parentLocators, parentOffsets);
    }

}
