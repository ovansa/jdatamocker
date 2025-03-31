package com.ovansa.jdatamocker;

import com.ovansa.jdatamocker.provider.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A utility class for generating mock data for testing purposes.
 * This class provides a centralized way to generate various types of mock data,
 * such as names, addresses, phone numbers, companies, dates, numbers, strings, and usernames,
 * by delegating to specific data providers.
 *
 * @author Muhammed Ibrahim
 * @version 1.0
 * @since 1.0
 */
public class JDataMocker {

    /** The random number generator used by all data providers. */
    private final ThreadLocalRandom random;

    /** A map of provider names to their corresponding data provider instances. */
    private final Map<String, DataProvider> providers = new HashMap<>();

    /**
     * Constructs a new {@code JDataMocker} with the default locale ({@link Locale#ENGLISH}).
     */
    public JDataMocker() {
        this(Locale.ENGLISH);
    }

    /**
     * Constructs a new {@code JDataMocker} with the specified locale.
     * The locale is currently not used for localization but is included for future extensibility.
     * Initializes the random number generator and registers all data providers.
     *
     * @param locale the {@link Locale} to use (currently unused but reserved for future localization)
     */
    public JDataMocker(Locale locale) {
        this.random = ThreadLocalRandom.current();
        registerProviders();
    }

    /**
     * Registers all available data providers.
     * This method populates the providers map with instances of each data provider,
     * associating them with their respective keys (e.g., "name", "address").
     */
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

    /**
     * Retrieves a data provider by its name.
     *
     * @param providerName the name of the provider to retrieve (e.g., "name", "address")
     * @return the {@link DataProvider} associated with the given name, or {@code null} if not found
     */
    public DataProvider getProvider(String providerName) {
        return providers.get(providerName);
    }

    /**
     * Generates a random Nigerian name using the {@link NameProvider}.
     *
     * @return a randomly generated Nigerian name (e.g., "Chinedu Okoro")
     */
    public String nigerianName() {
        return ((NameProvider) providers.get("name")).nigerianName();
    }

    /**
     * Generates a random Arabic name using the {@link NameProvider}.
     *
     * @return a randomly generated Arabic name (e.g., "Mohammed Al-Saud")
     */
    public String arabicName() {
        return ((NameProvider) providers.get("name")).arabicName();
    }

    /**
     * Generates a mock email address based on a Nigerian name.
     * The email is created by replacing spaces in the name with dots, converting to lowercase,
     * and appending "@example.com".
     *
     * @return a mock email address (e.g., "chinedu.okoro@example.com")
     */
    public String email() {
        return nigerianName().replace(" ", ".").toLowerCase() + "@example.com";
    }

    /**
     * Generates a random full address using the {@link AddressProvider}.
     *
     * @return a randomly generated full address (e.g., "123 Main St")
     */
    public String address() {
        return ((AddressProvider) providers.get("address")).fullAddress();
    }

    /**
     * Generates a random phone number using the {@link PhoneNumberProvider}.
     *
     * @return a randomly generated phone number (e.g., "0123456789")
     */
    public String phoneNumber() {
        return ((PhoneNumberProvider) providers.get("phoneNumber")).phoneNumber();
    }

    /**
     * Generates a random string of the specified length using the {@link StringProvider}.
     *
     * @param length the desired length of the random string
     * @return a randomly generated string of the specified length
     */
    public String randomString(int length) {
        return ((StringProvider) providers.get("string")).randomString(length);
    }

    /**
     * Generates a random date using the {@link DateProvider}.
     *
     * @return a randomly generated {@link LocalDate} between 1900 and 2023
     */
    public LocalDate randomDate() {
        return ((DateProvider) providers.get("date")).randomDate();
    }

    /**
     * Generates a random integer within the specified range using the {@link NumberProvider}.
     *
     * @param min the minimum value (inclusive) of the range
     * @param max the maximum value (inclusive) of the range
     * @return a random integer between {@code min} and {@code max}, inclusive
     */
    public int randomNumber(int min, int max) {
        return ((NumberProvider) providers.get("number")).randomNumber(min, max);
    }

    /**
     * Generates a random even integer within the specified range using the {@link NumberProvider}.
     *
     * @param min the minimum value (inclusive) of the range
     * @param max the maximum value (inclusive) of the range
     * @return a random even integer between {@code min} and {@code max}, inclusive
     */
    public int randomEvenNumber(int min, int max) {
        return ((NumberProvider) providers.get("number")).randomEvenNumber(min, max);
    }

    /**
     * Generates a random odd integer within the specified range using the {@link NumberProvider}.
     *
     * @param min the minimum value (inclusive) of the range
     * @param max the maximum value (inclusive) of the range
     * @return a random odd integer between {@code min} and {@code max}, inclusive
     */
    public int randomOddNumber(int min, int max) {
        return ((NumberProvider) providers.get("number")).randomOddNumber(min, max);
    }

    /**
     * Generates a random username using the {@link UsernameProvider}.
     *
     * @return a randomly generated username (e.g., "CoolTiger123")
     */
    public String randomUsername() {
        return ((UsernameProvider) providers.get("username")).randomUsername();
    }

    /**
     * Generates a random company name based on the specified continent using the {@link CompanyProvider}.
     *
     * @param continent the {@link Continent} to select a company from
     * @return a randomly selected company name from the specified continent
     */
    public String randomCompany(Continent continent) {
        return ((CompanyProvider) providers.get("company")).randomCompany(continent);
    }
}