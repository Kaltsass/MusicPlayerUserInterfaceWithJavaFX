package org.example.musicplayeruserinterfacewithjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

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

        // Optional: You can leave the cancel button action as is or change it if needed
        button_cancel.setOnAction(event -> cancelChanges());
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
        System.out.println("handleLogin method called."); // Debug log
        String username = tf_newUsername.getText();
        String password = tf_newPassword.getText();
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Username or password cannot be empty.");
            return;
        }

        // Save the username and password to the database
        try (Connection connection = DataBaseUtilityPleaseWork.getConnection()) {
            String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, username);  // Bind the username to the first placeholder
                preparedStatement.setString(2, password);  // Bind the password to the second placeholder

                int rowsInserted = preparedStatement.executeUpdate(); // Execute the update
                if (rowsInserted > 0) {
                    statusLabel.setText("User saved successfully!");
                } else {
                    statusLabel.setText("Failed to save user.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Database error: " + e.getMessage());
        }
    }



    // Method for cancelling
    private void cancelChanges() {
        // Logic for canceling (e.g., close the window)
        System.out.println("Closing");
    }
}
