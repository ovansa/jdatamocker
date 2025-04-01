package com.ovansa.jdatamocker.provider;

import com.ovansa.jdatamocker.JDataMocker;

/**
 * Main interface for the mock data generation fluent API.
 * Provides access to all data generator types through method chaining.
 */
public interface DataMocker {
    /**
     * Gets the name generator instance.
     *
     * @return Name generator fluent API
     */
    JDataMocker.NameGenerator name();

    /**
     * Gets the date generator instance.
     *
     * @return Date generator fluent API
     */
    JDataMocker.DateGenerator date();

    /**
     * Gets the email generator instance.
     *
     * @return Email generator fluent API
     */
    JDataMocker.EmailGenerator email();

    /**
     * Gets the number generator instance.
     *
     * @return Number generator fluent API
     */
    JDataMocker.NumberGenerator number();

    /**
     * Gets the username generator instance.
     *
     * @return Username generator fluent API
     */
    JDataMocker.UsernameGenerator username();
}
