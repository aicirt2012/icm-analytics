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

        String startXPath = getStartXPath(listIndex);
        int relativeStartOffset = getRelativeStartOffset(listIndex, startOffset);
        String endXPath = startXPath;
        int relativeEndOffset = relativeStartOffset + annotation.getValue().length();
        if (textNode.substring(startOffset).length() < annotation.getValue().length()) {
            // annotation value is split over multiple nodes
            endXPath = "not implemented";
            relativeEndOffset = -1;
        }

        RangeDTO rangeDTO = new RangeDTO();
        rangeDTO.setxPathStart(startXPath);
        rangeDTO.setxPathEnd(endXPath);
        rangeDTO.setOffsetStart(relativeStartOffset);
        rangeDTO.setOffsetEnd(relativeEndOffset);
        return rangeDTO;
    }

    private static String getStartXPath(Integer listIndex) {
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
