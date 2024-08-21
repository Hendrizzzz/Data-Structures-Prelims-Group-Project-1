/**
 * The {@code SortProfiler} interface defines methods for counting statements for sorting algorithms
 */
public interface SortProfiler {
    // "objects" will be replaced by the actual object to use
    // "Object" will be replaced
    int getBubbleSortStatementCount(Object[] objects);
    int getInsertionSortStatementCount(Object[] objects);
    int getSelectionSortStatementCount(Object[] objects);
}
