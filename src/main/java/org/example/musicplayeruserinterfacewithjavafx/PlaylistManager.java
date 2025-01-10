package org.example.musicplayeruserinterfacewithjavafx;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class PlaylistManager {
    // Αρχείο για τα playlists
    private static final String PLAYLISTS_FILE = "playlists.txt";
    // Αρχείο για τα playlists
    private ObservableList<String> playlistItems;
    // Φάκελος όπου θ αποθηκευονται τα playlists
    private static final String PLAYLIST_DIRECTORY = "playlists/";
    private final Gson gson = new Gson();



    public PlaylistManager() {

        File directory = new File(PLAYLIST_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir(); // Δημιουργία του φακέλου αν δεν υπάρχει
        }

    }

    // Μέθοδος για να ελέγξουμε αν το playlist υπάρχει ήδη στο αρχείο
    public boolean isPlaylistExist(String playlistName) {

        //return playlistItems.contains(playlistName);
        File playlistFile = new File(PLAYLIST_DIRECTORY + playlistName + ".txt");
        return playlistFile.exists();
    }

    public void createPlaylistFile(String playlistName) {
        try {
            File playlistFile = new File(PLAYLIST_DIRECTORY + playlistName + ".txt");
            if (playlistFile.createNewFile()) {
                System.out.println("Playlist created: " + playlistFile.getName());
            } else {
                System.out.println("Playlist already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteSongFromPlaylist(String playlistName, String songInfo) {
        File playlistFile = new File(PLAYLIST_DIRECTORY + playlistName + ".txt");
        if (!playlistFile.exists()) {
            System.out.println("Playlist file does not exist: " + playlistFile.getAbsolutePath());
            return;
        }

        try {
            // Φόρτωση όλων των γραμμών από το αρχείο
            List<String> lines = Files.readAllLines(playlistFile.toPath());

            // Φιλτράρισμα των γραμμών για να αφαιρεθεί το τραγούδι
            List<String> updatedLines = new ArrayList<>();
            for (String line : lines) {
                if (!line.contains(songInfo)) {
                    updatedLines.add(line);
                }
            }

            // Εγγραφή των ενημερωμένων γραμμών πίσω στο αρχείο
            Files.write(playlistFile.toPath(), updatedLines);
            System.out.println("Updated playlist file: " + playlistName);
        } catch (IOException e) {
            System.out.println("Error updating playlist file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String formatSong(Song song) {
        return song.getName() + "|" + song.getArtist() + "|" + song.getCover() + "|" + song.getPreviewUrl();
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


    // Αποθήκευση τραγουδιού στο playlist ως διαχωρισμένα δεδομένα
    public void addSongToPlaylist(String playlistName, Song song) {
        File playlistFile = new File(PLAYLIST_DIRECTORY + playlistName + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(playlistFile, true))) {
            String songData = song.getName() + "|" + song.getArtist() + "|" + song.getCover() + "|" + song.getPreviewUrl();
            writer.write(songData);
            writer.newLine();
            System.out.println("Saved song: " + songData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Φόρτωση τραγουδιών από το playlist
    public List<Song> loadSongsFromPlaylist(String playlistName) {
        File playlistFile = new File(PLAYLIST_DIRECTORY + playlistName + ".txt");
        List<Song> songs = new ArrayList<>();

        if (!playlistFile.exists()) {
            System.out.println("Playlist file does not exist: " + playlistName);
            return songs;
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(playlistFile.getAbsolutePath()));
            System.out.println("Lines read from playlist file: " + lines.size());

            for (String line : lines) {
                try {
                    if (line.contains("|")) { // Διαχωρισμένα δεδομένα με "|"
                        System.out.println("Processing line: " + line);
                        String[] parts = line.split("\\|");
                        if (parts.length == 4) {
                            String name = parts[0];
                            String artist = parts[1];
                            String cover = parts[2];
                            String previewUrl = parts[3];
                            songs.add(new Song(name, artist, previewUrl, cover));
                            System.out.println("Song added: " + name + " - " + artist);
                        } else {
                            System.out.println("Invalid line format: " + line);
                        }
                    } else if (line.contains(" - ")) { // Διαχωρισμένα δεδομένα με " - "
                        System.out.println("Processing simplified line: " + line);
                        String[] parts = line.split(" - ");
                        if (parts.length == 2) {
                            String name = parts[0];
                            String artist = parts[1];
                            songs.add(new Song(name, artist, "N/A", "N/A")); // Προσθέτουμε placeholders για URLs
                            System.out.println("Song added with placeholders: " + name + " - " + artist);
                        } else {
                            System.out.println("Invalid line format: " + line);
                        }
                    } else {
                        System.out.println("Skipping unsupported line: " + line);
                    }
                } catch (Exception e) {
                    System.out.println("Error processing line: " + line);
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return songs;
    }


    public void saveSongsToPlaylist(String playlistName, List<Song> songs) {
        File playlistFile = new File("playlists/" + playlistName + ".txt");
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(playlistFile.getAbsolutePath()))) {
            Gson gson = new Gson();
            for (Song song : songs) {
                writer.write(gson.toJson(song));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}