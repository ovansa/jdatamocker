package com.ovansa.jdatamocker.provider;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Objects;

/**
 * Provides comprehensive mock numerical data generation for testing purposes.
 * This class generates various types of random numbers including:
 * <ul>
 *   <li>Random numbers within ranges (integer and decimal)</li>
 *   <li>Even/odd number generation</li>
 *   <li>Prime number generation</li>
 *   <li>Numbers with specific divisibility</li>
 *   <li>Decimal numbers with precision control</li>
 *   <li>Percentage values</li>
 *   <li>Currency values</li>
 * </ul>
 * All methods are thread-safe when used with {@link ThreadLocalRandom}.
 *
 * @author Muhammed Ibrahim
 * @version 1.0.8
 * @since 1.0.0
 */
public class NumberProvider extends BaseProvider implements DataProvider {

    /**
     * Constructs a new {@code NumberProvider} with the specified random number generator.
     *
     * @param random the {@link ThreadLocalRandom} instance to use for random number generation
     * @throws NullPointerException if random is null
     */
    public NumberProvider(ThreadLocalRandom random) {
        super(Objects.requireNonNull(random, "Random number generator must not be null"));
    }

    /**
     * Generates a random integer within the specified range [min, max], inclusive.
     *
     * @param min the minimum value of the range (inclusive)
     * @param max the maximum value of the range (inclusive)
     * @return a random integer between {@code min} and {@code max}, inclusive
     * @throws IllegalArgumentException if {@code min > max}
     * <p>Example: {@code randomNumber(1, 10)} might return {@code 7}
     * <p>Example: {@code randomNumber(-5, 5)} might return {@code -2}
     */
    public int randomNumber(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Max must be greater than or equal to min");
        }
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Generates a random even integer within the specified range [min, max], inclusive.
     *
     * @param min the minimum value of the range (inclusive)
     * @param max the maximum value of the range (inclusive)
     * @return a random even integer between {@code min} and {@code max}, inclusive
     * @throws IllegalArgumentException if {@code min > max} or if no even numbers exist in range
     * <p>Example: {@code randomEvenNumber(1, 10)} might return {@code 4}
     * <p>Example: {@code randomEvenNumber(11, 11)} throws {@code IllegalArgumentException}
     */
    public int randomEvenNumber(int min, int max) {
        validateRange(min, max);

        // Adjust min to nearest even if odd
        int adjustedMin = min % 2 == 0 ? min : min + 1;
        // Adjust max to nearest even if odd
        int adjustedMax = max % 2 == 0 ? max : max - 1;

        if (adjustedMin > adjustedMax) {
            throw new IllegalArgumentException("No even numbers exist in the specified range");
        }

        return random.nextInt((adjustedMax - adjustedMin) / 2 + 1) * 2 + adjustedMin;
    }

    /**
     * Generates a random odd integer within the specified range [min, max], inclusive.
     *
     * @param min the minimum value of the range (inclusive)
     * @param max the maximum value of the range (inclusive)
     * @return a random odd integer between {@code min} and {@code max}, inclusive
     * @throws IllegalArgumentException if {@code min > max} or if no odd numbers exist in range
     * <p>Example: {@code randomOddNumber(1, 10)} might return {@code 7}
     * <p>Example: {@code randomOddNumber(10, 10)} throws {@code IllegalArgumentException}
     */
    public int randomOddNumber(int min, int max) {
        validateRange(min, max);

        // Adjust min to nearest odd if even
        int adjustedMin = min % 2 != 0 ? min : min + 1;
        // Adjust max to nearest odd if even
        int adjustedMax = max % 2 != 0 ? max : max - 1;

        if (adjustedMin > adjustedMax) {
            throw new IllegalArgumentException("No odd numbers exist in the specified range");
        }

        return random.nextInt((adjustedMax - adjustedMin) / 2 + 1) * 2 + adjustedMin;
    }

    /**
     * Generates a random prime number within the specified range [min, max], inclusive.
     *
     * @param min the minimum value of the range (inclusive)
     * @param max the maximum value of the range (inclusive)
     * @return a random prime number between {@code min} and {@code max}, inclusive
     * @throws IllegalArgumentException if {@code min > max} or if no primes exist in range
     * <p>Example: {@code randomPrime(10, 20)} might return {@code 13}
     * <p>Example: {@code randomPrime(24, 28)} throws {@code IllegalArgumentException}
     */
    public int randomPrime(int min, int max) {
        validateRange(min, max);

        // Generate all primes in the range
        java.util.List<Integer> primes = new java.util.ArrayList<>();
        for (int i = Math.max(2, min); i <= max; i++) {
            if (isPrime(i)) {
                primes.add(i);
            }
        }

        if (primes.isEmpty()) {
            throw new IllegalArgumentException("No prime numbers exist in the specified range");
        }

        return primes.get(random.nextInt(primes.size()));
    }

