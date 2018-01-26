package de.tum.in.icm.services;

import de.tum.in.icm.dtos.AnnotationDTO;
import de.tum.in.icm.dtos.NERType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSearchService {

    static final int MAX_WORDS_COUNT = 20;

    public ArrayList<AnnotationDTO> Search(String text, List<String> regexPatterns, Boolean matchTillSentenceEnd){

        ArrayList<AnnotationDTO> result = new ArrayList<AnnotationDTO>();
        for (String pattern: regexPatterns) {
            result.addAll(Search(text,pattern,matchTillSentenceEnd));
        }
            return result;
    }

    public ArrayList<AnnotationDTO> Search(String text, String regexPattern, Boolean matchTillSentenceEnd) {

        text = text.toLowerCase();
        regexPattern = regexPattern.toLowerCase();

        ArrayList<AnnotationDTO> result = new ArrayList<AnnotationDTO>();
        Pattern p = Pattern.compile(regexPattern);
        Matcher m = p.matcher(text);
        while (m.find()) {
            AnnotationDTO newMatch = new AnnotationDTO();
            newMatch.setNerType(NERType.TASK_TITLE);
            if (matchTillSentenceEnd)
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
