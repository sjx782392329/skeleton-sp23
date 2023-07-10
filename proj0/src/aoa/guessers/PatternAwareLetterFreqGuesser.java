package aoa.guessers;

import aoa.utils.FileUtils;
import edu.princeton.cs.algs4.In;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    /***
     * -e--
     * @param pattern
     * @return List String
     */
    public List<String> keepOnlyWordsThatMatchPattern(String pattern) {
        List<String> res = new ArrayList<>();
        boolean allFlag = true;
        for (char c : pattern.toCharArray()) {
            if (c != '-') {
                allFlag = false;
                break;
            }
        }
        if (allFlag) {
            res.addAll(words);
            return res;
        }
        char[] charArrays = pattern.toCharArray();
        Map<Character, List<Integer>> indexMap = new HashMap<>();
        for (int i = 0; i < charArrays.length; i++) {
            char c = charArrays[i];
            if (c == '-') {
                continue;
            }
            List<Integer> temp = indexMap.containsKey(c) ? indexMap.get(c) : new ArrayList<>();
            temp.add(i);
            indexMap.put(c, temp);
        }

        for (String word : words) {
            boolean flag = true;
            if (word.length() != pattern.length()) {
                continue;
            }
            for (Map.Entry<Character, List<Integer>> entry : indexMap.entrySet()) {
                List<Integer> lists = entry.getValue();
                char c = entry.getKey();
                for (int list : lists) {
                    if (word.charAt(list) != c) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) {
                res.add(word);
            }
        }
        return res;
    }

    public Map<Character, Integer> getFreqMapThatMatchesPattern(List<String> list) {
        Map<Character, Integer> map = new HashMap<>();
        TreeMap<Character, Integer> tree = new TreeMap<>((a, b) -> {
            if (map.get(a).equals(map.get(b))) {
                return a.compareTo(b);
            }
            return map.get(b).compareTo(map.get(a));
        });
        for (String str : list) {
            for (char c : str.toCharArray()) {
                map.put(c, map.getOrDefault(c, 0) + 1);
            }
        }
        tree.putAll(map);
        return tree;
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */
    public char getGuess(String pattern, List<Character> guesses) {
        // TODO: Fill in this method.
        final List<String> list = keepOnlyWordsThatMatchPattern(pattern);
        final Map<Character, Integer> map = getFreqMapThatMatchesPattern(list);
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            Character k = entry.getKey();
            if (!guesses.contains(k)) {
                return k;
            }
        }
        return '?';
    }

    public static void main(String[] args) {
        String path = "/Users/shenjinxin/github/skeleton-sp23/proj0/data/sorted_scrabble.txt";
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser(path);
        final List<String> strings = palfg.keepOnlyWordsThatMatchPattern("-o--a-");
        System.out.println(strings.toString());
        System.out.println(palfg.getFreqMapThatMatchesPattern(strings));
        System.out.println(palfg.getGuess("-e--", List.of('e')));
    }
}