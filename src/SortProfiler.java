/**
 * The {@code SortProfiler} interface defines methods for counting statements for sorting algorithms
 */
public interface SortProfiler {
    // "objects" will be replaced by the actual object to use
    // "Object" will be replaced
    int getBubbleSortStatementCount(MedicalRecords[] objects);
    int getInsertionSortStatementCount(MedicalRecords[] objects);
    int getSelectionSortStatementCount(MedicalRecords[] objects);
}
