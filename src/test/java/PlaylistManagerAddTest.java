import org.example.musicplayeruserinterfacewithjavafx.PlaylistManager;
import org.junit.jupiter.api.*;
import javafx.collections.ObservableList;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistManagerAddTest {

    private static final String TEST_FILE = "test_playlists.txt"; // Test-specific file
    private PlaylistManager playlistManager;

    @BeforeEach
    public void setUp() {
        playlistManager = new PlaylistManager() {
            @Override
            protected String getPlaylistsFilePath() {
                return TEST_FILE; // Override to use test-specific file
            }
        };

        // Clean up the test file before each test
        File testFile = new File(TEST_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @AfterEach
    public void tearDown() {
        // Clean up the test file after each test
        File testFile = new File(TEST_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testAddPlaylist() {
        // Add a playlist
        playlistManager.addPlaylist("Rock Classics");

        // Verify the playlist exists in memory
        ObservableList<String> playlists = playlistManager.getPlaylists();
        assertTrue(playlists.contains("Rock Classics"));

        // Verify the playlist is saved to the file
        File file = new File(TEST_FILE);
        assertTrue(file.exists(), "Playlist file should exist");

        // Verify that the file contains the correct playlist
        assertTrue(file.exists(), "Playlist file should exist");
    }
}
