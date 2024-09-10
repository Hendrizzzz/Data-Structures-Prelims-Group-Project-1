import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigInteger;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A graphical user interface (GUI) for comparing the performance of different sorting algorithms.
 * <p>
 * This class provides a Swing-based GUI to allow users to select dataset sizes, start sorting algorithms,
 * and view the results in a table. It includes a progress bar, estimated time remaining, and elapsed time
 * labels to provide feedback during the sorting process.
 * </p>
 *
 * @author Hyowon Bernabe
 */
public class SortingAlgorithmGUI extends JFrame {
    private JComboBox<String> datasetSizeDropdown;
    private JButton startButton;
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private JProgressBar progressBar;
    private JLabel estimatedTimeLabel;
    private JLabel elapsedTimeLabel;

    private Timer timer;
    private long startTime;
    private long elapsedTime;

    private final String[] datasetSizes = {"10000", "50000", "200000", "500000", "1000000"};
    private final String[] cases = {"Best-Case (Ascending)", "Worst-Case (Descending)", "Average-Case (Random)"};

    /**
     * Constructs a new {@code SortingAlgorithmGUI} and sets up the user interface components.
     * Initializes the dropdown, button, table, progress bar, and labels.
     */
    public SortingAlgorithmGUI() {
        setTitle("Sorting Algorithm Performance Comparison");
        setSize(600, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the GUI components
        datasetSizeDropdown = new JComboBox<>(datasetSizes);
        startButton = new JButton("Start");
        tableModel = new DefaultTableModel(0, 0);
        resultsTable = new JTable(tableModel);
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);  // Initially hidden
        estimatedTimeLabel = new JLabel("Estimated time remaining: ");
        elapsedTimeLabel = new JLabel("Elapsed time: 00:00:00");

        // Layout setup
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Select Dataset Size:"));
        controlPanel.add(datasetSizeDropdown);
        controlPanel.add(startButton);

        // Add components to the frame
        add(controlPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        // Adding progress bar and estimated time label at the bottom
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(progressBar, BorderLayout.NORTH);
        bottomPanel.add(estimatedTimeLabel, BorderLayout.CENTER);
        bottomPanel.add(elapsedTimeLabel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action listener for the start button
        startButton.addActionListener(e -> {
            int selectedSize = Integer.parseInt((String) datasetSizeDropdown.getSelectedItem());
            runSortingAlgorithms(selectedSize);
        });
    }

    /**
     * Starts the sorting algorithms and updates the table with the results.
     * <p>
     * Disables the start button and shows a progress bar during the sorting process. Sets the estimated
     * time remaining based on the selected dataset size. Uses a {@code SwingWorker} to run sorting algorithms
     * in the background and update the UI once sorting is complete.
     * </p>
     *
     * @param datasetSize The size of the dataset to use for sorting.
     */
    private void runSortingAlgorithms(int datasetSize) {
        // Disable the start button and show the progress bar
        startButton.setEnabled(false);
        progressBar.setVisible(true);

        // Set estimated time based on dataset size
        String estimatedTime = getEstimatedTimeRemaining(datasetSize);
        estimatedTimeLabel.setText("Estimated time remaining: " + estimatedTime);

        // Find index based on selected dataset size
        final int choice = findChoiceIndex(datasetSize);

        // Initialize and start the timer
        startTime = System.currentTimeMillis();
        elapsedTime = 0;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateElapsedTime();
            }
        }, 0, 1000);

