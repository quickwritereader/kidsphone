package kidtoys.az.kidphone;

/**
 * on TeachMode kid will learn basics
 */
public class TeachMode extends BaseMode implements  SoundCallBack{


    private static final String STATE = "keyMode";
    FunnyButton.KeyMode keysMode = FunnyButton.KeyMode.Letters;//this way it will be letters
    private String lastPressed = "";
    private int pressedTimes = 0;
    private SoundPlayer soundPlayer;
    private FunnyDisplay display;
    private  FunnyButton modeButton=null;
    private boolean playSound=true;
    private  LetterAnimation letterAnimation=null;
    boolean drawLetterFigureAnime =false;
    public boolean isPlaySound() {
        return playSound;
    }

    public void setPlaySound(boolean playSound) {
        this.playSound = playSound;
    }

    public TeachMode (Phone phone,boolean playSound) throws Exception{
        super(phone);
        this.playSound=playSound;
        this.soundPlayer=phone.getAudio();
        this.display=phone.getDisplay();
        FunnyButton.KeyMode mode=(FunnyButton.KeyMode)  getState(STATE);
        if(mode!=null) keysMode=mode;
        modeButton= (FunnyButton) phone.getViewById(R.id.KeysMode);
        openKeyMode(keysMode);
        letterAnimation=new LetterAnimation(this.display);
    }

    public TeachMode (Phone phone) throws Exception{
        super(phone);
        this.soundPlayer=phone.getAudio();
        this.display=phone.getDisplay();
        FunnyButton.KeyMode mode=(FunnyButton.KeyMode)  getState(STATE);
        if(mode!=null) keysMode=mode;
        modeButton= (FunnyButton) phone.getViewById(R.id.KeysMode);
        openKeyMode(keysMode);
        letterAnimation=new LetterAnimation(this.display);
    }

    @Override
    public void onClick(FunnyButton funnyButton) {
        if(letterAnimation!=null){
            letterAnimation.stop(true);
            drawLetterFigureAnime =false;
        }
        if (funnyButton.getKeyMode() == FunnyButton.KeyMode.Letters) {
            phone.stopSpeaker();
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
            phone.stopSpeaker();
            String number = funnyButton.getNumbersText();
            if (number.length() > 0) {
                draw_play(number.charAt(0));
            }
        } else if (funnyButton.getKeyMode() == FunnyButton.KeyMode.Figures) {
            phone.stopSpeaker();
            FunnyButton.InnerShapeType innerShapeType = funnyButton.getInnerShape();
            draw_play_figure(innerShapeType);
        }
    }


    /**
     * draw char and play at the same time
     *
     * @param innerShapeType
     */
    private void draw_play_figure(FunnyButton.InnerShapeType innerShapeType) {
        drawLetterFigureAnime =true;
        phone.deActivateDelay();
        if(letterAnimation!=null){
            letterAnimation.stop(true);
            letterAnimation.setInnerShapeType(innerShapeType);
        }
        phone.getDisplay().drawFigure(innerShapeType);
         phone.getAudio().playFigures(innerShapeType,this);
    }


    /**
     * draw char and play at the same time
     *
     * @param l
     */
    private void draw_play(char l) {
        drawLetterFigureAnime =true;
        phone.deActivateDelay();
        if(letterAnimation!=null){
            letterAnimation.stop(true);
            letterAnimation.setLetter(l);
        }
        phone.getDisplay().attachAnimation(null);
        phone.getDisplay().drawChar(l);
//        int duration=phone.getAudio().playChar(l);
//        phone.refreshActiveTime(duration);


        phone.getAudio().playChar(l,this);


    }

    private FunnyButton.KeyMode switchMode(FunnyButton.KeyMode old){
        FunnyButton.KeyMode retMode;
        switch (old) {
            case Numbers:
                retMode = FunnyButton.KeyMode.Figures;
                break;
            case Normal:
                retMode = FunnyButton.KeyMode.Numbers;
                break;
            case Letters:
                retMode = FunnyButton.KeyMode.Numbers;
                break;
            case Figures:
                retMode = FunnyButton.KeyMode.Letters;
                break;
            default:
                retMode = FunnyButton.KeyMode.Letters;
        }
        return retMode;
    }

    private void playMode(FunnyButton.KeyMode mode,SoundCallBack callback){
        phone.startSpeaker();
        switch (mode) {
            case Normal:
            case Numbers:
                soundPlayer.play_NumbersMode(callback);
                break;
            case Letters:
                soundPlayer.play_LettersMode(callback);
                break;
            case Figures:
                soundPlayer.play_FiguresMode(callback);
                break;
            default:
                soundPlayer.play_LettersMode(callback);
        }
    }

    public void changeModeButton(boolean switched){
        if(modeButton!=null){
            int ret=R.drawable.figure;
            FunnyButton.KeyMode inMode= switched ?switchMode(keysMode):keysMode;
            switch ( inMode ){
                case Letters:
                    ret=R.drawable.alphabet;
                    break;
                case Numbers:
                    ret=R.drawable.numbers;
                    break;
                case Figures:
                    ret=R.drawable.figure;
                    break;

            }

            modeButton.setPicture(ret);
        }
    }


    /**
     * Change keys' Mode while pressing KeysMode
     */
    private void changeKeyMode() {
        keysMode = switchMode(keysMode);
        openKeyMode(keysMode);

    }

    private void openKeyMode(FunnyButton.KeyMode mode ) {
        if(isPlaySound()) {
            phone.deActivateDelay();
            playMode(mode,this);
        }
        phone.changeKeys(mode);
        changeModeButton(true);
        //clear screen
        display.clear();
    }

    public   void onRefresh(){
        lastPressed="";
        pressedTimes=0;
        phone.stopSpeaker();
        if(letterAnimation!=null){
            letterAnimation.stop(true);
        }
        drawLetterFigureAnime =false;
        changeKeyMode();
        changeModeButton(true);

    }

    public   void onSave(){
        putState(STATE,keysMode);
        drawLetterFigureAnime =false;
        changeModeButton(false);
        phone.stopSpeaker();
        phone.getAudio().StopMp3();
        if(letterAnimation!=null){
            letterAnimation.stop(true);
        }
        //change button for old
    }

    @Override
    public void soundPlayFinished() {
        phone.activateDelay();
        phone.stopSpeaker(false);
        if(drawLetterFigureAnime){
            if(letterAnimation!=null)letterAnimation.start(-1,true);
            phone.refreshActiveTime(5000);
        }
    }
}
