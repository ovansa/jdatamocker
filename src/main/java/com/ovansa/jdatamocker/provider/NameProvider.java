package com.ovansa.jdatamocker.provider;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Objects;

/**
 * Provides mock name data generation with extensive support for multiple cultures,
 * genders, and naming conventions. This class generates realistic random names by
 * combining culturally appropriate first names, last names, titles, and middle names
 * from predefined lists with various formatting options.
 * <p>
 * Supported features include:
 * <ul>
 *   <li>Multiple cultural regions (Nigerian, Arabic, Western, Asian, European)</li>
 *   <li>Gender-specific name generation</li>
 *   <li>Various name formats (first-last, last-first, with middle names, with titles)</li>
 *   <li>Culturally appropriate titles and honorifics</li>
 * </ul>
 *
 * @author Muhammed Ibrahim
 * @version 2.0
 * @since 1.0
 */
public class NameProvider extends BaseProvider implements DataProvider {
    /**
     * Enum representing supported cultural regions for name generation.
     */
    public enum Region {
        /**
         * Nigerian names and naming conventions
         */
        NIGERIAN,
        /**
         * Arabic names and naming conventions
         */
        ARABIC,
        /**
         * Western (primarily Anglo-American) names and conventions
         */
        WESTERN,
        /**
         * Asian names (Chinese, Japanese, Korean, Indian)
         */
        ASIAN,
        /**
         * European names (French, Italian, German, Russian, Spanish)
         */
        EUROPEAN
    }

    /**
     * Enum representing gender options for name generation.
     */
    public enum Gender {
        /**
         * Male names
         */
        MALE,
        /**
         * Female names
         */
        FEMALE,
        /**
         * Gender-neutral or unspecified names
         */
        UNSPECIFIED
    }

    /**
     * Enum representing different name formatting options.
     */
    public enum Format {
        /**
         * Standard first name followed by last name (e.g., "John Doe")
         */
        FIRST_LAST,
        /**
         * Last name first, followed by comma and first name (e.g., "Doe, John")
         */
        LAST_FIRST,
        /**
         * First, middle, and last name (e.g., "John Michael Doe")
         */
        FIRST_MIDDLE_LAST,
        /**
         * Title followed by first and last name (e.g., "Mr. John Doe")
         */
        TITLE_FIRST_LAST
    }

    // Nigerian names
    private static final String[] NIGERIAN_MALE_FIRST_NAMES = {"Chinedu", "Emeka", "Oluwatobi", "Adebayo", "Chukwuemeka", "Obinna", "Ifeanyi", "Abdul", "Musa", "Yusuf", "Ibrahim", "Olumide", "Adetokunbo", "Okechukwu", "Nnamdi", "Eze", "Tunde", "Kayode", "Femi", "Segun", "Kunle", "Babatunde", "Wale", "Dayo", "Jide"};

    private static final String[] NIGERIAN_FEMALE_FIRST_NAMES = {"Amina", "Fatima", "Zainab", "Chioma", "Ngozi", "Aisha", "Funke", "Blessing", "Grace", "Mercy", "Patience", "Esther", "Rahama", "Halima", "Maryam", "Olamide", "Adesuwa", "Efe", "Titilayo", "Yewande", "Folake", "Bimpe", "Ronke", "Simisola", "Temilade"};

    private static final String[] NIGERIAN_LAST_NAMES = {"Okoro", "Adeyemi", "Okafor", "Ibrahim", "Bello", "Abdullahi", "Ogunlesi", "Nwachukwu", "Onyema", "Eze", "Adeleke", "Balogun", "Obi", "Okonkwo", "Uche", "Mohammed", "Sani", "Abubakar", "Yakubu", "Ojo", "Adewale", "Oladipo", "Akintola", "Bankole", "Oyinlola"};

    // Arabic names
    private static final String[] ARABIC_MALE_FIRST_NAMES = {"Mohammed", "Ahmed", "Ali", "Omar", "Youssef", "Mahmoud", "Khalid", "Abdullah", "Mustafa", "Ibrahim", "Hamza", "Tariq", "Yahya", "Hassan", "Hussein", "Zaid", "Samir", "Naser", "Faisal", "Waleed", "Karim", "Adel", "Rashid", "Salim", "Jamal"};

    private static final String[] ARABIC_FEMALE_FIRST_NAMES = {"Aisha", "Fatima", "Layla", "Mariam", "Noor", "Amal", "Huda", "Zahra", "Samira", "Farida", "Salma", "Yasmin", "Leila", "Nadia", "Rania", "Dalia", "Hanan", "Jameela", "Karima", "Mona", "Nawal", "Rasha", "Sana", "Wafa", "Zain"};

