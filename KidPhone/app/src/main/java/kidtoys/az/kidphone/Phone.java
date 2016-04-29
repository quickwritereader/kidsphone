package kidtoys.az.kidphone;

/**
 * interface for accessing phone functions
 */
public interface Phone {

    void changeKeys(FunnyButton.KeyMode newMode);

    FunnyDisplay getDisplay();

    SoundPlayer getAudio();


}
