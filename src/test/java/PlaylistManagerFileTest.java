import org.example.musicplayeruserinterfacewithjavafx.PlaylistManager;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistManagerFileTest {

    private static final String TEST_FILE = "playlists.txt";
    private PlaylistManager playlistManager;

    @BeforeEach
    public void setUp() {
        playlistManager = new PlaylistManager() {
            @Override
            protected String getPlaylistsFilePath() {
                return TEST_FILE;
            }
        };
        File testFile = new File(TEST_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @AfterEach
    public void tearDown() {
        File testFile = new File(TEST_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testFileCreatedOnFirstAdd() {
        File file = new File(TEST_FILE);
        assertFalse(file.exists(), "Playlist file should not exist initially");

        playlistManager.addPlaylist("Indie Vibes");

        assertTrue(file.exists(), "Playlist file should be created after adding the first playlist");
    }

    @Test
    public void testLoadPlaylistsFromEmptyFile() {
        File file = new File(TEST_FILE);
        try {
            assertTrue(file.createNewFile(), "Test file should be created");
        } catch (Exception e) {
            fail("Failed to create the test file: " + e.getMessage());
        }

        assertTrue(playlistManager.getPlaylists().isEmpty(), "Loaded playlist should be empty");
    }
}
