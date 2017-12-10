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
        NERResultDTO actualNERResultDTO = calculateHtmlIndices(type);
        NERResultDTO expectedNERResultDTO = NERTestDataFactory.getNERResultDTO(type);
        Assert.assertEquals(expectedNERResultDTO.getAnnotations(), actualNERResultDTO.getAnnotations());
    }

    @Test
    public void calculateHtmlIndicesSimple() {
        NERTestDataFactory.Type type = NERTestDataFactory.Type.SIMPLE;
        NERResultDTO actualNERResultDTO = calculateHtmlIndices(type);
        NERResultDTO expectedNERResultDTO = NERTestDataFactory.getNERResultDTO(type);
        Assert.assertEquals(expectedNERResultDTO.getAnnotations(), actualNERResultDTO.getAnnotations());
    }

    @Test
    public void calculateHtmlIndicesComplex() {
        NERTestDataFactory.Type type = NERTestDataFactory.Type.COMPLEX;
        NERResultDTO actualNERResultDTO = calculateHtmlIndices(type);
        NERResultDTO expectedNERResultDTO = NERTestDataFactory.getNERResultDTO(type);
        Assert.assertEquals(expectedNERResultDTO.getAnnotations(), actualNERResultDTO.getAnnotations());
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