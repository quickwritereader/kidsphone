package kidtoys.az.kidphone;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

/**
 * Created by ramil on 20.04.2016.
 */
public class SoundPlayer {

    public static final int[] wait_sounds = {R.raw.az_wait_1, R.raw.az_wait_2, R.raw.az_wait_3};
    public static MediaPlayer mediaPlayer;
    public Context context;
    public int poolAudio1;
    public int poolAudio2;
    private SoundPool pool;

    public SoundPlayer(Context ct) {
        this.context = ct;
    }

    public int PlayMp3(int fileId) {
        if (mediaPlayer != null) {
            this.StopMp3();
        }
        mediaPlayer = MediaPlayer.create(context, fileId);
        mediaPlayer.start();
        return  mediaPlayer.getDuration();
    }

    public void PlayMp3(int fileId,final SoundCallBack callback) {
        if (mediaPlayer != null) {
            this.StopMp3();
        }
        mediaPlayer = MediaPlayer.create(context, fileId);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (callback != null) callback.soundPlayFinished();
            }
        });
    }

    public void StopMp3() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void play_LettersMode(SoundCallBack callback) {
        this.PlayMp3(R.raw.az_letters_mode,callback);
    }

    public void play_NumbersMode(SoundCallBack callback) {
        this.PlayMp3(R.raw.az_numbers_mode,callback);
    }

    public void play_FiguresMode(SoundCallBack callback) {
        this.PlayMp3(R.raw.az_figures_mode,callback);
    }

    public int playPhoneOpenMode() {
        return this.PlayMp3(R.raw.az_open_ringtone);
    }

    public int playPhoneCloseMode() {
        return this.PlayMp3(R.raw.az_close);
    }

    /*play*/
    public int playChar(char Letter) {
        switch (Letter) {
            case 'A':
                return this.PlayMp3(R.raw.az_a);
            case 'B':
                return this.PlayMp3(R.raw.az_b);
            case 'C':
                return this.PlayMp3(R.raw.az_c);
            case 'Ç':
                return this.PlayMp3(R.raw.az_ch);
            case 'D':
                return this.PlayMp3(R.raw.az_d);
            case 'E':
                return this.PlayMp3(R.raw.az_e);
            case 'Ə':
            case 'ə':
                return this.PlayMp3(R.raw.az_ee);
            case 'F':
                return this.PlayMp3(R.raw.az_f);
            case 'G':
                return this.PlayMp3(R.raw.az_g);
            case 'Ğ':
                return this.PlayMp3(R.raw.az_gh);
            case 'H':
                return this.PlayMp3(R.raw.az_h);
            case 'I':
                return this.PlayMp3(R.raw.az_ii);
            case 'İ':
                return this.PlayMp3(R.raw.az_i);
            case 'J':
                return this.PlayMp3(R.raw.az_j);
            case 'K':
                return this.PlayMp3(R.raw.az_k);
            case 'Q':
                return this.PlayMp3(R.raw.az_q);
            case 'L':
                return this.PlayMp3(R.raw.az_l);
            case 'M':
                return this.PlayMp3(R.raw.az_m);
            case 'N':
                return this.PlayMp3(R.raw.az_n);
            case 'O':
                return this.PlayMp3(R.raw.az_o);
            case 'Ö':
                return this.PlayMp3(R.raw.az_oo);
            case 'P':
                return this.PlayMp3(R.raw.az_p);
            case 'R':
                return this.PlayMp3(R.raw.az_r);
            case 'S':
                return this.PlayMp3(R.raw.az_s);
            case 'Ş':
                return this.PlayMp3(R.raw.az_sh);
            case 'T':
                return this.PlayMp3(R.raw.az_t);
            case 'U':
                return this.PlayMp3(R.raw.az_u);
            case 'Ü':
                return this.PlayMp3(R.raw.az_uu);
            case 'V':
                return this.PlayMp3(R.raw.az_v);
            case 'Y':
                return this.PlayMp3(R.raw.az_y);
            case 'X':
                return this.PlayMp3(R.raw.az_x);
            case 'Z':
                return this.PlayMp3(R.raw.az_z);
            case '0':
                return this.PlayMp3(R.raw.az_0);
            case '1':
                return this.PlayMp3(R.raw.az_1);
            case '2':
                return this.PlayMp3(R.raw.az_2);
            case '3':
                return this.PlayMp3(R.raw.az_3);
            case '4':
                return this.PlayMp3(R.raw.az_4);
            case '5':
                return this.PlayMp3(R.raw.az_5);
            case '6':
                return this.PlayMp3(R.raw.az_6);
            case '7':
                return this.PlayMp3(R.raw.az_7);
            case '8':
                return this.PlayMp3(R.raw.az_8);
            case '9':
                return this.PlayMp3(R.raw.az_9);
            default:
                return 0;
        }
    }



    /**
     * Play figures
     *
     * @param innerShapeType
     */
    public int playFigures(FunnyButton.InnerShapeType innerShapeType) {
        switch (innerShapeType) {
            case Circle:
                return this.PlayMp3(R.raw.az_daire);
            case Square:
                return this.PlayMp3(R.raw.az_kvadrat);
            case Triangle:
                return this.PlayMp3(R.raw.az_ucbucaq);
            case Rectangle:
                return this.PlayMp3(R.raw.az_duzbucaq);
            case Trapes:
                return this.PlayMp3(R.raw.az_trapesiya);
            case Heart:
                return this.PlayMp3(R.raw.az_urek);
            case Star:
                return this.PlayMp3(R.raw.az_ulduz);
            case Pentagon:
                return this.PlayMp3(R.raw.az_beshbucaq);
            case Ellipse:
                return this.PlayMp3(R.raw.az_elips);
            case Hexagon:
                return this.PlayMp3(R.raw.az_altibucaq);
            default:
                return 0;
        }
    }

    /**
     * Play keypad tones
     *
     * @param keypad
     */
    public int playKeypadTones(char keypad) {
        switch (keypad) {
            case '0':
                return this.PlayMp3(R.raw.keypad_0);
            case '1':
                return this.PlayMp3(R.raw.keypad_1);
            case '2':
                return this.PlayMp3(R.raw.keypad_2);
            case '3':
                return this.PlayMp3(R.raw.keypad_3);
            case '4':
                return this.PlayMp3(R.raw.keypad_4);
            case '5':
                return this.PlayMp3(R.raw.keypad_5);
            case '6':
                return  this.PlayMp3(R.raw.keypad_6);
            case '7':
                return  this.PlayMp3(R.raw.keypad_7);
            case '8':
                return  this.PlayMp3(R.raw.keypad_8);
            case '9':
                return  this.PlayMp3(R.raw.keypad_9);
            default:
                return 0;
        }
    }



    public void playCall(final int soundId, final SoundCallBack callback) {
        this.PlayMp3(R.raw.dial_tone);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer player) {
                PlayMp3(soundId, callback);
            }
        });

    }

    public SoundPool getPool() {
        if (pool == null) {
            pool = new SoundPool(0, AudioManager.STREAM_MUSIC, 0);
            poolAudio1 = pool.load(context, R.raw.keypad_3, 1);
            poolAudio2 = pool.load(context, R.raw.keypad_8, 1);
        }
        return pool;
    }

}
