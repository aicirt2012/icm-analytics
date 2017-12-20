package de.tum.in.icm.services;

import de.tum.in.icm.dtos.HtmlSourceDTO;
import de.tum.in.icm.entities.TextNodeMap;
import de.tum.in.icm.entities.XPath;
import de.tum.in.icm.util.NERTestDataFactory;
import org.junit.Test;

import java.util.ListIterator;

import static org.junit.Assert.assertEquals;

public class NERPreProcessorServiceTest {

    @Test
    public void parseHtmlMinimal() {
        HtmlSourceDTO inputDTO = NERTestDataFactory.getHTMLSourceDTO(NERTestDataFactory.Type.MINIMAL);
        TextNodeMap actualResult = NERPreProcessorService.getTextNodeMap(inputDTO.getHtmlSource());
        TextNodeMap expectedResult = NERTestDataFactory.getTextNodeMap(NERTestDataFactory.Type.MINIMAL);
        this.checkForEquality(expectedResult, actualResult);
    }

    @Test
    public void parseHtmlSimple() {
        HtmlSourceDTO inputDTO = NERTestDataFactory.getHTMLSourceDTO(NERTestDataFactory.Type.SIMPLE);
        TextNodeMap actualResult = NERPreProcessorService.getTextNodeMap(inputDTO.getHtmlSource());
        TextNodeMap expectedResult = NERTestDataFactory.getTextNodeMap(NERTestDataFactory.Type.SIMPLE);
        this.checkForEquality(expectedResult, actualResult);
    }

    @Test
    public void parseHtmlComplex() {
        HtmlSourceDTO inputDTO = NERTestDataFactory.getHTMLSourceDTO(NERTestDataFactory.Type.COMPLEX);
        TextNodeMap actualResult = NERPreProcessorService.getTextNodeMap(inputDTO.getHtmlSource());
        TextNodeMap expectedResult = NERTestDataFactory.getTextNodeMap(NERTestDataFactory.Type.COMPLEX);
        this.checkForEquality(expectedResult, actualResult);
    }

    @Test
    public void parseHtmlOffset() {
        HtmlSourceDTO inputDTO = NERTestDataFactory.getHTMLSourceDTO(NERTestDataFactory.Type.OFFSET_TEST);
        TextNodeMap actualResult = NERPreProcessorService.getTextNodeMap(inputDTO.getHtmlSource());
        TextNodeMap expectedResult = NERTestDataFactory.getTextNodeMap(NERTestDataFactory.Type.OFFSET_TEST);
        this.checkForEquality(expectedResult, actualResult);
    }

    @Test
    public void parseHtmlElementCount() {
        HtmlSourceDTO inputDTO = NERTestDataFactory.getHTMLSourceDTO(NERTestDataFactory.Type.ELEMENT_COUNT);
        TextNodeMap actualResult = NERPreProcessorService.getTextNodeMap(inputDTO.getHtmlSource());
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