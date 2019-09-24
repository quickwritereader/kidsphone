package kidtoys.az.kidphone;


import android.media.SoundPool;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Game mode for kids
 */
public class GameMode extends BaseMode {

    private Snake snakeGame = null;
    private SoundPool pool = null;


    public GameMode(Phone phone) throws Exception {
        super(phone);
        onRefresh();
        pool = phone.getAudio().getPool();
    }

    @Override
    public void onClick(FunnyButton funnyButton) {
        if (funnyButton.getKeyMode() == FunnyButton.KeyMode.Numbers) {
            String number = funnyButton.getNumbersText();
            Snake.KeyPress key = null;
            switch (number) {
                case "4":
                    key = Snake.KeyPress.LEFT;
                    break;
                case "6":
                    key = Snake.KeyPress.RIGHT;
                    break;
                case "2":
                    key = Snake.KeyPress.UP;
                    break;
                case "8":
                    key = Snake.KeyPress.DOWN;
                    break;
            }
            if (this.snakeGame != null) {
                this.snakeGame.sendEvent(key);
            }
        }
    }

    @Override
    public void onRefresh() {
        //deactivate delay
        phone.getDisplay().attachAnimation(null);
        phone.deActivateDelay();
        phone.getDisplay().setDraw_grid(true);
        phone.changeKeys(FunnyButton.KeyMode.Numbers);
        if (snakeGame != null) {
            if (snakeGame.isStopped()) {
                snakeGame = null;

            }
        }
        if (snakeGame == null) {/**/
            snakeGame = new Snake(this);
            snakeGame.start();
        }


        changeIrrelevantKeysVisibility(View.INVISIBLE);
    }

    private void changeIrrelevantKeysVisibility(int visible) {
        ViewGroup keysGroup=(ViewGroup)phone.getViewById(R.id.KeysGroup);
        int childCount = keysGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {

            View v = keysGroup.getChildAt(i);
            if (v instanceof FunnyButton) {
                FunnyButton funnyButton = (FunnyButton) v;
                if (funnyButton.getKeyMode() != FunnyButton.KeyMode.System) {
                    String number = funnyButton.getNumbersText();
                    switch (number) {
                        case "2":
                        case "8":
                        case "4":
                        case "6":
                            break;
                        default:
                            funnyButton.setVisibility(visible);
                    }//switch
                }
            }//instance of

        }
    }


    @Override
    public void onSave() {
        if (snakeGame != null) {
            snakeGame.save();
            snakeGame.interrupt();
        }
        phone.getDisplay().setDraw_grid(false);
        changeIrrelevantKeysVisibility(View.VISIBLE);
        //reactivate delay
        phone.activateDelay();
    }

    private void playDead() {
        pool.play(phone.getAudio().poolAudio2, 1, 1, 0, 0, 1);
    }

    private void playFruit() {
        pool.play(phone.getAudio().poolAudio1, 1, 1, 0, 0, 1);
    }

    /**
     * Snake game implementation
     */
    public static class Snake extends Thread {

        public static final String LEN = "len";
        public static final String FRUIT = "fruit";
        public static final String SNAKE = "snake";
        public static final String LAST_KEY = "lastKey";
        public static final int CYCLE_TIME_MS = 150;
        public static final int DEAD_TIME_MS = 1800;
        static final int wx=(FunnySurfaceUtils.standardCharWidth +1)*FunnySurfaceUtils.scaleX;
        public final FunnyDisplayBase display;
        public final ArrayBlockingQueue<KeyPress> events = new ArrayBlockingQueue<>(5);
        private final GameMode mode;
        private final FunnySurface.DotColor snakeColor = FunnySurface.DotColor.Green;
        private final FunnySurface.DotColor fruitColor = FunnySurface.DotColor.Red;
        private final FunnySurface.DotType snakeHead = FunnySurface.DotType.Circle;
        private final FunnySurface.DotType snakeBody = FunnySurface.DotType.Square;
        private final FunnySurface.DotType snakeTail = FunnySurface.DotType.Romb;
        public boolean stop;
        public boolean gameRun;
        private int snakeLength;
        private short[] snakePos;

        private Direction last;
        private short fruit;
        private FunnySurface map;
        private FunnySurface panel;
        private Random random;
        public Snake(GameMode mode) {
            this.display = mode.phone.getDisplay();
            this.mode = mode;
            this.stop = false;
        }

