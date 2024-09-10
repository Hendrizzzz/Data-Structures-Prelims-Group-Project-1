import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

public class GUI_Template extends JFrame {
    private JComboBox<String> datasetSizeDropdown;
    private JButton startButton;
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private JProgressBar progressBar;
    private JLabel estimatedTimeLabel;

    private final String[] datasetSizes = {"10000", "50000", "200000", "500000", "1000000"};
    private final String[] algorithms = {"Bubble Sort", "Insertion Sort", "Selection Sort"};
    private final String[] cases = {"Best-Case (Ascending)", "Worst-Case (Descending)", "Average-Case (Random)"};

    public GUI_Template() {
        setTitle("Sorting Algorithm Performance Comparison");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the GUI components
        datasetSizeDropdown = new JComboBox<>(datasetSizes);
        startButton = new JButton("Start");
        tableModel = new DefaultTableModel(new Object[]{"", "Bubble Sort", "Insertion Sort", "Selection Sort"}, 0);
        resultsTable = new JTable(tableModel);
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);  // Initially hidden
        estimatedTimeLabel = new JLabel("Estimated time remaining: Program not started");

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
        bottomPanel.add(estimatedTimeLabel, BorderLayout.SOUTH);
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

                    // Hide the progress bar and reset label
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

        // Add rows to the table for each case
        for (int i = 0; i < 3; i++) {
            BigInteger bubbleSortResult = SortingAlgorithmBenchmark.BUBBLE_SORT_RESULTS[i];
            BigInteger insertionSortResult = SortingAlgorithmBenchmark.INSERTION_SORT_RESULTS[i];
            BigInteger selectionSortResult = SortingAlgorithmBenchmark.SELECTION_SORT_RESULTS[i];

            tableModel.addRow(new Object[]{cases[i], bubbleSortResult, insertionSortResult, selectionSortResult});
        }
    }

    // Main method to launch the GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI_Template gui = new GUI_Template();
            gui.setVisible(true);
        });
    }
}
