import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_Template implements ActionListener {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        JButton button = new JButton("Start");
        button.setBounds(100,100, 100, 100  );
        //button.setFocusable(false);
        button.setVerticalAlignment(JButton.BOTTOM);
        button.setVerticalAlignment(JButton.CENTER);


        JLabel label = new JLabel();
        label.setText("SORT");

        JComboBox dropdown = new JComboBox();
        JPanel panel = new JPanel();

        frame.add(button);
        //frame.add(label);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(1000,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.getContentPane();
        frame.setLayout(new GridBagLayout());
        frame.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
