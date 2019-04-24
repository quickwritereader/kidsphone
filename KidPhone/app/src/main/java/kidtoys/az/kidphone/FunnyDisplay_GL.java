package kidtoys.az.kidphone;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import java.lang.ref.WeakReference;

/**
 * Created by Abdelrauf on 4/24/2019.
 *
 * Funny Display Gl
 */

public class FunnyDisplay_GL extends GLSurfaceView implements FunnyDisplayBase {

    private WeakReference<BaseAnimation> attachedAnim;
    private final FunnyDisplayGLRenderer renderer;
    private FunnySurface mainSurface;
    private  FunnySurface backSurface;

    public FunnyDisplay_GL(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        renderer = new FunnyDisplayGLRenderer();
        setRenderer(renderer);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public FunnyDisplay_GL(Context context) {
        super(context);
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        renderer = new FunnyDisplayGLRenderer();
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
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
        FunnySurface surface=new FunnySurface(backSurface.getWidth(), backSurface.getHeight());
        try {
            backSurface.lock();
            surface.CopyFrom(backSurface);
        }finally {
            backSurface.unlock();
        }
        return surface;
    }

    @Override
    public void copyToSurface(FunnySurface surface) {
        try {
            backSurface.lock();
            backSurface.CopyFrom(surface);
        }finally {
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

    @Override
    public int getSurfaceWidth() {
        return 15;
    }

    @Override
    public int getSurfaceHeight() {
        return 20;
    }


}
