package de.tum.in.icm.services;

import de.tum.in.icm.dtos.AnnotationDTO;
import de.tum.in.icm.dtos.ResultDTO;
import de.tum.in.icm.entities.TextNodeMap;
import de.tum.in.icm.util.NERTestDataFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.ListIterator;

public class NERPostProcessorServiceTest {

    @Test
    public void calculateRangeObjectsMinimal() {
        doCalculateRangeObjects(NERTestDataFactory.Type.MINIMAL);
    }

    @Test
    public void calculateRangeObjectsSimple() {
        doCalculateRangeObjects(NERTestDataFactory.Type.SIMPLE);
    }

    @Test
    public void calculateRangeObjectsComplex() {
        doCalculateRangeObjects(NERTestDataFactory.Type.COMPLEX);
    }

    @Test
    public void calculateRangeObjectsOffsetTest() {
        doCalculateRangeObjects(NERTestDataFactory.Type.OFFSET_TEST);
    }

    @Test
    public void calculateRangeObjectsElementCount() {
        doCalculateRangeObjects(NERTestDataFactory.Type.ELEMENT_COUNT);
    }

    private void doCalculateRangeObjects(NERTestDataFactory.Type type) {
        ResultDTO nerResultDTO = NERTestDataFactory.getNERResultDTO(type);
        TextNodeMap textNodeMap = NERTestDataFactory.getTextNodeMap(type);
        for (AnnotationDTO annotationDTO : nerResultDTO.getAnnotations()) {
            annotationDTO.getRanges().clear();
        }
        ResultDTO actualResult = NERPostProcessorService.calculateRanges(nerResultDTO, textNodeMap);
        ResultDTO expectedResult = NERTestDataFactory.getNERResultDTO(type);
        ListIterator<AnnotationDTO> actualAnnotationsIterator = actualResult.getAnnotations().listIterator();
        ListIterator<AnnotationDTO> expectedAnnotationsIterator = expectedResult.getAnnotations().listIterator();
        while (expectedAnnotationsIterator.hasNext() || actualAnnotationsIterator.hasNext()) {
            Assert.assertEquals(expectedAnnotationsIterator.next(), actualAnnotationsIterator.next());
        }
    }
}