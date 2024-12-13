import org.example.musicplayeruserinterfacewithjavafx.PlaylistManager;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistFilePersistenceTest {

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
    public void testSavePlaylistsToFile() throws IOException {
        playlistManager.addPlaylist("Rock Classics");
        playlistManager.addPlaylist("Chill Vibes");

        // Verify the file is created
        File file = new File(TEST_FILE);
        assertTrue(file.exists(), "Playlist file should exist");

        // Verify the content of the file
        List<String> lines = Files.readAllLines(file.toPath());
        assertEquals(2, lines.size(), "File should contain two playlists");
        assertTrue(lines.contains("Rock Classics"), "File should contain 'Rock Classics'");
        assertTrue(lines.contains("Chill Vibes"), "File should contain 'Chill Vibes'");
    }
}
