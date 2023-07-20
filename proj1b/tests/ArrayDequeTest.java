import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {

    @Test
    public void testCir() {
        double d = (double)4/16;
        assertThat(d).isEqualTo(0.25);
        int nextFirst = 0;
        int num = Math.abs(nextFirst - 1 + 8) % 8;
        assertThat(num).isEqualTo(7);
        int nextLast = 7;
        num = (nextLast + 2) % 8;
        assertThat(num).isEqualTo(1);
    }

    @Test
    public void addFirstAndAddLastExampleTest() {
        // nextFirst 4
        // nextLast 5
        //https://docs.google.com/presentation/d/1kjbO8X7-i63NwQ_9wIt4HXr6APp2qc9PkghD-GO7_is/edit#slide=id.g15cbbcb770b_47_40
        ArrayDeque<String> ad = new ArrayDeque<>(4, 5);
        ad.addLast("a");
        ad.addLast("b");
        ad.addFirst("c");
        ad.addLast("d");
        ad.addLast("e");
        ad.addFirst("f");
        ad.addLast("g");
        ad.addLast("h");
        assertThat(ad.toList()).containsExactly("f", "c", "a", "b", "d", "e", "g", "h").inOrder();
        assertThat(ad.size()).isEqualTo(8);
        ad.addLast("Z");
        assertThat(ad.size()).isEqualTo(9);
        assertThat(ad.toList()).containsExactly("f", "c", "a", "b", "d", "e", "g", "h", "Z").inOrder();
        assertThat(ad.getItemsLength()).isEqualTo(16);
    }

    @Test
    public void addFirstAndAddLastConflictTest() {
        ArrayDeque<String> ad = new ArrayDeque<>(6, 4);
        ad.addFirst("c");
        ad.addLast("d");
        ad.addLast("e");
        ad.addFirst("f");
        assertThat(ad.toList()).containsExactly("f", "c", "d", "e").inOrder();
        assertThat(ad.size()).isEqualTo(4);
        assertThat(ad.getItemsLength()).isEqualTo(16);
    }

    @Test
    public void removeLastAndRemoveFirstTest() {
        ArrayDeque<String> ad = new ArrayDeque<>(6, 4);
        assertThat(ad.isEmpty()).isTrue();
        ad.addFirst("c");
        assertThat(ad.isEmpty()).isFalse();
        ad.addLast("d");
        assertThat(ad.getItemsLength()).isEqualTo(8);
        ad.addLast("e");
        assertThat(ad.getItemsLength()).isEqualTo(16);
        ad.addFirst("f");
        // f c d e
        assertThat(ad.toList()).containsExactly("f", "c", "d", "e").inOrder();
        assertThat(ad.size()).isEqualTo(4);
        assertThat(ad.getItemsLength()).isEqualTo(16);
        assertThat(ad.removeFirst()).isEqualTo("f");
        assertThat(ad.removeFirst()).isEqualTo("c");
        assertThat(ad.removeLast()).isEqualTo("e");
        assertThat(ad.size()).isEqualTo(1);
        assertThat(ad.removeLast()).isEqualTo("d");
        assertThat(ad.isEmpty()).isTrue();
        assertThat(ad.removeLast()).isEqualTo(null);
        assertThat(ad.getItemsLength()).isEqualTo(8);
    }

    @Test
    public void resizeDownTest() {
        // nextFirst 4
        // nextLast 5
        //https://docs.google.com/presentation/d/1kjbO8X7-i63NwQ_9wIt4HXr6APp2qc9PkghD-GO7_is/edit#slide=id.g15cbbcb770b_47_40
        ArrayDeque<String> ad = new ArrayDeque<>(4, 5);
        ad.addLast("a");
        ad.addLast("b");
        ad.addFirst("c");
        ad.addLast("d");
        ad.addLast("e");
        ad.addFirst("f");
        ad.addLast("g");
        ad.addLast("h");
        assertThat(ad.toList()).containsExactly("f", "c", "a", "b", "d", "e", "g", "h").inOrder();
        assertThat(ad.size()).isEqualTo(8);
        ad.addLast("Z");
        assertThat(ad.size()).isEqualTo(9);
        assertThat(ad.toList()).containsExactly("f", "c", "a", "b", "d", "e", "g", "h", "Z").inOrder();
        assertThat(ad.getItemsLength()).isEqualTo(16);
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        assertThat(ad.getItemsLength()).isEqualTo(8);
        ad.removeLast();
        ad.removeLast();
        assertThat(ad.getItemsLength()).isEqualTo(8);
    }



    @Test
    public void resizeDown2Test() {
        // nextFirst 4
        // nextLast 5
        //https://docs.google.com/presentation/d/1kjbO8X7-i63NwQ_9wIt4HXr6APp2qc9PkghD-GO7_is/edit#slide=id.g15cbbcb770b_47_40
        ArrayDeque<String> ad = new ArrayDeque<>(4, 5);
        ad.addLast("a");
        ad.addLast("b");
        ad.addFirst("c");
        ad.addLast("d");
        ad.addLast("e");
        ad.addFirst("f");
        ad.addLast("g");
        ad.addLast("h");
        assertThat(ad.toList()).containsExactly("f", "c", "a", "b", "d", "e", "g", "h").inOrder();
        assertThat(ad.size()).isEqualTo(8);
        ad.addLast("Z");
        assertThat(ad.size()).isEqualTo(9);
        assertThat(ad.toList()).containsExactly("f", "c", "a", "b", "d", "e", "g", "h", "Z").inOrder();
        assertThat(ad.getItemsLength()).isEqualTo(16);
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        assertThat(ad.getItemsLength()).isEqualTo(8);
        ad.removeFirst();
        ad.removeFirst();
        assertThat(ad.getItemsLength()).isEqualTo(8);
    }
}
