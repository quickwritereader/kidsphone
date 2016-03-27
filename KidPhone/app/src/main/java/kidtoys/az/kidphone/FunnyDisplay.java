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
    Paint innerLight;
    Paint realColors[];
    Paint centerColors[];
    Paint outerColors[];

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


    public static int blend(int color1,int color2 , float alpha){
        float red1=Color.red(color1);
        float blue1=Color.blue(color1);
        float green1=Color.green(color1);
        float red2=Color.red(color2);
        float blue2=Color.blue(color2);
        float green2=Color.green(color2);
        int b_red= (int) (alpha*red1+(1.0f-alpha)*red2);
        int b_green= (int) (alpha*green1+(1.0f-alpha)*green2);
        int b_blue= (int) (alpha*blue1+(1.0f-alpha)*blue2);
        return Color.argb(255,b_red,b_green,b_blue);

    }

    public static int realColorFromDotColor(FunnySurface.DotColor color) {
        int res = 0;
        switch (color) {
            case Red:
                res = Color.argb(255,255,0x40,0);//Orange red FF2400
                break;
            case Blue:
                res = Color.argb(255,0,0xbf,255); //
                break;
            case White:
                res = Color.WHITE;
                break;
            case Green:
                res = Color.argb(255, 0, 255, 0);//
                break;
            case Yellow:
                res = Color.YELLOW;
                break;
            case Pink:
                res = Color.argb(255, 0xFF, 0x6e, 0xC7);//Neon Pink (FF6EC7)
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
        innerLight=new Paint();
        innerLight.setColor(Color.WHITE);
        innerLight.setAntiAlias(true);
        innerLight.setStyle(Paint.Style.FILL);
        centerColors = new Paint[dotColors.length];
        outerColors = new Paint[dotColors.length];
        realColors = new Paint[dotColors.length];
        for (int i = 0; i < dotColors.length; i++) {
            centerColors[i] = new Paint();
            outerColors[i]=new Paint();
            realColors[i]=new Paint();
            int real=realColorFromDotColor(dotColors[i]);
            int blend1=blend(real, Color.WHITE, 0.375f);
            int blend2=blend(blend1, Color.BLACK, 0.225f);

            realColors[i].setColor(real);
            realColors[i].setAntiAlias(true);
            realColors[i].setStyle(Paint.Style.STROKE);
            realColors[i].setStrokeWidth(1);

            centerColors[i].setColor(blend1);
            centerColors[i].setAntiAlias(true);
            centerColors[i].setStyle(Paint.Style.FILL);

            outerColors[i].setColor(blend2);
            outerColors[i].setStyle(Paint.Style.FILL);
            outerColors[i].setAntiAlias(true);
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
                    path = getDotPath(d, 20 + i * diameter + diameter / 2, 20 + j * diameter + diameter / 2, diameter / 2,11);
                    if (path != null) {
                        Paint p = outerColors[mainSurface.getDotColor(i, j).ordinal()];
                        canvas.drawPath(path, p);
                    }
                }
            }
        }

        for (int j = 0; j < mainSurface.getHeight(); j++) {
            for (int i = 0; i < mainSurface.getWidth(); i++) {

                FunnySurface.DotType d = mainSurface.getDotType(i, j);
                if (d != FunnySurface.DotType.None) {

                    Path path;//=pathList[d.ordinal()];
                    path = getDotPath(d, 20 + i * diameter + diameter / 2, 20 + j * diameter + diameter / 2, diameter / 2, 1);
                    if (path != null) {
                        Paint p = centerColors[mainSurface.getDotColor(i, j).ordinal()];
                        canvas.drawPath(path, p);
                        if(diameter/2-5>3) {

                            path = getDotPath(d, 20 + i * diameter + diameter / 2, 20 + j * diameter + diameter / 2, diameter / 2, -5);

                            p = innerLight;
                            canvas.drawPath(path, p);

                           // getDotPath(d, 20 + i * diameter + diameter / 2, 20 + j * diameter + diameter / 2, diameter / 2, -4);
                            p = realColors[mainSurface.getDotColor(i, j).ordinal()];
                            canvas.drawPath(path, p);

                        }
                    }
                }

            }
        }//for exit

    }


}
