package de.tum.in.icm.services;

import de.tum.in.icm.dtos.AnnotationDTO;
import de.tum.in.icm.dtos.NERType;
import de.tum.in.icm.dtos.PatternDTO;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskService {

    static final int MAX_WORDS_COUNT = 20;

    public ArrayList<AnnotationDTO> Search(String text, List<PatternDTO> regexPatterns, List<AnnotationDTO> nerAnnotations) {

        ArrayList<AnnotationDTO> result = new ArrayList<AnnotationDTO>();
        for (PatternDTO pattern : regexPatterns) {
            result.addAll(Search(text, pattern, nerAnnotations));
        }
        return result;
    }

    public ArrayList<AnnotationDTO> Search(String text, PatternDTO regexPattern, List<AnnotationDTO> nerAnnotations) {

        if(!regexPattern.isCaseSensitive())
        {
            regexPattern.setLabel(regexPattern.getLabel().toLowerCase());
            text = text.toLowerCase();
        }
        ArrayList<AnnotationDTO> result = new ArrayList<AnnotationDTO>();
        Pattern p = Pattern.compile(regexPattern.getLabel());
        Matcher m = p.matcher(text);
        Random randomGenerator = new Random();
        while (m.find()) {
            AnnotationDTO newMatch = new AnnotationDTO();
            newMatch.setNerType(NERType.TASK_TITLE);

            if (regexPattern.isMatchTillSentenceEnd())
                newMatch.setValue(getFullSentence(text, m.start()));
            else
                newMatch.setValue(m.group());
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
