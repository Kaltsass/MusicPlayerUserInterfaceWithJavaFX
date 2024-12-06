package org.example.musicplayeruserinterfacewithjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ArtistInformationController {

    @FXML
    private ImageView artistImageView;

    @FXML
    private TextFlow artistInfoTextFlow;

    @FXML
    private Label artistNameLabel;

    @FXML
    private ListView<String> artistSongsListView;

    @FXML
    private Button closeButton;

    @FXML
    private Label followersLabel;

    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton;

    @FXML
    void OnBtnClickClose(ActionEvent event) {
        System.out.println("Pressed"); // Βγάζει μήνυμα στο τερματικό ότι πατήθηκε το κουμπί - να το σβήσουμε μετά
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onSearchButtonClick(ActionEvent event) {
        String artistName = searchBar.getText();
        try {
            // Ανακτούμε τις πληροφορίες του καλλιτέχνη από τις APIs
            String artistImage = APIArtistInformation.fetchArtistImageUrl(artistName);
            String artistNameFetched = APIArtistInformation.fetchArtistName(artistName);
            String artistBio = APIArtistInformation.fetchArtistInfo(artistName);
            List<String> songs = APIDeezerInformation.fetchArtistSongs(artistName);
            String followers = APIDeezerInformation.fetchArtistFollowers(artistName);

            // Ενημερώνουμε τα UI στοιχεία με τα δεδομένα
            artistNameLabel.setText(artistNameFetched);
            artistInfoTextFlow.getChildren().clear();
            artistInfoTextFlow.getChildren().add(new Text(artistBio));

            // Ενημερώνουμε τη λίστα με τα τραγούδια
            artistSongsListView.getItems().clear();
            artistSongsListView.getItems().addAll(songs);

            // Ενημερώνουμε τον αριθμό των followers
            followersLabel.setText(followers);

            // Ενημερώνουμε την εικόνα του καλλιτέχνη
            if (artistImage != null) {
                artistImageView.setImage(new Image(artistImage));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
