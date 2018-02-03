package de.tum.in.icm.services;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import de.tum.in.icm.dtos.AnnotationDTO;
import de.tum.in.icm.dtos.NERType;
import de.tum.in.icm.dtos.ResultDTO;
import de.tum.in.icm.dtos.TextOrigin;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@WebListener
public class NERCoreService implements ServletContextListener {

    private static StanfordCoreNLP pipeline;

    public void contextInitialized(ServletContextEvent event) {
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.put("annotators", "tokenize,ssplit, pos,lemma,ner");
        pipeline = new StanfordCoreNLP(props);
    }

    public void contextDestroyed(ServletContextEvent event) {
        // do nothing
    }

    public ResultDTO doRecognize(String input, TextOrigin textOrigin) {
        // create an empty Annotation just with the given text
        Annotation document = new Annotation(input);

        // run all Annotators on this text
        pipeline.annotate(document);

        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        ResultDTO result = new ResultDTO();
        AnnotationDTO previousWord = null;
        int previousWordStartIndex = -1;
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
                int newWordStartIndex = token.get(CoreAnnotations.CharacterOffsetBeginAnnotation.class);
                AnnotationDTO newWord = new AnnotationDTO();
                newWord.setValue(word);
                newWord.setNerType(NERType.fromString(ne));
                //TODO: do we still need this ?
                newWord.setPosType(pos);
                newWord.addPlainTextIndex(newWordStartIndex);
                newWord.setTextOrigin(textOrigin);

                if (!newWord.getNerType().equals(NERType.O)) {
                    if (previousWord != null && previousWord.getNerType().equals(newWord.getNerType())) {
                        if (tryJoinEntities(previousWord, previousWordStartIndex, newWord, newWordStartIndex)) {
                            // replace last added entity annotation with the joined one (previousWord gets edited by reference in tryJoinEntities)
                            words.remove(words.size() - 1);
                            words.add(previousWord);
                        }
                    } else {
                        previousWord = newWord;
                        words.add(newWord);
                        previousWordStartIndex = newWordStartIndex;

                    }
                }
            }
            result.addAnnotations(words);
        }
        formalizeDates(result);
        return result;
    }

    // returns true in case of join, false otherwise
    // edits the first object by reference and concatenates the value of second object to it
    private boolean tryJoinEntities(AnnotationDTO first, int firstStartIndex, AnnotationDTO second, int secondStartIndex) {
        // if the two entities were not separated by space like : "Thursday" and "," in  Thursday, February 1, 2018
        if (firstStartIndex + first.getValue().length() == secondStartIndex) {
            first.setValue(first.getValue() + second.getValue());
            return true;
        }
        // if the two entities were separated by ONE space
        if (firstStartIndex + first.getValue().length() == secondStartIndex - 1) {

            first.setValue(first.getValue() + " " + second.getValue());
            return true;
        }
        // if the two entities were separated by more than one space or by another ner entities (including O)

        return false;
    }


    private void formalizeDates(ResultDTO input) {
        Parser parser = new Parser();
        for (AnnotationDTO annotation : input.getAnnotations()) {

            if (annotation.getNerType() == NERType.DATE) {
                List<DateGroup> groups = parser.parse(annotation.getValue());
                String valWithoutBackslashes;
                if (groups.size() == 0) {
                    valWithoutBackslashes = StringUtils.remove(annotation.getValue(),"\\");
                    groups = parser.parse(valWithoutBackslashes);
                }
                if (groups.size() > 0) {
                    {
                        Date date = groups.get(0).getDates().get(0);
                        String formattedDate = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:SS");
                        annotation.setFormattedValue(formattedDate);
                    }
                }
            }
        }

    }
}