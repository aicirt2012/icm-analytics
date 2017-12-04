package de.tum.in.icm.services;

import de.tum.in.icm.dtos.AnnotationDTO;
import de.tum.in.icm.dtos.MessageDTO;
import de.tum.in.icm.dtos.ResultDTO;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@Path("/ner")
@Consumes(MediaType.APPLICATION_JSON)
public class CoreNERService {

    private static StanfordCoreNLP pipeline;

    static {
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.put("annotators", "tokenize,ssplit, pos,lemma,ner");
        pipeline = new StanfordCoreNLP(props);
    }

    @GET
    @Path("/{param}")
    public Response getMsg(@PathParam("param") String msg) {

        return Response.status(200).entity(Recognize(msg)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/recognize")
    public Response recognize(MessageDTO input) {
        StringBuilder inputString = new StringBuilder();
        for (String line : input.lines) {
            inputString.append(line);
        }
        return getMsg(inputString.toString());
    }




    public ResultDTO Recognize(String input)
    {
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();

        props.put("annotators","tokenize,ssplit, pos,lemma,ner");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(input);

        // run all Annotators on this text
        pipeline.annotate(document);

        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        ResultDTO result = new ResultDTO();

        for(CoreMap sentence: sentences) {
            JSONObject newSentence = new JSONObject();

            List words = new ArrayList<AnnotationDTO>();

            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                // this is the NER label of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                int CharacterOffsetBegin   = token.get(CoreAnnotations.CharacterOffsetBeginAnnotation.class);
                int CharacterOffsetEnd   = token.get(CoreAnnotations.CharacterOffsetEndAnnotation.class);
                AnnotationDTO  newWord = new AnnotationDTO(word,ne,pos,CharacterOffsetBegin,CharacterOffsetEnd);
                words.add(newWord);
            }
            result.Annotations = words   ;
        }
        return result ;
    }


}