package com.ovansa.jdatamocker.provider;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides mock numerical data for testing purposes.
 * This class generates random numbers, including general random numbers,
 * even numbers, and odd numbers, within a specified range.
 *
 * @author Muhammed Ibrahim
 * @version 1.0
 * @since 1.0
 */
public class NumberProvider extends BaseProvider implements DataProvider {

    /**
     * Constructs a new {@code NumberProvider} with the specified random number generator.
     * The random number generator is used to generate random numbers within specified ranges.
     *
     * @param random the {@link ThreadLocalRandom} instance to use for random number generation
     */
    public NumberProvider(ThreadLocalRandom random) {
        super(random);
    }

    /**
     * Generates a random integer within the specified range, inclusive.
     *
     * @param min the minimum value (inclusive) of the range
     * @param max the maximum value (inclusive) of the range
     * @return a random integer between {@code min} and {@code max}, inclusive
     */
    public int randomNumber(int min, int max) {
        return random.nextInt(min, max + 1);
    }

    /**
     * Generates a random even integer within the specified range, inclusive.
     * If the randomly generated number is odd, it is adjusted to the nearest even number
     * within the range by either adding or subtracting 1.
     *
     * @param min the minimum value (inclusive) of the range
     * @param max the maximum value (inclusive) of the range
     * @return a random even integer between {@code min} and {@code max}, inclusive
     */
    public int randomEvenNumber(int min, int max) {
        int number = randomNumber(min, max);
        return (number % 2 == 0) ? number : number + 1 > max ? number - 1 : number + 1;
    }

    /**
     * Generates a random odd integer within the specified range, inclusive.
     * If the randomly generated number is even, it is adjusted to the nearest odd number
     * within the range by either adding or subtracting 1.
     *
     * @param min the minimum value (inclusive) of the range
     * @param max the maximum value (inclusive) of the range
     * @return a random odd integer between {@code min} and {@code max}, inclusive
     */
    public int randomOddNumber(int min, int max) {
        int number = randomNumber(min, max);
        return (number % 2 != 0) ? number : number + 1 > max ? number - 1 : number + 1;
    }
}