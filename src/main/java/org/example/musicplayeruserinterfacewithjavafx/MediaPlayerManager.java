package org.example.musicplayeruserinterfacewithjavafx;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaPlayerManager {

    private MediaPlayer mediaPlayer;

    // Constructor για την MediaPlayerManager
    public MediaPlayerManager() {
        this.mediaPlayer = null; // Εγκατάσταση σε null αρχικά
    }

    // Μέθοδος για αναπαραγωγή τραγουδιού
    public void playSong(String previewUrl) {
        // Δημιουργούμε ένα αντικείμενο Media με το URL του preview τραγουδιού
        Media media = new Media(previewUrl);

        // Αν υπάρχει ήδη MediaPlayer, το σταματάμε πριν δημιουργήσουμε νέο
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        // Δημιουργούμε νέο MediaPlayer με το νέο τραγούδι
        mediaPlayer = new MediaPlayer(media);

        // Ρύθμιση της ποιότητας και της έντασης
        mediaPlayer.setVolume(1.0);  // Μέγιστη ένταση ήχου
        mediaPlayer.setRate(1.0);    // Κανονική ταχύτητα αναπαραγωγής

        // Σύνδεση ενός listener για να παρακολουθήσουμε την πρόοδο του buffering
        mediaPlayer.setOnReady(() -> {
            // Εδώ μπορείς να προσθέσεις κάποιο UI feedback (π.χ., δείκτη φόρτωσης)
            System.out.println("Song is ready to play!");
            mediaPlayer.play();  // Ξεκινάμε την αναπαραγωγή όταν το buffering είναι έτοιμο
        });

        mediaPlayer.setOnError(() -> {
            System.out.println("Error occurred: " + mediaPlayer.getError());
        });

        // Αν είναι δυνατό, ξεκινάμε την αναπαραγωγή μόλις το mediaPlayer είναι έτοιμο
        mediaPlayer.play();
    }

    // Μέθοδος για παύση του τραγουδιού
    public void pauseSong() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    // Μέθοδος για διακοπή του τραγουδιού
    public void stopSong() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    // Έλεγχος αν το τραγούδι παίζει
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }
}
