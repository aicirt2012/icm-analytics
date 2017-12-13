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
        TextNodeMap result = JsoupParserService.getTextNodes(inputDTO.getHtmlSource());
        Assert.assertNotNull(result);
    }

    @Test
    public void parseHtmlSimple() {
        NERInputDTO inputDTO = NERTestDataFactory.getNERInputDTO(NERTestDataFactory.Type.SIMPLE);
        TextNodeMap result = JsoupParserService.getTextNodes(inputDTO.getHtmlSource());
        Assert.assertNotNull(result);
    }

    @Test
    public void parseHtmlComplex() {
        NERInputDTO inputDTO = NERTestDataFactory.getNERInputDTO(NERTestDataFactory.Type.COMPLEX);
        TextNodeMap result = JsoupParserService.getTextNodes(inputDTO.getHtmlSource());
        Assert.assertNotNull(result);
    }

}