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

    public void deActivateDelay();

    public void refreshActiveTime(int forwardDelay);

    public void startSpeaker();

    public void startSpeaker(int duration);

    public  void stopSpeaker();

    public void stopSpeaker(boolean force) ;

}
