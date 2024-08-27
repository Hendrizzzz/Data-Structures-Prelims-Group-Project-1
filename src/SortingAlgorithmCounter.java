import java.util.Arrays;

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
        MedicalRecords[] medicalRecordsCopy = Arrays.copyOf(medicalRecords, medicalRecords.length);
        long statementCount = 0;

        for (int i = 0; i < medicalRecordsCopy.length - 1; i++, statementCount++) {
            boolean swapped = false;

            for (int j = 0; j < medicalRecordsCopy.length - i - 1; j++, statementCount++) {
                // Increment statement count for the comparison check
                statementCount++;

                // Check for null before comparing
                if (medicalRecordsCopy[j].compareTo(medicalRecordsCopy[j + 1]) > 0) {
                    MedicalRecords temp = medicalRecordsCopy[j];
                    medicalRecordsCopy[j] = medicalRecordsCopy[j + 1];
                    medicalRecordsCopy[j + 1] = temp;
                    swapped = true;
                    statementCount += 4; // all statements under the if-condition
                }
            }

            // boolean declaration and initialization, and the if-condition below
            statementCount += 3;

            if (!swapped) { // If inner loop swapped no two elements, then break
                statementCount++;
                break;
            }
        }

        // Include slack from the outer and inner loops
        statementCount += 1; // II
        return statementCount;
    }



    @Override
    public long getInsertionSortStatementCount(MedicalRecords[] medicalRecords) {
        MedicalRecords[] medicalRecordsCopy = Arrays.copyOf(medicalRecords, medicalRecords.length);
        long statementCount = 0;
        for (int i = 1; i < medicalRecordsCopy.length; i++, statementCount++) {
            MedicalRecords another = medicalRecordsCopy[i];
            int j = i - 1;

            // This is originally while loop, but converted to for loop due to counting issues
            for (; j >= 0 && medicalRecordsCopy[j].compareTo(another) > 0; j--, statementCount++) {
                medicalRecordsCopy[j + 1] = medicalRecordsCopy[j];
                statementCount++;
            }
            statementCount++;

            if (j + 1 < medicalRecordsCopy.length) {
                medicalRecordsCopy[j + 1] = another;
                statementCount++;
            }
            statementCount += 3;
        }
        statementCount += 1; // the missing 1 from outer and inner for loop
        return statementCount;
    }



    @Override
    public long getSelectionSortStatementCount(MedicalRecords[] medicalRecords) {
        MedicalRecords[] medicalRecordsCopy = Arrays.copyOf(medicalRecords, medicalRecords.length);
        long statementCount = 0;
        for (int i = 0; i < medicalRecordsCopy.length - 1; i++, statementCount++) {
            int minIndex = i;
            for (int j = i + 1; j < medicalRecordsCopy.length; j++, statementCount++) {
                statementCount++; // counter for the if condition below because it executes once, whether true or false

                if (medicalRecordsCopy[j].compareTo(medicalRecordsCopy[minIndex]) < 0) {
                    minIndex = j;
                    statementCount++;
                }
            }

            statementCount += 3; // 1 from the slack of inner for loop and 1 for the if condition and 1 for the minIndex
            if (minIndex != i) {
                MedicalRecords temp = medicalRecordsCopy[i];
                medicalRecordsCopy[i] = medicalRecordsCopy[minIndex];
                medicalRecordsCopy[minIndex] = temp;
                statementCount += 3; // Statements for the swap
            }
        }
        statementCount += 1; // 1 from the slack from the outer for loop
        return statementCount;
    }

}
