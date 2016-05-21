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

    public void PlayMp3(int fileId) {
        if (mediaPlayer != null) {
            this.StopMp3();
        }
        mediaPlayer = MediaPlayer.create(context, fileId);
        mediaPlayer.start();
    }

    public void StopMp3() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void play_LettersMode() {
        this.PlayMp3(R.raw.az_letters_mode);
    }

    public void play_NumbersMode() {
        this.PlayMp3(R.raw.az_numbers_mode);
    }

    public void play_FiguresMode() {
        this.PlayMp3(R.raw.az_figures_mode);
    }

    public void playPhoneOpenMode() {
        this.PlayMp3(R.raw.az_open_ringtone);
    }

    public void playPhoneCloseMode() {
        this.PlayMp3(R.raw.az_close);
    }

    /*play*/
    public void playChar(char Letter) {
        switch (Letter) {
            case 'A':
                this.PlayMp3(R.raw.az_a);
                break;
            case 'B':
                this.PlayMp3(R.raw.az_b);
                break;
            case 'C':
                this.PlayMp3(R.raw.az_c);
                break;
            case 'Ç':
                this.PlayMp3(R.raw.az_ch);
                break;
            case 'D':
                this.PlayMp3(R.raw.az_d);
                break;
            case 'E':
                this.PlayMp3(R.raw.az_e);
                break;
            case 'Ə':
            case 'ə':
                this.PlayMp3(R.raw.az_ee);
                break;
            case 'F':
                this.PlayMp3(R.raw.az_f);
                break;
            case 'G':
                this.PlayMp3(R.raw.az_g);
                break;
            case 'Ğ':
                this.PlayMp3(R.raw.az_gh);
                break;
            case 'H':
                this.PlayMp3(R.raw.az_h);
                break;
            case 'I':
                this.PlayMp3(R.raw.az_ii);
                break;
            case 'İ':
                this.PlayMp3(R.raw.az_i);
                break;
            case 'J':
                this.PlayMp3(R.raw.az_j);
                break;
            case 'K':
                this.PlayMp3(R.raw.az_k);
                break;
            case 'Q':
                this.PlayMp3(R.raw.az_q);
                break;
            case 'L':
                this.PlayMp3(R.raw.az_l);
                break;
            case 'M':
                this.PlayMp3(R.raw.az_m);
                break;
            case 'N':
                this.PlayMp3(R.raw.az_n);
                break;
            case 'O':
                this.PlayMp3(R.raw.az_o);
                break;
            case 'Ö':
                this.PlayMp3(R.raw.az_oo);
                break;
            case 'P':
                this.PlayMp3(R.raw.az_p);
                break;
            case 'R':
                this.PlayMp3(R.raw.az_r);
                break;
            case 'S':
                this.PlayMp3(R.raw.az_s);
                break;
            case 'Ş':
                this.PlayMp3(R.raw.az_sh);
                break;
            case 'T':
                this.PlayMp3(R.raw.az_t);
                break;
            case 'U':
                this.PlayMp3(R.raw.az_u);
                break;
            case 'Ü':
                this.PlayMp3(R.raw.az_uu);
                break;
            case 'V':
                this.PlayMp3(R.raw.az_v);
                break;
            case 'Y':
                this.PlayMp3(R.raw.az_y);
                break;
            case 'X':
                this.PlayMp3(R.raw.az_x);
                break;
            case 'Z':
                this.PlayMp3(R.raw.az_z);
                break;
            case '0':
                this.PlayMp3(R.raw.az_0);
                break;
            case '1':
                this.PlayMp3(R.raw.az_1);
                break;
            case '2':
                this.PlayMp3(R.raw.az_2);
                break;
            case '3':
                this.PlayMp3(R.raw.az_3);
                break;
            case '4':
                this.PlayMp3(R.raw.az_4);
                break;
            case '5':
                this.PlayMp3(R.raw.az_5);
                break;
            case '6':
                this.PlayMp3(R.raw.az_6);
                break;
            case '7':
                this.PlayMp3(R.raw.az_7);
                break;
            case '8':
                this.PlayMp3(R.raw.az_8);
                break;
            case '9':
                this.PlayMp3(R.raw.az_9);
                break;
            default:
        }
    }

    /**
     * Play wait sound
     *
     * @param index
     */
    public void playWait(int index) {
        this.PlayMp3(wait_sounds[index % wait_sounds.length]);
    }

    /**
     * Play figures
     *
     * @param innerShapeType
     */
    public void playFigures(FunnyButton.InnerShapeType innerShapeType) {
        switch (innerShapeType) {
            case Circle:
                this.PlayMp3(R.raw.az_daire);
                break;
            case Square:
                this.PlayMp3(R.raw.az_kvadrat);
                break;
            case Triangle:
                this.PlayMp3(R.raw.az_ucbucaq);
                break;
            case Rectangle:
                this.PlayMp3(R.raw.az_duzbucaq);
                break;
            case Trapes:
                this.PlayMp3(R.raw.az_trapesiya);
                break;
            case Heart:
                this.PlayMp3(R.raw.az_urek);
                break;
            case Star:
                this.PlayMp3(R.raw.az_ulduz);
                break;
            case Pentagon:
                this.PlayMp3(R.raw.az_beshbucaq);
                break;
            case Ellipse:
                this.PlayMp3(R.raw.az_elips);
                break;
            case Hexagon:
                this.PlayMp3(R.raw.az_altibucaq);
                break;
            default:
        }
    }

    /**
     * Play keypad tones
     *
     * @param keypad
     */
    public void playKeypadTones(char keypad) {
        switch (keypad) {
            case '0':
                this.PlayMp3(R.raw.keypad_0);
                break;
            case '1':
                this.PlayMp3(R.raw.keypad_1);
                break;
            case '2':
                this.PlayMp3(R.raw.keypad_2);
                break;
            case '3':
                this.PlayMp3(R.raw.keypad_3);
                break;
            case '4':
                this.PlayMp3(R.raw.keypad_4);
                break;
            case '5':
                this.PlayMp3(R.raw.keypad_5);
                break;
            case '6':
                this.PlayMp3(R.raw.keypad_6);
                break;
            case '7':
                this.PlayMp3(R.raw.keypad_7);
                break;
            case '8':
                this.PlayMp3(R.raw.keypad_8);
                break;
            case '9':
                this.PlayMp3(R.raw.keypad_9);
                break;
            default:
        }
    }

    public void playCallAnyOne() {
        this.PlayMp3(R.raw.az_gel_birine_zeng_edek);
    }

    public void playCall(final String number, final int callId, final CallModeInterface callback) {
        this.PlayMp3(R.raw.dial_tone);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer player) {
                switch (number) {
                    case "000":
                        if (callId == 1) {
                            PlayMp3(R.raw.az_call_000_1);
                        } else   {
                            PlayMp3(R.raw.az_call_000_2);
                        }
                        break;
                    case "102":
                        if (callId == 1) {
                            PlayMp3(R.raw.az_call_102_1);
                        } else if (callId == 2) {
                            PlayMp3(R.raw.az_call_102_2);
                        } else {
                            PlayMp3(R.raw.az_call_102_3);
                        }
                        break;
                    case "103":
                        if (callId == 1) {
                            PlayMp3(R.raw.az_call_103_1);
                        }  else  {
                            PlayMp3(R.raw.az_call_103_2);
                        }
                        break;
                    case "112":
                        if (callId == 1) {
                            PlayMp3(R.raw.az_call_112_1);
                        }  else if (callId == 2) {
                            PlayMp3(R.raw.az_call_112_2);
                        }  else   {
                            PlayMp3(R.raw.az_call_112_3);
                        }
                        break;
                    default:
                        if (callId == 1) {
                            PlayMp3(R.raw.az_incorrect_number_1);
                        }  else  {
                            PlayMp3(R.raw.az_incorrect_number_2);
                        }
                }
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if(callback!=null) callback.finished();
                    }
                });
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
