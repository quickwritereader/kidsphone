package kidtoys.az.kidphone;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.AttributeSet;

import java.lang.ref.WeakReference;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import kidtoys.az.kidphone.FunnySurface.DotType;

import static android.opengl.GLES20.glClearColor;
import static android.opengl.Matrix.orthoM;
import static kidtoys.az.kidphone.FunnySurface.DotType.None;
import static kidtoys.az.kidphone.GL_Shapes.Circle;
import static kidtoys.az.kidphone.GL_Shapes.Rectangle;
import static kidtoys.az.kidphone.GL_Shapes.Triangle;

/**
 * Created by Abdelrauf on 4/24/2019.
 * <p>
 * Funny Display Gl
 */

public class FunnyDisplay_GL extends GLSurfaceView implements FunnyDisplayBase, GLSurfaceView.Renderer {

    private final float[] projectionMatrix = new float[16];
    private final float[] placenMatrix = new float[16];
    int surfaceWidth = 20 * FunnySurfaceUtils.scaleX;
    int surfaceHeight = 16 * FunnySurfaceUtils.scaleY;
    DotType prev_type = null;
    private float[] transform = new float[16];
    private WeakReference<BaseAnimation> attachedAnim;
    private FunnySurface mainSurface;
    private FunnySurface backSurface;
    private Triangle mTriangle;
    private float scaledW;
    private float scaledH;
    private Circle mCircle;
    private GL_Shapes.Rectangle mRectangle;
    private GL_Shapes.Romb mRomb;
    private GL_Shapes.Poly mPentagon;
    private GL_Shapes.Poly mHexagon;
    private GL_Shapes.Heart mHeart;

    public FunnyDisplay_GL(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Create an OpenGL ES 2.0 context

        init_GL();
    }

    public FunnyDisplay_GL(Context context) {
        super(context);
        // Create an OpenGL ES 2.0 context
        init_GL();
    }

    private void init_GL() {

        initSurfaces(surfaceWidth, surfaceHeight);
        setEGLContextClientVersion(2);
        setRenderer(this);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }

    private void initSurfaces(int w, int h) {
        mainSurface = new FunnySurface(w, h);
        backSurface = new FunnySurface(w, h);
    }

    /**
     * returns    Surface that you can use to draw
     *
     * @return
     */
    public FunnySurface getSurface() {
        return backSurface;
    }

    @Override
    public FunnySurface getSurfaceSnapshot() {
        FunnySurface surface = new FunnySurface(backSurface.getWidth(), backSurface.getHeight());
        try {
            backSurface.lock();
            surface.CopyFrom(backSurface);
        } finally {
            backSurface.unlock();
        }
        return surface;
    }

    @Override
    public void copyToSurface(FunnySurface surface) {
        try {
            backSurface.lock();
            backSurface.CopyFrom(surface);
        } finally {
            backSurface.unlock();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public synchronized void attachAnimation(BaseAnimation anim) {

        BaseAnimation a = attachedAnim != null ? attachedAnim.get() : null;
        if (a != null) {
            if (a != anim) {
                a.stop(true);
            }
        }
        if (a != anim) {
            if (anim == null) {
                attachedAnim = null;
            } else {
                attachedAnim = new WeakReference<>(anim);
            }
        }
    }

    @Override
    public void postRender() {
        requestRender();
    }

    public void render() {
        requestRender();
    }

    public void clear() {
        mainSurface.lock();
        try {
            mainSurface.clear();
        } finally {
            mainSurface.unlock();
        }
        render();
    }

    @Override
    public int getSurfaceWidth() {
        return mainSurface.getWidth();
    }

    @Override
    public int getSurfaceHeight() {
        return mainSurface.getHeight();
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        // initialize a triangle
        mTriangle = new Triangle();
        mCircle = new Circle();
        mRectangle = new Rectangle();
        mRomb =new GL_Shapes.Romb();
        mPentagon = new GL_Shapes.Poly(5);
        mHexagon=new GL_Shapes.Poly(6);
        mHeart = new GL_Shapes.Heart();
        // Set the background frame color
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int w, int h) {
        GLES20.glViewport(0, 0, w, h);

        //final float aspectRatio = w > h ? (float) w / (float) h : (float) h / (float) w;

        orthoM(projectionMatrix, 0, -1, 1, -1f, 1f, -1f, 1f);

        scaledW = (float) 1.0 / ((float) surfaceWidth);
        scaledH = (float) 1.0 / ((float) surfaceHeight);
        Matrix.setIdentityM(placenMatrix, 0);
        Matrix.scaleM(placenMatrix, 0, scaledW, scaledH, 1);
        Matrix.multiplyMM(placenMatrix, 0, projectionMatrix, 0, placenMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        try {
            backSurface.lock();
            mainSurface.CopyFrom(backSurface);
        } finally {
            backSurface.unlock();
        }

        //draw
        int mw = mainSurface.getWidth();
        int mh = mainSurface.getHeight();
        int mw_half = mw / 2;
        int mh_half = mh / 2;
        float scaledW2 = scaledW * 2;
        float scaledH2 = scaledH * 2;
        for (int j = 0; j < mh; j++) {
            for (int i = 0; i < mw; i++) {

                DotType d = mainSurface.getDotType(i, j);
                FunnySurface.DotColor c = mainSurface.getDotColor(i, j);
                if (d != None) {

                    // scaledW   scaledH
                    float left = (i - mw_half) * scaledW2 + scaledW;
                    float top = (mh_half - j) * scaledH2 - scaledH;
                    Matrix.setIdentityM(transform, 0);
                    Matrix.translateM(transform, 0, transform, 0, left, top, 0);
                    Matrix.multiplyMM(transform, 0, transform, 0, placenMatrix, 0);
                    // Draw triangle
                    switch (d) {
                        case Circle:
                            mCircle.setColor(c);
                            mCircle.draw(transform);
                            break;
                        case Square:
                            mRectangle.setColor(c);
                            mRectangle.draw(transform);
                            break;
                        case Triangle:
                            mTriangle.setColor(c);
                            mTriangle.draw(transform);
                            break;
                        case Romb:
                            mRomb.setColor(c);
                            mRomb.draw(transform);
                            break;
                        case Hexagon:
                            mHexagon.setColor(c);
                            mHexagon.draw(transform);
                            break;
                        case Pentagon:
                            mPentagon.setColor(c);
                            mPentagon.draw(transform);
                            break;
                        case Heart:
                            mHeart.setColor(c);
                            mHeart.draw(transform);
                            break;
                        default:
                            mPentagon.setColor(c);
                            mPentagon.draw(transform);
                    }
                }
            }
        }
    }


}
