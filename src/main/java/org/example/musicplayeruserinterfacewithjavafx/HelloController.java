package org.example.musicplayeruserinterfacewithjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Song;

import javafx.event.ActionEvent;   // Import ActionEvent for button click events
import javafx.scene.Parent;       // Import Parent to set the new scene
import javafx.scene.Scene;        // Import Scene to change the current scene
import javafx.stage.Stage;        // Import Stage for window management
import java.io.IOException;       // Import IOException for handling loading errors

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;




public class HelloController implements Initializable {
    @FXML
    private HBox favoriteContainer;

    List<Song> recentlyPlayed;
    List<Song> favorites;


    @FXML
    private HBox recentlyPlayedContainer;


    @FXML
    private Button exploreButton;

    @FXML
    public void initialize() {
        // Optional: Initialize your controller here if needed
    }

    @FXML
    private void handleExploreButtonClick() {
        try {
            // Load the Explore page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ExploreView.fxml"));
            AnchorPane explorePage = loader.load();

            // Create a new Scene for the Explore page
            Scene exploreScene = new Scene(explorePage);

            // Get the current stage (the window)
            Stage stage = (Stage) exploreButton.getScene().getWindow();

            // Set the new scene to the current stage
            stage.setScene(exploreScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button backButton;  // Ensure you have a reference to the Back button in your controller

    @FXML
    private void handleBackButtonClick() {
        try {
            // Load the HelloView.fxml file (the main page)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            AnchorPane helloPage = loader.load();

            // Create a new Scene for the Hello page
            Scene helloScene = new Scene(helloPage);

            // Get the current stage (the window) where the Explore scene is being shown
            Stage stage = (Stage) backButton.getScene().getWindow();  // Using the Back button here

            // Set the new scene to the current stage (back to the Hello page)
            stage.setScene(helloScene);
            stage.show();

        } catch (IOException e) {
            System.out.println(getClass().getResource("hello-view.fxml"));
            e.printStackTrace();
        }
    }








    @FXML
    private void refreshRecentlyPlayed(MouseEvent event) {
        System.out.println("Label clicked! Refreshing the recently played songs.");
        // Your logic to clear and repopulate the recentlyPlayedContainer
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        recentlyPlayed = new ArrayList<>(getRecentlyPlayed());
        favorites=new ArrayList<>(getFavorites());
        try {
            for (Song song : recentlyPlayed) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("song.fxml"));

                VBox vBox = fxmlLoader.load();
                SongController songController = fxmlLoader.getController();
                songController.setData(song);

                recentlyPlayedContainer.getChildren().add(vBox);
            }
            for (Song song : favorites) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("song.fxml"));

                VBox vBox = fxmlLoader.load();
                SongController songController = fxmlLoader.getController();
                songController.setData(song);

                favoriteContainer.getChildren().add(vBox);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private List<Song> getRecentlyPlayed() {
        List<Song> ls = new ArrayList<>();

        Song song = new Song();
        song.setName("I Hronia Mou");
        song.setArtist("Mpelafon");
        song.setCover("/img/Mpelafon.png");
        ls.add(song);

        song = new Song();
        song.setName("Pellegrino");
        song.setArtist("Mikros Kleftis,Dof Twogee");
        song.setCover("/img/mk_pelegrino.png");
        ls.add(song);

        song = new Song();
        song.setName("20/20");
        song.setArtist("Mikros Kleftis,LEX");
        song.setCover("/img/mk_20-20.png");
        ls.add(song);

        song = new Song();
        song.setName("Smooth Kai Hardcorila");
        song.setArtist("Thitis,Sadomas,ΔΠΘ,Buzz,MadnessKey");
        song.setCover("/img/thitis_sado.png");
        ls.add(song);

        song = new Song();
        song.setName("Top Boys");
        song.setArtist("Sadam,LEX,Dof Twogee");
        song.setCover("/img/sadam.png");
        ls.add(song);


        song = new Song();
        song.setName("Gioconda");
        song.setArtist("Immune");
        song.setCover("/img/Gioconda-Immune.png");
        ls.add(song);


        return ls;
    }

    public List<Song> getFavorites() {
        List<Song> ls = new ArrayList<>();
        Song song = new Song();
        song.setName("Top 50");
        song.setArtist("Global");
        song.setCover("/img/Top50Charts.png");
        ls.add(song);

        song = new Song();
        song.setName("Top 50");
        song.setArtist("Podcast");
        song.setCover("/img/Podcast.png");
        ls.add(song);

        song = new Song();
        song.setName("Top 50");
        song.setArtist("Albums");
        song.setCover("/img/TopAlbums.png");
        ls.add(song);

        song = new Song();
        song.setName("Top 50");
        song.setArtist("Artists");
        song.setCover("/img/TopArtists1.png");
        ls.add(song);
        song = new Song();
        song.setName("Top 50");
        song.setArtist("Playlist");
        song.setCover("/img/TopPlaylist.png");
        ls.add(song);

        return ls;
    }

}