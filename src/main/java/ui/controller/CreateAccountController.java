package ui.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import java.time.LocalDate;
import gtpbms.bms.service.BankManager;
import gtpbms.bms.model.Bank;
import gtpbms.bms.model.BankAccount;
import gtpbms.bms.model.FixedDepositAccount;
import gtpbms.bms.model.SavingsAccount;
import gtpbms.bms.model.CurrentAccount;

/**
 * Controller for the account creation screen.
 * Handles user input validation and creation of different account types.
 */
public class CreateAccountController implements Initializable {

    @FXML private TextField fullNameField;
    @FXML private ComboBox<String> accountTypeComboBox;
    @FXML private TextField initialDepositField;
    @FXML private Button createAccountButton;
    @FXML private Button backToWelcomeButton;

    /**
     * Initializes the controller class.
     * Sets up the account type dropdown with available account types.
     *
     * @param url The location used to resolve relative paths
     * @param rb The resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        accountTypeComboBox.setItems(FXCollections.observableArrayList(
                "Savings Account", "Current Account", "Fixed Deposit Account"
        ));
    }

    /**
     * Handles account creation when the create account button is clicked.
     * Validates input, creates the account, and navigates to account operations.
     *
     * @param event The action event triggered by the button click
     */
    @FXML
    void handleCreateAccount(ActionEvent event) {
        Bank bank = BankManager.getBank();

        if (isInputValid()) {
            String fullName = fullNameField.getText();
            String accountType = accountTypeComboBox.getValue();
            double initialDeposit = Double.parseDouble(initialDepositField.getText());

            BankAccount newAccount = createBankAccountByType(fullName, accountType, initialDeposit);
            bank.addAccount(newAccount);

            showSuccessAlert("Account successfully created for " + fullName + "!");
            navigateToAccountOperations(event, newAccount);
        }
    }

    /**
     * Handles navigation back to the welcome screen.
     *
     * @param event The action event triggered by the button click
     */
    @FXML
    void handleBackToWelcome(ActionEvent event) {
        navigateToWelcome(event);
    }

    /**
     * Validates all user input fields.
     *
     * @return true if all input is valid, false otherwise
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (fullNameField.getText() == null || fullNameField.getText().trim().isEmpty()) {
            errorMessage += "Please enter your full name.\n";
        }

        String selectedAccountType = accountTypeComboBox.getValue();
        if (selectedAccountType == null) {
            errorMessage += "Please select an account type.\n";
        }

        if (initialDepositField.getText() == null || initialDepositField.getText().trim().isEmpty()) {
            errorMessage += "Please enter an initial deposit amount.\n";
        } else {
            try {
                double amount = Double.parseDouble(initialDepositField.getText());
                if (amount <= 0) {
                    errorMessage += "Initial deposit must be greater than 0.\n";
                } else if (selectedAccountType != null) {
                    double minimumRequired = getMinimumDepositForAccountType(selectedAccountType);
                    if (amount < minimumRequired) {
                        errorMessage += String.format("For %s, minimum initial deposit is GH₵ %.2f\n",
                                selectedAccountType, minimumRequired);
                    }
                }
            } catch (NumberFormatException e) {
                errorMessage += "Initial deposit must be a valid number.\n";
            }
        }

        if (!errorMessage.isEmpty()) {
            showErrorAlert("Invalid Fields", "Please correct the invalid fields", errorMessage);
            return false;
        }
        return true;
    }

    /**
     * Gets the minimum deposit required for the specified account type.
     *
     * @param accountType The type of account
     * @return The minimum required deposit amount
     */
    private double getMinimumDepositForAccountType(String accountType) {
        switch(accountType) {
            case "Savings Account": return SavingsAccount.MINIMUM_BALANCE;
            case "Current Account": return CurrentAccount.MINIMUM_BALANCE;
            default: return 0;
        }
    }

    /**
     * Creates a specific type of bank account based on user selection.
     *
     * @param fullName The account holder's full name
     * @param accountType The type of account to create
     * @param initialDeposit The initial deposit amount
     * @return The newly created bank account
     * @throws IllegalArgumentException if accountType is invalid
     */
    private BankAccount createBankAccountByType(String fullName, String accountType, double initialDeposit) {
        switch(accountType) {
            case "Savings Account":
                return new SavingsAccount(fullName, generateAccountNumber(), initialDeposit);
            case "Current Account":
                return new CurrentAccount(fullName, generateAccountNumber(), initialDeposit);
            case "Fixed Deposit Account":
                LocalDate maturityDate = LocalDate.now().plusYears(1);
                return new FixedDepositAccount(fullName, generateAccountNumber(), initialDeposit, maturityDate);
            default:
                throw new IllegalArgumentException("Invalid account type: " + accountType);
        }
    }

    /**
     * Navigates to the welcome screen.
     *
     * @param event The action event that triggered the navigation
     */
    private void navigateToWelcome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gtpbms/bms/view/welcomeview.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            handleNavigationError("Error loading welcome view: ", e, event);
        }
    }

    /**
     * Navigates to the account operations screen for the newly created account.
     *
     * @param event The action event that triggered the navigation
     * @param account The newly created bank account
     */
    private void navigateToAccountOperations(ActionEvent event, BankAccount account) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gtpbms/view/accountoperationsview.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());

            AccountOperationsController controller = loader.getController();
            controller.initData(account);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            handleNavigationError("Error loading account operations view: ", e, event);
            navigateToWelcome(event);
        }
    }

    /**
     * Shows an error alert dialog.
     *
     * @param title The alert title
     * @param header The alert header text
     * @param content The alert content text
     */
    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Shows a success alert dialog.
     *
     * @param message The success message to display
     */
    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Account Created");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles navigation errors by logging and showing an alert.
     *
     * @param message The error message prefix
     * @param e The exception that occurred
     * @param event The original action event
     */
    private void handleNavigationError(String message, Exception e, ActionEvent event) {
        System.out.println(message + e.getMessage());
        e.printStackTrace();
        showErrorAlert("Navigation Error", "Error loading view", message + e.getMessage());
    }

    /**
     * Generates a new account number.
     * Note: In a real implementation, this would use proper account number generation logic.
     *
     * @return A generated account number
     */
    private String generateAccountNumber() {
        return "NONA" + System.currentTimeMillis();
    }
}