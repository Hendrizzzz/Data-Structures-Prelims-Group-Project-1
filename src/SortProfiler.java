import java.math.BigInteger;

/**
 * The {@code SortProfiler} interface defines methods for counting statements for sorting algorithms
 */
public interface SortProfiler {
    BigInteger getBubbleSortStatementCount(MedicalRecords[] medicalRecords);
    BigInteger getInsertionSortStatementCount(MedicalRecords[] medicalRecords);
    BigInteger getSelectionSortStatementCount(MedicalRecords[] medicalRecords);
}
