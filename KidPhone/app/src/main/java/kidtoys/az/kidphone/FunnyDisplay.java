package kidtoys.az.kidphone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LruCache;
import android.view.View;

/**
 * Created by abdurrauf on 3/5/16.
 */
public class FunnyDisplay extends View {

    int surfaceWidth = 20;
    int surfaceHeight = 15;
    private Paint innerLight;
    private Paint[] realColors;
    private Paint[] centerColors;
    private Paint[] outerColors;

    private LruCache<String,Bitmap> bitmapCache;
    private static final double sqrt2=Math.sqrt(2);

    /**
     * returns Main Surface that you can use to draw
     *
     * @return
     */
    public FunnySurface getMainSurface() {
        return mainSurface;
    }


    private FunnySurface mainSurface;


    private static int blend(int color1, int color2, float alpha){
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

    private static int realColorFromDotColor(FunnySurface.DotColor color) {
        int res;
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
        bitmapCache =new LruCache<String,Bitmap>(12){


            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
                Log.d("display cache remove",key);
                oldValue.recycle();
            }
        };
        setWillNotDraw(false);
    }

    private void render() {
        invalidate();
    }

    public void postRender() {
        postInvalidate();
    }

    public void clear(){
        mainSurface.clear();
        render();
    }

    public void drawChar(char l){
        Log.d("letter", "letter: " + l);
        mainSurface.clear();
        int figureRandom = (int) (Math.random() * (FunnySurface.DotType.values().length - 1)) + 1;
        int colorRandom = (int) (Math.random() * (FunnySurface.DotColor.values().length - 2)) + 1;//exclude white and black
        FunnySurfaceUtils.drawChar(mainSurface, mainSurface.getWidth() / 2, 4, l, FunnySurface.DotColor.values()[colorRandom],
                FunnySurface.DotType.values()[figureRandom], true);
        render();
    }

    private Path getDotPath(FunnySurface.DotType type, int cx, int cy, int radius, int pad) {
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
        return getDotPath(type, radius, radius, radius, pad);
    }



    private int diameter = -1;
    private final Paint paintBack = new Paint();
    private Path screenPath=null;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        long time=System.currentTimeMillis();
        if(diameter<0) {
            paintBack.setColor(Color.BLACK);
            paintBack.setAntiAlias(true);
            diameter = (getWidth() - 40) / mainSurface.getWidth();
        }
        if(screenPath==null){
            screenPath=CanvasUtils.Rounded(0, 0, (float) getWidth(), (float) getHeight(), 30.f, 30.f);
        }
        canvas.drawPath(screenPath, paintBack);
        //blt surface
        bltBack(canvas);
        bltInner(canvas);
        //Log.d("display render time ", "" + (System.currentTimeMillis() - time));

    }
    //for reducing garbage collection
    private int oldBackType=-1;
    private int oldBackColor=-1;
    private Bitmap previousBackBitmap=null;
    private int oldType=-1;
    private int oldColor=-1;
    private Bitmap previousBitmap=null;


    private void bltBack(Canvas canvas) {

        for (int j = 0; j < mainSurface.getHeight(); j++) {
            for (int i = 0; i < mainSurface.getWidth(); i++) {

                FunnySurface.DotType d = mainSurface.getDotType(i, j);
                if (d != FunnySurface.DotType.None) {
                    int pad=11;
                    int color=mainSurface.getDotColor(i, j).ordinal();

                    Bitmap b=null;

                    //retrieve from previous
                    if(d.ordinal()==oldBackType && color==oldBackColor && previousBackBitmap!=null){
                        b=previousBackBitmap;
                    }
                    //retrieve from cache
                    String key=null;
                    if(b==null) {
                        //add to cache
                        key = "" + d.ordinal() + "_" + color;
                        b =   bitmapCache.get(key);
                    }
                    //generate and put into cache and save in previousBitmap
                    if(b==null){
                        Path path=getDotPath(d,  pad+diameter / 2,pad+  diameter / 2, diameter / 2,11);
                        int w=2*pad+diameter;
                        int h=2*pad+diameter;
                        Bitmap.Config conf = Bitmap.Config.ARGB_4444;
                        b = Bitmap.createBitmap(w, h, conf);
                        Canvas bmpCanvas = new Canvas(b);
                        Paint p = outerColors[color];
                        bmpCanvas.drawPath(path, p);
                        path=null;
                        bitmapCache.put(key, b);
                        previousBackBitmap=b;
                        Log.d("display bitmap cached ", key);

                    }
                    if (b != null) {
                        int left=  20 + i * diameter +  pad  ;
                        int top=  20 + j * diameter  +pad ;
                        canvas.drawBitmap(b,left,top,null);
                    }
                }//need to draw
            }//for inner
        }//for outer
    }

    private void bltInner(Canvas canvas) {
        for (int j = 0; j < mainSurface.getHeight(); j++) {
            for (int i = 0; i < mainSurface.getWidth(); i++) {

                FunnySurface.DotType d = mainSurface.getDotType(i, j);
                if (d != FunnySurface.DotType.None) {
                    int pad=1;
                    int color=mainSurface.getDotColor(i, j).ordinal();
                    Bitmap b=null;
                    //retrieve from previous
                    if(d.ordinal()==oldType && color==oldColor && previousBitmap!=null){
                        b=previousBitmap;
                    }
                    //retrieve from cache
                    String key=null;
                    if(b==null) {
                        //add to cache
                        key = "inner" + d.ordinal() + "_" + color;
                        b =  bitmapCache.get(key);
                    }

                    if(b==null){
                        int w=2*pad+diameter;
                        int h=2*pad+diameter;
                        Bitmap.Config conf = Bitmap.Config.ARGB_4444;
                        b = Bitmap.createBitmap(w, h, conf);
                        Canvas bmpCanvas = new Canvas(b);
                        Path path;//=pathList[d.ordinal()];
                        path = getDotPath( d,  pad+diameter / 2,pad+  diameter / 2, diameter / 2,  1);
                        if (path != null) {
                            Paint p = centerColors[mainSurface.getDotColor(i, j).ordinal()];
                            bmpCanvas.drawPath(path, p);
                            if(diameter/2-5>3) {
                                path=null;
                                path = getDotPath(d,  pad+diameter / 2,pad+  diameter / 2, diameter / 2, -5);

                                p = innerLight;
                                bmpCanvas.drawPath(path, p);

                                // getDotPath(d, 20 + i * diameter + diameter / 2, 20 + j * diameter + diameter / 2, diameter / 2, -4);
                                p = realColors[mainSurface.getDotColor(i, j).ordinal()];
                                bmpCanvas.drawPath(path, p);

                            }
                        }
                        path=null;
                        //add to cache
                        bitmapCache.put(key, b);
                        previousBitmap=b;
                    }//b==null
                    if (b != null) {
                        int left=  20 + i * diameter +  pad  ;
                        int top=  20 + j * diameter  +pad ;
                        canvas.drawBitmap(b,left,top,null);
                    }
                 }//need to draw
            }//for inner
        }//for exit
    }




}