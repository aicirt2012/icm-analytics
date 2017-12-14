package de.tum.in.icm.services;

import de.tum.in.icm.dtos.NERInputDTO;
import de.tum.in.icm.entities.TextNodeMap;
import de.tum.in.icm.entities.XPath;
import de.tum.in.icm.util.NERTestDataFactory;
import org.junit.Test;

import java.util.ListIterator;

import static org.junit.Assert.assertEquals;

public class JsoupParserServiceTest {

    @Test
    public void parseHtmlMinimal() {
        NERInputDTO inputDTO = NERTestDataFactory.getNERInputDTO(NERTestDataFactory.Type.MINIMAL);
        TextNodeMap actualResult = JsoupParserService.getTextNodeMap(inputDTO.getHtmlSource());
        TextNodeMap expectedResult = NERTestDataFactory.getTextNodeMap(NERTestDataFactory.Type.MINIMAL);
        this.checkForEquality(expectedResult, actualResult);
    }

    @Test
    public void parseHtmlSimple() {
        NERInputDTO inputDTO = NERTestDataFactory.getNERInputDTO(NERTestDataFactory.Type.SIMPLE);
        TextNodeMap actualResult = JsoupParserService.getTextNodeMap(inputDTO.getHtmlSource());
        TextNodeMap expectedResult = NERTestDataFactory.getTextNodeMap(NERTestDataFactory.Type.SIMPLE);
        this.checkForEquality(expectedResult, actualResult);
    }

    @Test
    public void parseHtmlComplex() {
        NERInputDTO inputDTO = NERTestDataFactory.getNERInputDTO(NERTestDataFactory.Type.COMPLEX);
        TextNodeMap actualResult = JsoupParserService.getTextNodeMap(inputDTO.getHtmlSource());
        TextNodeMap expectedResult = NERTestDataFactory.getTextNodeMap(NERTestDataFactory.Type.COMPLEX);
        this.checkForEquality(expectedResult, actualResult);
    }

    @Test
    public void parseHtmlOffset() {
        NERInputDTO inputDTO = NERTestDataFactory.getNERInputDTO(NERTestDataFactory.Type.OFFSET_TEST);
        TextNodeMap actualResult = JsoupParserService.getTextNodeMap(inputDTO.getHtmlSource());
        TextNodeMap expectedResult = NERTestDataFactory.getTextNodeMap(NERTestDataFactory.Type.OFFSET_TEST);
        this.checkForEquality(expectedResult, actualResult);
    }

    @Test
    public void parseHtmlElementCount() {
        NERInputDTO inputDTO = NERTestDataFactory.getNERInputDTO(NERTestDataFactory.Type.ELEMENT_COUNT);
        TextNodeMap actualResult = JsoupParserService.getTextNodeMap(inputDTO.getHtmlSource());
        TextNodeMap expectedResult = NERTestDataFactory.getTextNodeMap(NERTestDataFactory.Type.ELEMENT_COUNT);
        this.checkForEquality(expectedResult, actualResult);
    }

    private void checkForEquality(TextNodeMap expected, TextNodeMap actual) {
        ListIterator<String> expectedValueIterator = expected.getValues().listIterator();
        ListIterator<XPath> expectedParentLocatorIterator = expected.getParentLocators().listIterator();
        ListIterator<Integer> expectedParentOffsetIterator = expected.getParentOffsets().listIterator();
        ListIterator<String> actualValueIterator = actual.getValues().listIterator();
        ListIterator<XPath> actualParentLocatorIterator = actual.getParentLocators().listIterator();
        ListIterator<Integer> actualParentOffsetIterator = actual.getParentOffsets().listIterator();
        while (expectedValueIterator.hasNext()) {
            String expectedValue = expectedValueIterator.next();
            String actualValue = actualValueIterator.next();
            XPath expectedLocator = expectedParentLocatorIterator.next();
            XPath actualLocator = actualParentLocatorIterator.next();
            Integer expectedOffset = expectedParentOffsetIterator.next();
            Integer actualOffset = actualParentOffsetIterator.next();
            assertEquals("Plain text values did not match.", expectedValue, actualValue);
            assertEquals("Parent locators did not match.", expectedLocator, actualLocator);
            assertEquals("Parent offsets did not match.", expectedOffset, actualOffset);
        }
    }

}