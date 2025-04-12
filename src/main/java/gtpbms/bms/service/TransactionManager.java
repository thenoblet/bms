package gtpbms.bms.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gtpbms.bms.model.Transaction;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private static final String TXN_DIR = "transactions/";
    private static final Gson gson = new Gson();

    // Save transactions to a JSON file named after the account number
    public static void saveTransactions(String accountNumber, List<Transaction> transactions) {
        File transactionsDir = new File(TXN_DIR);
        if (!transactionsDir.exists()) {
            boolean dirCreated = transactionsDir.mkdir();
            if (!dirCreated) {
                System.out.println("Failed to create directory " + TXN_DIR);
            }
        }

        String filePath = TXN_DIR + accountNumber + ".json";

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(transactions, writer);
        } catch (IOException e) {
            System.err.println("Error saving transactions: " + e.getMessage());
        }
    }

    // Load transactions from JSON file
    public static List<Transaction> loadTransactions(String accountNumber) {
        String filePath = TXN_DIR + accountNumber + ".json";
        File file = new File(filePath);

        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                Type type = new TypeToken<List<Transaction>>(){}.getType();
                List<Transaction> transactions = gson.fromJson(reader, type);
                return transactions != null ? transactions : new ArrayList<>();
            } catch (IOException e) {
                System.err.println("Error loading transactions: " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }
}