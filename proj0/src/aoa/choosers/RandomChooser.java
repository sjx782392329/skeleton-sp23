package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

import java.util.List;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;

    public RandomChooser(int wordLength, String dictionaryFile) {
        // TODO: Fill in/change this constructor.
        if (wordLength < 1) {
            throw new IllegalArgumentException();
        }
        final List<String> words = FileUtils.readWordsOfLength(dictionaryFile, wordLength);
        int length = words.size();
        if (length == 0) {
            throw new IllegalStateException();
        }
        final int uniform = StdRandom.uniform(length);
        chosenWord = words.get(uniform);
        pattern = "-".repeat(wordLength);
    }

    @Override
    public int makeGuess(char letter) {
        // TODO: Fill in this method.
        int res = 0;
        char[] chars = new char[pattern.length()];
        char[] chosenChars = chosenWord.toCharArray();
        for (int i = 0; i < chosenChars.length; i++) {
            if (chosenChars[i] == letter) {
                res++;
                chars = pattern.toCharArray();
                chars[i] = letter;
                pattern = String.copyValueOf(chars);
            }
        }
        return res;
    }

    @Override
    public String getPattern() {
        // TODO: Fill in this method.
        return this.pattern;
    }

    @Override
    public String getWord() {
        // TODO: Fill in this method.
        return this.chosenWord;
    }
}
