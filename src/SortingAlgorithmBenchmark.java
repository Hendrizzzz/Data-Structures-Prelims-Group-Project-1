import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.*;

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

    private void runProgram() {
        BufferedReader kbdReader = new BufferedReader(new InputStreamReader(System.in));
        //pressEnter(kbdReader);
        while (true){
            int choice = readChoice(kbdReader); // Read choice from the menu
            if (choice == 6){
                System.exit(0); // Terminate the program
            }

            long startTime = System.currentTimeMillis(); // to be removed

            readData(choice);
            sortData();

            //displayResults("Results for dataset size (" + DATASET_SIZES[choice - 1] + ")");

            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime) / 1000; // in seconds
            System.out.println("Processing time: " + duration + " seconds"); // to be removed
            //pressEnter(kbdReader);
        }
    }

    protected int readChoice(BufferedReader kbdReader) {
        int choice = 0;
        boolean isChoiceValid = false;
        while (!isChoiceValid) {
            try {
                choice = Integer.parseInt(kbdReader.readLine());
                if (choice <= 0 || choice >= 7) {
                    System.out.println("Invalid input. Choice not found. ");
                    continue;
                }
                isChoiceValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter the corresponding number of the choice you pick.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return choice;
    }

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
     * Reads from the dataset and populates a fixed array
     * @author Hyowon
     *
     * */
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

    protected static void sortData() {
        System.out.println("Executing sorting algorithms and tracking statement counts...");
        SortingAlgorithmCounter counter = new SortingAlgorithmCounter();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // Initialize tasks
        Callable<BigInteger>[] bubbleSortTasks = new Callable[3];
        Callable<BigInteger>[] insertionSortTasks = new Callable[3];
        Callable<BigInteger>[] selectionSortTasks = new Callable[3];

        System.out.println("Sorting in progress, please wait...");
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
            System.out.println("All sorting tasks are completed.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