    /**
     * Generates a random number divisible by the specified divisor within [min, max].
     *
     * @param min the minimum value of the range (inclusive)
     * @param max the maximum value of the range (inclusive)
     * @param divisor the number to divide by (must be positive)
     * @return a random number divisible by {@code divisor} in the range
     * @throws IllegalArgumentException if {@code min > max}, {@code divisor ≤ 0},
     *         or no divisible numbers exist in range
     * <p>Example: {@code randomDivisible(10, 100, 7)} might return {@code 42}
     * <p>Example: {@code randomDivisible(10, 15, 7)} might return {@code 14}
     */
    public int randomDivisible(int min, int max, int divisor) {
        validateRange(min, max);
        if (divisor <= 0) {
            throw new IllegalArgumentException("Divisor must be positive");
        }

        // Adjust min to nearest divisible number
        int adjustedMin = ((min + divisor - 1) / divisor) * divisor;
        if (adjustedMin > max) {
            throw new IllegalArgumentException(
                    "No numbers divisible by " + divisor + " exist in the specified range");
        }

        // Calculate how many divisible numbers exist in range
        int count = (max - adjustedMin) / divisor + 1;
        return adjustedMin + random.nextInt(count) * divisor;
    }

    /**
     * Generates a random integer within [min, max], inclusive.
     *
     * @param min the minimum value (inclusive)
     * @param max the maximum value (inclusive)
     * @return a random integer in the specified range
     * @throws IllegalArgumentException if min > max
     */
    public int randomInt(int min, int max) {
        validateRange(min, max);
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Generates a random even integer within [min, max], inclusive.
     *
     * @param min the minimum value (inclusive)
     * @param max the maximum value (inclusive)
     * @return a random even integer in the specified range
     * @throws IllegalArgumentException if min > max or no even numbers exist in range
     */
    public int randomEvenInt(int min, int max) {
        validateRange(min, max);
        int adjustedMin = (min % 2 == 0) ? min : min + 1;
        int adjustedMax = (max % 2 == 0) ? max : max - 1;

        if (adjustedMin > adjustedMax) {
            throw new IllegalArgumentException("No even numbers in range [" + min + "," + max + "]");
        }

        return adjustedMin + 2 * random.nextInt((adjustedMax - adjustedMin) / 2 + 1);
    }

    /**
     * Generates a random decimal value within [min, max], with default precision.
     *
     * @param min the minimum value (inclusive)
     * @param max the maximum value (inclusive)
     * @return a random double in the specified range
     * @throws IllegalArgumentException if min > max or if range is invalid
     */
    public double randomDecimal(double min, double max) {
        validateRange(min, max);
        return min + (max - min) * random.nextDouble();
    }

    /**
     * Generates a random decimal value with specified precision (decimal places).
     *
     * @param min the minimum value (inclusive)
     * @param max the maximum value (inclusive)
     * @param precision the number of decimal places (0-10)
     * @return a random decimal in the specified range with given precision
     * @throws IllegalArgumentException if min > max or precision is invalid
     * <p>Example: {@code randomDecimal(1.0, 5.0, 2)} might return {@code 3.14}
     */
    public double randomDecimal(double min, double max, int precision) {
        validateRange(min, max);
        if (precision < 0 || precision > 10) {
            throw new IllegalArgumentException("Precision must be between 0 and 10");
        }

        double value = randomDecimal(min, max);
        double factor = Math.pow(10, precision);
        return Math.round(value * factor) / factor;
    }

    /**
     * Generates a random percentage value between 0.0 and 100.0.
     *
     * @param precision the number of decimal places (0-4)
     * @return a random percentage value
     * <p>Example: {@code randomPercentage(1)} might return {@code 56.3}
     */
    public double randomPercentage(int precision) {
        return randomDecimal(0.0, 100.0, precision);
    }

    /**
     * Generates a random currency value with standard 2 decimal precision.
     *
     * @param min the minimum value (inclusive)
     * @param max the maximum value (inclusive)
     * @return a random currency value
     * <p>Example: {@code randomCurrency(10.0, 100.0)} might return {@code 45.67}
     */
    public double randomCurrency(double min, double max) {
        return randomDecimal(min, max, 2);
    }

    /**
     * Generates a random boolean value with specified probability.
     *
     * @param trueProbability the probability of returning true (0.0 to 1.0)
     * @return true or false based on the specified probability
     * @throws IllegalArgumentException if probability is outside [0.0, 1.0]
     * <p>Example: {@code randomBoolean(0.7)} has a 70% chance of returning {@code true}
     */
    public boolean randomBoolean(double trueProbability) {
        if (trueProbability < 0.0 || trueProbability > 1.0) {
            throw new IllegalArgumentException("Probability must be between 0.0 and 1.0");
        }
        return random.nextDouble() < trueProbability;
    }

    /**
     * Validates that min ≤ max for decimal ranges.
     */
    private void validateRange(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("Max must be ≥ min (received min=" + min + ", max=" + max + ")");
        }
        if (Double.isInfinite(min) || Double.isInfinite(max)) {
            throw new IllegalArgumentException("Infinite values not supported");
        }
        if (Double.isNaN(min) || Double.isNaN(max)) {
            throw new IllegalArgumentException("NaN values not supported");
        }
    }

    /**
     * Validates that min ≤ max for range parameters.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @throws IllegalArgumentException if min > max
     */
    private void validateRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Max must be greater than or equal to min");
        }
    }

    /**
     * Checks if a number is prime.
     *
     * @param n the number to check
     * @return true if the number is prime, false otherwise
     */
    private boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;

        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}