package kidtoys.az.kidphone;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by abdurrauf on 2/15/16.
 * This is funny buttons with text and shape and curvy corners
 */
public class FunnyButton extends View {

    private String centerText;
    private String belowText;
    private int centerTextSize;
    private int belowTextSize;
    private int centerTextColor;
    private int belowTextColor;


    private int innerShapeColor;
    private int outerShapeColor;

    public enum InnerShapeType {
        None,
        Square,
        Circle,
        Triangle,
        Pentagon,
        Star,
        Trapes,
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
        return innerShapeColor;
    }

    public void setInnerShapeColor(int innerShapeColor) {
        this.innerShapeColor = innerShapeColor;
        invalidate();
    }

    public int getOuterShapeColor() {
        return outerShapeColor;
    }

    public void setOuterShapeColor(int outerShapeColor) {
        this.outerShapeColor = outerShapeColor;
        invalidate();
    }

    public int getBelowTextColor() {
        return belowTextColor;
    }

    public void setBelowTextColor(int belowTextColor) {
        this.belowTextColor = belowTextColor;
        invalidate();
    }

    public int getCenterTextColor() {
        return centerTextColor;
    }

    public void setCenterTextColor(int centerTextColor) {
        this.centerTextColor = centerTextColor;
        invalidate();
    }

    public String getBelowText() {
        return belowText;
    }

    public void setBelowText(String belowText) {
        this.belowText = belowText;
        invalidate();
    }

    public int getCenterTextSize() {
        return centerTextSize;
    }

    public void setCenterTextSize(int centerTextSize) {
        this.centerTextSize = centerTextSize;
        invalidate();
    }

    public int getBelowTextSize() {
        return belowTextSize;
    }

    public void setBelowTextSize(int belowTextSize) {
        this.belowTextSize = belowTextSize;
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

    }

    public void draw() {
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
        path.moveTo(right, top+ bry + bry);
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

    public static int getNewColor(int color, int add){
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = red + add;
        green += add;
        blue += add;
        if(red<0)red=0;
        if(green<0)green=0;
        if(blue<0)blue=0;
        if (red > 255) red = 255;
        if (green > 255) green = 255;
        if (blue > 255) blue = 255;

        color = Color.argb(Color.alpha(color), red, green, blue);
        return color;
    }
    public void drawOuter(Canvas canvas, OuterShapeType type, int width, int height, int color, boolean pressed) {
        Paint p = new Paint();
        Paint pAdd=new Paint();
        float scaleDiv=24;
        float scaleDivb=36;
        if (pressed) {
            //darken our color
            //outercolor
            color=getNewColor(color,-10);
            scaleDivb=scaleDivb*1.5f;
            scaleDiv=scaleDiv*1.5f;
        }
        p.setColor(color);
        pAdd.setColor(getNewColor(color,-50));
        pAdd.setAntiAlias(true);
        p.setAntiAlias(true);
        RectF rectF = new RectF();
        rectF.set(0, 0, width, height);
        switch (type) {
            case None: {
                canvas.drawColor(color);
            }
            break;
            case Rounded: {
                canvas.drawRoundRect(rectF, rectF.right / 4, rectF.bottom / 2, pAdd);
                rectF.left=rectF.right/scaleDiv;
                rectF.top=rectF.bottom/scaleDivb;
                rectF.right-=rectF.right/scaleDiv;
                rectF.bottom-= rectF.bottom/scaleDiv;
                canvas.drawRoundRect(rectF, rectF.width() / 4, rectF.height() / 2, p );
            }
            break;
            case BottomRounded: {
                canvas.drawPath(BottomRounded(rectF.left, rectF.top, rectF.right, rectF.bottom, rectF.width() / 8, rectF.height() / 8, rectF.width() / 2, rectF.height() / 2), pAdd);
                rectF.left=rectF.right/scaleDiv;
                rectF.top=rectF.bottom/scaleDivb;
                rectF.right-=rectF.right/scaleDiv;
                rectF.bottom-= rectF.bottom/scaleDiv;
                canvas.drawPath(BottomRounded(rectF.left, rectF.top, rectF.right, rectF.bottom, rectF.width() / 8, rectF.height() / 8, rectF.width() / 2, rectF.height() / 2), p );
            }
            break;
            case YesButton:

        }

    }

    public void drawText(Canvas canvas,String text,int width ,int height, float size,int color,boolean center){
        Paint p=new Paint();
        p.setColor(color);
        p.setTextSize(size);
        p.setAntiAlias(true);
        p.setTextAlign(Paint.Align.CENTER);
        Rect bounds = new Rect();
        p.getTextBounds(text, 0, text.length(), bounds);
        if(center) {
            int x = (canvas.getWidth() / 2) ;
            int y = (canvas.getHeight() / 2) - (bounds.height() / 2);
            canvas.drawText(text, x, y, p);
        }else{
            int x = (canvas.getWidth() / 2)  ;
            int y = (canvas.getHeight() / 2) + bounds.height();
            canvas.drawText(text, x, y, p);
        }
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
        int width = getWidth();
        int height = getHeight();
        drawOuter(canvas, this.outerShape, width, height, outerShapeColor,isPressed());
        drawText(canvas,centerText,width,height,centerTextSize,centerTextColor,true);
        drawText(canvas,belowText,width,height,belowTextSize,belowTextColor,false);

    }

}
