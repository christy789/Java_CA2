import java.io.*;
import java.util.*;

class Project_Control {
    // Create an instance of your Naive Bayes classifier
    private final Level4 classifier = new Level4();

    // Path to the CSV file that stores the dataset
    private final String filePath = "C:\\Users\\chris\\OneDrive\\Desktop\\Uni work\\oop_java\\predictive_dataset.csv";

    // Load the dataset from file and train the classifier
    public void train() throws Exception {
        classifier.train(filePath);
    }

    // Append a new row to the dataset CSV file
    public void addRow(String accountType, String hasCreditCard, String hasLoan, String employmentStatus, String label) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.newLine(); // Add a new line before the new row
            bw.write(accountType + "," + hasCreditCard + "," + hasLoan + "," + employmentStatus + "," + label);
        }
    }

    // Use the trained classifier to predict the outcome (yes/no) for a given set of input features
    public String predict(String accountType, String hasCreditCard, String hasLoan, String employmentStatus) {
        return classifier.predict(accountType, hasCreditCard, hasLoan, employmentStatus);
    }

    // Evaluate how accurate the classifier is by using a stratified split of the dataset
    public double calculateAccuracy() throws Exception {
        List<String[]> data = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filePath));
        String[] headers = scanner.nextLine().split(",");

        // Separate the rows into 'yes' and 'no' label groups
        List<String[]> yesList = new ArrayList<>();
        List<String[]> noList = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] row = line.split(",");
            if (row.length < 5) continue;
            if (row[4].equals("yes")) yesList.add(row);
            else if (row[4].equals("no")) noList.add(row);
        }
        scanner.close();

        // Randomize both yes and no sets
        Collections.shuffle(yesList);
        Collections.shuffle(noList);

        // Stratified split: 75% for training, 25% for testing in both yes and no groups
        int yesTrainCount = (int)(yesList.size() * 0.75);
        int noTrainCount = (int)(noList.size() * 0.75);

        List<String[]> trainingData = new ArrayList<>();
        List<String[]> testingData = new ArrayList<>();

        trainingData.addAll(yesList.subList(0, yesTrainCount));
        trainingData.addAll(noList.subList(0, noTrainCount));
        testingData.addAll(yesList.subList(yesTrainCount, yesList.size()));
        testingData.addAll(noList.subList(noTrainCount, noList.size()));

        // Train the model using the training portion
        classifier.trainFromData(trainingData, headers);

        // Test the model on the testing portion
        int correct = 0;
        for (String[] row : testingData) {
            String prediction = classifier.predict(row[0], row[1], row[2], row[3]);
            if (prediction.equalsIgnoreCase(row[4])) {
                correct++;
            }
        }

        // Return the accuracy as a percentage
        return (correct * 100.0) / testingData.size();
    }
}