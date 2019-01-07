package kidtoys.az.kidphone;

/**
 * Created by ramil on 24.07.2016.
 */
public class CallAnimation extends BaseAnimation {


    private long timeCall=0;
    private int scene=0;

    public CallAnimation(FunnyDisplay display) {
        super(display);
    }

    @Override
    protected boolean onDraw(FunnySurface surface) {
        boolean drawn=false;

        if(timeCall+100<System.currentTimeMillis()) {
            surface.clear();
            int center = surface.getWidth() / 2;
            drawn=true;
            timeCall=System.currentTimeMillis();
            FunnySurface face=FunnySurface.createSurface(6+scene*2,2+scene*2, FunnySurface.DotColor.Red, FunnySurface.DotType.Star);
            surface.putSurface(face, 7-scene, 7-scene);
            scene++;
            if (scene >7) scene = 0;
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