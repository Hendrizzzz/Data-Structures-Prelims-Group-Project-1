import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Timer;
import java.util.TimerTask;

public class GUI_Template extends JFrame {
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

    public GUI_Template() {
        setTitle("Sorting Algorithm Performance Comparison");
        setSize(800, 400);
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
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedSize = Integer.parseInt((String) datasetSizeDropdown.getSelectedItem());
                runSortingAlgorithms(selectedSize);
            }
        });
    }

    // Method to start sorting algorithms and update the table
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
                    JOptionPane.showMessageDialog(GUI_Template.this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    // Re-enable the start button
                    startButton.setEnabled(true);
                }
            }
        };

        // Start the worker thread
        worker.execute();
    }

    // Helper method to find the index of the dataset size
    private int findChoiceIndex(int datasetSize) {
        for (int i = 0; i < SortingAlgorithmBenchmark.DATASET_SIZES.length; i++) {
            if (SortingAlgorithmBenchmark.DATASET_SIZES[i] == datasetSize) {
                return i + 1;
            }
        }
        return 1; // Default value if not found (you might want to handle this case more robustly)
    }

    // Method to return estimated time based on dataset size
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

    // Method to update the table with sorting results
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

    // Update elapsed time every second
    private void updateElapsedTime() {
        elapsedTime = System.currentTimeMillis() - startTime;
        SwingUtilities.invokeLater(() -> elapsedTimeLabel.setText("Elapsed time: " + formatElapsedTime(elapsedTime)));
    }

    // Format elapsed time in HH:MM:SS
    private String formatElapsedTime(long elapsedTimeMillis) {
        long seconds = (elapsedTimeMillis / 1000) % 60;
        long minutes = (elapsedTimeMillis / (1000 * 60)) % 60;
        long hours = (elapsedTimeMillis / (1000 * 60 * 60));
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    // Main method to launch the GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI_Template gui = new GUI_Template();
            gui.setVisible(true);
        });
    }
}
