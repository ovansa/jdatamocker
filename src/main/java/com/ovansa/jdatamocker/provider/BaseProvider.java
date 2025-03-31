package com.ovansa.jdatamocker.provider;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Abstract base class for data providers in the JDataMocker library.
 * This class provides a common foundation for all data provider implementations,
 * supplying a shared random number generator to ensure consistent randomization
 * across different provider types.
 *
 * @author Muhammed Ibrahim
 * @version 1.0
 * @since 1.0
 */
abstract class BaseProvider {

    /** The random number generator used for generating mock data. */
    protected final ThreadLocalRandom random;

    /**
     * Constructs a new {@code BaseProvider} with the specified random number generator.
     * This constructor is called by subclasses to initialize the shared random number generator.
     *
     * @param random the {@link ThreadLocalRandom} instance to use for random number generation
     */
    protected BaseProvider(ThreadLocalRandom random) {
        this.random = random;
    }
}