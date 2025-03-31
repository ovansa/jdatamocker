package com.ovansa.jdatamocker.provider;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides mock string data for testing purposes.
 * This class generates random strings of specified lengths using a predefined
 * character set that includes uppercase letters, lowercase letters, and digits.
 *
 * @author Muhammed Ibrahim
 * @version 1.0
 * @since 1.0
 */
public class StringProvider extends BaseProvider implements DataProvider {

    /** The character set used for generating random strings, including letters and digits. */
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * Constructs a new {@code StringProvider} with the specified random number generator.
     * The random number generator is used to select random characters for string generation.
     *
     * @param random the {@link ThreadLocalRandom} instance to use for random number generation
     */
    public StringProvider(ThreadLocalRandom random) {
        super(random);
    }

    /**
     * Generates a random string of the specified length.
     * The string is composed of characters randomly selected from a predefined character set
     * that includes uppercase letters, lowercase letters, and digits.
     *
     * @param length the desired length of the random string
     * @return a randomly generated string of the specified length
     */
    public String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}