        public boolean isStopped() {
            return stop;
        }

        private short get(int x, int y) {
            int ret = (x & 0xFF) << 8;
            ret = ret + (y & 0xFF);
            return (short) (ret);
        }

        private int getX(short pos) {
            return (pos & 0xFF00) >> 8;
        }

        private int getY(short pos) {
            return pos & 0xFF;
        }

        @Override
        public void run() {
            gameRun = !stop;
            boolean dead = false;
            long time = System.currentTimeMillis();
            Serializable s = this.mode.getState(LEN);
            if (s != null) snakeLength = (int) s;
            s = this.mode.getState(FRUIT);
            if (s != null) fruit = (short) s;
            snakePos = (short[]) this.mode.getState(SNAKE);
            last = (Direction) this.mode.getState(LAST_KEY);

            map = new FunnySurface(display.getSurfaceWidth(), display.getSurfaceHeight());
            panel = new FunnySurface(display.getSurfaceWidth(), display.getSurfaceHeight());
            random = new Random();
            random.setSeed(System.currentTimeMillis());
            if (snakePos == null || last == null) {
                initGame();
            } else {
                mapSnakeOnMapFull();
            }
            while (gameRun) {

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    gameRun = false;
                    break;
                }

                if (dead && time + DEAD_TIME_MS < System.currentTimeMillis()) {
                    time = System.currentTimeMillis();
                    dead = false;
                    events.clear();
                }
                if (!dead && time + CYCLE_TIME_MS < System.currentTimeMillis()) {
                    time = System.currentTimeMillis();
                    if (events.peek() != null) {
                        KeyPress key = events.poll();
                        boolean reverse = last == Direction.RIGHT && key == KeyPress.LEFT || last == Direction.LEFT && key == KeyPress.RIGHT
                                || last == Direction.DOWN && key == KeyPress.UP || last == Direction.UP && key == KeyPress.DOWN;
                        if (!reverse) {
                            switch(key) {
                                case RIGHT:
                                    last=Direction.RIGHT;
                                    break;
                                case LEFT:
                                    last=Direction.LEFT;
                                    break;
                                case UP:
                                    last=Direction.UP;
                                    break;
                                case DOWN:
                                    last=Direction.DOWN;
                                    break;
                            }
                        }
                    }
                    //move snake
                    moveSnake();
                    //check for collision within body
                    dead = checkCollision();
                    //check for fruit
                    boolean eaten = checkForFruit();
                    if (eaten) {
                        generateFruit();
                    }

                    //map data on map
                    drawScore();
                    mapSnakeOnMap();
                    mapFruitOnMap();
                    //display
                    display();

                    if (eaten && gameRun) {
                        mode.playFruit();
                    }
                    if (dead && gameRun) {
                        mode.playDead();
                        initGame();
                    }
                    // Log.d("game time in loop ",""+(System.currentTimeMillis()-time) );

                }//timer

            }//run game
            if(BuildConfig.DEBUG) {
                Log.d("game", "exited");
            }
            this.mode.putState(LAST_KEY, last);
            this.mode.putState(LEN, snakeLength);
            this.mode.putState(SNAKE, snakePos);
            this.mode.putState(FRUIT, fruit);
            stop = true;
        }

        private void initGame() {
            map = null;
            map = new FunnySurface(display.getSurfaceWidth(), display.getSurfaceHeight());
            panel = new FunnySurface(display.getSurfaceWidth(), display.getSurfaceHeight());
            last = Direction.RIGHT;
            initSnake();
            drawScore();
            mapSnakeOnMap();
            generateFruit();
            mapFruitOnMap();
        }


        private void   drawScore(){
            int score= snakeLength-3;
            String nn = String.valueOf(score);
            panel.clear();
            int minx= nn.length();
            minx=minx>4?4:minx;
            for(int i=0;i<minx;i++){
                FunnySurfaceUtils.drawChar(panel,  i * wx+wx/2 , panel.getHeight()/2,
                        nn.charAt(i) ,
                         FunnySurface.DotColor.Magenta,
                        FunnySurface.DotType.Hexagon, true);
            }

        }
        private void mapSnakeOnMapFull() {
            if (snakeLength < 3) return;
            if (snakePos[snakeLength] != 0xFFFF) {
                map.putDot(getX(snakePos[snakeLength]), getY(snakePos[snakeLength]), FunnySurface.DotColor.Black,
                        FunnySurface.DotType.None);
            }
            map.putDot(getX(snakePos[0]), getY(snakePos[0]), snakeColor, snakeHead);
            for (int i = 1; i < snakeLength; i++) {
                map.putDot(getX(snakePos[i]), getY(snakePos[i]), snakeColor, snakeBody);
            }

            map.putDot(getX(snakePos[snakeLength - 1]), getY(snakePos[snakeLength - 1]), snakeColor, snakeTail);
        }

