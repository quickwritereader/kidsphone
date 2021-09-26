package kidtoys.az.kidphone;

import android.content.Context;
import android.graphics.ColorSpace;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.AttributeSet;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Arrays;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import kidtoys.az.kidphone.FunnySurface.DotType;

import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glCullFace;
import static android.opengl.GLES20.glEnable;
import static android.opengl.Matrix.frustumM;
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
    public static final int surfaceWidth = 20 * FunnySurfaceUtils.scaleX;
    public static final int surfaceHeight = 16 * FunnySurfaceUtils.scaleY;
    private boolean draw_grid=true;
    private float[] modelMatrix = new float[16];
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
    private GL_Shapes.GridDot2 mGrid;
    private GL_Shapes.Star mStar;
    private float[] viewMatrix = new float[16];
    private float[] m_v_pMatrix = new float[16];


    public void setDraw_grid(boolean draw_grid) {
        this.draw_grid = draw_grid;
    }



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
        backSurface.lock();
        try {
            backSurface.clear();
        } finally {
            backSurface.unlock();
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
        mStar = new GL_Shapes.Star(8);
        mGrid =new GL_Shapes.GridDot2();
        mGrid.setColorRgb( 0x00,0x10,0x5d);
        mGrid.setGrid(mainSurface.getWidth(),mainSurface.getHeight());
        // Set the background frame color
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int w, int h) {
        GLES20.glViewport(0, 0, w, h);

        // enable face culling feature
        glEnable(GL10.GL_CULL_FACE);
        // specify which faces to not draw
        glCullFace(GL10.GL_BACK);
       // frustumM(projectionMatrix, 0, -1, 1, -1f, 1f, 3f, 10f);
        orthoM(projectionMatrix, 0, -surfaceWidth/2.f, surfaceWidth/2.f,
                -surfaceHeight/2.f, surfaceHeight/2.f, 0.1f, 10f);

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
        Matrix.setIdentityM(viewMatrix, 0);
        //Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //draw
        if(draw_grid) {
            Matrix.setIdentityM(modelMatrix, 0);
            Matrix.translateM(modelMatrix,0,modelMatrix,0,-surfaceWidth/2.f,surfaceHeight/2,-1f);
            Matrix.scaleM(modelMatrix,0,surfaceWidth,surfaceHeight,1);
            Matrix.multiplyMM(m_v_pMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
            Matrix.multiplyMM(m_v_pMatrix, 0, m_v_pMatrix, 0, modelMatrix, 0);
            mGrid.draw(m_v_pMatrix);
        }
        for (int j = 0; j < surfaceHeight; j++) {
            for (int i = 0; i < surfaceWidth; i++) {

                DotType d = mainSurface.getDotType(i, j);
                FunnySurface.DotColor c = mainSurface.getDotColor(i, j);
                if (d != None) {

                    // scaledW   scaledH
                    float left = (i - surfaceWidth/2.f) ;// + scaledW;
                    float top = (surfaceHeight/2.f - j)  ;// - scaledH;
                    Matrix.setIdentityM(modelMatrix, 0);
                    Matrix.translateM(modelMatrix, 0, modelMatrix, 0, left, top, -1f);
                    //scale object as its from -1 to 1 with width 2
                    Matrix.scaleM(modelMatrix,0,0.5f,0.5f,1);
//
                    // Combine the projection and camera view matrices
                    Matrix.multiplyMM(m_v_pMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
                    Matrix.multiplyMM(m_v_pMatrix, 0, m_v_pMatrix, 0, modelMatrix, 0);
                    // Draw triangle
                    //Log.d("dot type", String.valueOf(d));
                    switch (d) {
                        case Circle:
                            mCircle.setColor(c);
                            mCircle.draw(m_v_pMatrix);
                            break;
                        case Square:
                            mRectangle.setColor(c);
                            mRectangle.draw(m_v_pMatrix);
                            break;
                        case Triangle:
                            mTriangle.setColor(c);
                            mTriangle.draw(m_v_pMatrix);
                            break;
                        case Romb:
                            mRomb.setColor(c);
                            mRomb.draw(m_v_pMatrix);
                            break;
                        case Hexagon:
                            mHexagon.setColor(c);
                            mHexagon.draw(m_v_pMatrix);
                            break;
                        case Pentagon:
                            mPentagon.setColor(c);
                            mPentagon.draw(m_v_pMatrix);
                            break;
                        case Heart:
                            mHeart.setColor(c);
                            mHeart.draw(m_v_pMatrix);
                            break;
                        case Star:
                            mStar.setColor(c);
                            mStar.draw(m_v_pMatrix);
                            break;
                        default:
                            mPentagon.setColor(c);
                            mPentagon.draw(m_v_pMatrix);
                    }
                }
            }
        }
    }


}
