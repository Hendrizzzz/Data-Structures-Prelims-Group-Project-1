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
    public long getBubbleSortStatementCount(MedicalRecords[] medicalRecords) {
        long statementCount = 0;

        for (int i = 0; i < medicalRecords.length - 1; i++, statementCount++) {
            boolean swapped = false;

            for (int j = 0; j < medicalRecords.length - i - 1; j++, statementCount++) {
                // Increment statement count for the comparison check
                statementCount++;

                // Check for null before comparing
                if (medicalRecords[j].compareTo(medicalRecords[j + 1]) > 0) {
                    MedicalRecords temp = medicalRecords[j];
                    medicalRecords[j] = medicalRecords[j + 1];
                    medicalRecords[j + 1] = temp;
                    swapped = true;
                    statementCount += 4; // all statements under the if-condition
                }
            }

            // boolean declaration and initialization, and the if-condition below
            statementCount += 2;

            if (!swapped) { // If inner loop swapped no two elements, then break
                statementCount++;
                break;
            }
        }

        // Include slack from the outer and inner loops
        statementCount += 2;
        return statementCount;
    }



    @Override
    public long getInsertionSortStatementCount(MedicalRecords[] medicalRecords) {
        long statementCount = 0;
        for (int i = 1; i < medicalRecords.length; i++, statementCount++) {
            MedicalRecords another = medicalRecords[i];
            int j = i - 1;

            while (medicalRecords[j].compareTo(another) > 0) {
                medicalRecords[j + 1] = medicalRecords[j];
                j = j - 1;
                statementCount += 2;
            }

            if (j + 1 < medicalRecords.length) {
                medicalRecords[j + 1] = another;
                statementCount++;
            }
            statementCount += 4;
        }
        statementCount += 2; // the missing 1 from for loop and while loop
        return statementCount;
    }



    @Override
    public long getSelectionSortStatementCount(MedicalRecords[] medicalRecords) {
        long statementCount = 0;
        for (int i = 0; i < medicalRecords.length - 1; i++, statementCount++) {
            int minIndex = i;
            for (int j = i + 1; j < medicalRecords.length; j++, statementCount++) {
                statementCount++;

                // Check for null before comparison
                if (medicalRecords[minIndex].compareTo(medicalRecords[j]) < 0) {
                    minIndex = j;
                    statementCount++;
                }
            }

            // Swap if minIndex has changed
            if (minIndex != i) {
                MedicalRecords temp = medicalRecords[i];
                medicalRecords[i] = medicalRecords[minIndex];
                medicalRecords[minIndex] = temp;
                statementCount += 4; // Statements for the swap
            }
            statementCount += 2;
        }
        statementCount += 2; // Account for the final loop statements
        return statementCount;
    }

}
