package kidtoys.az.kidphone;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

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


    /*play*/
    public  void playChar(char Letter){
        switch (Letter) {
            case 'A':
                this.PlayMp3(R.raw.az_a);
                break;
            case 'B':
                this.PlayMp3(R.raw.az_b);;
                break;
            case 'C':
                this.PlayMp3(R.raw.az_c);
                break;
            case 'Ç':
                this.PlayMp3(R.raw.az_ch);;
                break;
            case 'D':
                this.PlayMp3(R.raw.az_d);
                break;
            case 'E':
                this.PlayMp3(R.raw.az_e);
                break;
            case 'Ə':
            case 'ə':
                this.PlayMp3(R.raw.az_ee);;
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
                this.PlayMp3(R.raw.az_uu);;
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
}
