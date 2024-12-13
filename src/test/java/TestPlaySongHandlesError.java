import org.example.musicplayeruserinterfacewithjavafx.MediaPlayerManager;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class TestPlaySongHandlesError {

    @InjectMocks
    private MediaPlayerManager mediaPlayerManager;

    @Mock
    private MediaPlayer mockMediaPlayer;

    @BeforeEach
    public void setUp() {
        mediaPlayerManager = new MediaPlayerManager();
    }

    @Test
    public void testPlaySongHandlesError() {
        // Arrange
        String previewUrl = "https://example.com/song.mp3";
        doThrow(new RuntimeException("MediaPlayer error")).when(mockMediaPlayer).play();

        // Act
        mediaPlayerManager.playSong(previewUrl);

        // Assert that the error handling mechanism is invoked
        verify(mockMediaPlayer, times(1)).setOnError(any());
    }
}
