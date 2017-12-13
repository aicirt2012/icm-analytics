package de.tum.in.icm.services;

import de.tum.in.icm.dtos.NERInputDTO;
import de.tum.in.icm.entities.TextNodeMap;
import de.tum.in.icm.util.NERTestDataFactory;
import org.junit.Assert;
import org.junit.Test;

public class JsoupParserServiceTest {

    @Test
    public void parseHtmlMinimal() {
        NERInputDTO inputDTO = NERTestDataFactory.getNERInputDTO(NERTestDataFactory.Type.MINIMAL);
        TextNodeMap actualResult = JsoupParserService.getTextNodes(inputDTO.getHtmlSource());
        TextNodeMap expectedResult = NERTestDataFactory.getTextNodeMap(NERTestDataFactory.Type.MINIMAL);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void parseHtmlSimple() {
        NERInputDTO inputDTO = NERTestDataFactory.getNERInputDTO(NERTestDataFactory.Type.SIMPLE);
        TextNodeMap actualResult = JsoupParserService.getTextNodes(inputDTO.getHtmlSource());
        TextNodeMap expectedResult = NERTestDataFactory.getTextNodeMap(NERTestDataFactory.Type.SIMPLE);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void parseHtmlComplex() {
        NERInputDTO inputDTO = NERTestDataFactory.getNERInputDTO(NERTestDataFactory.Type.COMPLEX);
        TextNodeMap actualResult = JsoupParserService.getTextNodes(inputDTO.getHtmlSource());
        TextNodeMap expectedResult = NERTestDataFactory.getTextNodeMap(NERTestDataFactory.Type.COMPLEX);
        Assert.assertEquals(expectedResult, actualResult);
    }

}