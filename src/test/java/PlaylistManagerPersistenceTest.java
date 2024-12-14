import org.example.musicplayeruserinterfacewithjavafx.PlaylistManager;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistManagerPersistenceTest {

    private static final String TEST_FILE = "playlists.txt";

    @BeforeEach
    public void setUp() {
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
    public void testPersistence() {
        PlaylistManager playlistManager1 = new PlaylistManager() {
            @Override
            protected String getPlaylistsFilePath() {
                return TEST_FILE;
            }
        };
        playlistManager1.addPlaylist("Favorites");
        playlistManager1.addPlaylist("Workout");

        // Create a new instance and ensure it loads the playlists
        PlaylistManager playlistManager2 = new PlaylistManager() {
            @Override
            protected String getPlaylistsFilePath() {
                return TEST_FILE;
            }
        };

        assertTrue(playlistManager2.getPlaylists().contains("Favorites"), "Favorites playlist should persist");
        assertTrue(playlistManager2.getPlaylists().contains("Workout"), "Workout playlist should persist");
    }
}
