import org.example.musicplayeruserinterfacewithjavafx.PlaylistManager;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistManagerTest {

    private static final String TEST_FILE = "playlists.txt"; // Test-specific file
    private PlaylistManager playlistManager;

    @BeforeEach
    public void setUp() {
        playlistManager = new PlaylistManager() {
            @Override
            protected String getPlaylistsFilePath() {
                return TEST_FILE; // Use test-specific file path
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

        // Check if the file exists
        File file = new File(TEST_FILE);
        assertTrue(file.exists(), "Playlist file should exist");

        try {
            // Check if the file contains the playlist name
            String fileContent = Files.readString(file.toPath());
            assertTrue(fileContent.contains("Rock Classics"), "File should contain the playlist");
        } catch (Exception e) {
            fail("Failed to read the playlist file: " + e.getMessage());
        }

        // Print debug info if the file does not exist
        if (!file.exists()) {
            System.out.println("File does not exist! Check file path and permissions.");
        }
    }
}



//The purpose of this test is to verify the functionality of adding a new playlist to a file (playlists.txt)