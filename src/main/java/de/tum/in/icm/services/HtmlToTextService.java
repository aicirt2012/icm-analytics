package de.tum.in.icm.services;

import de.tum.in.icm.entities.IndexedPlainText;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

public class HtmlToTextService extends HTMLEditorKit.ParserCallback {

    private static final List<HTML.Tag> BREAKING_HTML_TAGS = Arrays.asList(
            HTML.Tag.BR, HTML.Tag.DD, HTML.Tag.DT, HTML.Tag.P, HTML.Tag.H1, HTML.Tag.H2, HTML.Tag.H3, HTML.Tag.H4, HTML.Tag.H5);

    public static IndexedPlainText stripHtmlTags(String htmlSource) {
        final StringBuilder sb = new StringBuilder();

        HTMLEditorKit.ParserCallback parserCallback = new HTMLEditorKit.ParserCallback() {
            private boolean readyForNewline;

            @Override
            public void handleText(final char[] data, final int pos) {
                String s = new String(data);
                sb.append(s.trim());
                readyForNewline = true;
            }

            @Override
            public void handleStartTag(final HTML.Tag t, final MutableAttributeSet a, final int pos) {
                if (readyForNewline && BREAKING_HTML_TAGS.contains(t)) {
                    sb.append("\n");
                    readyForNewline = false;
                }
            }

            @Override
            public void handleSimpleTag(final HTML.Tag t, final MutableAttributeSet a, final int pos) {
                handleStartTag(t, a, pos);
            }
        };
        try {
            new ParserDelegator().parse(new StringReader(htmlSource), parserCallback, false);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        IndexedPlainText indexedPlainText = new IndexedPlainText();
        indexedPlainText.addPlainText(sb.toString());
        return indexedPlainText;
    }

}
