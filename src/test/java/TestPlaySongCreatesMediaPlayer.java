import org.example.musicplayeruserinterfacewithjavafx.MediaPlayerManager;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class TestPlaySongCreatesMediaPlayer {

    @InjectMocks
    private MediaPlayerManager mediaPlayerManager;

    @Mock
    private MediaPlayer mockMediaPlayer;

    @BeforeEach
    public void setUp() {
        // Set up the mock for MediaPlayerManager's constructor.
        mediaPlayerManager = new MediaPlayerManager();
    }

    @Test
    public void testPlaySongCreatesMediaPlayer() {
        // Arrange
        String previewUrl = "https://example.com/song.mp3";

        // Act
        mediaPlayerManager.playSong(previewUrl);

        // Assert
        verify(mockMediaPlayer, times(1)).play();
        verify(mockMediaPlayer, times(1)).setVolume(1.0);
        verify(mockMediaPlayer, times(1)).setRate(1.0);
    }
}
