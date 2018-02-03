package de.tum.in.icm.services;

import de.tum.in.icm.dtos.AnnotationDTO;
import de.tum.in.icm.dtos.NERType;
import de.tum.in.icm.dtos.PatternDTO;
import de.tum.in.icm.dtos.TextOrigin;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskService {

    static final int MAX_WORDS_COUNT = 20;

    public ArrayList<AnnotationDTO> Search(String text, List<PatternDTO> patterns, List<AnnotationDTO> nerAnnotations, TextOrigin textOrigin) {

        ArrayList<AnnotationDTO> result = new ArrayList<AnnotationDTO>();
        for (PatternDTO pattern : patterns) {
            result.addAll(Search(text, pattern, nerAnnotations, textOrigin));
        }
        return result;
    }

    public ArrayList<AnnotationDTO> Search(String text, PatternDTO pattern, List<AnnotationDTO> nerAnnotations, TextOrigin textOrigin) {

        Matcher m;
        Pattern patternLabel = Pattern.compile(pattern.getLabel());
        ArrayList<AnnotationDTO> result = new ArrayList<AnnotationDTO>();

        if (pattern.isRegex())
            m = patternLabel.matcher(text);
        else {
            pattern.setLabel(pattern.getLabel().toLowerCase());
            String textInLowerCase = text.toLowerCase();
            m = patternLabel.matcher(textInLowerCase);
        }

        while (m.find()) {
            AnnotationDTO newMatch = new AnnotationDTO();
            newMatch.setNerType(NERType.TASK_TITLE);
            newMatch.setTextOrigin(textOrigin);

            if (pattern.isRegex())
                newMatch.setValue(m.group());
            // pass original text to get words with original cases
            else
                newMatch.setValue(getFullSentence(text, m.start()));

            newMatch.addPlainTextIndex(m.start());
            result.add(newMatch);

        }
        return result;
    }

    //returns a whole sentence until we hit the MAX_WORD_COUNT or the sentence ende with '.', '!' or '?'
    private String getFullSentence(String text, Integer index) {
        String result = "";
        int wordsCount = 0;
        if (index < 0 || index > text.length() - 1)
            return result;
        int currentPos = index;
        char currentChar = text.charAt(currentPos);
        while (wordsCount < MAX_WORDS_COUNT) {
            if (currentChar == '.' || currentChar == '?' || currentChar == '!')
                return result;
            if (currentChar == ' ')
                wordsCount++;
            result = result + currentChar;
            currentPos++;
            if (currentPos >= text.length())
                break;
            currentChar = text.charAt(currentPos);
        }
        return result;
    }
}
