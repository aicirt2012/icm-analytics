package de.tum.in.icm.util;

import de.tum.in.icm.dtos.AnnotationDTO;
import de.tum.in.icm.dtos.InputSourceDTO;
import de.tum.in.icm.dtos.ResultDTO;
import de.tum.in.icm.dtos.NERType;
import de.tum.in.icm.entities.TextNodeMap;
import de.tum.in.icm.entities.XPath;

import java.util.ArrayList;
import java.util.List;

public class NERTestDataFactory {

    public enum Type {
        MINIMAL, SIMPLE, COMPLEX, ELEMENT_COUNT, OFFSET_TEST
    }

    public static InputSourceDTO getHTMLSourceDTO(Type type) {
        InputSourceDTO inputSourceDTO = new InputSourceDTO();
        switch (type) {
            case MINIMAL:
                inputSourceDTO.setEmailId("Unit_test_postprocessor_minimal");
                inputSourceDTO.setBodySource("<div><h1>Test</h1>text Test</div><div>Te<b>st</b></div>");
                break;
            case SIMPLE:
                inputSourceDTO.setEmailId("Unit_test_postprocessor_simple");
                inputSourceDTO.setBodySource("<html><head></head><body><h1>Lorem ipsum</h1><p>dolor sit amet, consetetur sadipscing elitr, Google sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.</p><div><span>At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd Google gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus Google est Lorem ipsum dolor sit amet.</span></div><a href=\"www.some.url.containing.the.search.word/Google/index.html\">Google</a></body></html>");
                break;
            case COMPLEX:
                inputSourceDTO.setEmailId("Unit_test_postprocessor_complex");
                inputSourceDTO.setBodySource("<html><head></head><body><div><div><div><div><h1>Lorem ipsum</h1><table><tbody><tr><td><p></p><p>dolor sit amet, consetetur sadipscing elitr, Google sed diam nonumy eirmod tempor invidunt ut laboreetdolore magna aliquyam erat, sed diam voluptua.</p></td></tr><tr><td><div><span>At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd Google gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus Google est Lorem ipsum dolor sit amet.</span></div><a href=\"www.some.url.containing.the.search.word/Google/index.html\">Google</a></td></tr></tbody></table></div><div><span>This is <a>the</a> ugly Goo<i>gle</i>...</span></div></div></div></div><script src=\"annotator/annotator.custom-extensions.js\"></script><script src=\"annotator/annotator.js\" onload=\"annotatorCustomExtensions.initAnnotator();\"></script></body></html>");
                break;
            case OFFSET_TEST:
                inputSourceDTO.setEmailId("Unit_test_postprocessor_offset_test");
                inputSourceDTO.setBodySource("<div><h1>Caption1</h1>Google 1<h1>Caption2</h1>Google 2<p>Lorem ipsum dolor sit amet, consetetur Google sadipscing elitr, sed diam nonumy eirmod tempor.</p><h1>Caption3</h1>Google 4<span>Invidunt ut labore et dolore magna aliquyam erat, sed Google diam voluptua.</span><h1>Caption4</h1>Google 6<span>At vero eos et accusam <b>et justo Google</b> est.</span></div><script src=\"annotator/annotator.custom-extensions.js\"></script><script src=\"annotator/annotator.js\" onload=\"annotatorCustomExtensions.initAnnotator();\"></script>");
                break;
            case ELEMENT_COUNT:
                inputSourceDTO.setEmailId("Unit_test_postprocessor_element_count_test");
                inputSourceDTO.setBodySource("<div><p>Test</p><div>Test2</div><p>Test3</p></div>");
                break;
        }
        return inputSourceDTO;
    }

