import java.io.*;
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
public class SortingAlgorithmBenchmark implements Runnable {

    // CSV file paths from repo root
    final static String ASCENDING_ORDER_CSV = "src/datasets/medical_records_dataset_ascending.csv";
    final static String DESCENDING_ORDER_CSV = "src/datasets/medical_records_dataset_descending.csv";
    final static String RANDOM_ORDER_CSV = "src/datasets/medical_records_dataset_random.csv";

    // Arrays of MedicalRecords; established one for each since size is known
    static MedicalRecords[] dataset_10k_Ascending = new MedicalRecords[10000];
    static MedicalRecords[] dataset_10k_Descending = new MedicalRecords[10000];
    static MedicalRecords[] dataset_10k_Random = new MedicalRecords[10000];
    static MedicalRecords[] dataset_50k_Ascending = new MedicalRecords[50000];
    static MedicalRecords[] dataset_50k_Descending = new MedicalRecords[50000];
    static MedicalRecords[] dataset_50k_Random = new MedicalRecords[50000];
    static MedicalRecords[] dataset_200k_Ascending = new MedicalRecords[100000];
    static MedicalRecords[] dataset_200k_Descending = new MedicalRecords[100000];
    static MedicalRecords[] dataset_200k_Random = new MedicalRecords[100000];
    static MedicalRecords[] dataset_500k_Ascending = new MedicalRecords[500000];
    static MedicalRecords[] dataset_500k_Descending = new MedicalRecords[500000];
    static MedicalRecords[] dataset_500k_Random = new MedicalRecords[500000];
    static MedicalRecords[] dataset_1M_Ascending = new MedicalRecords[1000000];
    static MedicalRecords[] dataset_1M_Descending = new MedicalRecords[1000000];
    static MedicalRecords[] dataset_1M_Random = new MedicalRecords[1000000];

    static MedicalRecords[][] arrays = {
            dataset_10k_Ascending, dataset_10k_Descending, dataset_10k_Random,
            dataset_50k_Ascending, dataset_50k_Descending, dataset_50k_Random,
            dataset_200k_Ascending, dataset_200k_Descending, dataset_200k_Random,
            dataset_500k_Ascending, dataset_500k_Descending, dataset_500k_Random,
            dataset_1M_Ascending, dataset_1M_Descending, dataset_1M_Random
    };

    // Object of SortingAlgorithmCounter
    static SortingAlgorithmCounter counter = new SortingAlgorithmCounter();

    // arrays to store the statement counts of each Sorting Algorithm on different cases and sizes
    static int[] bubbleSortResults = new int[15];
    static int[] insertionSortResults = new int[15];
    static int[] selectionSortResults = new int[15];

    // Reader of keyboard input
    final static BufferedReader kbdReader = new BufferedReader(new InputStreamReader(System.in));


    public static void main(String[] args) {
        SortingAlgorithmBenchmark myProgram;

        try {
            myProgram = new SortingAlgorithmBenchmark();
            myProgram.start();
        } catch (Exception e){
            e.printStackTrace();
        }

//        int recordsCount = readMedicalRecordsFromCSV("src/datasets/medical_records_dataset.csv", medicalRecordsArray);

        // di ko alam why it's beginning at 4887
        // baka there's a limit to the console of intellij tapos everything else is getting overwritten
//        for (int i = 0; i < recordsCount; i++) {
//            System.out.println("("+ (i + 1) + ") " + medicalRecordsArray[i]);
//        }
    }

    /**
     * Starts the benchmarking process by prompting to press 'enter' to begin.
     */
    private void start() {
        // Press Enter to Start the Program
        while (true){
            System.out.println("Press 'Enter' to start BENCHMARKING TESTS: ");
            try {
                if (kbdReader.readLine().isBlank()){
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        populateArrays();
        sortArrays();
    }

    /**
     * Sorts the populated arrays and records the number of statements executed by each sorting algorithm.
     */
    private void sortArrays() {
        for (int i = 0; i < 15; i++){
            bubbleSortResults[i] = counter.getBubbleSortStatementCount(arrays[i]);
            insertionSortResults[i] = counter.getInsertionSortStatementCount(arrays[i]);
            selectionSortResults[i] = counter.getSelectionSortStatementCount(arrays[i]);
        }
    }

    /**
     * Populates the arrays with data from CSV files based on specified sizes.
     *
     * @see #ASCENDING_ORDER_CSV
     * @see #DESCENDING_ORDER_CSV
     * @see #RANDOM_ORDER_CSV
     */
    private void populateArrays() {
        int[] arraySizes = {10000, 50000, 200000, 500000, 1000000};
        for (int i = 0; i < 5; i++) {
            populate(arraySizes[i], ASCENDING_ORDER_CSV, arrays[3 * i]);
            populate(arraySizes[i], DESCENDING_ORDER_CSV, arrays[3 * i + 1]);
            populate(arraySizes[i], RANDOM_ORDER_CSV, arrays[3 * i + 2]);
        }
    }

    /**
     * Reads from the dataset and populates a fixed array
     * @author Hyowon
     *
     * */
    private void populate(int arraySize, String fileName, MedicalRecords[] arrayToFill) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            // Skip the header line cause the header line are just titles and are not part of the dataset
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            int count = 0;

            while (scanner.hasNextLine() && count < arraySize) {
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
                arrayToFill[count] = record;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }


//    private static int readPrompt() {
//        do {
//            try {
//                System.out.print("""
//                        Choose the size of the DATASET to sort:
//                        1. 10 000
//                        2. 50 000
//                        3. 200 000
//                        4. 500 000
//                        5. 1 000 000
//                        """);
//                int choice =
//                if ()
//            } catch (NumberFormatException e){
//                System.out.println("Invalid Input. Please enter an integer. ");
//            }
//        } while (true);
//    }


    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println();
            System.out.println("");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
