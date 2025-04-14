package gtpbms.bms.test;

import gtpbms.bms.model.*;
import gtpbms.bms.service.UserManager;

import java.util.Scanner;

public class Main {
    public static Bank bank = new Bank("NONA BANK");
    public static UserManager userManager = new UserManager();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeSampleData();

        while (true) {
            System.out.println("\n1. Register\n2. Login\n3. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // In real app, you'd associate with an existing account
        String accountNumber = "ACCT" + System.currentTimeMillis();
        SavingsAccount account = new SavingsAccount(accountNumber, username, 0);
        bank.addAccount(account);

        User user = new User(username, password, accountNumber);
        if (userManager.register(user)) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Username already exists");
        }
    }

    private static void loginUser() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = userManager.login(username, password);
        if (user != null) {
            BankAccount account = bank.getAccount(user.getAccountNumber());
            System.out.println("\nLogin successful! Welcome " + username);
            accountMenu(account);
        } else {
            System.out.println("Invalid credentials");
        }
    }

    private static void accountMenu(BankAccount account) {
        while (true) {
            System.out.println("\n1. Deposit\n2. Withdraw\n3. View Balance\n4. Transaction History\n5. Logout");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    boolean success = account.withdraw(withdrawAmount);
                    System.out.println(success ? "Withdrawal successful" : "Withdrawal failed");
                    break;
                case 3:
                    System.out.println("Current balance: " + account.getBalance());
                    break;
                case 4:
                    System.out.println("\nTransaction History:");
                    account.getTransactionHistory().forEach(System.out::println);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void initializeSampleData() {
        // Existing sample accounts
        SavingsAccount savings = new SavingsAccount("SAV001", "John Doe", 500.00);
        CurrentAccount current = new CurrentAccount("CUR001", "Jane Smith", 1000.00);
        bank.addAccount(savings);
        bank.addAccount(current);

        // Sample users
        userManager.register(new User("john", "pass123", "SAV001"));
        userManager.register(new User("jane", "pass456", "CUR001"));
    }
}