package com.ovansa.jdatamocker;

/**
 * Exception thrown when a provider is not found.
 */
public class ProviderNotFoundException extends IllegalArgumentException {
    /**
     * Creates exception for missing provider.
     *
     * @param providerName Name of missing provider
     */
    public ProviderNotFoundException(String providerName) {
        super("No provider found for: " + providerName);
    }
}
