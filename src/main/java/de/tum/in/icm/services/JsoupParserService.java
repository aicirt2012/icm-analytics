package de.tum.in.icm.services;

import de.tum.in.icm.entities.TextNodeMap;
import de.tum.in.icm.entities.XPathBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeVisitor;

public class JsoupParserService {

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
            } else if (node instanceof TextNode) {
                TextNode textNode = (TextNode) node;
                int parentOffset = 0;
                if (textNode.siblingIndex() != 0) {
                    Node previousSibling = textNode.previousSibling();
                    while (previousSibling != null) {
                        parentOffset += getSourceLength(previousSibling);
                        previousSibling = previousSibling.previousSibling();
                    }
                }
                textNodeMap.add(textNode.text(), xPathBuilder.toXPath(), parentOffset);
            }
        }

        private int getSourceLength(Node node) {
            return node.outerHtml().length();
        }

        @Override
        public void tail(Node node, int depth) {
            if (node instanceof Element) {
                Element element = (Element) node;
                xPathBuilder.addClosingTag(element.tagName());
            }
        }

    }

}