    private static final String[] ARABIC_LAST_NAMES = {"Al-Saud", "Al-Farsi", "Khan", "Al-Maktoum", "Hassan", "Abbas", "Abdul", "Al-Masri", "Al-Qurashi", "Al-Najjar", "Al-Sharif", "Al-Baghdadi", "Al-Hashimi", "Al-Ghamdi", "Al-Obeidi", "Al-Zahrani", "Al-Amri", "Al-Shammari", "Al-Qahtani", "Al-Dosari", "Al-Harbi", "Al-Juhani", "Al-Sulami", "Al-Yami", "Al-Zahawi"};

    // Western names
    private static final String[] WESTERN_MALE_FIRST_NAMES = {"John", "Michael", "David", "James", "Robert", "William", "Richard", "Joseph", "Thomas", "Charles", "Christopher", "Daniel", "Matthew", "Anthony", "Donald", "Mark", "Paul", "Steven", "Andrew", "Kenneth", "George", "Joshua", "Kevin", "Brian", "Edward"};

    private static final String[] WESTERN_FEMALE_FIRST_NAMES = {"Mary", "Jennifer", "Lisa", "Sarah", "Emily", "Jessica", "Amanda", "Melissa", "Nicole", "Elizabeth", "Michelle", "Ashley", "Stephanie", "Rebecca", "Laura", "Kimberly", "Amber", "Rachel", "Heather", "Danielle", "Christina", "Tiffany", "Samantha", "Katherine", "Victoria"};

    private static final String[] WESTERN_LAST_NAMES = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Garcia", "Rodriguez", "Wilson", "Martinez", "Anderson", "Taylor", "Thomas", "Hernandez", "Moore", "Martin", "Jackson", "Thompson", "White", "Lopez", "Lee", "Gonzalez", "Harris", "Clark"};

    // Asian names (Chinese, Japanese, Korean, Indian)
    private static final String[] ASIAN_MALE_FIRST_NAMES = {"Wei", "Jian", "Min", "Hao", "Yong", "Takeshi", "Hiroshi", "Kenji", "Ryota", "Daichi", "Min-ho", "Ji-hoon", "Seung", "Joon", "Hyun", "Raj", "Aarav", "Vihaan", "Arjun", "Aditya", "Wei", "Chen", "Li", "Zhang", "Wang"};

    private static final String[] ASIAN_FEMALE_FIRST_NAMES = {"Mei", "Ling", "Xia", "Yan", "Li", "Hana", "Yui", "Sakura", "Aoi", "Rin", "Ji-woo", "Seo-yeon", "Min-ji", "Hye-jin", "Eun-ji", "Priya", "Ananya", "Diya", "Aanya", "Ishita", "Ying", "Fang", "Jing", "Lan", "Xiu"};

    private static final String[] ASIAN_LAST_NAMES = {"Wang", "Li", "Zhang", "Liu", "Chen", "Tanaka", "Sato", "Suzuki", "Takahashi", "Watanabe", "Kim", "Lee", "Park", "Choi", "Jung", "Patel", "Singh", "Kumar", "Sharma", "Gupta", "Nguyen", "Tran", "Le", "Pham", "Hoang"};

    // European names
    private static final String[] EUROPEAN_MALE_FIRST_NAMES = {"Jean", "Pierre", "Michel", "André", "Philippe", "Giovanni", "Marco", "Luca", "Alessandro", "Matteo", "Hans", "Peter", "Thomas", "Michael", "Andreas", "Ivan", "Sergey", "Dmitri", "Alexei", "Mikhail", "Carlos", "Javier", "Miguel", "Antonio", "Juan"};

    private static final String[] EUROPEAN_FEMALE_FIRST_NAMES = {"Marie", "Sophie", "Isabelle", "Nathalie", "Valérie", "Giulia", "Sofia", "Alessia", "Chiara", "Elena", "Anna", "Maria", "Christine", "Petra", "Sabine", "Olga", "Elena", "Irina", "Natalia", "Svetlana", "Carmen", "Isabel", "Ana", "Lucia", "Elena"};

    private static final String[] EUROPEAN_LAST_NAMES = {"Martin", "Bernard", "Dubois", "Thomas", "Robert", "Rossi", "Ferrari", "Russo", "Bianchi", "Romano", "Müller", "Schmidt", "Schneider", "Fischer", "Weber", "Ivanov", "Smirnov", "Kuznetsov", "Popov", "Sokolov", "Garcia", "Rodriguez", "Gonzalez", "Fernandez", "Lopez"};

