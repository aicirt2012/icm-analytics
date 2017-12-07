package de.tum.in.icm.services;

import de.tum.in.icm.dtos.AnnotationDTO;
import de.tum.in.icm.dtos.NERResultDTO;
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

    public static NERResultDTO calculateHtmlIndices(NERResultDTO resultDTO, String htmlSource, IndexedPlainText indexedPlainText) {
    // FIXME does not play well with encoded entities (e.g. &copy; leads to differences in index between plain text and html)
        for (AnnotationDTO annotation : resultDTO.getAnnotations()) {
            for (int plainTextStartIndex : annotation.getPlainTextStartIndices()) {
                int currentIndex = plainTextStartIndex;
                Integer parentTagStartIndex = indexedPlainText.getIndexMap().get(currentIndex);
                while (parentTagStartIndex == null) {
                    parentTagStartIndex = indexedPlainText.getIndexMap().get(--currentIndex);
                }
                int startOffset = plainTextStartIndex - currentIndex;
                int htmlStartIndex = parentTagStartIndex + startOffset;

                // check if there is another index on the map in the range of the length of the annotation value
                int htmlEndIndex = htmlStartIndex + annotation.getValue().length();
                if (htmlSource.substring(htmlStartIndex, htmlEndIndex).equals(annotation.getValue())) {
                    annotation.addHtmlSourceOccurence(htmlStartIndex, htmlEndIndex);
                } else {
                    // FIXME also triggered by e.g. &copy; prefix
                    throw new UnsupportedOperationException("Parsing complex annotation is not yet implemented.");
                }
            }
        }
        return resultDTO;
    }

    public static NERResultDTO calculateRangeObjects(NERResultDTO resultDTO, String htmlSource) {

        HTMLEditorKit.ParserCallback parserCallback = new HTMLEditorKit.ParserCallback() {
            XPathBuilder xPathBuilder = new XPathBuilder();
            List<AnnotationDTO> annotations = resultDTO.getAnnotations();
            // annotation list is expected to be sorted ascending by plain text index
            AnnotationDTO currentAnnotation = null;

            @Override
            public void handleText(final char[] data, final int pos) {

            }

            @Override
            public void handleStartTag(final HTML.Tag t, final MutableAttributeSet a, final int pos) {
                if (t.equals(HTML.Tag.BODY)) {
                    // xpath is relative to body tag, drop everything we've parsed until now
                    xPathBuilder = new XPathBuilder();
                    return;
                }

            }

            @Override
            public void handleEndTag(final HTML.Tag t, final int pos) {

            }

            @Override
            public void handleSimpleTag(final HTML.Tag t, final MutableAttributeSet a, final int pos) {

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
