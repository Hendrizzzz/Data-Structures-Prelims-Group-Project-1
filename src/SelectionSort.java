import java.util.Arrays;

public class SelectionSort implements SortingAlgorithm{

    private long statementCount;

    public SelectionSort(){
        statementCount = 0;
    }


    @Override
    public void sort(MedicalRecord[] medicalRecords) {
        MedicalRecord[] medicalRecordsCopy = Arrays.copyOf(medicalRecords, medicalRecords.length);
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
                MedicalRecord temp = medicalRecordsCopy[i];
                medicalRecordsCopy[i] = medicalRecordsCopy[minIndex];
                medicalRecordsCopy[minIndex] = temp;
                statementCount += 3; // Statements for the swap
            }
        }
        statementCount++; // 1 from the slack from the outer for loop
    }

    @Override
    public long getStatementCount() {
        return statementCount;
    }


}
