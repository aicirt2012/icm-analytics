package de.tum.in.icm.util;

import de.tum.in.icm.dtos.AnnotationDTO;
import de.tum.in.icm.dtos.NERInputDTO;
import de.tum.in.icm.dtos.NERResultDTO;
import de.tum.in.icm.dtos.NERType;
import de.tum.in.icm.entities.IndexedPlainText;

import java.util.ArrayList;
import java.util.List;

public class NERTestDataFactory {

    public enum Type {
        MINIMAL, SIMPLE, COMPLEX
    }

    public static NERInputDTO getNERInputDTO(Type type) {
        NERInputDTO nerInputDTO = new NERInputDTO();
        switch (type) {
            case MINIMAL:
                nerInputDTO.emailId = "Unit_test_postprocessor_minimal";
                nerInputDTO.htmlSource = "<div><h1>Test</h1>text Test</div><div>Te<b>st</b></div>";
                break;
            case SIMPLE:
                nerInputDTO.emailId = "Unit_test_postprocessor_simple";
                nerInputDTO.htmlSource = "<html><head></head><body><h1>Lorem ipsum</h1><p>dolor sit amet, consetetur sadipscing elitr, Google sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.</p><div><span>At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd Google gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus Google est Lorem ipsum dolor sit amet.</span></div><a href=\"www.some.url.containing.the.search.word/Google/index.html\">Google</a></body></html>";
                break;
            case COMPLEX:
                nerInputDTO.emailId = "Unit_test_postprocessor_complex";
                nerInputDTO.htmlSource = "<html><head></head><body><div><div><div><div><h1>Lorem ipsum</h1><table><tbody><tr><p></p><p>dolor sit amet, consetetur sadipscing elitr, Google sed diam nonumy eirmod tempor invidunt ut laboreetdolore magna aliquyam erat, sed diam voluptua.</p></tr><tr><div><span>At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd Google gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus Google est Lorem ipsum dolor sit amet.</span></div><a href=\"www.some.url.containing.the.search.word/Google/index.html\">Google</a></tr></tbody></table></div><div><span>This is <a>the</a> ugly Goo<i>gle</i>...</span></div></div></div></div></body></html>";
                break;
        }
        return nerInputDTO;
    }

    public static NERResultDTO getNERResultDTO(Type type) {
        NERResultDTO nerResultDTO = new NERResultDTO();
        List<AnnotationDTO> annotations = new ArrayList<>();
        switch (type) {
            case MINIMAL:
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
                for (int i = 0; i < 5; i++) {
                    AnnotationDTO annotationDTO = new AnnotationDTO();
                    annotationDTO.setValue("Google");
                    annotationDTO.setNerType(NERType.ORGANIZATION);
                    annotations.add(annotationDTO);
                }
                annotations.get(0).addPlainTextIndex(57);
                annotations.get(1).addPlainTextIndex(236);
                annotations.get(2).addPlainTextIndex(574);
                annotations.get(3).addPlainTextIndex(613);
                annotations.get(4).addPlainTextIndex(637);
                annotations.get(0).addHtmlSourceOccurrence(93, 45);
                annotations.get(1).addHtmlSourceOccurrence(265, 73);
                annotations.get(2).addHtmlSourceOccurrence(265, 411);
                annotations.get(3).addHtmlSourceOccurrence(795, 0);
                annotations.get(4).addHtmlSourceOccurrence(861, 6);
                annotations.get(0).addXPathRange("/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/p[2]", 45, "/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/p[2]", 51);
                annotations.get(1).addXPathRange("/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/div[1]/span[1]", 73, "/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/div[1]/span[1]", 79);
                annotations.get(2).addXPathRange("/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/div[1]/span[1]", 411, "/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/div[1]/span[1]", 417);
                annotations.get(3).addXPathRange("/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/a[1]", 0, "/div[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/a[1]", 6);
                annotations.get(4).addXPathRange("/div[1]/div[1]/div[2]/span[1]", 6, "/div[1]/div[1]/div[2]/span[1]/i[1]", 3);  //TODO check this in annotator framework!
                break;
        }
        nerResultDTO.addAnnotations(annotations);
        return nerResultDTO;
    }

    public static IndexedPlainText getIndexedPlainText(Type type) {
        IndexedPlainText indexedPlainText = new IndexedPlainText();
        switch (type) {
            case MINIMAL:
                indexedPlainText.addPlainText("Test", 9);
                indexedPlainText.addPlainText("\n", -1);
                indexedPlainText.addPlainText("text Test", 18);
                indexedPlainText.addPlainText("\n", -1);
                indexedPlainText.addPlainText("Te", 38);
                indexedPlainText.addPlainText("st", 43);
                break;
            case SIMPLE:
                indexedPlainText.addPlainText("Lorem ipsum", 29);
                indexedPlainText.addPlainText("\n", -1);
                indexedPlainText.addPlainText("dolor sit amet, consetetur sadipscing elitr, Google sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.", 48);
                indexedPlainText.addPlainText("\n", -1);
                indexedPlainText.addPlainText("At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd Google gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus Google est Lorem ipsum dolor sit amet.", 213);
                indexedPlainText.addPlainText("\n", -1);
                indexedPlainText.addPlainText("Google", 743);
                break;
            case COMPLEX:
                indexedPlainText.addPlainText("Lorem ipsum", 49);
                indexedPlainText.addPlainText("\n", -1);
                indexedPlainText.addPlainText("dolor sit amet, consetetur sadipscing elitr, Google sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.", 93);
                indexedPlainText.addPlainText("\n", -1);
                indexedPlainText.addPlainText("At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd Google gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus Google est Lorem ipsum dolor sit amet.", 265);
                indexedPlainText.addPlainText("\n", -1);
                indexedPlainText.addPlainText("Google", 795);
                indexedPlainText.addPlainText("\n", -1);
                indexedPlainText.addPlainText("This is ", 843);
                indexedPlainText.addPlainText("the", 854);
                indexedPlainText.addPlainText(" ugly Goo", 861);
                indexedPlainText.addPlainText("gle", 873);
                indexedPlainText.addPlainText("...", 880);
                break;
        }
        return indexedPlainText;
    }

}