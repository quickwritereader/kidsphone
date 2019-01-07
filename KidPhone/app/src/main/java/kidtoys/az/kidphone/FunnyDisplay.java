package kidtoys.az.kidphone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.LruCache;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Funny Display
 */
public class FunnyDisplay extends View {
    private static final double sqrt2 = Math.sqrt(2);
    private final Paint paintBack = new Paint();
    int surfaceWidth = 20;
    int surfaceHeight = 16;
    private int width = -1;
    private int height = -1;
    //private Paint innerLight;
    private Paint[] realColors;
    private Paint[] centerColors;
    private Paint[] outerColors;
    private LruCache<Integer, Bitmap> bitmapCache;
    private FunnySurface mainSurface;
    private int diameter = -1;
    private Path screenPath = null;
    //for reducing garbage collection
    private int oldBackType = -1;
    private int oldBackColor = -1;
    private Bitmap previousBackBitmap = null;
    private int oldType = -1;
    private int oldColor = -1;
    private Bitmap previousBitmap = null;
    private Path[] outerPathList = null;
    private Path[] innerPathList = null;
    // private Path[] centerPathList = null;
    private int padOuter = 11;
    private int padInner = -5;
    private int padCenter = 1;

    private WeakReference<BaseAnimation> attachedAnim = null;

    /**
     * used as backbuffer
     */
    // private  FunnySurface backSurface;
    public FunnyDisplay(Context context) {
        super(context);
        init();


    }

    public synchronized void attachAnimation(BaseAnimation anim) {

        BaseAnimation a=attachedAnim!=null?attachedAnim.get():null;
        if(a!=null){
            if(a!=anim){
                a.stop(true);
            }
        }
        if(a!=anim) {
            if(anim==null){
                attachedAnim=null;
            }else {
                attachedAnim = new WeakReference<>(anim);
            }
        }
    }



