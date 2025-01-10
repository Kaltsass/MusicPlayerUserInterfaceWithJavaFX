package org.example.musicplayeruserinterfacewithjavafx;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class PlaylistViewController {


    @FXML
    private Button closeButton;

    @FXML
    private Button deleteplaylist;

    @FXML
    private Label playlistTitleLabel;

    @FXML
    private ListView<HBox> playlistContentsListView;

    @FXML
    private ListView<Label> playlistListView;

    private final PlaylistManager playlistManager = new PlaylistManager();



    @FXML
    void OnBtnClickClose(ActionEvent event) {

        System.out.println("Pressed"); // Βγάζει μήνυμα στο τερματικό ότι πατήθηκε το κουμπί - να το σβήσουμε μετά
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    void onDeletePlaylistButtonClick(ActionEvent event) {

        // Έλεγχος αν υπάρχουν επιλεγμένα στοιχεία
        if (playlistContentsListView.getSelectionModel().getSelectedItems().isEmpty()) {
            System.out.println("No items selected for deletion.");
            return;
        }

        // Απόκτηση του επιλεγμένου playlist από τον τίτλο
        String playlistName = playlistTitleLabel.getText().replace("Playlist: ", "").trim();
        if (playlistName.isEmpty()) {
            System.out.println("Playlist name is empty.");
            return;
        }

        // Απόκτηση των επιλεγμένων στοιχείων
        List<HBox> selectedItems = playlistContentsListView.getSelectionModel().getSelectedItems();

        // Διαγραφή των στοιχείων από το ListView και το αρχείο
        for (HBox item : selectedItems) {
            Label label = (Label) item.getChildren().get(1); // Υποθέτουμε ότι ο Label είναι το δεύτερο στοιχείο
            String songInfo = label.getText(); // Τίτλος και καλλιτέχνης, π.χ., "Song Title - Artist"

            playlistContentsListView.getItems().remove(item); // Αφαίρεση από το ListView

            // Κλήση μεθόδου στο PlaylistManager για διαγραφή από το αρχείο
            playlistManager.deleteSongFromPlaylist(playlistName, songInfo);
            System.out.println("Deleted song: " + songInfo);
        }

    }

    public void setPlaylistName(String playlistName) {
        System.out.println("Setting playlist name to: " + playlistName);

        // Ενημέρωση τίτλου
        playlistTitleLabel.setText("Playlist: " + playlistName);

        // Ανάγνωση τραγουδιών από το playlist αρχείο
        String playlistFilePath = "playlists/" + playlistName + ".txt";
        playlistContentsListView.getItems().clear();

        try {
            List<String> lines = Files.readAllLines(Paths.get(playlistFilePath));

            for (String line : lines) {
                // Διαχωρισμός της γραμμής σε μέρη
                String[] parts = line.split(" - ");
                if (parts.length >= 4) {
                    String title = parts[0];
                    String artist = parts[1];
                    String mp3Url = parts[2];
                    String coverUrl = parts[3];

                    // Δημιουργία στοιχείου λίστας
                    HBox hBox = new HBox(10); // 10 είναι το spacing
                    ImageView imageView = new ImageView(new Image(coverUrl));
                    imageView.setFitHeight(50); // Ύψος εικόνας
                    imageView.setFitWidth(50);  // Πλάτος εικόνας

                    Label label = new Label(title + " - " + artist);

                    hBox.getChildren().addAll(imageView, label);
                    playlistContentsListView.getItems().add(hBox);

                    System.out.println("Added song: " + title + " by " + artist);
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading playlist file: " + e.getMessage());
        }
    }


}
