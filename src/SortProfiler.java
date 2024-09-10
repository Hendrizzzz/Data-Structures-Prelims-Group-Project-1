import java.math.BigInteger;

/**
 * The {@code SortProfiler} interface defines methods for profiling sorting algorithms
 * by counting the number of executed statements.
 * <p>
 * Implementations of this interface should provide specific methods to count statements
 * for various sorting algorithms, such as bubble sort, insertion sort, and selection sort,
 * when applied to an array of {@link MedicalRecords} objects.
 * </p>
 *
 * @author Jim Hendrix
 */
public interface SortProfiler {

    /**
     * Counts the number of statements executed by the bubble sort algorithm while sorting the given array.
     *
     * @param medicalRecords The array of {@link MedicalRecords} objects to be sorted.
     * @return The number of statements executed by the bubble sort algorithm.
     */
    BigInteger getBubbleSortStatementCount(MedicalRecords[] medicalRecords);

    /**
     * Counts the number of statements executed by the insertion sort algorithm while sorting the given array.
     *
     * @param medicalRecords The array of {@link MedicalRecords} objects to be sorted.
     * @return The number of statements executed by the insertion sort algorithm.
     */
    BigInteger getInsertionSortStatementCount(MedicalRecords[] medicalRecords);

    /**
     * Counts the number of statements executed by the selection sort algorithm while sorting the given array.
     *
     * @param medicalRecords The array of {@link MedicalRecords} objects to be sorted.
     * @return The number of statements executed by the selection sort algorithm.
     */
    BigInteger getSelectionSortStatementCount(MedicalRecords[] medicalRecords);
}
