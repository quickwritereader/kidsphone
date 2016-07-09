package kidtoys.az.kidphone;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by abdurrauf on 2/15/16.
 * This is funny buttons with text and shape and curvy corners
 */
public class FunnyButton extends View {

    View.OnClickListener clickListener;
    Bitmap latestNormal = null;
    Bitmap latestPressed = null;
    Bitmap figure = null;
    private String numbersText;
    private boolean hasChanges = true;
    private boolean hasFigureChange = false;
    private String lettersText;

    private Paint innerP1;
    private int outerShapeColor;
    private Paint outerP1;
    private Paint outerP2;
    private Paint centerTxtP;
    private RectF rectF = new RectF();
    private RectF rectF2 = new RectF();

    public Drawable getPicture() {
        return picture;
    }

    public void setPicture(int id) {
        this.picture = ResourcesCompat.getDrawable(getResources(),id,null);
        hasFigureChange=true;
        invalidate();
    }

    private Rect bounds = new Rect();
    private InnerShapeType innerShape;
    private OuterShapeType outerShape;
    private Drawable picture=null;
    private KeyMode bMode;

    public FunnyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.FunnyButton, 0, 0);
        this.setClickable(true);
        float centerTextSize;
        int centerTextColor, innerShapeColor;
        bMode = KeyMode.Letters;

        try {
            numbersText = typedArray.getString(R.styleable.FunnyButton_NumbersText);
            lettersText = typedArray.getString(R.styleable.FunnyButton_LettersText);
            centerTextColor = typedArray.getColor(R.styleable.FunnyButton_TextColor, 0);
            centerTextSize = typedArray.getDimensionPixelSize(R.styleable.FunnyButton_TextSize, 14);
            innerShapeColor = typedArray.getColor(R.styleable.FunnyButton_innerShapeColor, 0);
            outerShapeColor = typedArray.getColor(R.styleable.FunnyButton_outerShapeColor, 0);
            picture = typedArray.getDrawable(R.styleable.FunnyButton_pictureSrc);
            int ordinal = typedArray.getInt(R.styleable.FunnyButton_InnerShapeProperty, 0);

            if (ordinal >= 0 && ordinal < InnerShapeType.values().length) {
                innerShape = InnerShapeType.values()[ordinal];
            }
            ordinal = typedArray.getInt(R.styleable.FunnyButton_OuterShapeProperty, 0);
            if (ordinal >= 0 && ordinal < InnerShapeType.values().length) {
                outerShape = OuterShapeType.values()[ordinal];
            }
            ordinal = typedArray.getInt(R.styleable.FunnyButton_KeyMode, KeyMode.Letters.ordinal());
            if (ordinal >= 0 && ordinal < InnerShapeType.values().length) {
                bMode = KeyMode.values()[ordinal];
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
        rectF.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
    }

    private static int getNewColor(int color, int add) {
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

    public String getLettersText() {
        return lettersText;
    }

    public void setLettersText(String lettersText) {
        this.lettersText = lettersText;
        invalidate();
    }

    public KeyMode getKeyMode() {
        return bMode;
    }

    public void setKeyMode(KeyMode bMode) {
        if (this.bMode != bMode) {
            this.bMode = bMode;
            invalidate();
        }
    }

    public InnerShapeType getInnerShape() {
        return innerShape;
    }

    public void setInnerShape(InnerShapeType innerShape) {
        this.innerShape = innerShape;
        invalidate();
        hasFigureChange = true;
    }

    public OuterShapeType getOuterShape() {
        return outerShape;
    }

    public void setOuterShape(OuterShapeType outerShape) {
        this.outerShape = outerShape;
        invalidate();
        hasChanges = true;
    }

    public int getInnerShapeColor() {
        return innerP1.getColor();
    }

    public void setInnerShapeColor(int innerShapeColor) {
        this.innerP1.setColor(innerShapeColor);
        invalidate();
        hasFigureChange = true;
    }

    public int getOuterShapeColor() {
        return this.outerShapeColor;
    }

    public void setOuterShapeColor(int outerShapeColor) {
        this.outerShapeColor = outerShapeColor;
        invalidate();
        hasChanges = true;
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

    private void drawOuter(Canvas canvas, boolean pressed) {

        float scaleDivision = 32 * 1.6f;
        int color = getOuterShapeColor();
        if (pressed) {
            color = getNewColor(color, -50);
            outerP1.setColor(color);
            scaleDivision = 32;
        } else {
            outerP1.setColor(color);
        }
        outerP2.setColor(getNewColor(color, -90));
        float pad = rectF.height() / scaleDivision;
        if (pad > 5) pad = 5;
        rectF2.left = rectF.left + pad;
        rectF2.top = rectF.top + pad;
        rectF2.right = rectF.right - pad;
        rectF2.bottom = rectF.bottom - pad;

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
                canvas.drawPath(CanvasUtils.BottomRounded(rectF.left, rectF.top, rectF.right, rectF.bottom, rectF.width() / 8, rectF.height() / 8, rectF.width() / 2, rectF.height() / 2), outerP2);
                canvas.drawPath(CanvasUtils.BottomRounded(rectF2.left, rectF2.top, rectF2.right, rectF2.bottom, rectF2.width() / 8, rectF2.height() / 8, rectF2.width() / 2, rectF2.height() / 2), outerP1);
            }
            break;
            case YesButton: {
                canvas.drawPath(CanvasUtils.YesButtonPath(rectF.left, rectF.top, rectF.width(), rectF.height()), outerP2);
                canvas.drawPath(CanvasUtils.YesButtonPath(rectF2.left, rectF2.top, rectF2.width(), rectF2.height()), outerP1);
            }
            break;
            case NoButton: {
                canvas.drawPath(CanvasUtils.NoButtonPath(rectF.left, rectF.top, rectF.width(), rectF.height()), outerP2);
                canvas.drawPath(CanvasUtils.NoButtonPath(rectF2.left, rectF2.top, rectF2.width(), rectF2.height()), outerP1);
            }
            break;
        }

    }


    private void drawInner(Canvas canvas) {
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
                canvas.drawPath(CanvasUtils.TrapesPath(cx - radius - radius / 3.f, cy - radius,
                        cx + radius + radius / 3, cy + radius), innerP1);
                break;
            case Ellipse:
                RectF r = new RectF();
                r.set(cx - radius - radius / 3.f, cy - radius,
                        cx + radius + radius / 3, cy + radius);
                canvas.drawRoundRect(r, r.width() / 2.f, radius, innerP1);
                break;
            case Star:
                canvas.drawPath(CanvasUtils.StarPath(cx, cy, 5, radius, radius - radius / 2.f),
                        innerP1);
                break;
            case Hexagon:
                canvas.drawPath(CanvasUtils.StandardPolyPath(cx, cy, radius, 6,-(float) (Math.PI )),
                        innerP1);
                break;
            case Pentagon:
                canvas.drawPath(CanvasUtils.StandardPolyPath(cx, cy, radius, 5),
                        innerP1);
                break;
            case Triangle:
                canvas.drawPath(CanvasUtils.StandardPolyPath(cx, cy, radius, 3),
                        innerP1);
                break;
            case Heart:
                canvas.drawPath(CanvasUtils.HeartPath(cx - radius, cy - radius,
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
                canvas.drawPath(CanvasUtils.PhonePath(left + pull, cy - radius,
                        right + pull, cy + radius, isNo), innerP1);
            }
            break;
            case Picture:
            {
                if(picture!=null){
                    radius+=10;
                    picture.setBounds((int)(cx - radius), (int)(cy - radius),
                            (int)(  cx + radius),(int) (cy + radius));
                    picture.draw(canvas);
                }
            }
            break;
            default:

        }
    }

    private void drawText(Canvas canvas, String text, Paint p) {
        if (text == null) return;
        p.getTextBounds(text, 0, text.length(), bounds);
        float x = rectF.left + rectF.width() / 2.f;
        float y = rectF.top + rectF.height() / 2.f;
        canvas.drawText(text, x, y - bounds.exactCenterY(), p);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        rectF.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        invalidate();
        hasChanges = true;
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

        if (hasChanges) {
            //re Init button look
            hasChanges = false;
            if (latestNormal != null) latestNormal.recycle();
            if (latestPressed != null) latestPressed.recycle();
            Bitmap.Config conf = Bitmap.Config.ARGB_4444;
            latestNormal = Bitmap.createBitmap(getWidth(), getHeight(), conf);
            latestPressed = Bitmap.createBitmap(getWidth(), getHeight(), conf);
            Canvas c = new Canvas(latestNormal);
            drawOnCanvas(c, false);
            c.setBitmap(latestPressed);
            drawOnCanvas(c, true);
            c = null;
        }
        if (isPressed()) {
            if (latestPressed != null) canvas.drawBitmap(latestPressed, 0, 0, null);
        } else {
            if (latestNormal != null) canvas.drawBitmap(latestNormal, 0, 0, null);
        }
        if (bMode == KeyMode.Figures || bMode == KeyMode.Normal || bMode == KeyMode.System) {
            if (hasFigureChange || figure == null) {
                if (figure != null) figure.recycle();
                Bitmap.Config conf = Bitmap.Config.ARGB_4444;
                figure = Bitmap.createBitmap(getWidth(), getHeight(), conf);

                Canvas c = new Canvas(figure);
                drawInner(c);
                hasFigureChange = false;
            }

            if (figure != null) canvas.drawBitmap(figure, 0, 0, null);
        }
        if ((bMode != KeyMode.Figures && bMode != KeyMode.System) || bMode == KeyMode.Normal) {
            String t;
            if (bMode == KeyMode.Letters) {
                t = lettersText;
            } else {
                t = numbersText;
            }
            drawText(canvas, t, centerTxtP);
        }

    }

    private void drawOnCanvas(Canvas canvas, boolean pressed) {
        drawOuter(canvas, pressed);
    }
    public enum KeyMode {
        Normal,
        Figures,
        Numbers,
        Letters,
        System ,
        Gaming
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
        Hexagon,
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

}
