import org.example.musicplayeruserinterfacewithjavafx.MediaPlayerManager;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class TestPlaySongStopsPreviousSong {

    @InjectMocks
    private MediaPlayerManager mediaPlayerManager;

    @Mock
    private MediaPlayer mockMediaPlayer;

    @BeforeEach
    public void setUp() {
        mediaPlayerManager = new MediaPlayerManager();
    }

    @Test
    public void testPlaySongStopsPreviousSong() {
        // Arrange
        String previewUrl = "https://example.com/song.mp3";

        // Act
        mediaPlayerManager.playSong(previewUrl); // First play
        mediaPlayerManager.playSong(previewUrl); // Second play

        // Assert that stop was called before starting the next song
        verify(mockMediaPlayer, times(1)).stop();
    }
}
