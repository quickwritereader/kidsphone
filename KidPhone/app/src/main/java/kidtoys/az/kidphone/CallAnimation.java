package kidtoys.az.kidphone;

import android.renderscript.Matrix3f;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static kidtoys.az.kidphone.FunnySurfaceUtils.scaleX;
import static kidtoys.az.kidphone.FunnySurfaceUtils.scaleY;
import static kidtoys.az.kidphone.FunnySurfaceUtils.standardFigHeight;
import static kidtoys.az.kidphone.FunnySurfaceUtils.standardFigWidth;

/**
 * Created by ramil on 24.07.2016.
 */
public class CallAnimation extends BaseAnimation {


    private long timeCall = 0;
    private final static List<FunnySurfaceUtils.Primitive> callFigures = Arrays.<FunnySurfaceUtils.Primitive>asList(
            new FunnySurfaceUtils.PointObj(10, 10),
            new FunnySurfaceUtils.Ellipse(6, 8, 14, 12),
            new FunnySurfaceUtils.Ellipse(4, 6, 16, 14),
            new FunnySurfaceUtils.Ellipse(2, 4, 18, 16),
            new FunnySurfaceUtils.Ellipse(0, 2, 20, 18),
            new FunnySurfaceUtils.Ellipse(-2, 0, 22, 20)
    );

    private final static List<FunnySurfaceUtils.Primitive> front = Arrays.<FunnySurfaceUtils.Primitive>asList(
            new FunnySurfaceUtils.Line(0, 0, 8, 8),
            new FunnySurfaceUtils.Line(12, 12, 20, 20),
            new FunnySurfaceUtils.Line(0, 20, 8, 12),
            new FunnySurfaceUtils.Line(12, 8, 20, 0)
    );
    private int callRing = 0;
    private int inc = 1;

    public CallAnimation(FunnyDisplayBase display) {
        super(display);
        callRing= 0;
        inc = 1;
    }

    @Override
    protected boolean onDraw(FunnySurface surface) {
        boolean drawn = false;
        int centerX = surface.getWidth() / 2;
        int centerY = surface.getHeight() / 2;
        Matrix3f matrix3f = new Matrix3f();
        matrix3f.translate(centerX, centerY);
        matrix3f.scale(scaleX, scaleY);
        matrix3f.translate(-10, -10);
        if (timeCall + 100 < System.currentTimeMillis()) {
            surface.clear();
            drawn = true;
            timeCall = System.currentTimeMillis();
            for (int i = 0; i < callRing; i++) {
                FunnySurfaceUtils.Primitive prim = callFigures.get(i);
                prim.setpH(2);
                prim.setpW(2);
                prim.draw(surface, null, FunnySurface.DotColor.Blue, FunnySurface.DotType.Circle, matrix3f);
            }
            for(FunnySurfaceUtils.Primitive prim:front){
                prim.setpH(9);
                prim.setpW(9);
                prim.draw(surface,null,FunnySurface.DotColor.Black, FunnySurface.DotType.None, matrix3f);
            }
            if (callRing + inc > callFigures.size()) inc = -1;
            if (callRing + inc < 0) inc = 1;
            callRing += inc;

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