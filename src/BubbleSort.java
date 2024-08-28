import java.util.Arrays;

public class BubbleSort implements SortingAlgorithm{

    private long statementCount;

    public BubbleSort(){
        statementCount = 0;
    }


    @Override
    public void sort(MedicalRecord[] medicalRecords) {
        MedicalRecord[] medicalRecordsCopy = Arrays.copyOf(medicalRecords, medicalRecords.length);

        for (int i = 0; i < medicalRecordsCopy.length - 1; i++, statementCount++) {
            boolean swapped = false;

            for (int j = 0; j < medicalRecordsCopy.length - i - 1; j++, statementCount++) {
                // Increment statement count for the comparison check
                statementCount++;

                // Check for null before comparing
                if (medicalRecordsCopy[j].compareTo(medicalRecordsCopy[j + 1]) > 0) {
                    MedicalRecord temp = medicalRecordsCopy[j];
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
        statementCount++; // II
    }

    @Override
    public long getStatementCount() {
        return statementCount;
    }
}
