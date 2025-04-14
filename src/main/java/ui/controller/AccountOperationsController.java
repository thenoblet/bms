package ui.controller;

import gtpbms.bms.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Controller class for managing account operations UI.
 * Handles displaying account information, transaction history,
 * and processing deposit/withdrawal operations.
 */
public class AccountOperationsController {
    private BankAccount currentAccount;

    // UI Components
    @FXML private Label userNameLabel;
    @FXML private Label accountNumberLabel;
    @FXML private Label balanceLabel;
    @FXML private Label accountTypeLabel;
    @FXML private Label specialInfoLabel;

    // Transaction Table Components
    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, String> dateColumn;
    @FXML private TableColumn<Transaction, String> descriptionColumn;
    @FXML private TableColumn<Transaction, String> amountColumn;
    @FXML private TableColumn<Transaction, String> balanceColumn;

    // Operation Components
    @FXML private TabPane operationsTabPane;
    @FXML private TextField depositAmountField;
    @FXML private TextField withdrawalAmountField;
    @FXML private Button depositButton;
    @FXML private Button withdrawButton;

    /**
     * Initializes the controller with account data and sets up UI components.
     *
     * @param account The BankAccount to display and manage
     */
    public void initData(BankAccount account) {
        this.currentAccount = account;
        updateAccountInfo();
        setupTransactionTable();
        configureAccountSpecificFeatures();
    }

    /**
     * Updates the account information display fields.
     */
    private void updateAccountInfo() {
        userNameLabel.setText(currentAccount.getAccountHolder());
        accountNumberLabel.setText(currentAccount.getAccountNumber());
        balanceLabel.setText(String.format("GH₵%.2f", currentAccount.getBalance()));
        accountTypeLabel.setText(currentAccount.getAccountType());
        transactionTable.setItems(FXCollections.observableArrayList(currentAccount.getTransactionHistory()));
    }

    /**
     * Configures the transaction table columns and cell value factories.
     */
    private void setupTransactionTable() {
        dateColumn.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            return new SimpleStringProperty(cellData.getValue().getTimestamp().format(formatter));
        });

        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("GH₵%.2f", cellData.getValue().getAmount())));
        balanceColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("GH₵%.2f", cellData.getValue().getBalanceAfter())));
    }

    /**
     * Configures account-specific UI features based on account type.
     */
    private void configureAccountSpecificFeatures() {
        if (currentAccount instanceof FixedDepositAccount fdAccount) {
            LocalDate maturityDate = fdAccount.getMaturityDate();
            specialInfoLabel.setText("Matures on: " + maturityDate.format(DateTimeFormatter.ISO_DATE));

            if (LocalDate.now().isBefore(maturityDate)) {
                withdrawButton.setDisable(true);
                specialInfoLabel.setText(specialInfoLabel.getText() + " (Withdrawals not allowed yet)");
            }
        }
        else if (currentAccount instanceof SavingsAccount) {
            specialInfoLabel.setText("Minimum Balance: GHc" + SavingsAccount.getMinimumBalance());
        }
        else if (currentAccount instanceof CurrentAccount) {
            specialInfoLabel.setText("Overdraft Limit: GHc" + CurrentAccount.getOverdraftLimit());
        }
    }

    /**
     * Handles deposit operation when deposit button is clicked.
     * Validates input and processes the deposit transaction.
     */
    @FXML
    private void handleDeposit() {
        try {
            double amount = Double.parseDouble(depositAmountField.getText());
            if (amount <= 0) {
                showErrorAlert("Deposit amount must be positive");
                return;
            }

            currentAccount.deposit(amount);
            updateAccountInfo();
            depositAmountField.clear();

        } catch (NumberFormatException e) {
            showErrorAlert("Please enter a valid amount");
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    /**
     * Handles withdrawal operation when withdraw button is clicked.
     * Validates input and processes the withdrawal transaction.
     */
    @FXML
    private void handleWithdrawal() {
        try {
            double amount = Double.parseDouble(withdrawalAmountField.getText());
            if (amount <= 0) {
                showErrorAlert("Withdrawal amount must be positive");
                return;
            }

            if (currentAccount.withdraw(amount)) {
                updateAccountInfo();
                withdrawalAmountField.clear();
            } else {
                showErrorAlert("Withdrawal failed. Check minimum balance or available funds.");
            }

        } catch (NumberFormatException e) {
            showErrorAlert("Please enter a valid amount");
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    /**
     * Handles logout action (to be implemented).
     */
    @FXML
    private void handleLogout() {
        //TODO: Implement navigation back to welcome screen
    }

    /**
     * Displays an error alert dialog with the specified message.
     *
     * @param message The error message to display
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an information alert dialog with the specified message.
     *
     * @param message The information message to display
     */
    private void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}