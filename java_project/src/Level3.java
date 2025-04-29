import java.io.*;
import java.util.*;

public class Level3 {
    private Map<String, Map<String, Map<String, Double>>> conditionalProbs = new HashMap<>();
    private Map<String, Double> priorProbs = new HashMap<>();
    private boolean trained = false;

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

        Map<String, Integer> labelCounts = new HashMap<>();
        Map<String, Map<String, Map<String, Integer>>> counts = new HashMap<>();

        for (String[] row : data) {
            String label = row[4];
            labelCounts.put(label, labelCounts.getOrDefault(label, 0) + 1);

            for (int i = 0; i < 4; i++) {
                String feature = headers[i];
                String value = row[i];

                counts.putIfAbsent(feature, new HashMap<>());
                counts.get(feature).putIfAbsent(label, new HashMap<>());
                Map<String, Integer> valueCounts = counts.get(feature).get(label);
                valueCounts.put(value, valueCounts.getOrDefault(value, 0) + 1);
            }
        }

        int total = data.size();
        for (String label : labelCounts.keySet()) {
            priorProbs.put(label, labelCounts.get(label) / (double) total);
        }

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

    public String predict(String accountType, String hasCreditCard, String hasLoan, String employmentStatus) {
        if (!trained) return "Not trained yet";

        String[] labels = {"yes", "no"};
        double bestProb = -1;
        String bestLabel = "";

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

    private double getProb(String feature, String value, String label) {
        return conditionalProbs.getOrDefault(feature, new HashMap<>())
                .getOrDefault(label, new HashMap<>())
                .getOrDefault(value, 1e-6); // Smoothing
    }
}