    // Titles
    private static final String[] NIGERIAN_TITLES = {"Chief", "Alhaji", "Dr.", "Engr.", "Prof.", "Barr.", "Pastor", "Imam", "Oba", "Eze", "Olori", "Iyaloja", "Alhaja", "Madam", "Sir"};

    private static final String[] ARABIC_TITLES = {"Sheikh", "Dr.", "Prof.", "Hajji", "Sayyid", "Imam", "Ustadh", "Amir", "Mufti", "Qadi", "Hakim", "Ra'is", "Basha", "Effendi", "Mawlana"};

    private static final String[] WESTERN_TITLES = {"Mr.", "Mrs.", "Ms.", "Dr.", "Prof.", "Rev.", "Hon.", "Sen.", "Gov.", "Pres.", "Capt.", "Col.", "Gen.", "Judge", "Sir"};

    private static final String[] ASIAN_TITLES = {"Dr.", "Prof.", "Mr.", "Mrs.", "Ms.", "Shifu", "Sensei", "Sifu", "Guru", "Pandit", "Acharya", "Swami", "Baba", "Lao", "Xiansheng"};

    private static final String[] EUROPEAN_TITLES = {"Herr", "Frau", "Dr.", "Prof.", "M.", "Mme", "Mlle", "Sig.", "Dott.", "Ing.", "Mag.", "Dr.med.", "Lic.", "Arch.", "Avv."};

    // Middle names
    private static final String[] MIDDLE_NAMES = {"Ade", "Mohammed", "James", "Lee", "Xiao", "Jean", "Marie", "Anne", "Lynn", "Grace", "David", "Michael", "John", "William", "Robert", "Fatima", "Aisha", "Chinedu", "Oluwatobi", "Emeka", "Yong", "Min", "Wei", "Jian", "Hao", "Giovanni", "Marco", "Pierre", "Hans", "Ivan", "Olga", "Sophie", "Mei", "Hana", "Priya"};

    /**
     * Constructs a new {@code NameProvider} with the specified random number generator.
     *
     * @param random the {@link ThreadLocalRandom} instance to use for random number generation
     * @throws NullPointerException if random is null
     */
    public NameProvider(ThreadLocalRandom random) {
        super(Objects.requireNonNull(random, "Random must not be null"));
    }

    /**
     * Generates a random name with default settings (Nigerian region, unspecified gender,
     * and first-last format).
     *
     * @return a randomly generated name in the format "FirstName LastName"
     * <p>Example: {@code "Chinedu Okoro"}
     */
    public String getName() {
        return getName(Region.NIGERIAN, Gender.UNSPECIFIED, Format.FIRST_LAST);
    }

    /**
     * Generates a random name with the specified region and default format/gender.
     *
     * @param region the cultural region for the name generation
     * @return a randomly generated name in the format "FirstName LastName"
     * @throws NullPointerException if region is null
     *                              <p>Examples:
     *                              <pre>{@code
     *                                                           getName(Region.ARABIC) → "Ahmed Al-Saud"
     *                                                           getName(Region.WESTERN) → "Emily Johnson"
     *                                                           }</pre>
     */
    public String getName(Region region) {
        return getName(region, Gender.UNSPECIFIED, Format.FIRST_LAST);
    }

    /**
     * Generates a random name with the specified region and gender, using default format.
     *
     * @param region the cultural region for the name generation
     * @param gender the gender specification for the name
     * @return a randomly generated name in the format "FirstName LastName"
     * @throws NullPointerException if region or gender is null
     *                              <p>Examples:
     *                              <pre>{@code
     *                                                           getName(Region.NIGERIAN, Gender.FEMALE) → "Amina Ibrahim"
     *                                                           getName(Region.ASIAN, Gender.MALE) → "Wei Wang"
     *                                                           }</pre>
     */
    public String getName(Region region, Gender gender) {
        return getName(region, gender, Format.FIRST_LAST);
    }

