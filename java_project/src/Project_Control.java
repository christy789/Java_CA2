import java.io.*;
import java.util.*;


class Project_Control {
    private final Level4 classifier = new Level4();
    private final String filePath = "C:\\Users\\chris\\OneDrive\\Desktop\\Uni work\\oop_java\\predictive_dataset.csv";

    public void train() throws Exception {
        classifier.train(filePath);
    }

    public void addRow(String accountType, String hasCreditCard, String hasLoan, String employmentStatus, String label) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.newLine();
            bw.write(accountType + "," + hasCreditCard + "," + hasLoan + "," + employmentStatus + "," + label);
        }
    }

    public String predict(String accountType, String hasCreditCard, String hasLoan, String employmentStatus) {
        return classifier.predict(accountType, hasCreditCard, hasLoan, employmentStatus);
    }

    public double calculateAccuracy() throws Exception {
        List<String[]> data = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filePath));
        String[] headers = scanner.nextLine().split(",");

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

        Collections.shuffle(yesList);
        Collections.shuffle(noList);

        int yesTrainCount = (int)(yesList.size() * 0.75);
        int noTrainCount = (int)(noList.size() * 0.75);

        List<String[]> trainingData = new ArrayList<>();
        List<String[]> testingData = new ArrayList<>();

        trainingData.addAll(yesList.subList(0, yesTrainCount));
        trainingData.addAll(noList.subList(0, noTrainCount));
        testingData.addAll(yesList.subList(yesTrainCount, yesList.size()));
        testingData.addAll(noList.subList(noTrainCount, noList.size()));

        classifier.trainFromData(trainingData, headers);

        int correct = 0;
        for (String[] row : testingData) {
            String prediction = classifier.predict(row[0], row[1], row[2], row[3]);
            if (prediction.equalsIgnoreCase(row[4])) {
                correct++;
            }
        }
        return (correct * 100.0) / testingData.size();
    }
}