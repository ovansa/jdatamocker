package com.ovansa.jdatamocker.provider;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides mock username data for testing purposes.
 * This class generates random usernames by combining an adjective, a noun,
 * and a random number to create unique and realistic usernames.
 *
 * @author Muhammed Ibrahim
 * @version 1.0
 * @since 1.0
 */
public class UsernameProvider extends BaseProvider implements DataProvider {

    /** Array of adjectives used for generating random usernames. */
    private static final String[] ADJECTIVES = {"Cool", "Smart", "Fast", "Brave", "Witty", "Mighty", "Fierce", "Bold", "Sly", "Energetic"};

    /** Array of nouns used for generating random usernames. */
    private static final String[] NOUNS = {"Tiger", "Eagle", "Shark", "Panther", "Wolf", "Dragon", "Falcon", "Cheetah", "Viper", "Hawk"};

    /**
     * Constructs a new {@code UsernameProvider} with the specified random number generator.
     * The random number generator is used to select random adjectives, nouns, and numbers.
     *
     * @param random the {@link ThreadLocalRandom} instance to use for random number generation
     */
    public UsernameProvider(ThreadLocalRandom random) {
        super(random);
    }

    /**
     * Generates a random username by combining an adjective, a noun, and a random number.
     * The adjective and noun are selected from predefined lists, and the number is a random
     * integer between 100 and 9998 (inclusive).
     *
     * @return a randomly generated username in the format "adjectiveNounNumber"
     *         (e.g., "CoolTiger123")
     */
    public String randomUsername() {
        return getRandom(ADJECTIVES) + getRandom(NOUNS) + random.nextInt(100, 9999);
    }

    /**
     * Selects a random element from the provided array of strings.
     * This method is used internally to pick random adjectives and nouns from the predefined lists.
     *
     * @param array the array of strings to select from
     * @return a randomly selected string from the array
     */
    private String getRandom(String[] array) {
        return array[random.nextInt(array.length)];
    }
}