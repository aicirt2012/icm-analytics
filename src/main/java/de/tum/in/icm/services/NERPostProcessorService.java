package de.tum.in.icm.services;

import de.tum.in.icm.dtos.AnnotationDTO;
import de.tum.in.icm.dtos.NERResultDTO;
import de.tum.in.icm.dtos.RangeDTO;
import de.tum.in.icm.entities.IndexedPlainText;
import de.tum.in.icm.entities.XPathBuilder;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class NERPostProcessorService {

    public static NERResultDTO calculateHtmlIndices(NERResultDTO resultDTO, IndexedPlainText indexedPlainText) {
        for (AnnotationDTO annotation : resultDTO.getAnnotations()) {
            for (int plainTextStartIndex : annotation.getPlainTextIndices()) {
                int plainTextNodeIndex = plainTextStartIndex;
                Integer htmlTextNodeIndex = indexedPlainText.getIndexMap().get(plainTextNodeIndex);
                while (htmlTextNodeIndex == null) {
                    htmlTextNodeIndex = indexedPlainText.getIndexMap().get(--plainTextNodeIndex);
                }
                int startOffset = plainTextStartIndex - plainTextNodeIndex;
                annotation.addHtmlSourceOccurrence(htmlTextNodeIndex, startOffset);
            }
        }
        return resultDTO;
    }

    public static NERResultDTO calculateRangeObjects(NERResultDTO resultDTO, String htmlSource) {

        HTMLEditorKit.ParserCallback parserCallback = new HTMLEditorKit.ParserCallback() {
            XPathBuilder xPathBuilder = new XPathBuilder();
            List<AnnotationDTO> annotations = resultDTO.getAnnotations();

            RangeDTO currentSplitAnnotationRange = null;
            String annotationValueNotYetRead = "";

            @Override
            public void handleText(final char[] data, final int pos) {
                xPathBuilder.addTextTag(pos);
                if (currentSplitAnnotationRange != null) {
                    // already found the start of a split annotation, need to find the end now
                    int charsToRead = annotationValueNotYetRead.length();
                    int charsAlreadyRead;
                    for (charsAlreadyRead = 0; charsAlreadyRead <= charsToRead && charsAlreadyRead < data.length; charsAlreadyRead++) {
                        if (data[charsAlreadyRead] != annotationValueNotYetRead.toCharArray()[charsAlreadyRead]) {
                            if (charsAlreadyRead != charsToRead) {
                                // found unexpected character while still parsing the annotation value => malformed html
                                throw new UnsupportedOperationException("HTML source malformed: Could not find the end of a split annotation.");
                            }
                        }
                    }
                    if (charsAlreadyRead == charsToRead) {
                        // done parsing the split annotation
                        currentSplitAnnotationRange.setxPathEnd(xPathBuilder.toString());
                        currentSplitAnnotationRange.setOffsetEnd(charsAlreadyRead);
                        currentSplitAnnotationRange = null;
                        annotationValueNotYetRead = "";
                    } else {
                        // not yet done with parsing, continue in next text node
                        annotationValueNotYetRead = annotationValueNotYetRead.substring(charsAlreadyRead);
                    }
                }
                for (AnnotationDTO annotation : annotations) {
                    if (annotation.getHtmlTextNodeIndices().contains(pos)) {
                        for (int i = 0; i < annotation.getHtmlTextNodeIndices().size(); i++) {
                            if (annotation.getHtmlTextNodeIndices().get(i) == pos) {
                                RangeDTO rangeDTO = new RangeDTO();
                                rangeDTO.setxPathStart(xPathBuilder.toString());
                                rangeDTO.setOffsetStart(xPathBuilder.getOffsetFromInnerHtml(pos) + annotation.getHtmlAnnotationOffsets().get(i));
                                int startIndex = pos + annotation.getHtmlAnnotationOffsets().get(i);
                                int endIndex = startIndex + annotation.getValue().length();
                                String htmlValue = htmlSource.substring(startIndex, endIndex);
                                if (!htmlValue.equals(annotation.getValue())) {
                                    // complex annotation, split over multiple text nodes
                                    for (int j = 0; j < htmlValue.length(); j++) {
                                        if (htmlValue.toCharArray()[j] != annotation.getValue().toCharArray()[j]) {
                                            annotationValueNotYetRead = annotation.getValue().substring(j);
                                            break;
                                        }
                                    }
                                    currentSplitAnnotationRange = rangeDTO;
                                } else {
                                    // simple annotation, all contained in one text node
                                    rangeDTO.setxPathEnd(xPathBuilder.toString());
                                    rangeDTO.setOffsetEnd(rangeDTO.getOffsetStart() + annotation.getValue().length());
                                }
                                annotation.getRanges().add(rangeDTO);
                            }
                        }
                    }
                }
            }

            @Override
            public void handleStartTag(final HTML.Tag tag, final MutableAttributeSet a, final int pos) {
                if (tag.equals(HTML.Tag.BODY)) {
                    // xpaths are relative to body tag, drop everything we've parsed until now
                    xPathBuilder = new XPathBuilder();
                    return;
                }
                xPathBuilder.addOpeningTag(tag, pos);
            }

            @Override
            public void handleEndTag(final HTML.Tag tag, final int pos) {
                xPathBuilder.addClosingTag(tag);
            }
        };

        try {
            new ParserDelegator().parse(new StringReader(htmlSource), parserCallback, false);
        } catch (IOException e) {
            throw new RuntimeException(e);  //TODO improve error handling
        }
        return resultDTO;
    }

}
