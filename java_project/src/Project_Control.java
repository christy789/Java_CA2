class Project_Control {
    private final Level1 classifier;

    public Project_Control() {
        this.classifier = new Level1();
    }

    public String predict(String accountType, String hasCreditCard, String hasLoan, String employmentStatus) {
        double prediction = classifier.predict(accountType, hasCreditCard, hasLoan, employmentStatus);
        return prediction == 1.0 ? "Yes" : "No";
    }
}

