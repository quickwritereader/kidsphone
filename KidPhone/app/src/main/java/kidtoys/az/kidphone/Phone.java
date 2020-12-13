package kidtoys.az.kidphone;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

/**
 * interface for accessing phone functions
 */
public interface Phone {

    void changeKeys(FunnyButton.KeyMode newMode);

    FunnyDisplayBase getDisplay();

    SoundPlayer getAudio();

    Handler getHandler();

    View getViewById(int id);

    ViewGroup getKeysGroup();

    void activateDelay();

    void activateDelay(UiHandler.DelayObject object,long delay);

    void deActivateDelay();

    void refreshActiveTime(int forwardDelay);

    void startSpeaker();

    void startSpeaker(int duration);

    void startSpeaker(boolean restoreOld);

    void startSpeaker(int duration, boolean restoreOld);

    void stopSpeaker();

    void stopSpeaker(boolean force) ;

    void activateDelay(long timeDelay);
}
