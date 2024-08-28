import java.math.BigInteger;

/**
 * The {@code SortingAlgorithm} interface defines method for sorting and counting statements for sorting algorithms
 */
public interface SortingAlgorithm {
    void sort(MedicalRecord[] medicalRecords);
    long getStatementCount();
}
