package kidtoys.az.kidphone;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by abdurrauf on 2/15/16.
 * This is funny buttons with text and shape and curvy corners
 *
 */
public class FunnyButton extends View {

    private String centerText;
    private String belowText;
    private float centerTextSize;
    private float belowTextSize;
    private int centerTextColor;
    private int belowTextColor;

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

    public InnerShapeType getInnerShape() {
        return innerShape;
    }

    public void setInnerShape(InnerShapeType innerShape) {
        this.innerShape = innerShape;
    }

    public OuterShapeType getOuterShape() {
        return outerShape;
    }

    public void setOuterShape(OuterShapeType outerShape) {
        this.outerShape = outerShape;
    }

    private  OuterShapeType outerShape;

    public int getBelowTextColor() {
        return belowTextColor;
    }

    public void setBelowTextColor(int belowTextColor) {
        this.belowTextColor = belowTextColor;
    }

    public int getCenterTextColor() {
        return centerTextColor;
    }

    public void setCenterTextColor(int centerTextColor) {
        this.centerTextColor = centerTextColor;
    }

    public String getBelowText() {
        return belowText;
    }

    public void setBelowText(String belowText) {
        this.belowText = belowText;
    }

    public float getCenterTextSize() {
        return centerTextSize;
    }

    public void setCenterTextSize(float centerTextSize) {
        this.centerTextSize = centerTextSize;
    }

    public float getBelowTextSize() {
        return belowTextSize;
    }

    public void setBelowTextSize(float belowTextSize) {
        this.belowTextSize = belowTextSize;
    }


    public String getCenterText() {
        return centerText;
    }

    public void setCenterText(String centerText) {
        this.centerText = centerText;
    }


    public FunnyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.FunnyButton, 0, 0);
        try {
            centerText = typedArray.getString(R.styleable.FunnyButton_centerText);
            belowText = typedArray.getString(R.styleable.FunnyButton_belowText);
            centerTextColor = typedArray.getColor(R.styleable.FunnyButton_centerTextColor, 0);
            belowTextColor = typedArray.getColor(R.styleable.FunnyButton_belowTextColor, 0);
            centerTextSize = typedArray.getFloat(R.styleable.FunnyButton_centerTextSize, 14);
            belowTextSize = typedArray.getFloat(R.styleable.FunnyButton_belowTextSize, 14);
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

    //draw will be implemented

}
