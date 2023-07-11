package aoa.choosers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

public class EvilChooser implements Chooser {
    private String pattern;
    private List<String> wordPool;
    private String lastPattern;

    public EvilChooser(int wordLength, String dictionaryFile) {
        // TODO: Fill in this constructor.
        if (wordLength < 1) {
            throw new IllegalArgumentException();
        }
        wordPool = FileUtils.readWordsOfLength(dictionaryFile, wordLength);
        if (wordPool.size() == 0) {
            throw new IllegalStateException();
        }
        pattern = "-".repeat(wordLength);
    }

    @Override
    public int makeGuess(char letter) {
        // TODO: Fill in this method.
        int res = 0;
        Map<String, List<String>> map = new HashMap<>();
        TreeMap<String, List<String>> tree = new TreeMap<>((a, b) -> {
            int sizeA = map.get(a).size();
            int sizeB = map.get(b).size();
            if (sizeA == sizeB) {
                return a.compareTo(b);
            }
            return sizeB - sizeA;
        });
        for (String word : wordPool) {
            pattern = "-".repeat(word.length());
            List<String> lists;
            // words not contains letter
            if (word.indexOf(letter) == -1) {
                if (lastPattern != null) {
                    pattern = lastPattern;
                }
                lists = map.getOrDefault(pattern, new ArrayList<>());
                lists.add(word);
                map.put(pattern, lists);
                continue;
            }
            char[] chars = word.toCharArray();
            if (lastPattern != null) {
                pattern = lastPattern;
            }
            char[] patterns = pattern.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == letter) {
                    patterns[i] = letter;
                    pattern = String.copyValueOf(patterns);
                }
            }
            lists = map.getOrDefault(pattern, new ArrayList<>());
            lists.add(word);
            map.put(pattern, lists);
        }
        tree.putAll(map);
        pattern = tree.firstKey();
        lastPattern = pattern;
        wordPool = tree.get(pattern);
        for (char c : pattern.toCharArray()) {
            if (c == letter) {
                res++;
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
        return wordPool.get(0);
    }
}
