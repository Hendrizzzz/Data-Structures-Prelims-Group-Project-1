import java.util.Arrays;

public class InsertionSort implements SortingAlgorithm{

    private long statementCount;

    public InsertionSort() {
        statementCount = 0;
    }

    @Override
    public void sort(MedicalRecord[] medicalRecords) {
        MedicalRecord[] medicalRecordsCopy = Arrays.copyOf(medicalRecords, medicalRecords.length);
        for (int i = 1; i < medicalRecordsCopy.length; i++, statementCount++) {
            MedicalRecord another = medicalRecordsCopy[i];
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
    }

    @Override
    public long getStatementCount() {
        return statementCount;
    }
}
