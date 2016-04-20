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
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
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

    /*  Play Alphabet   */
    public void play_A() {
        this.PlayMp3(R.raw.az_a);
    }

    public void play_B() {
        this.PlayMp3(R.raw.az_b);
    }

    public void play_C() {
        this.PlayMp3(R.raw.az_c);
    }

    public void play_CH() {
        this.PlayMp3(R.raw.az_ch);
    }

    public void play_D() {
        this.PlayMp3(R.raw.az_d);
    }

    public void play_E() {
        this.PlayMp3(R.raw.az_e);
    }

    public void play_EE() {
        this.PlayMp3(R.raw.az_ee);
    }

    public void play_F() {
        this.PlayMp3(R.raw.az_f);
    }

    public void play_G() {
        this.PlayMp3(R.raw.az_g);
    }

    public void play_GH() {
        this.PlayMp3(R.raw.az_gh);
    }

    public void play_H() {
        this.PlayMp3(R.raw.az_h);
    }

    public void play_X() {
        this.PlayMp3(R.raw.az_x);
    }

    public void play_II() {
        this.PlayMp3(R.raw.az_ii);
    }

    public void play_I() {
        this.PlayMp3(R.raw.az_i);
    }

    public void play_J() {
        this.PlayMp3(R.raw.az_j);
    }

    public void play_K() {
        this.PlayMp3(R.raw.az_k);
    }

    public void play_KK() {
        this.PlayMp3(R.raw.az_kk);
    }

    public void play_Q() {
        this.PlayMp3(R.raw.az_q);
    }

    public void play_L() {
        this.PlayMp3(R.raw.az_l);
    }

    public void play_M() {
        this.PlayMp3(R.raw.az_m);
    }

    public void play_N() {
        this.PlayMp3(R.raw.az_n);
    }

    public void play_O() {
        this.PlayMp3(R.raw.az_o);
    }

    public void play_OO() {
        this.PlayMp3(R.raw.az_oo);
    }

    public void play_P() {
        this.PlayMp3(R.raw.az_p);
    }

    public void play_R() {
        this.PlayMp3(R.raw.az_r);
    }

    public void play_S() {
        this.PlayMp3(R.raw.az_s);
    }

    public void play_SH() {
        this.PlayMp3(R.raw.az_sh);
    }

    public void play_T() {
        this.PlayMp3(R.raw.az_t);
    }

    public void play_U() {
        this.PlayMp3(R.raw.az_u);
    }

    public void play_UU() {
        this.PlayMp3(R.raw.az_uu);
    }

    public void play_V() {
        this.PlayMp3(R.raw.az_v);
    }

    public void play_Y() {
        this.PlayMp3(R.raw.az_y);
    }

    public void play_Z() {
        this.PlayMp3(R.raw.az_z);
    }
    /*  Play Alphabet */


    /*  Play Numbers */

    /*  Play Numbers */
}
