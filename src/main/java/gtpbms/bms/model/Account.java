package gtpbms.bms.model;

import java.util.List;

/**
 * Represents a bank account with basic operations and transaction tracking.
 * This interface defines the common behavior for all types of bank accounts.
 *
 */
public interface Account {

  /**
   * Deposits the specified amount into the account.
   *
   * @param amount the amount to deposit (must be positive)
   * @throws IllegalArgumentException if amount is negative or zero
   */
  void deposit(double amount);

  /**
   * Withdraws the specified amount from the account.
   *
   * @param amount the amount to withdraw (must be positive)
   * @return true if withdrawal was successful, false otherwise
   * @throws IllegalArgumentException if amount is negative or zero
   */
  boolean withdraw(double amount);

  /**
   * Gets the current balance of the account.
   *
   * @return the current balance
   */
  double getBalance();

  /**
   * Gets the unique account number identifying this account.
   *
   * @return the account number
   */
  String getAccountNumber();

  /**
   * Adds a transaction to the account's transaction history.
   *
   * @param transaction the transaction to add
   * @throws IllegalArgumentException if transaction is null
   */
  void addTransaction(Transaction transaction);

  /**
   * Gets the complete transaction history of the account.
   *
   * @return a list of all transactions, ordered by date (most recent first)
   */
  List<Transaction> getTransactionHistory();

  /**
   * Gets the type of the account (e.g., "Savings", "Checking").
   *
   * @return the account type
   */
  String getAccountType();
}