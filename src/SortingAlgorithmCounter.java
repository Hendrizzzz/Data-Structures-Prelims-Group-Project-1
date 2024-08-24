/**
 * Implements {@code SortProfiler} to count statements for sorting algorithms.
 *
 * @author Jim Hendrix Bag-eo
 *
 * @see SortProfiler
 */
public class SortingAlgorithmCounter implements SortProfiler {


    /**
     * Counts statements in bubble sort algorithm while sorting.
     *
     * @param medicalRecords Array of {@code MedicalRecords} to sort.
     * @return Number of statements executed.
     */
    @Override
    public int getBubbleSortStatementCount(MedicalRecords[] medicalRecords) {
        int statementCount = 0;

        for (int i = 0; i < medicalRecords.length - 1; i++, statementCount++) {
            boolean swapped = false;

            for (int j = 0; j < medicalRecords.length - i - 1; j++, statementCount++) { // the
                statementCount++;
                if (medicalRecords[j].compareTo(medicalRecords[j + 1]) > 0) {
                    MedicalRecords temp = medicalRecords[j];
                    medicalRecords[j] = medicalRecords[j + 1];
                    medicalRecords[j + 1] = temp;
                    swapped = true;
                    statementCount += 4; // all statements under the if-condition
                }
            }

            statementCount += 2; // first statement under the outer for loop and the if condition below
            if (!swapped) { // If inner loop swapped no two elements, then break
                statementCount++;
                break;
            }
        }
        statementCount += 2; // Include slack from the outer and inner loops
        return statementCount;
    }


    /**
     * Counts statements in insertion sort algorithm while sorting.
     *
     * @param medicalRecords Array of {@code MedicalRecords} to sort.
     * @return Number of statements executed.
     */
    @Override
    public int getInsertionSortStatementCount(MedicalRecords[] medicalRecords) {
        int statementCount = 0;
        for (int i = 1; i < medicalRecords.length; i++, statementCount++) { // increment the element statementCount and the statementCount of the statements
            MedicalRecords another = medicalRecords[i];
            int j = i - 1;
            while (j >= 0 && medicalRecords[j].compareTo(another) > 0) {
                medicalRecords[j + 1] = medicalRecords[j];
                j = j - 1;
                statementCount += 2;
            }
            medicalRecords[j + 1] = another;
            statementCount += 4;
        }
        statementCount += 2; // the missing 1 from for loop and while loop
        return statementCount;
    }


    /**
     * Counts statements in selection sort algorithm while sorting.
     *
     * @param medicalRecords Array of {@code MedicalRecords} to sort.
     * @return Number of statements executed.
     */
    @Override
    public int getSelectionSortStatementCount(MedicalRecords[] medicalRecords) {
        int statementCount = 0;
        for (int i = 0; i < medicalRecords.length - 1; i++, statementCount++){
            int minIndex = i;
            for (int j = i + 1; j < medicalRecords.length; j++, statementCount++){
                statementCount++;
                if (medicalRecords[minIndex].compareTo(medicalRecords[j]) > 0){
                    minIndex = j;
                    statementCount++;
                }
            }
            
            MedicalRecords temp = medicalRecords[i];
            medicalRecords[i] = medicalRecords[minIndex];
            medicalRecords[minIndex] = temp;
            statementCount += 4;
        }
        statementCount += 2; // Account for the final loop statements
        return statementCount;
    }
}
