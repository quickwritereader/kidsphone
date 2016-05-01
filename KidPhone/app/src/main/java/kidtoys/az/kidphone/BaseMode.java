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
    protected Phone phone=null;

    public BaseMode(Phone phone) throws Exception {
        if(phone==null) {
            throw new Exception("Phone should not be null");
        }
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
    public abstract void onRefresh();

    /**
     * Event for saving states of Mode
     */
    public abstract void onSave();


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
