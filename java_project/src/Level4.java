import java.io.*;
import java.util.*;

public class Level4 {
    // Stores conditional probabilities: P(feature=value | label)
    private Map<String, Map<String, Map<String, Double>>> conditionalProbs = new HashMap<>();

    // Stores prior probabilities: P(label)
    private Map<String, Double> priorProbs = new HashMap<>();

    // Flag to check if the model has been trained
    private boolean trained = false;

    // Train the classifier using a given list of data and headers
    public void trainFromData(List<String[]> data, String[] headers) {
        Map<String, Integer> labelCounts = new HashMap<>();
        Map<String, Map<String, Map<String, Integer>>> counts = new HashMap<>();

        for (String[] row : data) {
            if (row.length < 5) continue;
            String label = row[4];
            labelCounts.put(label, labelCounts.getOrDefault(label, 0) + 1);

            // Count how often each feature value appears for each label
            for (int i = 0; i < 4; i++) {
                String feature = headers[i];
                String value = row[i];

                counts.putIfAbsent(feature, new HashMap<>());
                counts.get(feature).putIfAbsent(label, new HashMap<>());
                Map<String, Integer> valueCounts = counts.get(feature).get(label);
                valueCounts.put(value, valueCounts.getOrDefault(value, 0) + 1);
            }
        }

        // Calculate prior probabilities for each label
        int total = data.size();
        priorProbs.clear();
        conditionalProbs.clear();

        for (String label : labelCounts.keySet()) {
            priorProbs.put(label, labelCounts.get(label) / (double) total);
        }

        // Calculate conditional probabilities for each feature value given a label
        for (String feature : counts.keySet()) {
            conditionalProbs.putIfAbsent(feature, new HashMap<>());
            for (String label : counts.get(feature).keySet()) {
                conditionalProbs.get(feature).putIfAbsent(label, new HashMap<>());
                int labelTotal = labelCounts.get(label);
                for (String value : counts.get(feature).get(label).keySet()) {
                    double prob = counts.get(feature).get(label).get(value) / (double) labelTotal;
                    conditionalProbs.get(feature).get(label).put(value, prob);
                }
            }
        }

        trained = true;
    }

    // Load data from a CSV file and train the classifier
    public void train(String filePath) throws Exception {
        List<String[]> data = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filePath));
        String[] headers = scanner.nextLine().split(",");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] row = line.split(",");
            if (row.length < 5) continue;
            data.add(row);
        }
        scanner.close();
        trainFromData(data, headers);
    }

    // Predict if an account will be overdrawn based on feature values
    public String predict(String accountType, String hasCreditCard, String hasLoan, String employmentStatus) {
        if (!trained) return "Not trained yet";

        String[] labels = {"yes", "no"};
        double bestProb = -1;
        String bestLabel = "";

        // Calculate the probability for each label and pick the one with the highest probability
        for (String label : labels) {
            double prob = priorProbs.getOrDefault(label, 1e-6) *
                    getProb("AccountType", accountType, label) *
                    getProb("HasCreditCard", hasCreditCard, label) *
                    getProb("HasLoan", hasLoan, label) *
                    getProb("EmploymentStatus", employmentStatus, label);

            if (prob > bestProb) {
                bestProb = prob;
                bestLabel = label;
            }
        }

        return bestLabel.equals("yes") ? "Yes" : "No";
    }

    // Helper method to get conditional probability safely
    private double getProb(String feature, String value, String label) {
        return conditionalProbs.getOrDefault(feature, new HashMap<>())
                .getOrDefault(label, new HashMap<>())
                .getOrDefault(value, 1e-6);
    }
}