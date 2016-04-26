package kidtoys.az.kidphone;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * UiHandler use for handling messages sent from another threads
 */
public class UiHandler extends Handler {

    public static  final String TAG="UiHandler";

    /**
     * DELAy_MSG will be used to constantly inform about inactivity
     */
    public static final int DELAY_MSG=1;
    public static final long TIME_DELAY=10000;
    public static int wait_signal=0;
    public boolean active=false;


    final WeakReference<PhoneActivity> phoneRef;

    public UiHandler(PhoneActivity phone){
        this.phoneRef=new WeakReference<PhoneActivity>(phone);
    }

    private void sendDelay(long delay) {
        this.active = true;
        Message msg = Message.obtain();
        msg.what = UiHandler.DELAY_MSG;
        sendMessageDelayed(msg, delay);
    }


    public synchronized void activateDelay(long delay){
        if(this.active==false) {
            Log.d(TAG,"activateDelay");
            sendDelay(delay);
        }
    }
    public synchronized void reSendDelay(long delay){
        if(this.active==true) {
            sendDelay(delay);
        }
    }


    /**
     * Activate delay message
     */
    public void activateDelay(){
        activateDelay(UiHandler.TIME_DELAY);
    }

    public synchronized  void deActivateDelay(){
        active=false;
        removeMessages(UiHandler.DELAY_MSG);
        Log.d(TAG, "deActivateDelay");
    }


    @Override
    public void handleMessage(Message inputMessage){

        if(inputMessage.what==DELAY_MSG && active==true){
            PhoneActivity phone=phoneRef.get();
            if(phone!=null){
                long current=System.currentTimeMillis();
                long difference=current-phone.userActivityTime;
                long new_delay=TIME_DELAY;
                if(difference>=TIME_DELAY){
                    phone.playWait(wait_signal);
                    wait_signal+=1;
                    if(wait_signal>Integer.MAX_VALUE-2) {
                        wait_signal = 0;
                    }
                } else{
                    new_delay=TIME_DELAY-difference;
                }
                Log.d(TAG, " dif:"+difference+" delay: "+new_delay);
                //send again
                reSendDelay(new_delay);
            }
        }//if delay
    }



}
