package kidtoys.az.kidphone;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * Created by abdurrauf on 2/15/16.
 * This is funny buttons with text and shape and curvy corners
 */
public class FunnyButton extends View {

    private String centerText;
    private String belowText;


    private Paint innerP1;
    private int outerShapeColor;
    private Paint outerP1;
    private Paint outerP2;
    private Paint centerTxtP;
    private Paint belowTxtP;
    private RectF rectF;




    public enum InnerShapeType {
        None,
        Square,
        Rectangle,
        Circle,
        Triangle,
        Pentagon,
        Star,
        Trapes,
        Ellipse,
        Heart,
        YesPhone,
        NoPhone,
        Sound,
        Picture
    }

    public enum OuterShapeType {
        None,
        Rounded,
        BottomRounded,
        YesButton,
        NoButton,
        Picture
    }

    private InnerShapeType innerShape;
    private OuterShapeType outerShape;


    public InnerShapeType getInnerShape() {
        return innerShape;
    }

    public void setInnerShape(InnerShapeType innerShape) {
        this.innerShape = innerShape;
        invalidate();
    }

    public OuterShapeType getOuterShape() {
        return outerShape;
    }

    public void setOuterShape(OuterShapeType outerShape) {
        this.outerShape = outerShape;
        invalidate();
    }

    public int getInnerShapeColor() {
        return innerP1.getColor();
    }

    public void setInnerShapeColor(int innerShapeColor) {
        this.innerP1.setColor( innerShapeColor);
        invalidate();
    }

    public int getOuterShapeColor() {
        return this.outerShapeColor;
    }

    public void setOuterShapeColor(int outerShapeColor) {
        this.outerShapeColor=outerShapeColor ;
        invalidate();
    }

    public int getBelowTextColor() {
        return belowTxtP.getColor();
    }

    public void setBelowTextColor(int belowTextColor) {
        belowTxtP.setColor(belowTextColor);
        invalidate();
    }

    public int getCenterTextColor() {
        return centerTxtP.getColor();
    }

    public void setCenterTextColor(int centerTextColor) {
        this.centerTxtP.setColor(centerTextColor);
        invalidate();
    }

    public String getBelowText() {
        return belowText;
    }

    public void setBelowText(String belowText) {
        this.belowText = belowText;
        invalidate();
    }

    public float getCenterTextSize() {
        return centerTxtP.getTextSize();
    }

    public void setCenterTextSize(float centerTextSize) {
        this.centerTxtP.setTextSize(centerTextSize);
        invalidate();
    }

    public float getBelowTextSize() {
        return this.belowTxtP.getTextSize();
    }

    public void setBelowTextSize(float belowTextSize) {
        this.belowTxtP.setTextSize(belowTextSize);
        invalidate();
    }


    public String getCenterText() {
        return centerText;
    }

    public void setCenterText(String centerText) {
        this.centerText = centerText;
        invalidate();
    }


