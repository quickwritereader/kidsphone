package kidtoys.az.kidphone;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class PhoneActivity extends AppCompatActivity implements Phone, View.OnClickListener {


    public long userActivityTime;
    private SoundPlayer soundPlayer;
    private FunnyDisplay display;
    private FunnyButton.KeyMode lastKeyMode = null;
    private UiHandler handler;
    private ViewGroup keysGroup;
    private BaseMode mode = null;

    @Override
    public View getViewById(int id) {
        return findViewById(id);
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
        display = (FunnyDisplay) findViewById(R.id.display);
        keysGroup = (ViewGroup) findViewById(R.id.KeysGroup);

        userActivityTime = System.currentTimeMillis();
        handler = new UiHandler(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        soundPlayer.playPhoneOpenMode();
        try {
            TeachMode teachMode= new TeachMode(this,false);
            mode =teachMode;
            teachMode.setPlaySound(true);
            //finally, we can set mode
          //  drawAny();;
        } catch (Exception e) {
            //should not happen
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.activateDelay(UiHandler.TIME_DELAY * 2);
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
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        handler.deActivateDelay();
        this.soundPlayer.playPhoneCloseMode();
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
        userActivityTime = System.currentTimeMillis();
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
                case R.id.buttonYes:
                    if (mode != null && mode instanceof CallMode) {
                        mode.onRefresh();
                    } else {
                        if (mode != null) mode.onSave();
                        mode = null;
                        mode = new CallMode(this);
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

    public void playWait(int index) {
        this.soundPlayer.playWait(index);
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