        // Use SwingWorker to perform the task in the background
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Run the sorting algorithms
                SortingAlgorithmBenchmark.readData(choice);
                SortingAlgorithmBenchmark.sortData();
                return null;
            }

            @Override
            protected void done() {
                try {
                    get(); // This will re-throw any exception from doInBackground()
                    // Update the table with results once sorting is complete
                    updateResultsTable();

                    // Stop the timer and update the label
                    timer.cancel();
                    elapsedTimeLabel.setText("Elapsed time: " + formatElapsedTime(elapsedTime));
                    progressBar.setVisible(false);
                    estimatedTimeLabel.setText("Estimated time remaining: Complete!");

                } catch (Exception e) {
                    // Handle exceptions that occurred during doInBackground
                    JOptionPane.showMessageDialog(SortingAlgorithmGUI.this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    // Re-enable the start button
                    startButton.setEnabled(true);
                }
            }
        };

        // Start the worker thread
        worker.execute();
    }

    /**
     * Finds the index of the dataset size in the predefined list of sizes.
     *
     * @param datasetSize The dataset size to find.
     * @return The index corresponding to the dataset size.
     */
    private int findChoiceIndex(int datasetSize) {
        for (int i = 0; i < SortingAlgorithmBenchmark.DATASET_SIZES.length; i++) {
            if (SortingAlgorithmBenchmark.DATASET_SIZES[i] == datasetSize) {
                return i + 1;
            }
        }
        return 1; // Default value if not found (you might want to handle this case more robustly)
    }

    /**
     * Returns a string representation of the estimated time remaining based on the dataset size.
     *
     * @param datasetSize The size of the dataset.
     * @return A string indicating the estimated time remaining.
     */
    private String getEstimatedTimeRemaining(int datasetSize) {
        switch (datasetSize) {
            case 10000:
                return "10 seconds";
            case 50000:
                return "5 minutes";
            case 200000:
                return "1 hour and 30 minutes";
            case 500000:
                return "12 hours and 30 minutes";
            case 1000000:
                return "32 hours";
            default:
                return "Unknown time";
        }
    }

    /**
     * Updates the results table with the sorting results from the benchmark.
     * <p>
     * Clears existing data and populates the table with the results of the Bubble Sort, Insertion Sort,
     * and Selection Sort algorithms for each dataset case.
     * </p>
     */
    private void updateResultsTable() {
        // Clear existing table data
        tableModel.setRowCount(0);

        // Set the column names (headers) after execution is complete
        tableModel.setColumnIdentifiers(new Object[]{"", "Bubble Sort", "Insertion Sort", "Selection Sort"});

        // Add rows to the table for each case
        for (int i = 0; i < 3; i++) {
            BigInteger bubbleSortResult = SortingAlgorithmBenchmark.BUBBLE_SORT_RESULTS[i];
            BigInteger insertionSortResult = SortingAlgorithmBenchmark.INSERTION_SORT_RESULTS[i];
            BigInteger selectionSortResult = SortingAlgorithmBenchmark.SELECTION_SORT_RESULTS[i];

            tableModel.addRow(new Object[]{cases[i], bubbleSortResult, insertionSortResult, selectionSortResult});
        }
    }

    /**
     * Updates the elapsed time label every second.
     * <p>
     * This method is called periodically by a {@code Timer} to refresh the elapsed time display.
     * </p>
     */
    private void updateElapsedTime() {
        elapsedTime = System.currentTimeMillis() - startTime;
        SwingUtilities.invokeLater(() -> elapsedTimeLabel.setText("Elapsed time: " + formatElapsedTime(elapsedTime)));
    }

    /**
     * Formats the elapsed time from milliseconds to a string in HH:MM:SS format.
     *
     * @param elapsedTimeMillis The elapsed time in milliseconds.
     * @return A string representing the elapsed time in HH:MM:SS format.
     */
    private String formatElapsedTime(long elapsedTimeMillis) {
        long seconds = (elapsedTimeMillis / 1000) % 60;
        long minutes = (elapsedTimeMillis / (1000 * 60)) % 60;
        long hours = (elapsedTimeMillis / (1000 * 60 * 60));
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Main method to launch the {@code SortingAlgorithmGUI}.
     * <p>
     * This method is the entry point for the application and initializes the GUI on the Event Dispatch Thread.
     * </p>
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SortingAlgorithmGUI gui = new SortingAlgorithmGUI();
            gui.setVisible(true);
        });
    }
}
