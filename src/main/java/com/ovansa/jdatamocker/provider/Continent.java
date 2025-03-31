package com.ovansa.jdatamocker.provider;

/**
 * Enum representing different continents for use in mock data generation.
 * This enum is used to categorize data (e.g., company names) by geographic region,
 * with a special {@code GLOBAL} value for data that applies worldwide.
 *
 * @author Muhammed Ibrahim
 * @version 1.0
 * @since 1.0
 */
public enum Continent {
    /**
     * Represents the continent of Africa.
     */
    AFRICA,

    /**
     * Represents the continent of America (North, Central, or South).
     */
    AMERICA,

    /**
     * Represents the continent of Europe.
     */
    EUROPE,

    /**
     * Represents the continent of Asia.
     */
    ASIA,

    /**
     * Represents the continent of Australia.
     */
    AUSTRALIA,

    /**
     * Represents a global category, applicable worldwide.
     */
    GLOBAL
}