        private void mapSnakeOnMap() {
            //clear reduced tail
            if (snakePos[snakeLength] != 0xFFFF) {
                map.clearDot(getX(snakePos[snakeLength]), getY(snakePos[snakeLength]));
            }
            map.putDot(getX(snakePos[0]), getY(snakePos[0]), snakeColor, snakeHead);
            map.putDot(getX(snakePos[1]), getY(snakePos[1]), snakeColor, snakeBody);
            map.putDot(getX(snakePos[snakeLength - 1]), getY(snakePos[snakeLength - 1]), snakeColor, snakeTail);

        }

        private void mapFruitOnMap() {
            map.putDot(getX(fruit), getY(fruit), fruitColor, FunnySurface.DotType.Heart);
        }

        private boolean checkCollision() {
            int x = getX(snakePos[0]);
            int y = getY(snakePos[0]);
            boolean reducedTail = (snakePos[0] == snakePos[snakeLength]);
            FunnySurface.DotType type = map.getDotType(x, y);
            return type != FunnySurface.DotType.None && !reducedTail && type != FunnySurface.DotType.Heart;
        }

        private boolean checkForFruit() {
            if (snakePos[0] == fruit) {
                snakeLength += 1;
                if (snakeLength >= snakePos.length) {
                    snakeLength = snakePos.length - 1;
                }
                return true;
            }
            return false;
        }

        private void generateFruit() {
            int x = random.nextInt(map.getWidth() - 1);
            int y = random.nextInt(map.getHeight() - 1);
            //if on body move it away until it is not
            out:
            if (map.getDotType(x, y) != FunnySurface.DotType.None) {
                //find first empty
                for (int i = 0; i < map.getWidth(); i++) {
                    for (int j = 0; i < map.getHeight(); j++) {
                        int newX = x + i;
                        int newY = y + j;
                        if (newX >= map.getWidth()) newX = newX - map.getWidth();
                        if (newY >= map.getHeight()) newY = newY - map.getHeight();
                        if (map.getDotType(newX, newY) == FunnySurface.DotType.None) {
                            x = newX;
                            y = newY;
                            break out;
                        }
                    }
                }

            }
            fruit = get(x, y);
        }

        private void display() {
            if (gameRun) {
                panel.putSurfaceOverlay(map,0,0);
                display.copyToSurface(panel);
                display.postRender();
            }
        }

        private void moveSnake() {
            System.arraycopy(snakePos, 0, snakePos, 1, snakeLength);

            int x = getX(snakePos[0]);
            int y = getY(snakePos[0]);
            switch (last) {
                case LEFT:
                    x -= 1;
                    break;
                case RIGHT:
                    x += 1;
                    break;
                case UP:
                    y -= 1;
                    break;
                case DOWN:
                    y += 1;
                    break;
            }
            if (x < 0) x = map.getWidth() - 1;
            if (x >= map.getWidth()) x = 0;
            if (y < 0) y = map.getHeight() - 1;
            if (y >= map.getHeight()) y = 0;
            snakePos[0] = get(x, y);
        }

        private void initSnake() {
            snakePos = null;
            snakePos = new short[display.getSurfaceWidth() * display.getSurfaceHeight()*2/3];
            for (int i = 0; i < snakePos.length; i++) {
                snakePos[i] = (short) 0xFFFF;
            }
            snakePos[0] = get(6, 6);
            snakePos[1] = get(5, 6);
            snakePos[2] = get(4, 6);
            snakeLength = 3;
        }

        public void sendEvent(KeyPress event) {
            events.offer(event);
        }

        public void save() {
            gameRun = false;
        }

        public enum KeyPress { RIGHT, LEFT, UP,DOWN}
        public enum Direction {RIGHT, LEFT, UP,DOWN}
    }
}
