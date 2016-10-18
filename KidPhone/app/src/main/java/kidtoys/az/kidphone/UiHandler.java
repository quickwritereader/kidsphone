package kidtoys.az.kidphone;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * UiHandler use for handling messages sent from another threads
 */
public class UiHandler extends Handler implements SoundCallBack {

    public static final long TIME_DELAY = 10000;
    private static final String TAG = "UiHandler";
    /**
     * DELAy_MSG will be used to constantly inform about inactivity
     */
    private static final int DELAY_MSG = 1;
    private static int wait_signal = 0;
    private final WeakReference<PhoneActivity> phoneRef;
    Message reSendMsg = null;
    private long timeDelay = TIME_DELAY;
    private long userActivity = System.currentTimeMillis();
    private boolean active = false;
    private DelayObject standard = new DelayObject(SoundPlayer.wait_sounds, 0);


    public UiHandler(PhoneActivity phone) {
        this.phoneRef = new WeakReference<>(phone);
    }

    @Override
    public void soundPlayFinished() {
        if (reSendMsg != null) {
            Log.d(TAG,"audio soundPlayFinished. resend delay");
            PhoneActivity phone = phoneRef.get();
            if (phone != null) {
              phone.stopSpeaker(false);
            }
            sendMsg(reSendMsg, timeDelay);
        }
    }

    private void sendStandardDelay(long delay) {
        this.active = true;
        Message msg = Message.obtain();
        msg.what = UiHandler.DELAY_MSG;
        msg.obj = standard;
        sendMessageDelayed(msg, delay);
    }

    /**
     * refresh userActivity time to current
     */
    public synchronized  void refreshActiveTime() {
            userActivity=System.currentTimeMillis();
            Log.d(TAG,"current active time "+userActivity);
    }

    /**
     * refresh userActivity time to current+ forwarding
     * @param forward will forward as if user pressed later
     */
    public synchronized  void refreshActiveTime(int forward) {
        userActivity = System.currentTimeMillis()+forward;
       // Log.d(TAG,"current active time "+userActivity);
    }

    /**
     * Activate delay message
     */
    public void activateDelay() {
        activateDelay(UiHandler.TIME_DELAY);
    }

    public synchronized void activateDelay(long delay) {
        if (!this.active) {
            Log.d(TAG, "activateDelay");
            timeDelay = TIME_DELAY;
            sendStandardDelay(delay);
            userActivity = System.currentTimeMillis();
        }
    }

    public synchronized void activateDelay(DelayObject object, long delay) {
        if (!this.active) {
            timeDelay = delay;
            Log.d(TAG, "activateDelay");
            this.active = true;
            Message msg = Message.obtain();
            msg.what = UiHandler.DELAY_MSG;
            msg.obj = object;
            sendMessageDelayed(msg, delay);
            userActivity = System.currentTimeMillis();
        }
    }

    private synchronized void sendMsg(Message msg, long delay) {
        if (this.active) {
            sendMessageDelayed(msg, delay);
        }
    }

    public synchronized void deActivateDelay() {
        active = false;
        removeMessages(UiHandler.DELAY_MSG);
        Log.d(TAG, "deActivateDelay");
    }

    @Override
    public void handleMessage(Message inputMessage) {

        if (active) {
            PhoneActivity phone = phoneRef.get();
            if (phone != null) {

                //clone message for sending
                reSendMsg = Message.obtain();
                reSendMsg.what = inputMessage.what;
                reSendMsg.obj = inputMessage.obj;

                long current = System.currentTimeMillis();
                long difference = current - userActivity;
                long new_delay = timeDelay;
                if (difference >= timeDelay) {
                    if (inputMessage.what == DELAY_MSG) {
                        DelayObject object = (DelayObject) inputMessage.obj;
                        int sound = object.getWaitSound();
                        int ret=object.increment();
                        if(ret==0){
                            //if ret=0 it means it reached end
                            //so we will say only this without resending message
                            reSendMsg=null;
                            Log.d(TAG, "say this and end delay packet");
                            int duration=phone.getAudio().PlayMp3(sound);
                            phone.startSpeaker(duration,true);
                        }else {
                            phone.startSpeaker(true);
                            phone.getAudio().PlayMp3(sound, this);
                        }
                    }
                } else {
                    new_delay = timeDelay - difference;
                    //send again
                    sendMsg(reSendMsg, new_delay);
                    reSendMsg = null;//nullify
                }
                Log.d(TAG, " dif:" + difference + " delay: " + new_delay);

            }
        }//if delay
    }

    public static class DelayObject {
        private int[] soundIds = null;
        private int position = 0;


        public DelayObject(int[] SoundIds, int position) {
            if (SoundIds != null) {
                soundIds = SoundIds;
                if (position < 0) position = soundIds.length - 1;
                if (position >= soundIds.length) position = 0;
            }
        }

        public int getWaitSound() {
            if (soundIds != null) {
                return soundIds[position];
            }
            return -1;
        }

        public int increment() {
            position += 1;
            if (position >= soundIds.length) position = 0;
            return position;
        }
    }


}
