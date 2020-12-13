package kidtoys.az.kidphone;

import android.renderscript.Matrix3f;

import java.util.Arrays;
import java.util.List;

import static kidtoys.az.kidphone.FunnySurfaceUtils.scaleX;
import static kidtoys.az.kidphone.FunnySurfaceUtils.scaleY;

/**
 * Created by ramil on 24.07.2016.
 */
public class CallNoButtonAnim extends BaseAnimation {

    public CallNoButtonAnim(FunnyDisplayBase display) {
        super(display);
    }

    private final static Matrix3f initM=new Matrix3f();
    private static List<FunnySurfaceUtils.Primitive> RejectLines = Arrays.<FunnySurfaceUtils.Primitive>asList(
            new FunnySurfaceUtils.Line(0,0,2,0),
            new FunnySurfaceUtils.Line(5,0,7,0),
            new FunnySurfaceUtils.Line(10,0,12,0));


    private long timeCall=0;
    private int scene=0;



    @Override
    protected boolean onDraw(FunnySurface surface) {
        boolean drawn=false;

        if(timeCall+400<System.currentTimeMillis()) {
            surface.clear();
            drawn=true;
            timeCall=System.currentTimeMillis();
            int topLineX=0;
            int bottomLineX=3;
            int topLineY=surface.getHeight()/2-2;
            int bottomLineY=topLineY+4;
           if(scene==1){
               topLineX=3;
               bottomLineX=0;
           }
            Matrix3f matrix3f=new Matrix3f();
            matrix3f.translate(surface.getWidth()/2-5+topLineX,topLineY);
            matrix3f.scale( scaleX,  scaleY);
            matrix3f.translate(-5,0);
            for (FunnySurfaceUtils.Primitive prim : RejectLines) {
                prim.draw(surface, null, FunnySurface.DotColor.Red, FunnySurface.DotType.Square, matrix3f);
            }

            matrix3f.load(initM);
            matrix3f.translate(surface.getWidth()/2-5+bottomLineX,bottomLineY);
            matrix3f.scale( scaleX,  scaleY);
            matrix3f.translate(-5,0);
            for (FunnySurfaceUtils.Primitive prim : RejectLines) {
                prim.draw(surface, null, FunnySurface.DotColor.Red, FunnySurface.DotType.Square, matrix3f);
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
