package com.ovansa.jdatamocker.provider;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides mock company names for testing purposes.
 * This class generates random company names based on a specified continent,
 * allowing for region-specific company data generation. If the specified continent
 * is not found, it defaults to a list of global companies.
 *
 * @author Muhammed Ibrahim
 * @version 1.0
 * @since 1.0
 */
public class CompanyProvider extends BaseProvider implements DataProvider {

    /**
     * A mapping of continents to arrays of company names.
     * Each continent is associated with a list of well-known companies from that region.
     * The {@link Continent#GLOBAL} key provides a fallback list of globally recognized companies.
     */
    private static final Map<Continent, String[]> COMPANIES = new EnumMap<>(Continent.class);

    static {
        COMPANIES.put(Continent.AFRICA, new String[]{"Safaricom", "Dangote Group", "MTN Group", "Shoprite", "Ecobank"});
        COMPANIES.put(Continent.AMERICA, new String[]{"Apple", "Microsoft", "Google", "Amazon", "Tesla"});
        COMPANIES.put(Continent.EUROPE, new String[]{"Siemens", "Volkswagen", "Nestle", "Shell", "Unilever"});
        COMPANIES.put(Continent.ASIA, new String[]{"Samsung", "Toyota", "Alibaba", "Huawei", "Sony"});
        COMPANIES.put(Continent.AUSTRALIA, new String[]{"BHP", "Woolworths", "Telstra", "Qantas", "Commonwealth Bank"});
        COMPANIES.put(Continent.GLOBAL, new String[]{"Coca-Cola", "McDonald's", "Nike", "Disney", "IBM"});
    }

    /**
     * Constructs a new {@code CompanyProvider} with the specified random number generator.
     * The random number generator is used to select random company names from the predefined lists.
     *
     * @param random the {@link ThreadLocalRandom} instance to use for random number generation
     */
    public CompanyProvider(ThreadLocalRandom random) {
        super(random);
    }

    /**
     * Generates a random company name based on the specified continent.
     * If the continent is not found in the predefined mapping, it defaults to a list of global companies.
     *
     * @param continent the {@link Continent} to select a company from
     * @return a randomly selected company name from the specified continent, or a global company if the continent is not found
     */
    public String randomCompany(Continent continent) {
        String[] companies = COMPANIES.getOrDefault(continent, COMPANIES.get(Continent.GLOBAL));
        return getRandom(companies);
    }

    /**
     * Selects a random element from the provided array of strings.
     * This method is used internally to pick a random company name from the list associated with a continent.
     *
     * @param array the array of strings to select from
     * @return a randomly selected string from the array
     */
    private String getRandom(String[] array) {
        return array[random.nextInt(array.length)];
    }
}