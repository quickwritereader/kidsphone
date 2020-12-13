package kidtoys.az.kidphone;

import android.renderscript.Matrix3f;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import static kidtoys.az.kidphone.FunnySurfaceUtils.scaleX;
import static kidtoys.az.kidphone.FunnySurfaceUtils.scaleY;
import static kidtoys.az.kidphone.FunnySurfaceUtils.standardFigHeight;
import static kidtoys.az.kidphone.FunnySurfaceUtils.standardFigWidth;

/**
 * Created by abdurrauf on 7/10/16.
 */
public class SpeakerAnimation extends BaseAnimation {

    private final static List<FunnySurfaceUtils.Primitive> FigMouth3 = Arrays.<FunnySurfaceUtils.Primitive>asList(
            new FunnySurfaceUtils.Line(1, 2, 6, 1),
            new FunnySurfaceUtils.Line(6, 1, 8, 1),
            new FunnySurfaceUtils.Line(8, 1, 13, 2),
            new FunnySurfaceUtils.Line(13, 2, 11, 4),
            new FunnySurfaceUtils.Line(11, 4, 3, 4),
            new FunnySurfaceUtils.Line(3, 4, 1, 2)
    );
    private final static List<FunnySurfaceUtils.Primitive> FigMouth2 = Arrays.<FunnySurfaceUtils.Primitive>asList(
            new FunnySurfaceUtils.Line(2, 2, 5, 1),
            new FunnySurfaceUtils.Line(5, 1, 9, 1),
            new FunnySurfaceUtils.Line(9, 1, 12, 2),
            new FunnySurfaceUtils.Line(12, 2, 10, 4),
            new FunnySurfaceUtils.Line(10, 4, 4, 4),
            new FunnySurfaceUtils.Line(4, 4, 2, 2)
    );
    private final static List<FunnySurfaceUtils.Primitive> FigMouth1 = Arrays.<FunnySurfaceUtils.Primitive>asList(
            new FunnySurfaceUtils.Line(2+1, 2, 5+1, 1),
            new FunnySurfaceUtils.Line(5+1, 1, 9-1, 1),
            new FunnySurfaceUtils.Line(9-1, 1, 12-1, 2),
            new FunnySurfaceUtils.Line(12-1, 2, 10-1, 3),
            new FunnySurfaceUtils.Line(10-1, 3, 4+1, 3),
            new FunnySurfaceUtils.Line(4+1, 3, 2+1, 2)
    );
    private static final String TAG = "SpeakerAnim";
    int faceScene = 0;
    int inc = 1;
    long timeFace;

    public SpeakerAnimation(FunnyDisplayBase display) {
        super(display);
        faceScene=0;
        inc=1;
        timeFace = 0;
    }


    @Override
    protected boolean onDraw(FunnySurface surface) {
        boolean drawn = false;
        int centerX = surface.getWidth() / 2;
        int posY = 3*surface.getHeight() / 4;
        Matrix3f matrix3f = new Matrix3f();
        matrix3f.translate(centerX, posY);
        matrix3f.scale( scaleX,  scaleY);
        matrix3f.translate(-standardFigWidth / 2, -standardFigHeight / 4);
        if (timeFace + 100 < System.currentTimeMillis()) {
            surface.clear();
            drawn = true;
            timeFace = System.currentTimeMillis();

            surface.putDot(centerX - 3*scaleX,  5*scaleY,4,8, FunnySurface.DotColor.Blue, FunnySurface.DotType.Star);
            surface.putDot(centerX + 3*scaleX,  5*scaleY,4,8, FunnySurface.DotColor.Blue, FunnySurface.DotType.Star);
            surface.putDot(centerX - 3*scaleX,  5*scaleY,6,6, FunnySurface.DotColor.Blue, FunnySurface.DotType.Star);
            surface.putDot(centerX + 3*scaleX,  5*scaleY,6,6, FunnySurface.DotColor.Blue, FunnySurface.DotType.Star);

            List<FunnySurfaceUtils.Primitive> drawMouth;
            //Log.d(TAG," faceScene "+faceScene);
            if(faceScene==0)
                drawMouth=FigMouth1;
            else if(faceScene==1){
                drawMouth=FigMouth2;
            }else{
                drawMouth=FigMouth3;
            }
            for (FunnySurfaceUtils.Primitive prim : drawMouth) {
                prim.draw(surface, null, FunnySurface.DotColor.Blue, FunnySurface.DotType.Star, matrix3f);
            }
            if(faceScene + inc >2) inc=-1;
            if(faceScene + inc<0) inc=1;
            faceScene+=inc;

        }
        return drawn;
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
