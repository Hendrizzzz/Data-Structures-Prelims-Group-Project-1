import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This {@code SortingAlgorithmBenchmark} class is the Main Class
 *
 * <p> contains:
 * <ul>
 *   <li>An instance of {@code SortingAlgorithmCounter}.</li>
 * </ul>
 *
 *
 *
 * @see SortingAlgorithmCounter
 */
public class SortingAlgorithmBenchmark {
    public static void main(String[] args) {
        // should have an object of SortingAlgorithmCounter
        // this is where to read csv/txt files and place it in an array type of <Object> (to be renamed).
        // Main Class, read files, call methods, and output results (if necessary)

        final int SIZE = 10000; // Set the fixed size of the array
        MedicalRecords[] medicalRecordsArray = new MedicalRecords[SIZE];

        int recordsCount = readMedicalRecordsFromCSV("src/datasets/medical_records_dataset.csv", medicalRecordsArray);

        // di ko alam why it's beginning at 4887
        // baka there's a limit to the console of intellij tapos everything else is getting overwritten
        for (int i = 0; i < recordsCount; i++) {
            System.out.println("("+ (i + 1) + ") " + medicalRecordsArray[i]);
        }
    }

    /**
     * Reads from the dataset and populates a fixed array
     * @author Hyowon
     * */
    public static int readMedicalRecordsFromCSV(String fileName, MedicalRecords[] medicalRecordsArray) {
        int count = 0;

        try (Scanner scanner = new Scanner(new File(fileName))) {
            // Skip the header line cause the header line are just titles and are not part of the dataset
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine() && count < medicalRecordsArray.length) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");

                // Handling the case where medications might have commas (e.g., "Atorvastatin, Metformin")
                String lastName = fields[0].trim();
                String firstName = fields[1].trim();
                String patientID = fields[2].trim();
                String gender = fields[3].trim();
                int contactInfo = Integer.parseInt(fields[4].trim().replace("+639", ""));

                // Combine all fields after contactInfo into medications
                StringBuilder medicationsBuilder = new StringBuilder();
                for (int i = 5; i < fields.length - 2; i++) {
                    medicationsBuilder.append(fields[i].trim());
                    if (i < fields.length - 3) {
                        medicationsBuilder.append(", ");
                    }
                }
                String medications = medicationsBuilder.toString();
                String reasonForVisit = fields[fields.length - 2].trim();
                String physician = fields[fields.length - 1].trim();

                MedicalRecords record = new MedicalRecords(lastName, firstName, patientID, gender, contactInfo, medications, reasonForVisit, physician);
                medicalRecordsArray[count] = record;
                count++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }

        return count;
    }

    private static void selectionSort(MedicalRecords[] medicalRecordsArray) {
    }
    private static void insertionSort(MedicalRecords[] medicalRecordsArray) {
    }
    private static void bubbleSort(MedicalRecords[] medicalRecordsArray) {
    }
}
