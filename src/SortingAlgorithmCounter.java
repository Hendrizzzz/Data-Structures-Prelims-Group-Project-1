/**
 * The {@code SortingAlgorithmCounter} class implements the {@code SortProfiler} interface
 * @see SortProfiler
 */
public class SortingAlgorithmCounter implements SortProfiler {
    @Override
    public int getBubbleSortStatementCount(MedicalRecords[] objects) {
        return 0;
    }

    @Override
    public int getInsertionSortStatementCount(MedicalRecords[] medicalRecordsArray) {
        int count = 0;
        for (int i = 1; i < medicalRecordsArray.length; ++i, count++) {
            MedicalRecords another = medicalRecordsArray[i];
            int j = i - 1;
            count += 2;
            while (j >= 0 && medicalRecordsArray[j].compareTo(another) > 0) {
                medicalRecordsArray[j + 1] = medicalRecordsArray[j];
                j = j - 1;
                count += 2;
            }
            count += 1;
            medicalRecordsArray[j + 1] = another;
        }
    }

    @Override
    public int getSelectionSortStatementCount(MedicalRecords[] objects) {
        return 0;
    }
}
