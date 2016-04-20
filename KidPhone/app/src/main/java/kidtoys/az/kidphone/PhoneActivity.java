package kidtoys.az.kidphone;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class PhoneActivity extends AppCompatActivity implements View.OnClickListener {

    FunnyButton.BehaviorMode mode = FunnyButton.BehaviorMode.Letters;
    int defaultColor = Color.BLACK;
    FunnyDisplay display;
    private String lastPressed ="";
    private int pressedTimes = 0;

    public static SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_phone);

        soundPlayer = new SoundPlayer(getApplicationContext());

        setListenersForKeys();
        FunnyButton button=(FunnyButton)findViewById(R.id.KeysMode);
        button.setOnClickListener(this);
        display = (FunnyDisplay) findViewById(R.id.display);
    }

    /**
     * set On Click Listeners of keys buttons
     */
    private void setListenersForKeys() {
        ViewGroup group = (ViewGroup) findViewById(R.id.KeysGroup);
        int childcount = group.getChildCount();
        for (int i = 0; i < childcount; i++) {
            View v = group.getChildAt(i);
            if (v instanceof FunnyButton) {
                v.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.KeysMode) {
            changeKeys();
            lastPressed = "";
            return;
        }

        FunnyButton funnyButton = (FunnyButton) v;
        if (funnyButton.getbMode() == FunnyButton.BehaviorMode.Letters) {
            String letters = funnyButton.getLettersText();
            pressedTimes = lastPressed.equals(letters) ? pressedTimes : 0;
            lastPressed = letters;
            if (letters.length() > 0) {
                char l = '_';
                if (pressedTimes < letters.length()) {
                    l = letters.charAt(pressedTimes);
                    pressedTimes += 1;
                    if(pressedTimes==letters.length())pressedTimes=0;
                }
                if (l != '_') {
                    //Toast.makeText(this, "letter: " + l, Toast.LENGTH_SHORT).show();
                    //drawChar
                    draw_play(l);
                }

            }
        }else if   (funnyButton.getbMode() == FunnyButton.BehaviorMode.Numbers){
            String number = funnyButton.getNumbersText();
            if (number.length() > 0){
                draw_play(number.charAt(0));
            }
        }


    }

    /**
     * draw char and play at the same time
     * @param l
     */
    public void draw_play(char l) {
        Log.d("letter", "letter: " + l);
        FunnySurface mainSurface = display.getMainSurface();
        mainSurface.clear();
        int figureRandom = (int) (Math.random() * (FunnySurface.DotType.values().length - 1)) + 1;
        int colorRandom =(int) (Math.random() * (FunnySurface.DotColor.values().length - 2))+1;//exclude white and black
        FunnySurfaceUtils.drawChar(mainSurface, mainSurface.getWidth() / 2, 4, l, FunnySurface.DotColor.values()[colorRandom],
                FunnySurface.DotType.values()[figureRandom], true);
        display.Render();
        //play sound
        soundPlayer.playChar(l);
    }


    /**
     * Change keys' Mode while pressing KeysMode
     */
    private void changeKeys() {
        ViewGroup group = (ViewGroup) findViewById(R.id.KeysGroup);
        int childcount = group.getChildCount();
        boolean changeTextColor = false;
        FunnyButton.BehaviorMode newMode;
        switch (mode) {
            case Numbers:
                newMode = FunnyButton.BehaviorMode.Figures;
                changeTextColor = true;
                soundPlayer.play_FiguresMode();
                break;
            case Normal:
                newMode = FunnyButton.BehaviorMode.Numbers;
                changeTextColor = true;
                soundPlayer.play_NumbersMode();
                break;
            case Letters:
                newMode = FunnyButton.BehaviorMode.Numbers;
                soundPlayer.play_NumbersMode();
                break;
            case Figures:
                newMode = FunnyButton.BehaviorMode.Letters;
                soundPlayer.play_LettersMode();
                break;
            default:
                newMode = FunnyButton.BehaviorMode.Letters;
                soundPlayer.play_LettersMode();
        }
        FunnyButton funnyButton;
        for (int i = 0; i < childcount; i++) {

            View v = group.getChildAt(i);
            if (v instanceof FunnyButton) {
                funnyButton = (FunnyButton) group.getChildAt(i);
                if (funnyButton.getbMode() != FunnyButton.BehaviorMode.System) {

                    //set mode after
                    funnyButton.setbMode(newMode);
                }
            }

        }
        mode = newMode;
        //clear screen
        FunnySurface mainSurface = display.getMainSurface();
        mainSurface.clear();
        display.Render();
    }
}
