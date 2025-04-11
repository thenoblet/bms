package gtpbms.bms.test;

import gtpbms.bms.model.*;

import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {

        final String CURRENCY = "GH₵";

        Bank bank = new Bank("NONA BANK");

        SavingsAccount savings = new SavingsAccount("Patrick", "NONA1234", 500.00);
        CurrentAccount current = new CurrentAccount("Nancy", "NONA9999", 1000);
        FixedDepositAccount fixedDeposit = new FixedDepositAccount(
                "FIX001",
                "Alice Johnson",
                5000.00,
                LocalDate.now().plusYears(1)
        );


        bank.addAccount(savings);
        bank.addAccount(current);
        bank.addAccount(fixedDeposit);

        testAccountOperations(savings);
        testAccountOperations(current);
        //testAccountOperations(fixedDeposit);

    }

    private static void testAccountOperations(BankAccount account) {
        System.out.printf("\nTesting %s: %s\n", account.getAccountType(), account.getAccountNumber());

        System.out.printf("Initial balance: %.2f\n", account.getBalance());

        account.deposit(200);
        System.out.println("After depositing GHc200.00: $" + account.getBalance());

        boolean withdrawSuccess = account.withdraw(100.00);
        System.out.println("Withdrawal of $100.00 " +
                (withdrawSuccess ? "succeeded" : "failed") +
                ". New balance: $" + account.getBalance());

        boolean largeWithdrawSuccess = account.withdraw(.00);
        System.out.println("Large withdrawal of GHc1000.00 " +
                (largeWithdrawSuccess ? "succeeded" : "failed") +
                ". New balance: $" + account.getBalance());

        if (account instanceof InterestBearing) {
            ((InterestBearing) account).calculateInterest();
            System.out.println("After interest calculation: GH₵" + account.getBalance());
        }
    }
}
