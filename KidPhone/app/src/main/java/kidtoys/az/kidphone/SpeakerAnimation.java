package kidtoys.az.kidphone;

/**
 * Created by abdurrauf on 7/10/16.
 */
public class SpeakerAnimation extends BaseAnimation {


    int faceScene =0;
    long timeFace;
    public SpeakerAnimation(FunnyDisplayBase display) {
        super(display);
        timeFace=0;
    }



    @Override
    protected boolean onDraw(FunnySurface surface) {
        boolean drawn=false;

        if(timeFace+250<System.currentTimeMillis()) {
            surface.clear();
            int centerX = surface.getWidth() / 2;
            int centerY=surface.getHeight()/2;
            drawn=true;
            timeFace=System.currentTimeMillis();
            //eyes
            surface.putDot(centerX - 2,  centerY-2, FunnySurface.DotColor.Red, FunnySurface.DotType.Star);
            surface.putDot(centerX + 2,  centerY-2, FunnySurface.DotColor.Red, FunnySurface.DotType.Star);
            //lips
            if (faceScene == 0) {
                surface.drawLine(centerX - 3,  centerY+1, centerX + 3,  centerY+1, FunnySurface.DotColor.Red, FunnySurface.DotType.Romb);
                surface.drawLine(centerX - 3, centerY+1, centerX, centerY+2, FunnySurface.DotColor.Black, FunnySurface.DotType.None);
                surface.drawLine(centerX, centerY+2, centerX + 3,  centerY+1, FunnySurface.DotColor.Black, FunnySurface.DotType.None);
            } else if (faceScene == 1) {
                surface.drawLine(centerX - 3, centerY+1, centerX + 3, centerY+1, FunnySurface.DotColor.Black, FunnySurface.DotType.None);
                surface.drawLine(centerX - 3, centerY+1, centerX, centerY+2, FunnySurface.DotColor.Red, FunnySurface.DotType.Romb);
                surface.drawLine(centerX, centerY+2, centerX + 3, centerY+1, FunnySurface.DotColor.Red, FunnySurface.DotType.Romb);
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
