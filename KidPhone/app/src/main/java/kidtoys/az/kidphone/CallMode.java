package kidtoys.az.kidphone;

/**
 * Phone calls for kids
 */
public class CallMode extends BaseMode implements SoundCallBack {

    public static String dialedNumber;
    public int lastDialedIndex = 0;
    public static int[] callPositions = {0, 0, 0, 0, 0, 0, 0, 0};
    FunnySurface surface;
    private static int[] callAny = {R.raw.az_gel_birine_zeng_edek};
    private static int[] callWho = {R.raw.az_kimin_nomresin_yigmaq_isteyirsen};
    private static int[] call000Arr = {R.raw.az_call_000_1, R.raw.az_call_000_2};
    private static int[] call102Arr = {R.raw.az_call_102_1, R.raw.az_call_102_2, R.raw.az_call_102_3};
    private static int[] call103Arr = {R.raw.az_call_103_1, R.raw.az_call_103_2};
    private static int[] call112Arr = {R.raw.az_call_112_1, R.raw.az_call_112_2, R.raw.az_call_112_3};
    private static int[] callIncorrect = {R.raw.az_incorrect_number_1, R.raw.az_incorrect_number_2};
    BaseAnimation callAnimation;
    BaseAnimation callNoAnimation;
    boolean isNoActive = false;
    boolean isNoPressed = false;
    private int dial_audio_id;
    private boolean enableCallbacks;

    public enum LAST_TYPE {NONE,SUCCESS_CALL,ANSWER,ABORT,DIAL}
    public LAST_TYPE last_type=LAST_TYPE.NONE;

    public int[] getCallSoundArray(int index) {
        switch (index) {
            case 1:
                return call000Arr;
            case 2:
                return call102Arr;
            case 3:
                return call103Arr;
            case 4:
                return call112Arr;
            case 5:
                return callAny;
            case 6:
                return callWho;
            case 7:
                return callIncorrect;
            default:
                return callWho;
        }
    }

    private SoundPlayer audio;

    public CallMode(Phone phone) throws Exception {
        super(phone);
        FunnyDisplayBase display=phone.getDisplay();
        surface = new FunnySurface(display.getSurfaceWidth(), display.getSurfaceHeight());
        callAnimation = new CallAnimation(display);
        callNoAnimation = new CallNoButtonAnim(display);
        onRefresh();
    }

    private boolean handleKeys = true;



    @Override
    public void onClick(FunnyButton funnyButton) {
        if (funnyButton.getId() == R.id.buttonNo) {
            if (isNoActive) {
                isNoActive = false;
                isNoPressed = true;
                handleKeys=false;
                phone.stopSpeaker(true);
                last_type=LAST_TYPE.ABORT;
                callNoAnimation.start();
                audio.PlayMp3(R.raw.busy_signal,this);
                return;
            } else {
                readyToCall();
            }
        }else if(funnyButton.getId()==R.id.buttonYes){
            call();
        }
        if (funnyButton.getKeyMode() == FunnyButton.KeyMode.Numbers) {
            String number = funnyButton.getNumbersText();
            if (number.length() > 0 && handleKeys) {
                phone.stopSpeaker();
                isNoActive = false;
                char num=number.charAt(0);
                last_type=LAST_TYPE.DIAL;
                audio.playKeypadTones(num,this);

                adjust_draw(num);
            }

        }
    }

