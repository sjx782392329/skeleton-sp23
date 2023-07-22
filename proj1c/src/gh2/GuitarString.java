package gh2;

import deque.ArrayDeque;

import java.util.Arrays;
// TODO: maybe more imports

//Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    // TODO: uncomment the following line once you're ready to start this portion
     private ArrayDeque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // TODO: Create a buffer with capacity = SR / frequency. You'll need to
        //       cast the result of this division operation into an int. For
        //       better accuracy, use the Math.round() function before casting.
        //       Your should initially fill your buffer array with zeros.
        buffer = new ArrayDeque<>((int)Math.round(SR / frequency));
        Arrays.fill(buffer.getItems(), 0.0);
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // TODO: Dequeue everything in buffer, and replace with random numbers
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        //
        //       Make sure that your random numbers are different from each
        //       other. This does not mean that you need to check that the numbers
        //       are different from each other. It means you should repeatedly call
        //       Math.random() - 0.5 to generate new random numbers for each array index.
        int length = buffer.items.length;
//        System.out.println("buffer items: " + length);
        buffer.clear(length);
        for (int i = 0; i < length; i++) {
            buffer.addFirst(Math.random() - 0.5);
        }
//        System.out.println(buffer.toList());
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // TODO: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       **Do not call StdAudio.play().**
        if (buffer.isEmpty()) {
            return;
        }
        Double double1 = buffer.removeFirst();
        Double double2 = buffer.get((buffer.getNextFirst() + 1) % buffer.getItemsLength());
        buffer.addLast(DECAY * (double1 + double2) / 2);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        // TODO: Return the correct thing.
        return buffer.get(buffer.getNextFirst());
    }
}
    // TODO: Remove all comments that say TODO when you're done.
