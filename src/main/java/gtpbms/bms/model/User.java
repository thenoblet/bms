package gtpbms.bms.model;

import java.io.Serializable;

public class User implements Serializable {
    private final String username;
    private final String password;
    private final String accountNumber;

    public User(String username, String password, String accountNumber) {
        this.username = username;
        this.password = password;
        this.accountNumber = accountNumber;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public String getAccountNumber() {
        return accountNumber;
    }
}