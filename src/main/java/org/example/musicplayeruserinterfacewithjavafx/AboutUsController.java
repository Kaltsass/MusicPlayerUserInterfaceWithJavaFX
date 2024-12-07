package org.example.musicplayeruserinterfacewithjavafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

import java.awt.Desktop;
import java.net.URI;

public class AboutUsController {

    @FXML
    private VBox mainVBox; // Reference to the VBox defined in FXML
    @FXML
    private ImageView discordIcon;   // Discord icon
    @FXML
    private ImageView githubIcon;    // GitHub icon
    @FXML
    private ImageView facebookIcon;  // Facebook icon


    @FXML
    private ImageView instagramIcon; // Reference to the Instagram icon

    public void initialize() {
        // Dynamically set the padding for main VBox
        if (mainVBox != null) {
            mainVBox.setPadding(new Insets(20, 20, 20, 20));
        }

        // Set a click event listener on the Instagram icon
        if (instagramIcon != null) {
            instagramIcon.setOnMouseClicked(event -> openInstagram());

        }
        if (discordIcon != null) {
            discordIcon.setOnMouseClicked(event -> openDiscord());
        }
        if (githubIcon != null) {
            githubIcon.setOnMouseClicked(event -> openGithub());
        }
        if (facebookIcon != null) {
            facebookIcon.setOnMouseClicked(event -> openFacebook());
        }
    }

    private void openFacebook() {
        try {
            // Open the Facebook link in the default browser
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("https://www.facebook.com"));
            } else {
                System.out.println("Desktop browsing is not supported on this system.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openDiscord() {
        try {
            // Open the Discord link in the default browser
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("https://discord.com"));
            } else {
                System.out.println("Desktop browsing is not supported on this system.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openGithub() {
        try {
            // Open the GitHub link in the default browser
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("https://github.com"));
            } else {
                System.out.println("Desktop browsing is not supported on this system.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openInstagram() {
        try {
            // Open the Instagram link in the default browser
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("https://www.instagram.com"));
            } else {
                System.out.println("Desktop browsing is not supported on this system.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAboutWindow() {
        try {
            // Load the AboutUsView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/musicplayeruserinterfacewithjavafx/AboutUsView.fxml"));

            // Load the scene from the FXML
            Scene scene = new Scene(loader.load());

            // Create a popup stage
            Stage popupStage = new Stage();
            popupStage.setTitle("About Us");
            popupStage.initModality(Modality.APPLICATION_MODAL); // Makes it modal
            popupStage.setResizable(true);

            // Set the scene and show the popup window
            popupStage.setScene(scene);
            popupStage.setWidth(900); // Set popup window's width
            popupStage.setHeight(900); // Set popup window's height
            popupStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
