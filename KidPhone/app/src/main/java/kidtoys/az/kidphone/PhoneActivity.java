package kidtoys.az.kidphone;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class PhoneActivity extends AppCompatActivity implements Phone, View.OnClickListener {


    private SoundPlayer soundPlayer;
    private FunnyDisplay display;
    private FunnyButton.KeyMode lastKeyMode = null;
    private UiHandler handler;
    private ViewGroup keysGroup;
    private BaseMode mode = null;
    private BaseAnimation localSpeaker;
    private BaseAnimation callAnimation;

    @Override
    public View getViewById(int id) {
        return findViewById(id);
    }

    @Override
    public void activateDelay() {
        handler.activateDelay();
    }

    @Override
    public void activateDelay(UiHandler.DelayObject object, long delay) {
        handler.activateDelay(object, delay);
    }

    @Override
    public void deActivateDelay() {
        handler.deActivateDelay();
    }

    @Override
    public void refreshActiveTime(int forwardDelay) {
        handler.refreshActiveTime(forwardDelay);
    }

    @Override
    public void startSpeaker(boolean restoreOld) {
        localSpeaker.start(-1, restoreOld);
    }

    @Override
    public void startSpeaker(int duration, boolean restoreOld) {
        localSpeaker.start(duration, restoreOld);
    }

    @Override
    public void startSpeaker() {
        localSpeaker.start();
    }

    @Override
    public void startSpeaker(int duration) {
        localSpeaker.start(duration);
    }

    @Override
    public void stopSpeaker() {
        localSpeaker.stop(true);
    }

    @Override
    public void stopSpeaker(boolean force) {
        localSpeaker.stop(force);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_phone);
        soundPlayer = new SoundPlayer(getApplicationContext());
        setListenersForKeys();
        View button = findViewById(R.id.KeysMode);
        button.setOnClickListener(this);
        button = findViewById(R.id.gameMode);
        button.setOnClickListener(this);
        button = findViewById(R.id.buttonYes);
        button.setOnClickListener(this);
        button = findViewById(R.id.buttonNo);
        button.setOnClickListener(this);
        button = findViewById(R.id.quizMode);
        button.setOnClickListener(this);
        display = (FunnyDisplay) findViewById(R.id.display);
        keysGroup = (ViewGroup) findViewById(R.id.KeysGroup);
        handler = new UiHandler(this);
        localSpeaker = new SpeakerAnimation(display);
        callAnimation = new CallAnimation(display);
    }

    int started = 0;

    @Override
    protected void onStart() {
        super.onStart();
        handler.deActivateDelay();
        handler.activateDelay(UiHandler.TIME_DELAY);
        try {
            if (started == 0) {
                started = 1;

                callAnimation.start();
                getAudio().PlayMp3(R.raw.open_ringtone, new SoundCallBack() {
                    @Override
                    public void soundPlayFinished() {
                        callAnimation.stop(true);
                        int duration = soundPlayer.playPhoneOpenMode();
                        startSpeaker(duration);
                        refreshActiveTime(duration);
                    }
                });

                TeachMode teachMode = new TeachMode(this, false);
                mode = teachMode;
                teachMode.setPlaySound(true);
            }
            //finally, we can set mode
            //  drawAny();;
        } catch (Exception e) {
            //should not happen
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mode != null && mode instanceof GameMode) {
            mode.onRefresh();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        /*handler.deActivateDelay();*/
        if (mode != null) mode.onSave();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_HOME)) {
            if (mode != null) mode.onSave();
            handler.deActivateDelay();
            started = 0;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        handler.deActivateDelay();
        this.soundPlayer.playPhoneCloseMode();
        started = 0;
        super.onBackPressed();
    }

    /**
     * set On Click Listeners of keys buttons
     */
    private void setListenersForKeys() {
        ViewGroup group = (ViewGroup) findViewById(R.id.KeysGroup);
        int childCount = group.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = group.getChildAt(i);
            if (v instanceof FunnyButton) {
                v.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        handler.refreshActiveTime();
        try {

            switch (v.getId()) {
                case R.id.KeysMode:
                    if (mode != null && mode instanceof TeachMode) {
                        mode.onRefresh();
                    } else {
                        if (mode != null) mode.onSave();
                        mode = null;
                        mode = new TeachMode(this);
                    }
                    break;
                case R.id.quizMode:
                    if (mode != null && mode instanceof QuestionMode) {
                        mode.onRefresh();
                    } else {
                        if (mode != null) mode.onSave();
                        mode = null;
                        mode = new QuestionMode(this);
                    }
                    break;
                case R.id.buttonYes:
                    if (mode != null && mode instanceof CallMode) {
                        mode.onRefresh();
                    } else {
                        if (mode != null) mode.onSave();
                        mode = null;
                        mode = new CallMode(this);
                    }
                    break;
                case R.id.buttonNo:
                    if (mode != null && mode instanceof CallMode) {
                        mode.onClick((FunnyButton) v);
                    }
                    break;
                case R.id.gameMode:
                    if (mode != null && mode instanceof GameMode) {
                        mode.onRefresh();
                    } else {
                        if (mode != null) mode.onSave();
                        mode = null;
                        mode = new GameMode(this);
                    }
                default:
                    if (((FunnyButton) v).getKeyMode() != FunnyButton.KeyMode.System) {
                        if (mode != null) {
                            mode.onClick((FunnyButton) v);
                        }
                    }
            }//switch end
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

    }


    @Override
    public void changeKeys(FunnyButton.KeyMode newMode) {
        if (newMode == lastKeyMode) return;
        FunnyButton funnyButton;
        int childCount = keysGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {

            View v = keysGroup.getChildAt(i);
            if (v instanceof FunnyButton) {
                funnyButton = (FunnyButton) keysGroup.getChildAt(i);
                if (funnyButton.getKeyMode() != FunnyButton.KeyMode.System) {
                    //set keysMode after
                    funnyButton.setKeyMode(newMode);
                }
            }

        }
        lastKeyMode = newMode;
    }

    @Override
    public FunnyDisplay getDisplay() {
        return this.display;
    }

    @Override
    public SoundPlayer getAudio() {
        return this.soundPlayer;
    }


    public Handler getHandler() {
        return this.handler;
    }

//    public void drawAny(){
//        FunnySurface surface=display.getMainSurface();
//        surface.clear();
//        surface.putDot(1,1, FunnySurface.DotColor.Blue, FunnySurface.DotType.Star);
//        FunnySurface surface1=FunnySurface.createSurface(3,3, FunnySurface.DotColor.Blue, FunnySurface.DotType.Heart);
//        surface.putSurface(surface1,2,2);
//        surface.drawLine(1,1,6,6, FunnySurface.DotColor.Red, FunnySurface.DotType.Star);
//        display.render();
//    }

}
