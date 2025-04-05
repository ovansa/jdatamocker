package com.ovansa.jdatamocker.provider;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides mock string data for testing purposes with extensive generation options.
 * This class generates random strings, names, emails, passwords, text, file paths, URLs, and more,
 * inspired by libraries like Java Faker.
 *
 * @author Muhammed Ibrahim
 * @version 1.0.8
 * @since 1.0.0
 */
public class StringProvider extends BaseProvider implements DataProvider {
    // Character sets
    private static final String ALPHABET_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHABET_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;':,.<>?";
    private static final String HEX_CHARS = "0123456789ABCDEF";
    private static final String ALPHANUMERIC = ALPHABET_UPPER + ALPHABET_LOWER + DIGITS;
    private static final String ALL_CHARS = ALPHANUMERIC + SPECIAL_CHARS;

    // Common data sets
    private static final String[] FIRST_NAMES = {"John", "Jane", "Michael", "Emily", "David", "Sarah"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia"};
    private static final String[] TOP_LEVEL_DOMAINS = {"com", "net", "org", "io", "co", "ai"};
    private static final String[] LOREM_WORDS = {"lorem", "ipsum", "dolor", "sit", "amet", "consectetur",
            "adipiscing", "elit", "sed", "do", "eiusmod", "tempor"};
    private static final String[] FOLDERS = {"documents", "pictures", "downloads", "music", "videos"};

    /**
     * Constructs a new {@code StringProvider} with the specified random number generator.
     *
     * @param random the {@link ThreadLocalRandom} instance to use for random number generation
     */
    public StringProvider(ThreadLocalRandom random) {
        super(random);
    }

    // ========================
    // Basic String Generation
    // ========================

    /**
     * Generates a random alphabetic string (letters only).
     *
     * @param length the desired length
     * @return e.g., "AbCdEf" for length 6
     * @throws IllegalArgumentException if length is not positive
     */
    public String alphabetic(int length) {
        return buildString(length, ALPHABET_UPPER + ALPHABET_LOWER);
    }

    /**
     * Generates a random numeric string (digits only).
     *
     * @param length the desired length
     * @return e.g., "123456" for length 6
     * @throws IllegalArgumentException if length is not positive
     */
    public String numeric(int length) {
        return buildString(length, DIGITS);
    }

    /**
     * Generates a random alphanumeric string (letters and digits).
     *
     * @param length the desired length
     * @return e.g., "Xy7Kp9" for length 6
     * @throws IllegalArgumentException if length is not positive
     */
    public String alphanumeric(int length) {
        return buildString(length, ALPHANUMERIC);
    }

    /**
     * Generates a random string with special characters included.
     *
     * @param length the desired length
     * @return e.g., "Xy7#Kp" for length 6
     * @throws IllegalArgumentException if length is not positive
     */
    public String withSpecialChars(int length) {
        return buildString(length, ALL_CHARS);
    }

    /**
     * Generates a random hexadecimal string.
     *
     * @param length the desired length
     * @return e.g., "A1F9B2" for length 6
     * @throws IllegalArgumentException if length is not positive
     */
    public String hex(int length) {
        return buildString(length, HEX_CHARS);
    }

    /**
     * Generates a random string from a custom character set.
     *
     * @param length  the desired length
     * @param charset the custom character set to use
     * @return a random string using the provided charset
     * @throws IllegalArgumentException if length is not positive or charset is null/empty
     */
    public String custom(int length, String charset) {
        if (charset == null || charset.isEmpty()) {
            throw new IllegalArgumentException("Character set must not be null or empty");
        }
        return buildString(length, charset);
    }

    /**
     * Generates a string based on a pattern where '#' is a digit, '@' is an uppercase letter,
     * and other characters are literals.
     *
     * @param pattern the pattern to follow (e.g., "###-@@@" for "123-ABC")
     * @return a random string matching the pattern
     * @throws IllegalArgumentException if pattern is null or empty
     */
    public String fromPattern(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern must not be null or empty");
        }
        StringBuilder sb = new StringBuilder();
        for (char c : pattern.toCharArray()) {
            switch (c) {
                case '#':
                    sb.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
                    break;
                case '@':
                    sb.append(ALPHABET_UPPER.charAt(random.nextInt(ALPHABET_UPPER.length())));
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    private String buildString(int length, String charSet) {
        validateLength(length);
        char[] chars = charSet.toCharArray();
        char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            result[i] = chars[random.nextInt(chars.length)];
        }
        return new String(result);
    }

    // ========================
    // Names
    // ========================

    public String firstName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }

    public String lastName() {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }

    public String fullName() {
        return firstName() + " " + lastName();
    }

    // ========================
    // Email
    // ========================

    public String email() {
        return username() + "@" + domainName() + "." + topLevelDomain();
    }

    private String username() {
        return alphanumeric(random.nextInt(6) + 5); // 5-10 characters
    }

    private String domainName() {
        return alphanumeric(random.nextInt(6) + 5); // 5-10 characters
    }

    private String topLevelDomain() {
        return TOP_LEVEL_DOMAINS[random.nextInt(TOP_LEVEL_DOMAINS.length)];
    }

    // ========================
    // Text Generation
    // ========================

    public String lorem(int words) {
        validateCount(words, "Word count");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words; i++) {
            sb.append(LOREM_WORDS[random.nextInt(LOREM_WORDS.length)]);
            if (i < words - 1) sb.append(" ");
        }
        return sb.toString();
    }

