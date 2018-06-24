package de.tum.in.icm.services;

import de.tum.in.icm.dtos.AnnotationDTO;
import de.tum.in.icm.dtos.RangeDTO;
import de.tum.in.icm.dtos.ResultDTO;
import de.tum.in.icm.entities.TextNodeMap;

import java.util.Map;
import java.util.logging.Logger;

public class NERPostProcessorService {

    private static final Logger logger = Logger.getLogger(NERPostProcessorService.class.getName());

    private Map<Integer, Integer> textToNodeIndexMap;
    private TextNodeMap textNodeMap;

    public static ResultDTO calculateRangesPlainText(ResultDTO resultDto) {
        for (AnnotationDTO annotation : resultDto.getAnnotations()) {
            for (int textIndex : annotation.getPlainTextIndices()) {
                RangeDTO rangeDTO = new RangeDTO();
                rangeDTO.setxPathStart("");
                rangeDTO.setxPathEnd("");
                rangeDTO.setOffsetStart(textIndex);
                rangeDTO.setOffsetEnd(textIndex + annotation.getValue().length());
                annotation.getRanges().add(rangeDTO);
            }
        }
        return resultDto;
    }

    public ResultDTO calculateRanges(ResultDTO resultDto, TextNodeMap textNodeMap) {
        this.textNodeMap = textNodeMap;
        textToNodeIndexMap = textNodeMap.getTextToNodeIndexMap();
        for (AnnotationDTO annotation : resultDto.getAnnotations()) {
            if (annotation.getRanges().isEmpty()) {
                for (int textIndex : annotation.getPlainTextIndices()) {
                    RangeDTO rangeDTO = calculateRangeDTO(annotation, textIndex);
                    annotation.getRanges().add(rangeDTO);
                }
            }
        }
        return resultDto;
    }

    private RangeDTO calculateRangeDTO(AnnotationDTO annotation, int textIndex) {
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
                        logger.warning("Unexpected character, using current index as end of annotation!");
                    }
                    endXPath = getParentLocator(listIndex);
                    relativeEndOffset = remainingAnnotationValue.length();
                    remainingAnnotationValue = "";
                } else {
                    // remaining value not fully contained, keep on parsing
                    if (!remainingAnnotationValue.startsWith(textNode)) {
                        logger.warning("Unexpected character, using current index as end of annotation!");
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

    private String getParentLocator(Integer listIndex) {
        return textNodeMap.getParentLocators().get(listIndex).toString();
    }

    private int getRelativeStartOffset(Integer listIndex, int startOffset) {
        return textNodeMap.getParentOffsets().get(listIndex) + startOffset;
    }

    private int getListIndex(int textIndex) {
        Integer listIndex = textToNodeIndexMap.get(textIndex);
        while (listIndex == null) {
            listIndex = textToNodeIndexMap.get(--textIndex);
        }
        return listIndex;
    }

    private int getTextNodeIndex(int textIndex) {
        Integer listIndex = textToNodeIndexMap.get(textIndex);
        while (listIndex == null) {
            listIndex = textToNodeIndexMap.get(--textIndex);
        }
        return textIndex;
    }

}
