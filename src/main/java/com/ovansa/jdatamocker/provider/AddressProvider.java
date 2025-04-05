package com.ovansa.jdatamocker.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides mock address data for testing purposes with international support.
 * Generates random addresses including street numbers, street names, cities, states/provinces,
 * and postal codes, tailored to specific countries.
 *
 * @author Muhammed Ibrahim
 * @version 1.0.8
 * @since 1.0.0
 */
public class AddressProvider extends BaseProvider implements DataProvider {

    // Country-specific address data: {streetTypes, cities, states/provinces, postalCodePattern}
    private static final Map<String, CountryAddressData> COUNTRY_DATA = new HashMap<>();
    static {
        COUNTRY_DATA.put("US", new CountryAddressData(
                List.of("St", "Ave", "Rd", "Blvd", "Ln"),
                List.of("New York", "Los Angeles", "Chicago", "Houston", "Phoenix"),
                List.of("NY", "CA", "IL", "TX", "AZ"),
                "#####",
                "{streetNumber} {streetName} {streetType}, {city}, {state} {postalCode}"
        ));
        COUNTRY_DATA.put("UK", new CountryAddressData(
                List.of("Street", "Road", "Lane", "Avenue", "Close"),
                List.of("London", "Manchester", "Birmingham", "Glasgow", "Edinburgh"),
                List.of("England", "Scotland", "Wales", "Northern Ireland"),
                "##@@ #@@",
                "{streetNumber} {streetName} {streetType}, {city}, {postalCode}"
        ));
        COUNTRY_DATA.put("NG", new CountryAddressData(
                List.of("Street", "Road", "Avenue", "Close", "Lane"),
                List.of("Lagos", "Abuja", "Kano", "Ibadan", "Port Harcourt"),
                List.of("Lagos", "FCT", "Kano", "Oyo", "Rivers"),
                "####",
                "{streetNumber} {streetName} {streetType}, {city}, {state}"
        ));
        COUNTRY_DATA.put("CA", new CountryAddressData(
                List.of("St", "Ave", "Rd", "Blvd", "Dr"),
                List.of("Toronto", "Vancouver", "Montreal", "Calgary", "Ottawa"),
                List.of("ON", "BC", "QC", "AB", "ON"),
                "@#@ #@#",
                "{streetNumber} {streetName} {streetType}, {city}, {state} {postalCode}"
        ));
    }

    private static final String[] STREET_NAMES = {
            "Main", "Broadway", "Market", "Park", "High", "Church", "Elm", "Oak", "Cedar", "Pine"
    };
    private static final String DEFAULT_COUNTRY = "US";

    /**
     * Constructs a new {@code AddressProvider} with the specified random number generator.
     *
     * @param random the {@link ThreadLocalRandom} instance to use for random number generation
     */
    public AddressProvider(ThreadLocalRandom random) {
        super(random);
    }

    // ========================
    // Full Address Generation
    // ========================

    /**
     * Generates a random full address (default country).
     *
     * @return e.g., "123 Main St, New York, NY 10001" (US)
     */
    public String fullAddress() {
        return fullAddress(DEFAULT_COUNTRY);
    }

    /**
     * Generates a random full address for the specified country.
     *
     * @param countryCode the country code (e.g., "US", "UK", "NG", "CA")
     * @return e.g., "45 High Street, London, SW1A 1AA" (UK)
     */
    public String fullAddress(String countryCode) {
        CountryAddressData data = getCountryData(countryCode);
        String streetNumber = String.valueOf(random.nextInt(1, 1000));
        String streetName = STREET_NAMES[random.nextInt(STREET_NAMES.length)];
        String streetType = data.streetTypes().get(random.nextInt(data.streetTypes().size()));
        String city = data.cities().get(random.nextInt(data.cities().size()));
        String state = data.states().get(random.nextInt(data.states().size()));
        String postalCode = generatePostalCode(data.postalCodePattern());

        return data.formatPattern()
                .replace("{streetNumber}", streetNumber)
                .replace("{streetName}", streetName)
                .replace("{streetType}", streetType)
                .replace("{city}", city)
                .replace("{state}", state)
                .replace("{postalCode}", postalCode);
    }

    // ========================
    // Component Generation
    // ========================

