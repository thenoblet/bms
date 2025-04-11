package ui.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Main application class for the banking system GUI.
 * Bootstraps the JavaFX application and loads the initial welcome view.
 *
 * This class serves as the entry point for the JavaFX application and handles:
 * - Application startup configuration
 * - Initial FXML view loading
 * - Primary stage setup
 */
public class BankApplication extends Application {

    /**
     * The main entry point for the JavaFX application.
     *
     * @param stage The primary stage for this application
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("FXML Resource: " + getClass().getResource("/gtpbms/view/welcomeview.fxml"));

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gtpbms/view/welcomeview.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            System.out.println("Application started successfully");
        } catch (Exception e) {
            System.out.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * The main method that launches the application.
     *
     * @param args Command line arguments (not currently used)
     */
    public static void main(String[] args) {
        launch();
    }
}