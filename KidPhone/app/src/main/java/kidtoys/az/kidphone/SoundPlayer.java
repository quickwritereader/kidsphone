package kidtoys.az.kidphone;


import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by ramil on 20.04.2016.
 */
public class SoundPlayer {

    public MediaPlayer mediaPlayer;
    public Context context;

    public SoundPlayer(Context ct) {
        this.context = ct;
    }

    public void PlayMp3(int fileId) {
        if (mediaPlayer != null) {
            this.StopMp3();
        }
        mediaPlayer = MediaPlayer.create(context, fileId);
        mediaPlayer.start();
    }

    public void StopMp3() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }


}
