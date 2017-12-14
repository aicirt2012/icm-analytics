package de.tum.in.icm.services;

import de.tum.in.icm.entities.TextNodeMap;
import de.tum.in.icm.entities.XPath;
import de.tum.in.icm.entities.XPathBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeVisitor;

import java.util.Arrays;
import java.util.List;

public class NERPreProcessorService {

    private static final List<String> BREAKING_HTML_TAGS = Arrays.asList("br", "dd", "div", "dt", "h1", "h2", "h3", "h4", "h5", "p", "tr");

    private static final TextNodeMapFactory textNodeMapFactory = new TextNodeMapFactory();
    private static TextNodeMap textNodeMap;
    private static XPathBuilder xPathBuilder;

    public static TextNodeMap getTextNodeMap(String html) {
        textNodeMap = new TextNodeMap();
        xPathBuilder = new XPathBuilder();
        Document document = Jsoup.parse(html);
        document.body().childNodes().forEach(node -> node.traverse(textNodeMapFactory));  // parse all children of body
        return textNodeMap;
    }

    private static class TextNodeMapFactory implements NodeVisitor {

        @Override
        public void head(Node node, int depth) {
            if (node instanceof Element) {
                Element element = (Element) node;
                xPathBuilder.addOpeningTag(element.tagName());
                this.considerNewLine(element);
            } else if (node instanceof TextNode) {
                TextNode textNode = (TextNode) node;
                int parentOffset = 0;
                if (textNode.siblingIndex() != 0) {
                    Node previousSibling = textNode.previousSibling();
                    while (previousSibling != null) {
                        parentOffset += getTextOffset(previousSibling);
                        previousSibling = previousSibling.previousSibling();
                    }
                }
                textNodeMap.add(textNode.text(), xPathBuilder.toXPath(), parentOffset);
            }
        }

        private int getTextOffset(Node node) {
            if (node instanceof Element)
                return ((Element) node).text().length();
            if (node instanceof TextNode)
                return ((TextNode) node).text().length();
            return 0;
        }

        @Override
        public void tail(Node node, int depth) {
            if (node instanceof Element) {
                Element element = (Element) node;
                xPathBuilder.addClosingTag(element.tagName());
                this.considerNewLine(element);
            }
        }

        private void considerNewLine(Element element) {
            if (BREAKING_HTML_TAGS.contains(element.tagName())) {
                if (!textNodeMap.getValues().isEmpty() && !textNodeMap.lastTagEquals("\n")) {
                    textNodeMap.add("\n", new XPath(), 0);
                }
            }
        }

    }

}
