package com.ovansa.jdatamocker;

import com.ovansa.jdatamocker.provider.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

/**
 * Fluent API for generating mock data with thread-safe implementation.
 *
 * <p>Example usage:
 * <pre>{@code
 * JDataMocker mocker = new JDataMocker.Builder()
 *     .withLocale(Locale.US)
 *     .withCustomProvider("custom", new CustomDataProvider())
 *     .build();
 *
 * String email = mocker.email().business();
 * LocalDate date = mocker.date().past(5); // Date within last 5 years
 * }</pre>
 *
 * @author Muhammed Ibrahim
 * @version 2.1
 * @since 1.0
 */
public class JDataMocker implements DataMocker {

    private final ThreadLocalRandom random;
    private final Map<String, DataProvider> providers;
    private final Locale locale;

    private static final Pattern COMPANY_NAME_CLEANER = Pattern.compile("[^a-zA-Z0-9]");

    /**
     * Builder pattern for configuring JDataMocker instances.
     * Allows customization of locale and providers before building the JDataMocker.
     *
     * <p>Example usage:
     * <pre>{@code
     * JDataMocker mocker = new JDataMocker.Builder()
     *     .withLocale(Locale.FRENCH)
     *     .build();
     * }</pre>
     */
    public static class Builder {
        private Locale locale = Locale.ENGLISH;
        private final Map<String, DataProvider> customProviders = new HashMap<>();

        /**
         * Constructs a new Builder with default configuration:
         * - Locale: English
         * - No custom providers
         */
        public Builder() {
        }

        /**
         * Sets the locale for data generation.
         *
         * @param locale the locale to use
         * @return this builder instance
         */
        public Builder withLocale(Locale locale) {
            this.locale = Objects.requireNonNull(locale, "Locale must not be null");
            return this;
        }

        /**
         * Adds a custom data provider.
         *
         * @param name     the provider name
         * @param provider the provider instance
         * @return this builder instance
         */
        public Builder withCustomProvider(String name, DataProvider provider) {
            this.customProviders.put(
                    Objects.requireNonNull(name, "Provider name must not be null"),
                    Objects.requireNonNull(provider, "Provider must not be null")
            );
            return this;
        }

        /**
         * Builds the {@code JDataMocker} instance.
         *
         * @return new JDataMocker instance
         */
        public JDataMocker build() {
            return new JDataMocker(this);
        }
    }

    private JDataMocker(Builder builder) {
        this.random = ThreadLocalRandom.current();
        this.locale = builder.locale;
        this.providers = registerProviders(builder.customProviders);
    }

    private Map<String, DataProvider> registerProviders(Map<String, DataProvider> customProviders) {
        Map<String, DataProvider> providers = new HashMap<>(customProviders);

        // Register default providers if not overridden
        providers.putIfAbsent("name", new NameProvider(random));
        providers.putIfAbsent("address", new AddressProvider(random));
        providers.putIfAbsent("phoneNumber", new PhoneNumberProvider(random));
        providers.putIfAbsent("company", new CompanyProvider(random));
        providers.putIfAbsent("date", new DateProvider(random));
        providers.putIfAbsent("number", new NumberProvider(random));
        providers.putIfAbsent("string", new StringProvider(random));
        providers.putIfAbsent("username", new UsernameProvider(random));

        return Collections.unmodifiableMap(providers);
    }

    @Override
    public NameGenerator name() {
        return new NameGenerator(this);
    }

    @Override
    public DateGenerator date() {
        return new DateGenerator(this);
    }

    @Override
    public EmailGenerator email() {
        return new EmailGenerator(this);
    }

    @Override
    public NumberGenerator number() {
        return new NumberGenerator(this);
    }

    @Override
    public UsernameGenerator username() {
        return new UsernameGenerator(this);
    }

    /**
     * Gets a specific provider by name.
     *
     * @param providerName the name of the provider
     * @return the requested DataProvider
     * @throws ProviderNotFoundException if provider not found
     */
    public DataProvider getProvider(String providerName) {
        return Optional.ofNullable(providers.get(providerName))
                .orElseThrow(() -> new ProviderNotFoundException(providerName));
    }

    /**
     * Name generator fluent API.
     */
    public static final class NameGenerator {
        private final JDataMocker mocker;

        private NameGenerator(JDataMocker mocker) {
            this.mocker = mocker;
        }

        /**
         * Generates a Nigerian name.
         *
         * @return Random Nigerian name
         */
        public String nigerian() {
            return ((NameProvider) mocker.getProvider("name")).nigerianName();
        }

        /**
         * Generates an Arabic name.
         *
         * @return Random Arabic name
         */
        public String arabic() {
            return ((NameProvider) mocker.getProvider("name")).arabicName();
        }

        /**
         * Generates a Western name.
         *
         * @return Random Western name
         */
        public String western() {
            return ((NameProvider) mocker.getProvider("name"))
                    .getName(NameProvider.Region.WESTERN);
        }

        /**
         * Generates a full name in default format.
         *
         * @return Random full name
         */
        public String fullName() {
            return ((NameProvider) mocker.getProvider("name")).getName();
        }
    }

