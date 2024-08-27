
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
public class MainClassForReference1 {

    // CSV file paths from repo root
    final static String ASCENDING_ORDER_CSV = "src/datasets/medical_records_dataset_ascending.csv";
    final static String DESCENDING_ORDER_CSV = "src/datasets/medical_records_dataset_descending.csv";
    final static String RANDOM_ORDER_CSV = "src/datasets/medical_records_dataset_random.csv";

    private static final int SIZE_10K = 10000;
    private static final int SIZE_50K = 50000;
    private static final int SIZE_200K = 200000;
    private static final int SIZE_500K = 500000;
    private static final int SIZE_1M = 1000000;

    static MedicalRecords[][] arrays = new MedicalRecords[][] {
            new MedicalRecords[SIZE_10K], new MedicalRecords[SIZE_10K], new MedicalRecords[SIZE_10K],
            new MedicalRecords[SIZE_50K], new MedicalRecords[SIZE_50K], new MedicalRecords[SIZE_50K],
            new MedicalRecords[SIZE_200K], new MedicalRecords[SIZE_200K], new MedicalRecords[SIZE_200K],
            new MedicalRecords[SIZE_500K], new MedicalRecords[SIZE_500K], new MedicalRecords[SIZE_500K],
            new MedicalRecords[SIZE_1M], new MedicalRecords[SIZE_1M], new MedicalRecords[SIZE_1M]
    };


    // Object of SortingAlgorithmCounter
    static SortingAlgorithmCounter counter = new SortingAlgorithmCounter();


    // arrays to store the statement counts of each Sorting Algorithm on different cases and sizes
    static long[] bubbleSortResults = new long[15];
    static long[] insertionSortResults = new long[15];
    static long[] selectionSortResults = new long[15];

    // Reader of keyboard input
    final static BufferedReader kbdReader = new BufferedReader(new InputStreamReader(System.in));
    static MainClassForReference1 myProgram;



    public static void main(String[] args) {
        try {
            myProgram = new MainClassForReference1();
            myProgram.play();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Starts the benchmarking process by prompting to press 'enter' to begin.
     */
    private void play() {
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
        displayResults();
    }


    /**
     * Populates the arrays with data from CSV files based on specified sizes.
     *
     * @see #ASCENDING_ORDER_CSV
     * @see #DESCENDING_ORDER_CSV
     * @see #RANDOM_ORDER_CSV
     */
    private void populateArrays() {
        int[] arraySizes = {10000, 50000};
        for (int i = 0; i < 2; i++) {
            populate(arraySizes[i], ASCENDING_ORDER_CSV, arrays[3 * i]);
            populate(arraySizes[i], DESCENDING_ORDER_CSV, arrays[(3 * i) + 1]);
            populate(arraySizes[i], RANDOM_ORDER_CSV, arrays[(3 * i) + 2]);
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
                count++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }



    /**
     * Sorts the populated arrays and records the number of statements executed by each sorting algorithm.
     */
    private void sortArrays() {
        for (int i = 0; i < 6; i++){
            bubbleSortResults[i] = counter.getBubbleSortStatementCount(arrays[i]);
            insertionSortResults[i] = counter.getInsertionSortStatementCount(arrays[i]);
            selectionSortResults[i] = counter.getSelectionSortStatementCount(arrays[i]);
        }
    }


    private void displayResults() {
        display("Results for 10k Dataset", 0);
        display("Results for 50k Dataset", 1);

    }

    private void display(String message, int index) {
        System.out.println(message);
        System.out.printf("%-16s%-17s%-17s%-17s%n", "", "Bubble Sort", "Insertion Sort", "Selection Sort");
        System.out.printf("%-16s%-17d%-17d%-17d%n", "Best-Case", bubbleSortResults[index * 3], insertionSortResults[index * 3], selectionSortResults[index]);
        System.out.printf("%-16s%-17d%-17d%-17d%n", "Worst-Case", bubbleSortResults[(index * 3) + 1], insertionSortResults[(index * 3) + 1], selectionSortResults[(index * 3) + 1]);
        System.out.printf("%-16s%-17d%-17d%-17d%n%n%n", "Average-Case", bubbleSortResults[(index * 3) + 2], insertionSortResults[(index * 3) + 2], selectionSortResults[(index * 3) + 2]);
    }

}
