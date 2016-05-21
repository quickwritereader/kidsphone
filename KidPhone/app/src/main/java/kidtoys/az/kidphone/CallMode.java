package kidtoys.az.kidphone;

import android.media.MediaPlayer;

/**
 * Phone calls for kids
 */
public class CallMode extends BaseMode implements  CallModeInterface{

    public static String callNumber;
    public static int id_call000 = 1;
    public static int id_call102 = 1;
    public static int id_call103 = 1;
    public static int id_call112 = 1;
    public static int id_callwrong = 1;

    public CallMode(Phone phone) throws Exception {
        super(phone);
        onRefresh();
        callNumber = "";
    }
    private boolean handleKeys=true;

    private CallMode callMode=this;

    @Override
    public void onClick(FunnyButton funnyButton) {
        if (funnyButton.getKeyMode() == FunnyButton.KeyMode.Numbers) {
            String number = funnyButton.getNumbersText();
            if (number.length() > 0 && handleKeys) {
                callNumber = callNumber + number;
                if(callNumber.length()>=3) handleKeys=false;
                phone.getAudio().playKeypadTones(number.charAt(0));
                SoundPlayer.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (callNumber.length() >= 3) {
                            switch (callNumber) {
                                case "000":
                                    Call(callNumber, id_call000,callMode);
                                    if (id_call000 == 2) {
                                        id_call000 = 0;
                                    }
                                    id_call000++;
                                    break;
                                case "102":
                                    Call(callNumber, id_call102,callMode);
                                    if (id_call102 == 3) {
                                        id_call102 = 0;
                                    }
                                    id_call102++;
                                    break;
                                case "103":
                                    Call(callNumber, id_call103,callMode);
                                    if (id_call103 == 2) {
                                        id_call103 = 0;
                                    }
                                    id_call103++;
                                    break;
                                case "112":
                                    Call(callNumber, id_call112,callMode);
                                    if (id_call112 == 3) {
                                        id_call112 = 0;
                                    }
                                    id_call112++;
                                    break;
                                default:
                                    Call(callNumber, id_callwrong,callMode);
                                    if (id_callwrong == 2) {
                                        id_callwrong = 0;
                                    }
                                    id_callwrong++;
                            }
                            callNumber = "";
                        }
                    }
                });
            }

        }
    }

    @Override
    public void onRefresh() {
        phone.getAudio().playCallAnyOne();
        phone.changeKeys(FunnyButton.KeyMode.Numbers);
        phone.getDisplay().clear();
        handleKeys=true;
        callNumber = "";
    }

    @Override
    public void onSave() {
        //putState("key",object);
    }

    public void Call(String number, int callId ,CallModeInterface callback) {
        phone.getAudio().playCall(number, callId,callback);
    }

    @Override
    public void finished() {
        handleKeys=true;
    }
}
