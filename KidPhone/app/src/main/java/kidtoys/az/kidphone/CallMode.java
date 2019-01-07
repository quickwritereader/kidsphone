package kidtoys.az.kidphone;

import android.media.MediaPlayer;

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
    private static int[] callIncorret = {R.raw.az_incorrect_number_1, R.raw.az_incorrect_number_2};
    BaseAnimation callAnimation;
    BaseAnimation callNoAnimation;
    boolean isNoActive = false;
    boolean isNoPressed = false;


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
                return callIncorret;
            default:
                return callWho;
        }
    }

    private SoundPlayer audio;

    public CallMode(Phone phone) throws Exception {
        super(phone);
        surface = new FunnySurface(phone.getDisplay().surfaceWidth, phone.getDisplay().surfaceHeight);
        callAnimation = new CallAnimation(phone.getDisplay());
        callNoAnimation = new CallNoButtonAnim(phone.getDisplay());
        onRefresh();
    }

    private boolean handleKeys = true;

    private CallMode callMode = this;

    @Override
    public void onClick(FunnyButton funnyButton) {
        if (funnyButton.getId() == R.id.buttonNo) {
            if (isNoActive) {
                isNoActive = false;
                isNoPressed = true;
                phone.stopSpeaker(true);
                callNoAnimation.start();
                audio.PlayMp3(R.raw.busy_signal, new SoundCallBack() {
                    @Override
                    public void soundPlayFinished() {
                        callNoAnimation.stop(true);
                        isNoActive = true;
                        onRefresh();
                    }
                });
                return;
            } else {
                onRefresh();
            }
        }
        if (funnyButton.getKeyMode() == FunnyButton.KeyMode.Numbers) {
            String number = funnyButton.getNumbersText();
            if (number.length() > 0 && handleKeys) {
                phone.stopSpeaker();
                isNoActive = false;
                dialedNumber = dialedNumber + number;
                //if first time change wait to who
                if (dialedNumber.length() == 1) {
                    phone.deActivateDelay();
                    callModeWait(0);
                } else if (dialedNumber.length() >= 3) {
                    phone.deActivateDelay();
                    handleKeys = false;
                }

                audio.playKeypadTones(number.charAt(0));
                draw(dialedNumber);
                SoundPlayer.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
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
                            final int audio_id = arr[position];
                            // phone.startSpeaker();
                            //audio.playCall( arr[position],callMode);
                            isNoActive = true;
                            isNoPressed = false;
                            callAnimation.start();
                            audio.PlayMp3(R.raw.dial_tone, new SoundCallBack() {
                                @Override
                                public void soundPlayFinished() {
                                    if (!isNoPressed) {
                                        callAnimation.stop(true);
                                        phone.startSpeaker();
                                        audio.PlayMp3(audio_id, callMode);
                                    }
                                }
                            });

                            dialedNumber = "";
                        }
                    }
                });
            }

        }
    }

    private void draw(String dialedNumber) {
        if (dialedNumber == null || dialedNumber.length() < 1) return;
        int figureRandom = (int) (Math.random() * (FunnySurface.getMaxTypeSupport() - 1)) + 1;
        int colorRandom = (int) (Math.random() * (FunnySurface.getMaxColorSupport() - 2)) + 1;//exclude white and black
        if (dialedNumber.length() == 1) {
            surface.clear();
        }
        int i = dialedNumber.length() - 1;
        char Char = dialedNumber.charAt(dialedNumber.length() - 1);
        int length = dialedNumber.length() * 7;
        FunnySurfaceUtils.drawChar(surface, i * 7, 0, dialedNumber.charAt(i), FunnySurface.supportedColors[colorRandom],
                FunnySurface.supportedTypes[figureRandom], false);
        phone.getDisplay().clear();

        phone.getDisplay().getMainSurface().putSurface(surface, (surface.getWidth() - length) / 2, 4);
        phone.getDisplay().render();
    }

    @Override
    public void onRefresh() {
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
        phone.deActivateDelay();
        phone.activateDelay();
        phone.stopSpeaker();
        callAnimation.stop(true);
        callNoAnimation.stop(true);
    }


    @Override
    public void soundPlayFinished() {

        callModeWait(lastDialedIndex);
        phone.stopSpeaker(false);
        handleKeys = true;
    }

    private void callModeWait(int dialedIndex) {
        UiHandler.DelayObject obj;
        if (dialedIndex == 7) dialedIndex = 0;
        obj = new UiHandler.DelayObject(getCallSoundArray(dialedIndex), callPositions[dialedIndex]);
        phone.activateDelay(obj, 5000);
    }
}
