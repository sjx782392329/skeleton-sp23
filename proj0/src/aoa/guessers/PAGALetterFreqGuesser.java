package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }


    public List<String> keepOnlyWordsThatMatchPattern(String pattern, List<Character> guesses) {
        List<String> res = new ArrayList<>();
        boolean allFlag = true;
        for (char c : pattern.toCharArray()) {
            if (c != '-') {
                allFlag = false;
                break;
            }
        }
        if (allFlag) {
            for (String word : words) {
                if (word.length() != pattern.length()) {
                    continue;
                }
                boolean flag = true;
                for (Character c : guesses) {
                    // word 包含 guesses 中的字符
                    // indexOf return -1 if c not exists
                    if (word.indexOf(c) != -1) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    res.add(word);
                }
            }
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
            char[] arrs = word.toCharArray();
            for (Map.Entry<Character, List<Integer>> entry : indexMap.entrySet()) {
                List<Integer> lists = entry.getValue();
                char c = entry.getKey();
                for (int list : lists) {
                    if (word.charAt(list) != c) {
                        flag = false;
                        break;
                    }
                    if (flag) {
                        arrs[list] = '1';
                    }
                }
                if (!flag) {
                    break;
                }
            }
            if (flag) {
                boolean addFlag = true;
                for (Character c : guesses) {
                    if (String.copyValueOf(arrs).indexOf(c) != -1) {
                        addFlag = false;
                    }
                }
                if (addFlag) {
                    res.add(word);
                }
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
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        // TODO: Fill in this method.
        final List<String> lists = keepOnlyWordsThatMatchPattern(pattern, guesses);
        final Map<Character, Integer> map = getFreqMapThatMatchesPattern(lists);
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            Character k = entry.getKey();
            if (!guesses.contains(k)) {
                return k;
            }
        }
        return '?';
    }

    public static void main(String[] args) {
        String path = "/Users/shenjinxin/github/skeleton-sp23/proj0/data/example.txt";
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser(path);
        System.out.println(pagalfg.getGuess("-o--", List.of('o')));
        final List<String> strings = pagalfg.keepOnlyWordsThatMatchPattern("-o--", List.of('o'));
        System.out.println(strings.toString());
        System.out.println(pagalfg.getFreqMapThatMatchesPattern(strings));
    }
}
