
import java.io.*;
import java.util.Scanner;

public class MainClassForReference2 implements Runnable{

    public static final String GREEN = "\033[32m";
    public static final String BOLD = "\033[1m";
    public static final String RESET = "\033[0m";

    // CSV file paths from repository root
    private final static String ASCENDING_ORDER_CSV = "src/datasets/medical_records_dataset_ascending.csv";
    private final static String DESCENDING_ORDER_CSV = "src/datasets/medical_records_dataset_descending.csv";
    private final static String RANDOM_ORDER_CSV = "src/datasets/medical_records_dataset_random.csv";

    private final static int[] DATASET_SIZES = {10000, 50000, 200000, 500000, 1000000}; // 10k, 50k, 200k, 500k, 1000k

    private static MedicalRecords[] medicalRecordsAO; // AO = Ascending Order
    private static MedicalRecords[] medicalRecordsDO; // DO = Descending Order
    private static MedicalRecords[] medicalRecordsRO; // RO = Randomly Order

    private static final long[] bubbleSortResults = new long[3];
    private static final long[] insertionSortResults = new long[3];
    private static final long[] selectionSortResults = new long[3];


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
            if (choice == 7){
                System.exit(0); // Terminate the program
            }

            MainClassForReference2 otherThread = new MainClassForReference2();
            Thread thread = new Thread(otherThread);
            thread.start();

            readData(choice);
            sortData();

            while (true) {
                if (!thread.isAlive()) {
                    displayResults("Results for dataset size (" + DATASET_SIZES[choice - 1] + ")");
                    break;
                }
            }
            pressEnter(kbdReader);
        }
    }



    private int readChoice(BufferedReader kbdReader) {
        int choice = 0;
        boolean isChoiceValid = false;
        try {
            while (!isChoiceValid) {
                showMenu();
                choice = Integer.parseInt(kbdReader.readLine());
                if (choice <= 0 || choice >= 7){
                    System.out.println("Invalid input. Choice not found. ");
                    continue;
                }
                isChoiceValid = true;
            }
        } catch (NumberFormatException e){
            System.out.println("Invalid input. Please enter the corresponding number of the choice you pick.");
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                6. 1 000 000
                7. Exit
                """);
    }



    private void readData(int choice) {
        int datasetSize = DATASET_SIZES[choice - 1];

        // Initialize medical records arrays
        medicalRecordsAO = new MedicalRecords[datasetSize];
        medicalRecordsDO = new MedicalRecords[datasetSize];
        medicalRecordsRO = new MedicalRecords[datasetSize];

        // Populate arrays
        readMedicalRecordsFromCSV(ASCENDING_ORDER_CSV, medicalRecordsAO);
        readMedicalRecordsFromCSV(DESCENDING_ORDER_CSV, medicalRecordsDO);
        readMedicalRecordsFromCSV(RANDOM_ORDER_CSV, medicalRecordsRO);
    }


    /**
     * Reads from the dataset and populates a fixed array
     * @author Hyowon
     * */
    public static void readMedicalRecordsFromCSV(String fileName, MedicalRecords[] medicalRecordsArray) {
        int count = 0;

        try (Scanner scanner = new Scanner(new File(fileName))) {

            // Check if file exists. If file does not exist, remind user to pull from Git LFS
            if (scanner.hasNextLine()) {
                String firstLine = scanner.nextLine();

                if (firstLine.toLowerCase().contains("version")) { // Detects file is not pulled from Git LFS
                    System.out.println("File does not exist.\nFile needs to be pulled from Git LFS.");
                    return;
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
    }

    private void sortData() {
        SortingAlgorithmCounter counter = new SortingAlgorithmCounter();

        // A for loop can be used, but the 3 arrays of MedicalRecords should be placed inside an array
        bubbleSortResults[0] = counter.getBubbleSortStatementCount(medicalRecordsAO);
        insertionSortResults[0] = counter.getInsertionSortStatementCount(medicalRecordsAO);
        selectionSortResults[0] = counter.getSelectionSortStatementCount(medicalRecordsAO);

        bubbleSortResults[1] = counter.getBubbleSortStatementCount(medicalRecordsDO);
        insertionSortResults[1] = counter.getInsertionSortStatementCount(medicalRecordsDO);
        selectionSortResults[1] = counter.getSelectionSortStatementCount(medicalRecordsDO);

        bubbleSortResults[2] = counter.getBubbleSortStatementCount(medicalRecordsRO);
        insertionSortResults[2] = counter.getInsertionSortStatementCount(medicalRecordsRO);
        selectionSortResults[2] = counter.getSelectionSortStatementCount(medicalRecordsRO);
    }


    private void displayResults(String message) {
        System.out.println("Sorting and statement counting completed successfully." + RESET + "\n\n");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(BOLD + message);
        System.out.printf("%-16s%-17s%-17s%-17s%n", "", "Bubble Sort", "Insertion Sort", "Selection Sort");
        System.out.printf("%-16s%-17d%-17d%-17d%n", "Best-Case", bubbleSortResults[0], insertionSortResults[0], selectionSortResults[0]);
        System.out.printf("%-16s%-17d%-17d%-17d%n", "Worst-Case", bubbleSortResults[1], insertionSortResults[1], selectionSortResults[1]);
        System.out.printf("%-16s%-17d%-17d%-17d%n%n%n" + RESET, "Average-Case", bubbleSortResults[2], insertionSortResults[2], selectionSortResults[2]);
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
        try {
            System.out.println(GREEN + BOLD + "Initializing file read operation...");
            Thread.sleep(3000);
            System.out.println("Reading data from the file...");
            Thread.sleep(3000);
            System.out.println("Executing sorting algorithms and tracking statement counts...");
            Thread.sleep(3000);
            System.out.println("Sorting in progress, please wait...");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
