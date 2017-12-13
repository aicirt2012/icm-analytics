package de.tum.in.icm.services;

import de.tum.in.icm.entities.TextNodeMap;
import de.tum.in.icm.entities.XPathBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public class JsoupParserService {

    private static final TextNodeMapFactory textNodeMapFactory = new TextNodeMapFactory();
    private static TextNodeMap textNodeMap;
    private static XPathBuilder xPathBuilder;

    public static TextNodeMap getTextNodes(String html) {
        textNodeMap = new TextNodeMap();
        xPathBuilder = new XPathBuilder();
        Document document = Jsoup.parse(html);
        NodeTraversor.traverse(textNodeMapFactory, document.body());
        return textNodeMap;
    }

    private static class TextNodeMapFactory implements NodeVisitor {

        @Override
        public void head(Node node, int depth) {
            if (node instanceof Element) {
                Element element = (Element) node;
                xPathBuilder.addOpeningTag(element.tagName());
                if (element.tagName().equals("body")) {
                    // xpaths we want are relative to body, drop everything we got until now
                    xPathBuilder = new XPathBuilder();
                }
            } else if (node instanceof TextNode) {
                TextNode textNode = (TextNode) node;
                textNodeMap.add(textNode.text(), xPathBuilder.toXPath(), -1);   // TODO handle parent offsets
            } else {
                // TODO handle other node types
                return;
            }
        }

        @Override
        public void tail(Node node, int depth) {
            if (node instanceof Element) {
                Element element = (Element) node;
                xPathBuilder.addClosingTag(element.tagName());
            } else {
                // TODO handle other node types
                return;
            }
        }
    }

}
