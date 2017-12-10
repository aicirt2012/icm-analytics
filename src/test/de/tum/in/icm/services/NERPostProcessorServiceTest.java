package de.tum.in.icm.services;

import de.tum.in.icm.dtos.AnnotationDTO;
import de.tum.in.icm.dtos.NERInputDTO;
import de.tum.in.icm.dtos.NERResultDTO;
import de.tum.in.icm.dtos.NERType;
import de.tum.in.icm.entities.IndexedPlainText;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NERPostProcessorServiceTest {

    private NERInputDTO nerInputDTOSimple = new NERInputDTO();
    private IndexedPlainText indexedPlainTextSimple = new IndexedPlainText();
    private NERResultDTO nerResultDTOSimple = new NERResultDTO();

    private NERInputDTO nerInputDTOComplex = new NERInputDTO();
    private IndexedPlainText indexedPlainTextComplex = new IndexedPlainText();
    private NERResultDTO nerResultDTOComplex = new NERResultDTO();

    @Before
    public void setUp() {
        setUpSimpleExample();
        setUpComplexExample();
    }

    private void setUpSimpleExample() {
        nerInputDTOSimple.emailId = "Unit_test_postprocessor_simple";
        nerInputDTOSimple.htmlSource = "<html><head></head><body><h1>Lorem ipsum</h1><p>dolor sit amet, consetetur sadipscing elitr, Google sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.</p><div><span>At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd Google gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus Google est Lorem ipsum dolor sit amet.</span></div><a href=\"www.some.url.containing.the.search.word/Google/index.html\">Google</a></body></html>";
        indexedPlainTextSimple.addPlainText("Lorem ipsum", 29);
        indexedPlainTextSimple.addPlainText("\n", -1);
        indexedPlainTextSimple.addPlainText("dolor sit amet, consetetur sadipscing elitr, Google sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.", 48);
        indexedPlainTextSimple.addPlainText("\n", -1);
        indexedPlainTextSimple.addPlainText("At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd Google gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus Google est Lorem ipsum dolor sit amet.", 213);
        indexedPlainTextSimple.addPlainText("\n", -1);
        indexedPlainTextSimple.addPlainText("Google", 749);
        List<AnnotationDTO> annotations = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            AnnotationDTO annotationDTO = new AnnotationDTO();
            annotationDTO.setValue("Google");
            annotationDTO.setNerType(NERType.ORGANIZATION);
            annotations.add(annotationDTO);
        }
        annotations.get(0).addPlainTextIndex(56);
        annotations.get(0).addHtmlSourceOccurrence(48, -1);
        annotations.get(1).addPlainTextIndex(234);
        annotations.get(1).addHtmlSourceOccurrence(213, -1);
        annotations.get(2).addPlainTextIndex(574);
        annotations.get(2).addHtmlSourceOccurrence(213, -1);
        annotations.get(3).addPlainTextIndex(613);
        annotations.get(3).addHtmlSourceOccurrence(749, -1);
        nerResultDTOSimple.addAnnotations(annotations);
    }

    private void setUpComplexExample() {
        nerInputDTOComplex.emailId = "Unit_test_postprocessor_complex";
        nerInputDTOComplex.htmlSource = "<html><head></head><body><div><div><div><div><h1>Lorem ipsum</h1><table><tbody><tr><p></p><p>dolor sit amet, consetetur sadipscing elitr, Google sed diam nonumy eirmod tempor invidunt ut laboreetdolore magna aliquyam erat, sed diam voluptua.</p></tr><tr><div><span>At vero eos et accusam et justo duo dolores et ea rebum.Stet clita kasd Google gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.Lorem ipsum dolor sit amet, consetetur sadipscing elitr,sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.Stet clita kasd gubergren, no sea takimata sanctus Google est Lorem ipsum dolor sit amet.</span></div><a href=\"www.some.url.containing.the.search.word/Google/index.html\">Google</a></tr></tbody></table></div><div><span>This is <a>the</a> ugly Goo<i>gle</i>...</span></div></div></div></div></body></html>";
        indexedPlainTextComplex.addPlainText("Lorem ipsum", 49);
        indexedPlainTextSimple.addPlainText("\n", -1);
        indexedPlainTextComplex.addPlainText("dolor sit amet, consetetur sadipscing elitr, Google sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.", 93);
        indexedPlainTextSimple.addPlainText("\n", -1);
        indexedPlainTextComplex.addPlainText("At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd Google gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus Google est Lorem ipsum dolor sit amet.", 265);
        indexedPlainTextSimple.addPlainText("\n", -1);
        indexedPlainTextComplex.addPlainText("Google", 796);
        indexedPlainTextSimple.addPlainText("\n", -1);
        indexedPlainTextComplex.addPlainText("This is ", 844);
        indexedPlainTextComplex.addPlainText("the", 855);
        indexedPlainTextComplex.addPlainText(" ugly Goo", 862);
        indexedPlainTextComplex.addPlainText("gle", 874);
        indexedPlainTextComplex.addPlainText("...", 881);
        List<AnnotationDTO> annotations = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AnnotationDTO annotationDTO = new AnnotationDTO();
            annotationDTO.setValue("Google");
            annotationDTO.setNerType(NERType.ORGANIZATION);
            annotations.add(annotationDTO);
        }
        annotations.get(0).addPlainTextIndex(57);
        annotations.get(1).addPlainTextIndex(233);
        annotations.get(2).addPlainTextIndex(567);
        annotations.get(3).addPlainTextIndex(606);
        annotations.get(4).addPlainTextIndex(630);
        nerResultDTOComplex.addAnnotations(annotations);
    }

    @Test
    public void calculateHtmlIndicesSimple() {
        for (AnnotationDTO annotationDTO : nerResultDTOSimple.getAnnotations()) {
            annotationDTO.getHtmlTextNodeIndices().clear();
            annotationDTO.getHtmlAnnotationOffsets().clear();
        }
        nerResultDTOSimple = NERPostProcessorService.calculateHtmlIndices(nerResultDTOSimple, nerInputDTOSimple.htmlSource, indexedPlainTextSimple);
        Assert.assertNotNull(nerResultDTOSimple);
        checkHtmlIndices(nerResultDTOSimple.getAnnotations());
    }

    @Test
    public void calculateHtmlIndicesComplex() {
        for (AnnotationDTO annotationDTO : nerResultDTOComplex.getAnnotations()) {
            annotationDTO.getHtmlTextNodeIndices().clear();
            annotationDTO.getHtmlAnnotationOffsets().clear();
        }
        nerResultDTOComplex = NERPostProcessorService.calculateHtmlIndices(nerResultDTOComplex, nerInputDTOComplex.htmlSource, indexedPlainTextComplex);
        Assert.assertNotNull(nerResultDTOComplex);
        checkHtmlIndices(nerResultDTOComplex.getAnnotations());
    }

    private void checkHtmlIndices(List<AnnotationDTO> annotations) {
        for (AnnotationDTO annotationDTO : annotations) {
            Assert.assertNotNull(annotationDTO.getHtmlTextNodeIndices());
            Assert.assertFalse(annotationDTO.getHtmlTextNodeIndices().isEmpty());
            Assert.assertNotNull(annotationDTO.getHtmlAnnotationOffsets());
            Assert.assertFalse(annotationDTO.getHtmlAnnotationOffsets().isEmpty());
        }
    }

    @Test
    public void calculateRangeObjectsSimple() {
        nerResultDTOSimple = NERPostProcessorService.calculateRangeObjects(nerResultDTOSimple, nerInputDTOSimple.htmlSource);
        Assert.assertNotNull(nerResultDTOSimple);
    }

    @Test
    public void calculateRangeObjectsComplex() {
        nerResultDTOComplex = NERPostProcessorService.calculateRangeObjects(nerResultDTOComplex, nerInputDTOComplex.htmlSource);
        Assert.assertNotNull(nerResultDTOComplex);
    }

}