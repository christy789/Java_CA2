class Project_Control {
    private final level2 classifier = new level2();

    public void train() throws Exception {
        classifier.train("C:\\Users\\chris\\OneDrive\\Desktop\\Uni work\\oop_java\\predictive_dataset.csv");
    }

    public String predict(String accountType, String hasCreditCard, String hasLoan, String employmentStatus) {
        return classifier.predict(accountType, hasCreditCard, hasLoan, employmentStatus);
    }
}