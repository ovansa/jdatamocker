package com.ovansa.jdatamocker.provider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides mock phone number data for testing purposes with comprehensive international support.
 * Generates random phone numbers in various formats with country-specific validation rules,
 * custom formatting, and advanced features like toll-free numbers and extensions.
 *
 * @author Muhammed Ibrahim
 * @version 1.0.8
 * @since 1.0.0
 */
public class PhoneNumberProvider extends BaseProvider implements DataProvider {

    // Country data with format: {countryCode, internationalPrefix, numberLength, mobilePrefixes, landlinePrefixes, formatPattern}
    private static final Map<String, CountryPhoneData> COUNTRY_DATA = new HashMap<>();

    static {
        COUNTRY_DATA.put("US", new CountryPhoneData("+1", 10,
                List.of("2", "3", "4", "5", "6", "7", "8", "9"),
                List.of("1"),
                "(XXX) XXX-XXXX"));
        COUNTRY_DATA.put("CA", new CountryPhoneData("+1", 10,
                List.of("2", "3", "4", "5", "6", "7", "8", "9"),
                List.of("1"),
                "(XXX) XXX-XXXX"));
        COUNTRY_DATA.put("UK", new CountryPhoneData("+44", 10,
                List.of("7"),
                List.of("1", "2", "3"),
                "XXXX XXX XXXX"));
        COUNTRY_DATA.put("FR", new CountryPhoneData("+33", 9,
                List.of("6", "7"),
                List.of("1", "2", "3", "4", "5"),
                "X XX XX XX XX"));
        COUNTRY_DATA.put("DE", new CountryPhoneData("+49", 10,
                List.of("15", "16", "17"),
                List.of("2", "3", "4", "5", "6", "7", "8", "9"),
                "XXXX-XXXXXXX"));
        COUNTRY_DATA.put("NG", new CountryPhoneData("+234", 10,
                List.of("70", "80", "81", "90", "91"),
                List.of("1", "2", "7"),
                "XXX XXX XXXX"));
        COUNTRY_DATA.put("ZA", new CountryPhoneData("+27", 9,
                List.of("6", "7", "8"),
                List.of("1", "2"),
                "XX XXX XXXX"));
        COUNTRY_DATA.put("IN", new CountryPhoneData("+91", 10,
                List.of("7", "8", "9"),
                List.of("1", "2", "3", "4", "5"),
                "XXXXX-XXXXX"));
        COUNTRY_DATA.put("CN", new CountryPhoneData("+86", 11,
                List.of("13", "15", "18"),
                List.of("10", "20", "21", "22", "23", "24"),
                "XXX-XXXX-XXXX"));
    }

    private static final String DEFAULT_COUNTRY = "NG";
    private static final List<String> SUPPORTED_COUNTRIES = List.copyOf(COUNTRY_DATA.keySet());

    public PhoneNumberProvider(ThreadLocalRandom random) {
        super(random);
    }

    // ========================
    // Basic Phone Number Generation
    // ========================

    /**
     * Generates a random local phone number (default country format).
     *
     * @return e.g., "8031234567" (NG)
     */
    public String phoneNumber() {
        return phoneNumber(DEFAULT_COUNTRY);
    }

    /**
     * Generates a random local phone number for the specified country.
     *
     * @param countryCode the country code (e.g., "US", "UK")
     * @return e.g., "2025550123" (US)
     */
    public String phoneNumber(String countryCode) {
        CountryPhoneData data = getCountryData(countryCode);
        String prefix = data.mobilePrefixes().get(random.nextInt(data.mobilePrefixes().size()));
        return prefix + generateNumber(data.numberLength() - prefix.length());
    }

    // ========================
    // International Format Generation
    // ========================

    /**
     * Generates a phone number with international prefix (default country).
     *
     * @return e.g., "+2348031234567" (NG)
     */
    public String international() {
        return international(DEFAULT_COUNTRY);
    }

