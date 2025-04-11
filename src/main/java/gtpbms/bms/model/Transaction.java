package gtpbms.bms.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a financial transaction in the banking system.
 * Captures all relevant details including description, amount,
 * resulting balance, and timestamp.
 */
public class Transaction {
  private final String description;
  private final double amount;
  private final double balanceAfter;
  private final LocalDateTime timestamp;

  /**
   * Creates a new Transaction with the specified details.
   *
   * @param description A description of the transaction (not null or empty)
   * @param amount The transaction amount (positive for deposits, negative for withdrawals)
   * @param balanceAfter The account balance after this transaction
   * @throws IllegalArgumentException if description is null or empty
   */
  public Transaction(String description, double amount, double balanceAfter) {
    this.description = description;
    this.amount = amount;
    this.balanceAfter = balanceAfter;
    this.timestamp = LocalDateTime.now();
  }

  /**
   * Gets the transaction description.
   *
   * @return The transaction description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the transaction amount.
   *
   * @return The amount (positive for deposits, negative for withdrawals)
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Gets the balance after this transaction was applied.
   *
   * @return The post-transaction balance
   */
  public double getBalanceAfter() {
    return balanceAfter;
  }

  /**
   * Gets the timestamp when the transaction occurred.
   *
   * @return The transaction timestamp
   */
  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  /**
   * Returns a formatted string representation of the transaction.
   * Format: "[DD-MM-YYYY HH:MM:SS] Description: GHcX.XX | Balance: GHcX.XX"
   *
   * @return Formatted transaction string
   */
  @Override
  public String toString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedTime = timestamp.format(formatter);

    return String.format("[%s] %s: %s | Balance: %s",
            formattedTime,
            description,
            formatCurrency(amount),
            formatCurrency(balanceAfter));
  }

  /**
   * Formats a monetary value as Ghanaian Cedi (GHc) with 2 decimal places.
   *
   * @param value The monetary value to format
   * @return Formatted currency string (e.g., "GHc100.50")
   */
  private String formatCurrency(double value) {
    return String.format("GH₵%.2f", value);
  }
}