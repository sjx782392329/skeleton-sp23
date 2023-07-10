package aoa.guessers;

import aoa.utils.FileUtils;

import javax.sql.rowset.BaseRowSet;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        // TODO: Fill in this method.
        Map<Character, Integer> map = new HashMap<>();
        for (String word : words) {
            for (char c : word.toCharArray()) {
                map.put(c, map.getOrDefault(c, 0) + 1);
            }
        }
        return map;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        // TODO: Fill in this method.
        final Map<Character, Integer> map = getFrequencyMap();
        if (map.size() == 0) {
            return '?';
        }
        TreeMap<Character, Integer> tree = new TreeMap<>((k1, k2) -> map.get(k2).compareTo(map.get(k1)));
        tree.putAll(map);
        for (Map.Entry<Character, Integer> entry : tree.entrySet()) {
            if (!guesses.contains(entry.getKey())) {
                return entry.getKey();
            }
        }
        return '?';
    }

    public static void main(String[] args) {
        String path = "/Users/shenjinxin/github/skeleton-sp23/proj0/data/example.txt";
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser(path);
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
