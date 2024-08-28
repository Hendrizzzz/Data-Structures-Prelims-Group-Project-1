import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainClassForReference2 implements Runnable{

    public static final String GREEN = "\033[32m";
    public static final String BOLD = "\033[1m";
    public static final String RESET = "\033[0m";

    // CSV file paths from repository root
    private final static String ASCENDING_ORDER_CSV = "src/datasets/medical_records_dataset_ascending.csv";
    private final static String DESCENDING_ORDER_CSV = "src/datasets/medical_records_dataset_descending.csv";
    private final static String RANDOM_ORDER_CSV = "src/datasets/medical_records_dataset_random.csv";

    private final static int[] DATASET_SIZES = {10000, 50000, 200000, 500000, 1000000}; // 10k, 50k, 200k, 500k, 1000k

    private static MedicalRecord[] medicalRecordsAO; // AO = Ascending Order
    private static MedicalRecord[] medicalRecordsDO; // DO = Descending Order
    private static MedicalRecord[] medicalRecordsRO; // RO = Randomly Order

    private static final long[] BUBBLE_SORT_RESULTS = new long[3];
    private static final long[] INSERTION_SORT_RESULTS = new long[3];
    private static final long[] SELECTION_SORT_RESULTS = new long[3];



    public static void main(String[] args) {
        MainClassForReference2 myProgram;
        try{
            myProgram = new MainClassForReference2();
            myProgram.runProgram();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void runProgram() {
        BufferedReader kbdReader = new BufferedReader(new InputStreamReader(System.in));
        pressEnter(kbdReader);
        while (true){
            int choice = readChoice(kbdReader); // Read choice from the menu
            if (choice == 6){
                System.exit(0); // Terminate the program
            }

            long startTime = System.currentTimeMillis(); // to be removed

            readData(choice);
            sortData();

            displayResults("Results for dataset size (" + DATASET_SIZES[choice - 1] + ")");

            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime) / 1000; // in seconds
            System.out.println("Processing time: " + duration + " seconds"); // to be removed
            pressEnter(kbdReader);
        }
    }



    private int readChoice(BufferedReader kbdReader) {
        int choice = 0;
        boolean isChoiceValid = false;
        while (!isChoiceValid) {
            try {
                showMenu();
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

    private void showMenu() {
        System.out.println("""
                Please select a dataset size for comparing the performance of the three sorting algorithms
                (Bubble Sort, Insertion Sort, and Selection Sort):
                1. 10 000
                2. 50 000
                3. 200 000
                4. 500 000
                5. 1 000 000
                6. Exit
                """);
    }



    private void readData(int choice) {
        System.out.println(GREEN + BOLD + "Initializing file read operation...");
        int datasetSize = DATASET_SIZES[choice - 1];

        // Initialize medical records arrays
        medicalRecordsAO = new MedicalRecord[datasetSize];
        medicalRecordsDO = new MedicalRecord[datasetSize];
        medicalRecordsRO = new MedicalRecord[datasetSize];

        // Create threads for parallel sorting
        System.out.println(GREEN + BOLD + "Reading data from the file...");
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
    public static void readMedicalRecordsFromCSV(String fileName, MedicalRecord[] medicalRecordsArray) {
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

                MedicalRecord record = new MedicalRecord(lastName, firstName, patientID, gender, contactInfo, medications, reasonForVisit, physician);
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
     * Algorithm
     * 1. Uses the Callable to define tasks to execute which is sort and get statementCount (the () -> { })
     * 2. Submit tasks to Executor which will run in a separate thread
     * 3. Use the Future to retrieve and store results.
     */
    private void sortData() {
        System.out.println("Executing sorting algorithms concurrently and tracking statement counts...");
        ExecutorService executor = Executors.newFixedThreadPool(9);
        Future<Long>[] futures = new Future[9];

        // index = 0 is AO, index = 1 is DO, index = 2 is RO
        BubbleSort[] bubbleSorters = {new BubbleSort(), new BubbleSort(), new BubbleSort()};
        InsertionSort[] insertionSorters = {new InsertionSort(), new InsertionSort(), new InsertionSort()};
        SelectionSort[] selectionSorters = {new SelectionSort(), new SelectionSort(), new SelectionSort()};

        MedicalRecord[][] records = {medicalRecordsAO, medicalRecordsDO, medicalRecordsRO};

        System.out.println("Sorting in progress, please wait...");

        // Loop through the sorters and records arrays
        for (int i = 0; i < 3; i++) {
            final int index = i;
            futures[i] = executor.submit(() -> {
                bubbleSorters[index].sort(records[index]);
                return bubbleSorters[index].getStatementCount();
            });

            futures[i + 3] = executor.submit(() -> {
                insertionSorters[index].sort(records[index]);
                return insertionSorters[index].getStatementCount();
            });

            futures[i + 6] = executor.submit(() -> {
                selectionSorters[index].sort(records[index]);
                return selectionSorters[index].getStatementCount();
            });
        }

        retrieveResults(futures, executor);
    }

    private void retrieveResults(Future<Long>[] futures, ExecutorService executor) {
        // Retrieve and assign results
        try {
            for (int i = 0; i < 3; i++) {
                BUBBLE_SORT_RESULTS[i] = futures[i].get();
                INSERTION_SORT_RESULTS[i] = futures[i + 3].get();
                SELECTION_SORT_RESULTS[i] = futures[i + 6].get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }


    private void displayResults(String message) {
        System.out.println("Sorting and statement counting completed successfully." + RESET);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(BOLD + "\n\n" + message);
        System.out.printf("%-16s%-17s%-17s%-17s%n", "", "Bubble Sort", "Insertion Sort", "Selection Sort");
        System.out.printf("%-16s%-17d%-17d%-17d%n", "Best-Case", BUBBLE_SORT_RESULTS[0], INSERTION_SORT_RESULTS[0], SELECTION_SORT_RESULTS[0]);
        System.out.printf("%-16s%-17d%-17d%-17d%n", "Worst-Case", BUBBLE_SORT_RESULTS[1], INSERTION_SORT_RESULTS[1], SELECTION_SORT_RESULTS[1]);
        System.out.printf("%-16s%-17d%-17d%-17d%n%n%n" + RESET, "Average-Case", BUBBLE_SORT_RESULTS[2], INSERTION_SORT_RESULTS[2], SELECTION_SORT_RESULTS[2]);
    }


    private void pressEnter(BufferedReader kbdReader) {
        // Press Enter to Start the Program
        System.out.println("Press 'Enter' to show the Menu.... ");
        try {
            kbdReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

    }
}