    /**
     * Generates a random name with full customization options including region,
     * gender, and format specifications.
     *
     * @param region the cultural region for the name generation
     * @param gender the gender specification for the name
     * @param format the desired format of the generated name
     * @return a randomly generated name according to the specified parameters
     * @throws NullPointerException     if any parameter is null
     * @throws IllegalArgumentException if the combination of parameters is unsupported
     *                                  <p>Examples:
     *                                  <pre>{@code
     *                                                                   getName(Region.WESTERN, Gender.FEMALE, Format.TITLE_FIRST_LAST) → "Ms. Emily Johnson"
     *                                                                   getName(Region.ARABIC, Gender.MALE, Format.LAST_FIRST) → "Al-Saud, Ahmed"
     *                                                                   getName(Region.NIGERIAN, Gender.UNSPECIFIED, Format.FIRST_MIDDLE_LAST) → "Chinedu Ade Okoro"
     *                                                                   }</pre>
     */
    public String getName(Region region, Gender gender, Format format) {
        Objects.requireNonNull(region, "Region must not be null");
        Objects.requireNonNull(gender, "Gender must not be null");
        Objects.requireNonNull(format, "Format must not be null");

        String firstName = getRandomFirstName(region, gender);
        String lastName = getRandomLastName(region);
        String middleName = format == Format.FIRST_MIDDLE_LAST ? " " + getRandom(MIDDLE_NAMES) : "";

        return switch (format) {
            case FIRST_LAST -> firstName + " " + lastName;
            case LAST_FIRST -> lastName + ", " + firstName;
            case FIRST_MIDDLE_LAST -> firstName + middleName + " " + lastName;
            case TITLE_FIRST_LAST -> getRandomTitle(region) + " " + firstName + " " + lastName;
        };
    }

    /**
     * Generates a random Nigerian name with default format and unspecified gender.
     * This is a convenience method equivalent to getName(Region.NIGERIAN).
     *
     * @return a randomly generated Nigerian name
     * <p>Example: {@code "Emeka Okafor"}
     */
    public String nigerianName() {
        return getName(Region.NIGERIAN);
    }

    /**
     * Generates a random Arabic name with default format and unspecified gender.
     * This is a convenience method equivalent to getName(Region.ARABIC).
     *
     * @return a randomly generated Arabic name
     * <p>Example: {@code "Fatima Al-Maktoum"}
     */
    public String arabicName() {
        return getName(Region.ARABIC);
    }

    /**
     * Generates a random Western name with default format and unspecified gender.
     * This is a convenience method equivalent to getName(Region.WESTERN).
     *
     * @return a randomly generated Western name
     * <p>Example: {@code "Michael Smith"}
     */
    public String westernName() {
        return getName(Region.WESTERN);
    }

    /**
     * Selects a random first name based on the specified region and gender.
     *
     * @param region the cultural region for the name
     * @param gender the gender specification
     * @return a randomly selected first name
     * @throws IllegalArgumentException if the region is not supported
     */
    private String getRandomFirstName(Region region, Gender gender) {
        return switch (region) {
            case NIGERIAN -> getRandom(gender == Gender.MALE ? NIGERIAN_MALE_FIRST_NAMES : NIGERIAN_FEMALE_FIRST_NAMES);
            case ARABIC -> getRandom(gender == Gender.MALE ? ARABIC_MALE_FIRST_NAMES : ARABIC_FEMALE_FIRST_NAMES);
            case WESTERN -> getRandom(gender == Gender.MALE ? WESTERN_MALE_FIRST_NAMES : WESTERN_FEMALE_FIRST_NAMES);
            case ASIAN -> getRandom(gender == Gender.MALE ? ASIAN_MALE_FIRST_NAMES : ASIAN_FEMALE_FIRST_NAMES);
            case EUROPEAN -> getRandom(gender == Gender.MALE ? EUROPEAN_MALE_FIRST_NAMES : EUROPEAN_FEMALE_FIRST_NAMES);
        };
    }

    /**
     * Selects a random last name based on the specified region.
     *
     * @param region the cultural region for the name
     * @return a randomly selected last name
     * @throws IllegalArgumentException if the region is not supported
     */
    private String getRandomLastName(Region region) {
        return switch (region) {
            case NIGERIAN -> getRandom(NIGERIAN_LAST_NAMES);
            case ARABIC -> getRandom(ARABIC_LAST_NAMES);
            case WESTERN -> getRandom(WESTERN_LAST_NAMES);
            case ASIAN -> getRandom(ASIAN_LAST_NAMES);
            case EUROPEAN -> getRandom(EUROPEAN_LAST_NAMES);
        };
    }

    /**
     * Selects a random title based on the specified region.
     *
     * @param region the cultural region for the title
     * @return a randomly selected title
     * @throws IllegalArgumentException if the region is not supported
     */
    private String getRandomTitle(Region region) {
        return switch (region) {
            case NIGERIAN -> getRandom(NIGERIAN_TITLES);
            case ARABIC -> getRandom(ARABIC_TITLES);
            case WESTERN -> getRandom(WESTERN_TITLES);
            case ASIAN -> getRandom(ASIAN_TITLES);
            case EUROPEAN -> getRandom(EUROPEAN_TITLES);
        };
    }

    /**
     * Selects a random element from the provided array of strings.
     *
     * @param array the array of strings to select from
     * @return a randomly selected string from the array
     * @throws IllegalArgumentException if the array is null or empty
     */
    private String getRandom(String[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        return array[random.nextInt(array.length)];
    }
}