    /**
     * Generates a random street address (number + street name + type).
     *
     * @param countryCode the country code
     * @return e.g., "123 Main St" (US)
     */
    public String streetAddress(String countryCode) {
        CountryAddressData data = getCountryData(countryCode);
        return String.format("%d %s %s",
                random.nextInt(1, 1000),
                STREET_NAMES[random.nextInt(STREET_NAMES.length)],
                data.streetTypes().get(random.nextInt(data.streetTypes().size())));
    }

    /**
     * Generates a random city.
     *
     * @param countryCode the country code
     * @return e.g., "Lagos" (NG)
     */
    public String city(String countryCode) {
        return getCountryData(countryCode).cities().get(random.nextInt(getCountryData(countryCode).cities().size()));
    }

    /**
     * Generates a random state or province.
     *
     * @param countryCode the country code
     * @return e.g., "CA" (US)
     */
    public String state(String countryCode) {
        return getCountryData(countryCode).states().get(random.nextInt(getCountryData(countryCode).states().size()));
    }

    /**
     * Generates a random postal code.
     *
     * @param countryCode the country code
     * @return e.g., "10001" (US), "SW1A 1AA" (UK)
     */
    public String postalCode(String countryCode) {
        return generatePostalCode(getCountryData(countryCode).postalCodePattern());
    }

    // ========================
    // Advanced Features
    // ========================

    /**
     * Generates a full address with an apartment number.
     *
     * @param countryCode the country code
     * @return e.g., "123 Main St Apt 4B, New York, NY 10001" (US)
     */
    public String fullAddressWithApartment(String countryCode) {
        String baseAddress = fullAddress(countryCode);
        String apt = String.format("Apt %s%s", random.nextInt(1, 100), random.nextBoolean() ? "" : randomLetter());
        int commaIndex = baseAddress.indexOf(",");
        if (commaIndex == -1) return baseAddress + " " + apt;
        return baseAddress.substring(0, commaIndex) + " " + apt + baseAddress.substring(commaIndex);
    }

    /**
     * Generates a random address from a random supported country.
     *
     * @return e.g., "45 High Street, London, SW1A 1AA" (UK)
     */
    public String randomCountryAddress() {
        String[] countries = COUNTRY_DATA.keySet().toArray(new String[0]);
        return fullAddress(countries[random.nextInt(countries.length)]);
    }

    // ========================
    // Validation
    // ========================

    /**
     * Checks if a string resembles a valid address for a given country.
     *
     * @param address     the address to validate
     * @param countryCode the country code
     * @return true if the address matches the country's pattern
     */
    public boolean isValidAddress(String address, String countryCode) {
        if (address == null || address.isEmpty()) return false;
        CountryAddressData data = getCountryData(countryCode);
        String pattern = data.formatPattern()
                .replace("{streetNumber}", "\\d+")
                .replace("{streetName}", "\\w+")
                .replace("{streetType}", String.join("|", data.streetTypes()))
                .replace("{city}", String.join("|", data.cities()))
                .replace("{state}", String.join("|", data.states()))
                .replace("{postalCode}", data.postalCodePattern().replace("#", "\\d").replace("@", "[A-Z]"));
        return address.matches(pattern.replace(" ", "\\s*"));
    }

    // ========================
    // Helper Methods
    // ========================

    private String generatePostalCode(String pattern) {
        StringBuilder result = new StringBuilder();
        for (char c : pattern.toCharArray()) {
            if (c == '#') {
                result.append(random.nextInt(10));
            } else if (c == '@') {
                result.append(randomLetter());
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    private char randomLetter() {
        return (char) ('A' + random.nextInt(26));
    }

    private CountryAddressData getCountryData(String countryCode) {
        CountryAddressData data = COUNTRY_DATA.get(countryCode.toUpperCase());
        if (data == null) {
            throw new IllegalArgumentException("Unsupported country code: " + countryCode + ". Supported: " + COUNTRY_DATA.keySet());
        }
        return data;
    }

    // ========================
    // Record for Country Data
    // ========================

    private record CountryAddressData(
            List<String> streetTypes,
            List<String> cities,
            List<String> states,
            String postalCodePattern,
            String formatPattern
    ) {}
}