    /**
     * Date generator fluent API.
     */
    public static final class DateGenerator {
        private final JDataMocker mocker;

        private DateGenerator(JDataMocker mocker) {
            this.mocker = mocker;
        }

        /**
         * Generates a random date.
         *
         * @return Randomly generated date
         */
        public LocalDate random() {
            return ((DateProvider) mocker.getProvider("date")).randomDate();
        }

        /**
         * Generates a date in the past.
         *
         * @param maxYears Maximum years in the past
         * @return Random date up to maxYears ago
         */
        public LocalDate past(int maxYears) {
            validateYears(maxYears);
            return ((DateProvider) mocker.getProvider("date"))
                    .randomPastDate(maxYears);
        }

        /**
         * Generates a date in the future.
         *
         * @param maxYears Maximum years in the future
         * @return Random date up to maxYears ahead
         */
        public LocalDate future(int maxYears) {
            validateYears(maxYears);
            return ((DateProvider) mocker.getProvider("date"))
                    .randomFutureDate(maxYears);
        }

        /**
         * Generates a birth date for someone of specified age.
         *
         * @param age Desired age in years
         * @return Birth date that would make someone the specified age
         */
        public LocalDate birthday(int age) {
            validateYears(age);
            return ((DateProvider) mocker.getProvider("date"))
                    .randomBirthDate(age);
        }

        private void validateYears(int years) {
            if (years < 0) {
                throw new IllegalArgumentException("Years must be positive");
            }
        }
    }

    /**
     * Email generator fluent API.
     */
    public static final class EmailGenerator {
        private final JDataMocker mocker;

        private EmailGenerator(JDataMocker mocker) {
            this.mocker = mocker;
        }

        /**
         * Generates a personal email address.
         *
         * @return Random personal email
         */
        public String personal() {
            return mocker.name().fullName()
                    .replace(" ", ".")
                    .toLowerCase(mocker.locale) + "@example.com";
        }

        /**
         * Generates a business email address.
         *
         * @return Random business email
         */
        public String business() {
            String company = COMPANY_NAME_CLEANER.matcher(
                    ((CompanyProvider) mocker.getProvider("company"))
                            .randomCompany(Continent.AMERICA)
            ).replaceAll("").toLowerCase(mocker.locale);
            return "contact@" + company + ".com";
        }
    }

    /**
     * Number generator fluent API.
     */
    public static final class NumberGenerator {
        private final JDataMocker mocker;

        private NumberGenerator(JDataMocker mocker) {
            this.mocker = mocker;
        }

        /**
         * Generates a random integer between min and max (inclusive).
         *
         * @param min Minimum value (inclusive)
         * @param max Maximum value (inclusive)
         * @return Random integer in specified range
         */
        public int integer(int min, int max) {
            return ((NumberProvider) mocker.getProvider("number"))
                    .randomNumber(min, max);
        }

        /**
         * Generates a random even number between min and max (inclusive).
         *
         * @param min Minimum value (inclusive)
         * @param max Maximum value (inclusive)
         * @return Random even integer in specified range
         */
        public int even(int min, int max) {
            return ((NumberProvider) mocker.getProvider("number"))
                    .randomEvenNumber(min, max);
        }

        /**
         * Generates a random odd number between min and max (inclusive).
         *
         * @param min Minimum value (inclusive)
         * @param max Maximum value (inclusive)
         * @return Random odd integer in specified range
         */
        public int odd(int min, int max) {
            return ((NumberProvider) mocker.getProvider("number"))
                    .randomOddNumber(min, max);
        }

        /**
         * Generates a random decimal between min and max (inclusive).
         *
         * @param min Minimum value (inclusive)
         * @param max Maximum value (inclusive)
         * @return Random decimal in specified range
         */
        public double decimal(double min, double max) {
            return ((NumberProvider) mocker.getProvider("number"))
                    .randomDecimal(min, max);
        }

        /**
         * Generates a random percentage between 0.0 and 100.0.
         *
         * @return Random percentage value
         */
        public double percentage() {
            return ((NumberProvider) mocker.getProvider("number"))
                    .randomPercentage(2);
        }
    }

    /**
     * Username generator fluent API.
     */
    public static final class UsernameGenerator {
        private final JDataMocker mocker;

        private UsernameGenerator(JDataMocker mocker) {
            this.mocker = mocker;
        }

        /**
         * Generates a random username.
         *
         * @return Random username
         */
        public String random() {
            return ((UsernameProvider) mocker.getProvider("username"))
                    .randomUsername();
        }

        /**
         * Generates a username based on name patterns.
         *
         * @return Name-based username
         */
        public String nameBased() {
            return ((UsernameProvider) mocker.getProvider("username"))
                    .randomNameUsername();
        }

        /**
         * Generates a custom username with specified parameters.
         *
         * @param length       Desired username length
         * @param specialChars Whether to include special characters
         * @return Custom generated username
         */
        public String custom(int length, boolean specialChars) {
            return ((UsernameProvider) mocker.getProvider("username"))
                    .randomCustomUsername(length, specialChars, true);
        }
    }
}