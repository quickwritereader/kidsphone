package kidtoys.az.kidphone;

/**
 * here we should implement surface drawing primitivies like line and so on
 * Implementation by Abdurrauf and Ramil
 */
public class FunnySurfaceUtils {
    /**
     * Draw Letter A
     *
     * @param surface
     * @param x          left corner
     * @param y          top corver
     * @param charWidth  char width
     * @param charHeight char height
     * @param color      color
     * @param figure     figure
     */
    private static void drawA(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        if (charWidth > charHeight) charWidth = charHeight - 1;
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, bottom, x, y + centerOffset, color, figure, clbk);
        surface.drawLine(x, y + centerOffset, x + centerOffset, y, color, figure, clbk);
        surface.drawLine(right - centerOffset, y, right, y + centerOffset, color, figure, clbk);
        surface.drawLine(right, y + centerOffset, right, bottom, color, figure, clbk);
        surface.drawLine(x, y + charHeight / 2 + 1, right, y + charHeight / 2 + 1, color, figure, clbk);

    }

    /**
     * Draw Letter E
     *
     * @param surface
     * @param x          left corner
     * @param y          top corver
     * @param charWidth  char width
     * @param charHeight char height
     * @param color      color
     * @param figure     figure
     */
    private static void drawE(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(right, y, x, y, color, figure, clbk);
        surface.drawLine(x, y, x, bottom, color, figure, clbk);
        surface.drawLine(x, bottom, right, bottom, color, figure, clbk);
        surface.drawLine(x, y + centerOffset + 1, x + centerOffset + 1, y + centerOffset + 1, color, figure, clbk);

    }

    private static void drawK(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0);
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, y, x, bottom, color, figure, clbk);
        surface.drawLine(x, y + centerOffset, x + centerOffset, y, color, figure, clbk);
        surface.drawLine(x, bottom - centerOffset, x + centerOffset, bottom, color, figure, clbk);
    }

    private static void drawL(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {

        int bottom = y + charHeight - 1;
        surface.drawLine(x, y, x, bottom, color, figure, clbk);
        surface.drawLine(x, bottom, x + charWidth - 1, bottom, color, figure, clbk);
    }

    private static void drawY(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {

        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, y, x + centerOffset, y + centerOffset, color, figure, clbk);
        surface.drawLine(right - centerOffset, y + centerOffset, right, y, color, figure, clbk);
        surface.drawLine(right - centerOffset, y + centerOffset, x + centerOffset, bottom, color, figure, clbk);
    }

    private static void drawN(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {

        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, bottom, x, y, color, figure, clbk);
        surface.drawLine(x, y, right, bottom, color, figure, clbk);
        surface.drawLine(right, bottom, right, y, color, figure, clbk);
    }

    private static void drawM(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, bottom, x, y, color, figure, clbk);
        surface.drawLine(x, y, x + centerOffset, y + centerOffset, color, figure, clbk);
        surface.drawLine(right - centerOffset, y + centerOffset, right, y, color, figure, clbk);
        surface.drawLine(right, y, right, bottom, color, figure, clbk);
    }

    private static void drawF(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, y, x, bottom, color, figure, clbk);
        surface.drawLine(x, y, right, y, color, figure, clbk);
        surface.drawLine(x, y + centerOffset + 1, x + centerOffset + 1, y + centerOffset + 1, color, figure, clbk);
    }

    private static void drawX(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {

        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, y, right, bottom, color, figure, clbk);
        surface.putDot(right, bottom, color, figure, clbk);
        surface.drawLine(right, y, x, bottom, color, figure, clbk);
    }

    private static void drawII(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {

        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        surface.drawLine(x + centerOffset, y, x + centerOffset, bottom, color, figure, clbk);
        surface.drawLine(x + centerOffset - 1, bottom, x + centerOffset + 1, bottom, color, figure, clbk);
        surface.drawLine(x + centerOffset - 1, y, x + centerOffset + 1, y, color, figure, clbk);
    }

    private static void drawI(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        surface.drawLine(x + centerOffset, y, x + centerOffset, bottom, color, figure, clbk);
        surface.drawLine(x + centerOffset - 1, bottom, x + centerOffset + 1, bottom, color, figure, clbk);
        surface.drawLine(x + centerOffset - 1, y, x + centerOffset + 1, y, color, figure, clbk);
        surface.putDot(x + centerOffset, y - 2, color, figure, clbk);
    }

    private static void drawT(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        int centerOf = (int) Math.ceil(charWidth / 2.0) - 1;
        surface.drawLine(x + centerOf, y, x + centerOf, bottom, color, figure, clbk);
        surface.drawLine(x, y, right, y, color, figure, clbk);
    }

    private static void drawV(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        if (charWidth > charHeight) charWidth = charHeight - 1;
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, y, x, bottom - centerOffset, color, figure, clbk);
        surface.drawLine(x, bottom - centerOffset, x + centerOffset, bottom, color, figure, clbk);
        surface.drawLine(right - centerOffset, bottom, right, bottom - centerOffset, color, figure, clbk);
        surface.drawLine(right, bottom - centerOffset, right, y, color, figure, clbk);

    }

    private static void drawB(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, y, x, bottom, color, figure, clbk);
        surface.drawLine(x + 1, y, right - 1, y, color, figure, clbk);
        surface.drawLine(right, y + 1, right, bottom - 4, color, figure, clbk);
        surface.drawLine(right - 1, y + charHeight / 2, x, y + charHeight / 2, color, figure, clbk);
        surface.drawLine(right, y + 4, right, bottom - 1, color, figure, clbk);
        surface.drawLine(right - 1, bottom, x + 1, bottom, color, figure, clbk);
    }

    private static void drawC(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.putDot(bottom + 1, y + 1, color, figure, clbk);
        surface.drawLine(right - 1, y, x + 1, y, color, figure, clbk);
        surface.drawLine(x, y + 1, x, bottom - 1, color, figure, clbk);
        surface.drawLine(x + 1, bottom, right - 1, bottom, color, figure, clbk);
        surface.putDot(bottom + 1, bottom - 1, color, figure, clbk);
    }

    private static void drawCh(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.putDot(bottom + 1, y + 1, color, figure, clbk);
        surface.drawLine(right - 1, y, x + 1, y, color, figure, clbk);
        surface.drawLine(x, y + 1, x, bottom - 1, color, figure, clbk);
        surface.drawLine(x + 1, bottom, right - 1, bottom, color, figure, clbk);
        surface.putDot(bottom + 1, bottom - 1, color, figure, clbk);
        surface.putDot(bottom - 1, bottom - 1, color, figure, clbk);
        surface.putDot(bottom - 1, bottom + 1, color, figure, clbk);
    }

    private static void drawD(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, y, x, bottom, color, figure, clbk);
        surface.drawLine(x + 1, y, right - 1, y, color, figure, clbk);
        surface.drawLine(right, y + 1, right, bottom - 1, color, figure, clbk);
        surface.drawLine(right - 1, bottom, x + 1, bottom, color, figure, clbk);
    }

    private static void drawEE(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, y + 1, x + 1, y, color, figure, clbk);
        surface.drawLine(x + 2, y, right - 1, y, color, figure, clbk);
        surface.drawLine(right, y + 1, right, bottom - 1, color, figure, clbk);
        surface.drawLine(right - 1, bottom, x + 1, bottom, color, figure, clbk);
        surface.drawLine(x, bottom - 1, x, bottom - 2, color, figure, clbk);
        surface.drawLine(x, bottom - 3, right - 1, bottom - 3, color, figure, clbk);
    }

    private static void drawG(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(right, y, x + 1, y, color, figure, clbk);
        surface.drawLine(x, y + 1, x, bottom - 2, color, figure, clbk);
        surface.putDot(x + 1, bottom - 1, color, figure, clbk);
        surface.drawLine(x + 2, bottom, right, bottom, color, figure, clbk);
        surface.drawLine(right, bottom - 1, right, y + 4, color, figure, clbk);
        surface.drawLine(x + centerOffset + 3, y + centerOffset + 1, x + 3, y + centerOffset + 1, color, figure, clbk);
    }

    private static void drawGH(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(right, y, x + 1, y, color, figure, clbk);
        surface.drawLine(x, y + 1, x, bottom - 2, color, figure, clbk);
        surface.putDot(x + 1, bottom - 1, color, figure, clbk);
        surface.drawLine(x + 2, bottom, right, bottom, color, figure, clbk);
        surface.drawLine(right, bottom - 1, right, y + 4, color, figure, clbk);
        surface.drawLine(x + centerOffset + 3, y + centerOffset + 1, x + 3, y + centerOffset + 1, color, figure, clbk);

        surface.putDot(8, 2, color, figure, clbk);
        surface.putDot(9, 3, color, figure, clbk);
        surface.putDot(10, 3, color, figure, clbk);
        surface.putDot(11, 2, color, figure, clbk);
    }

    private static void drawH(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {

        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, y, x, bottom, color, figure, clbk);
        surface.drawLine(x, y + charHeight / 2, right, y + charHeight / 2, color, figure, clbk);
        surface.drawLine(right, y, right, bottom, color, figure, clbk);
    }

    private static void drawJ(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(right, y, right, bottom - 1, color, figure, clbk);
        surface.drawLine(right - 1, bottom, x + 1, bottom, color, figure, clbk);
        surface.drawLine(x, bottom - 1, x, y + 4, color, figure, clbk);
    }

    private static void drawQ(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int leftCorner = x + centerOffset - 1;
        int rightCorner = x + centerOffset + 1;
        int topCorner = y + centerOffset - 3;
        int bottomCorner = y + centerOffset + 4;

        surface.drawLine(leftCorner + 1, topCorner, rightCorner - 1, topCorner, color, figure, clbk);
        surface.drawLine(leftCorner, topCorner, leftCorner - 2, topCorner + 2, color, figure, clbk);
        surface.drawLine(leftCorner - 2, bottomCorner - 4, leftCorner - 2, bottomCorner - 3, color, figure, clbk);
        surface.drawLine(leftCorner - 2, bottomCorner - 2, leftCorner, bottomCorner, color, figure, clbk);
        surface.drawLine(leftCorner + 1, bottomCorner, rightCorner - 1, bottomCorner, color, figure, clbk);
        surface.drawLine(rightCorner, bottomCorner, rightCorner + 2, bottomCorner - 2, color, figure, clbk);
        surface.drawLine(rightCorner + 2, bottomCorner - 3, rightCorner + 2, bottomCorner - 4, color, figure, clbk);
        surface.drawLine(rightCorner + 2, topCorner + 2, rightCorner, topCorner, color, figure, clbk);

        surface.drawLine(rightCorner, bottomCorner - 2, rightCorner + 3, bottomCorner + 1, color, figure, clbk);

    }

    private static void drawO(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int leftCorner = x + centerOffset - 1;
        int rightCorner = x + centerOffset + 1;
        int topCorner = y + centerOffset - 3;
        int bottomCorner = y + centerOffset + 4;

        surface.drawLine(rightCorner - 1, topCorner, leftCorner + 1, topCorner, color, figure, clbk);
        surface.drawLine(leftCorner, topCorner, leftCorner - 2, topCorner + 2, color, figure, clbk);
        surface.drawLine(leftCorner - 2, bottomCorner - 4, leftCorner - 2, bottomCorner - 3, color, figure, clbk);
        surface.drawLine(leftCorner - 2, bottomCorner - 2, leftCorner, bottomCorner, color, figure, clbk);
        surface.drawLine(leftCorner + 1, bottomCorner, rightCorner - 1, bottomCorner, color, figure, clbk);
        surface.drawLine(rightCorner, bottomCorner, rightCorner + 2, bottomCorner - 2, color, figure, clbk);
        surface.drawLine(rightCorner + 2, bottomCorner - 3, rightCorner + 2, bottomCorner - 4, color, figure, clbk);
        surface.drawLine(rightCorner + 2, topCorner + 2, rightCorner, topCorner, color, figure, clbk);
    }

    private static void drawOO(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int leftCorner = x + centerOffset - 1;
        int rightCorner = x + centerOffset + 1;
        int topCorner = y + centerOffset - 3;
        int bottomCorner = y + centerOffset + 4;

        surface.drawLine(rightCorner - 1, topCorner, leftCorner + 1, topCorner, color, figure, clbk);
        surface.drawLine(leftCorner, topCorner, leftCorner - 2, topCorner + 2, color, figure, clbk);
        surface.drawLine(leftCorner - 2, bottomCorner - 4, leftCorner - 2, bottomCorner - 3, color, figure, clbk);
        surface.drawLine(leftCorner - 2, bottomCorner - 2, leftCorner, bottomCorner, color, figure, clbk);
        surface.drawLine(leftCorner + 1, bottomCorner, rightCorner - 1, bottomCorner, color, figure, clbk);
        surface.drawLine(rightCorner, bottomCorner, rightCorner + 2, bottomCorner - 2, color, figure, clbk);
        surface.drawLine(rightCorner + 2, bottomCorner - 3, rightCorner + 2, bottomCorner - 4, color, figure, clbk);
        surface.drawLine(rightCorner + 2, topCorner + 2, rightCorner, topCorner, color, figure, clbk);

        surface.putDot(x + centerOffset - 1, y - 2, color, figure, clbk);
        surface.putDot(x + centerOffset + 1, y - 2, color, figure, clbk);
    }

    private static void drawP(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, y, x, bottom, color, figure, clbk);
        surface.drawLine(x + 1, y, right - 1, y, color, figure, clbk);
        surface.drawLine(right, y + 1, right, bottom - 4, color, figure, clbk);
        surface.drawLine(right - 1, y + charHeight / 2, x, y + charHeight / 2, color, figure, clbk);
    }

    private static void drawR(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, y, x, bottom, color, figure, clbk);
        surface.drawLine(x + 1, y, right - 1, y, color, figure, clbk);
        surface.drawLine(right, y + 1, right, bottom - 4, color, figure, clbk);
        surface.drawLine(right - 1, y + charHeight / 2, x, y + charHeight / 2, color, figure, clbk);
        surface.drawLine(x, y + 2, right - 1, bottom, color, figure, clbk);
        surface.drawLine(right, y + 6, right, bottom, color, figure, clbk);
    }

    private static void drawS(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(right, y, x + 1, y, color, figure, clbk);
        surface.drawLine(x, y + 1, x, y + 2, color, figure, clbk);
        surface.drawLine(x + 1, y + 3, right - 1, y + 3, color, figure, clbk);
        surface.drawLine(right, y + 4, right, bottom - 1, color, figure, clbk);
        surface.drawLine(right - 1, bottom, x, bottom, color, figure, clbk);
    }

    private static void drawSH(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(right, y, x + 1, y, color, figure, clbk);
        surface.drawLine(x, y + 1, x, y + 2, color, figure, clbk);
        surface.drawLine(x + 1, y + 3, right - 1, y + 3, color, figure, clbk);
        surface.drawLine(right, y + 4, right, bottom - 1, color, figure, clbk);
        surface.drawLine(right - 1, bottom, x, bottom, color, figure, clbk);
        surface.drawLine(right - 2, bottom - 1, right - 2, bottom + 1, color, figure, clbk);
    }

    private static void drawU(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, y, x, bottom - 1, color, figure, clbk);
        surface.drawLine(x + 1, bottom, right - 1, bottom, color, figure, clbk);
        surface.drawLine(right, bottom - 1, right, y, color, figure, clbk);
    }

    private static void drawUU(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, y, x, bottom - 1, color, figure, clbk);
        surface.drawLine(x + 1, bottom, right - 1, bottom, color, figure, clbk);
        surface.drawLine(right, bottom - 1, right, y, color, figure, clbk);
        surface.putDot(8, centerOffset, color, figure, clbk);
        surface.putDot(10, centerOffset, color, figure, clbk);
    }

    private static void drawZ(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, y, right, y, color, figure, clbk);
        surface.drawLine(right, y, x, bottom, color, figure, clbk);
        surface.drawLine(x, bottom, right, bottom, color, figure, clbk);
    }

    private static void draw0(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, y + 1, x, bottom - 1, color, figure, clbk);
        surface.drawLine(x + 1, bottom, right - 1, bottom, color, figure, clbk);
        surface.drawLine(right, bottom - 1, right, y + 1, color, figure, clbk);
        surface.drawLine(right - 1, y, x + 1, y, color, figure, clbk);
    }

    private static void draw1(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        surface.putDot(x + centerOffset - 1, y + 1, color, figure, clbk);
        surface.drawLine(x + centerOffset, y, x + centerOffset, bottom, color, figure, clbk);
        surface.drawLine(x + centerOffset - 1, bottom, x + centerOffset + 1, bottom, color, figure, clbk);
    }

    private static void draw2(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, y + 1, x + 1, y, color, figure, clbk);
        surface.drawLine(x + 2, y, right - 1, y, color, figure, clbk);
        surface.drawLine(right, y + 1, right, y + 2, color, figure, clbk);
        surface.drawLine(right - 1, y + 3, x, bottom, color, figure, clbk);
        surface.drawLine(x + 1, bottom, right, bottom, color, figure, clbk);
    }

    private static void draw3(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, y + 1, x + 1, y, color, figure, clbk);
        surface.drawLine(x + 2, y, right - 1, y, color, figure, clbk);
        surface.drawLine(right, y + 1, right, y + 2, color, figure, clbk);
        surface.drawLine(right - 1, y + 3, right - 2, y + 3, color, figure, clbk);
        surface.drawLine(right, y + 4, right, bottom - 1, color, figure, clbk);
        surface.drawLine(right - 1, bottom, x + 2, bottom, color, figure, clbk);
        surface.drawLine(x + 1, bottom, x, bottom - 1, color, figure, clbk);
    }

    private static void draw4(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, y, x, y + 3, color, figure, clbk);
        surface.drawLine(x + 1, y + 3, right, y + 3, color, figure, clbk);
        surface.drawLine(right - 1, y, right - 1, bottom, color, figure, clbk);
    }

    private static void draw5(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, y, x, y + 2, color, figure, clbk);
        surface.drawLine(x + 1, y + 2, right - 1, y + 2, color, figure, clbk);
        surface.drawLine(right, y + 3, right, bottom - 1, color, figure, clbk);
        surface.drawLine(right - 1, bottom, x + 2, bottom, color, figure, clbk);
        surface.drawLine(x + 1, bottom, x, bottom - 1, color, figure, clbk);
        surface.drawLine(x, y, right, y, color, figure, clbk);
    }

    private static void draw6(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(right - 1, y, x + 1, y, color, figure, clbk);
        surface.drawLine(x, y + 1, x, bottom - 1, color, figure, clbk);
        surface.drawLine(x + 1, bottom, right - 1, bottom, color, figure, clbk);
        surface.drawLine(right, bottom - 1, right, bottom - 2, color, figure);
        surface.drawLine(right - 1, y + 3, x + 1, y + 3, color, figure, clbk);
    }

    private static void draw7(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x + 1, y, right - 1, y, color, figure, clbk);
        surface.drawLine(right, y, right, y + 1, color, figure, clbk);
        surface.drawLine(right, y + 2, right - 2, y + 4, color, figure, clbk);
        surface.drawLine(right - 2, y + 5, right - 2, bottom, color, figure, clbk);
    }

    private static void draw8(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x + 1, y, right - 1, y, color, figure, clbk);
        surface.drawLine(right, y + 1, right, y + 2, color, figure, clbk);
        surface.drawLine(right - 1, y + 3, x + 1, y + 3, color, figure, clbk);
        surface.drawLine(x, bottom - 2, x, bottom - 1, color, figure, clbk);
        surface.drawLine(x + 1, bottom, right - 1, bottom, color, figure, clbk);
        surface.drawLine(right, bottom - 1, right, y + 4, color, figure, clbk);
        surface.drawLine(x, y + 2, x, y + 1, color, figure, clbk);
    }

    private static void draw9(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(right - 1, y, x + 1, y, color, figure, clbk);
        surface.drawLine(x, y + 1, x, y + 2, color, figure, clbk);
        surface.drawLine(x + 1, y + 3, right - 1, y + 3, color, figure, clbk);
        surface.drawLine(right, y + 1, right, bottom - 1, color, figure, clbk);
        surface.drawLine(right - 1, bottom, x + 1, bottom, color, figure, clbk);
    }

    private static void drawSquare(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight-1  ;
        int right = x + charWidth -1 ;

        surface.drawLine(x, y, x, bottom, color, figure, clbk);
        surface.drawLine(x, bottom, right, bottom, color, figure, clbk);
        surface.drawLine(right, bottom, right, y, color, figure, clbk);
        surface.drawLine(right, y, x, y, color, figure, clbk);
    }

    private static void drawTriangle(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        int maxL = charHeight > charWidth ? charHeight : charWidth;
        int centerL = maxL / 2;
        surface.drawLine(x, bottom, x + centerL, bottom - centerL, color, figure, clbk);
        surface.drawLine(right - centerL, bottom - centerL, right, bottom, color, figure, clbk);
        surface.drawLine(right - 1, bottom, x + 1, bottom, color, figure, clbk);
    }

    private static void drawRectangle(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 3;
        int right = x + charWidth - 1;

        surface.drawLine(x, y, x, bottom, color, figure, clbk);
        surface.drawLine(x, bottom, right, bottom, color, figure, clbk);
        surface.drawLine(right, bottom, right, y, color, figure, clbk);
        surface.drawLine(right, y, x, y, color, figure, clbk);
    }

    private static void drawTrapes(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        int minoff = charWidth - charHeight;
        minoff = minoff > 0 ? minoff : minoff;
        surface.drawLine(x, y, right - minoff - 1, y, color, figure, clbk);
        surface.drawLine(right - minoff, y + 1, right, bottom, color, figure, clbk);
        surface.drawLine(right - 1, bottom, x, bottom, color, figure, clbk);
        surface.drawLine(x, bottom - 1, x, y + 1, color, figure, clbk);
    }

    public static void drawHeart(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {

        int minOff = charWidth / 4;
        int centerL = charWidth / 2;
        int bottom = y + charHeight - 2;
        int right = x + charWidth - 1;


        surface.drawLine(x, bottom - centerL - minOff, x + minOff, bottom - centerL - minOff - minOff, color, figure, clbk);
        surface.drawLine(x + minOff + 1, bottom - centerL - minOff - minOff, x + centerL, bottom - centerL - minOff - 1, color, figure, clbk);
        surface.drawLine(x + centerL, bottom - centerL - minOff, x + centerL, bottom - centerL - 1, color, figure, clbk);
        surface.drawLine(x + centerL, bottom - centerL, x, bottom, color, figure, clbk);
        //left part
        surface.drawLine(x, bottom - centerL - minOff, x - minOff, bottom - centerL - minOff - minOff, color, figure, clbk);
        surface.drawLine(x - minOff - 1, bottom - centerL - minOff - minOff, x - centerL, bottom - centerL - minOff - 1, color, figure, clbk);
        surface.drawLine(x - centerL, bottom - centerL - minOff, x - centerL, bottom - centerL - 1, color, figure, clbk);
        surface.drawLine(x - centerL, bottom - centerL, x, bottom, color, figure, clbk);
    }


    public static void drawCircle(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {

        int centerL = charWidth / 2;

        surface.drawLine(x + centerL, y, x + centerL + 2, y, color, figure, clbk);
        surface.drawLine(x + centerL + 3, y + 1, x + centerL + 4, y + 1, color, figure, clbk);
        surface.drawLine(x + centerL + 5, y + 2, x + centerL + 5, y + 3, color, figure, clbk);

        surface.drawLine(x + centerL + 6, y + 4, x + centerL + 6, y + 7, color, figure, clbk);

        surface.drawLine(x + centerL + 5, y + 8, x + centerL + 5, y + 9, color, figure, clbk);
        surface.drawLine(x + centerL + 4, y + 10, x + centerL + 3, y + 10, color, figure, clbk);

        surface.drawLine(x + centerL + 2, y + 11, x + centerL - 2, y + 11, color, figure, clbk);

        surface.drawLine(x + centerL - 3, y + 10, x + centerL - 4, y + 10, color, figure, clbk);
        surface.drawLine(x + centerL - 5, y + 9, x + centerL - 5, y + 8, color, figure, clbk);
        surface.drawLine(x + centerL - 6, y + 7, x + centerL - 6, y + 4, color, figure, clbk);

        surface.drawLine(x + centerL - 5, y + 3, x + centerL - 5, y + 2, color, figure, clbk);
        surface.drawLine(x + centerL - 4, y + 1, x + centerL - 3, y + 1, color, figure, clbk);

        surface.drawLine(x + centerL - 2, y, x + centerL - 1, y, color, figure, clbk);
    }

    public static void drawEllipse(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {

        int centerL = charWidth / 2;
        int minx = (charWidth - charHeight) / 2;
        minx = minx > 0 ? minx : -minx;
        surface.drawLine(x + centerL, y, x + centerL + 2 + minx, y, color, figure, clbk);
        surface.drawLine(x + centerL + 3 + minx, y + 1, x + centerL + 4 + minx, y + 1, color, figure, clbk);
        surface.drawLine(x + centerL + 5 + minx, y + 2, x + centerL + 5 + minx, y + 3, color, figure, clbk);

        surface.drawLine(x + centerL + 6 + minx, y + 4, x + centerL + 6 + minx, y + 7, color, figure, clbk);

        surface.drawLine(x + centerL + 5 + minx, y + 8, x + centerL + 5 + minx, y + 9, color, figure, clbk);
        surface.drawLine(x + centerL + 4 + minx, y + 10, x + centerL + 3 + minx, y + 10, color, figure, clbk);

        surface.drawLine(x + centerL + 2 + minx, y + 11, x + centerL - 2 - minx, y + 11, color, figure, clbk);

        surface.drawLine(x + centerL - 3 - minx, y + 10, x + centerL - 4 - minx, y + 10, color, figure, clbk);
        surface.drawLine(x + centerL - 5 - minx, y + 9, x + centerL - 5 - minx, y + 8, color, figure, clbk);
        surface.drawLine(x + centerL - 6 - minx, y + 7, x + centerL - 6 - minx, y + 4, color, figure, clbk);

        surface.drawLine(x + centerL - 5 - minx, y + 3, x + centerL - 5 - minx, y + 2, color, figure, clbk);
        surface.drawLine(x + centerL - 4 - minx, y + 1, x + centerL - 3 - minx, y + 1, color, figure, clbk);

        surface.drawLine(x + centerL - 2 - minx, y, x + centerL - 1, y, color, figure, clbk);

    }


    public static void drawStar(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {

        int centerL = charWidth / 2;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, bottom - centerL - centerL, x, bottom - centerL, color, figure, clbk);
        surface.drawLine(x, bottom - centerL, x, bottom, color, figure, clbk);
        surface.drawLine(x - centerL, bottom - centerL, x, bottom - centerL, color, figure, clbk);
        surface.drawLine(x, bottom - centerL, x + centerL, bottom - centerL, color, figure, clbk);
        surface.drawLine(x, bottom - centerL, x - centerL + 1, bottom - centerL - centerL + 1, color, figure, clbk);
        surface.drawLine(x, bottom - centerL, x + centerL - 1, bottom - centerL - centerL + 1, color, figure, clbk);
        surface.drawLine(x, bottom - centerL, x - centerL + 1, bottom - 1, color, figure, clbk);
        surface.drawLine(x, bottom - centerL, x + centerL - 1, bottom - 1, color, figure, clbk);
    }

    public static void drawPentagon(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        int centerX = charWidth / 2;
        int mint = charWidth;
        mint = mint / 3;

        surface.drawLine(x, y + mint, x + centerX, y + mint - centerX, color, figure, clbk);
        surface.drawLine(right - centerX, y + mint - centerX, right, mint + y, color, figure, clbk);

        surface.drawLine(right - 1, mint + y + 1, right - mint, mint + y + mint, color, figure, clbk);
        surface.drawLine(right - mint - 1, mint + y + mint, x + mint + 1, mint + y + mint, color, figure, clbk);
        surface.drawLine(x + mint, y + mint + mint, x, y + mint, color, figure, clbk);
    }

    public static void drawHexagon(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure, FunnySurface.CallbackDraw clbk) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        int mint = charWidth;
        mint = mint / 3;

        surface.drawLine(x, y + mint, x + mint, y, color, figure, clbk);
        surface.drawLine(x + mint + 1, y, right - mint - 1, y, color, figure, clbk);
        surface.drawLine(right - mint, y, right, mint + y, color, figure, clbk);

        surface.drawLine(right - 1, mint + y + 1, right - mint, mint + y + mint, color, figure, clbk);
        surface.drawLine(right - mint - 1, mint + y + mint, x + mint + 1, mint + y + mint, color, figure, clbk);
        surface.drawLine(x + mint, y + mint + mint, x, y + mint, color, figure, clbk);

    }

    public static void drawCorrect(FunnySurface surface, int x, int y, boolean center) {
        drawCorrect(surface, x, y, center, null);
    }

    public static void drawCorrect(FunnySurface surface, int x, int y, boolean center, FunnySurface.CallbackDraw clbk) {
        int charWidth = 10;
        int charHeight = 7;
        if (center) x = x - charWidth / 2;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        FunnySurface.DotColor color = FunnySurface.DotColor.Green;

        surface.drawLine(x, bottom - 3, x + 3, bottom, color, FunnySurface.DotType.Romb, clbk);
        surface.drawLine(x + 4, bottom - 1, right, y, color, FunnySurface.DotType.Romb, clbk);

    }

    public static void drawFigure(FunnySurface surface, int x, int y, FunnyButton.InnerShapeType innerShapeType, FunnySurface.DotColor color, FunnySurface.DotType figure, boolean center) {
        drawFigure(surface, x, y, innerShapeType, color, figure, center, null);
    }

    public static void drawFigure(FunnySurface surface, int x, int y, FunnyButton.InnerShapeType innerShapeType, FunnySurface.DotColor color, FunnySurface.DotType figure, boolean center, FunnySurface.CallbackDraw clbk) {
        int standardWidth;
        int standardHeight;
        switch (innerShapeType) {
            case Circle:
                standardWidth = 12;
                standardHeight = 12;
                if (center) x = x - standardWidth / 2;
                FunnySurfaceUtils.drawCircle(surface, x, y - 2, standardWidth, standardHeight, color, figure, clbk);
                break;
            case Square:
                standardWidth = 10;
                standardHeight = 10;
                if (center) x = x - standardWidth / 2;
                FunnySurfaceUtils.drawSquare(surface, x, y-1, standardWidth, standardHeight, color, figure, clbk);
                break;
            case Triangle:
                standardWidth = 15;
                standardHeight = 8;
                if (center) x = x - standardWidth / 2;
                FunnySurfaceUtils.drawTriangle(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case Rectangle:
                standardWidth = 14;
                standardHeight = 10;
                if (center) x = x - standardWidth / 2;
                FunnySurfaceUtils.drawRectangle(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case Trapes:
                standardWidth = 16;
                standardHeight = 9;
                if (center) x = x - standardWidth / 2;
                FunnySurfaceUtils.drawTrapes(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case Heart:
                standardWidth = 10;
                standardHeight = 10;
                drawHeart(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case Star:
                standardWidth = 10;
                standardHeight = 10;
                drawStar(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case Pentagon:
                standardWidth = 15;
                standardHeight = 10;
                if (center) x = x - standardWidth / 2;
                drawPentagon(surface, x, y - 1, standardWidth, standardHeight, color, figure, clbk);
                break;
            case Ellipse:
                standardWidth = 16;
                standardHeight = 12;
                if (center) x = x - standardWidth / 2;
                FunnySurfaceUtils.drawEllipse(surface, x, y - 2, standardWidth, standardHeight, color, figure, clbk);

                break;
            case Hexagon:
                standardWidth = 15;
                standardHeight = 10;
                if (center) x = x - standardWidth / 2;
                drawHexagon(surface, x, y - 2, standardWidth, standardHeight, color, figure, clbk);
                break;
            default:
        }
    }


    public static void drawChar(FunnySurface surface, int x, int y, char Letter, FunnySurface.DotColor color, FunnySurface.DotType figure, boolean center) {
        drawChar(surface, x, y, Letter, color, figure, center, null);
    }

    /**
     * Draw [6x8 sized] character on surface
     *
     * @param surface
     * @param x
     * @param y
     * @param Letter
     * @param color
     * @param figure
     */
    public static void drawChar(FunnySurface surface, int x, int y, char Letter, FunnySurface.DotColor color, FunnySurface.DotType figure, boolean center, FunnySurface.CallbackDraw clbk) {
        int standardWidth = 5;
        int standardHeight = 7;
        switch (Letter) {
            case 'A':
            case 'a':
                if (center) x = x - standardWidth / 2 - 1;
                drawA(surface, x, y, standardWidth + 1, standardHeight, color, figure, clbk);
                break;
            case 'B':
            case 'b':
                if (center) x = x - standardWidth / 2 - 1;
                drawB(surface, x, y, standardWidth + 1, standardHeight, color, figure, clbk);
                break;
            case 'C':
            case 'c':
                if (center) x = x - standardWidth / 2 - 1;
                drawC(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case 'Ç':
            case 'ç':
                if (center) x = x - standardWidth / 2 - 1;
                drawCh(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case 'D':
            case 'd':
                if (center) x = x - standardWidth / 2 - 1;
                drawD(surface, x, y, standardWidth + 1, standardHeight, color, figure, clbk);
                break;
            case 'E':
            case 'e':
                if (center) x = x - standardWidth / 2 - 1;
                drawE(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case 'Ə':
            case 'ə':
                if (center) x = x - standardWidth / 2 - 1;
                drawEE(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case 'F':
            case 'f':
                if (center) x = x - standardWidth / 2;
                drawF(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case 'G':
            case 'g':
                if (center) x = x - standardWidth / 2 - 1;
                drawG(surface, x, y, standardWidth + 1, standardHeight, color, figure, clbk);
                break;
            case 'Ğ':
            case 'ğ':
                if (center) x = x - standardWidth / 2 - 1;
                drawGH(surface, x, y, standardWidth + 1, standardHeight, color, figure, clbk);
                break;
            case 'H':
            case 'h':
                if (center) x = x - standardWidth / 2 - 1;
                drawH(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case 'I':
            case 'ı':
                if (center) x = x - standardWidth / 2 - 1;
                drawII(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case 'İ':
            case 'i':
                if (center) x = x - standardWidth / 2 - 1;
                drawI(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case 'J':
            case 'j':
                if (center) x = x - standardWidth / 2;
                drawJ(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case 'K':
            case 'k':
                if (center) x = x - standardHeight / 2;
                drawK(surface, x, y, standardHeight, standardHeight, color, figure, clbk);
                break;
            case 'Q':
            case 'q':
                if (center) x = x - standardHeight / 2 - 1;
                drawQ(surface, x, y, standardHeight, standardHeight, color, figure, clbk);
                break;
            case 'L':
            case 'l':
                standardWidth = 5;
                if (center) x = x - standardWidth / 2 - 1;
                drawL(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case 'M':
            case 'm':
                if (center) x = x - standardHeight / 2;
                drawM(surface, x, y, standardHeight, standardHeight, color, figure, clbk);
                break;
            case 'N':
            case 'n':
                if (center) x = x - standardHeight / 2;
                drawN(surface, x, y, standardHeight, standardHeight, color, figure, clbk);
                break;
            case 'O':
            case 'o':
                if (center) x = x - standardHeight / 2 - 1;
                drawO(surface, x, y, standardWidth + 2, standardHeight, color, figure, clbk);
                break;
            case 'Ö':
            case 'ö':
                if (center) x = x - standardHeight / 2 - 1;
                drawOO(surface, x, y, standardWidth + 2, standardHeight, color, figure, clbk);
                break;
            case 'P':
            case 'p':
                if (center) x = x - standardWidth / 2 - 1;
                drawP(surface, x, y, standardWidth + 1, standardHeight, color, figure, clbk);
                break;
            case 'R':
            case 'r':
                if (center) x = x - standardWidth / 2 - 1;
                drawR(surface, x, y, standardWidth + 1, standardHeight, color, figure, clbk);
                break;
            case 'S':
            case 's':
                if (center) x = x - standardWidth / 2 - 1;
                drawS(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case 'Ş':
            case 'ş':
                if (center) x = x - standardWidth / 2 - 1;
                drawSH(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case 'T':
            case 't':
                if (center) x = x - standardWidth / 2 - 1;
                drawT(surface, x, y, standardWidth + 2, standardHeight, color, figure, clbk);
                break;
            case 'U':
            case 'u':
                if (center) x = x - standardWidth / 2 - 1;
                drawU(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case 'Ü':
            case 'ü':
                if (center) x = x - standardWidth / 2 - 1;
                drawUU(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case 'V':
            case 'v':
                if (center) x = x - standardHeight / 2;
                drawV(surface, x, y, standardHeight, standardHeight, color, figure, clbk);
                break;
            case 'Y':
            case 'y':
                if (center) x = x - standardHeight / 2;
                drawY(surface, x, y, standardHeight, standardHeight, color, figure, clbk);
                break;
            case 'X':
            case 'x':
                if (center) x = x - standardHeight / 2;
                drawX(surface, x, y, standardHeight, standardHeight, color, figure, clbk);
                break;
            case 'Z':
            case 'z':
                if (center) x = x - standardHeight / 2;
                drawZ(surface, x, y, standardHeight, standardHeight, color, figure, clbk);
                break;
            case '0':
                if (center) x = x - standardWidth / 2 - 1;
                draw0(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case '1':
                if (center) x = x - standardWidth / 2 - 1;
                draw1(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case '2':
                if (center) x = x - standardWidth / 2;
                draw2(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case '3':
                if (center) x = x - standardWidth / 2 - 1;
                draw3(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case '4':
                if (center) x = x - standardWidth / 2 - 1;
                draw4(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case '5':
                if (center) x = x - standardWidth / 2 - 1;
                draw5(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case '6':
                if (center) x = x - standardWidth / 2 - 1;
                draw6(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case '7':
                if (center) x = x - standardHeight / 2 - 1;
                draw7(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case '8':
                if (center) x = x - standardWidth / 2 - 1;
                draw8(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            case '9':
                if (center) x = x - standardWidth / 2 - 1;
                draw9(surface, x, y, standardWidth, standardHeight, color, figure, clbk);
                break;
            default:
        }
    }
}
