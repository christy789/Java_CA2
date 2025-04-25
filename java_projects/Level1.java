package com.java_projects;

class Level1 {
    private final double pYes = 0.545;
    private final double pNo = 0.455;

    public double predict(String accountType, String hasCreditCard, String hasLoan, String employmentStatus) {
        double yesProb = pYes *
                getProb("AccountType", accountType, "yes") *
                getProb("HasCreditCard", hasCreditCard, "yes") *
                getProb("HasLoan", hasLoan, "yes") *
                getProb("EmploymentStatus", employmentStatus, "yes");

        double noProb = pNo *
                getProb("AccountType", accountType, "no") *
                getProb("HasCreditCard", hasCreditCard, "no") *
                getProb("HasLoan", hasLoan, "no") *
                getProb("EmploymentStatus", employmentStatus, "no");

        return yesProb > noProb ? 1.0 : 0.0;
    }

    private double getProb(String feature, String value, String label) {
        switch (feature) {
            case "AccountType":
                return label.equals("yes") ? (value.equals("business") ? 0.523 : 0.477) : (value.equals("business") ? 0.429 : 0.571);
            case "HasCreditCard":
                return label.equals("yes") ? (value.equals("yes") ? 0.514 : 0.486) : (value.equals("yes") ? 0.527 : 0.473);
            case "HasLoan":
                return label.equals("yes") ? (value.equals("yes") ? 0.468 : 0.532) : (value.equals("yes") ? 0.538 : 0.462);
            case "EmploymentStatus":
                return label.equals("yes") ? (value.equals("employed") ? 0.495 : 0.505) : (value.equals("employed") ? 0.505 : 0.495);
        }
        return 1.0;
    }
}
