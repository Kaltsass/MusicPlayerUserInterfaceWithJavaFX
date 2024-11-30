package org.example.musicplayeruserinterfacewithjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;

public class AccountPopupController {

    private HelloController mainController; // Reference to the main controller

    // Method to set the main controller (passed from the main window)
    public void setMainController(HelloController mainController) {
        this.mainController = mainController;}


    // Matching fx:id fields
    @FXML private Label label_username;
    @FXML private TextField tf_newUsername;
    @FXML private Label label_password;
    @FXML private TextField tf_newPassword;
    @FXML private RadioButton rb_favNewSong;
    @FXML private RadioButton rb_favNewSong2;
    @FXML private Button button_saveChanges;
    @FXML private Button button_cancel;
    @FXML
    private Label statusLabel;



    // Initialize method
    @FXML
    private void initialize() {
        // Example initialization code
        label_username.setText("Enter your username:");
        label_password.setText("Enter your password:");

        // Ensure the save button triggers the login process
        button_saveChanges.setOnAction(event -> handleLogin());


    }

    @FXML
    private void handleSaveChanges(ActionEvent event) {
        // Handle saving changes to username and password
        String newUsername = tf_newUsername.getText();
        String newPassword = tf_newPassword.getText();

        // Assuming you are saving the new data to the database or memory
        System.out.println("New Username: " + newUsername);
        System.out.println("New Password: " + newPassword);

        // Close the popup after saving the changes (optional)
        Stage stage = (Stage) tf_newUsername.getScene().getWindow();
        stage.close();

        // Update the account button with the new username
        HelloController helloController = new HelloController();
        helloController.updateAccountButton(newUsername);
    }

    @FXML
    private void handleDatabaseAction() {
        System.out.println("handleDatabaseAction method called."); // Debug log
        try (Connection connection = DataBaseUtilityPleaseWork.getConnection()) {
            if (connection != null) {
                System.out.println("Connected to database in handleDatabaseAction.");
                statusLabel.setText("Connection successful!");

                String query = "SELECT * FROM users";
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(query)) {

                    // Process the result
                    while (resultSet.next()) {
                        String username = resultSet.getString("username");
                        System.out.println("Username from database: " + username);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    statusLabel.setText("Error executing query.");
                    System.out.println("Error executing query: " + e.getMessage());
                }
            } else {
                statusLabel.setText("Connection failed!");
                System.out.println("Database connection failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Error connecting to the database.");
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }


    @FXML
    private void handleLogin() {
        String username = tf_newUsername.getText();
        String password = tf_newPassword.getText();

        // Validate username and password (you can check against the database here)
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            // Assuming login is successful, update the Account button text in the main controller
            mainController.updateAccountButton(username);

            // Close the popup window
            Stage stage = (Stage) button_saveChanges.getScene().getWindow();
            stage.close(); // Close the login popup

            System.out.println("Logging in...");
        } else {
            System.out.println("Invalid credentials!");
            // You can show an alert if login fails
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Username or Password", ButtonType.OK);
            alert.showAndWait();
        }

        // Database interaction to save the user (as per your previous implementation)
        try (Connection connection = DataBaseUtilityPleaseWork.getConnection()) {
            String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    statusLabel.setText("User saved successfully!");

                    // Update the main account button text with the username
                    if (mainController != null) {
                        mainController.updateAccountButton(username);
                    }

                    // Close the popup after login
                    Stage stage = (Stage) button_saveChanges.getScene().getWindow();
                    stage.close();
                } else {
                    statusLabel.setText("Failed to save user.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Database error: " + e.getMessage());
        }
    }


    @FXML
    private void handleCancel() {
        // Close the popup without making changes
        Stage stage = (Stage) button_cancel.getScene().getWindow();
        stage.close();
    }
}
