package gtpbms.bms.model;

/**
 * Defines the contract for accounts that can accrue interest.
 *
 * Implementing classes must define how interest is calculated.
 * This is a functional interface representing the single operation of interest calculation.
 *
 * @see SavingsAccount
 * @see FixedDepositAccount
 */
public interface InterestBearing {

  /**
   * Calculates and applies interest according to the account's specific rules.
   *
   * Implementations should:
   * - Determine their own interest rate and calculation method
   * - Handle the timing of interest application
   * - Update the account balance with accrued interest
   *
   * Note: The actual interest calculation (simple/compound) and
   * frequency (daily/monthly/annually) are implementation details.
   */
  void calculateInterest();
}