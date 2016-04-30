package kidtoys.az.kidphone;

import android.os.Handler;

/**
 * interface for accessing phone functions
 */
public interface Phone {

    void changeKeys(FunnyButton.KeyMode newMode);

    FunnyDisplay getDisplay();

    SoundPlayer getAudio();

    Handler getHandler();


}
