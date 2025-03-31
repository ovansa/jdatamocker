package com.ovansa.jdatamocker.provider;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides comprehensive mock date data generation for testing purposes.
 * This class generates random dates with various constraints including:
 * <ul>
 *   <li>Random dates within configurable ranges</li>
 *   <li>Future and past dates relative to current date</li>
 *   <li>Weekdays-only or weekends-only dates</li>
 *   <li>Dates within specific months or seasons</li>
 *   <li>Age-appropriate birth dates</li>
 * </ul>
 * All methods are thread-safe when used with {@link ThreadLocalRandom}.
 *
 * @author Muhammed Ibrahim
 * @version 2.0
 * @since 1.0
 */
public class DateProvider extends BaseProvider implements DataProvider {

    private static final LocalDate DEFAULT_MIN_DATE = LocalDate.of(1900, 1, 1);
    private static final LocalDate DEFAULT_MAX_DATE = LocalDate.of(2023, 12, 31);

    /**
     * Constructs a new {@code DateProvider} with the specified random number generator.
     *
     * @param random the {@link ThreadLocalRandom} instance to use for random number generation
     * @throws NullPointerException if random is null
     */
    public DateProvider(ThreadLocalRandom random) {
        super(Objects.requireNonNull(random, "Random number generator must not be null"));
    }

    /**
     * Generates a random date between default range (1900-01-01 to 2023-12-31).
     *
     * @return a randomly generated {@link LocalDate} within default range
     * <p>Example: {@code randomDate()} might return {@code LocalDate.of(1999, 5, 15)}
     */
    public LocalDate randomDate() {
        return randomDate(DEFAULT_MIN_DATE, DEFAULT_MAX_DATE);
    }

    /**
     * Generates a random date between specified min and max dates (inclusive).
     *
     * @param min the minimum date (inclusive)
     * @param max the maximum date (inclusive)
     * @return a random date between min and max
     * @throws IllegalArgumentException if min is after max
     * <p>Example: {@code randomDate(LocalDate.of(2020,1,1), LocalDate.now())}
     * might return {@code LocalDate.of(2022, 8, 20)}
     */
    public LocalDate randomDate(LocalDate min, LocalDate max) {
        Objects.requireNonNull(min, "Min date must not be null");
        Objects.requireNonNull(max, "Max date must not be null");

        if (min.isAfter(max)) {
            throw new IllegalArgumentException("Max date must be on or after min date");
        }

        long minDay = min.toEpochDay();
        long maxDay = max.toEpochDay();
        return LocalDate.ofEpochDay(minDay + random.nextLong(maxDay - minDay + 1));
    }

    /**
     * Generates a random date in the past relative to current date.
     *
     * @param maxYearsAgo maximum number of years in the past
     * @return a random past date
     * @throws IllegalArgumentException if maxYearsAgo is negative
     * <p>Example: {@code randomPastDate(5)} might return a date between today and 5 years ago
     */
    public LocalDate randomPastDate(int maxYearsAgo) {
        if (maxYearsAgo < 0) {
            throw new IllegalArgumentException("Years ago must be positive");
        }
        LocalDate today = LocalDate.now();
        return randomDate(today.minusYears(maxYearsAgo), today);
    }

    /**
     * Generates a random date in the future relative to current date.
     *
     * @param maxYearsAhead maximum number of years in the future
     * @return a random future date
     * @throws IllegalArgumentException if maxYearsAhead is negative
     * <p>Example: {@code randomFutureDate(3)} might return a date between today and 3 years from now
     */
    public LocalDate randomFutureDate(int maxYearsAhead) {
        if (maxYearsAhead < 0) {
            throw new IllegalArgumentException("Years ahead must be positive");
        }
        LocalDate today = LocalDate.now();
        return randomDate(today, today.plusYears(maxYearsAhead));
    }

    /**
     * Generates a random date that is a weekday (Monday-Friday).
     *
     * @return a random weekday date
     * <p>Example: {@code randomWeekday()} might return {@code LocalDate.of(2023, 4, 12)} (a Wednesday)
     */
    public LocalDate randomWeekday() {
        LocalDate date;
        do {
            date = randomDate();
        } while (date.getDayOfWeek().getValue() > 5); // 6 and 7 are weekend
        return date;
    }

    /**
     * Generates a random date that is a weekend (Saturday-Sunday).
     *
     * @return a random weekend date
     * <p>Example: {@code randomWeekend()} might return {@code LocalDate.of(2023, 4, 15)} (a Saturday)
     */
    public LocalDate randomWeekend() {
        LocalDate date;
        do {
            date = randomDate();
        } while (date.getDayOfWeek().getValue() <= 5); // 1-5 are weekdays
        return date;
    }

    /**
     * Generates a random birth date for a person of specified age.
     *
     * @param age the desired age in years
     * @return a birth date that would make someone the specified age
     * @throws IllegalArgumentException if age is negative
     * <p>Example: {@code randomBirthDate(25)} might return a date around 25 years ago
     */
    public LocalDate randomBirthDate(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age must be positive");
        }
        LocalDate today = LocalDate.now();
        LocalDate maxDate = today.minusYears(age);
        LocalDate minDate = maxDate.minusYears(1).plusDays(1);
        return randomDate(minDate, maxDate);
    }

    /**
     * Generates a random date within a specific month.
     *
     * @param month the month (1-12)
     * @param year the year
     * @return a random date within the specified month and year
     * @throws IllegalArgumentException if month is invalid
     * <p>Example: {@code randomDateInMonth(2, 2020)} might return {@code LocalDate.of(2020, 2, 15)}
     */
    public LocalDate randomDateInMonth(int month, int year) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        return randomDate(start, end);
    }

    /**
     * Generates a random date within a specific season.
     *
     * @param season the season (1=Winter, 2=Spring, 3=Summer, 4=Fall)
     * @param year the year
     * @return a random date within the specified season and year
     * @throws IllegalArgumentException if season is invalid
     * <p>Example: {@code randomDateInSeason(3, 2022)} might return {@code LocalDate.of(2022, 7, 20)}
     */
    public LocalDate randomDateInSeason(int season, int year) {
        LocalDate start, end;
        switch (season) {
            case 1: // Winter (Dec-Feb)
                start = LocalDate.of(year-1, 12, 1);
                end = LocalDate.of(year, 2, 28);
                if (end.isLeapYear()) end = end.withDayOfMonth(29);
                break;
            case 2: // Spring (Mar-May)
                start = LocalDate.of(year, 3, 1);
                end = LocalDate.of(year, 5, 31);
                break;
            case 3: // Summer (Jun-Aug)
                start = LocalDate.of(year, 6, 1);
                end = LocalDate.of(year, 8, 31);
                break;
            case 4: // Fall (Sep-Nov)
                start = LocalDate.of(year, 9, 1);
                end = LocalDate.of(year, 11, 30);
                break;
            default:
                throw new IllegalArgumentException("Season must be between 1 and 4");
        }
        return randomDate(start, end);
    }
}