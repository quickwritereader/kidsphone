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
    private static void drawA(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        if (charWidth > charHeight) charWidth = charHeight - 1;
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, bottom, x, y + centerOffset, color, figure);
        surface.drawLine(x, y + centerOffset, x + centerOffset, y, color, figure);
        surface.drawLine(right - centerOffset, y, right, y + centerOffset, color, figure);
        surface.drawLine(right, y + centerOffset, right, bottom, color, figure);
        surface.drawLine(x, y + charHeight / 2 + 1, right, y + charHeight / 2 + 1, color, figure);

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
    private static void drawE(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(right, y, x, y, color, figure);
        surface.drawLine(x, y, x, bottom, color, figure);
        surface.drawLine(x, bottom, right, bottom, color, figure);
        surface.drawLine(x, y + centerOffset + 1, x + centerOffset+1, y + centerOffset + 1, color, figure);

    }

    private static void drawK(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0);
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, y, x, bottom, color, figure);
        surface.drawLine(x, y + centerOffset, x + centerOffset, y, color, figure);
        surface.drawLine(x, bottom - centerOffset, x + centerOffset, bottom, color, figure);
    }

    private static void drawL(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

        int bottom = y + charHeight - 1;
        surface.drawLine(x, y, x, bottom, color, figure);
        surface.drawLine(x, bottom, x + charWidth - 1, bottom, color, figure);
    }

    private static void drawH(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, bottom, x, y, color, figure);
        surface.drawLine(x, y + charHeight / 2, right, y + charHeight / 2, color, figure);
        surface.drawLine(right, bottom, right, y, color, figure);
    }

    private static void drawY(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, y, x + centerOffset, y + centerOffset, color, figure);
        surface.drawLine(right - centerOffset, y + centerOffset, right, y, color, figure);
        surface.drawLine(right - centerOffset, y + centerOffset, x + centerOffset, bottom, color, figure);
    }

    private static void drawN(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, bottom, x, y, color, figure);
        surface.drawLine(x, y, right, bottom, color, figure);
        surface.drawLine(right, bottom, right, y, color, figure);
    }

    private static void drawM(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, bottom, x, y, color, figure);
        surface.drawLine(x, y, x + centerOffset, y + centerOffset, color, figure);
        surface.drawLine(right - centerOffset, y + centerOffset, right, y, color, figure);
        surface.drawLine(right, y, right, bottom, color, figure);
    }

    private static void drawF(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(right, y, x, y, color, figure);
        surface.drawLine(x, y, x, bottom, color, figure);
        surface.drawLine(x, y + centerOffset + 1, x + centerOffset+1, y + centerOffset + 1, color, figure);
    }

    private static void drawX(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, y, right, bottom, color, figure);
        surface.putDot(right, bottom, color, figure);
        surface.drawLine(right, y, x, bottom, color, figure);
    }

    private static void drawII(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        surface.drawLine(x + centerOffset - 1, y, x + centerOffset + 1, y, color, figure);
        surface.drawLine(x + centerOffset, y, x + centerOffset, bottom, color, figure);
        surface.drawLine(x + centerOffset - 1, bottom, x + centerOffset + 1, bottom, color, figure);
    }

    private static void drawI(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        surface.putDot(x + centerOffset, y, color, figure);
        surface.drawLine(x + centerOffset - 1, y + 2, x + centerOffset + 1, y + 2, color, figure);
        surface.drawLine(x + centerOffset, y + 2, x + centerOffset, bottom, color, figure);
        surface.drawLine(x + centerOffset - 1, bottom, x + centerOffset + 1, bottom, color, figure);
    }

    private static void drawT(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        int centerOf = (int) Math.ceil(charWidth / 2.0) - 1;
        surface.drawLine(x, y, right, y, color, figure);
        surface.drawLine(x + centerOf, y, x + centerOf, bottom, color, figure);
    }

    private static void drawV(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        if (charWidth > charHeight) charWidth = charHeight - 1;
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, y, x, bottom - centerOffset, color, figure);
        surface.drawLine(x, bottom - centerOffset, x + centerOffset, bottom, color, figure);
        surface.drawLine(right - centerOffset, bottom, right, bottom - centerOffset, color, figure);
        surface.drawLine(right, bottom - centerOffset, right, y, color, figure);

    }

    private static void drawB(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, bottom, x, y, color, figure);
        surface.drawLine(x + 1, y, right - 1, y, color, figure);
        surface.drawLine(right, y + 1, right, bottom - 4, color, figure);
        surface.drawLine(x, y + charHeight / 2, right-1, y + charHeight / 2, color, figure);
        surface.drawLine(right, y + 4, right, bottom - 1, color, figure);
        surface.drawLine(right - 1, bottom, x + 1, bottom, color, figure);
    }

    private static void drawC(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.putDot(bottom+1,y+1,color, figure);
        surface.drawLine(x + 1, y, right - 1, y, color, figure);
        surface.drawLine(x, bottom - 1, x, y + 1, color, figure);
        surface.drawLine(right - 1, bottom, x + 1, bottom, color, figure);
        surface.putDot(bottom+1,bottom-1,color, figure);
    }

    private static void drawCh(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.putDot(bottom+1,y+1,color, figure);
        surface.drawLine(x + 1, y, right - 1, y, color, figure);
        surface.drawLine(x, bottom - 1, x, y + 1, color, figure);
        surface.drawLine(right - 1, bottom, x + 1, bottom, color, figure);
        surface.putDot(bottom+1,bottom-1,color, figure);
        surface.putDot(bottom-1,bottom-1,color, figure);
        surface.putDot(bottom-1,bottom+1,color, figure);
    }

    private static void drawD(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, bottom, x, y, color, figure);
        surface.drawLine(x + 1, y, right - 1, y, color, figure);
        surface.drawLine(right, y + 1, right, bottom - 1, color, figure);
        surface.drawLine(right - 1, bottom, x + 1, bottom, color, figure);
    }

    private static void drawEE(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawG(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x+1, y, right, y, color, figure);
        surface.drawLine(x, bottom - 2, x, y + 1, color, figure);
        surface.putDot(x+1,bottom-1,color,figure);
        surface.drawLine(right, bottom, x+2 , bottom, color, figure);
        surface.drawLine(right, y + 4, right, bottom - 1, color, figure);
        surface.drawLine(x+3, y + centerOffset + 1, x + centerOffset+3, y + centerOffset + 1, color, figure);
    }

    private static void drawGH(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x+1, y, right, y, color, figure);
        surface.drawLine(x, bottom - 2, x, y + 1, color, figure);
        surface.putDot(x+1,bottom-1,color,figure);
        surface.drawLine(right, bottom, x+2 , bottom, color, figure);
        surface.drawLine(right, y + 4, right, bottom - 1, color, figure);
        surface.drawLine(x+3, y + centerOffset + 1, x + centerOffset+3, y + centerOffset + 1, color, figure);

        surface.putDot(8,2,color,figure);
        surface.putDot(9,3,color,figure);
        surface.putDot(10,3,color,figure);
        surface.putDot(11,2,color,figure);
    }


    private static void drawJ(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x , y + 4, x, bottom-1, color, figure);
        surface.drawLine(right - 1, bottom, x + 1, bottom, color, figure);
        surface.drawLine(right, bottom-1, right, y, color, figure);
    }

    private static void drawQ(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, bottom - 2, x, y + 2, color, figure);
        surface.drawLine(x, y + centerOffset-1, x + centerOffset-1, y, color, figure);
        surface.drawLine(x + 2, y, right - 2, y, color, figure);
        surface.drawLine(right, y + centerOffset, right-1, bottom-1, color, figure);
        surface.drawLine(right, y + 2, right, bottom - 2, color, figure);
        surface.drawLine(right - centerOffset, y, right-1, y + centerOffset-2, color, figure);
        surface.drawLine(right - 2, bottom, x + 2, bottom, color, figure);
        surface.drawLine(x, bottom - centerOffset, x + centerOffset-2, bottom-1, color, figure);

        surface.putDot(12, 10, color, figure);
        surface.putDot(10, 8, color, figure);
    }

    private static void drawO(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, bottom - 2, x, y + 2, color, figure);
        surface.drawLine(x, y + centerOffset-1, x + centerOffset-1, y, color, figure);
        surface.drawLine(x + 2, y, right - 2, y, color, figure);
        surface.drawLine(right, y + centerOffset, right-1, bottom-1, color, figure);
        surface.drawLine(right, y + 2, right, bottom - 2, color, figure);
        surface.drawLine(right - centerOffset, y, right-1, y + centerOffset-2, color, figure);
        surface.drawLine(right - 2, bottom, x + 2, bottom, color, figure);
        surface.drawLine(x, bottom - centerOffset, x + centerOffset-2, bottom-1, color, figure);
    }

    private static void drawOO(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, bottom - 2, x, y + 2, color, figure);
        surface.drawLine(x, y + centerOffset-1, x + centerOffset-1, y, color, figure);
        surface.drawLine(x + 2, y, right - 2, y, color, figure);
        surface.drawLine(right, y + centerOffset, right-1, bottom-1, color, figure);
        surface.drawLine(right, y + 2, right, bottom - 2, color, figure);
        surface.drawLine(right - centerOffset, y, right-1, y + centerOffset-2, color, figure);
        surface.drawLine(right - 2, bottom, x + 2, bottom, color, figure);
        surface.drawLine(x, bottom - centerOffset, x + centerOffset-2, bottom-1, color, figure);
        surface.putDot(bottom-1, centerOffset, color,figure);
        surface.putDot(bottom+1, centerOffset, color,figure);
    }

    private static void drawP(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, bottom, x, y, color, figure);
        surface.drawLine(x + 1, y, right - 1, y, color, figure);
        surface.drawLine(right, y + 1, right, bottom - 4, color, figure);
        surface.drawLine(x, y + charHeight / 2, right-1, y + charHeight / 2, color, figure);
    }

    private static void drawR(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, bottom, x, y, color, figure);
        surface.drawLine(x + 1, y, right - 1, y, color, figure);
        surface.drawLine(right, y + 1, right, bottom - 4, color, figure);
        surface.drawLine(x, y + charHeight / 2, right-1, y + charHeight / 2, color, figure);
        surface.drawLine(x, y+2, right-1, bottom, color, figure);
        surface.drawLine(right, y + 6, right, bottom, color, figure);
    }

    private static void drawS(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawSH(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawU(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, bottom-1, x, y, color, figure);
        surface.drawLine(right - 1, bottom, x + 1, bottom, color, figure);
        surface.drawLine(right, bottom-1, right, y, color, figure);
    }

    private static void drawUU(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, bottom-1, x, y, color, figure);
        surface.drawLine(right - 1, bottom, x + 1, bottom, color, figure);
        surface.drawLine(right, bottom-1, right, y, color, figure);
        surface.putDot(8, centerOffset, color,figure);
        surface.putDot(10, centerOffset, color,figure);
    }

    private static void drawZ(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x, y, right, y, color, figure);
        surface.drawLine(right, y, x, bottom, color, figure);
        surface.drawLine(x, bottom, right, bottom, color, figure);
    }

    private static void draw0(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, bottom - 1, x, y + 1, color, figure);
        surface.drawLine(x + 1, y, right - 1, y, color, figure);
        surface.drawLine(right, y + 1, right, bottom - 1, color, figure);
        surface.drawLine(right - 1, bottom, x + 1, bottom, color, figure);
    }

    private static void draw1(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int centerOffset = (int) Math.ceil(charWidth / 2.0) - 1;
        surface.putDot(x+centerOffset - 1, y + 1, color, figure);
        surface.drawLine(x+centerOffset, y, x+centerOffset, bottom, color, figure);
        surface.drawLine(x + centerOffset - 1, bottom, x + centerOffset + 1, bottom, color, figure);
    }

    private static void draw2(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, y+1, x+1,y , color, figure);
        surface.drawLine(x+2, y ,right-1,y , color, figure);
        surface.drawLine(right, y+1 ,right ,y+2 , color, figure);
        surface.drawLine(right-1, y+3 ,x ,bottom , color, figure);
        surface.drawLine( x +1,bottom ,right,bottom, color, figure);
    }

    private static void draw3(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x, y+1, x+1,y , color, figure);
        surface.drawLine(x+2, y ,right-1,y , color, figure);
        surface.drawLine(right, y+1 ,right ,y+2 , color, figure);
        surface.drawLine(right-1, y+3 ,right-2 ,y+3 , color, figure);
        surface.drawLine(right , y+4 ,right ,bottom-1 , color, figure);
        surface.drawLine(right-1, bottom ,x+2,bottom , color, figure);
        surface.drawLine(x+1,bottom, x ,bottom-1 , color, figure);
    }

    private static void draw4(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x,y,x,y+3,color,figure);
        surface.drawLine(x+1,y+3,right,y+3,color,figure);
        surface.drawLine(right-1,y,right-1,bottom,color,figure);
    }

    private static void draw5(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(right ,y,x,y,color,figure);
        surface.drawLine(x,y,x,y+2,color,figure);
        surface.drawLine( x+1,y+2,right-1,y+2,color,figure);
        surface.drawLine(right , y+3 ,right ,bottom-1 , color, figure);
        surface.drawLine(right-1, bottom ,x+2,bottom , color, figure);
        surface.drawLine(x+1,bottom, x ,bottom-1 , color, figure);

    }

    private static void draw6(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(right-1, y ,x+1,y , color, figure);
        surface.drawLine(x , y+1 ,x ,bottom-1 , color, figure);
        surface.drawLine(x+1, bottom ,right-1,bottom , color, figure);
        surface.drawLine(right,bottom-1,right,bottom-2,color,figure);
        surface.drawLine(right-1, y+3 ,x+1 ,y+3 , color, figure);
    }

    private static void draw7(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(x+1, y , right-1,y , color, figure);
        surface.drawLine(right,y ,right,y+1,color,figure);
        surface.drawLine(right ,y+2,right-2,y+4,color,figure);
        surface.drawLine(right-2,y+5,right-2,bottom,color,figure);
    }

    private static void draw8(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;

        surface.drawLine(x,y+2,x,y+1,color,figure);
        surface.drawLine(x+1, y ,right-1,y , color, figure);
        surface.drawLine(right, y+1 ,right ,y+2 , color, figure);
        surface.drawLine(right , y+4 ,right ,bottom-1 , color, figure);
        surface.drawLine(right-1, y+3 ,x+1 ,y+3 , color, figure);
        surface.drawLine(right-1, bottom ,x+1,bottom , color, figure);
        surface.drawLine(x ,bottom-1, x ,bottom-2 , color, figure);
    }

    private static void draw9(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom = y + charHeight - 1;
        int right = x + charWidth - 1;
        surface.drawLine(right-1, y+3 ,x+1 ,y+3 , color, figure);
        surface.drawLine(x,y+2,x,y+1,color,figure);
        surface.drawLine(x+1, y ,right-1,y , color, figure);
        surface.drawLine(right , y+1 ,right ,bottom-1 , color, figure);
        surface.drawLine(right-1, bottom ,x+1,bottom , color, figure);
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
    public static void drawChar(FunnySurface surface, int x, int y, char Letter, FunnySurface.DotColor color, FunnySurface.DotType figure, boolean center) {
        int standardWidth = 5;
        int standardHeight = 7;
        switch (Letter) {
            case 'A':
            case 'a':
                if (center) x = x - standardWidth / 2 - 1;
                drawA(surface, x, y, standardWidth + 1, standardHeight, color, figure);
                break;
            case 'B':
            case 'b':
                if (center) x = x - standardWidth / 2 - 1;
                drawB(surface, x, y, standardWidth+1, standardHeight, color, figure);
                break;
            case 'C':
            case 'c':
                if (center) x = x - standardWidth / 2 - 1;
                drawC(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case 'Ç':
            case 'ç':
                if (center) x = x - standardWidth / 2 - 1;
                drawCh(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case 'D':
            case 'd':
                if (center) x = x - standardWidth / 2 - 1;
                drawD(surface, x, y, standardWidth+1, standardHeight, color, figure);
                break;
            case 'E':
            case 'e':
                if (center) x = x - standardWidth / 2 - 1;
                drawE(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case 'Ə':
            case 'ə':
                drawEE(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case 'F':
            case 'f':
                if (center) x = x - standardWidth / 2;
                drawF(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case 'G':
            case 'g':
                if (center) x = x - standardWidth / 2 - 1;
                drawG(surface, x, y, standardWidth+1, standardHeight, color, figure);
                break;
            case 'Ğ':
            case 'ğ':
                if (center) x = x - standardWidth / 2 - 1;
                drawGH(surface, x, y, standardWidth+1, standardHeight, color, figure);
                break;
            case 'H':
            case 'h':
                if (center) x = x - standardWidth / 2;
                drawH(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case 'I':
            case 'ı':
                if (center) x = x - standardWidth / 2;
                drawII(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case 'İ':
            case 'i':
                if (center) x = x - standardWidth / 2;
                drawI(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case 'J':
            case 'j':
                if (center) x = x - standardWidth / 2;
                drawJ(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case 'K':
            case 'k':
                if (center) x = x - standardHeight / 2;
                drawK(surface, x, y, standardHeight, standardHeight, color, figure);
                break;
            case 'Q':
            case 'q':
                if (center) x = x - standardHeight / 2-1;
                drawQ(surface, x, y, standardHeight, standardHeight, color, figure);
                break;
            case 'L':
            case 'l':
                standardWidth = 5;
                if (center) x = x - standardWidth / 2;
                drawL(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case 'M':
            case 'm':
                if (center) x = x - standardHeight / 2;
                drawM(surface, x, y, standardHeight, standardHeight, color, figure);
                break;
            case 'N':
            case 'n':
                if (center) x = x - standardHeight / 2;
                drawN(surface, x, y, standardHeight, standardHeight, color, figure);
                break;
            case 'O':
            case 'o':
                if (center) x = x - standardHeight / 2;
                drawO(surface, x, y, standardWidth+2, standardHeight, color, figure);
                break;
            case 'Ö':
            case 'ö':
                if (center) x = x - standardHeight / 2;
                drawOO(surface, x, y, standardWidth+2, standardHeight, color, figure);
                break;
            case 'P':
            case 'p':
                if (center) x = x - standardWidth / 2 - 1;
                drawP(surface, x, y, standardWidth+1, standardHeight, color, figure);
                break;
            case 'R':
            case 'r':
                if (center) x = x - standardWidth / 2 - 1;
                drawR(surface, x, y, standardWidth+1, standardHeight, color, figure);
                break;
            case 'S':
            case 's':
                drawS(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case 'Ş':
            case 'ş':
                drawSH(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case 'T':
            case 't':
                if (center) x = x - standardWidth / 2 - 1;
                drawT(surface, x, y, standardWidth+2, standardHeight, color, figure);
                break;
            case 'U':
            case 'u':
                if (center) x = x - standardWidth / 2 - 1;
                drawU(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case 'Ü':
            case 'ü':
                if (center) x = x - standardWidth / 2 - 1;
                drawUU(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case 'V':
            case 'v':
                if (center) x = x - standardHeight / 2;
                drawV(surface, x, y, standardHeight, standardHeight, color, figure);
                break;
            case 'Y':
            case 'y':
                if (center) x = x - standardHeight / 2;
                drawY(surface, x, y, standardHeight, standardHeight, color, figure);
                break;
            case 'X':
            case 'x':
                if (center) x = x - standardHeight / 2;
                drawX(surface, x, y, standardHeight, standardHeight, color, figure);
                break;
            case 'Z':
            case 'z':
                if (center) x = x - standardHeight / 2;
                drawZ(surface, x, y, standardHeight, standardHeight, color, figure);
                break;
            case '0':
                if (center) x = x - standardWidth / 2-1;
                draw0(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case '1':
                if (center) x = x - standardWidth / 2-1;
                draw1(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case '2':
                if (center) x = x - standardWidth / 2;
                draw2(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case '3':
                if (center) x = x - standardWidth / 2-1;
                draw3(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case '4':
                if (center) x = x - standardWidth / 2-1;
                draw4(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case '5':
                if (center) x = x - standardWidth / 2-1;
                draw5(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case '6':
                if (center) x = x - standardWidth / 2-1;
                draw6(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case '7':
                if (center) x = x - standardHeight / 2-1;
                draw7(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case '8':
                if (center) x = x - standardWidth / 2-1;
                draw8(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            case '9':
                if (center) x = x - standardWidth / 2-1;
                draw9(surface, x, y, standardWidth, standardHeight, color, figure);
                break;
            default:
        }
    }
}
