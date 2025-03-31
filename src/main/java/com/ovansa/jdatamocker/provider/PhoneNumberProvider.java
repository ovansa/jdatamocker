package com.ovansa.jdatamocker.provider;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides mock phone number data for testing purposes.
 * This class generates random phone numbers in a simplified format,
 * starting with "0" followed by a 9-digit number.
 *
 * @author Muhammed Ibrahim
 * @version 1.0
 * @since 1.0
 */
public class PhoneNumberProvider extends BaseProvider implements DataProvider {

    /**
     * Constructs a new {@code PhoneNumberProvider} with the specified random number generator.
     * The random number generator is used to create random phone numbers.
     *
     * @param random the {@link ThreadLocalRandom} instance to use for random number generation
     */
    public PhoneNumberProvider(ThreadLocalRandom random) {
        super(random);
    }

    /**
     * Generates a random phone number in a simplified format.
     * The phone number starts with "0" followed by a 9-digit number
     * ranging from 100000000 to 999999999.
     *
     * @return a randomly generated phone number as a string (e.g., "0123456789")
     */
    public String phoneNumber() {
        return "0" + (random.nextInt(900_000_000) + 100_000_000);
    }
}