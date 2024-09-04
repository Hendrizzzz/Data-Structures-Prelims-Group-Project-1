import javax.swing.*;
import java.awt.*;

public class GUI_Template {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sorting Benchmark");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null);

            // Create components
            JButton button = new JButton("Start");
            String[] sizes = {"10000", "50000", "200000", "500000", "1000000"};
            JComboBox<String> dropdown = new JComboBox<>(sizes);
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setLineWrap(true);  // Ensure lines wrap in the text area
            textArea.setWrapStyleWord(true);  // Wrap words rather than breaking them

            // Set layout manager
            frame.setLayout(new BorderLayout());

            // Create a panel for the dropdown and button
            JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            controlPanel.add(dropdown);
            controlPanel.add(button);

            // Create a panel for the text area and place it in the center
            JPanel textAreaPanel = new JPanel(new BorderLayout());
            textAreaPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

            // Add components to the frame
            frame.add(controlPanel, BorderLayout.NORTH); // Add controlPanel at the top
            frame.add(textAreaPanel, BorderLayout.CENTER); // Add textAreaPanel in the center

            frame.setVisible(true);
        });
    }
}
