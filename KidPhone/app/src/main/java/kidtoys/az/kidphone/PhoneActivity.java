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
