package kidtoys.az.kidphone;

/**
 * Created by abdurrauf on 7/10/16.
 */
public abstract class BaseAnimation {

    private  AnimThread anim;
    private  final FunnyDisplay display;
    public BaseAnimation(FunnyDisplay display) {
        this.display=display;
    }

    public void start(){
       start(-1);
    }

    public void start(int duration){
        stop(true);
        anim=new AnimThread(this,display,duration);
        anim.start();
    }

    public void stop(boolean force){
        if(anim!=null && anim.isAlive()){

            if(force){
                anim.setAnimRun(false,true);
                anim.interrupt();
            }else{
                anim.setAnimRun(false,false);
            }
        }
        anim=null;

    }



    protected abstract boolean onDraw(FunnySurface surface);


    private static class AnimThread extends  Thread
    {
        private int duration=-1;
        private final FunnyDisplay display;
        private FunnySurface surface;
        private BaseAnimation baseAnimation;

        private boolean animRun=true;
        private boolean immediate=false;

        public synchronized  boolean isAnimRun() {
            return animRun;
        }

        public synchronized  void setAnimRun(boolean animRun,boolean immediate) {
            this.animRun = animRun;
            this.immediate=immediate;
        }

        public AnimThread(BaseAnimation animation,FunnyDisplay display) {
            this.display=display;
            this.surface=new FunnySurface(display.surfaceWidth,display.surfaceHeight);
            this.baseAnimation=animation;
        }
        public AnimThread(BaseAnimation animation,FunnyDisplay display,int duration) {
            this.duration=duration;
            this.display=display;
            this.surface=new FunnySurface(display.surfaceWidth,display.surfaceHeight);
            this.baseAnimation=animation;
        }



        @Override
        public void run() {
            long startTime=System.currentTimeMillis();
            boolean forced=false;
            while (isAnimRun()){
                   if(baseAnimation.onDraw(surface)) {
                       draw();
                   }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    animRun=false;
                    forced=true;
                     break;
                }
                if(this.duration>0 && startTime+this.duration<System.currentTimeMillis()){
                    animRun=false;
                    break;
                }
            }
            if(!forced & !immediate){
                draw();
            }

        }

        private void draw() {
            FunnySurface mainSurface = display.getMainSurface();
            if (mainSurface.tryLock() ) {
                try {
                   if(isAnimRun()) mainSurface.putSurface(surface, 0, 0);
                    else mainSurface.clear();
                }finally {
                    mainSurface.unlock();
                }
                display.postInvalidate();
            }
        }
    }


}
