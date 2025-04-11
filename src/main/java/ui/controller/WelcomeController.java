package ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller for the welcome screen of the banking application.
 * Handles navigation to the account creation screen.
 */
public class WelcomeController {

    @FXML private Button startBankingButton;

    /**
     * Handles the action when the start banking button is clicked.
     * Loads and displays the account creation screen.
     *
     * @param event The action event triggered by the button click
     */
    @FXML
    void handleStartBanking(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gtpbms/view/createaccountview.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            handleViewLoadingError("Error loading create account view: ", e);
        }
    }

    /**
     * Handles errors that occur during view loading.
     *
     * @param errorMessage The base error message
     * @param e The exception that occurred
     */
    private void handleViewLoadingError(String errorMessage, IOException e) {
        System.err.println(errorMessage + e.getMessage());
        e.printStackTrace();
    }
}