import java.math.BigInteger;
import java.util.Arrays;

/**
 * Implements the {@link SortProfiler} interface to count the number of statements executed
 * by various sorting algorithms.
 * <p>
 * This class provides methods to calculate the number of statements executed during the execution
 * of Bubble Sort, Insertion Sort, and Selection Sort algorithms. The statement count is tracked
 * using a {@link BigInteger} to accommodate large numbers.
 * </p>
 *
 * @author Jim Hendrix Bag-eo
 *
 * @see SortProfiler
 */
public class SortingAlgorithmCounter implements SortProfiler {

    /**
     * Counts the number of statements executed by the Bubble Sort algorithm while sorting the
     * provided array of {@link MedicalRecords}.
     * <p>
     * The count includes comparisons, assignments, and other statements executed within the
     * algorithm. The method creates a copy of the input array to avoid modifying the original array.
     * </p>
     *
     * @param medicalRecords Array of {@link MedicalRecords} objects to be sorted.
     * @return The number of statements executed during the Bubble Sort.
     */
    @Override
    public BigInteger getBubbleSortStatementCount(MedicalRecords[] medicalRecords) {
        MedicalRecords[] medicalRecordsCopy = Arrays.copyOf(medicalRecords, medicalRecords.length);
        BigInteger statementCount = new BigInteger("0");

        for (int i = 0; i < medicalRecordsCopy.length - 1; i++, statementCount = statementCount.add(BigInteger.ONE)) {
            boolean swapped = false;

            for (int j = 0; j < medicalRecordsCopy.length - i - 1; j++, statementCount = statementCount.add(BigInteger.ONE)) {
                // Increment statement count for the comparison check
                statementCount = statementCount.add(BigInteger.ONE);

                // Check for null before comparing
                if (medicalRecordsCopy[j].compareTo(medicalRecordsCopy[j + 1]) > 0) {
                    MedicalRecords temp = medicalRecordsCopy[j];
                    medicalRecordsCopy[j] = medicalRecordsCopy[j + 1];
                    medicalRecordsCopy[j + 1] = temp;
                    swapped = true;
                    statementCount = statementCount.add(BigInteger.TWO); // all statements under the if-condition
                    statementCount = statementCount.add(BigInteger.TWO);
                }
            }

            // boolean declaration and initialization, and the if-condition below
            statementCount = statementCount.add(BigInteger.TWO);
            statementCount = statementCount.add(BigInteger.ONE);

            if (!swapped) { // If inner loop swapped no two elements, then break
                statementCount = statementCount.add(BigInteger.ONE);
                break;
            }
        }

        statementCount = statementCount.add(BigInteger.TWO); // the slack from the outer for loop and for the return statement
        return statementCount;
    }

    /**
     * Counts the number of statements executed by the Insertion Sort algorithm while sorting the
     * provided array of {@link MedicalRecords}.
     * <p>
     * The count includes comparisons, assignments, and other statements executed within the
     * algorithm. The method creates a copy of the input array to avoid modifying the original array.
     * </p>
     *
     * @param medicalRecords Array of {@link MedicalRecords} objects to be sorted.
     * @return The number of statements executed during the Insertion Sort.
     */
    @Override
    public BigInteger getInsertionSortStatementCount(MedicalRecords[] medicalRecords) {
        MedicalRecords[] medicalRecordsCopy = Arrays.copyOf(medicalRecords, medicalRecords.length);
        BigInteger statementCount = new BigInteger("0");

        for (int i = 1; i < medicalRecordsCopy.length; i++, statementCount = statementCount.add(BigInteger.ONE)) {
            MedicalRecords another = medicalRecordsCopy[i];
            int j = i - 1;

            // This is originally a while loop, but converted to for loop due to counting issues
            for (; j >= 0 && medicalRecordsCopy[j].compareTo(another) > 0; j--, statementCount = statementCount.add(BigInteger.ONE)) {
                medicalRecordsCopy[j + 1] = medicalRecordsCopy[j];
                statementCount = statementCount.add(BigInteger.ONE);
            }
            statementCount = statementCount.add(BigInteger.ONE); // the slack from the inner for loop

            if (j + 1 < medicalRecordsCopy.length) {
                medicalRecordsCopy[j + 1] = another;
                statementCount = statementCount.add(BigInteger.ONE);
            }
            statementCount = statementCount.add(BigInteger.TWO); // the first two statements and the counter for the if-condition
            statementCount = statementCount.add(BigInteger.ONE);
        }
        statementCount = statementCount.add(BigInteger.TWO); // the missing 1 from outer and for the return statement
        return statementCount;
    }

    /**
     * Counts the number of statements executed by the Selection Sort algorithm while sorting the
     * provided array of {@link MedicalRecords}.
     * <p>
     * The count includes comparisons, assignments, and other statements executed within the
     * algorithm. The method creates a copy of the input array to avoid modifying the original array.
     * </p>
     *
     * @param medicalRecords Array of {@link MedicalRecords} objects to be sorted.
     * @return The number of statements executed during the Selection Sort.
     */
    @Override
    public BigInteger getSelectionSortStatementCount(MedicalRecords[] medicalRecords) {
        MedicalRecords[] medicalRecordsCopy = Arrays.copyOf(medicalRecords, medicalRecords.length);
        BigInteger statementCount = new BigInteger("0");

        for (int i = 0; i < medicalRecordsCopy.length - 1; i++, statementCount = statementCount.add(BigInteger.ONE)) {
            int minIndex = i;

            for (int j = i + 1; j < medicalRecordsCopy.length; j++, statementCount = statementCount.add(BigInteger.ONE)) {
                statementCount = statementCount.add(BigInteger.ONE); // counter for the if condition below because it executes once, whether true or false

                if (medicalRecordsCopy[j].compareTo(medicalRecordsCopy[minIndex]) < 0) {
                    minIndex = j;
                    statementCount = statementCount.add(BigInteger.ONE);
                }
            }

            statementCount = statementCount.add(BigInteger.TWO); // 1 from the slack of inner for loop and 1 for the if condition and 1 for the minIndex
            statementCount = statementCount.add(BigInteger.ONE);

            if (minIndex != i) {
                MedicalRecords temp = medicalRecordsCopy[i];
                medicalRecordsCopy[i] = medicalRecordsCopy[minIndex];
                medicalRecordsCopy[minIndex] = temp;
                statementCount = statementCount.add(BigInteger.TWO); // Statements for the swap
                statementCount = statementCount.add(BigInteger.ONE);
            }
        }
        statementCount = statementCount.add(BigInteger.TWO); // 1 from the slack from the outer for loop and 1 for the return statement
        return statementCount;
    }
}
