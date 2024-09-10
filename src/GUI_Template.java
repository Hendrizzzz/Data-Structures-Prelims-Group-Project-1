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

        // Layout setup
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Select Dataset Size:"));
        controlPanel.add(datasetSizeDropdown);
        controlPanel.add(startButton);

        // Add components to the frame
        add(controlPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultsTable), BorderLayout.CENTER);

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
        // Find index based on selected dataset size
        int choice = 0;
        for (int i = 0; i < SortingAlgorithmBenchmark.DATASET_SIZES.length; i++) {
            if (SortingAlgorithmBenchmark.DATASET_SIZES[i] == datasetSize) {
                choice = i + 1;
                break;
            }
        }

        // Run the program for the selected dataset size
        SortingAlgorithmBenchmark.readData(choice);
        SortingAlgorithmBenchmark.sortData();

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
