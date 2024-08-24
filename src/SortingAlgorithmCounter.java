/**
 * The {@code SortingAlgorithmCounter} class implements the {@code SortProfiler} interface
 * @see SortProfiler
 */
public class SortingAlgorithmCounter implements SortProfiler {
    @Override
    public int getBubbleSortStatementCount(MedicalRecords[] medicalRecordsArray) {
        int statementCount = 0;  // Initialize statement count

        for (int i = 0; i < medicalRecordsArray.length - 1; i++, statementCount++) {
            boolean swapped = false;
            statementCount++; // counter of the above statement

            for (int j = 0; j < medicalRecordsArray.length - i - 1; j++, statementCount++) { // the

                statementCount++;
                if (medicalRecordsArray[j].compareTo(medicalRecordsArray[j + 1]) > 0) {
                    // Swap medicalRecordsArray[j] and medicalRecordsArray[j + 1]
                    MedicalRecords temp = medicalRecordsArray[j];
                    medicalRecordsArray[j] = medicalRecordsArray[j + 1];
                    medicalRecordsArray[j + 1] = temp;
                    swapped = true;
                    statementCount += 4;
                }
            }

            // If no two elements were swapped by inner loop, then break
            statementCount++;
            if (!swapped) {
                statementCount++;
                break;
            }
        }

        statementCount += 2; // 1 from the slack of outer for loop, 1 from the slack of for loop, and 1 from the slack if the 'if condition' = 3
        return statementCount;
    }


    @Override
    public int getInsertionSortStatementCount(MedicalRecords[] medicalRecordsArray) {
        int count = 1;
        for (int i = 1; i < medicalRecordsArray.length; ++i, count++) { // increment the element count and the count of the statements
            MedicalRecords another = medicalRecordsArray[i];
            int j = i - 1;
            count += 2;
            while (j >= 0 && medicalRecordsArray[j].compareTo(another) > 0) {
                medicalRecordsArray[j + 1] = medicalRecordsArray[j];
                j = j - 1;
                count += 3;
            }
            medicalRecordsArray[j + 1] = another;
            count += 1;
        }
        count += 1; // the missing 1 from the while loop
        return count;
    }

    @Override
    public int getSelectionSortStatementCount(MedicalRecords[] objects) {
        return -1;
    }
}
