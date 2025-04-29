import java.io.*;

class Project_Control {
    private final Level3 classifier = new Level3();
    private final String filePath = "C:\\Users\\chris\\OneDrive\\Desktop\\Uni work\\oop_java\\predictive_dataset.csv";

    public void train() throws Exception {
        classifier.train(filePath);
    }

    public void addRow(String accountType, String hasCreditCard, String hasLoan, String employmentStatus, String label) throws Exception {
        FileWriter fw = new FileWriter(filePath, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.newLine();
        bw.write(accountType + "," + hasCreditCard + "," + hasLoan + "," + employmentStatus + "," + label);
        bw.close();
    }

    public String predict(String accountType, String hasCreditCard, String hasLoan, String employmentStatus) {
        return classifier.predict(accountType, hasCreditCard, hasLoan, employmentStatus);
    }
}