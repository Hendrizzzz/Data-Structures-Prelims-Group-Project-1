import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.*;

/**
 * A class to benchmark various sorting algorithms on medical records datasets.
 * <p>
 * This class provides functionality to read datasets from CSV files, sort the datasets using different sorting algorithms,
 * and measure the performance of these algorithms.
 * </p>
 * <p>
 * It supports three different datasets: ascending order, descending order, and random order. The class benchmarks Bubble Sort,
 * Insertion Sort, and Selection Sort algorithms, and records the number of statements executed by each algorithm.
 * </p>
 * <p>
 * The class uses multithreading to read the datasets concurrently and utilizes an {@link ExecutorService} to perform sorting operations in parallel.
 * </p>
 *
 * @see MedicalRecords
 * @see SortingAlgorithmCounter
 */
public class SortingAlgorithmBenchmark {

    // CSV file paths from repository root
    private final static String ASCENDING_ORDER_CSV = "src/datasets/medical_records_dataset_ascending.csv";
    private final static String DESCENDING_ORDER_CSV = "src/datasets/medical_records_dataset_descending.csv";
    private final static String RANDOM_ORDER_CSV = "src/datasets/medical_records_dataset_random.csv";

    private static MedicalRecords[] medicalRecordsAO; // AO = Ascending Order
    private static MedicalRecords[] medicalRecordsDO; // DO = Descending Order
    private static MedicalRecords[] medicalRecordsRO; // RO = Randomly Order
    protected final static int[] DATASET_SIZES = {10000, 50000, 200000, 500000, 1000000}; // 10k, 50k, 200k, 500k, 1000k

    protected static final BigInteger[] BUBBLE_SORT_RESULTS = new BigInteger[3];
    protected static final BigInteger[] INSERTION_SORT_RESULTS = new BigInteger[3];
    protected static final BigInteger[] SELECTION_SORT_RESULTS = new BigInteger[3];