    /**
     * Generates a phone number with international prefix for the specified country.
     *
     * @param countryCode the country code
     * @return e.g., "+12025550123" (US)
     */
    public String international(String countryCode) {
        CountryPhoneData data = getCountryData(countryCode);
        String prefix = data.mobilePrefixes().get(random.nextInt(data.mobilePrefixes().size()));
        return data.internationalPrefix() + prefix + generateNumber(data.numberLength() - prefix.length());
    }

    // ========================
    // Formatted Phone Numbers
    // ========================

    /**
     * Generates a formatted phone number (default country).
     *
     * @return e.g., "803 123 4567" (NG)
     */
    public String formatted() {
        return formatted(DEFAULT_COUNTRY);
    }

    /**
     * Generates a formatted phone number for the specified country using its default pattern.
     *
     * @param countryCode the country code
     * @return e.g., "(202) 555-0123" (US)
     */
    public String formatted(String countryCode) {
        CountryPhoneData data = getCountryData(countryCode);
        String number = phoneNumber(countryCode);
        return formatNumber(number, data.formatPattern());
    }

    /**
     * Generates a phone number with a custom format pattern.
     *
     * @param countryCode the country code
     * @param pattern     the custom format pattern (e.g., "XXX-XXX-XXXX")
     * @return a formatted number using the custom pattern
     * @throws IllegalArgumentException if pattern is invalid
     */
    public String customFormatted(String countryCode, String pattern) {
        if (pattern == null || pattern.isEmpty() || !pattern.contains("X")) {
            throw new IllegalArgumentException("Pattern must contain 'X' placeholders for digits");
        }
        String number = phoneNumber(countryCode);
        return formatNumber(number, pattern);
    }

    // ========================
    // Mobile/Landline Specific
    // ========================

    /**
     * Generates a mobile phone number (default country).
     *
     * @return e.g., "+2348031234567" (NG)
     */
    public String mobile() {
        return mobile(DEFAULT_COUNTRY);
    }

    /**
     * Generates a mobile phone number for the specified country.
     *
     * @param countryCode the country code
     * @return e.g., "+447123456789" (UK)
     */
    public String mobile(String countryCode) {
        CountryPhoneData data = getCountryData(countryCode);
        String prefix = data.mobilePrefixes().get(random.nextInt(data.mobilePrefixes().size()));
        return data.internationalPrefix() + prefix + generateNumber(data.numberLength() - prefix.length());
    }

    /**
     * Generates a landline phone number (default country).
     *
     * @return e.g., "+2341123456789" (NG)
     */
    public String landline() {
        return landline(DEFAULT_COUNTRY);
    }

    /**
     * Generates a landline phone number for the specified country.
     *
     * @param countryCode the country code
     * @return e.g., "+12015550123" (US)
     */
    public String landline(String countryCode) {
        CountryPhoneData data = getCountryData(countryCode);
        String prefix = data.landlinePrefixes().get(random.nextInt(data.landlinePrefixes().size()));
        return data.internationalPrefix() + prefix + generateNumber(data.numberLength() - prefix.length());
    }

    // ========================
    // Advanced Features
    // ========================

    /**
     * Generates a toll-free number (if supported by country).
     *
     * @param countryCode the country code
     * @return e.g., "+18001234567" (US)
     */
    public String tollFree(String countryCode) {
        CountryPhoneData data = getCountryData(countryCode);
        String prefix = countryCode.equals("US") || countryCode.equals("CA") ? "800" : "0800"; // US/CA vs others
        return data.internationalPrefix() + prefix + generateNumber(data.numberLength() - prefix.length());
    }

    /**
     * Generates a premium rate number (if supported by country).
     *
     * @param countryCode the country code
     * @return e.g., "+19001234567" (US)
     */
    public String premiumRate(String countryCode) {
        CountryPhoneData data = getCountryData(countryCode);
        String prefix = countryCode.equals("US") || countryCode.equals("CA") ? "900" : "0900";
        return data.internationalPrefix() + prefix + generateNumber(data.numberLength() - prefix.length());
    }

