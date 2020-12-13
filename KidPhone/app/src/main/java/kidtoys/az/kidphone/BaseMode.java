package kidtoys.az.kidphone;

import java.io.Serializable;
import java.util.HashMap;

/**
 * BaseMode has basic functions for modes
 * Extends BaseMode to add new modes
 */
public abstract class BaseMode  {

    /**
     * Global state Storage
     */
    private static final HashMap<String,Serializable> stateStorage =new HashMap<>();
    /**
     * Phone interface
     */
    protected Phone phone;

    public BaseMode(Phone phone) throws Exception {
        if(phone==null) {
            throw new Exception("Phone should not be null");
        }

        phone.activateDelay(UiHandler.TIME_DELAY);
        this.phone=phone;

    }

    /**
     * On Click
     * @param funnyButton button
     */
    public abstract void onClick(FunnyButton funnyButton);

    /**
     * It will be called when Mode key pressed
     */
    public void onRefresh(){
        if(phone!=null) {
            phone.activateDelay(UiHandler.TIME_DELAY);
        }
    }

    /**
     * Event for saving states of Mode
     */
    public void onSave(){
        if(phone!=null) {
            phone.deActivateDelay();
            phone.stopSpeaker();
            phone.getAudio().StopMp3();
        }
    }


    /**
     * Gets object from storage by key for  Mode
     * @param key key string
     * @return serializable object
     */
    public synchronized Serializable  getState(String key){
         String realKey=this.getClass().getName()+"_"+key;
         if( stateStorage.containsKey(realKey)){
             return  stateStorage.get(realKey);
         }
        return null;
    }

    /**
     * Put objects  into storage for  Mode
     * @param key key string
     * @param object Serializable object
     */
    public synchronized void putState(String key,Serializable object){
        String realKey=this.getClass().getName()+"_"+key;
         stateStorage.put(realKey, object);
    }

}
