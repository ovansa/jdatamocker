package com.ovansa.jdatamocker.provider;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Objects;

/**
 * Provides comprehensive mock username generation for testing purposes.
 * This class generates random usernames with various patterns and constraints including:
 * <ul>
 *   <li>Adjective-Noun-Number pattern (default)</li>
 *   <li>Firstname-Lastname pattern</li>
 *   <li>Simple alphanumeric patterns</li>
 *   <li>Configurable length and complexity</li>
 *   <li>Special character inclusion</li>
 * </ul>
 * All methods are thread-safe when used with {@link ThreadLocalRandom}.
 *
 * @author Muhammed Ibrahim
 * @version 2.0
 * @since 1.0
 */
public class UsernameProvider extends BaseProvider implements DataProvider {

    private static final String[] ADJECTIVES = {
            "Cool", "Smart", "Fast", "Brave", "Witty", "Mighty", "Fierce", "Bold",
            "Sly", "Energetic", "Happy", "Lucky", "Swift", "Clever", "Gentle",
            "Jolly", "Nimble", "Proud", "Royal", "Silent", "Vivid", "Wild"
    };

    private static final String[] NOUNS = {
            "Tiger", "Eagle", "Shark", "Panther", "Wolf", "Dragon", "Falcon",
            "Cheetah", "Viper", "Hawk", "Lion", "Bear", "Fox", "Rhino", "Owl",
            "Panda", "Raven", "Cobra", "Moose", "Stag", "Horse", "Bull"
    };

    private static final String[] FIRST_NAMES = {
            "Alex", "Jordan", "Taylor", "Casey", "Morgan", "Jamie", "Riley",
            "Quinn", "Avery", "Peyton", "Dakota", "Skyler", "Emerson", "Rowan"
    };

    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis",
            "Garcia", "Rodriguez", "Wilson", "Martinez", "Anderson", "Taylor"
    };

    private static final String SPECIAL_CHARS = "._-";
    private static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789" + SPECIAL_CHARS;

    /**
     * Constructs a new {@code UsernameProvider} with the specified random number generator.
     *
     * @param random the {@link ThreadLocalRandom} instance to use for random number generation
     * @throws NullPointerException if random is null
     */
    public UsernameProvider(ThreadLocalRandom random) {
        super(Objects.requireNonNull(random, "Random number generator must not be null"));
    }

    /**
     * Generates a random username in the format "adjectiveNounNumber".
     *
     * @return a randomly generated username
     * <p>Example: {@code "BraveShark4287"}
     */
    public String randomUsername() {
        return randomAdjectiveNounUsername();
    }

    /**
     * Generates a username in adjective-noun-number format.
     *
     * @return a username in format "adjectiveNounNumber"
     * <p>Example: {@code "SwiftFox5821"}
     */
    public String randomAdjectiveNounUsername() {
        return getRandom(ADJECTIVES) + getRandom(NOUNS) + random.nextInt(100, 9999);
    }

    /**
     * Generates a username in firstname-lastname format.
     *
     * @return a username in format "firstname.lastname" or "firstnamelastname"
     * <p>Example: {@code "alex.smith"} or {@code "jamiejohnson"}
     */
    public String randomNameUsername() {
        boolean useDot = random.nextBoolean();
        String first = getRandom(FIRST_NAMES).toLowerCase();
        String last = getRandom(LAST_NAMES).toLowerCase();
        return useDot ? first + "." + last : first + last;
    }

    /**
     * Generates a simple alphanumeric username of specified length.
     *
     * @param length desired username length (6-32)
     * @return a random alphanumeric username
     * @throws IllegalArgumentException if length is invalid
     * <p>Example: {@code randomSimpleUsername(8)} might return {@code "x7f9k2m8"}
     */
    public String randomSimpleUsername(int length) {
        return randomCustomUsername(length, false, false);
    }

    /**
     * Generates a custom username with configurable properties.
     *
     * @param length desired username length (6-32)
     * @param includeSpecialChars whether to include special characters
     * @param startWithLetter whether username must start with a letter
     * @return a randomly generated username
     * @throws IllegalArgumentException if length is invalid
     * <p>Example: {@code randomCustomUsername(10, true, true)} might return {@code "user_name.42"}
     */
    public String randomCustomUsername(int length, boolean includeSpecialChars, boolean startWithLetter) {
        if (length < 6 || length > 32) {
            throw new IllegalArgumentException("Username length must be between 6 and 32");
        }

        StringBuilder sb = new StringBuilder(length);
        String allowedChars = includeSpecialChars ? ALLOWED_CHARS : ALLOWED_CHARS.replaceAll("["+SPECIAL_CHARS+"]", "");

        if (startWithLetter) {
            sb.append((char) ('a' + random.nextInt(26)));
            length--;
        }

        for (int i = 0; i < length; i++) {
            sb.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }

        return sb.toString();
    }

    /**
     * Generates a username with a specific prefix.
     *
     * @param prefix the prefix to use
     * @param maxLength maximum total length (prefix + random part)
     * @return a username with the given prefix
     * @throws IllegalArgumentException if prefix is empty or maxLength is too small
     * <p>Example: {@code randomPrefixedUsername("admin", 10)} might return {@code "admin7x9f"}
     */
    public String randomPrefixedUsername(String prefix, int maxLength) {
        Objects.requireNonNull(prefix, "Prefix must not be null");
        if (prefix.isEmpty()) {
            throw new IllegalArgumentException("Prefix must not be empty");
        }
        if (maxLength <= prefix.length()) {
            throw new IllegalArgumentException("Max length must be greater than prefix length");
        }

        int randomLength = maxLength - prefix.length();
        String randomPart = randomSimpleUsername(randomLength);
        return prefix + randomPart.substring(0, Math.min(randomPart.length(), randomLength));
    }

    /**
     * Selects a random element from the provided array of strings.
     *
     * @param array the array of strings to select from
     * @return a randomly selected string from the array
     * @throws IllegalArgumentException if array is empty
     */
    private String getRandom(String[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        return array[random.nextInt(array.length)];
    }
}