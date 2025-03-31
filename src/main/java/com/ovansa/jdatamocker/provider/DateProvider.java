package com.ovansa.jdatamocker.provider;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides mock date data for testing purposes.
 * This class generates random dates within a specified range (from January 1, 1900, to December 31, 2023)
 * to simulate realistic date data for use in testing environments.
 *
 * @author Muhammed Ibrahim
 * @version 1.0
 * @since 1.0
 */
public class DateProvider extends BaseProvider implements DataProvider {

    /**
     * Constructs a new {@code DateProvider} with the specified random number generator.
     * The random number generator is used to select random dates within the defined range.
     *
     * @param random the {@link ThreadLocalRandom} instance to use for random number generation
     */
    public DateProvider(ThreadLocalRandom random) {
        super(random);
    }

    /**
     * Generates a random date between January 1, 1900, and December 31, 2023.
     * The date is selected by calculating a random day within the range of epoch days
     * between the minimum and maximum dates.
     *
     * @return a randomly generated {@link LocalDate} within the specified range
     */
    public LocalDate randomDate() {
        long minDay = LocalDate.of(1900, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2023, 12, 31).toEpochDay();
        return LocalDate.ofEpochDay(minDay + random.nextInt((int) (maxDay - minDay)));
    }
}