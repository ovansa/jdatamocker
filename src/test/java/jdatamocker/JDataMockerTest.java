package jdatamocker;

import com.ovansa.jdatamocker.JDataMocker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JDataMockerTest {
    private JDataMocker mocker;

    @BeforeEach
    void setUp() {
        mocker = new JDataMocker();
    }

    @Test
    void builder_withDefaultConfiguration_createsValidInstance() {
        assertNotNull(mocker);
        assertNotNull(mocker.name());
        assertNotNull(mocker.date());
        assertNotNull(mocker.email());
        assertNotNull(mocker.number());
        assertNotNull(mocker.username());
    }

    @Test
    void nameGenerator_nigerianName_generatesValidName() {
        String nigerianName = mocker.name().nigerian();
        assertNotNull(nigerianName, "Generated Nigerian name should not be null");
        String[] parts = nigerianName.split(" ");
        assertEquals(2, parts.length, "Nigerian name should have two parts: " + nigerianName);
        assertTrue(parts[0].length() > 1, "First name should be non-empty and reasonable: " + nigerianName);
        assertTrue(parts[1].length() > 1, "Last name should be non-empty and reasonable: " + nigerianName);
        assertTrue(parts[0].matches("[A-Za-z]+") && parts[1].matches("[A-Za-z]+"),
                "Name parts should contain only letters: " + nigerianName);
    }

    @Test
    void nameGenerator_arabicName_generatesValidName() {
        String arabicName = mocker.name().arabic();
        assertNotNull(arabicName, "Generated Arabic name should not be null");
        String[] parts = arabicName.split(" ");
        assertEquals(2, parts.length, "Arabic name should have two parts: " + arabicName);
        assertTrue(parts[0].length() > 1, "First name should be non-empty: " + arabicName);
        assertTrue(parts[1].length() > 1, "Last name should be non-empty: " + arabicName);
        assertTrue(parts[0].matches("[A-Za-z]+") && parts[1].matches("[A-Za-z-]+"),
                "Arabic name parts should contain letters and hyphens where appropriate: " + arabicName);
    }

    @Test
    void nameGenerator_westernName_generatesValidName() {
        String westernName = mocker.name().western();
        assertNotNull(westernName, "Generated Western name should not be null");
        String[] parts = westernName.split(" ");
        assertEquals(2, parts.length, "Western name should have two parts: " + westernName);
        assertTrue(parts[0].length() > 1, "First name should be non-empty: " + westernName);
        assertTrue(parts[1].length() > 1, "Last name should be non-empty: " + westernName);
        assertTrue(parts[0].matches("[A-Za-z]+") && parts[1].matches("[A-Za-z]+"),
                "Name parts should contain only letters: " + westernName);
    }

    @Test
    void nameGenerator_asianName_generatesValidName() {
        String asianName = mocker.name().asian();
        assertNotNull(asianName, "Generated Asian name should not be null");
        String[] parts = asianName.split(" ");
        assertEquals(2, parts.length, "Asian name should have two parts: " + asianName);
        assertTrue(parts[0].length() > 1, "First name should be non-empty: " + asianName);
        assertTrue(parts[1].length() > 1, "Last name should be non-empty: " + asianName);
        assertTrue(parts[0].matches("[A-Za-z-]+") && parts[1].matches("[A-Za-z]+"),
                "Asian name parts should contain letters and hyphens where appropriate: " + asianName);
    }

    @Test
    void nameGenerator_europeanName_generatesValidName() {
        String europeanName = mocker.name().european();
        assertNotNull(europeanName, "Generated European name should not be null");
        String[] parts = europeanName.split(" ");
        assertEquals(2, parts.length, "European name should have two parts: " + europeanName);
        assertTrue(parts[0].length() > 1, "First name should be non-empty: " + europeanName);
        assertTrue(parts[1].length() > 1, "Last name should be non-empty: " + europeanName);
        assertTrue(parts[0].matches("\\p{L}+") && parts[1].matches("\\p{L}+"),
                "European name parts should contain letters including diacritics: " + europeanName);
    }

    @Test
    void nameGenerator_fullName_generatesValidName() {
        String fullName = mocker.name().fullName();
        assertNotNull(fullName, "Generated full name should not be null");
        String[] parts = fullName.split(" ");
        assertEquals(2, parts.length, "Full name should have two parts: " + fullName);
        assertTrue(parts[0].length() > 1, "First name should be non-empty: " + fullName);
        assertTrue(parts[1].length() > 1, "Last name should be non-empty: " + fullName);
        assertTrue(parts[0].matches("[A-Za-z]+") && parts[1].matches("[A-Za-z]+"),
                "Name parts should contain only letters: " + fullName);
    }

    @Test
    void dateGenerator_random_generatesValidDate() {
        LocalDate date = mocker.date().random();
        assertNotNull(date, "Generated date should not be null");
        assertTrue(date.isBefore(LocalDate.now().plusDays(1)), "Date should not be in the future");
    }

    @Test
    void dateGenerator_past_generatesValidPastDate() {
        int maxYears = 5;
        LocalDate pastDate = mocker.date().past(maxYears);
        assertNotNull(pastDate, "Generated past date should not be null");
        LocalDate earliest = LocalDate.now().minusYears(maxYears);
        assertTrue(pastDate.isAfter(earliest) || pastDate.isEqual(earliest),
                "Past date should be within " + maxYears + " years: " + pastDate);
        assertTrue(pastDate.isBefore(LocalDate.now()), "Past date should be before today: " + pastDate);
    }

    @Test
    void dateGenerator_future_generatesValidFutureDate() {
        int maxYears = 5;
        LocalDate futureDate = mocker.date().future(maxYears);
        assertNotNull(futureDate, "Generated future date should not be null");
        LocalDate latest = LocalDate.now().plusYears(maxYears);
        assertTrue(futureDate.isBefore(latest) || futureDate.isEqual(latest),
                "Future date should be within " + maxYears + " years: " + futureDate);
        assertTrue(futureDate.isAfter(LocalDate.now()), "Future date should be after today: " + futureDate);
    }

    @Test
    void dateGenerator_birthday_generatesValidBirthDate() {
        int age = 30;
        LocalDate birthDate = mocker.date().birthday(age);
        assertNotNull(birthDate, "Generated birth date should not be null");
        LocalDate expectedYearStart = LocalDate.now().minusYears(age + 1);
        LocalDate expectedYearEnd = LocalDate.now().minusYears(age);
        assertTrue(birthDate.isAfter(expectedYearStart) && birthDate.isBefore(expectedYearEnd),
                "Birth date should match age " + age + ": " + birthDate);
    }

    @Test
    void emailGenerator_personal_generatesValidEmail() {
        String email = mocker.email().personal();
        assertNotNull(email, "Generated personal email should not be null");
        assertTrue(email.matches("[a-z]+\\.[a-z]+@[a-z]+\\.[a-z]{2,4}"),
                "Personal email should match expected format (e.g., john.smith@domain.com): " + email);
        assertTrue(email.contains("."), "Personal email should include a dot separator in username: " + email);
        assertTrue(List.of("gmail.com", "yahoo.com", "outlook.com", "hotmail.com", "example.com").contains(email.substring(email.indexOf("@") + 1)),
                "Personal email should use a valid domain: " + email);
    }

    @Test
    void emailGenerator_business_generatesValidEmail() {
        String email = mocker.email().business();
        assertNotNull(email, "Generated business email should not be null");
        assertTrue(email.matches("(contact|info|sales|support|admin)@[a-z0-9]+\\.[a-z.]{2,6}"),
                "Business email should match expected format (e.g., sales@acme.com): " + email);
        assertTrue(List.of("contact", "info", "sales", "support", "admin").contains(email.substring(0, email.indexOf("@"))),
                "Business email should start with a valid prefix: " + email);
        assertTrue(List.of("com", "org", "net", "co.uk", "ng", "ca", "de", "fr", "jp").contains(email.substring(email.lastIndexOf(".") + 1)),
                "Business email should use a valid TLD: " + email);
    }

    @Test
    void numberGenerator_integer_generatesValidNumber() {
        int min = 10;
        int max = 20;
        int number = mocker.number().integer(min, max);
        assertTrue(number >= min && number <= max,
                "Generated integer should be between " + min + " and " + max + ": " + number);
    }

    @Test
    void numberGenerator_even_generatesValidEvenNumber() {
        int min = 10;
        int max = 20;
        int even = mocker.number().even(min, max);
        assertTrue(even >= min && even <= max,
                "Generated even number should be between " + min + " and " + max + ": " + even);
        assertTrue(even % 2 == 0, "Generated number should be even: " + even);
    }

    @Test
    void numberGenerator_odd_generatesValidOddNumber() {
        int min = 11;
        int max = 21;
        int odd = mocker.number().odd(min, max);
        assertTrue(odd >= min && odd <= max,
                "Generated odd number should be between " + min + " and " + max + ": " + odd);
        assertTrue(odd % 2 != 0, "Generated number should be odd: " + odd);
    }

    @Test
    void numberGenerator_decimal_generatesValidDecimal() {
        double min = 1.5;
        double max = 5.5;
        double decimal = mocker.number().decimal(min, max);
        assertTrue(decimal >= min && decimal <= max,
                "Generated decimal should be between " + min + " and " + max + ": " + decimal);
    }

    @Test
    void numberGenerator_percentage_generatesValidPercentage() {
        double percentage = mocker.number().percentage();
        assertTrue(percentage >= 0.0 && percentage <= 100.0,
                "Generated percentage should be between 0 and 100: " + percentage);
    }

    @Test
    void usernameGenerator_random_generatesValidUsername() {
        String username = mocker.username().random();
        assertNotNull(username, "Generated username should not be null");
        assertTrue(username.length() >= 3, "Username should be at least 3 characters: " + username);
        assertTrue(username.matches("[a-zA-Z0-9]+"), "Username should contain only alphanumeric characters: " + username);
    }

    @Test
    void usernameGenerator_nameBased_generatesValidUsername() {
        String username = mocker.username().nameBased();
        assertNotNull(username, "Generated name-based username should not be null");
        assertTrue(username.length() >= 3, "Username should be at least 3 characters: " + username);
        assertTrue(username.matches("[a-zA-Z0-9.]+"), "Username should contain alphanumeric characters and dots: " + username);
    }

    @Test
    void usernameGenerator_custom_generatesValidUsername() {
        int length = 8;
        boolean specialChars = false;
        String username = mocker.username().custom(length, specialChars);
        assertNotNull(username, "Generated custom username should not be null");
        assertEquals(length, username.length(), "Username should match specified length: " + username);
        assertTrue(username.matches("[a-zA-Z0-9]+"), "Username should contain only alphanumeric characters when special chars are disabled: " + username);
    }
}