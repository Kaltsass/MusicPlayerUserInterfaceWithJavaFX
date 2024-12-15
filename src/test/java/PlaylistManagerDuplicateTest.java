import org.example.musicplayeruserinterfacewithjavafx.PlaylistManager;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
//WORKS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
public class PlaylistManagerDuplicateTest {

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
    public void testPreventDuplicatePlaylist() {
        playlistManager.addPlaylist("Rock Classics");
        playlistManager.addPlaylist("Rock Classics");

        // Assert that "Rock Classics" appears only once in the list
        assertEquals(1, playlistManager.getPlaylists().stream()
                .filter(playlist -> playlist.equals("Rock Classics")).count());
    }
}
