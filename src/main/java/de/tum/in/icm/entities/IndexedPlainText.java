package de.tum.in.icm.entities;

import java.util.HashMap;
import java.util.Map;

public class IndexedPlainText {

    private StringBuilder plainText = new StringBuilder();
    private Map<Integer, Integer> indexMap = new HashMap<>();

    public void addPlainText(String additionalPlainText) {
        this.plainText.append(additionalPlainText);
    }

    public String getPlainText() {
        return plainText.toString();
    }

    public void setPlainText(String plainText) {
        this.plainText = new StringBuilder(plainText);
    }

    public Map<Integer, Integer> getIndexMap() {
        return indexMap;
    }

    public void setIndexMap(Map<Integer, Integer> indexMap) {
        this.indexMap = indexMap;
    }

}
