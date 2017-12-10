package de.tum.in.icm.services;

import de.tum.in.icm.dtos.AnnotationDTO;
import de.tum.in.icm.dtos.NERInputDTO;
import de.tum.in.icm.dtos.NERResultDTO;
import de.tum.in.icm.entities.IndexedPlainText;
import de.tum.in.icm.util.NERTestDataFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class NERPostProcessorServiceTest {

    @Test
    public void calculateHtmlIndicesMinimal() {
        NERResultDTO nerResultDTO = calculateHtmlIndices(NERTestDataFactory.Type.MINIMAL);
        checkHtmlIndices(nerResultDTO.getAnnotations());
    }

    @Test
    public void calculateHtmlIndicesSimple() {
        NERResultDTO nerResultDTO = calculateHtmlIndices(NERTestDataFactory.Type.SIMPLE);
        checkHtmlIndices(nerResultDTO.getAnnotations());
    }

    @Test
    public void calculateHtmlIndicesComplex() {
        NERResultDTO nerResultDTO = calculateHtmlIndices(NERTestDataFactory.Type.COMPLEX);
        checkHtmlIndices(nerResultDTO.getAnnotations());
    }

    private NERResultDTO calculateHtmlIndices(NERTestDataFactory.Type type) {
        NERResultDTO nerResultDTO = NERTestDataFactory.getNERResultDTO(type);
        NERInputDTO nerInputDTO = NERTestDataFactory.getNERInputDTO(type);
        IndexedPlainText indexedPlainText = NERTestDataFactory.getIndexedPlainText(type);
        for (AnnotationDTO annotationDTO : nerResultDTO.getAnnotations()) {
            annotationDTO.getHtmlTextNodeIndices().clear();
            annotationDTO.getHtmlAnnotationOffsets().clear();
        }
        return NERPostProcessorService.calculateHtmlIndices(nerResultDTO, nerInputDTO.htmlSource, indexedPlainText);
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
        NERResultDTO nerResultDTO = calculateRangeObjects(NERTestDataFactory.Type.SIMPLE);
        Assert.assertNotNull(nerResultDTO);
    }

    @Test
    public void calculateRangeObjectsComplex() {
        NERResultDTO nerResultDTO = calculateRangeObjects(NERTestDataFactory.Type.COMPLEX);
        Assert.assertNotNull(nerResultDTO);
    }

    private NERResultDTO calculateRangeObjects(NERTestDataFactory.Type type) {
        NERResultDTO nerResultDTO = NERTestDataFactory.getNERResultDTO(type);
        NERInputDTO nerInputDTO = NERTestDataFactory.getNERInputDTO(type);
        for (AnnotationDTO annotationDTO : nerResultDTO.getAnnotations()) {
            annotationDTO.getRanges().clear();
        }
        return NERPostProcessorService.calculateRangeObjects(nerResultDTO, nerInputDTO.htmlSource);
    }

}