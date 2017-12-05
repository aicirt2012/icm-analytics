package de.tum.in.icm.controllers;

import de.tum.in.icm.dtos.AnnotationDTO;
import de.tum.in.icm.dtos.NERInputDTO;
import de.tum.in.icm.dtos.NERResultDTO;
import de.tum.in.icm.dtos.NERType;
import de.tum.in.icm.entities.IndexedPlainText;
import de.tum.in.icm.services.CoreNERService;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@Path("/ner")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CoreNERController {

    private static StanfordCoreNLP pipeline;

    static {
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.put("annotators", "tokenize,ssplit, pos,lemma,ner");
        pipeline = new StanfordCoreNLP(props);
    }

    @POST
    @Path("/recognize")
    public Response recognize(NERInputDTO inputDTO) {
        IndexedPlainText indexedPlainText = CoreNERService.parseHtmlSource(inputDTO.htmlSource);
        NERResultDTO resultDto = doRecognize(indexedPlainText.getPlainText());
        resultDto.setEmailId(inputDTO.emailId);
        return Response.status(200).entity(resultDto).build();
    }

    NERResultDTO doRecognize(String input) {
        // create an empty Annotation just with the given text
        Annotation document = new Annotation(input);

        // run all Annotators on this text
        pipeline.annotate(document);

        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        NERResultDTO result = new NERResultDTO();

        for (CoreMap sentence : sentences) {
            List<AnnotationDTO> words = new ArrayList<>();

            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                // this is the NER label of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                int startIndex = token.get(CoreAnnotations.CharacterOffsetBeginAnnotation.class);
                int endIndex = token.get(CoreAnnotations.CharacterOffsetEndAnnotation.class);
                AnnotationDTO newWord = new AnnotationDTO();
                newWord.setValue(word);
                newWord.setNerType(NERType.valueOf(ne));
                newWord.setPosType(pos);
                newWord.addPlainTextOccurence(startIndex, endIndex);
                words.add(newWord);
            }
            result.addAnnotations(words);
        }
        return result;
    }

}