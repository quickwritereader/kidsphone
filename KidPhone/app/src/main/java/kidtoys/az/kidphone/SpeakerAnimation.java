package kidtoys.az.kidphone;

/**
 * Created by abdurrauf on 7/10/16.
 */
public class SpeakerAnimation extends BaseAnimation {


    int faceScene =0;
    long timeFace;
    public SpeakerAnimation(FunnyDisplay display) {
        super(display);
        timeFace=0;
    }



    @Override
    protected boolean onDraw(FunnySurface surface) {
        boolean drawn=false;

        if(timeFace+250<System.currentTimeMillis()) {
            surface.clear();
            int center = surface.getWidth() / 2;
            drawn=true;
            timeFace=System.currentTimeMillis();
            //eyes
            surface.putDot(center - 2, 5, FunnySurface.DotColor.Red, FunnySurface.DotType.Star);
            surface.putDot(center + 2, 5, FunnySurface.DotColor.Red, FunnySurface.DotType.Star);
            //lips
            if (faceScene == 0) {
                surface.drawLine(center - 3, 8, center + 3, 8, FunnySurface.DotColor.Red, FunnySurface.DotType.Romb);
                surface.drawLine(center - 3, 8, center, 9, FunnySurface.DotColor.Black, FunnySurface.DotType.None);
                surface.drawLine(center, 9, center + 3, 8, FunnySurface.DotColor.Black, FunnySurface.DotType.None);
            } else if (faceScene == 1) {
                surface.drawLine(center - 3, 8, center + 3, 8, FunnySurface.DotColor.Black, FunnySurface.DotType.None);
                surface.drawLine(center - 3, 8, center, 9, FunnySurface.DotColor.Red, FunnySurface.DotType.Romb);
                surface.drawLine(center, 9, center + 3, 8, FunnySurface.DotColor.Red, FunnySurface.DotType.Romb);
            }
            faceScene++;
            if (faceScene > 1) faceScene = 0;
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
