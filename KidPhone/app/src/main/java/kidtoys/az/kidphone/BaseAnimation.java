package kidtoys.az.kidphone;

/**
 * Created by abdurrauf on 7/10/16.
 */
public abstract class BaseAnimation {

    public interface RenderCallback {
         void RenderSurfaceOnMain();
    }

    private  AnimThread anim;
    private  final FunnyDisplayBase display;


    public BaseAnimation(FunnyDisplayBase display) {
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
        private final FunnyDisplayBase display;
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
        public AnimThread(BaseAnimation animation, FunnyDisplayBase display, int duration, boolean restoreOld, boolean isLoop) {
            this.duration=duration;
            this.display=display;
            this.restoreOld=restoreOld;
            this.surface=new FunnySurface(display.getSurfaceWidth(),display.getSurfaceHeight());
            this.baseAnimation=animation;
            isLoopBased=isLoop;
        }



        @Override
        public void run() {
            long startTime=System.currentTimeMillis();
            boolean forced=false;
            if(restoreOld){
                this.prevSurface=null;
                this.prevSurface=display.getSurfaceSnapshot();
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
                    display.copyToSurface(prevSurface);
                    display.postRender();
                }else{
                    draw();
                }

            }

        }

        private void draw() {
            if(isAnimRun()) display.copyToSurface(surface );
            else display.clear();
            display.postRender();
        }


        @Override
        public void RenderSurfaceOnMain() {
            if(isAnimRun())draw();
        }
    }


}
