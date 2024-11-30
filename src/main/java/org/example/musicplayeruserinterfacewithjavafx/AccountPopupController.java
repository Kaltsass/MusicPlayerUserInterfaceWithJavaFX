package org.example.musicplayeruserinterfacewithjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AccountPopupController {

    @FXML
    private Label accountLabel; // Reference to the Label from FXML

    @FXML
    private Button closeButton; // Reference to the Button from FXML

    // This method will be called when the close button is clicked
    @FXML
    private void handleCloseButtonAction() {
        // Close the popup window
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    // This method can be used to set account information dynamically if needed
    public void setAccountInfo(String accountInfo) {
        accountLabel.setText(accountInfo);
    }

    // Initialization method for setting up the UI (if necessary)
    @FXML
    private void initialize() {
        // Example: Setting a default message or account info
        accountLabel.setText("Account Info: User12345");
    }
}
