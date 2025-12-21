package com.lucadani;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;

/**
 * Solves the "Gift Shop" puzzle challenge.
 * <p>
 * This class identifies "invalid" product IDs based on specific pattern repetition rules
 * within given numerical ranges. It handles two distinct validation rules:
 * <ul>
 * <li><b>Part 1:</b> IDs formed by a sequence repeated exactly twice (e.g., "123123").</li>
 * <li><b>Part 2:</b> IDs formed by a sequence repeated two or more times (e.g., "123123123").</li>
 * </ul>
 */
public class GiftShop {
    private static final String FILE_NAME = "input.txt";
    private static final String DEFAULT_INPUT = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,"
            + "1698522-1698528,446443-446449,38593856-38593862,565653-565659,"
            + "824824821-824824827,2121212118-2121212124";

    /**
     * The entry point of the application.
     * <p>
     * Orchestrates the reading of input data and the calculation of results for both
     * parts of the puzzle, printing the final sums to the console.
     *
     * @param args the command line arguments (not used).
     * @throws IOException if an I/O error occurs while reading the input file.
     */
    public static void main(String[] args) throws IOException {
        String dataToProcess = getDataToProcess(FILE_NAME);
        long[] results = solve(dataToProcess);
        System.out.printf("Part 1: sum of invalid IDs is %d\n", results[0]);
        System.out.printf("Part 2: sum of invalid IDs is %d\n", results[1]);
    }

    /**
     * Retrieves the input data to be processed.
     * <p>
     * Attempts to read from the specified file. If the file does not exist or is empty,
     * it falls back to the hardcoded {@link #DEFAULT_INPUT}.
     *
     * @param fileName the path to the input file.
     * @return the content of the file or the default input string if the file is unusable.
     * @throws IOException if an I/O error occurs during file reading.
     */
    private static String getDataToProcess(String fileName) throws IOException {
        var pathFile = Path.of(fileName);
        if (!Files.exists(pathFile)) {
            System.out.printf("The file %s does not exist, so we will use the default input.\n", fileName);
            return DEFAULT_INPUT;
        }
        String fileContent = Files.readString(pathFile);
        if (fileContent.trim().isEmpty()) {
            System.out.printf("The file %s is empty, so we will use the default input.\n", fileName);
            return DEFAULT_INPUT;
        }
        System.out.printf("The file %s has been read successfully.\n", fileName);
        return fileContent;
    }

    /**
     * Processes the input ranges and calculates the sum of invalid IDs for both puzzle parts.
     * <p>
     * This method parses the comma-separated ranges (e.g., "11-22") and iterates through
     * every number in each range. It applies two predicates to check for invalidity:
     * <ol>
     * <li><b>Rule 1:</b> The number is composed of two identical halves.</li>
     * <li><b>Rule 2:</b> The number is composed of a pattern repeated N times (where N >= 2).</li>
     * </ol>
     *
     * @param input the raw string containing comma-separated ID ranges.
     * @return a two-element array of type {@code long[]}, where:
     * <ul>
     * <li>Index 0 contains the sum for Part 1.</li>
     * <li>Index 1 contains the sum for Part 2.</li>
     * </ul>
     */
    public static long[] solve(String input) {
        Predicate<String> isInvalid1 = (value) -> {
            int len = value.length();
            if (len % 2 != 0) {
                return false;
            }
            String firstHalf = value.substring(0, len / 2);
            String secondHalf = value.substring(len / 2);
            return firstHalf.equals(secondHalf);
        };

        Predicate<String> isInvalid2 = (value) -> {
            int len = value.length();
            for (int k = 1; k <= len / 2; ++k) {
                if (len % k == 0) {
                    String pattern = value.substring(0, k);
                    int repetitions = len / k;
                    if (pattern.repeat(repetitions).equals(value)) {
                        return true;
                    }
                }
            }
            return false;
        };

        long totalSum1 = 0, totalSum2 = 0;
        String[] ranges = input.trim().replace("\n", "").replace("\r", "").split(",");
        for (String range : ranges) {
            if (range.isBlank()) {
                continue;
            }
            try {
                String[] parts = range.split("-");
                long start = Long.parseLong(parts[0].trim());
                long end = Long.parseLong(parts[1].trim());

                for (long index = Math.min(start, end); index <= Math.max(start, end); ++index) {
                    String str = String.valueOf(index);
                    if (isInvalid1.test(str)) {
                        totalSum1 += index;
                    }
                    if (isInvalid2.test(str)) {
                        totalSum2 += index;
                    }
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.err.printf("Error while parsing the range %s, we will skip it...\n", range);
            }
        }
        return new long[]{totalSum1, totalSum2};
    }
}