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
        if(charWidth>charHeight) charWidth=charHeight-1;
        int centerOffset= (int)Math.ceil(charWidth/2.0)-1 ;
        int bottom=y+charHeight-1;
        int right=x+charWidth-1;

        surface.drawLine(x,bottom,x,y+centerOffset,color,figure);
        surface.drawLine(x, y+centerOffset, x+centerOffset, y , color, figure);
        surface.drawLine(right-centerOffset, y , right, y+centerOffset , color, figure);
        surface.drawLine( right, y+centerOffset ,right,bottom,color,figure);
        surface.drawLine( x, y+charHeight/2+1 ,right,y+charHeight/2+1,color,figure);

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
        int centerOffset= (int)Math.ceil(charWidth/2.0)-1 ;
        int bottom=y+charHeight-1;
        int right=x+charWidth-1;
        surface.drawLine(right,y,x,y ,color,figure);
        surface.drawLine(x,y,x,bottom ,color,figure);
        surface.drawLine(x, bottom , right, bottom , color, figure);
        surface.drawLine(x, y +centerOffset+1, x+centerOffset, y+centerOffset +1, color, figure);

    }

    private static void drawK(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int centerOffset= (int)Math.ceil(charWidth/2.0) ;
        int bottom=y+charHeight-1;
        int right=x+charWidth-1;
        surface.drawLine(x, y , x , bottom , color, figure);
        surface.drawLine(x, y+centerOffset , x+centerOffset, y  , color, figure);
        surface.drawLine(x, bottom-centerOffset , x+centerOffset,bottom  , color, figure);
    }

    private static void drawL(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

        int bottom=y+charHeight-1;
        surface.drawLine(x, y, x, bottom, color, figure);
        surface.drawLine(x, bottom, x+charWidth-1, bottom, color, figure);
    }

    private static void drawH(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

        int bottom=y+charHeight-1;
        int right=x+charWidth-1;

        surface.drawLine(x,bottom,x,y ,color,figure);
        surface.drawLine(x, y+charHeight/2 , right,y+charHeight/2, color, figure);
        surface.drawLine(right ,bottom , right, y  , color, figure);
    }

    private static void drawY(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

        int centerOffset= (int)Math.ceil(charWidth/2.0)-1 ;
        int bottom=y+charHeight-1;
        int right=x+charWidth-1;
        surface.drawLine(x, y , x+centerOffset, y+centerOffset , color, figure);
        surface.drawLine(right-centerOffset, y+centerOffset , right, y  , color, figure);
        surface.drawLine(right-centerOffset, y+centerOffset , x+centerOffset, bottom, color, figure);
    }

    private static void drawN(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

        int bottom=y+charHeight-1;
        int right=x+charWidth-1;

        surface.drawLine(x,bottom,x,y ,color,figure);
        surface.drawLine(x, y  , right,bottom , color, figure);
        surface.drawLine(right ,bottom , right, y  , color, figure);
    }

    private static void drawM(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int centerOffset= (int)Math.ceil(charWidth/2.0)-1 ;
        int bottom=y+charHeight-1;
        int right=x+charWidth-1;

        surface.drawLine(x,bottom,x,y ,color,figure);
        surface.drawLine(x, y , x+centerOffset, y+centerOffset , color, figure);
        surface.drawLine(right-centerOffset, y+centerOffset , right, y  , color, figure);
        surface.drawLine( right, y  ,right,bottom,color,figure);
    }

    private static void drawF(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int centerOffset= (int)Math.ceil(charWidth/2.0)-1 ;
        int bottom=y+charHeight-1;
        int right=x+charWidth-1;
        surface.drawLine(right,y,x,y ,color,figure);
        surface.drawLine(x, y, x, bottom, color, figure);
        surface.drawLine(x, y +centerOffset+1, x+centerOffset, y+centerOffset +1, color, figure);
    }

    private static void drawX(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

        int bottom=y+charHeight-1;
        int right=x+charWidth-1;
        surface.drawLine(x, y  , right, bottom  , color, figure);
        surface.putDot(right, bottom, color, figure);
        surface.drawLine(right, y  , x, bottom  , color, figure);
    }

    private static void drawII(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {

        int centerOffset= (int)Math.ceil(charWidth/2.0)-1 ;
        int bottom=y+charHeight-1;
        surface.drawLine(x+centerOffset-1, y  , x+centerOffset+1, y , color, figure);
        surface.drawLine(x+centerOffset, y  , x+centerOffset, bottom , color, figure);
        surface.drawLine(x+centerOffset-1, bottom  , x+centerOffset+1, bottom , color, figure);
    }

    private static void drawI(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int centerOffset= (int)Math.ceil(charWidth/2.0)-1 ;
        int bottom=y+charHeight-1;
        surface.putDot(x + centerOffset, y, color, figure);
        surface.drawLine(x+centerOffset-1, y +2 , x+centerOffset+1, y +2, color, figure);
        surface.drawLine(x + centerOffset, y + 2, x + centerOffset, bottom, color, figure);
        surface.drawLine(x+centerOffset-1, bottom  , x+centerOffset+1, bottom , color, figure);
    }

    private static void drawT(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        int bottom=y+charHeight-1;
        int right=x+charWidth-1;
        int centerOf=(int)Math.ceil(charWidth/2.0)-1;
        surface.drawLine(x,y,right,y ,color,figure);
        surface.drawLine(x+centerOf, y  , x+centerOf,bottom , color, figure);
    }

    private static void drawV(FunnySurface surface, int x, int y, int charWidth, int charHeight, FunnySurface.DotColor color, FunnySurface.DotType figure) {
        if(charWidth>charHeight) charWidth=charHeight-1;
        int centerOffset= (int)Math.ceil(charWidth/2.0)-1 ;
        int bottom=y+charHeight-1;
        int right=x+charWidth-1;

        surface.drawLine(x,y,x,bottom-centerOffset,color,figure);
        surface.drawLine(x, bottom-centerOffset, x+centerOffset, bottom , color, figure);
        surface.drawLine(right-centerOffset, bottom , right, bottom-centerOffset , color, figure);
        surface.drawLine( right, bottom-centerOffset ,right,y,color,figure);

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
        int bottom=y+charHeight-1;
        int right=x+charWidth-1;

        surface.drawLine(x,y, right ,y,color,figure);
        surface.drawLine(right, y  , x,bottom , color, figure);
        surface.drawLine(x ,bottom , right, bottom  , color, figure);
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
    public static void drawLetter(FunnySurface surface, int x, int y, char Letter, FunnySurface.DotColor color, FunnySurface.DotType figure,boolean center) {
        int standardWidth=5;
        int standardHeight=7;

        switch (Letter) {
            case 'A':
            case 'a':
                if(center) x=x-standardWidth/2;
                drawA(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_A();
                break;
            case 'B':
            case 'b':
                drawB(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_B();
                break;
            case 'C':
            case 'c':
                drawC(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_C();
                break;
            case 'Ç':
            case 'ç':
                drawCh(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_CH();
                break;
            case 'D':
            case 'd':
                drawD(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_D();
                break;
            case 'E':
            case 'e':
                if(center) x=x-standardWidth/2;
                drawE(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_E();
                break;
            case 'Ə':
            case 'ə':
                drawEE(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_EE();
                break;
            case 'F':
            case 'f':
                if(center) x=x-standardWidth/2;
                drawF(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_F();
                break;
            case 'G':
            case 'g':
                drawG(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_G();
                break;
            case 'Ğ':
            case 'ğ':
                drawGH(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_GH();
                break;
            case 'H':
            case 'h':
                if(center) x=x-standardWidth/2;
                drawH(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_H();
                break;
            case 'I':
            case 'ı':
                if(center) x=x-standardWidth/2;
                drawII(surface, x, y, standardWidth, standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_II();
                break;
            case 'İ':
            case 'i':
                if(center) x=x-standardWidth/2;
                drawI(surface, x, y, standardWidth, standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_I();
                break;
            case 'J':
            case 'j':
                drawJ(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_J();
                break;
            case 'K':
            case 'k':
                if(center) x=x-standardHeight/2;
                drawK(surface, x, y, standardHeight,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_K();
                break;
            case 'Q':
            case 'q':
                drawQ(surface, x, y, standardHeight,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_Q();
                break;
            case 'L':
            case 'l':
                standardWidth=5;
                if(center) x=x-standardWidth/2;
                drawL(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_L();
                break;
            case 'M':
            case 'm':
                if(center) x=x-standardHeight/2;
                drawM(surface, x, y, standardHeight,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_M();
                break;
            case 'N':
            case 'n':
                if(center) x=x-standardHeight/2;
                drawN(surface, x, y, standardHeight,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_N();
                break;
            case 'O':
            case 'o':
                drawO(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_O();
                break;
            case 'Ö':
            case 'ö':
                drawOO(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_OO();
                break;
            case 'P':
            case 'p':
                drawP(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_P();
                break;
            case 'R':
            case 'r':
                drawR(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_R();
                break;
            case 'S':
            case 's':
                drawS(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_S();
                break;
            case 'Ş':
            case 'ş':
                drawSH(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_SH();
                break;
            case 'T':
            case 't':
                if(center) x=x-standardWidth/2;
                drawT(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_T();
                break;
            case 'U':
            case 'u':
                if(center) x=x-standardWidth/2;
                drawU(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_U();
                break;
            case 'Ü':
            case 'ü':
                if(center) x=x-standardWidth/2;
                drawUU(surface, x, y, standardWidth,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_UU();
                break;
            case 'V':
            case 'v':
                if(center) x=x-standardHeight/2;
                drawV(surface, x, y, standardHeight,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_V();
                break;
            case 'Y':
            case 'y':
                if(center) x=x-standardHeight/2;
                drawY(surface, x, y, standardHeight,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_Y();
                break;
            case 'X':
            case 'x':
                if(center) x=x-standardHeight/2;
                drawX(surface, x, y, standardHeight,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_X();
                break;
            case 'Z':
            case 'z':
                if(center) x=x-standardHeight/2;
                drawZ(surface, x, y, standardHeight,standardHeight, color, figure);
                PhoneActivity.soundPlayer.play_Z();
                break;
            default:
        }
    }


}