    public static ResultDTO getNERResultDTO(Type type) {
        ResultDTO nerResultDTO = new ResultDTO();
        List<AnnotationDTO> annotations = new ArrayList<>();
        switch (type) {
            case MINIMAL:
                nerResultDTO.setEmailId("Unit_test_postprocessor_minimal");
                for (int i = 0; i < 3; i++) {
                    AnnotationDTO annotationDTO = new AnnotationDTO();
                    annotationDTO.setValue("Test");
                    annotationDTO.setNerType(NERType.ORGANIZATION);
                    annotations.add(annotationDTO);
                }
                annotations.get(0).addPlainTextIndex(0);
                annotations.get(1).addPlainTextIndex(10);
                annotations.get(2).addPlainTextIndex(15);
                annotations.get(0).addHtmlSourceOccurrence(9, 0);
                annotations.get(1).addHtmlSourceOccurrence(18, 5);
                annotations.get(2).addHtmlSourceOccurrence(38, 0);
                annotations.get(0).addXPathRange("/div[1]/h1[1]", 0, "/div[1]/h1[1]", 4);
                annotations.get(1).addXPathRange("/div[1]", 5, "/div[1]", 9);
                annotations.get(2).addXPathRange("/div[2]", 0, "/div[2]/b[1]", 2);
                break;
            case SIMPLE:
                nerResultDTO.setEmailId("Unit_test_postprocessor_simple");
                for (int i = 0; i < 4; i++) {
                    AnnotationDTO annotationDTO = new AnnotationDTO();
                    annotationDTO.setValue("Google");
                    annotationDTO.setNerType(NERType.ORGANIZATION);
                    annotations.add(annotationDTO);
                }
                annotations.get(0).addPlainTextIndex(57);
                annotations.get(1).addPlainTextIndex(236);
                annotations.get(2).addPlainTextIndex(574);
                annotations.get(3).addPlainTextIndex(613);
                annotations.get(0).addHtmlSourceOccurrence(48, 45);
                annotations.get(1).addHtmlSourceOccurrence(213, 73);
                annotations.get(2).addHtmlSourceOccurrence(213, 411);
                annotations.get(3).addHtmlSourceOccurrence(743, 0);
                annotations.get(0).addXPathRange("/p[1]", 45, "/p[1]", 51);
                annotations.get(1).addXPathRange("/div[1]/span[1]", 73, "/div[1]/span[1]", 79);
                annotations.get(2).addXPathRange("/div[1]/span[1]", 411, "/div[1]/span[1]", 417);
                annotations.get(3).addXPathRange("/a[1]", 0, "/a[1]", 6);
                break;
            case COMPLEX:
                nerResultDTO.setEmailId("Unit_test_postprocessor_complex");
                for (int i = 0; i < 5; i++) {
                    AnnotationDTO annotationDTO = new AnnotationDTO();
                    annotationDTO.setValue("Google");
                    annotationDTO.setNerType(NERType.ORGANIZATION);
                    annotations.add(annotationDTO);
                }
                annotations.get(0).addPlainTextIndex(57);
                annotations.get(1).addPlainTextIndex(234);
                annotations.get(2).addPlainTextIndex(572);
                annotations.get(3).addPlainTextIndex(611);
                annotations.get(4).addPlainTextIndex(635);
                annotations.get(0).addHtmlSourceOccurrence(97, 45);
                annotations.get(1).addHtmlSourceOccurrence(278, 73);
                annotations.get(2).addHtmlSourceOccurrence(278, 411);
                annotations.get(3).addHtmlSourceOccurrence(808, 0);
                annotations.get(4).addHtmlSourceOccurrence(879, 6);
                annotations.get(0).addXPathRange("/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/p[2]", 45, "/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/p[2]", 51);
                annotations.get(1).addXPathRange("/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/span[1]", 73, "/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/span[1]", 79);
                annotations.get(2).addXPathRange("/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/span[1]", 411, "/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/span[1]", 417);
                annotations.get(3).addXPathRange("/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]/a[1]", 0, "/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]/a[1]", 6);
                annotations.get(4).addXPathRange("/div[1]/div[1]/div[1]/div[2]/span[1]", 17, "/div[1]/div[1]/div[1]/div[2]/span[1]/i[1]", 3);  //TODO check this in annotator framework!
                break;
        }
        nerResultDTO.addAnnotations(annotations);
        return nerResultDTO;
    }

