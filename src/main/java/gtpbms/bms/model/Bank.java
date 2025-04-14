package gtpbms.bms.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a banking institution that manages multiple bank accounts.
 * Provides functionality to add, retrieve, and list accounts.
 */
public class Bank {
  private final String name;
  private final Map<String, BankAccount> accounts;

  /**
   * Constructs a new Bank with the specified name.
   *
   * @param name the name of the bank (cannot be null or empty)
   * @throws IllegalArgumentException if name is null or empty
   */
  public Bank(String name) {
    this.name = name;
    this.accounts = new HashMap<>();
  }

  /**
   * Adds a new account to the bank.
   *
   * @param account the account to add (cannot be null)
   * @throws IllegalArgumentException if account is null or an account with the same number already exists
   */
  public void addAccount(BankAccount account) {
    accounts.put(account.getAccountNumber(), account);
  }

  /**
   * Retrieves an account by its account number.
   *
   * @param accountNumber the account number to search for (cannot be null or empty)
   * @return the BankAccount with the specified number, or null if not found
   * @throws IllegalArgumentException if accountNumber is null or empty
   */
  public BankAccount getAccount(String accountNumber) {
    return accounts.get(accountNumber);
  }

  /**
   * Gets a list of all accounts in the bank.
   *
   * @return a new List containing all accounts
   */
  public List<BankAccount> getAllAccounts() {
    return new ArrayList<>(accounts.values());
  }

  /**
   * Gets the name of the bank.
   *
   * @return the bank name
   */
  public String getName() {
    return name;
  }
}