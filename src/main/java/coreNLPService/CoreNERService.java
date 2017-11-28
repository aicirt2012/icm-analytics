package coreNLPService;

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
        String output = "";
        for (JSONObject item : Recognize(msg)) {
            output += item.toJSONString();
        }
        return Response.status(200).entity(output).build();
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

    public List<JSONObject> Recognize(String input) {
        // create an empty Annotation just with the given text
        Annotation document = new Annotation(input);

        // run all Annotators on this text
        pipeline.annotate(document);

        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        List result = new ArrayList<JSONObject>();

        for (CoreMap sentence : sentences) {
            JSONObject newSentence = new JSONObject();
            newSentence.put("value", sentence);

            List words = new ArrayList<JSONObject>();

            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                JSONObject newWord = new JSONObject();
                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                // this is the NER label of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

                int CharacterOffsetBegin = token.get(CoreAnnotations.CharacterOffsetBeginAnnotation.class);

                int CharacterOffsetEnd = token.get(CoreAnnotations.CharacterOffsetEndAnnotation.class);

                newWord.put("value", word);
                newWord.put("POS", pos);
                newWord.put("NE", ne);
                newWord.put("CharacterOffsetBeginAnnotation", CharacterOffsetBegin);
                newWord.put("CharacterOffsetEndAnnotation", CharacterOffsetEnd);
                words.add(newWord);

//                System.out.println("word: " + word + " pos: " + pos + " ne:" + ne +
//                        "offset Begin:" +  CharacterOffsetBegin + "offsetEnd:" + CharacterOffsetEnd);
            }

            newSentence.put("words", words);
            result.add(newSentence);
        }
        return (ArrayList<JSONObject>) result;
    }

    private void initializeAnnotators() {

    }
}