    public static TextNodeMap getTextNodeMap(Type type) {
        TextNodeMap textNodeMap = new TextNodeMap();
        switch (type) {
            case MINIMAL:
                textNodeMap.add("Test", asXPath("/div[1]/h1[1]"), 0);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("text Test", asXPath("/div[1]"), 4);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("Te", asXPath("/div[2]"), 0);
                textNodeMap.add("st", asXPath("/div[2]/b[1]"), 0);
                textNodeMap.add("\n", new XPath(), 0);
                break;
            case SIMPLE:
                textNodeMap.add("Lorem ipsum", asXPath("/h1[1]"), 0);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("dolor sit amet, consetetur sadipscing elitr, Google sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.", asXPath("/p[1]"), 0);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd Google gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus Google est Lorem ipsum dolor sit amet.", asXPath("/div[1]/span[1]"), 0);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("Google", asXPath("/a[1]"), 0);
                break;
            case COMPLEX:
                textNodeMap.add("Lorem ipsum", asXPath("/div[1]/div[1]/div[1]/div[1]/h1[1]"), 0);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("dolor sit amet, consetetur sadipscing elitr, Google sed diam nonumy eirmod tempor invidunt ut laboreetdolore magna aliquyam erat, sed diam voluptua.", asXPath("/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/p[2]"), 0);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd Google gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus Google est Lorem ipsum dolor sit amet.", asXPath("/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]/div[1]/span[1]"), 0);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("Google", asXPath("/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]/a[1]"), 0);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("This is ", asXPath("/div[1]/div[1]/div[1]/div[2]/span[1]"), 0);
                textNodeMap.add("the", asXPath("/div[1]/div[1]/div[1]/div[2]/span[1]/a[1]"), 0);
                textNodeMap.add(" ugly Goo", asXPath("/div[1]/div[1]/div[1]/div[2]/span[1]"), 11);
                textNodeMap.add("gle", asXPath("/div[1]/div[1]/div[1]/div[2]/span[1]/i[1]"), 0);
                textNodeMap.add("...", asXPath("/div[1]/div[1]/div[1]/div[2]/span[1]"), 23);
                textNodeMap.add("\n", new XPath(), 0);
                break;
            case OFFSET_TEST:
                textNodeMap.add("Caption1", asXPath("/div[1]/h1[1]"), 0);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("Google 1", asXPath("/div[1]"), 8);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("Caption2", asXPath("/div[1]/h1[2]"), 0);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("Google 2", asXPath("/div[1]"), 24);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("Lorem ipsum dolor sit amet, consetetur Google sadipscing elitr, sed diam nonumy eirmod tempor.", asXPath("/div[1]/p[1]"), 0);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("Caption3", asXPath("/div[1]/h1[3]"), 0);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("Google 4", asXPath("/div[1]"), 134);
                textNodeMap.add("Invidunt ut labore et dolore magna aliquyam erat, sed Google diam voluptua.", asXPath("/div[1]/span[1]"), 0);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("Caption4", asXPath("/div[1]/h1[4]"), 0);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("Google 6", asXPath("/div[1]"), 225);
                textNodeMap.add("At vero eos et accusam ", asXPath("/div[1]/span[2]"), 0);
                textNodeMap.add("et justo Google", asXPath("/div[1]/span[2]/b[1]"), 0);
                textNodeMap.add(" est.", asXPath("/div[1]/span[2]"), 38);
                textNodeMap.add("\n", new XPath(), 0);
                break;
            case ELEMENT_COUNT:
                textNodeMap.add("Test", asXPath("/div[1]/p[1]"), 0);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("Test2", asXPath("/div[1]/div[1]"), 0);
                textNodeMap.add("\n", new XPath(), 0);
                textNodeMap.add("Test3", asXPath("/div[1]/p[2]"), 0);
                textNodeMap.add("\n", new XPath(), 0);
                break;
        }
        return textNodeMap;
    }

    private static XPath asXPath(String value) {
        XPath xPath = new XPath();
        for (String subPath : value.split("/")) {
            if (subPath.isEmpty())
                continue;
            String[] tagAndCount = subPath.split("\\[");
            xPath.add(tagAndCount[0], Integer.valueOf(tagAndCount[1].replace("]", "")));
        }
        return xPath;
    }

}
