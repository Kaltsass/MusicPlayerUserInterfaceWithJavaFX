package org.example.musicplayeruserinterfacewithjavafx;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.Song;

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
    private ListView<Label> artistSongsListView;

    @FXML
    private Button closeButton;

    @FXML
    private Label followersLabel;

    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton;

    private HelloController helloController; // Controller για την αναπαραγωγή

    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }

    @FXML
    void OnBtnClickClose(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onSearchButtonClick(ActionEvent event) {
        String artistName = searchBar.getText();
        try {
            // Ανάκτηση δεδομένων από το API
            String artistImage = APIArtistInformation.fetchArtistImageUrl(artistName);
            String artistNameFetched = APIArtistInformation.fetchArtistName(artistName);
            String artistBio = APIArtistInformation.fetchArtistInfo(artistName);
            JsonArray tracksArray = APIDeezerInformation.fetchArtistTracks(artistName);
            String followers = APIDeezerInformation.fetchArtistFollowers(artistName);
            // Ενημέρωση UI
            artistNameLabel.setText(artistNameFetched);
            artistInfoTextFlow.getChildren().clear();
            artistInfoTextFlow.getChildren().add(new Text(artistBio));
            artistSongsListView.getItems().clear();

            // Ενημέρωση λίστας τραγουδιών
            for (int i = 0; i < tracksArray.size(); i++) {
                JsonObject track = tracksArray.get(i).getAsJsonObject();
                String songTitle = track.get("title").getAsString();
                String previewUrl = track.get("preview").getAsString();
                String coverUrl = track.getAsJsonObject("album").get("cover_small").getAsString();

                // Δημιουργία τραγουδιού
                Song song = new Song();
                song.setName(songTitle);
                song.setArtist(artistName);
                song.setPreviewUrl(previewUrl);
                song.setCover(coverUrl);

                // Δημιουργία Label για το τραγούδι
                Label resultLabel = new Label((i + 1) + ". " + songTitle);
                resultLabel.setStyle("-fx-text-fill: white;");

                if (coverUrl != null && !coverUrl.isEmpty()) {
                    try {
                        Image albumCoverImage = new Image(coverUrl);
                        ImageView imageView = new ImageView(albumCoverImage);
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        resultLabel.setGraphic(imageView);
                    } catch (Exception e) {
                        System.out.println("Error creating ImageView for song cover: " + e.getMessage());
                    }
                }

                // Προσθήκη συμβάντος στο Label
                resultLabel.setOnMouseClicked(event1 -> playSong(song));

                artistSongsListView.getItems().add(resultLabel);
            }

            // Ενημέρωση followers
            if (!followers.equals("Δεν βρέθηκαν followers.") && !followers.contains("Error")) {
                try {
                    int followersCount = Integer.parseInt(followers);
                    String formattedFollowers = String.format("%,d", followersCount);
                    Platform.runLater(() -> followersLabel.setText(formattedFollowers));
                } catch (NumberFormatException e) {
                    Platform.runLater(() -> followersLabel.setText("Δεν βρέθηκαν followers.."));
                    e.printStackTrace();
                }
            } else {
                Platform.runLater(() -> followersLabel.setText(followers));
            }

            // Ενημέρωση εικόνας καλλιτέχνη
            if (artistImage != null) {
                artistImageView.setImage(new Image(artistImage));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playSong(Song song) {
        if (helloController != null) {
            helloController.playSong(song);  // Καλεί τη μέθοδο playSong του HelloController
        } else {
            System.out.println("HelloController is null, cannot play song.");
        }
    }
}
