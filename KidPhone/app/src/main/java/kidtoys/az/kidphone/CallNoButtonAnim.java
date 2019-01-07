package kidtoys.az.kidphone;

/**
 * Created by ramil on 24.07.2016.
 */
public class CallNoButtonAnim extends BaseAnimation {

    public CallNoButtonAnim(FunnyDisplay display) {
        super(display);
    }



    private long timeCall=0;
    private int scene=0;



    @Override
    protected boolean onDraw(FunnySurface surface) {
        boolean drawn=false;

        if(timeCall+400<System.currentTimeMillis()) {
            surface.clear();
            drawn=true;
            timeCall=System.currentTimeMillis();
           if(scene==0){
               surface.drawLine(3,7,5,7, FunnySurface.DotColor.Red, FunnySurface.DotType.Square);
               surface.drawLine(8,7,10,7, FunnySurface.DotColor.Red, FunnySurface.DotType.Square);
               surface.drawLine(12,7,14,7, FunnySurface.DotColor.Red, FunnySurface.DotType.Square);
               surface.drawLine(5,9,7,9, FunnySurface.DotColor.Red, FunnySurface.DotType.Square);
               surface.drawLine(9,9,11,9, FunnySurface.DotColor.Red, FunnySurface.DotType.Square);
               surface.drawLine(13,9,15,9, FunnySurface.DotColor.Red, FunnySurface.DotType.Square);
           }else{
               surface.drawLine(3,9,5,9, FunnySurface.DotColor.Red, FunnySurface.DotType.Square);
               surface.drawLine(8,9,10,9, FunnySurface.DotColor.Red, FunnySurface.DotType.Square);
               surface.drawLine(12,9,14,9, FunnySurface.DotColor.Red, FunnySurface.DotType.Square);
               surface.drawLine(5,7,7,7, FunnySurface.DotColor.Red, FunnySurface.DotType.Square);
               surface.drawLine(9,7,11,7, FunnySurface.DotColor.Red, FunnySurface.DotType.Square);
               surface.drawLine(13,7,15,7, FunnySurface.DotColor.Red, FunnySurface.DotType.Square);
           }
            scene++;
            if (scene >1) scene = 0;
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
