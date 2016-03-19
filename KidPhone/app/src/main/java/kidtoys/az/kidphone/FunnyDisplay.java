package kidtoys.az.kidphone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by abdurrauf on 3/5/16.
 */
public class FunnyDisplay extends View {

    int surfaceWidth = 20;
    int surfaceHeight = 15;
    Paint realColors[];

    Path pathList[] = null;
    Path fillPathList[] = null;
    public static double sqrt2=Math.sqrt(2);

    /**
     * returns Main Surface that you can use to draw
     *
     * @return
     */
    public FunnySurface getMainSurface() {
        return mainSurface;
    }


    private FunnySurface mainSurface;

    public static int realColorFromDotColor(FunnySurface.DotColor color) {
        int res = 0;
        switch (color) {
            case Red:
                res = Color.RED;
                break;
            case Blue:
                res = Color.BLUE;
                break;
            case White:
                res = Color.WHITE;
                break;
            case Green:
                res = Color.GREEN;
                break;
            case Yellow:
                res = Color.YELLOW;
                break;
            case Pink:
                res = Color.argb(255, 0xFF, 0xC0, 0xCB);
                break;
            case Orange:
                res = Color.argb(255, 0xFF, 0x45, 0);
                break;
            case Magenta:
                res = Color.argb(255, 0xFF, 0, 0xFF);
                break;
            default:
                res = 0;
        }
        return res;
    }

    /**
     * used as backbuffer
     */
    // private  FunnySurface backSurface;
    public FunnyDisplay(Context context) {
        super(context);
        init();


    }


    public FunnyDisplay(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        FunnySurface.DotColor dotColors[] = FunnySurface.DotColor.values();
        realColors = new Paint[dotColors.length];
        for (int i = 0; i < dotColors.length; i++) {
            realColors[i] = new Paint();
            realColors[i].setColor(realColorFromDotColor(dotColors[i]));
            realColors[i].setAntiAlias(true);
        }
        mainSurface = new FunnySurface(surfaceWidth, surfaceHeight);
    }

    public void Render() {
        invalidate();
    }

    public Path getDotPath(FunnySurface.DotType type, int cx, int cy, int radius, int pad) {
        int r = radius + pad;
        switch (type) {
            case Circle:
                return CanvasUtils.Rounded(cx - r, cy - r, cx + r, cy + r, r, r);
            case Romb:
                return CanvasUtils.StandardPolyPath(cx, cy, r, 4);
            case Square: {
                //increase radius
                r= (int) (r*sqrt2);//r=r*sqrt(2)
                return CanvasUtils.StandardPolyPath(cx, cy, r, 4, -((float) Math.PI * 3.f / 4.f));
            }
            case Hexagon:
                return CanvasUtils.StandardPolyPath(cx, cy, r, 6);
            case Pentagon:
                return CanvasUtils.StandardPolyPath(cx, cy, r, 5);
            case Triangle:
                return CanvasUtils.StandardPolyPath(cx, cy, r, 3);
            case Star:
                return CanvasUtils.StarPath(cx, cy, 5, r, r / 2.f);
            case Heart:
                return CanvasUtils.HeartPath(cx - r, cy - r,
                        cx + r, cy + r);
            default:
                break;

        }
        return null;
    }

    public Path getDotPath(FunnySurface.DotType type, int radius, int pad) {
        int cx = radius;
        int cy = radius;
        return getDotPath(type, cx, cy, radius, pad);
    }

    void initPath(int radius) {
        FunnySurface.DotType[] dotTypes = FunnySurface.DotType.values();
        pathList = new Path[dotTypes.length];
        fillPathList = new Path[dotTypes.length];
        for (int i = 0; i < dotTypes.length; i++) {
            pathList[i] = getDotPath(dotTypes[i], radius, -5);
            fillPathList[i] = getDotPath(dotTypes[i], radius, -15);
        }
    }

    int diameter = 0;
    Paint pback = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (pathList == null) {
            diameter = (getWidth() - 40) / mainSurface.getWidth();
            // initPath(diameter/2);
        }
        pback.setColor(Color.BLACK);
        pback.setAntiAlias(true);
        canvas.drawPath(CanvasUtils.Rounded(0, 0, (float) getWidth(), (float) getHeight(), 30.f, 30.f), pback);

        //blt surface
        for (int j = 0; j < mainSurface.getHeight(); j++) {
            for (int i = 0; i < mainSurface.getWidth(); i++) {

                FunnySurface.DotType d = mainSurface.getDotType(i, j);
                if (d != FunnySurface.DotType.None) {

                    Path path;//=pathList[d.ordinal()];
                    path = getDotPath(d, 20 + i * diameter + diameter / 2, 20 + j * diameter + diameter / 2, diameter / 2, -1);
                    if (path != null) {
                        // Matrix translateMatrix = new Matrix();
                        // translateMatrix.setTranslate(i * diameter, j * diameter);

                        Paint p = realColors[mainSurface.getDotColor(i, j).ordinal()];
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(3);
                        p.setAntiAlias(true);
                        p.setStrokeCap(Paint.Cap.ROUND);
                        // path.transform(translateMatrix);
                        canvas.drawPath(path, p);
                        path = getDotPath(d, 20 + i * diameter + diameter / 2, 20 + j * diameter + diameter / 2, diameter / 2, -5);
                        p.setStyle(Paint.Style.FILL);
                        //                       path = fillPathList[d.ordinal()];
//                        path.transform(translateMatrix);
                        canvas.drawPath(path, p);
                    }
                }

            }
        }//for exit

    }


}
