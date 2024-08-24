import java.io.File;
import java.io.FileNotFoundException;
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

        final int SIZE = 1000000; // Set the fixed size of the array
        final String DATASET = "src/datasets/medical_records_dataset_random.csv"; // Set the directory of the data set
        MedicalRecords[] medicalRecordsArray = new MedicalRecords[SIZE];

        int recordsCount = readMedicalRecordsFromCSV(DATASET, medicalRecordsArray);

        // Adjust your Console Cycle Buffer Size in IntelliJ to see the entirety of the console
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

            // Check if file exists. If file does not exist, remind user to pull from Git LFS
            if (scanner.hasNextLine()) {
                String firstLine = scanner.nextLine();

                if (firstLine.toLowerCase().contains("version")) { // Detects file is not pulled from Git LFS
                    System.out.println("File does not exist.\nFile needs to be pulled from Git LFS.");
                    return -1;
                }
            } else {
                System.out.println("The file is empty.");
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
    /**
     * Sorts the dataset via Insertion method
     * @author Cardenas
     * @param dataSize specific size of the data set (i.e., 10,000/50,000/200,000 etc.)
     * @param medicalRecordsArray Object used to store and manipulate the data set
     * Algorithm:
     * 1. Instantiate a new variable for the object to be sorted.
     * 2. Through a nested loop, make the outer loop such that it iterates from the 2nd element to the last.
     * 3. Within the inner loop, compare the element from the left to its adjacent right.
     * 4. If said condition was satisfied, left element is moved to the right.
     * 5. The variable used in process 1 will then be assigned with the value with its sorted placement.
     * */
    private static void insertionSort(MedicalRecords[] medicalRecordsArray, int dataSize) {
        for (int i = 1; i < dataSize; ++i) {
            MedicalRecords another = medicalRecordsArray[i];
            int j = i - 1;
            while (j >= 0 && medicalRecordsArray[j].compareTo(another) > 0) {
                medicalRecordsArray[j + 1] = medicalRecordsArray[j];
                j = j - 1;
            }
            medicalRecordsArray[j + 1] = another;
        }
    }
    private static void bubbleSort(MedicalRecords[] medicalRecordsArray) {
    }
}
