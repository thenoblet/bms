package gtpbms.bms.service;

import gtpbms.bms.model.User;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static final String USER_FILE = "src/main/java/gtpbms/bms/data/users.json";
    private Map<String, User> users = new HashMap<>();
    private final Gson gson = new Gson();

    public UserManager() {
        loadUsers();
    }

    public boolean register(User user) {
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        users.put(user.getUsername(), user);
        saveUsers();
        return true;
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    private void saveUsers() {
        try (FileWriter writer = new FileWriter(USER_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    private void loadUsers() {
        File file = new File(USER_FILE);
        if (file.exists()) {
            try (Reader reader = new FileReader(USER_FILE)) {
                Type type = new TypeToken<Map<String, User>>(){}.getType();
                Map<String, User> loadedUsers = gson.fromJson(reader, type);
                if (loadedUsers != null) {
                    users = loadedUsers;
                }
            } catch (IOException e) {
                System.err.println("Error loading users: " + e.getMessage());
            }
        }
    }
}