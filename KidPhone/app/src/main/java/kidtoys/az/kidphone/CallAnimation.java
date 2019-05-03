package kidtoys.az.kidphone;

/**
 * Created by ramil on 24.07.2016.
 */
public class CallAnimation extends BaseAnimation {


    private long timeCall=0;
    private int scene=0;

    public CallAnimation(FunnyDisplayBase display) {
        super(display);
    }

    @Override
    protected boolean onDraw(FunnySurface surface) {
        boolean drawn=false;
        int count=surface.getWidth()/2-FunnySurfaceUtils.scaleX;
        if(timeCall+25<System.currentTimeMillis()) {
            surface.clear();
            drawn=true;
            timeCall=System.currentTimeMillis();
            FunnySurface rectA=FunnySurface.createSurface( scene*2+FunnySurfaceUtils.scaleX, scene*2+FunnySurfaceUtils.scaleY, FunnySurface.DotColor.Orange, FunnySurface.DotType.Star);
            surface.putSurface(rectA, count-scene, count-scene);
            scene++;
            if (scene >count) scene = 0;
        }
        return  drawn;
    }

    @Override
    protected boolean isLooped() {
        return false;
    }

    @Override
    protected boolean onLoopDraw(FunnySurface surface, RenderCallback renderClbk) {
        return false;
    }
}