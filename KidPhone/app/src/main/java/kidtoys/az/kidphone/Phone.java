package kidtoys.az.kidphone;

import android.os.Handler;
import android.view.View;

/**
 * interface for accessing phone functions
 */
public interface Phone {

    void changeKeys(FunnyButton.KeyMode newMode);

    FunnyDisplay getDisplay();

    SoundPlayer getAudio();

    Handler getHandler();

    View getViewById(int id);

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

}