    /**
     * Reads data from CSV files and populates arrays of {@link MedicalRecords} objects.
     * <p>
     * This method initializes the arrays for medical records and starts separate threads to read data from CSV files concurrently.
     * After reading, it waits for all threads to finish before returning.
     * </p>
     *
     * @param choice an integer representing the size of the dataset to be used.
     *               It corresponds to the index in the {@code DATASET_SIZES} array (1-based index).
     */
    protected static void readData(int choice) {
        int datasetSize = DATASET_SIZES[choice - 1];

        // Initialize medical records arrays
        medicalRecordsAO = new MedicalRecords[datasetSize];
        medicalRecordsDO = new MedicalRecords[datasetSize];
        medicalRecordsRO = new MedicalRecords[datasetSize];

        // Create threads for parallel sorting
        Thread readAscendingDataThread1 = new Thread(() -> readMedicalRecordsFromCSV(ASCENDING_ORDER_CSV, medicalRecordsAO));
        Thread readAscendingDataThread2 = new Thread(() -> readMedicalRecordsFromCSV(DESCENDING_ORDER_CSV, medicalRecordsDO));
        Thread readAscendingDataThread3 = new Thread(() -> readMedicalRecordsFromCSV(RANDOM_ORDER_CSV, medicalRecordsRO));

        // Read data files at the same time in different threads
        readAscendingDataThread1.start();
        readAscendingDataThread2.start();
        readAscendingDataThread3.start();

        try {
            readAscendingDataThread1.join();
            readAscendingDataThread2.join();
            readAscendingDataThread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads medical records from a CSV file and populates the provided array.
     * <p>
     * This method processes the CSV file line by line, parsing the fields and creating {@link MedicalRecords} objects.
     * It handles cases where medications may contain commas and provides appropriate error messages if the file is not found
     * or if there is an issue reading the file.
     * </p>
     *
     * @param fileName the path to the CSV file.
     * @param medicalRecordsArray the array to populate with {@link MedicalRecords} objects.
     */
    public static void readMedicalRecordsFromCSV(String fileName, MedicalRecords[] medicalRecordsArray) {
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String firstLine = reader.readLine();

            if (firstLine != null) {
                if (firstLine.toLowerCase().contains("version")) { // Detects file is not pulled from Git LFS
                    System.out.println("File does not exist.\nFile needs to be pulled from Git LFS.");
                    return;
                }
            } else {
                System.out.println("The file is empty.");
                return;
            }

            String line;
            while ((line = reader.readLine()) != null && count < medicalRecordsArray.length) {
                String[] fields = line.split(",");

                // Handling the case where medications might have commas (e.g., "Atorvastatin, Metformin")
                String lastName = fields[0].trim();
                String firstName = fields[1].trim();
                String patientID = fields[2].trim();
                String gender = fields[3].trim();
                int contactInfo = Integer.parseInt(fields[4].trim().replace("+639", ""));

                // Combine all fields after contactInfo into medications
                String medications = String.join(", ", Arrays.copyOfRange(fields, 5, fields.length - 2));
                String reasonForVisit = fields[fields.length - 2].trim();
                String physician = fields[fields.length - 1].trim();

                MedicalRecords record = new MedicalRecords(lastName, firstName, patientID, gender, contactInfo, medications, reasonForVisit, physician);
                medicalRecordsArray[count] = record;
                count++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + fileName);
        }
    }

    /**
     * Sorts the medical records data using various sorting algorithms and measures the performance.
     * <p>
     * This method creates tasks for sorting the data using Bubble Sort, Insertion Sort, and Selection Sort algorithms.
     * It submits these tasks to an {@link ExecutorService} to be executed in parallel and collects the results.
     * The number of statements executed by each sorting algorithm is recorded in the static arrays {@code BUBBLE_SORT_RESULTS},
     * {@code INSERTION_SORT_RESULTS}, and {@code SELECTION_SORT_RESULTS}.
     * </p>
     */
    protected static void sortData() {
        SortingAlgorithmCounter counter = new SortingAlgorithmCounter();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // Initialize tasks
        Callable<BigInteger>[] bubbleSortTasks = new Callable[3];
        Callable<BigInteger>[] insertionSortTasks = new Callable[3];
        Callable<BigInteger>[] selectionSortTasks = new Callable[3];

        bubbleSortTasks[0] = () -> counter.getBubbleSortStatementCount(medicalRecordsAO);
        bubbleSortTasks[1] = () -> counter.getBubbleSortStatementCount(medicalRecordsDO);
        bubbleSortTasks[2] = () -> counter.getBubbleSortStatementCount(medicalRecordsRO);

        insertionSortTasks[0] = () -> counter.getInsertionSortStatementCount(medicalRecordsAO);
        insertionSortTasks[1] = () -> counter.getInsertionSortStatementCount(medicalRecordsDO);
        insertionSortTasks[2] = () -> counter.getInsertionSortStatementCount(medicalRecordsRO);

        selectionSortTasks[0] = () -> counter.getSelectionSortStatementCount(medicalRecordsAO);
        selectionSortTasks[1] = () -> counter.getSelectionSortStatementCount(medicalRecordsDO);
        selectionSortTasks[2] = () -> counter.getSelectionSortStatementCount(medicalRecordsRO);

        // Submit tasks and store Future objects
        Future<BigInteger>[] bubbleSortFutures = new Future[3];
        Future<BigInteger>[] insertionSortFutures = new Future[3];
        Future<BigInteger>[] selectionSortFutures = new Future[3];

        for (int i = 0; i < 3; i++) {
            bubbleSortFutures[i] = executorService.submit(bubbleSortTasks[i]);
            insertionSortFutures[i] = executorService.submit(insertionSortTasks[i]);
            selectionSortFutures[i] = executorService.submit(selectionSortTasks[i]);
        }

        executorService.shutdown();

        try {
            // Wait for all tasks to complete and collect results
            for (int i = 0; i < 3; i++) {
                BUBBLE_SORT_RESULTS[i] = bubbleSortFutures[i].get();
                INSERTION_SORT_RESULTS[i] = insertionSortFutures[i].get();
                SELECTION_SORT_RESULTS[i] = selectionSortFutures[i].get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
