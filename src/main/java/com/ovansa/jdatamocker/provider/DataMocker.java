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

    /**
     * Gets the address generator instance.
     *
     * @return Address generator fluent API
     */
    JDataMocker.AddressGenerator address();

    /**
     * Gets the phone number generator instance.
     *
     * @return Phone number generator fluent API
     */
    JDataMocker.PhoneNumberGenerator phoneNumber();

    /**
     * Gets the company generator instance.
     *
     * @return Company generator fluent API
     */
    JDataMocker.CompanyGenerator company();

    /**
     * Gets the string generator instance.
     *
     * @return String generator fluent API
     */
    JDataMocker.StringGenerator string();
}