package kidtoys.az.kidphone;

/**
 * Created by AbdelRauf on 4/24/2019.
 */

public interface FunnyDisplayBase {
    FunnySurface getSurface();

    /**
     * Gets a copy of the last  surface
     * @return
     */
    FunnySurface getSurfaceSnapshot();

    void copyToSurface(FunnySurface surface);
    void render();
    void clear();
    int getSurfaceWidth();
    int getSurfaceHeight();

    void attachAnimation(BaseAnimation baseAnimation);

    void postRender();
}
