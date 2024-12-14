import org.example.musicplayeruserinterfacewithjavafx.PlaylistManager;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistManagerInvalidPlaylistTest {

    private static final String TEST_FILE = "playlists.txt"; // Test-specific file
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
    public void testAddInvalidPlaylists() {
        // Add invalid playlists
        playlistManager.addPlaylist(null);
        playlistManager.addPlaylist("");
        playlistManager.addPlaylist("    "); // Whitespace-only

        // Verify none of these are added
        assertFalse(playlistManager.getPlaylists().contains(null), "Null playlist should not be added");
        assertFalse(playlistManager.getPlaylists().contains(""), "Empty playlist should not be added");
        assertFalse(playlistManager.getPlaylists().contains("    "), "Whitespace-only playlist should not be added");

        // Verify that the file is not created or remains empty
        File file = new File(TEST_FILE);
        if (file.exists()) {
            assertTrue(file.length() == 0, "File should remain empty for invalid playlists");
        }
    }
}
