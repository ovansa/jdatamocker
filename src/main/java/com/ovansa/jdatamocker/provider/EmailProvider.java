package com.ovansa.jdatamocker.provider;

import java.util.List;
import java.util.regex.Pattern;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides mock email addresses for testing purposes.
 * Generates personal and business email addresses with various domains, TLDs, and formats.
 * Operates independently without relying on other providers.
 *
 * @author Muhammed Ibrahim
 * @version 1.0.8
 * @since 1.0.8
 */
public class EmailProvider extends BaseProvider implements DataProvider {

    private static final Pattern COMPANY_NAME_CLEANER = Pattern.compile("[^A-Za-z0-9]");
    private static final List<String> PERSONAL_DOMAINS = List.of("gmail.com", "yahoo.com", "outlook.com", "hotmail.com", "example.com");
    private static final List<String> BUSINESS_PREFIXES = List.of("contact", "info", "sales", "support", "admin");
    private static final List<String> TLDS = List.of("com", "org", "net", "co.uk", "ng", "ca", "de", "fr", "jp");

    // Basic name data for personal emails
    private static final String[] FIRST_NAMES = {
            "John", "Emma", "Michael", "Sophie", "James", "Olivia", "David", "Grace", "Robert", "Ava",
            "William", "Isabella", "Thomas", "Mia", "Charles", "Amelia", "Joseph", "Charlotte", "Daniel", "Emily"
    };
    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Brown", "Taylor", "Wilson", "Davis", "Clark", "Lewis", "Walker", "Hall",
            "Allen", "Young", "King", "Wright", "Scott", "Green", "Adams", "Baker", "Nelson", "Carter"
    };

    // Basic company data for business emails
    private static final String[] COMPANIES = {
            "Acme", "Nexlify", "Techtron", "Globex", "Innovate", "Synergy", "Quantum", "Apex", "Vertex", "Omni",
            "Eco", "Bright", "Core", "Fusion", "Peak", "Pulse", "Vortex", "Strive", "Nova", "Zenith"
    };

    /**
     * Constructs a new {@code EmailProvider} with the specified random number generator.
     *
     * @param random the {@link ThreadLocalRandom} instance to use for random number generation
     */
    public EmailProvider(ThreadLocalRandom random) {
        super(random);
    }

    // ========================
    // Personal Email Generation
    // ========================

    /**
     * Generates a personal email address with a random domain.
     *
     * @return e.g., "john.smith@gmail.com"
     */
    public String personal() {
        String username = generateFullName().replace(" ", ".").toLowerCase();
        String domain = PERSONAL_DOMAINS.get(random.nextInt(PERSONAL_DOMAINS.size()));
        return username + "@" + domain;
    }

    /**
     * Generates a personal email address with a custom domain.
     *
     * @param domain the custom domain (e.g., "mydomain.com")
     * @return e.g., "john.smith@mydomain.com"
     * @throws IllegalArgumentException if domain is null or empty
     */
    public String personalWithDomain(String domain) {
        validateDomain(domain);
        String username = generateFullName().replace(" ", ".").toLowerCase();
        return username + "@" + domain;
    }

    /**
     * Generates a personal email address with a random username format.
     *
     * @return e.g., "jsmith123@yahoo.com"
     */
    public String personalRandomFormat() {
        String firstName = getRandom(FIRST_NAMES).toLowerCase();
        String lastName = getRandom(LAST_NAMES).toLowerCase();
        String domain = PERSONAL_DOMAINS.get(random.nextInt(PERSONAL_DOMAINS.size()));

        int format = random.nextInt(4);
        String username = switch (format) {
            case 0 -> firstName + "." + lastName;             // "john.smith"
            case 1 -> firstName.charAt(0) + lastName;         // "jsmith"
            case 2 -> firstName + lastName + random.nextInt(100); // "johnsmith42"
            default -> lastName + "." + firstName.charAt(0);  // "smith.j"
        };
        return username + "@" + domain;
    }

    // ========================
    // Business Email Generation
    // ========================

    /**
     * Generates a business email address with a random company and TLD.
     *
     * @return e.g., "sales@acme.com"
     */
    public String business() {
        String company = COMPANY_NAME_CLEANER.matcher(getRandom(COMPANIES)).replaceAll("").toLowerCase();
        String prefix = BUSINESS_PREFIXES.get(random.nextInt(BUSINESS_PREFIXES.size()));
        String tld = TLDS.get(random.nextInt(TLDS.size()));
        return prefix + "@" + company + "." + tld;
    }

    /**
     * Generates a business email address for a specific country.
     *
     * @param countryCode the country code (e.g., "US", "UK", "NG")
     * @return e.g., "info@acme.co.uk" (UK)
     */
    public String businessByCountry(String countryCode) {
        String company = COMPANY_NAME_CLEANER.matcher(getRandom(COMPANIES)).replaceAll("").toLowerCase();
        String prefix = BUSINESS_PREFIXES.get(random.nextInt(BUSINESS_PREFIXES.size()));
        String tld = getTldForCountry(countryCode);
        return prefix + "@" + company + "." + tld;
    }

    /**
     * Generates a business email address with a custom company and TLD.
     *
     * @param companyName the company name
     * @param tld         the top-level domain (e.g., "com", "org")
     * @return e.g., "support@mycompany.org"
     * @throws IllegalArgumentException if companyName or tld is null or empty
     */
    public String businessWithCustom(String companyName, String tld) {
        if (companyName == null || companyName.trim().isEmpty()) {
            throw new IllegalArgumentException("Company name must not be null or empty");
        }
        validateTld(tld);
        String cleanCompany = COMPANY_NAME_CLEANER.matcher(companyName).replaceAll("").toLowerCase();
        String prefix = BUSINESS_PREFIXES.get(random.nextInt(BUSINESS_PREFIXES.size()));
        return prefix + "@" + cleanCompany + "." + tld;
    }

    // ========================
    // Validation
    // ========================

    /**
     * Validates if a string is a plausible email address.
     *
     * @param email the email to validate
     * @return true if it matches a basic email pattern
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    // ========================
    // Helper Methods
    // ========================

    private String generateFullName() {
        return getRandom(FIRST_NAMES) + " " + getRandom(LAST_NAMES);
    }

    private String getRandom(String[] array) {
        return array[random.nextInt(array.length)];
    }

    private void validateDomain(String domain) {
        if (domain == null || domain.trim().isEmpty() || !domain.contains(".")) {
            throw new IllegalArgumentException("Domain must not be null, empty, or missing a TLD");
        }
    }

    private void validateTld(String tld) {
        if (tld == null || tld.trim().isEmpty()) {
            throw new IllegalArgumentException("TLD must not be null or empty");
        }
    }

    private String getTldForCountry(String countryCode) {
        return switch (countryCode.toUpperCase()) {
            case "UK" -> "co.uk";
            case "NG" -> "ng";
            case "CA" -> "ca";
            case "DE" -> "de";
            case "FR" -> "fr";
            case "JP" -> "jp";
            default -> "com";
        };
    }
}