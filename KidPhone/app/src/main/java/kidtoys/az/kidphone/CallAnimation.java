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
        int countX=surface.getWidth()/2 ;
        int countY=surface.getHeight()/2;
        if(timeCall+50<System.currentTimeMillis()) {
            surface.clear();
            drawn=true;
            timeCall=System.currentTimeMillis();
            FunnySurface rectA=FunnySurface.createSurface( scene*2 , scene*2 , FunnySurface.DotColor.Orange, FunnySurface.DotType.Star);
            surface.putSurface(rectA, countX-scene-1, countY-scene-1);
            scene++;
            if (scene-4 >=countX) scene = 0;
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