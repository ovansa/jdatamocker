package com.ovansa.jdatamocker.provider;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides mock name data for testing purposes.
 * This class generates random names, supporting both Nigerian and Arabic naming conventions,
 * by combining first and last names from predefined lists.
 *
 * @author Muhammed Ibrahim
 * @version 1.0
 * @since 1.0
 */
public class NameProvider extends BaseProvider implements DataProvider {

    /** Array of Nigerian first names for generating random Nigerian names. */
    private static final String[] NIGERIAN_FIRST_NAMES = {"Chinedu", "Amina", "Oluwatobi", "Fatima", "Emeka", "Zainab"};

    /** Array of Nigerian last names for generating random Nigerian names. */
    private static final String[] NIGERIAN_LAST_NAMES = {"Okoro", "Abdullahi", "Adeyemi", "Ibrahim", "Okafor", "Bello"};

    /** Array of Arabic first names for generating random Arabic names. */
    private static final String[] ARABIC_FIRST_NAMES = {"Mohammed", "Aisha", "Ahmed", "Fatima", "Ali", "Layla"};

    /** Array of Arabic last names for generating random Arabic names. */
    private static final String[] ARABIC_LAST_NAMES = {"Al-Saud", "Al-Farsi", "Khan", "Al-Maktoum", "Hassan", "Abbas"};

    /**
     * Constructs a new {@code NameProvider} with the specified random number generator.
     * The random number generator is used to select random first and last names.
     *
     * @param random the {@link ThreadLocalRandom} instance to use for random number generation
     */
    public NameProvider(ThreadLocalRandom random) {
        super(random);
    }

    /**
     * Generates a random Nigerian name by combining a first name and a last name.
     * The names are selected from predefined lists of Nigerian first and last names.
     *
     * @return a randomly generated Nigerian name in the format "firstName lastName"
     *         (e.g., "Chinedu Okoro")
     */
    public String nigerianName() {
        return getRandom(NIGERIAN_FIRST_NAMES) + " " + getRandom(NIGERIAN_LAST_NAMES);
    }

    /**
     * Generates a random Arabic name by combining a first name and a last name.
     * The names are selected from predefined lists of Arabic first and last names.
     *
     * @return a randomly generated Arabic name in the format "firstName lastName"
     *         (e.g., "Mohammed Al-Saud")
     */
    public String arabicName() {
        return getRandom(ARABIC_FIRST_NAMES) + " " + getRandom(ARABIC_LAST_NAMES);
    }

    /**
     * Selects a random element from the provided array of strings.
     * This method is used internally to pick random first and last names from the predefined lists.
     *
     * @param array the array of strings to select from
     * @return a randomly selected string from the array
     */
    private String getRandom(String[] array) {
        return array[random.nextInt(array.length)];
    }
}