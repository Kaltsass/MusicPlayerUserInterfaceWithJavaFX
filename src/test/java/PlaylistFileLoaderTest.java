import org.example.musicplayeruserinterfacewithjavafx.PlaylistManager;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistFileLoaderTest {

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

        // Ensure a clean slate before each test
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
    public void testLoadPlaylistsFromFile() throws IOException {
        // Prepare a file with predefined playlists
        Files.writeString(new File(TEST_FILE).toPath(), "Rock Classics\nChill Vibes\n");

        // Load playlists from the file
        List<String> playlists = playlistManager.loadPlaylistsFromFile();

        // Verify the loaded playlists
        assertEquals(2, playlists.size(), "The file should contain two playlists");
        assertTrue(playlists.contains("Rock Classics"), "Playlists should include 'Rock Classics'");
        assertTrue(playlists.contains("Chill Vibes"), "Playlists should include 'Chill Vibes'");
    }
}
