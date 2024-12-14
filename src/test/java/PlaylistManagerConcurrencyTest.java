import org.example.musicplayeruserinterfacewithjavafx.PlaylistManager;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistManagerConcurrencyTest {

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
    public void testConcurrentAccess() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executor.submit(() -> playlistManager.addPlaylist("Playlist " + index));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all threads to finish
        }

        assertEquals(9, playlistManager.getPlaylists().size(), "All playlists should be added without errors");
    }

}
