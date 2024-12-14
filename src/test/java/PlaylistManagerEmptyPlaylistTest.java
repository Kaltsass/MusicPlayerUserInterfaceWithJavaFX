import org.example.musicplayeruserinterfacewithjavafx.PlaylistManager;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistManagerEmptyPlaylistTest {

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
    public void testAddEmptyPlaylist() {
        playlistManager.addPlaylist(""); // Attempt to add an empty playlist

        // Verify that an empty playlist is not added
        assertFalse(playlistManager.getPlaylists().contains(""), "Empty playlist should not be added");
    }
}
