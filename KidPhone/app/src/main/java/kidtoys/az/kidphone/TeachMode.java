package kidtoys.az.kidphone;

import android.view.Display;

/**
 * on TeachMode kid will learn basics
 */
public class TeachMode extends BaseMode{


    public static final String STATE = "keyMode";
    FunnyButton.KeyMode keysMode = FunnyButton.KeyMode.Figures;//this way it will be letters
    private String lastPressed = "";
    private int pressedTimes = 0;
    private SoundPlayer soundPlayer;
    private FunnyDisplay display;

    public TeachMode (Phone phone) throws Exception{
        super(phone);
        this.soundPlayer=phone.getAudio();
        this.display=phone.getDisplay();
        FunnyButton.KeyMode mode=(FunnyButton.KeyMode)  getState(STATE);
        if(mode!=null) keysMode=mode;
        onRefresh();
    }

    @Override
    public void onClick(FunnyButton funnyButton) {
        if (funnyButton.getKeyMode() == FunnyButton.KeyMode.Letters) {
            String letters = funnyButton.getLettersText();
            pressedTimes = lastPressed.equals(letters) ? pressedTimes : 0;
            lastPressed = letters;
            if (letters.length() > 0) {
                char l = '_';
                if (pressedTimes < letters.length()) {
                    l = letters.charAt(pressedTimes);
                    pressedTimes += 1;
                    if (pressedTimes == letters.length()) pressedTimes = 0;
                }
                if (l != '_') {
                    //Toast.makeText(this, "letter: " + l, Toast.LENGTH_SHORT).show();
                    //drawChar
                    draw_play(l);
                }

            }
        } else if (funnyButton.getKeyMode() == FunnyButton.KeyMode.Numbers) {
            String number = funnyButton.getNumbersText();
            if (number.length() > 0) {
                draw_play(number.charAt(0));
            }
        } else if (funnyButton.getKeyMode() == FunnyButton.KeyMode.Figures) {
            FunnyButton.InnerShapeType innerShapeType = funnyButton.getInnerShape();
            phone.getAudio().playFigures(innerShapeType);
        }
    }

    /**
     * draw char and play at the same time
     *
     * @param l
     */
    private void draw_play(char l) {
        phone.getDisplay().drawChar(l);
        phone.getAudio().playChar(l);
    }

    /**
     * Change keys' Mode while pressing KeysMode
     */
    private void changeKeys() {

        FunnyButton.KeyMode newMode;
        switch (keysMode) {
            case Numbers:
                newMode = FunnyButton.KeyMode.Figures;
                soundPlayer.play_FiguresMode();
                break;
            case Normal:
                newMode = FunnyButton.KeyMode.Numbers;
                soundPlayer.play_NumbersMode();
                break;
            case Letters:
                newMode = FunnyButton.KeyMode.Numbers;
                soundPlayer.play_NumbersMode();
                break;
            case Figures:
                newMode = FunnyButton.KeyMode.Letters;
                soundPlayer.play_LettersMode();
                break;
            default:
                newMode = FunnyButton.KeyMode.Letters;
                soundPlayer.play_LettersMode();
        }

        keysMode = newMode;
        //clear screen

        phone.changeKeys(newMode);
        display.clear();
    }

    public   void onRefresh(){
        lastPressed="";
        pressedTimes=0;
        changeKeys();
    }

    public   void onSave(){
        putState(STATE,keysMode);
    }

}
