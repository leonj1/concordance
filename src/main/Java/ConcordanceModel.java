import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Concordance model
 * Created by jose on 2/7/15.
 */
class ConcordanceModel {
    private Map<String, Integer> wordFrequencyMap = Maps.newTreeMap();
    private Map<String, Set<String>> wordLocationMap = Maps.newTreeMap();

    /**
     * Process a line by splitting by word.
     * Remove any punctuations since it should not be part of the concordance.
     * Track the number of occurrences of a word and
     * @param line          a String to process the words within it
     * @param lineNumber    to help track where this line is from in relation to the overall contents
     */
    public void processLine(String line, String lineNumber) {
        if (line.isEmpty()) {
            return;
        }

        String[] words = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        List<String> wordsList = Arrays.asList(words);

        /**
         * Looping could be a problem if the line is exceptionally log and another approach would need to be considered.
         * For loop was chosen here for readability and therefore maintainability.
         * If does become a concern we could start splitting the String and possibly spawn threads to process each.
         */
        for (String word : words) {
            word = word.trim();
            if (word.isEmpty()) {
                continue;
            }
            int frequency = Collections.frequency(wordsList, word);
            int count = wordFrequencyMap.containsKey(word) ? wordFrequencyMap.get(word) + frequency : frequency;
            wordFrequencyMap.put(word, count);

            Set<String> lineNumbers = Sets.newTreeSet();
            if (wordLocationMap.containsKey(word)) {
                lineNumbers = wordLocationMap.get(word);
            }

            lineNumbers.add(lineNumber);
            wordLocationMap.put(word, lineNumbers);
        }
    }

    public Map getWordFrequency() {
        return wordFrequencyMap;
    }

    public Map getWordLocation() {
        return this.wordLocationMap;
    }
}
