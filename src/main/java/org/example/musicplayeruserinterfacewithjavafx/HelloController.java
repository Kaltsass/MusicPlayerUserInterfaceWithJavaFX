package org.example.musicplayeruserinterfacewithjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Song;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.ListView;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class HelloController implements Initializable {
    @FXML
    private HBox favoriteContainer;

    @FXML
    private Label likedSongsLabel;
    @FXML
    private ListView<String> likedSongsListView;


    // Method that handles what happens when the "Liked Songs" label is clicked
    @FXML
    private void showLikedSongs() {
        //   Logic to display liked songs
        System.out.println("Liked Songs");
        // Here, you could load a list of liked songs, switch to a different pane, or display relevant data.
        // List of liked songs
        ObservableList<String> likedSongs = FXCollections.observableArrayList(
                "Song 1 - Artist A", "Song 2 - Artist B", "Song 3 - Artist C"
        );

        likedSongsListView.setItems(likedSongs);
    }

    List<Song> recentlyPlayed;
     List<Song> favorites;
    // This will be called on initialization

    private List<Song> getRecentlyPlayed(){
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}