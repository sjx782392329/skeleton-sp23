package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;
    // TODO: Add any necessary static/instance variables.
    private final Map<String, TimeSeries> wordMap;
    private final TimeSeries countTimeSeries;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        wordMap = new HashMap<>();
        countTimeSeries = new TimeSeries();
        final In wordIn = new In(wordsFilename);
        while (wordIn.hasNextLine() && !wordIn.isEmpty()) {
            String word = wordIn.readString();
            int year = wordIn.readInt();
            int count = wordIn.readInt();
            wordIn.readInt();
            final TimeSeries orDefault = wordMap.getOrDefault(word, new TimeSeries());
            orDefault.put(year, (double) count);
            wordMap.put(word, orDefault);
        }
        final In countsIn = new In(countsFilename);
        while (countsIn.hasNextLine() && !countsIn.isEmpty()) {
            final String[] splits = countsIn.readString().split(",");
            int year = Integer.parseInt(splits[0]);
            double count = Double.parseDouble(splits[1]);
            countTimeSeries.put(year, count);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries timeSeries = this.wordMap.getOrDefault(word, null);
        if (timeSeries == null) {
            return null;
        }
        timeSeries = new TimeSeries(timeSeries, startYear, endYear);
        return timeSeries;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        return wordMap.getOrDefault(word, null);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        return countTimeSeries;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries timeSeries = wordMap.getOrDefault(word, null);
        if (timeSeries == null) {
            return null;
        }
        timeSeries = new TimeSeries(timeSeries, startYear, endYear);
        for (int i = startYear; i <= endYear; i++) {
            timeSeries.put(i, timeSeries.get(i) / countTimeSeries.get(i));
        }
        return timeSeries;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year. If the word is not in the data files, return an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries timeSeries = new TimeSeries();
        for (String word : words) {
            final TimeSeries series = wordMap.get(word);
            timeSeries = timeSeries.plus(new TimeSeries(series, startYear, endYear));
        }
        for (int i = startYear; i <= endYear; i++) {
            timeSeries.put(i, timeSeries.getOrDefault(i, 0.0) / countTimeSeries.get(i));
        }
        return timeSeries;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
