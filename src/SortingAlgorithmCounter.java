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
        statementCount += 2; // II
        return statementCount;
    }



    @Override
    public long getInsertionSortStatementCount(MedicalRecords[] medicalRecords) {
        long statementCount = 0;
        for (int i = 1; i < medicalRecords.length; i++, statementCount++) {
            MedicalRecords another = medicalRecords[i];
            int j = i - 1;

            // This is originally while loop, but converted to for loop due to counting issues
            for (; j >= 0 && medicalRecords[j].compareTo(another) > 0; j--, statementCount++) {
                medicalRecords[j + 1] = medicalRecords[j];
                statementCount++;
            }

            if (j + 1 < medicalRecords.length) {
                medicalRecords[j + 1] = another;
                statementCount++;
            }
            statementCount += 3;
        }
        statementCount += 2; // the missing 1 from outer and inner for loop
        return statementCount;
    }



    @Override
    public long getSelectionSortStatementCount(MedicalRecords[] medicalRecords) {
        long statementCount = 0;
        for (int i = 0; i < medicalRecords.length - 1; i++, statementCount++) {
            int minIndex = i;
            for (int j = i + 1; j < medicalRecords.length; j++, statementCount++) {
                statementCount++; // counter for the if condition below because it executes once, whether true or false

                if (medicalRecords[j].compareTo(medicalRecords[minIndex]) < 0) {
                    minIndex = j;
                    statementCount++;
                }
            }

            // Swap if minIndex has changed
            if (minIndex != i) {
                MedicalRecords temp = medicalRecords[i];
                medicalRecords[i] = medicalRecords[minIndex];
                medicalRecords[minIndex] = temp;
                statementCount += 3; // Statements for the swap
            }
            statementCount += 2; // one for the minIndex and the if condition
        }
        statementCount += 2; // Account for the final loop statements
        return statementCount;
    }

}
