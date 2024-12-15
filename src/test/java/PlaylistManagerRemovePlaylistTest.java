import org.example.musicplayeruserinterfacewithjavafx.PlaylistManager;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistManagerRemovePlaylistTest {

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
    public void testRemovePlaylist() {
        playlistManager.addPlaylist("Rock Anthems");
        playlistManager.getPlaylists().remove("Rock Anthems");
        playlistManager.savePlaylistsToFile();

        assertFalse(playlistManager.getPlaylists().contains("Rock Anthems"), "Playlist should be removed");

        File file = new File(TEST_FILE);
        assertTrue(file.exists(), "Playlist file should exist");
        try {
            String fileContent = Files.readString(file.toPath());
            assertFalse(fileContent.contains("Rock Anthems"), "File should not contain the removed playlist");
        } catch (Exception e) {
            fail("Failed to read the playlist file: " + e.getMessage());
        }
    }
}
