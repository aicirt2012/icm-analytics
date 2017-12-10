package de.tum.in.icm.services;

import de.tum.in.icm.dtos.AnnotationDTO;
import de.tum.in.icm.dtos.NERInputDTO;
import de.tum.in.icm.dtos.NERResultDTO;
import de.tum.in.icm.entities.IndexedPlainText;
import de.tum.in.icm.util.NERTestDataFactory;
import org.junit.Assert;
import org.junit.Test;

public class NERPostProcessorServiceTest {

    @Test
    public void calculateHtmlIndicesMinimal() {
        NERTestDataFactory.Type type = NERTestDataFactory.Type.MINIMAL;
        NERResultDTO expectedNERResultDTO = NERTestDataFactory.getNERResultDTO(type);
        NERResultDTO actualNERResultDTO = calculateHtmlIndices(type);
        Assert.assertEquals(expectedNERResultDTO.getAnnotations(), actualNERResultDTO.getAnnotations());
    }

    @Test
    public void calculateHtmlIndicesSimple() {
        NERTestDataFactory.Type type = NERTestDataFactory.Type.SIMPLE;
        NERResultDTO expectedNERResultDTO = NERTestDataFactory.getNERResultDTO(type);
        NERResultDTO actualNERResultDTO = calculateHtmlIndices(type);
        Assert.assertEquals(expectedNERResultDTO.getAnnotations(), actualNERResultDTO.getAnnotations());
    }

    @Test
    public void calculateHtmlIndicesComplex() {
        NERTestDataFactory.Type type = NERTestDataFactory.Type.COMPLEX;
        NERResultDTO expectedNERResultDTO = NERTestDataFactory.getNERResultDTO(type);
        NERResultDTO actualNERResultDTO = calculateHtmlIndices(type);
        Assert.assertEquals(expectedNERResultDTO.getAnnotations(), actualNERResultDTO.getAnnotations());
    }

    private NERResultDTO calculateHtmlIndices(NERTestDataFactory.Type type) {
        NERResultDTO nerResultDTO = NERTestDataFactory.getNERResultDTO(type);
        IndexedPlainText indexedPlainText = NERTestDataFactory.getIndexedPlainText(type);
        for (AnnotationDTO annotationDTO : nerResultDTO.getAnnotations()) {
            annotationDTO.getHtmlTextNodeIndices().clear();
            annotationDTO.getHtmlAnnotationOffsets().clear();
        }
        return NERPostProcessorService.calculateHtmlIndices(nerResultDTO, indexedPlainText);
    }

    @Test
    public void calculateRangeObjectsSimple() {
        NERTestDataFactory.Type type = NERTestDataFactory.Type.SIMPLE;
        NERResultDTO expectedNERResultDTO = NERTestDataFactory.getNERResultDTO(type);
        NERResultDTO actualNERResultDTO = calculateRangeObjects(type);
        Assert.assertEquals(expectedNERResultDTO.getAnnotations(), actualNERResultDTO.getAnnotations());
    }

    @Test
    public void calculateRangeObjectsComplex() {
        NERTestDataFactory.Type type = NERTestDataFactory.Type.COMPLEX;
        NERResultDTO expectedNERResultDTO = NERTestDataFactory.getNERResultDTO(type);
        NERResultDTO actualNERResultDTO = calculateRangeObjects(type);
        Assert.assertEquals(expectedNERResultDTO.getAnnotations(), actualNERResultDTO.getAnnotations());
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