    public String sentence(int words) {
        return capitalizeFirstLetter(lorem(words)) + ".";
    }

    public String paragraph(int sentences) {
        validateCount(sentences, "Sentence count");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentences; i++) {
            sb.append(sentence(random.nextInt(6) + 5)); // 5-10 words
            if (i < sentences - 1) sb.append(" ");
        }
        return sb.toString();
    }

    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) return input;
        return Character.toUpperCase(input.charAt(0)) + input.substring(1);
    }

    // ========================
    // UUID/GUID
    // ========================

    public String uuid() {
        return UUID.randomUUID().toString();
    }

    public String guid() {
        return uuid();
    }

    /**
     * Generates a UUID-like string (not cryptographically secure, but formatted like UUID).
     *
     * @return e.g., "1a2b3c4d-5e6f-7g8h-9i0j-klmno"
     */
    public String uuidLike() {
        return String.format("%s-%s-%s-%s-%s",
                hex(8), hex(4), hex(4), hex(4), hex(12));
    }

    // ========================
    // Password Generation
    // ========================

    public String password(int length) {
        return withSpecialChars(length);
    }

    public String strongPassword() {
        String upper = alphabetic(2).toUpperCase();
        String lower = alphabetic(2).toLowerCase();
        String digit = numeric(2);
        String special = buildString(2, SPECIAL_CHARS);
        String rest = withSpecialChars(4); // Remaining 4 chars
        String combined = upper + lower + digit + special + rest;
        return shuffleString(combined);
    }

    private String shuffleString(String input) {
        char[] characters = input.toCharArray();
        for (int i = characters.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }
        return new String(characters);
    }

    // ========================
    // File Paths and URLs
    // ========================

    public String filePath() {
        return "/" + FOLDERS[random.nextInt(FOLDERS.length)] + "/" + alphanumeric(10) + ".txt";
    }

    public String url() {
        return "https://" + domainName() + "." + topLevelDomain() + "/" + alphanumeric(random.nextInt(6) + 5);
    }

    // ========================
    // Validation Methods
    // ========================

    public boolean isEmail(String input) {
        return input != null && input.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    // ========================
    // Utility
    // ========================

    private void validateLength(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }
    }

    private void validateCount(int count, String name) {
        if (count <= 0) {
            throw new IllegalArgumentException(name + " must be positive");
        }
    }
}