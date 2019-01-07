package kidtoys.az.kidphone;

/**
 * Created by abdurrauf on 7/10/16.
 */
public abstract class BaseAnimation {

    public interface RenderCallback {
         void RenderSurfaceOnMain();
    }

    private  AnimThread anim;
    private  final FunnyDisplay display;


    public BaseAnimation(FunnyDisplay display) {
        this.display=display;
    }

    public void start(){
       start(-1);
    }

    public void start(int duration){
        this.display.attachAnimation(this);
        stop(true);
        anim=new AnimThread(this,display,duration,false,isLooped());
        anim.start();
    }

    public void start(int duration,boolean restoreOld){
        this.display.attachAnimation(this);
        stop(true);
        anim=new AnimThread(this,display,duration,restoreOld,isLooped());
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

    protected abstract boolean isLooped( );
    protected abstract boolean onLoopDraw(FunnySurface surface,RenderCallback renderClbk);

    private static class AnimThread extends  Thread implements RenderCallback
    {
        private int duration=-1;
        private final FunnyDisplay display;
        private FunnySurface surface;
        private BaseAnimation baseAnimation;
        private FunnySurface prevSurface;
        private boolean animRun=true;
        private boolean immediate=false;
        private boolean restoreOld=false;
        private boolean isLoopBased=false;
        public synchronized  boolean isAnimRun() {
            return animRun;
        }

        public synchronized  void setAnimRun(boolean animRun,boolean immediate) {
            this.animRun = animRun;
            this.immediate=immediate;
        }

        public AnimThread(BaseAnimation animation,FunnyDisplay display,boolean restoreOld) {
            this.display=display;
            this.surface=new FunnySurface(display.surfaceWidth,display.surfaceHeight);
            this.restoreOld=restoreOld;
            this.baseAnimation=animation;
        }

        public AnimThread(BaseAnimation animation,FunnyDisplay display,boolean restoreOld,boolean isLoop) {
            this.display=display;
            this.surface=new FunnySurface(display.surfaceWidth,display.surfaceHeight);
            this.restoreOld=restoreOld;
            this.baseAnimation=animation;
            isLoopBased=isLoop;
        }
        public AnimThread(BaseAnimation animation,FunnyDisplay display,int duration,boolean restoreOld,boolean isLoop) {
            this.duration=duration;
            this.display=display;
            this.restoreOld=restoreOld;
            this.surface=new FunnySurface(display.surfaceWidth,display.surfaceHeight);
            this.baseAnimation=animation;
            isLoopBased=isLoop;
        }



        @Override
        public void run() {
            long startTime=System.currentTimeMillis();
            boolean forced=false;
            if(restoreOld){
                this.prevSurface=new FunnySurface(display.surfaceWidth,display.surfaceHeight);
                FunnySurface mainSurface = display.getMainSurface();
                if (mainSurface.tryLock() ) {
                    try {
                        prevSurface.putSurface(mainSurface,0,0); 
                    }finally {
                        mainSurface.unlock();
                    }
                }
            }
            while (isAnimRun()){
                   if(isLoopBased){
                       baseAnimation.onLoopDraw(surface,this);
                       animRun=false;
                   }else {
                       if (baseAnimation.onDraw(surface)) {
                           draw();
                       }
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
                if(restoreOld){
                    restoreOldDisplay();
                }else{
                    draw( );
                }


            }

        }

        private  void restoreOldDisplay(){
            FunnySurface mainSurface = display.getMainSurface();
            if (mainSurface.tryLock() ) {
                try {
                    if(prevSurface!=null) mainSurface.putSurface(prevSurface, 0, 0);
                    else mainSurface.clear();
                }finally {
                    mainSurface.unlock();
                }
                display.postInvalidate();
            }
        }
        private void draw( ) {
            FunnySurface mainSurface = display.getMainSurface();
            if (mainSurface.tryLock() ) {
                try {
                   if(isAnimRun()) mainSurface.putSurface(surface, 0, 0);
                    else mainSurface.clear();
                }finally {
                    mainSurface.unlock();
                }
                display.postRender();
            }
        }

        @Override
        public void RenderSurfaceOnMain() {
            if(isAnimRun())draw();
        }
    }


}
