import org.example.musicplayeruserinterfacewithjavafx.MediaPlayerManager;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestIsPlayingReturnsFalseWhenNotPlaying {

    @InjectMocks
    private MediaPlayerManager mediaPlayerManager;

    @Mock
    private MediaPlayer mockMediaPlayer;

    @BeforeEach
    public void setUp() {
        mediaPlayerManager = new MediaPlayerManager();
    }

    @Test
    public void testIsPlayingReturnsFalseWhenNotPlaying() {
        // Arrange
        when(mockMediaPlayer.getStatus()).thenReturn(MediaPlayer.Status.STOPPED);

        // Act
        boolean isPlaying = mediaPlayerManager.isPlaying();

        // Assert
        assertFalse(isPlaying);
    }
}
