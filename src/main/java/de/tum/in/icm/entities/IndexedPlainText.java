package de.tum.in.icm.entities;

import java.util.HashMap;
import java.util.Map;

public class IndexedPlainText {

    private StringBuilder plainText = new StringBuilder();
    private Map<Integer, Integer> indexMap = new HashMap<>();

    public void addPlainText(String additionalPlainText, int htmlSourceIndex) {
        this.indexMap.put(plainText.length(), htmlSourceIndex);
        this.plainText.append(additionalPlainText);
    }

    public String getPlainText() {
        return plainText.toString();
    }

    public Map<Integer, Integer> getIndexMap() {
        return indexMap;
    }

}