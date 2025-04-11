package gtpbms.bms.model;

import gtpbms.bms.exception.InvalidInitialBalanceException;
import gtpbms.bms.exception.InvalidWithdrawalAmountException;

/**
 * Represents a current/checking bank account that allows overdrafts up to a specified limit.
 * This account type requires a minimum balance and provides overdraft facilities.
 *
 * <p>Key features:
 * <ul>
 *   <li>Minimum balance requirement: {@value #MINIMUM_BALANCE}</li>
 *   <li>Overdraft limit: {@value #OVERDRAFT_LIMIT}</li>
 *   <li>Standard withdrawal operations with overdraft protection</li>
 * </ul>
 *
 * @see BankAccount
 */
public class CurrentAccount extends BankAccount {
  public static final double MINIMUM_BALANCE = 200.0;
  private static final double OVERDRAFT_LIMIT = 1000.0;

  /**
   * Constructs a new CurrentAccount with the specified details.
   *
   * @param accountHolder the name of the account holder (cannot be null or empty)
   * @param accountNumber the unique account number (cannot be null or empty)
   * @param initialBalance the initial balance (must be >= MINIMUM_BALANCE)
   * @throws InvalidInitialBalanceException if initialBalance is less than MINIMUM_BALANCE
   * @throws IllegalArgumentException if accountHolder or accountNumber are invalid
   */
  public CurrentAccount(String accountHolder, String accountNumber, double initialBalance) {
    super(accountHolder, accountNumber, initialBalance);

    if (initialBalance < MINIMUM_BALANCE) {
      throw new InvalidInitialBalanceException(
              String.format("Initial balance must be at least %.2f", MINIMUM_BALANCE));
    }
  }

  /**
   * Withdraws the specified amount from the account.
   * The withdrawal may be permitted even if it causes a negative balance,
   * as long as it doesn't exceed the overdraft limit.
   *
   * @param amount the amount to withdraw (must be > 0)
   * @return true if withdrawal was successful, false if it would exceed overdraft limit
   * @throws InvalidWithdrawalAmountException if amount is <= 0
   */
  @Override
  public boolean withdraw(double amount) {
    if (amount <= 0) {
      throw new InvalidWithdrawalAmountException(
              String.format("Invalid withdrawal amount. Received: %.2f", amount));
    }

    if (balance - amount < -OVERDRAFT_LIMIT) {
      return false;
    }

    balance -= amount;
    addTransaction(new Transaction("Withdrawal", -amount, balance));
    return true;
  }

  /**
   * Gets the type of this account.
   *
   * @return the account type as "Current Account"
   */
  @Override
  public String getAccountType() {
    return "Current Account";
  }

  /**
   * Gets the minimum balance requirement for this account type.
   *
   * @return the minimum balance required
   */
  public static double getMinimumBalance() {
    return MINIMUM_BALANCE;
  }

  /**
   * Gets the overdraft limit for this account type.
   *
   * @return the maximum allowed overdraft amount
   */
  public static double getOverdraftLimit() {
    return OVERDRAFT_LIMIT;
  }
}