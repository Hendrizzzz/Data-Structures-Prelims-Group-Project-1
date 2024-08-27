/**
 * The {@code SortProfiler} interface defines methods for counting statements for sorting algorithms
 */
public interface SortProfiler {
    long getBubbleSortStatementCount(MedicalRecords[] medicalRecords);
    long getInsertionSortStatementCount(MedicalRecords[] medicalRecords);
    long getSelectionSortStatementCount(MedicalRecords[] medicalRecords);
}
