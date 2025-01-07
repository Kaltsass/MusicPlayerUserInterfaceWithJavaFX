import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.example.musicplayeruserinterfacewithjavafx.MediaPlayerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MediaPlayerManagerTest {

    private MediaPlayerManager mediaPlayerManager;
    private MediaPlayer mediaPlayer;

    @BeforeEach
    void setUp() {
        // Δημιουργία του MediaPlayerManager και του πραγματικού MediaPlayer
        mediaPlayerManager = new MediaPlayerManager();
    }

    @Test
    void testPlaySong_whenMediaPlayerIsNull() {
        // Δημιουργία ενός preview URL (χρησιμοποιούμε ένα παράδειγμα URL)
        String previewUrl = "https://www.example.com/song.mp3";

        // Ρύθμιση του MediaPlayerManager με τον πραγματικό MediaPlayer
        mediaPlayer = new MediaPlayer(new Media(previewUrl));
        mediaPlayerManager.setMediaPlayer(mediaPlayer);

        // Καλούμε τη μέθοδο playSong
        mediaPlayerManager.playSong(previewUrl);

        // Ελέγχουμε αν το MediaPlayer είναι σε κατάσταση αναπαραγωγής
        assertEquals(MediaPlayer.Status.PLAYING, mediaPlayer.getStatus());
    }

    @Test
    void testPauseSong_whenMediaPlayerIsNotNull() {
        // Δημιουργία ενός preview URL και ρύθμιση του MediaPlayerManager
        String previewUrl = "https://www.example.com/song.mp3";
        mediaPlayer = new MediaPlayer(new Media(previewUrl));
        mediaPlayerManager.setMediaPlayer(mediaPlayer);

        // Καλούμε τη μέθοδο playSong για να ξεκινήσει το τραγούδι
        mediaPlayerManager.playSong(previewUrl);

        // Καλούμε την μέθοδο pauseSong
        mediaPlayerManager.pauseSong();

        // Ελέγχουμε αν το MediaPlayer είναι σε κατάσταση παύσης
        assertEquals(MediaPlayer.Status.PAUSED, mediaPlayer.getStatus());
    }

    @Test
    void testStopSong_whenMediaPlayerIsNotNull() {
        // Δημιουργία ενός preview URL και ρύθμιση του MediaPlayerManager
        String previewUrl = "https://www.example.com/song.mp3";
        mediaPlayer = new MediaPlayer(new Media(previewUrl));
        mediaPlayerManager.setMediaPlayer(mediaPlayer);

        // Καλούμε τη μέθοδο playSong για να ξεκινήσει το τραγούδι
        mediaPlayerManager.playSong(previewUrl);

        // Καλούμε την μέθοδο stopSong
        mediaPlayerManager.stopSong();

        // Ελέγχουμε αν το MediaPlayer είναι σε κατάσταση σταμάτημα
        assertEquals(MediaPlayer.Status.STOPPED, mediaPlayer.getStatus());
    }

    @Test
    void testIsPlaying_whenMediaPlayerIsPlaying() {
        // Δημιουργία ενός preview URL και ρύθμιση του MediaPlayerManager
        String previewUrl = "https://www.example.com/song.mp3";
        mediaPlayer = new MediaPlayer(new Media(previewUrl));
        mediaPlayerManager.setMediaPlayer(mediaPlayer);

        // Καλούμε τη μέθοδο playSong για να ξεκινήσει το τραγούδι
        mediaPlayerManager.playSong(previewUrl);

        // Ελέγχουμε αν το τραγούδι παίζει
        assertTrue(mediaPlayerManager.isPlaying());
    }

    @Test
    void testIsPlaying_whenMediaPlayerIsNotPlaying() {
        // Δημιουργία ενός preview URL και ρύθμιση του MediaPlayerManager
        String previewUrl = "https://www.example.com/song.mp3";
        mediaPlayer = new MediaPlayer(new Media(previewUrl));
        mediaPlayerManager.setMediaPlayer(mediaPlayer);

        // Ελέγχουμε αν το τραγούδι δεν παίζει
        assertFalse(mediaPlayerManager.isPlaying());
    }
}
