package de.tum.in.icm.services;

import de.tum.in.icm.dtos.AnnotationDTO;
import de.tum.in.icm.dtos.NERResultDTO;
import de.tum.in.icm.entities.TextNodeMap;

import java.util.Map;

public class NERPostProcessorService {

    private static Map<Integer, Integer> textToNodeIndexMap;

    public static NERResultDTO calculateRangeObjects(NERResultDTO resultDto, TextNodeMap textNodeMap) {
        textToNodeIndexMap = textNodeMap.getTextToNodeIndexMap();
        for (AnnotationDTO annotation : resultDto.getAnnotations()) {
            for (int textIndex : annotation.getPlainTextIndices()) {
                Integer listIndex = getListIndex(textIndex);
                int textNodeIndex = getTextNodeIndex(textIndex);
                int startOffset = textIndex - textNodeIndex;
                String textNode = textNodeMap.getValues().get(listIndex);

                String startXPath = textNodeMap.getParentLocators().get(listIndex).toString();
                int relativeStartOffset = textNodeMap.getParentOffsets().get(listIndex) + startOffset;
                String endXPath = startXPath;
                int relativeEndOffset = relativeStartOffset + annotation.getValue().length();
                if (textNode.substring(startOffset).length() < annotation.getValue().length()) {
                    // annotation value is split over multiple nodes
                    endXPath = "not implemented";
                    relativeEndOffset = -1;
                }

                annotation.addXPathRange(startXPath, relativeStartOffset, endXPath, relativeEndOffset);
            }
        }
        return resultDto;
    }

    private static int getListIndex(int textIndex) {
        Integer listIndex = textToNodeIndexMap.get(textIndex);
        while (listIndex == null) {
            listIndex = textToNodeIndexMap.get(--textIndex);
        }
        return listIndex;
    }

    private static int getTextNodeIndex(int textIndex) {
        Integer listIndex = textToNodeIndexMap.get(textIndex);
        while (listIndex == null) {
            listIndex = textToNodeIndexMap.get(--textIndex);
        }
        return textIndex;
    }

}
