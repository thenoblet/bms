package gtpbms.bms.model;

import gtpbms.bms.exception.InvalidWithdrawalAmountException;
import gtpbms.bms.exception.PrematureWithdrawalException;

import java.time.LocalDate;

/**
 * Represents a fixed deposit bank account that earns interest and has a maturity date.
 * Withdrawals are only permitted after the maturity date, and the account earns
 * a fixed interest rate of {@value #INTEREST_RATE} per annum.
 *
 * <p>Key features:
 * <ul>
 *   <li>Fixed interest rate: {@value #INTEREST_RATE} (12%) per annum</li>
 *   <li>Withdrawals restricted until maturity date</li>
 *   <li>Monthly interest calculation and compounding</li>
 * </ul>
 *
 * @see BankAccount
 * @see InterestBearing
 */
public class FixedDepositAccount extends BankAccount implements InterestBearing {

  /**
   * The annual interest rate for fixed deposit accounts (12%)
   */
  private static final double INTEREST_RATE = 0.12;

  /**
   * The date when the fixed deposit matures and withdrawals are permitted
   */
  private final LocalDate maturityDate;

  /**
   * Constructs a new FixedDepositAccount with the specified details.
   *
   * @param accountHolder the name of the account holder (cannot be null or empty)
   * @param accountNumber the unique account number (cannot be null or empty)
   * @param initialBalance the initial deposit amount (must be positive)
   * @param maturityDate the date when the deposit matures (cannot be null or in the past)
   * @throws IllegalArgumentException if any parameter is invalid
   */
  public FixedDepositAccount(String accountHolder, String accountNumber, double initialBalance,
                             LocalDate maturityDate) {
    super(accountHolder, accountNumber, initialBalance);
    this.maturityDate = maturityDate;
  }

  /**
   * Attempts to withdraw the specified amount from the account.
   * Withdrawals are only permitted after the maturity date.
   *
   * @param amount the amount to withdraw (must be positive)
   * @return true if withdrawal was successful, false if insufficient funds
   * @throws PrematureWithdrawalException if current date is before maturity date
   * @throws InvalidWithdrawalAmountException if amount is zero or negative
   */
  @Override
  public boolean withdraw(double amount) {
    if (LocalDate.now().isBefore(maturityDate)) {
      throw new PrematureWithdrawalException(LocalDate.now(), maturityDate);
    }

    if (amount <= 0) {
      throw new InvalidWithdrawalAmountException(
              String.format("Invalid withdrawal amount: %.2f", amount));
    }

    if (amount > balance) {
      return false;
    }

    balance -= amount;
    addTransaction(new Transaction("Withdrawal", -amount, balance));
    return true;
  }

  /**
   * Calculates and deposits monthly interest to the account.
   * The interest is calculated as (balance * annual rate / 12).
   */
  @Override
  public void calculateInterest() {
    double interest = balance * INTEREST_RATE / 12;
    deposit(interest);
    addTransaction(new Transaction("Interest Credit", interest, balance));
  }

  /**
   * Gets the type of this account.
   *
   * @return the account type as "Fixed Deposit Account"
   */
  @Override
  public String getAccountType() {
    return "Fixed Deposit Account";
  }

  /**
   * Gets the maturity date when withdrawals are permitted.
   *
   * @return the maturity date of this fixed deposit
   */
  public LocalDate getMaturityDate() {
    return maturityDate;
  }

  /**
   * Gets the annual interest rate for this account type.
   *
   * @return the fixed interest rate
   */
  public static double getInterestRate() {
    return INTEREST_RATE;
  }
}