package de.tum.in.icm.services;

import de.tum.in.icm.dtos.AnnotationDTO;
import de.tum.in.icm.dtos.NERResultDTO;
import de.tum.in.icm.dtos.RangeDTO;
import de.tum.in.icm.entities.TextNodeMap;

import java.util.Map;

public class NERPostProcessorService {

    private static Map<Integer, Integer> textToNodeIndexMap;
    private static TextNodeMap textNodeMap;

    public static NERResultDTO calculateRanges(NERResultDTO resultDto, TextNodeMap textNodeMap) {
        NERPostProcessorService.textNodeMap = textNodeMap;
        textToNodeIndexMap = textNodeMap.getTextToNodeIndexMap();
        for (AnnotationDTO annotation : resultDto.getAnnotations()) {
            for (int textIndex : annotation.getPlainTextIndices()) {
                RangeDTO rangeDTO = calculateRangeDTO(annotation, textIndex);
                annotation.getRanges().add(rangeDTO);
            }
        }
        return resultDto;
    }

    private static RangeDTO calculateRangeDTO(AnnotationDTO annotation, int textIndex) {
        Integer listIndex = getListIndex(textIndex);
        int textNodeIndex = getTextNodeIndex(textIndex);
        int startOffset = textIndex - textNodeIndex;
        String textNode = textNodeMap.getValues().get(listIndex);

        String startXPath = getParentLocator(listIndex);
        int relativeStartOffset = getRelativeStartOffset(listIndex, startOffset);
        String endXPath = startXPath;
        int relativeEndOffset = relativeStartOffset + annotation.getValue().length();
        if (textNode.substring(startOffset).length() < annotation.getValue().length()) {
            // annotation value is split over multiple nodes
            String remainingAnnotationValue = annotation.getValue().substring(textNode.substring(startOffset).length());
            while (!remainingAnnotationValue.isEmpty()) {
                // get next text node until annotation is fully read
                listIndex++;
                textNode = textNodeMap.getValues().get(listIndex);
                if (textNode.length() >= remainingAnnotationValue.length()) {
                    // remaining value fully contained, this is the end node
                    if (!textNode.startsWith(remainingAnnotationValue)) {
                        throw new RuntimeException("Unexpected character, could not find end of annotation!");
                        // TODO think about simply stopping and using this as the end instead of aborting with exception
                    }
                    endXPath = getParentLocator(listIndex);
                    relativeEndOffset = remainingAnnotationValue.length();
                    remainingAnnotationValue = "";
                } else {
                    // remaining value not fully contained, keep on parsing
                    if (!remainingAnnotationValue.startsWith(textNode)) {
                        throw new RuntimeException("Unexpected character, could not find end of annotation!");
                        // TODO think about simply stopping and using this as the end instead of aborting with exception
                    }
                    remainingAnnotationValue = remainingAnnotationValue.substring(textNode.length());
                }
            }
        }

        RangeDTO rangeDTO = new RangeDTO();
        rangeDTO.setxPathStart(startXPath);
        rangeDTO.setxPathEnd(endXPath);
        rangeDTO.setOffsetStart(relativeStartOffset);
        rangeDTO.setOffsetEnd(relativeEndOffset);
        return rangeDTO;
    }

    private static String getParentLocator(Integer listIndex) {
        return textNodeMap.getParentLocators().get(listIndex).toString();
    }

    private static int getRelativeStartOffset(Integer listIndex, int startOffset) {
        return textNodeMap.getParentOffsets().get(listIndex) + startOffset;
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
