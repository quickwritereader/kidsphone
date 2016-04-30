package kidtoys.az.kidphone;


import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class GameMode extends  BaseMode {

    public  Snake snakeGame=null;
    public  SoundPool pool=null;


    public GameMode(Phone phone) throws Exception{
        super(phone);
        onRefresh();
        pool=phone.getAudio().getPool();
    }

    @Override
    public void onClick(FunnyButton funnyButton) {
        if (funnyButton.getKeyMode() == FunnyButton.KeyMode.Numbers) {
            String number = funnyButton.getNumbersText();
            Snake.KeyPress key=null;
             if(number.equals("2")){
                 key= Snake.KeyPress.UP;
             }else if(number.equals("4")){
                 key= Snake.KeyPress.LEFT;
             }else if(number.equals("6")){
                 key= Snake.KeyPress.RIGHT;
             }else if(number.equals("8")){
                 key= Snake.KeyPress.DOWN;
             }
            if(this.snakeGame!=null){
                this.snakeGame.sendEvent(key);
            }
        }
    }

    @Override
    public   void onRefresh(){
        //deactivate delat
        ((UiHandler)phone.getHandler()).deActivateDelay();
        phone.changeKeys(FunnyButton.KeyMode.Numbers);
        if(snakeGame!=null){
            if(snakeGame.isStopped()){
                snakeGame.start();
            }
        }
        else {
            snakeGame=new Snake(this);
            snakeGame.start();
        }

    }



    @Override
    public   void onSave(){
        if(snakeGame!=null){
            snakeGame.save();
            snakeGame.interrupt();
        }
        //reactivate delay
        ((UiHandler)phone.getHandler()).activateDelay();
    }

    /**
     * Snake game implementation     */
    public static class Snake extends Thread{

        public static final String LEN = "len";
        public static final String FRUIT = "fruit";
        public static final String SNAKE = "snake";
        public static final String LAST_KEY = "lastKey";
        public  final FunnyDisplay display;
        private final GameMode mode;
        private int snakeLength;
        private short[] snakePos;
        private KeyPress last;
        private short fruit;
        private FunnySurface map;
        private Random random;

        public enum KeyPress { UP,DOWN,RIGHT,LEFT}
        public ArrayBlockingQueue<KeyPress> events=new ArrayBlockingQueue<KeyPress>(5);
        public boolean stop;
        public  boolean gameRun;
        private SoundPool pool;
        public Snake(GameMode mode){
            this.display=mode.phone.getDisplay();
            this.mode=mode;
            this.stop=false;
        }

        public  boolean isStopped(){
            return stop;
        }

        private short get(int x, int y){
            int ret=(x & 0xFF)<<8 ;
            ret=ret+ (y & 0xFF);
            return (short) (ret);
        }

        private int getX(short pos){
            return  (pos & 0xFF00 )>>8 ;
        }

        private int getY(short pos){
            return  pos & 0xFF  ;
        }

        @Override
        public void run() {
            gameRun=!stop;
            boolean dead=false;
            long time=System.currentTimeMillis();
            Serializable s=this.mode.getState(LEN);
            if(s!=null) snakeLength=(int)s;
            s=this.mode.getState(FRUIT);
            if(s!=null) fruit=(short)s;
            snakePos=(short[])this.mode.getState(SNAKE);
            last=(KeyPress)this.mode.getState(LAST_KEY);

            map=new FunnySurface(display.surfaceWidth,display.surfaceHeight);
            if(last==null) last=KeyPress.RIGHT;
            random=new Random();
            random.setSeed(System.currentTimeMillis());
            if(snakePos==null){
                initGame();
            }
            while (gameRun){

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    gameRun=false;
                    break;
                }

                if(dead && time+1800<System.currentTimeMillis()){
                    time=System.currentTimeMillis();
                    dead=false;
                    events.clear();
                }
                if(!dead && time +180<System.currentTimeMillis()) {
                    time = System.currentTimeMillis();
                    if(events.peek()!=null){
                        KeyPress key=events.poll();
                        boolean reverse=last==KeyPress.RIGHT && key==KeyPress.LEFT || last==KeyPress.LEFT && key==KeyPress.RIGHT
                                ||  last==KeyPress.DOWN && key==KeyPress.UP || last==KeyPress.UP && key==KeyPress.DOWN;
                        if(!reverse) last=key;
                    }
                    //move snake
                    moveSnake( );
                    //check for collision within body
                    dead=checkCollision( );
                    //check for fruit
                    boolean eaten=checkForFruit();
                    if(eaten){
                        generateFruit();
                    }

                    //map data on map
                    mapSnakeOnMap( );
                    mapFruitOnMap( );
                    //display
                    display( );

                    if(eaten && gameRun){
                       mode.playFruit();
                    }
                    if(dead && gameRun){
                        mode.playDead();
                        initGame();
                    }
                    // Log.d("game time in loop ",""+(System.currentTimeMillis()-time) );

                }//timer

            }//run game
            Log.d("game","exited");
            this.mode.putState(LAST_KEY,last);
            this.mode.putState(LEN,snakeLength);
            this.mode.putState(SNAKE,snakePos);
            this.mode.putState(FRUIT,fruit);
            stop=true;
        }

        private void initGame() {
            map=null;
            map=new FunnySurface(display.surfaceWidth,display.surfaceHeight);
            initSnake();
            mapSnakeOnMap( );
            generateFruit();
            mapFruitOnMap();
        }

        private void mapSnakeOnMap() {
            map.putDot(getX(snakePos[0]),getY(snakePos[0]), FunnySurface.DotColor.Yellow, FunnySurface.DotType.Circle);
            map.putDot(getX(snakePos[snakeLength]) ,getY(snakePos[snakeLength ]),FunnySurface.DotColor.Black,
                    FunnySurface.DotType.None);
        }

        private void mapFruitOnMap() {
            map.putDot(getX(fruit),getY(fruit), FunnySurface.DotColor.Red, FunnySurface.DotType.Heart);
        }

        private boolean checkCollision() {
            int x=getX(snakePos[0]);
            int y=getY(snakePos[0]);
            return  map.getDotType(x,y)== FunnySurface.DotType.Circle;
        }

        private boolean checkForFruit() {
            if(snakePos[0]==fruit){
                snakeLength+=1;
                if (snakeLength>snakePos.length ) {
                    snakeLength=snakePos.length;
                }
                return  true;
            }
            return false;
        }

        private void generateFruit( ) {
            int x=random.nextInt(map.getWidth()-1);
            int y=random.nextInt(map.getHeight()-1);
            //if on body move it away untill it is not
            out:
            if(map.getDotType(x,y)== FunnySurface.DotType.Circle){
                //find first empty
                for(int i=0;i<map.getWidth();i++){
                    for(int j=0;i<map.getHeight();j++){
                        int newX =x+i;
                        int newY=y+j;
                        if(newX>=map.getWidth())newX=newX-map.getWidth();
                        if(newY>=map.getHeight()) newY=newY-map.getHeight();
                        if(map.getDotType(newX,newY)!= FunnySurface.DotType.Circle){
                            x=newX;
                            y=newY;
                            break out;
                        }
                    }
                }

            }
            fruit=get(x,y);
        }

        private void display() {
            if(gameRun) {
                if (display.getMainSurface().tryLock()) {
                    display.getMainSurface().putSurface(map, 0, 0);
                    display.getMainSurface().unlock();
                }
                display.postInvalidate();
            }
        }

        private void moveSnake() {
            for(int i=snakeLength ;i> 0;i--){
                snakePos[i ]=snakePos[i-1];
            }

            int x=getX(snakePos[0]);
            int y=getY(snakePos[0]);
            switch (last){
                case LEFT:
                    x-=1;
                    break;
                case RIGHT:
                    x+=1;
                    break;
                case UP:
                    y-=1;
                    break;
                case DOWN:
                   y+=1;
                    break;
            }
            if (x<0)x=map.getWidth()-1;
            if(x>=map.getWidth()) x=0;
            if(y<0) y=map.getHeight()-1;
            if(y>=map.getHeight())y=0;
            snakePos[0]=get(x,y);
        }

        private void initSnake() {
            snakePos=null;
            snakePos=new short[ display.surfaceHeight*display.surfaceWidth/2 ];
            snakePos[0]=get(6,6);
            snakePos[1]=get(5,6);
            snakePos[2]=get(4,6);
            snakeLength=3;
        }

        public void sendEvent(KeyPress event){
            events.offer(event);
        }

        public    void save(){
            gameRun=false;
        }
    }

    private void playDead() {

        pool.play(phone.getAudio().poolAudio2,1, 1, 0, 0, 1);
        Runtime.getRuntime().gc();
    }

    private void playFruit() {
        pool.play(phone.getAudio().poolAudio1,1, 1, 0, 0, 1);
    }
}
