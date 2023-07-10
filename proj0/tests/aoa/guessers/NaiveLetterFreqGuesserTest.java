package aoa.guessers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static com.google.common.truth.Truth.assertThat;

public class NaiveLetterFreqGuesserTest {

    @Order(1)
    @DisplayName("NaiveLetterFreqGuesser returns correct frequency map (small input)")
    @Test
    public void testFreqMapSmallFile() {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        Map<Character, Integer> freqMap = nlfg.getFrequencyMap();

        // y should occur once.
        assertThat(freqMap.get('y')).isEqualTo(1);

        // z shouldn't be present.
        assertThat(freqMap.containsKey('z')).isFalse();

        // a should appear three times.
        assertThat(freqMap.get('a')).isEqualTo(3);
    }

    @Order(2)
    @DisplayName("NaiveLetterFreqGuesser returns correct frequency map (large input)")
    @Test
    public void testFreqMapLargeFile() {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/sorted_scrabble.txt");
        Map<Character, Integer> freqMap = nlfg.getFrequencyMap();

        // y should occur 17,313 times.
        assertThat(freqMap.get('y')).isEqualTo(17313);

        // a should appear 80,229 times.
        assertThat(freqMap.get('a')).isEqualTo(80229);
    }

    @Order(3)
    @DisplayName("NaiveLetterFreqGuesser returns correct guess based on previous guesses")
    @Test
    public void testGetGuess() {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");

        // check that the first guess is e, the most common letter in the dictionary.
        char guess = nlfg.getGuess(List.of());
        assertThat(guess).isEqualTo('e');

        // check that the next guess is l if someone has already guessed e.
        guess = nlfg.getGuess(List.of('e'));
        assertThat(guess).isEqualTo('l');

        // check that the next guess is b if someone has already guessed l, o, x, a, e (in that order).
        guess = nlfg.getGuess(List.of('l', 'o', 'x', 'a', 'e'));
        assertThat(guess).isEqualTo('b');
    }

    @Test
    public void testFile() {
        final File file = new File("data/example.txt");
        System.out.println(file.exists());
    }

    @Test
    public void testTreeMap() {
        Map<Character, Integer> map = new HashMap<>();
        map.put('a', 3);
        map.put('b', 2);
        map.put('d', 2);
        map.put('e', 7);
        map.put('l', 6);
        map.put('o', 5);
        TreeMap<Character, Integer> tree = new TreeMap<>((k1, k2) -> map.get(k2).compareTo(map.get(k1)));
        tree.putAll(map);
        tree.forEach((k, v) -> {
            System.out.println((char)k);
            System.out.println(v);
        });

    }
}
