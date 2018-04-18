package de.tum.in.icm.services;

import de.tum.in.icm.dtos.AnnotationDTO;
import de.tum.in.icm.dtos.NERType;
import de.tum.in.icm.dtos.PatternDTO;
import de.tum.in.icm.dtos.TextOrigin;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskService {

    private static final Logger logger = Logger.getLogger(TaskService.class.getName());

    private static final int MAXIMUM_SENTENCE_LENGTH_WORDS = 20;
    private static final char[] SENTENCE_STOP_CHARACTERS = {'.', '!', '?'};

    public ArrayList<AnnotationDTO> findByUserPatterns(String text, List<PatternDTO> patterns, TextOrigin textOrigin) {

        ArrayList<AnnotationDTO> result = new ArrayList<>();
        for (PatternDTO pattern : patterns) {
            result.addAll(findByUserPattern(text, pattern, textOrigin));
        }
        return result;
    }

    private ArrayList<AnnotationDTO> findByUserPattern(String text, PatternDTO pattern, TextOrigin textOrigin) {
        ArrayList<AnnotationDTO> result = new ArrayList<>();

        if (pattern.isRegex()) {
            Pattern patternLabel = Pattern.compile(pattern.getLabel());
            Matcher m = patternLabel.matcher(text);
            while (m.find()) {
                AnnotationDTO newMatch = new AnnotationDTO();
                newMatch.setNerType(NERType.TASK_TITLE);
                newMatch.setTextOrigin(textOrigin);
                newMatch.setValue(m.group());
                newMatch.addPlainTextIndex(m.start());
                result.add(newMatch);
            }
        } else {

            String textInLowerCase = text.toLowerCase();
            //first occurrence
            int index = textInLowerCase.indexOf(pattern.getLabel().toLowerCase()) + pattern.getLabel().length();
            while (index >= pattern.getLabel().length()) {
                AnnotationDTO newMatch = new AnnotationDTO();
                newMatch.setNerType(NERType.TASK_TITLE);
                newMatch.setTextOrigin(textOrigin);
                // pass original text to get words with original letter cases
                newMatch.setValue(getFullSentenceLegacy(text, index));
                newMatch.addPlainTextIndex(index);
                result.add(newMatch);
                index = textInLowerCase.indexOf(pattern.getLabel().toLowerCase(), index + 1);
                index = index > 0 ? index + pattern.getLabel().length() : -1;
            }
        }
        return result;
    }

    private String getFullSentence(String text, int index) {
        int endIndex = getIndexOfFirstStopChar(text.substring(index));
        String sentence = endIndex == -1 ? text.substring(index) : text.substring(index, index + endIndex);
        return ensureMaximumSentenceLength(sentence);
    }

    private int getIndexOfFirstStopChar(String text) {
        int smallestIndex = Integer.MAX_VALUE;
        int currentIndex;
        for (char stopCharacter : SENTENCE_STOP_CHARACTERS) {
            currentIndex = text.indexOf(stopCharacter);
            if (currentIndex < smallestIndex)
                smallestIndex = currentIndex;
        }
        return smallestIndex == Integer.MAX_VALUE ? -1 : smallestIndex;
    }

    private String ensureMaximumSentenceLength(String text) {
        BreakIterator wordIterator = BreakIterator.getWordInstance();
        wordIterator.setText(text);
        for (int i = 0; i < MAXIMUM_SENTENCE_LENGTH_WORDS; i++) {
            if (wordIterator.next() == BreakIterator.DONE)
                break;
        }
        return text.substring(0, wordIterator.current());
    }

    //returns a whole sentence until we hit the MAX_WORD_COUNT or the sentence ende with '.', '!' or '?'
    @Deprecated
    private String getFullSentenceLegacy(String text, Integer index) {
        // TODO simplify by replacing char based processing with usage of indexOf or similar methods
        String result = "";
        int wordsCount = 0;
        if (index < 0 || index > text.length() - 1)
            return result;
        int currentPos = index;
        char currentChar = text.charAt(currentPos);
        while (wordsCount < MAXIMUM_SENTENCE_LENGTH_WORDS) {
            if (currentChar == '.' || currentChar == '?' || currentChar == '!' || currentChar == '\n')
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
