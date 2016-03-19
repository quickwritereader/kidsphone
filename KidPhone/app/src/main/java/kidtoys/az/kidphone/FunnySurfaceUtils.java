package kidtoys.az.kidphone;

/**
 * here we should implement surface drawing primitivies like line and so on
 * Implementation by Abdurrauf and Ramil
 */
public class FunnySurfaceUtils {


    private static int standardHeight=8;
    private static int standardWidth=5;

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
        if(charWidth>charHeight) charWidth=charHeight-1;
        int centerOffset= (int)Math.ceil(charWidth/2.0)-1 ;
        int bottom=y+charHeight-1;
        int right=x+charWidth-1;

        surface.drawLine(x,bottom,x,y+centerOffset,color,figure);
        surface.drawLine(x, y+centerOffset, x+centerOffset, y , color, figure);
        surface.drawLine(right-centerOffset, y , right, y+centerOffset , color, figure);
        surface.drawLine( right, y+centerOffset ,right,bottom,color,figure);
        surface.drawLine( x, y+charHeight/2 ,right,y+charHeight/2,color,figure);

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

    }

    private static void drawK(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawL(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

        int bottom=y+charHeight-1;
        surface.drawLine(x, y, x, bottom, color, figure);
        surface.drawLine(x, bottom, x+charWidth-1, bottom, color, figure);
    }

    private static void drawH(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawY(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawN(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawM(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawF(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawX(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawI(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawI2(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawT(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawV(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawB(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawC(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawCh(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawD(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawEE(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawG(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawGH(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawII(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawJ(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawQ(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawO(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawOO(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawP(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawR(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawS(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawSH(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawU(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawUU(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

    }

    private static void drawZ(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

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
    public static void drawLetter(FunnySurface surface, int x, int y, char Letter, FunnySurface.DotColor color, FunnySurface.DotType figure) {

        switch (Letter) {
            case 'A':
            case 'a':
                drawA(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'B':
            case 'b':
                drawB(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'C':
            case 'c':
                drawC(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'Ç':
            case 'ç':
                drawCh(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'D':
            case 'd':
                drawD(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'E':
            case 'e':
                drawE(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'Ə':
            case 'ə':
                drawEE(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'F':
            case 'f':
                drawF(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'G':
            case 'g':
                drawG(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'Ğ':
            case 'ğ':
                drawGH(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'H':
            case 'h':
                drawH(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'I':
            case 'ı':
                drawI(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'İ':
            case 'i':
                drawII(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'J':
            case 'j':
                drawJ(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'K':
            case 'k':
                drawK(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'Q':
            case 'q':
                drawQ(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'L':
            case 'l':
                drawL(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'M':
            case 'm':
                drawM(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'N':
            case 'n':
                drawN(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'O':
            case 'o':
                drawO(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'Ö':
            case 'ö':
                drawOO(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'P':
            case 'p':
                drawP(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'R':
            case 'r':
                drawR(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'S':
            case 's':
                drawS(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'Ş':
            case 'ş':
                drawSH(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'T':
            case 't':
                drawT(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'U':
            case 'u':
                drawU(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'Ü':
            case 'ü':
                drawUU(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'V':
            case 'v':
                drawV(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'Y':
            case 'y':
                drawY(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'X':
            case 'x':
                drawX(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            case 'Z':
            case 'z':
                drawZ(surface, x, y, standardWidth,standardHeight, color, figure);
                break;
            default:
        }
    }


}
