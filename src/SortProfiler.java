/**
 * The {@code SortProfiler} interface defines methods for counting statements for sorting algorithms
 */
public interface SortProfiler {
    int getBubbleSortStatementCount(MedicalRecords[] medicalRecords);
    int getInsertionSortStatementCount(MedicalRecords[] medicalRecords);
    int getSelectionSortStatementCount(MedicalRecords[] medicalRecords);
}
