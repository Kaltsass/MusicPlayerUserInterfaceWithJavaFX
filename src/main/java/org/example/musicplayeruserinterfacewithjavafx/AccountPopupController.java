package org.example.musicplayeruserinterfacewithjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class AccountPopupController {

    // Matching fx:id fields
    @FXML private Label label_username;
    @FXML private TextField tf_newUsername;
    @FXML private Label label_password;
    @FXML private TextField tf_newPassword;
    @FXML private RadioButton rb_favNewSong;
    @FXML private RadioButton rb_favNewSong2;
    @FXML private Button button_saveChanges;
    @FXML private Button button_cancel;

    // Initialize method
    @FXML
    private void initialize() {
        // Example initialization code
        label_username.setText("Enter your username:");
        label_password.setText("Enter your password:");

        button_saveChanges.setOnAction(event -> saveChanges());
        button_cancel.setOnAction(event -> cancelChanges());
    }

    // Method for saving changes
    private void saveChanges() {
        // Logic for saving changes (could update UI or save data)
        System.out.println("Logging in");
    }

    // Method for cancelling
    private void cancelChanges() {
        // Logic for canceling (e.g., close the window)
        System.out.println("Closing");
    }
}
