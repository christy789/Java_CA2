# Account Overdrawn Predictor (Java OOP + Naive Bayes)

This Java application is a machine learning prediction tool that determines whether a bank account is likely to be overdrawn, based on user-entered categorical data. It is built using **Object-Oriented Programming (OOP)** principles and a **Naive Bayes classifier**, and includes a user-friendly **GUI** for interaction.

---

##  Features

-  **Train** the classifier using a CSV dataset of 200 examples.
-  **Add new training data** directly from the GUI.
-  **Make predictions** based on user input (4 features).
-  **Evaluate model accuracy** using a stratified 75/25 split.
-  Intuitive GUI built with Java Swing.

---

## Dataset Format

The dataset used is a CSV file with **5 columns**:
Each column is **categorical** with 2 possible values:

- `AccountType`: `business`, `personal`
- `HasCreditCard`: `yes`, `no`
- `HasLoan`: `yes`, `no`
- `EmploymentStatus`: `employed`, `unemployed`
- `AccountIsOverdrawn` (label): `yes`, `no`

---

## How to Run

1. Open the project in your Java IDE or command line.
2. Make sure your CSV dataset is located in the correct path (e.g., `predictive_dataset.csv`).
3. Run the `Project_GUI` class.
4. Use the GUI to:
   - Train the model
   - Make predictions
   - Add new rows
   - Evaluate accuracy

---

## Project Structure

| File | Purpose |
|------|---------|
| `Project_GUI.java` | Main GUI class |
| `Project_Control.java` | Handles coordination between GUI and model |
| `Level4.java` | Contains the Naive Bayes logic |
| `predictive_dataset.csv` | Your training/testing dataset |

---

##  OOP Concepts Used

- **Encapsulation**: Data hidden inside classes with public methods for interaction.
- **Composition**: GUI class uses controller; controller uses classifier.
- **Abstraction**: Class responsibilities are clearly defined and separated.
- **Modularity**: Each class focuses on a specific task, improving reusability and readability.

---

## Evaluation Method

Model accuracy is calculated using a **stratified split** of the data:
- 75% training
- 25% testing
- Maintains same label distribution (e.g., 60/40 split of yes/no in both sets)

---

## Example Use Case

Predict if a new customer with:
- A business account
- No credit card
- No loan
- Unemployed status  
…will likely have an overdrawn account.

---

## Author

- **Christian Bagrin**  
- Computer Science Infrastructure, TU Dublin  
- Module: Object-Oriented Programming (CA2 Submission)

---
