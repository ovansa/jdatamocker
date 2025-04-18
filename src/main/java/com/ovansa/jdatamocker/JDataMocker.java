package com.ovansa.jdatamocker;

import com.ovansa.jdatamocker.provider.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

/**
 * Fluent API for generating thread-safe mock data.
 *
 * @author Muhammed Ibrahim
 * @version 2.1
 * @since 1.0
 */
public class JDataMocker implements DataMocker {
    private final ThreadLocalRandom random;
    private final Map<String, DataProvider> providers;

    private static final Pattern COMPANY_NAME_CLEANER = Pattern.compile("[^a-zA-Z0-9]");

    /**
     * Constructs a new JDataMocker instance with default providers.
     */
    public JDataMocker() {
        this.random = ThreadLocalRandom.current();
        this.providers = registerProviders();
    }

    private Map<String, DataProvider> registerProviders() {
        Map<String, DataProvider> providers = new HashMap<>();

        providers.put("name", new NameProvider(random));
        providers.put("address", new AddressProvider(random));
        providers.put("phoneNumber", new PhoneNumberProvider(random));
        providers.put("company", new CompanyProvider(random));
        providers.put("date", new DateProvider(random));
        providers.put("number", new NumberProvider(random));
        providers.put("string", new StringProvider(random));
        providers.put("username", new UsernameProvider(random));
        providers.put("email", new EmailProvider (random));

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

    @Override
    public AddressGenerator address() {
        return new AddressGenerator(this);
    }

    @Override
    public PhoneNumberGenerator phoneNumber() {
        return new PhoneNumberGenerator(this);
    }

    @Override
    public CompanyGenerator company() {
        return new CompanyGenerator(this);
    }

    @Override
    public StringGenerator string() {
        return new StringGenerator(this);
    }

    /**
     * Retrieves a provider by name.
     *
     * @param providerName name of the provider
     * @return the DataProvider instance
     * @throws ProviderNotFoundException if provider not found
     */
    public DataProvider getProvider(String providerName) {
        return Optional.ofNullable(providers.get(providerName))
                .orElseThrow(() -> new ProviderNotFoundException(providerName));
    }

    /**
     * Fluent API for generating names.
     */
    public static final class NameGenerator {
        private final JDataMocker mocker;

        private NameGenerator(JDataMocker mocker) {
            this.mocker = mocker;
        }

        /**
         * Generates a random Nigerian name.
         *
         * @return e.g., "Emeka Okafor"
         */
        public String nigerian() {
            return ((NameProvider) mocker.getProvider("name")).nigerianName();
        }

        /**
         * Generates a random Arabic name.
         *
         * @return e.g., "Fatima Al-Maktoum"
         */
        public String arabic() {
            return ((NameProvider) mocker.getProvider("name")).arabicName();
        }

        /**
         * Generates a random Western name.
         *
         * @return e.g., "Michael Smith"
         */
        public String western() {
            return ((NameProvider) mocker.getProvider("name")).westernName();
        }

        /**
         * Generates a random Asian name.
         *
         * @return e.g., "Wei Wang"
         */
        public String asian() {
            return ((NameProvider) mocker.getProvider("name")).getName(NameProvider.Region.ASIAN);
        }

        /**
         * Generates a random European name.
         *
         * @return e.g., "Jean Martin"
         */
        public String european() {
            return ((NameProvider) mocker.getProvider("name")).getName(NameProvider.Region.EUROPEAN);
        }

        /**
         * Generates a random full name (Western by default).
         *
         * @return e.g., "John Smith"
         */
        public String fullName() {
            return ((NameProvider) mocker.getProvider("name")).getName();
        }
    }

    /**
     * Fluent API for generating dates.
     */
    public static final class DateGenerator {
        private final JDataMocker mocker;

        private DateGenerator(JDataMocker mocker) {
            this.mocker = mocker;
        }

        /**
         * Generates a random date.
         *
         * @return random LocalDate
         */
        public LocalDate random() {
            return ((DateProvider) mocker.getProvider("date")).randomDate();
        }

        /**
         * Generates a date up to maxYears in the past.
         *
         * @param maxYears maximum years back
         * @return random past LocalDate
         * @throws IllegalArgumentException if maxYears is negative
         */
        public LocalDate past(int maxYears) {
            validateYears(maxYears);
            return ((DateProvider) mocker.getProvider("date")).randomPastDate(maxYears);
        }

        /**
         * Generates a date up to maxYears in the future.
         *
         * @param maxYears maximum years ahead
         * @return random future LocalDate
         * @throws IllegalArgumentException if maxYears is negative
         */
        public LocalDate future(int maxYears) {
            validateYears(maxYears);
            return ((DateProvider) mocker.getProvider("date")).randomFutureDate(maxYears);
        }

        /**
         * Generates a birth date for a given age.
         *
         * @param age age in years
         * @return birth LocalDate
         * @throws IllegalArgumentException if age is negative
         */
        public LocalDate birthday(int age) {
            validateYears(age);
            return ((DateProvider) mocker.getProvider("date")).randomBirthDate(age);
        }

        private void validateYears(int years) {
            if (years < 0) {
                throw new IllegalArgumentException("Years must be positive");
            }
        }
    }

    /**
     * Fluent API for generating emails.
     */
    public static final class EmailGenerator {
        private final JDataMocker mocker;

        private EmailGenerator(JDataMocker mocker) {
            this.mocker = mocker;
        }

        public String personal() { return ((EmailProvider) mocker.getProvider("email")).personal(); }
        public String personalWithDomain(String domain) { return ((EmailProvider) mocker.getProvider("email")).personalWithDomain(domain); }
        public String personalRandomFormat() { return ((EmailProvider) mocker.getProvider("email")).personalRandomFormat(); }
        public String business() { return ((EmailProvider) mocker.getProvider("email")).business(); }
        public String businessByCountry(String countryCode) { return ((EmailProvider) mocker.getProvider("email")).businessByCountry(countryCode); }
        public String businessWithCustom(String companyName, String tld) { return ((EmailProvider) mocker.getProvider("email")).businessWithCustom(companyName, tld); }
        public boolean isValidEmail(String email) { return ((EmailProvider) mocker.getProvider("email")).isValidEmail(email); }
    }

    /**
     * Fluent API for generating numbers.
     */
    public static final class NumberGenerator {
        private final JDataMocker mocker;

        private NumberGenerator(JDataMocker mocker) {
            this.mocker = mocker;
        }

        /**
         * Generates a random integer in range [min, max].
         *
         * @param min minimum value (inclusive)
         * @param max maximum value (inclusive)
         * @return random integer
         */
        public int integer(int min, int max) {
            return ((NumberProvider) mocker.getProvider("number")).randomNumber(min, max);
        }

        /**
         * Generates a random even integer in range [min, max].
         *
         * @param min minimum value (inclusive)
         * @param max maximum value (inclusive)
         * @return random even integer
         */
        public int even(int min, int max) {
            return ((NumberProvider) mocker.getProvider("number")).randomEvenNumber(min, max);
        }

        /**
         * Generates a random odd integer in range [min, max].
         *
         * @param min minimum value (inclusive)
         * @param max maximum value (inclusive)
         * @return random odd integer
         */
        public int odd(int min, int max) {
            return ((NumberProvider) mocker.getProvider("number")).randomOddNumber(min, max);
        }

        /**
         * Generates a random decimal in range [min, max].
         *
         * @param min minimum value (inclusive)
         * @param max maximum value (inclusive)
         * @return random decimal
         */
        public double decimal(double min, double max) {
            return ((NumberProvider) mocker.getProvider("number")).randomDecimal(min, max);
        }

        /**
         * Generates a random percentage (0.0 to 100.0).
         *
         * @return random percentage
         */
        public double percentage() {
            return ((NumberProvider) mocker.getProvider("number")).randomPercentage(2);
        }
    }

    /**
     * Fluent API for generating usernames.
     */
    public static final class UsernameGenerator {
        private final JDataMocker mocker;

        private UsernameGenerator(JDataMocker mocker) {
            this.mocker = mocker;
        }

        /**
         * Generates a random username.
         *
         * @return random username
         */
        public String random() {
            return ((UsernameProvider) mocker.getProvider("username")).randomUsername();
        }

        /**
         * Generates a name-based username.
         *
         * @return username derived from a name
         */
        public String nameBased() {
            return ((UsernameProvider) mocker.getProvider("username")).randomNameUsername();
        }

        /**
         * Generates a custom username with specified length and special characters.
         *
         * @param length       desired length
         * @param specialChars include special characters if true
         * @return custom username
         */
        public String custom(int length, boolean specialChars) {
            return ((UsernameProvider) mocker.getProvider("username")).randomCustomUsername(length, specialChars, true);
        }
    }

    /**
     * Fluent API for generating addresses.
     */
    public static final class AddressGenerator {
        private final JDataMocker mocker;

        private AddressGenerator(JDataMocker mocker) {
            this.mocker = mocker;
        }

        public String fullAddress() { return ((AddressProvider) mocker.getProvider("address")).fullAddress(); }
        public String fullAddress(String countryCode) { return ((AddressProvider) mocker.getProvider("address")).fullAddress(countryCode); }
        public String streetAddress(String countryCode) { return ((AddressProvider) mocker.getProvider("address")).streetAddress(countryCode); }
        public String city(String countryCode) { return ((AddressProvider) mocker.getProvider("address")).city(countryCode); }
        public String state(String countryCode) { return ((AddressProvider) mocker.getProvider("address")).state(countryCode); }
        public String postalCode(String countryCode) { return ((AddressProvider) mocker.getProvider("address")).postalCode(countryCode); }
        public String fullAddressWithApartment(String countryCode) { return ((AddressProvider) mocker.getProvider("address")).fullAddressWithApartment(countryCode); }
        public String randomCountryAddress() { return ((AddressProvider) mocker.getProvider("address")).randomCountryAddress(); }
        public boolean isValidAddress(String address, String countryCode) { return ((AddressProvider) mocker.getProvider("address")).isValidAddress(address, countryCode); }
    }

    /**
     * Fluent API for generating phone numbers.
     */
    public static final class PhoneNumberGenerator {
        private final JDataMocker mocker;

        private PhoneNumberGenerator(JDataMocker mocker) {
            this.mocker = mocker;
        }

        public String phoneNumber() { return ((PhoneNumberProvider) mocker.getProvider("phoneNumber")).phoneNumber(); }
        public String phoneNumber(String countryCode) { return ((PhoneNumberProvider) mocker.getProvider("phoneNumber")).phoneNumber(countryCode); }
        public String international() { return ((PhoneNumberProvider) mocker.getProvider("phoneNumber")).international(); }
        public String international(String countryCode) { return ((PhoneNumberProvider) mocker.getProvider("phoneNumber")).international(countryCode); }
        public String formatted() { return ((PhoneNumberProvider) mocker.getProvider("phoneNumber")).formatted(); }
        public String formatted(String countryCode) { return ((PhoneNumberProvider) mocker.getProvider("phoneNumber")).formatted(countryCode); }
        public String customFormatted(String countryCode, String pattern) { return ((PhoneNumberProvider) mocker.getProvider("phoneNumber")).customFormatted(countryCode, pattern); }
        public String mobile() { return ((PhoneNumberProvider) mocker.getProvider("phoneNumber")).mobile(); }
        public String mobile(String countryCode) { return ((PhoneNumberProvider) mocker.getProvider("phoneNumber")).mobile(countryCode); }
        public String landline() { return ((PhoneNumberProvider) mocker.getProvider("phoneNumber")).landline(); }
        public String landline(String countryCode) { return ((PhoneNumberProvider) mocker.getProvider("phoneNumber")).landline(countryCode); }
        public String tollFree(String countryCode) { return ((PhoneNumberProvider) mocker.getProvider("phoneNumber")).tollFree(countryCode); }
        public String premiumRate(String countryCode) { return ((PhoneNumberProvider) mocker.getProvider("phoneNumber")).premiumRate(countryCode); }
        public String withExtension(String countryCode, int extensionLength) { return ((PhoneNumberProvider) mocker.getProvider("phoneNumber")).withExtension(countryCode, extensionLength); }
        public String randomCountry() { return ((PhoneNumberProvider) mocker.getProvider("phoneNumber")).randomCountry(); }
        public boolean isValidPhoneNumber(String input) { return ((PhoneNumberProvider) mocker.getProvider("phoneNumber")).isValidPhoneNumber(input); }
        public boolean isValidForCountry(String phoneNumber, String countryCode) { return ((PhoneNumberProvider) mocker.getProvider("phoneNumber")).isValidForCountry(phoneNumber, countryCode); }
        public List<String> getSupportedCountries() { return ((PhoneNumberProvider) mocker.getProvider("phoneNumber")).getSupportedCountries(); }
    }

    /**
     * Fluent API for generating company names.
     */
    public static final class CompanyGenerator {
        private final JDataMocker mocker;

        private CompanyGenerator(JDataMocker mocker) {
            this.mocker = mocker;
        }

        public String randomCompany(CompanyProvider.Continent continent) {
            return ((CompanyProvider) mocker.getProvider("company")).randomCompany(continent);
        }
        public String randomCompanyByCountry(String countryCode) {
            return ((CompanyProvider) mocker.getProvider("company")).randomCompanyByCountry(countryCode);
        }
        public String randomCompanyByIndustry(CompanyProvider.Industry industry, boolean withSuffix) {
            return ((CompanyProvider) mocker.getProvider("company")).randomCompanyByIndustry(industry, withSuffix);
        }
        public String randomGeneratedCompany(boolean withSuffix) {
            return ((CompanyProvider) mocker.getProvider("company")).randomGeneratedCompany(withSuffix);
        }
        public String randomFromCustomList(List<String> customCompanies) {
            return ((CompanyProvider) mocker.getProvider("company")).randomFromCustomList(customCompanies);
        }
        public boolean isValidCompanyName(String name) {
            return ((CompanyProvider) mocker.getProvider("company")).isValidCompanyName(name);
        }
    }

    /**
     * Fluent API for generating random strings.
     */
    public static final class StringGenerator {
        private final JDataMocker mocker;

        private StringGenerator(JDataMocker mocker) {
            this.mocker = mocker;
        }

        public String alphabetic(int length) { return ((StringProvider) mocker.getProvider("string")).alphabetic(length); }
        public String numeric(int length) { return ((StringProvider) mocker.getProvider("string")).numeric(length); }
        public String alphanumeric(int length) { return ((StringProvider) mocker.getProvider("string")).alphanumeric(length); }
        public String withSpecialChars(int length) { return ((StringProvider) mocker.getProvider("string")).withSpecialChars(length); }
        public String hex(int length) { return ((StringProvider) mocker.getProvider("string")).hex(length); }
        public String custom(int length, String charset) { return ((StringProvider) mocker.getProvider("string")).custom(length, charset); }
        public String fromPattern(String pattern) { return ((StringProvider) mocker.getProvider("string")).fromPattern(pattern); }
        public String firstName() { return ((StringProvider) mocker.getProvider("string")).firstName(); }
        public String lastName() { return ((StringProvider) mocker.getProvider("string")).lastName(); }
        public String fullName() { return ((StringProvider) mocker.getProvider("string")).fullName(); }
        public String email() { return ((StringProvider) mocker.getProvider("string")).email(); }
        public String lorem(int words) { return ((StringProvider) mocker.getProvider("string")).lorem(words); }
        public String sentence(int words) { return ((StringProvider) mocker.getProvider("string")).sentence(words); }
        public String paragraph(int sentences) { return ((StringProvider) mocker.getProvider("string")).paragraph(sentences); }
        public String uuid() { return ((StringProvider) mocker.getProvider("string")).uuid(); }
        public String guid() { return ((StringProvider) mocker.getProvider("string")).guid(); }
        public String uuidLike() { return ((StringProvider) mocker.getProvider("string")).uuidLike(); }
        public String password(int length) { return ((StringProvider) mocker.getProvider("string")).password(length); }
        public String strongPassword() { return ((StringProvider) mocker.getProvider("string")).strongPassword(); }
        public String filePath() { return ((StringProvider) mocker.getProvider("string")).filePath(); }
        public String url() { return ((StringProvider) mocker.getProvider("string")).url(); }
        public boolean isEmail(String input) { return ((StringProvider) mocker.getProvider("string")).isEmail(input); }
    }
}