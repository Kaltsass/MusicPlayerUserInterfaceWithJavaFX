package org.example.musicplayeruserinterfacewithjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistManager {
    // Αρχείο για τα playlists
    private static final String PLAYLISTS_FILE = "playlists.txt";
    // Αρχείο για τα playlists
    private ObservableList<String> playlistItems;

    public PlaylistManager() {
        // Αρχικοποίηση του ObservableList
        playlistItems = FXCollections.observableArrayList(loadPlaylistsFromFile());
    }

    // Μέθοδος για να φορτώσουμε όλα τα playlists από το αρχείο
    public List<String> loadPlaylistsFromFile() {
        List<String> playlists = new ArrayList<>();
        File file = new File(getPlaylistsFilePath());

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(getPlaylistsFilePath()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        playlists.add(line.trim());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return playlists;
    }

    // Μέθοδος για να ελέγξουμε αν το playlist υπάρχει ήδη στο αρχείο
    public boolean isPlaylistExist(String playlistName) {

        return playlistItems.contains(playlistName);
    }

    // Μέθοδος για να προσθέσουμε νέο playlist στο αρχείο
    public void addPlaylist(String playlistName) {
        if (playlistName == null || playlistName.trim().isEmpty()) {
            System.out.println("Cannot add an empty playlist name!");
            return; // Don't add an empty or null playlist name
        }

        if (!isPlaylistExist(playlistName)) {
            playlistItems.add(playlistName);
            savePlaylistsToFile(); // Save the playlists to the file
        } else {
            System.out.println("Playlist already exists!");
        }
    }

    // Μέθοδος για να αποθηκεύσουμε την τρέχουσα λίστα playlists στο αρχείο
    public void savePlaylistsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PLAYLISTS_FILE))) {
            for (String playlist : playlistItems) {
                writer.write(playlist);
                writer.newLine(); // Καταχώρηση κάθε playlist σε νέα γραμμή
            }
            System.out.println("Playlists saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected String getPlaylistsFilePath() {
        return PLAYLISTS_FILE;
    }

    // Getter for playlist items
    public ObservableList<String> getPlaylists() {
        return playlistItems;
    }
}