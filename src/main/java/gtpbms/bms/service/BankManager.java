package gtpbms.bms.service;

import gtpbms.bms.model.Bank;

/**
 * Provides centralized management of the bank instance in the application.
 * This class serves as a singleton manager for the bank object, ensuring
 * there's only one instance throughout the application.
 * The bank is initialised with the name "NONA BANK" when the class is loaded.
 */
public class BankManager {
    /** The single instance of the bank */
    private static final Bank bank = new Bank("NONA BANK");

    /**
     * Gets the singleton instance of the bank.
     *
     * @return The single Bank instance managed by this class
     */
    public static Bank getBank() {
        return bank;
    }

    /**
     * Private constructor to prevent instantiation.
     * This enforces the singleton pattern.
     */
    private BankManager() {
        throw new AssertionError("Cannot instantiate BankManager");
    }
}