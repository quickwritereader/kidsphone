package kidtoys.az.kidphone;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import org.apache.http.client.protocol.ClientContextConfigurer;

public class PhoneActivity extends AppCompatActivity implements View.OnClickListener {

    FunnyButton.BehaviorMode mode = FunnyButton.BehaviorMode.Normal;
    int defaultColor = Color.BLACK;
    FunnyDisplay display;
    private String lastPressed ="";
    private int pressedTimes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_phone);
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
                    //drawLetter
                    Log.d("letter", "letter: " + l);
                    FunnySurface mainSurface = display.getMainSurface();
                    mainSurface.clear();
                    int figureRandom = (int) (Math.random() * (FunnySurface.DotType.values().length - 1)) + 1;
                    int colorRandom = (int) (Math.random() * (FunnySurface.DotColor.values().length - 1));
                    FunnySurfaceUtils.drawLetter(mainSurface, mainSurface.getWidth() / 2 - 4, 4, l, FunnySurface.DotColor.values()[colorRandom],
                            FunnySurface.DotType.values()[figureRandom]);
                    ;
                    display.Render();
                }

            }
        }


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
                newMode = FunnyButton.BehaviorMode.Letters;
                changeTextColor = true;
                break;
            case Normal:
                newMode = FunnyButton.BehaviorMode.Numbers;
                changeTextColor = true;
                break;
            case Letters:
                newMode = FunnyButton.BehaviorMode.Figures;
                break;
            case Figures:
                newMode = FunnyButton.BehaviorMode.Normal;
                break;
            default:
                newMode = FunnyButton.BehaviorMode.Normal;
        }
        FunnyButton funnyButton;
        for (int i = 0; i < childcount; i++) {

            View v = group.getChildAt(i);
            if (v instanceof FunnyButton) {
                funnyButton = (FunnyButton) group.getChildAt(i);
                if (funnyButton.getbMode() != FunnyButton.BehaviorMode.System) {
                    if (mode == FunnyButton.BehaviorMode.Normal) {
                        defaultColor = funnyButton.getTextColor();
                    }
                    if (changeTextColor) {
                        funnyButton.setTextColor(Color.WHITE);
                    } else {
                        funnyButton.setTextColor(defaultColor);
                    }
                    //set mode after
                    funnyButton.setbMode(newMode);
                }
            }

        }
        mode = newMode;
    }
}
