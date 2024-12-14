import org.example.musicplayeruserinterfacewithjavafx.PlaylistManager;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistManagerEdgeCasesTest {

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
    }

    @Test
    public void testLongPlaylistName() {
        String longName = "a".repeat(1000); // A playlist name with 1000 characters
        playlistManager.addPlaylist(longName);

        assertTrue(playlistManager.getPlaylists().contains(longName), "Playlist with long name should be added");
    }

    @Test
    public void testPlaylistNameWithSpecialCharacters() {
        String specialName = "🎶💃Party Hits!💃🎶";
        playlistManager.addPlaylist(specialName);

        assertTrue(playlistManager.getPlaylists().contains(specialName), "Playlist with special characters should be added");
    }
}
