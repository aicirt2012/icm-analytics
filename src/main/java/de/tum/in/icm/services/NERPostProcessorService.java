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

            @Override
            public void handleText(final char[] data, final int pos) {
                for (AnnotationDTO annotation : annotations) {
                    if (annotation.getHtmlTextNodeIndices().contains(pos)) {
                        for (int i = 0; i < annotation.getHtmlTextNodeIndices().size(); i++) {
                            if (annotation.getHtmlTextNodeIndices().get(i) == pos) {
                                RangeDTO rangeDTO = new RangeDTO();
                                rangeDTO.setxPathStart(xPathBuilder.toString());
                                rangeDTO.setOffsetStart(annotation.getHtmlAnnotationOffsets().get(i));
                                int startIndex = pos + rangeDTO.getOffsetStart();
                                int endIndex = startIndex + annotation.getValue().length();
                                if (!htmlSource.substring(startIndex, endIndex).equals(annotation.getValue())) {
                                    throw new UnsupportedOperationException("HTML source contains complex annotations. Handling those is not yet implemented.");
                                } else {
                                    rangeDTO.setxPathEnd(xPathBuilder.toString());
                                    rangeDTO.setOffsetEnd(rangeDTO.getOffsetStart() + annotation.getValue().length());
                                }
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
                xPathBuilder.addOpeningTag(tag);
            }

            @Override
            public void handleEndTag(final HTML.Tag tag, final int pos) {
                xPathBuilder.addClosingTag(tag);
            }
        };

        try {
            new ParserDelegator().parse(new StringReader(htmlSource), parserCallback, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultDTO;
    }

}