    private void adjust_draw(char number) {
        dialedNumber = dialedNumber + number;
        int w=(FunnySurfaceUtils.standardCharWidth +1)*FunnySurfaceUtils.scaleX;

        if(dialedNumber.length()>3){
            dialedNumber=dialedNumber.substring(dialedNumber.length()-3,dialedNumber.length());
            //shift left previous ones
            surface.putSurface(surface,  -w  , 0);
            surface.clear( 2 * w ,0,surface.getWidth(),surface.getHeight());

        }
        else if (dialedNumber.length() == 1) {
            surface.clear();
        }
        if (dialedNumber == null || dialedNumber.length() < 1) return;

        int figureRandom = (int) (Math.random() * (FunnySurface.getMaxTypeSupport() - 1)) + 1;
        //exclude white and black

        int colorRandom = (int) (Math.random() * (FunnySurface.getMaxColorSupport() - 2)) + 1;
        int i=dialedNumber.length()-1;
        FunnySurfaceUtils.drawChar(surface,  i * w+w/2 , surface.getHeight()/2, number,
                FunnySurface.supportedColors[colorRandom],
                FunnySurface.supportedTypes[figureRandom], true);
        int length = dialedNumber.length() * w;
        FunnySurface displaySurface=phone.getDisplay().getSurface();
        try {
            displaySurface.lock();
            displaySurface.clear();

            displaySurface.putSurface(surface, (int)(Math.ceil((surface.getWidth()-length)/2.f)), 0);
        }finally {
            displaySurface.unlock();
        }
        phone.getDisplay().render();
    }

    @Override
    public void onRefresh() {
        enableCallbacks=true;
       readyToCall();
    }

    public void readyToCall(){
        audio = phone.getAudio();
        isNoActive = false;
        handleKeys = true;
        phone.deActivateDelay();
        int duration = audio.PlayMp3(R.raw.az_gel_birine_zeng_edek);
        callAnimation.stop(true);
        callNoAnimation.stop(true);
        phone.startSpeaker(duration);
        phone.activateDelay(new UiHandler.DelayObject(getCallSoundArray(0), callPositions[0]), 5000);
        phone.refreshActiveTime(duration);//forward user timing
        phone.changeKeys(FunnyButton.KeyMode.Numbers);
        phone.getDisplay().clear();
        lastDialedIndex = 0;
        dialedNumber = "";
    }


    @Override
    public void onSave() {
        //change delay to standard
        enableCallbacks=false;
        phone.deActivateDelay();
        phone.activateDelay();
        phone.stopSpeaker();
        callAnimation.stop(true);
        callNoAnimation.stop(true);
        audio.StopMp3();
    }

    public void call(){
        if (dialedNumber.length() >= 3) {
            int index;
            switch (dialedNumber) {
                case "000":
                    index = 1;
                    break;
                case "102":
                    index = 2;
                    break;
                case "103":
                    index = 3;
                    break;
                case "112":
                    index = 4;
                    break;
                default:
                    index = 7;
            }
            int[] arr = getCallSoundArray(index);
            lastDialedIndex = index;
            final int position = callPositions[index];
            callPositions[index] = callPositions[index] + 1;
            if (callPositions[index] >= arr.length) {
                callPositions[index] = 0;
            }
            dial_audio_id = arr[position];
            phone.deActivateDelay();
            isNoActive = true;
            isNoPressed = false;
            callAnimation.start();
            handleKeys = false;
            last_type=LAST_TYPE.SUCCESS_CALL;
            audio.PlayMp3(R.raw.dial_tone, this);

            dialedNumber = "";
        }
    }
    @Override
    public void soundPlayFinished() {
        if(!enableCallbacks) return;
        switch (last_type){
            case NONE:
                break;
            case SUCCESS_CALL:
                callAnimation.stop(true);
                if (!isNoPressed) {
                      phone.startSpeaker();
                      last_type=LAST_TYPE.ANSWER;
                      audio.PlayMp3(dial_audio_id, this);
                     }
                break;
            case ABORT:
                callNoAnimation.stop(false);
                readyToCall();
                break;
            case DIAL:
                phone.deActivateDelay();
                phone.activateDelay();
                break;
            case ANSWER:
                phone.stopSpeaker(false);
                handleKeys = true;
                lastAnswerCallResponce(lastDialedIndex);
                break;
        }


    }

    private void lastAnswerCallResponce(int dialedIndex) {
        UiHandler.DelayObject obj;
        if (dialedIndex == 7) dialedIndex = 0;
        obj = new UiHandler.DelayObject(getCallSoundArray(dialedIndex), callPositions[dialedIndex]);
        phone.activateDelay(obj, 5000);
    }
}
