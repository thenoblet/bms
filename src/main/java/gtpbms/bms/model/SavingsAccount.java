package gtpbms.bms.model;

import gtpbms.bms.exception.InsufficientFundsException;
import gtpbms.bms.exception.InvalidInitialBalanceException;
import gtpbms.bms.exception.InvalidWithdrawalAmountException;

/**
 * Represents a savings bank account that earns interest and maintains a minimum balance.
 * Implements InterestBearing for monthly interest calculations.
 *
 * Minimum balance: 100.0
 * Interest rate: 2% per annum
 *
 * @see BankAccount
 * @see InterestBearing
 */
public class SavingsAccount extends BankAccount implements InterestBearing {
  public static final double MINIMUM_BALANCE = 100.0;
  public static final double INTEREST_RATE = 0.02;

  /**
   * Creates a new SavingsAccount with specified details.
   *
   * @param accountHolder Name of account holder (not null or empty)
   * @param accountNumber Unique account number (not null or empty)
   * @param initialBalance Opening balance (must be >= MINIMUM_BALANCE)
   * @throws InvalidInitialBalanceException if initialBalance is insufficient
   * @throws IllegalArgumentException for invalid parameters
   */
  public SavingsAccount(String accountHolder, String accountNumber, double initialBalance) {
    super(accountHolder, accountNumber, initialBalance);
    if (initialBalance < MINIMUM_BALANCE) {
      throw new InvalidInitialBalanceException(
              String.format("Initial balance must be at least %.2f", MINIMUM_BALANCE));
    }
  }

  /**
   * Withdraws specified amount if allowed by account rules.
   *
   * @param amount Positive amount to withdraw
   * @return true if withdrawal succeeded, false if it would violate minimum balance
   * @throws InvalidWithdrawalAmountException for non-positive amounts
   * @throws InsufficientFundsException if balance would go negative
   */
  @Override
  public boolean withdraw(double amount) {
    if (amount <= 0) {
      throw new InvalidWithdrawalAmountException(
              String.format("Invalid withdrawal amount. Received: %.2f", amount));
    }

    if (amount > balance) {
      throw new InsufficientFundsException(
              String.format("Insufficient funds. Requested: %.2f, Available: %.2f", amount, balance));
    }

    if (balance - amount < MINIMUM_BALANCE) {
      return false;
    }

    balance -= amount;
    addTransaction(new Transaction("Withdrawal", -amount, balance));
    return true;
  }

  /**
   * Calculates and deposits monthly interest (1/12 of annual rate).
   * Uses current balance and fixed interest rate.
   */
  @Override
  public void calculateInterest() {
    double interest = balance * INTEREST_RATE / 12;
    deposit(interest);
    addTransaction(new Transaction("Interest Credit", interest, balance));
  }

  /**
   * Returns the account type identifier.
   *
   * @return "Savings Account"
   */
  @Override
  public String getAccountType() {
    return "Savings Account";
  }

  /**
   * Gets the minimum balance requirement.
   *
   * @return The minimum balance amount
   */
  public static double getMinimumBalance() {
    return MINIMUM_BALANCE;
  }

  /**
   * Gets the annual interest rate.
   *
   * @return The interest rate
   */
  public static double getInterestRate() {
    return INTEREST_RATE;
  }
}