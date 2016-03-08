package kidtoys.az.kidphone;

/**
 * here we should implement surface drawing primitivies like line and so on
 * Created by abdurrauf on 3/5/16.
 */
public class FunnySurfaceUtils {


    /**
     * Draw Letter A
     * @param surface
     * @param x  left corner
     * @param y  top corver
     * @param charWidth char width
     * @param charHeight char height
     * @param color   color
     * @param figure figure
     */
    public static void drawA(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        surface.drawLine(x + charWidth / 2, y, x, y + charHeight, color, figure);
        surface.drawLine(x + charWidth / 2, y, x + charWidth, y + charHeight, color, figure);
        surface.drawLine(x + 1, y + (charHeight / 2) + 1, x + charWidth - 1, y + (charHeight / 2) + 1, color, figure);

    }

    /**
     * Draw Letter E
     * @param surface
     * @param x  left corner
     * @param y  top corver
     * @param charWidth char width
     * @param charHeight char height
     * @param color   color
     * @param figure figure
     */
    public static void drawE(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    public static void drawK(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    public static void drawL(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    public static void drawH(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    public static void drawY(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    public static void drawN(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    public static void drawM(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    public static void drawF(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    public static void drawX(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    public static void drawI(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    public static void drawI2(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    public static void drawT(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

}