    /**
     * Generates a phone number with an extension.
     *
     * @param countryCode     the country code
     * @param extensionLength the length of the extension
     * @return e.g., "+12025550123x123" (US)
     * @throws IllegalArgumentException if extensionLength is not positive
     */
    public String withExtension(String countryCode, int extensionLength) {
        validateLength(extensionLength);
        String number = international(countryCode);
        return number + "x" + generateNumber(extensionLength);
    }

    /**
     * Generates a phone number from a randomly selected country.
     *
     * @return e.g., "+447123456789" (UK)
     */
    public String randomCountry() {
        String countryCode = SUPPORTED_COUNTRIES.get(random.nextInt(SUPPORTED_COUNTRIES.size()));
        return international(countryCode);
    }

    // ========================
    // Validation Methods
    // ========================

    /**
     * Validates if a string is a plausible phone number.
     *
     * @param input the phone number to validate
     * @return true if it matches a basic phone number pattern
     */
    public boolean isValidPhoneNumber(String input) {
        if (input == null || input.isEmpty()) return false;
        return input.matches("^(\\+\\d{1,4}[\\s-]?)?(\\d[\\s-]?){7,15}(x\\d+)?$");
    }

    /**
     * Validates if a phone number matches a specific country's format and prefix rules.
     *
     * @param phoneNumber the phone number to validate
     * @param countryCode the country code
     * @return true if valid for the country
     */
    public boolean isValidForCountry(String phoneNumber, String countryCode) {
        CountryPhoneData data = COUNTRY_DATA.get(countryCode.toUpperCase());
        if (data == null || phoneNumber == null) return false;

        String digitsOnly = phoneNumber.replaceAll("[^0-9]", "");
        boolean hasIntlPrefix = phoneNumber.startsWith(data.internationalPrefix());

        String localNumber = hasIntlPrefix ? digitsOnly.substring(data.internationalPrefix().length() - 1) : digitsOnly;
        if (localNumber.length() != data.numberLength()) return false;

        String prefix = localNumber.substring(0, Math.min(localNumber.length(), 2));
        boolean isMobile = data.mobilePrefixes().stream().anyMatch(prefix::startsWith);
        boolean isLandline = data.landlinePrefixes().stream().anyMatch(prefix::startsWith);

        if (!isMobile && !isLandline) return false;

        String formatted = formatNumber(localNumber, data.formatPattern());
        return phoneNumber.replaceAll("[^0-9+x]", "").equals(formatted.replaceAll("[^0-9+x]", ""));
    }

    /**
     * Returns the list of supported country codes.
     *
     * @return immutable list of country codes
     */
    public List<String> getSupportedCountries() {
        return SUPPORTED_COUNTRIES;
    }

    // ========================
    // Helper Methods
    // ========================

    private String generateNumber(int length) {
        validateLength(length);
        char[] digits = new char[length];
        for (int i = 0; i < length; i++) {
            digits[i] = (char) ('0' + random.nextInt(10));
        }
        return new String(digits);
    }

    private String formatNumber(String number, String pattern) {
        StringBuilder formatted = new StringBuilder();
        int numIndex = 0;

        for (char c : pattern.toCharArray()) {
            if (c == 'X' && numIndex < number.length()) {
                formatted.append(number.charAt(numIndex++));
            } else {
                formatted.append(c);
            }
        }

        while (numIndex < number.length()) {
            formatted.append(number.charAt(numIndex++));
        }

        return formatted.toString();
    }

    private CountryPhoneData getCountryData(String countryCode) {
        CountryPhoneData data = COUNTRY_DATA.get(countryCode.toUpperCase());
        if (data == null) {
            throw new IllegalArgumentException("Unsupported country code: " + countryCode + ". Supported: " + SUPPORTED_COUNTRIES);
        }
        return data;
    }

    private void validateLength(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }
    }

    // ========================
    // Record for Country Data
    // ========================

    private record CountryPhoneData(
            String internationalPrefix,
            int numberLength,
            List<String> mobilePrefixes,
            List<String> landlinePrefixes,
            String formatPattern
    ) {}
}