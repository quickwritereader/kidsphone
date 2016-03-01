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
import android.view.View;


/**
 * Created by abdurrauf on 2/15/16.
 * This is funny buttons with text and shape and curvy corners
 */
public class FunnyButton extends View {

    private String numbersText;

    public String getLettersText() {
        return lettersText;
    }

    public void setLettersText(String lettersText) {
        this.lettersText = lettersText;
        invalidate();
    }

    public BehaviorMode getbMode() {
        return bMode;
    }

    public void setbMode(BehaviorMode bMode) {
        this.bMode = bMode;
        invalidate();
    }

    private String lettersText;

    private Paint innerP1;
    private int outerShapeColor;
    private Paint outerP1;
    private Paint outerP2;
    private Paint centerTxtP;
    private RectF rectF;


    public enum BehaviorMode {
        Normal,
        Figures,
        Numbers,
        Letters,
        System
    }

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
    private BehaviorMode bMode;


    View.OnClickListener clickListener;


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
        this.innerP1.setColor(innerShapeColor);
        invalidate();
    }

    public int getOuterShapeColor() {
        return this.outerShapeColor;
    }

    public void setOuterShapeColor(int outerShapeColor) {
        this.outerShapeColor = outerShapeColor;
        invalidate();
    }


    public int getTextColor() {
        return centerTxtP.getColor();
    }

    public void setTextColor(int TextColor) {
        this.centerTxtP.setColor(TextColor);
        invalidate();
    }


    public float getTextSize() {
        return centerTxtP.getTextSize();
    }

    public void setTextSize(float TextSize) {
        this.centerTxtP.setTextSize(TextSize);
        invalidate();
    }


    public String getNumbersText() {
        return numbersText;
    }

    public void setNumbersText(String numbersText) {
        this.numbersText = numbersText;
        invalidate();
    }


    public FunnyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.FunnyButton, 0, 0);
        this.setClickable(true);
        float centerTextSize;
        int centerTextColor, innerShapeColor;
        bMode = BehaviorMode.Normal;

        try {
            numbersText = typedArray.getString(R.styleable.FunnyButton_NumbersText);
            lettersText = typedArray.getString(R.styleable.FunnyButton_LettersText);
            centerTextColor = typedArray.getColor(R.styleable.FunnyButton_TextColor, 0);
            centerTextSize = typedArray.getDimensionPixelSize(R.styleable.FunnyButton_TextSize, 14);
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
            ordinal = typedArray.getInt(R.styleable.FunnyButton_BehaviorMode, 0);
            if (ordinal >= 0 && ordinal < InnerShapeType.values().length) {
                bMode = BehaviorMode.values()[ordinal];
            }
        } finally {
            typedArray.recycle();
        }
        innerP1 = new Paint();
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
        rectF = new RectF();
        rectF.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
    }

    public void draw() {
    }


    //yes button
    static public Path YesButtonPath(float left, float top, float width, float height) {
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

        float scaleDiv = 30 * 1.5f;
        float scaleDivb = 36 * 1.5f;
        int color = getOuterShapeColor();
        if (pressed) {
            //darken our color
            //outercolor
            color = getNewColor(color, -40);
            outerP1.setColor(color);
            scaleDivb = 30;
            scaleDiv = 36;
        } else {
            outerP1.setColor(color);
        }
        outerP2.setColor(getNewColor(color, -80));
        RectF rectF2 = new RectF();
        rectF2.left = rectF.left + rectF.width() / scaleDiv;
        rectF2.top = rectF.top + rectF.height() / scaleDivb;
        rectF2.right = rectF.right - rectF.width() / scaleDiv;
        rectF2.bottom = rectF.bottom - rectF.height() / scaleDiv;

        switch (outerShape) {
            case None: {
                canvas.drawColor(color);
            }
            break;
            case Rounded: {
                canvas.drawRoundRect(rectF, rectF.width() / 4, rectF.height() / 2, outerP2);
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

    public static Path StarPath(float cx, float cy, int spikes, float outerRadius, float innerRadius) {
        Path path = new Path();
        float rot = (float) Math.PI / 2 * 3;
        float x = cx;
        float y = cy;
        float step = (float) Math.PI / spikes;
        path.moveTo(cx, cy - outerRadius);
        for (int i = 0; i < spikes; i++) {
            x = cx + (float) Math.cos(rot) * outerRadius;
            y = cy + (float) Math.sin(rot) * outerRadius;
            path.lineTo(x, y);
            rot += step;
            x = cx + (float) Math.cos(rot) * innerRadius;
            y = cy + (float) Math.sin(rot) * innerRadius;
            path.lineTo(x, y);
            rot += step;
        }
        path.lineTo(cx, cy - outerRadius);

        path.close();
        return path;
    }

    public static Path TrapesPath(float left, float top, float right, float bottom) {
        Path path = new Path();
        path.moveTo(left, top);
        path.lineTo(right - (right - left) / 4.f, top);
        path.lineTo(right, bottom);
        path.lineTo(left, bottom);
        path.close();
        return path;
    }

    public Path StandardPolyPath(float cx, float cy, float radius, int sides) {
        if (sides < 3) return new Path();
        Path path = new Path();
        float start = -(float) (Math.PI / 2);
        float a = (float) (Math.PI * 2) / (float) sides;

        path.moveTo(cx, cy + radius * (float) Math.sin(start));
        //Log.d("p: ", " " + sides + "p: " + cx  + " : " + cy  +" ;" +radius*(float)Math.sin(start));

        for (int i = 1; i < sides; i++) {
            // Log.d("p: ", " "+sides+"p: " + cx +" : " + (float) radius * (float) Math.cos(a * i + start) + " : " + cy + " : " + (float) radius * (float) Math.sin(a * i + start));
            path.lineTo(cx + (float) radius * (float) Math.cos(a * i + start), cy + (float) radius * (float) Math.sin(a * i + start));
        }
        path.close();
        return path;
    }

    public static Path HeartPath(float left, float top, float right, float bottom) {
        Path path = new Path();
        float d = Math.min(right - left, bottom - top);
        float k = left;
        float k2 = top;
        path.moveTo(k, k2 + d / 4);
        path.quadTo(k, k2, k + d / 4, k2);
        path.quadTo(k + d / 2, k2, k + d / 2, k2 + d / 4);
        path.quadTo(k + d / 2, k2, k + d * 3 / 4, k2);
        path.quadTo(k + d, k2, k + d, k2 + d / 4);
        path.quadTo(k + d, k2 + d / 2, k + d * 3 / 4, k2 + d * 3 / 4);
        path.lineTo(k + d / 2, k2 + d);
        path.lineTo(k + d / 4, k2 + d * 3 / 4);
        path.quadTo(k, k2 + d / 2, k, k2 + d / 4);
        path.close();
        return path;
    }

    public static Path PhonePath(float left, float top, float right, float bottom, boolean isNo) {
        Path p = new Path();
        float x = left;
        float y = top;
        float w = right - left;
        float h = bottom - top;
        //make sure that height is half of width
        if (h > w / 2.f) h = w / 2.f;
        p.moveTo(x, y + h / 4);
        p.lineTo(x, y + h);
        p.lineTo(x + w / 4, y + h - h / 4);
        p.lineTo(x + w / 4, y + h / 2);
        p.lineTo(x + w - w / 4, y + h / 2);
        p.lineTo(x + w - w / 4, y + h - h / 4);
        p.lineTo(x + w, y + h);
        p.lineTo(x + w, y + h / 4);
        p.cubicTo(x + w, y, x, y, x, y + h / 4);
        if (isNo) {
            p.moveTo(x + w / 4 + w / 12, y + h - h / 4);
            p.lineTo(x + w - w / 4 - w / 12, y + h - h / 4);
            p.lineTo(x + w - w / 4 - w / 12, y + h);
            p.lineTo(x + w / 4 + w / 12, y + h);
            p.lineTo(x + w / 4 + w / 12, y + h - h / 4);
        }
        p.close();
        return p;
    }

    public void drawInner(Canvas canvas) {
        float squareWidth = rectF.height() > rectF.width() ? rectF.width() / 1.5f : rectF.height() / 1.5f;
        float cx = rectF.left + rectF.width() / 2;
        float cy = rectF.top + rectF.height() / 2;
        float radius = squareWidth / 2.f;
        switch (innerShape) {
            case Circle:
                canvas.drawCircle(cx, cy, squareWidth / 2.f, innerP1);
                break;
            case Square:
                canvas.drawRect(cx - radius, cy - radius,
                        cx + radius, cy + radius, innerP1);
                break;
            case Rectangle:
                canvas.drawRect(cx - radius - radius / 3.f, cy - radius,
                        cx + radius + radius / 3, cy + radius, innerP1);
                break;
            case Trapes:
                canvas.drawPath(TrapesPath(cx - radius - radius / 3.f, cy - radius,
                        cx + radius + radius / 3, cy + radius), innerP1);
                break;
            case Ellipse:
                RectF r = new RectF();
                r.set(cx - radius - radius / 3.f, cy - radius,
                        cx + radius + radius / 3, cy + radius);
                canvas.drawRoundRect(r, r.width() / 2.f, radius, innerP1);
                break;
            case Star:
                canvas.drawPath(StarPath(cx, cy, 5, radius, radius - radius / 2.f),
                        innerP1);
                break;
            case Pentagon:
                canvas.drawPath(StandardPolyPath(cx, cy, radius, 5),
                        innerP1);
                break;
            case Triangle:
                canvas.drawPath(StandardPolyPath(cx, cy, radius, 3),
                        innerP1);
                break;
            case Heart:
                canvas.drawPath(HeartPath(cx - radius, cy - radius,
                        cx + radius, cy + radius), innerP1);
                break;
            case NoPhone:
            case YesPhone: {
                float pull = 0;
                float left = cx - radius - radius / 3.f;
                float right = cx + radius + radius / 3.f;
                boolean isNo = innerShape == InnerShapeType.NoPhone;
                if (left > 0) {
                    pull = left / 2.f;
                    if (outerShape == OuterShapeType.YesButton) pull = -pull;
                }
                //pad a bit
                cy += 3;
                canvas.drawPath(PhonePath(left + pull, cy - radius,
                        right + pull, cy + radius, isNo), innerP1);
            }
            break;

        }
    }

    public void drawText(Canvas canvas, String text, Paint p) {
        if (text == null) return;
        Rect bounds = new Rect();
        p.getTextBounds(text, 0, text.length(), bounds);
        float x = rectF.left + rectF.width() / 2.f;
        float y = rectF.top + rectF.height() / 2.f;
        canvas.drawText(text, x, y - bounds.exactCenterY(), p);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
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
        if (bMode == BehaviorMode.Figures || bMode == BehaviorMode.Normal || bMode == BehaviorMode.System) {
            drawInner(canvas);
        }
        if ((bMode != BehaviorMode.Figures && bMode != BehaviorMode.System) || bMode == BehaviorMode.Normal) {
            String t = "";
            if (bMode == BehaviorMode.Letters) {
                t = lettersText;
            } else {
                t = numbersText;
            }
            drawText(canvas, t, centerTxtP);
        }

    }

}
