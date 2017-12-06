package de.tum.in.icm.services;

import de.tum.in.icm.entities.IndexedPlainText;

import java.util.HashMap;
import java.util.Map;

public class NERCoreService {

    public static String[] tokenizeToText(String htmlSource) {
        return null;
    }

    public static IndexedPlainText parseHtmlSource(String htmlSource) {
        String[] tokenizedPlainText = tokenizeToText(htmlSource);
        String line;
        Map<Integer, Integer> indices = new HashMap<>();
        String plainTextString = "";
        for (int i = 0; i < tokenizedPlainText.length; i++) {
            line = tokenizedPlainText[i];
            indices.put(tokenizedPlainText.length, -1);
            plainTextString += "\n" + line;
        }
        return new IndexedPlainText();
    }

//    addAnnotationIndices(annotations, htmlSource) {
//        let disassembledEmail = this.tokenizeToText(htmlSource);
//        for (let i = 0; i < annotations.length; i++) {
//            let annotation = annotations[i];
//            for (let j = 0; j < disassembledEmail.length; j++) {
//                let emailTextLine = disassembledEmail[j];
//                let index = emailTextLine.value.indexOf(annotation.value);
//                while (index > -1) {
//                    index += emailTextLine.index;
//                    if (!annotation.occurences) {
//                        annotation.occurences = [];
//                        annotation.occurence_context = [];
//                        annotation.offset = annotation.value.length;
//                    }
//                    annotation.occurences.push(index);
//                    annotation.occurence_context.push(emailTextLine.value);   // FIXME only for development purposes, remove when service development is done
//                    index = emailTextLine.value.indexOf(annotation.value, index + annotation.value.length);
//                }
//            }
//        }
//        return annotations;
//    }

//    /**
//     * Takes a list of annotations with their occurrence indices and calculates the browser-style Range objects for each occurrence
//     *
//     * @param indexedAnnotations The list of annotations, including their occurrence indices
//     * @param htmlSource The full HTML source text as a single string
//     * @returns {*}
//     *          The same list that was passed in, with the added range objects for each occurrence index
//     */
//    addAnnotationRanges(indexedAnnotations, htmlSource) {
//        for (let i = 0; i < indexedAnnotations.length; i++) {
//            let annotation = indexedAnnotations[i];   // the annotation object
//            let annotationValue = annotation.value;   // the value of the annotation (e.g. "Google")
//            let annotationOccurrenceIndices = annotation.occurences;   // the array of integer indices with all occurrences of the annotation in the source HTML
//            annotation.ranges = [];   // initialize the array where the calculated ranges should go
//            if (annotationOccurrenceIndices) {
//                for (let j = 0; j < annotationOccurrenceIndices.length; j++) {
//                    let annotationOccurrenceIndex = annotationOccurrenceIndices[j];   // a single index of an occurrence of the annotation value
//                    let range = this.calculateOccurrenceRange(annotationValue, annotationOccurrenceIndex, htmlSource);  // call the actual xpath range calculation function
//                    annotation.ranges.push(range);  // push the calculated range into the result array
//                }
//            }
//        }
//        return indexedAnnotations;
//    }

//    /**
//     * Calculates the browser-style Range object for an occurrence of an annotation value at a specific index in the HTML source
//     *
//     * @param annotationValue The plain text value of the currently processed annotation
//     * @param annotationOccurrenceIndex The currently processed index of the first character of the annotationValue in the htmlSource
//     * @param htmlSource The full HTML source text as a single string
//     * @returns {{end: string, endOffset: number, start: string, startOffset: number}}
//     *          The browser-style Range object containing the start/end xpath and their offsets
//     */
//    calculateOccurrenceRange(annotationValue, annotationOccurrenceIndex, htmlSource) {
//        // initialize the variables that should hold the calculation results
//        let xPathStart = "";  // string variable for the xpath of the HTML tag where the annotation starts
//        let xPathEnd = "";  // string variable for the xpath of the HTML tag where the annotation ends
//        let offsetStart = 0;  // integer offset of the occurrence of the first character of the annotation value inside the inner text of the HTML start element
//        let offsetEnd = 0;  // integer offset of the occurrence of the last character of the annotation value inside the inner text of the HTML end element
//
//        // TODO implement range calculation
//
//        return {end: xPathEnd, endOffset: offsetEnd, start: xPathStart, startOffset: offsetStart};
//    }

}