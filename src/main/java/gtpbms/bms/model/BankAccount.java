package gtpbms.bms.model;

import gtpbms.bms.exception.InvalidDepositAmountException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * An abstract base class representing a bank account that implements the core Account functionality.
 * Provides common operations like deposits, balance tracking, and transaction history.
 *
 * <p>This class maintains:
 * <ul>
 *   <li>Account holder information</li>
 *   <li>Account number</li>
 *   <li>Current balance</li>
 *   <li>Transaction history (most recent first)</li>
 * </ul>
 */
public abstract class BankAccount implements Account {
  protected String accountHolder;
  protected String accountNumber;
  protected double balance;
  protected LinkedList<Transaction> transactionHistory;

  /**
   * Constructs a new BankAccount with the specified details.
   *
   * @param accountHolder the name of the account holder (cannot be null or empty)
   * @param accountNumber the unique account number (cannot be null or empty)
   * @param initialBalance the initial balance (must be >= 0)
   * @throws IllegalArgumentException if any argument is invalid
   */
  public BankAccount(String accountHolder, String accountNumber, double initialBalance) {
    this.accountHolder = accountHolder;
    this.accountNumber = accountNumber;
    this.balance = initialBalance;
    this.transactionHistory = new LinkedList<>();
    this.addTransaction(new Transaction("Account opened", initialBalance, initialBalance));
  }

  /**
   * Deposits the specified amount into the account.
   *
   * @param amount the amount to deposit (must be > 0)
   * @throws InvalidDepositAmountException if amount is <= 0
   */
  @Override
  public void deposit(double amount) {
    if (amount <= 0) {
      throw new InvalidDepositAmountException("Deposit amount must be positive. Received: " + amount);
    }

    balance += amount;
    this.addTransaction(new Transaction("Deposit", amount, balance));
  }

  /**
   * Gets the current account balance.
   *
   * @return the current balance
   */
  @Override
  public double getBalance() {
    return balance;
  }

  /**
   * Gets the account number.
   *
   * @return the account number
   */
  @Override
  public String getAccountNumber() {
    return accountNumber;
  }

  /**
   * Adds a transaction to the account's transaction history.
   * Transactions are stored in reverse chronological order (most recent first).
   *
   * @param transaction the transaction to add (cannot be null)
   * @throws IllegalArgumentException if transaction is null
   */
  @Override
  public void addTransaction(Transaction transaction) {
    transactionHistory.addFirst(transaction);
  }

  /**
   * Gets a copy of the transaction history.
   *
   * @return a new List containing all transactions in chronological order (most recent first)
   */
  @Override
  public List<Transaction> getTransactionHistory() {
    return new ArrayList<>(transactionHistory);
  }

  /**
   * Gets the account holder's name.
   *
   * @return the account holder name
   */
  public String getAccountHolder() {
    return accountHolder;
  }
}