    public FunnyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.FunnyButton, 0, 0);
        this.setClickable(true);
        float belowTextSize, centerTextSize;
        int belowTextColor, centerTextColor,  innerShapeColor;
        try {
            centerText = typedArray.getString(R.styleable.FunnyButton_centerText);
            belowText = typedArray.getString(R.styleable.FunnyButton_belowText);
            centerTextColor = typedArray.getColor(R.styleable.FunnyButton_centerTextColor, 0);
            belowTextColor = typedArray.getColor(R.styleable.FunnyButton_belowTextColor, 0);
            centerTextSize = typedArray.getDimensionPixelSize(R.styleable.FunnyButton_centerTextSize, 14);
            belowTextSize = typedArray.getDimensionPixelSize(R.styleable.FunnyButton_belowTextSize, 14);
            innerShapeColor = typedArray.getColor(R.styleable.FunnyButton_innerShapeColor, 0);
            outerShapeColor = typedArray.getColor(R.styleable.FunnyButton_outerShapeColor, 0);
            int ordinal = typedArray.getInt(R.styleable.FunnyButton_InnerShapeProperty, 0);

            if (ordinal >= 0 && ordinal < InnerShapeType.values().length) {
                innerShape = InnerShapeType.values()[ordinal];
            }
            ordinal = typedArray.getInt(R.styleable.FunnyButton_OuterShapeProperty, 0);
            if (ordinal >= 0 && ordinal < InnerShapeType.values().length) {
                outerShape = OuterShapeType.values()[ordinal];
            }
        } finally {
            typedArray.recycle();
        }
        innerP1=new Paint();
        innerP1.setAntiAlias(true);
        innerP1.setColor(innerShapeColor);
        outerP1 = new Paint();
        outerP1.setColor(outerShapeColor);
        outerP1.setAntiAlias(true);
        outerP2 = new Paint();
        outerP2.setAntiAlias(true);
        centerTxtP = new Paint();
        centerTxtP.setColor(centerTextColor);
        centerTxtP.setTextSize(centerTextSize);
        centerTxtP.setAntiAlias(true);
        centerTxtP.setTextAlign(Paint.Align.CENTER);
        belowTxtP = new Paint();
        belowTxtP.setColor(belowTextColor);
        belowTxtP.setTextSize(belowTextSize);
        belowTxtP.setAntiAlias(true);
        belowTxtP.setTextAlign(Paint.Align.CENTER);
        rectF = new RectF();
        rectF.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
    }

    public void draw() {
    }



    //yes button
    static public Path YesButtonPath(float left, float top, float width, float height) {

        /*
        xPos=0;yPos=0;
        ctx.moveTo(xPos + 186 * scale, yPos + 108 * scale);
    ctx.bezierCurveTo(xPos + 111 * scale, yPos + 121 * scale, xPos + 50 * scale, yPos + 130 * scale, xPos + 19 * scale, yPos + 103 * scale);
    ctx.bezierCurveTo(xPos + -7 * scale, yPos + 74 * scale, xPos + 4.1 * scale, yPos + 32.58 * scale, xPos + 10.7 * scale, yPos + 25.98 * scale);
    ctx.bezierCurveTo(xPos + 47 * scale, yPos + -26 * scale, xPos + 120.92 * scale, yPos + 25.32 * scale, xPos + 335 * scale, yPos + 28 * scale);
    ctx.bezierCurveTo(xPos + 354 * scale, yPos + 29 * scale, xPos + 360 * scale, yPos + 91 * scale, xPos + 322 * scale, yPos + 90 * scale);
    ctx.bezierCurveTo(xPos + 200 * scale, yPos + 103 * scale, xPos + 235 * scale, yPos + 100 * scale, xPos + 210 * scale, yPos + 104 * scale);

    ctx.stroke(); */
        Path path = new Path();
        float scaleX = width / 360.f;
        float scale = height / 130.f;
        path.moveTo(left + 186 * scaleX, top + 108 * scale);
        path.cubicTo(left + 111 * scaleX, top + 121 * scale, left + 50 * scaleX, top + 130 * scale, left + 19 * scaleX, top + 103 * scale);
        path.cubicTo(left - 7 * scaleX, top + 74 * scale, left + 4.1f * scaleX, top + 32.58f * scale, left + 10.7f * scaleX, top + 25.98f * scale);
        path.cubicTo(left + 47 * scaleX, top - 26 * scale, left + 120.92f * scaleX, top + 25.32f * scale, left + 335 * scaleX, top + 28 * scale);
        path.cubicTo(left + 354 * scaleX, top + 29 * scale, left + 360 * scaleX, top + 91 * scale, left + 322 * scaleX, top + 90 * scale);
        path.cubicTo(left + 200 * scaleX, top + 103 * scale, left + 235 * scaleX, top + 100 * scale, left + 210 * scaleX, top + 104 * scale);
        path.close();
        return path;
    }

    static public Path BottomRounded(float left, float top, float right, float bottom, float brx, float bry, float rx, float ry) {
        Path path = new Path();
        if (rx < 0) rx = 0;
        if (ry < 0) ry = 0;
        if (brx < 0) brx = 0;
        if (bry < 0) bry = 0;
        float width = right - left;
        float height = bottom - top;
        if (rx > width / 2) rx = width / 2;
        if (ry > height / 2) ry = height / 2;
        if (brx > width / 4) brx = width / 4;
        if (bry > height / 4) bry = height / 4;
        float bwidthMinusCorners = (width - (2 * brx));
        float bheightMinusCorners = (height - (2 * bry));
        float widthMinusCorners = (width - (2 * rx));
        float heightMinusCorners = (height - (2 * ry));
        //r is releative
        path.moveTo(right, top + bry + bry);
        path.rLineTo(0, -bry);
        path.rQuadTo(0, -bry, -brx, -bry);//top-right corner
        path.rLineTo(-bwidthMinusCorners, 0);
        path.rQuadTo(-brx, 0, -brx, bry); //top-left corner
        path.rLineTo(0, bry);
        path.rQuadTo(0, 2 * bry + ry, rx, 2 * bry + ry);//bottom-left corner
        path.rLineTo(widthMinusCorners, 0);
        path.rQuadTo(rx, 0, rx, -(2 * bry + ry)); //bottom-right corner
        path.close();//Given close, last lineto can be removed.

        return path;
    }

    static public Path NoButtonPath(float left, float top, float width, float height) {
        Path path = YesButtonPath(left, top, width, height);
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);
        matrix.postTranslate(width + 2 * left, 0);
        path.transform(matrix);
        return path;
    }

    public static int getNewColor(int color, int add) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = red + add;
        green += add;
        blue += add;
        if (red < 0) red = 0;
        if (green < 0) green = 0;
        if (blue < 0) blue = 0;
        if (red > 255) red = 255;
        if (green > 255) green = 255;
        if (blue > 255) blue = 255;

        color = Color.argb(Color.alpha(color), red, green, blue);
        return color;
    }

    public void drawOuter(Canvas canvas, boolean pressed) {

        float scaleDiv = 30*1.5f;
        float scaleDivb = 36*1.5f;
        int color = getOuterShapeColor();
        if (pressed) {
            //darken our color
            //outercolor
            color = getNewColor(color, -20);
            outerP1.setColor(color);
            scaleDivb = 30;
            scaleDiv = 36 ;
        }else{
            outerP1.setColor(color);
        }
        outerP2.setColor(getNewColor(color, -70));
        RectF rectF2 = new RectF();
        rectF2.left = rectF.right / scaleDiv;
        rectF2.top = rectF.bottom / scaleDivb;
        rectF2.right  = rectF.right-rectF.right / scaleDiv;
        rectF2.bottom  =rectF.bottom- rectF.bottom / scaleDiv;

        switch (outerShape) {
            case None: {
                canvas.drawColor(color);
            }
            break;
            case Rounded: {
                canvas.drawRoundRect(rectF, rectF.right / 4, rectF.bottom / 2, outerP2);
                canvas.drawRoundRect(rectF2, rectF2.width() / 4, rectF2.height() / 2, outerP1);
            }
            break;
            case BottomRounded: {
                canvas.drawPath(BottomRounded(rectF.left, rectF.top, rectF.right, rectF.bottom, rectF.width() / 8, rectF.height() / 8, rectF.width() / 2, rectF.height() / 2), outerP2);
                canvas.drawPath(BottomRounded(rectF2.left, rectF2.top, rectF2.right, rectF2.bottom, rectF2.width() / 8, rectF2.height() / 8, rectF2.width() / 2, rectF2.height() / 2), outerP1);
            }
            break;
            case YesButton: {
                canvas.drawPath(YesButtonPath(rectF.left, rectF.top, rectF.width(), rectF.height()), outerP2);
                canvas.drawPath(YesButtonPath(rectF2.left, rectF2.top, rectF2.width(), rectF2.height()), outerP1);
            }
            break;
            case NoButton: {
                canvas.drawPath(NoButtonPath(rectF.left, rectF.top, rectF.width(), rectF.height()), outerP2);
                canvas.drawPath(NoButtonPath(rectF2.left, rectF2.top, rectF2.width(), rectF2.height()), outerP1);
            }
            break;
        }

    }

    public static Path StarPath(float cx,float cy,int spikes,float outerRadius,float innerRadius){
        Path path = new Path();
        float rot=(float)Math.PI/2*3;
        float x=cx;
        float y=cy;
        float step=(float)Math.PI/spikes;
        path.moveTo(cx,cy-outerRadius);
            for(int i=0;i<spikes;i++){
                x=cx+(float)Math.cos(rot)*outerRadius;
                y=cy+(float)Math.sin(rot)*outerRadius;
                path.lineTo(x,y);
                rot+=step;
                x=cx+(float)Math.cos(rot)*innerRadius;
                y=cy+(float)Math.sin(rot)*innerRadius;
                path.lineTo(x,y);
                rot+=step;
            }
            path.lineTo(cx,cy-outerRadius);

            path.close();
        return path;
    }
    public static Path TrapesPath(float left,float top,float right,float bottom)
    {
        Path path=new Path();
        path.moveTo(left,top);
        path.lineTo(right - (right-left) / 4.f, top);
        path.lineTo(right, bottom);
        path.lineTo(left,bottom);
        path.close();
        return path;
    }

    public Path StandardPolyPath( float cx,float cy,float radius, int sides  ) {
        if (sides < 3) return new Path();
        Path path=new Path();
        float start=-(float)(Math.PI / 2);
        float a = (float)(Math.PI * 2)/(float)sides;

        path.moveTo(cx, cy + radius * (float) Math.sin(start));
        //Log.d("p: ", " " + sides + "p: " + cx  + " : " + cy  +" ;" +radius*(float)Math.sin(start));

        for (int i = 1; i < sides; i++) {
           // Log.d("p: ", " "+sides+"p: " + cx +" : " + (float) radius * (float) Math.cos(a * i + start) + " : " + cy + " : " + (float) radius * (float) Math.sin(a * i + start));
            path.lineTo(cx + (float) radius * (float) Math.cos(a * i + start), cy + (float) radius * (float) Math.sin(a * i + start));
        }
        path.close() ;
        return path;
    }

    public static Path HeartPath(float left,float top,float right,float bottom){
        Path path=new Path();
        float d = Math.min(right-left, bottom-top);
        float k = left;
        path.moveTo(k, k + d / 4);
        path.quadTo(k, k, k + d / 4, k);
        path.quadTo(k + d / 2, k, k + d / 2, k + d / 4);
        path.quadTo(k + d / 2, k, k + d * 3 / 4, k);
        path.quadTo(k + d, k, k + d, k + d / 4);
        path.quadTo(k + d, k + d / 2, k + d * 3 / 4, k + d * 3 / 4);
        path.lineTo(k + d / 2, k + d);
        path.lineTo(k + d / 4, k + d * 3 / 4);
        path.quadTo(k, k + d / 2, k, k + d / 4);
        path.close();
        return path;
    }

    public void drawInner(Canvas canvas){
        float squareWidth= rectF.height()> rectF.width()?rectF.width()-rectF.width()/2:rectF.height()-rectF.height()/2;
        switch (innerShape){
            case Circle:
                 canvas.drawCircle(rectF.width()/2,rectF.height()/2,squareWidth/2.f,innerP1);
                break;
            case Square:
                canvas.drawRect((rectF.width() - squareWidth) / 2.f, (rectF.height() - squareWidth) / 2.f,
                        (rectF.width() + squareWidth) / 2.f, (rectF.height() + squareWidth) / 2.f, innerP1);
                break;
            case Rectangle:
                canvas.drawRect((rectF.width() - squareWidth-   squareWidth/3.f) / 2.f, (rectF.height() - squareWidth) / 2.f,
                        (rectF.width() + squareWidth+  squareWidth/3.f) / 2.f, (rectF.height() + squareWidth) / 2.f, innerP1);
                break;
            case Trapes:
                canvas.drawPath(TrapesPath((rectF.width() - squareWidth-  squareWidth / 3.f) / 2.f, (rectF.height() - squareWidth) / 2.f,
                        (rectF.width() + squareWidth+  squareWidth/3.f) / 2.f, (rectF.height() + squareWidth) / 2.f), innerP1);
                break;
            case Ellipse:
                RectF r=new RectF();
                r.set((rectF.width() - squareWidth-  squareWidth / 3.f) / 2.f, (rectF.height() - squareWidth) / 2.f,
                        (rectF.width() + squareWidth + squareWidth / 3.f) / 2.f, (rectF.height() + squareWidth) / 2.f);
                canvas.drawRoundRect(r,r.width()/2.f,r.height()/2.f, innerP1);
                break;
            case Star:
                canvas.drawPath(StarPath(rectF.width() / 2, rectF.height() / 2, 5, squareWidth / 2.f, squareWidth / 2.f-squareWidth/4.f),
                        innerP1);
                break;
            case Pentagon:
                canvas.drawPath(StandardPolyPath(rectF.width() / 2, rectF.height() / 2, squareWidth/2.f,5 ),
                        innerP1);
                break;
            case Triangle:
                canvas.drawPath(StandardPolyPath(rectF.width() / 2, rectF.height() / 2, squareWidth/2.f,3  ),
                        innerP1);
                break;
            case Heart:
                canvas.drawPath(HeartPath((rectF.width() - squareWidth) / 2.f, (rectF.height() - squareWidth) / 2.f,
                        (rectF.width() + squareWidth) / 2.f, (rectF.height() + squareWidth) / 2.f), innerP1);
                break;

        }
    }

    public void drawText(Canvas canvas, String text, int width, int height, Paint p, boolean center) {

        Rect bounds = new Rect();
        p.getTextBounds(text, 0, text.length(), bounds);
        if (center) {
            int x = (width / 2);
            int y = (height / 2) - (bounds.height() / 2);
            canvas.drawText(text, x, y, p);
        } else {
            int x = (width / 2);
            int y = (height / 2) + bounds.height();
            canvas.drawText(text, x, y, p);
        }
    }

    @Override
    protected  void onSizeChanged(int w,int h, int oldw,int oldh){
        super.onSizeChanged(w,h,oldw,oldh);
        rectF.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        invalidate();
    }

    @Override
    protected void dispatchSetPressed(boolean pressed) {
        super.dispatchSetPressed(pressed);

        invalidate();
    }

    //draw will be implemented
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOuter(canvas, isPressed());
        drawInner(canvas);
        drawText(canvas, centerText, (int) rectF.width(), (int) rectF.height(), centerTxtP, true);
        drawText(canvas, belowText, (int) rectF.width(), (int) rectF.height(), belowTxtP, false);

    }

}
