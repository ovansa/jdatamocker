package com.ovansa.jdatamocker;

import com.ovansa.jdatamocker.provider.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A comprehensive utility class for generating realistic mock data for testing purposes.
 * Provides fluent access to various data generators including:
 * <ul>
 *   <li>Names (multiple cultures and formats)</li>
 *   <li>Addresses (international formats)</li>
 *   <li>Phone numbers (country-specific formats)</li>
 *   <li>Companies (by industry and region)</li>
 *   <li>Dates (with various constraints)</li>
 *   <li>Numbers (with mathematical properties)</li>
 *   <li>Strings (configurable patterns)</li>
 *   <li>Usernames (multiple generation strategies)</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>{@code
 * JDataMocker mocker = new JDataMocker();
 * String name = mocker.name().arabic();
 * LocalDate birthDate = mocker.date().past(30); // Date up to 30 years ago
 * String email = mocker.email().business();
 * }</pre>
 *
 * @author Muhammed Ibrahim
 * @version 2.0
 * @since 1.0
 */
public class JDataMocker {

    private final ThreadLocalRandom random;
    private final Map<String, DataProvider> providers = new HashMap<>();

    private final NameGenerator nameGenerator;
    private final DateGenerator dateGenerator;
    private final EmailGenerator emailGenerator;
    private final NumberGenerator numberGenerator;
    private final UsernameGenerator usernameGenerator;

    /**
     * Constructs a new {@code JDataMocker} with default locale (English).
     */
    public JDataMocker() {
        this(Locale.ENGLISH);
    }

    /**
     * Constructs a new {@code JDataMocker} with specified locale.
     *
     * @param locale the locale to use for localized data generation
     */
    public JDataMocker(Locale locale) {
        this.random = ThreadLocalRandom.current();
        registerProviders();

        // Initialize fluent API components
        this.nameGenerator = new NameGenerator(this);
        this.dateGenerator = new DateGenerator(this);
        this.emailGenerator = new EmailGenerator(this);
        this.numberGenerator = new NumberGenerator(this);
        this.usernameGenerator = new UsernameGenerator(this);
    }

    private void registerProviders() {
        providers.put("name", new NameProvider(random));
        providers.put("address", new AddressProvider(random));
        providers.put("phoneNumber", new PhoneNumberProvider(random));
        providers.put("company", new CompanyProvider(random));
        providers.put("date", new DateProvider(random));
        providers.put("number", new NumberProvider(random));
        providers.put("string", new StringProvider(random));
        providers.put("username", new UsernameProvider(random));
    }

    // Fluent API accessors
    public NameGenerator name() { return nameGenerator; }
    public DateGenerator date() { return dateGenerator; }
    public EmailGenerator email() { return emailGenerator; }
    public NumberGenerator number() { return numberGenerator; }
    public UsernameGenerator username() { return usernameGenerator; }

    /**
     * Gets a specific provider by name.
     *
     * @param providerName the name of the provider
     * @return the requested DataProvider
     * @throws IllegalArgumentException if provider not found
     */
    public DataProvider getProvider(String providerName) {
        DataProvider provider = providers.get(providerName);
        if (provider == null) {
            throw new IllegalArgumentException("No provider found for: " + providerName);
        }
        return provider;
    }

    // Fluent API inner classes
    public static class NameGenerator {
        private final JDataMocker mocker;

        NameGenerator(JDataMocker mocker) { this.mocker = mocker; }

        public String nigerian() { return ((NameProvider) mocker.getProvider("name")).nigerianName(); }
        public String arabic() { return ((NameProvider) mocker.getProvider("name")).arabicName(); }
        public String western() { return ((NameProvider) mocker.getProvider("name")).getName(NameProvider.Region.WESTERN); }
        public String fullName() { return ((NameProvider) mocker.getProvider("name")).getName(); }
    }

    public static class DateGenerator {
        private final JDataMocker mocker;

        DateGenerator(JDataMocker mocker) { this.mocker = mocker; }

        public LocalDate random() { return ((DateProvider) mocker.getProvider("date")).randomDate(); }
        public LocalDate past(int maxYears) { return ((DateProvider) mocker.getProvider("date")).randomPastDate(maxYears); }
        public LocalDate future(int maxYears) { return ((DateProvider) mocker.getProvider("date")).randomFutureDate(maxYears); }
        public LocalDate birthday(int age) { return ((DateProvider) mocker.getProvider("date")).randomBirthDate(age); }
    }

    public static class EmailGenerator {
        private final JDataMocker mocker;

        EmailGenerator(JDataMocker mocker) { this.mocker = mocker; }

        public String personal() {
            return mocker.name().fullName().replace(" ", ".").toLowerCase() + "@example.com";
        }

        public String business() {
            String company = ((CompanyProvider) mocker.getProvider("company"))
                    .randomCompany(Continent.AMERICA)
                    .replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
            return "contact@" + company + ".com";
        }
    }

    public static class NumberGenerator {
        private final JDataMocker mocker;

        NumberGenerator(JDataMocker mocker) { this.mocker = mocker; }

        public int integer(int min, int max) { return ((NumberProvider) mocker.getProvider("number")).randomNumber(min, max); }
        public int even(int min, int max) { return ((NumberProvider) mocker.getProvider("number")).randomEvenNumber(min, max); }
        public int odd(int min, int max) { return ((NumberProvider) mocker.getProvider("number")).randomOddNumber(min, max); }
        public double decimal(double min, double max) { return ((NumberProvider) mocker.getProvider("number")).randomDecimal(min, max); }
        public double percentage() { return ((NumberProvider) mocker.getProvider("number")).randomPercentage(2); }
    }

    public static class UsernameGenerator {
        private final JDataMocker mocker;

        UsernameGenerator(JDataMocker mocker) { this.mocker = mocker; }

        public String random() { return ((UsernameProvider) mocker.getProvider("username")).randomUsername(); }
        public String nameBased() { return ((UsernameProvider) mocker.getProvider("username")).randomNameUsername(); }
        public String custom(int length, boolean specialChars) {
            return ((UsernameProvider) mocker.getProvider("username")).randomCustomUsername(length, specialChars, true);
        }
    }
}