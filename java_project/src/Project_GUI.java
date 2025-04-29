import javax.swing.*;
import java.awt.*;

public class Project_GUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Account Overdrawn Predictor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 450);
        frame.setLayout(new GridLayout(9, 2));

        String[] options1 = {"business", "personal"};
        String[] options2 = {"yes", "no"};
        String[] options3 = {"employed", "unemployed"};

        JComboBox<String> accountTypeBox = new JComboBox<>(options1);
        JComboBox<String> creditCardBox = new JComboBox<>(options2);
        JComboBox<String> loanBox = new JComboBox<>(options2);
        JComboBox<String> employmentBox = new JComboBox<>(options3);
        JComboBox<String> labelBox = new JComboBox<>(options2);

        JButton trainButton = new JButton("Train");
        JButton predictButton = new JButton("Predict");
        JButton addRowButton = new JButton("Add Row");
        JButton evaluateButton = new JButton("Evaluate Accuracy");
        JLabel resultLabel = new JLabel("Prediction will appear here");

        Project_Control controller = new Project_Control();

        trainButton.addActionListener(e -> {
            try {
                controller.train();
                JOptionPane.showMessageDialog(frame, "Training complete!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error during training: " + ex.getMessage());
            }
        });

        predictButton.addActionListener(e -> {
            String accountType = (String) accountTypeBox.getSelectedItem();
            String creditCard = (String) creditCardBox.getSelectedItem();
            String loan = (String) loanBox.getSelectedItem();
            String employment = (String) employmentBox.getSelectedItem();

            String prediction = controller.predict(accountType, creditCard, loan, employment);
            resultLabel.setText("Prediction: Account will be overdrawn = " + prediction);
        });

        addRowButton.addActionListener(e -> {
            String accountType = (String) accountTypeBox.getSelectedItem();
            String creditCard = (String) creditCardBox.getSelectedItem();
            String loan = (String) loanBox.getSelectedItem();
            String employment = (String) employmentBox.getSelectedItem();
            String label = (String) labelBox.getSelectedItem();

            try {
                controller.addRow(accountType, creditCard, loan, employment, label);
                JOptionPane.showMessageDialog(frame, "Row added successfully! Please click 'Train' again.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error adding row: " + ex.getMessage());
            }
        });

        evaluateButton.addActionListener(e -> {
            try {
                double accuracy = controller.calculateAccuracy();
                JOptionPane.showMessageDialog(frame, String.format("Accuracy: %.2f%%", accuracy));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error calculating accuracy: " + ex.getMessage());
            }
        });

        frame.add(new JLabel("Account Type:"));
        frame.add(accountTypeBox);
        frame.add(new JLabel("Has Credit Card:"));
        frame.add(creditCardBox);
        frame.add(new JLabel("Has Loan:"));
        frame.add(loanBox);
        frame.add(new JLabel("Employment Status:"));
        frame.add(employmentBox);
        frame.add(new JLabel("Known Label (yes/no):"));
        frame.add(labelBox);
        frame.add(trainButton);
        frame.add(predictButton);
        frame.add(addRowButton);
        frame.add(evaluateButton);
        frame.add(resultLabel);

        frame.setVisible(true);
    }
}