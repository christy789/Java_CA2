import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Project_GUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Account Overdrawn Predictor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setLayout(new GridLayout(7, 2));

        String[] options1 = {"business", "personal"};
        String[] options2 = {"yes", "no"};
        String[] options3 = {"employed", "unemployed"};

        JComboBox<String> accountTypeBox = new JComboBox<>(options1);
        JComboBox<String> creditCardBox = new JComboBox<>(options2);
        JComboBox<String> loanBox = new JComboBox<>(options2);
        JComboBox<String> employmentBox = new JComboBox<>(options3);

        JButton trainButton = new JButton("Train");
        JButton predictButton = new JButton("Predict");
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

        predictButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountType = (String) accountTypeBox.getSelectedItem();
                String creditCard = (String) creditCardBox.getSelectedItem();
                String loan = (String) loanBox.getSelectedItem();
                String employment = (String) employmentBox.getSelectedItem();

                String prediction = controller.predict(accountType, creditCard, loan, employment);
                resultLabel.setText("Prediction: Account will be overdrawn = " + prediction);
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
        frame.add(trainButton);
        frame.add(predictButton);
        frame.add(resultLabel);

        frame.setVisible(true);
    }
}