    public FunnyDisplay(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private static int blend(int color1, int color2, float alpha) {
        float red1 = Color.red(color1);
        float blue1 = Color.blue(color1);
        float green1 = Color.green(color1);
        float red2 = Color.red(color2);
        float blue2 = Color.blue(color2);
        float green2 = Color.green(color2);
        int b_red = (int) (alpha * red1 + (1.0f - alpha) * red2);
        int b_green = (int) (alpha * green1 + (1.0f - alpha) * green2);
        int b_blue = (int) (alpha * blue1 + (1.0f - alpha) * blue2);
        return Color.argb(255, b_red, b_green, b_blue);

    }

    private static int realColorFromDotColor(FunnySurface.DotColor color) {
        int res;
        switch (color) {
            case Red:
                res = Color.argb(255, 255, 0x40, 0);//Orange red FF2400
                break;
            case Blue:
                res = Color.argb(255, 0, 0xbf, 255); //
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


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        initPath(widthSize, heightSize);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * returns Main Surface that you can use to draw
     *
     * @return
     */
    public FunnySurface getMainSurface() {
        return mainSurface;
    }

    private void init() {
        FunnySurface.DotColor dotColors[] = FunnySurface.DotColor.values();
//        innerLight = new Paint();
//        innerLight.setColor(Color.WHITE);
//        innerLight.setAntiAlias(true);
//        innerLight.setStyle(Paint.Style.FILL);
        centerColors = new Paint[dotColors.length];
        outerColors = new Paint[dotColors.length];
        realColors = new Paint[dotColors.length];
        for (int i = 0; i < dotColors.length; i++) {
            centerColors[i] = new Paint();
            outerColors[i] = new Paint();
            realColors[i] = new Paint();
            int real = realColorFromDotColor(dotColors[i]);
            int blend1 = blend(real, Color.WHITE, 0.08f);
            int blend2 = blend(real, Color.BLACK, 0.345f);

            realColors[i].setColor(real);
            realColors[i].setAntiAlias(true);
            realColors[i].setStyle(Paint.Style.STROKE);
             realColors[i].setStrokeWidth(2);

            centerColors[i].setColor(blend1);
            centerColors[i].setAntiAlias(true);
            centerColors[i].setStyle(Paint.Style.FILL);

            outerColors[i].setColor(blend2);
            outerColors[i].setStyle(Paint.Style.FILL);
            outerColors[i].setAntiAlias(true);
        }
        mainSurface = new FunnySurface(surfaceWidth, surfaceHeight);
        bitmapCache = new LruCache<Integer, Bitmap>(12) {


            @Override
            protected void entryRemoved(boolean evicted, Integer key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
                //Log.d("display cache remove", key);
                oldValue.recycle();
            }
        };
        paintBack.setColor(Color.argb(255,2,16,20) );
        paintBack.setAntiAlias(true);
        setWillNotDraw(false);
    }

    private void initPath(int width, int height) {
        if (this.width != width || this.height != height) {
            diameter = (width - 40) / mainSurface.getWidth();
            float rx=width/surfaceWidth;
            float ry=height/surfaceHeight;
            screenPath = CanvasUtils.Rounded(0, 0, (float) width, (float) height, rx, ry);
            //initialize Pathes
            FunnySurface.DotType[] dotTypes = FunnySurface.DotType.values();
            int len = dotTypes.length;
            outerPathList = new Path[len];
            innerPathList = new Path[len];
            //centerPathList = new Path[len];
            padOuter=diameter/4;
            padInner=-3;//0-diameter/8;
            for (int i = 0; i < len; i++) {
                FunnySurface.DotType dType = dotTypes[i];
                outerPathList[i] = getDotPath(dType, padOuter + diameter / 2, padOuter + diameter / 2, diameter / 2, padOuter);
                //centerPathList[i] = getDotPath(dType, 1 + diameter / 2, 1 + diameter / 2, diameter / 2, 0);
                innerPathList[i] = getDotPath(dType, 1 + diameter / 2, 1 + diameter / 2, diameter / 2, padInner);
            }
        }

    }

    public void render() {
        invalidate();
    }

    public void postRender() {
        postInvalidate();
    }

    public void clear() {
        mainSurface.lock();
        try {
            mainSurface.clear();
        }finally {
            mainSurface.unlock();
        }
        render();
    }

    public void drawFigure(FunnyButton.InnerShapeType innerShapeType) {
        mainSurface.lock();
        try {
            mainSurface.clear();
            int figureRandom = (int) (Math.random() * (FunnySurface.getMaxTypeSupport()- 1)) + 1;
            int colorRandom = (int) (Math.random() * (FunnySurface.getMaxColorSupport() - 2)) + 1;//exclude white and black
            /*FunnySurfaceUtils.drawFigure(mainSurface, mainSurface.getWidth() / 2, 4, l, FunnySurface.supportedColors[colorRandom],
                    FunnySurface.supportedTypes[figureRandom], true);*/
            FunnySurfaceUtils.drawFigure(mainSurface, mainSurface.getWidth() / 2, 4, innerShapeType, FunnySurface.supportedColors[colorRandom],
                    FunnySurface.DotType.Circle, true);
            render();
        } finally {
            mainSurface.unlock();
        }
    }

    public void drawChar(char l) {
        //Log.d("letter", "letter: " + l);
        mainSurface.lock();
        try {
            mainSurface.clear();
            int figureRandom = (int) (Math.random() * (FunnySurface.getMaxTypeSupport() - 1)) + 1;
            int colorRandom = (int) (Math.random() * (FunnySurface.getMaxColorSupport() - 2)) + 1;//exclude white and black
        /*FunnySurfaceUtils.drawChar(mainSurface, mainSurface.getWidth() / 2, 4, l, FunnySurface.supportedColors[colorRandom],
                FunnySurface.supportedTypes[figureRandom], true);*/
            FunnySurfaceUtils.drawChar(mainSurface, mainSurface.getWidth() / 2, 4, l, FunnySurface.supportedColors[colorRandom],
                    FunnySurface.DotType.Circle, true);
        }finally {
            mainSurface.unlock();
        }
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
                r = (int) (r * sqrt2);//r=r*sqrt(2)
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //long time = System.currentTimeMillis();

        canvas.clipPath(screenPath);
        canvas.drawColor(paintBack.getColor());
        //blt surface
        bltBack(canvas);
        bltInner(canvas);
        //Log.d("display render time ", "" + (System.currentTimeMillis() - time));

    }

    private void bltBack(Canvas canvas) {

        for (int j = 0; j < mainSurface.getHeight(); j++) {
            for (int i = 0; i < mainSurface.getWidth(); i++) {

                FunnySurface.DotType d = mainSurface.getDotType(i, j);
                if (d != FunnySurface.DotType.None) {
                    int pad = padOuter;
                    int color = mainSurface.getDotColor(i, j).ordinal();

                    Bitmap b = null;

                    //retrieve from previous
                    if (d.ordinal() == oldBackType && color == oldBackColor && previousBackBitmap != null) {
                        b = previousBackBitmap;
                    }
                    //retrieve from cache
                    Integer key = null;
                    if (b == null) {
                        //add to cache
                        //use integer cache of java platform (-128 til 127 are in cache within Integer.valueof
                        key = -127 + (d.ordinal() * FunnySurface.getMaxColorSupport() + color);
                        b = bitmapCache.get(key);
                    }
                    //generate and put into cache and save in previousBitmap
                    if (b == null) {
                        Path path = outerPathList[d.ordinal()];
                        int w = 2 * pad + diameter;
                        int h = 2 * pad + diameter;
                        Bitmap.Config conf = Bitmap.Config.ARGB_4444;
                        b = Bitmap.createBitmap(w, h, conf);
                        Canvas bmpCanvas = new Canvas(b);
                        Paint p = outerColors[color];
                        bmpCanvas.drawPath(path, p);
                        bitmapCache.put(key, b);
                        previousBackBitmap = b;
                        // Log.d("display bitmap cached ", key);

                    }
                    int left = 20 + i * diameter -padOuter+1 ;
                    int top = 20 + j * diameter -padOuter+1;
                    canvas.drawBitmap(b, left, top, null);
                }//need to draw
            }//for inner
        }//for outer
    }

    private void bltInner(Canvas canvas) {
        for (int j = 0; j < mainSurface.getHeight(); j++) {
            for (int i = 0; i < mainSurface.getWidth(); i++) {

                FunnySurface.DotType d = mainSurface.getDotType(i, j);
                if (d != FunnySurface.DotType.None) {
                    int pad = 1;
                    int color = mainSurface.getDotColor(i, j).ordinal();
                    Bitmap b = null;
                    //retrieve from previous
                    if (d.ordinal() == oldType && color == oldColor && previousBitmap != null) {
                        b = previousBitmap;
                    }
                    //retrieve from cache
                    Integer key = null;
                    if (b == null) {
                        //add to cache
                        //use integer cache of java platform
                        key = d.ordinal() * FunnySurface.getMaxColorSupport() + color;
                        b = bitmapCache.get(key);
                    }

                    if (b == null) {
                        int w = 2 * pad + diameter;
                        int h = 2 * pad + diameter;
                        Bitmap.Config conf = Bitmap.Config.ARGB_4444;
                        b = Bitmap.createBitmap(w, h, conf);
                        Canvas bmpCanvas = new Canvas(b);
                        Path path;
                       // path = centerPathList[d.ordinal()];
                        path = innerPathList[d.ordinal()];
                        if (path != null) {
                            Paint p = realColors[color];
                            bmpCanvas.drawPath(path, p);
                            if (diameter / 2 + padInner > 3) {
                               // path = innerPathList[d.ordinal()];
                                p = centerColors[color];
                                bmpCanvas.drawPath(path, p);
//                                p = realColors[color];
//                                bmpCanvas.drawPath(path, p);
                            }
                        }
                        //add to cache
                        bitmapCache.put(key, b);
                        previousBitmap = b;
                    }//b==null
                    int left = 20 + i * diameter + pad;
                    int top = 20 + j * diameter + pad;
                    canvas.drawBitmap(b, left, top, null);
                }//need to draw
            }//for inner
        }//for exit
    }


}