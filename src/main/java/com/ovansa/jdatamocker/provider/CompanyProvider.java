package com.ovansa.jdatamocker.provider;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides mock company names for testing purposes with enhanced features.
 * Generates random company names based on continents, countries, industries,
 * or custom generation with suffixes. Supports region-specific and global company data.
 *
 * @author Muhammed Ibrahim
 * @version 1.0.8
 * @since 1.0.0
 */
public class CompanyProvider extends BaseProvider implements DataProvider {

    // Continent-based company names
    private static final Map<Continent, String[]> CONTINENT_COMPANIES = new EnumMap<>(Continent.class);
    static {
        CONTINENT_COMPANIES.put(Continent.AFRICA, new String[]{"Safaricom", "Dangote Group", "MTN Group", "Shoprite", "Ecobank"});
        CONTINENT_COMPANIES.put(Continent.AMERICA, new String[]{"Apple", "Microsoft", "Google", "Amazon", "Tesla"});
        CONTINENT_COMPANIES.put(Continent.EUROPE, new String[]{"Siemens", "Volkswagen", "Nestle", "Shell", "Unilever"});
        CONTINENT_COMPANIES.put(Continent.ASIA, new String[]{"Samsung", "Toyota", "Alibaba", "Huawei", "Sony"});
        CONTINENT_COMPANIES.put(Continent.AUSTRALIA, new String[]{"BHP", "Woolworths", "Telstra", "Qantas", "Commonwealth Bank"});
        CONTINENT_COMPANIES.put(Continent.GLOBAL, new String[]{"Coca-Cola", "McDonald's", "Nike", "Disney", "IBM"});
    }

    // Country-specific company names (subset for demonstration)
    private static final Map<String, String[]> COUNTRY_COMPANIES = new HashMap<>();
    static {
        COUNTRY_COMPANIES.put("US", new String[]{"Apple", "Google", "Amazon", "Tesla", "Walmart"});
        COUNTRY_COMPANIES.put("UK", new String[]{"BP", "HSBC", "Tesco", "Rolls-Royce", "Barclays"});
        COUNTRY_COMPANIES.put("NG", new String[]{"Dangote Group", "MTN Nigeria", "Zenith Bank", "Glo", "First Bank"});
        COUNTRY_COMPANIES.put("JP", new String[]{"Toyota", "Sony", "Honda", "Nintendo", "Panasonic"});
    }

    // Industry-specific prefixes and suffixes
    private static final Map<Industry, String[]> INDUSTRY_PREFIXES = new EnumMap<>(Industry.class);
    static {
        INDUSTRY_PREFIXES.put(Industry.TECH, new String[]{"Tech", "Nex", "Cyber", "Inno", "Data"});
        INDUSTRY_PREFIXES.put(Industry.RETAIL, new String[]{"Shop", "Market", "Store", "Retail", "Trade"});
        INDUSTRY_PREFIXES.put(Industry.MANUFACTURING, new String[]{"Indust", "Manu", "Forge", "Build", "Works"});
        INDUSTRY_PREFIXES.put(Industry.FINANCE, new String[]{"Bank", "Fin", "Invest", "Capital", "Trust"});
    }

    private static final String[] SUFFIXES = {"Inc.", "Ltd", "LLC", "GmbH", "Co.", "Corp", "Group", "Solutions"};
    private static final String[] GENERIC_TERMS = {"ify", "tron", "ex", "ly", "on", "is", "um", "er"};

    /**
     * Constructs a new {@code CompanyProvider} with the specified random number generator.
     *
     * @param random the {@link ThreadLocalRandom} instance to use for random number generation
     */
    public CompanyProvider(ThreadLocalRandom random) {
        super(random);
    }

    // ========================
    // Continent-Based Generation
    // ========================

    /**
     * Generates a random company name based on the specified continent.
     *
     * @param continent the {@link Continent} to select a company from
     * @return e.g., "Safaricom" (Africa)
     */
    public String randomCompany(Continent continent) {
        String[] companies = CONTINENT_COMPANIES.getOrDefault(continent, CONTINENT_COMPANIES.get(Continent.GLOBAL));
        return getRandom(companies);
    }

    // ========================
    // Country-Based Generation
    // ========================

    /**
     * Generates a random company name based on the specified country code.
     *
     * @param countryCode the country code (e.g., "US", "UK", "NG")
     * @return e.g., "Google" (US)
     */
    public String randomCompanyByCountry(String countryCode) {
        String[] companies = COUNTRY_COMPANIES.getOrDefault(countryCode.toUpperCase(),
                CONTINENT_COMPANIES.get(Continent.GLOBAL));
        return getRandom(companies);
    }

    // ========================
    // Industry-Based Generation
    // ========================

    /**
     * Generates a random company name based on the specified industry with an optional suffix.
     *
     * @param industry the {@link Industry} to base the name on
     * @param withSuffix whether to append a suffix (e.g., "Inc.", "Ltd")
     * @return e.g., "Techtron Inc." (Tech, with suffix)
     */
    public String randomCompanyByIndustry(Industry industry, boolean withSuffix) {
        String prefix = getRandom(INDUSTRY_PREFIXES.getOrDefault(industry, INDUSTRY_PREFIXES.get(Industry.TECH)));
        String suffix = withSuffix ? " " + getRandom(SUFFIXES) : "";
        return prefix + getRandom(GENERIC_TERMS) + suffix;
    }

    // ========================
    // Fully Random Generation
    // ========================

    /**
     * Generates a completely random company name with an optional suffix.
     *
     * @param withSuffix whether to append a suffix
     * @return e.g., "Nexlify Ltd"
     */
    public String randomGeneratedCompany(boolean withSuffix) {
        String prefix = randomString(4, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        String suffix = withSuffix ? " " + getRandom(SUFFIXES) : "";
        return prefix + getRandom(GENERIC_TERMS) + suffix;
    }

    // ========================
    // Custom Generation
    // ========================

    /**
     * Generates a company name from a custom list of names.
     *
     * @param customCompanies the custom list of company names
     * @return a random name from the provided list
     * @throws IllegalArgumentException if the list is null or empty
     */
    public String randomFromCustomList(List<String> customCompanies) {
        if (customCompanies == null || customCompanies.isEmpty()) {
            throw new IllegalArgumentException("Custom company list must not be null or empty");
        }
        return customCompanies.get(random.nextInt(customCompanies.size()));
    }

    // ========================
    // Validation
    // ========================

    /**
     * Checks if a string resembles a plausible company name.
     *
     * @param name the name to validate
     * @return true if it matches a basic company name pattern
     */
    public boolean isValidCompanyName(String name) {
        if (name == null || name.trim().isEmpty()) return false;
        return name.matches("^[A-Za-z0-9][A-Za-z0-9\\s]*(Inc\\.|Ltd|LLC|GmbH|Co\\.|Corp|Group|Solutions)?$");
    }

    // ========================
    // Helper Methods
    // ========================

    private String getRandom(String[] array) {
        return array[random.nextInt(array.length)];
    }

    private String randomString(int length, String charset) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(charset.charAt(random.nextInt(charset.length())));
        }
        return sb.toString();
    }

    // ========================
    // Enums for Continent and Industry
    // ========================

    public enum Continent {
        AFRICA, AMERICA, EUROPE, ASIA, AUSTRALIA, GLOBAL
    }

    public enum Industry {
        TECH, RETAIL, MANUFACTURING, FINANCE
    }
}