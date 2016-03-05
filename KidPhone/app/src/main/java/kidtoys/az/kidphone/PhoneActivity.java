package kidtoys.az.kidphone;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

public class PhoneActivity extends AppCompatActivity implements View.OnClickListener {

    FunnyButton.BehaviorMode mode = FunnyButton.BehaviorMode.Normal;
    int defaultColor = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        FunnyButton keysMode = (FunnyButton) findViewById(R.id.KeysMode);
        keysMode.setOnClickListener(this);

        FunnyDisplay display=(FunnyDisplay)findViewById(R.id.display);

        //draw
        FunnySurface mainSurface= display.getMainSurface();
        FunnySurface RombRect=FunnySurface.createSurface(2, 2, FunnySurface.DotColor.Blue, FunnySurface.DotType.Romb);
        FunnySurface HeartRect=FunnySurface.createSurface(5,5, FunnySurface.DotColor.Red, FunnySurface.DotType.Heart);
        FunnySurface CircleRect=FunnySurface.createSurface(3, 3, FunnySurface.DotColor.Red, FunnySurface.DotType.Circle);
        mainSurface.putDot(0,0, FunnySurface.DotColor.Yellow, FunnySurface.DotType.Star);
        mainSurface.putSurface(RombRect,1,1 );
        mainSurface.putSurface(HeartRect,3,3);
        mainSurface.putSurface(CircleRect,9,3);
        mainSurface.putDot(11, 9, FunnySurface.DotColor.Yellow, FunnySurface.DotType.Square);
        mainSurface.putDot(12,9, FunnySurface.DotColor.Magenta, FunnySurface.DotType.Star);
        mainSurface.putDot(13,9, FunnySurface.DotColor.Orange, FunnySurface.DotType.Hexagon);
        display.Render();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.KeysMode) {
            changeKeys();
        }
    }

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
