package com.ovansa.jdatamocker.provider;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides mock address data for testing purposes.
 * This class generates random addresses, such as street names and house numbers,
 * to simulate realistic address data for use in testing environments.
 *
 * @author [Muhammed Ibrahim]
 * @version 1.0
 * @since 1.0
 */
public class AddressProvider extends BaseProvider implements DataProvider {

    /** Array of street names used for generating random addresses. */
    private static final String[] STREET_NAMES = {"Main St", "Broadway", "Market St", "Park Ave"};

    /**
     * Constructs a new {@code AddressProvider} with the specified random number generator.
     * The random number generator is used to select random street names and house numbers.
     *
     * @param random the {@link ThreadLocalRandom} instance to use for random number generation
     */
    public AddressProvider(ThreadLocalRandom random) {
        super(random);
    }

    /**
     * Generates a random full address by combining a house number and a street name.
     * The house number is a random integer between 1 and 1000, and the street name is
     * randomly selected from a predefined list of street names.
     *
     * @return a randomly generated full address in the format "houseNumber streetName"
     *         (e.g., "123 Main St")
     */
    public String fullAddress() {
        return (random.nextInt(1000) + 1) + " " + getRandom();
    }

    /**
     * Selects a random element from the provided array of strings.
     * This method is used internally to pick a random street name from the predefined list.
     *
     * @return a randomly selected string from the array
     */
    private String getRandom() {
        return AddressProvider.STREET_NAMES[random.nextInt(AddressProvider.STREET_NAMES.length)];
    }
}