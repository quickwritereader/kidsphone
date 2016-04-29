package kidtoys.az.kidphone;

import android.os.Bundle;
import android.view.Display;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

/**
 * BaseMode has basic functions for modes
 * Extends BaseMode to add new modes
 */
public abstract class BaseMode  {


    public  static final HashMap<String,Serializable> stateObject=new HashMap<>();
    protected Phone phone=null;

    public BaseMode(Phone phone) throws Exception {
        if(phone==null) {
            throw new Exception("Phone should not be null");
        }
        this.phone=phone;

    }

    public abstract void onClick(FunnyButton funnyButton);

    public abstract void onRefresh();

    public abstract void onSave();


    public Serializable getState(String key){
         String realKey=this.getClass().getName()+"_"+key;
         if( stateObject.containsKey(realKey)){
             return  stateObject.get(realKey);
         }
        return null;
    }

    public void putState(String key,Serializable object){
        String realKey=this.getClass().getName()+"_"+key;
         stateObject.put(realKey,object);
    }

}
