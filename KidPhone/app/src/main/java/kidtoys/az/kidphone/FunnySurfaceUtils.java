package kidtoys.az.kidphone;

import android.renderscript.Matrix3f;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static kidtoys.az.kidphone.FunnyButton.InnerShapeType.*;

/**
 * here we should implement surface drawing primitivies like line and so on
 * Implementation by Abdurrauf and Ramil
 */
public class FunnySurfaceUtils {


    public static final int scaleX=2;
    public static final int scaleY=2 ;
    public static final int standardCharWidth = 5;
    public static final int standardCharHeight = 7;
    public static final int standardFigWidth = 15;
    public static final int standardFigHeight = 15;
    public abstract static class Primitive {

        public abstract void draw(FunnySurface surface, FunnySurface.CallbackDraw callbackDraw, FunnySurface.DotColor color, FunnySurface.DotType dot_type, Matrix3f transform);
    }

    public static class Line extends Primitive {
        public int[] ix = new int[4];

        public Line(int x, int y, int x2, int y2) {
            this.ix[0] = x;
            this.ix[1] = y;
            this.ix[2] = x2;
            this.ix[3] = y2;
        }

        public void draw(FunnySurface surface, FunnySurface.CallbackDraw callbackDraw, FunnySurface.DotColor color, FunnySurface.DotType dot_type, Matrix3f transform) {
            if(transform!=null){
                float [] trans=transform.getArray();
                int x00=Math.round( trans[0]*ix[0] +trans[3]*ix[1]+trans[6]);
                int x10=Math.round( trans[1]*ix[0] +trans[4]*ix[1]+ trans[7] );

                int x01=Math.round( trans[0]*ix[2] +trans[3]*ix[3]+trans[6]);
                int x11=Math.round( trans[1]*ix[2] +trans[4]*ix[3]+trans[7]);
                surface.drawLine(x00, x10, x01, x11, color, dot_type, callbackDraw);

            }else {

                surface.drawLine(ix[0], ix[1], ix[2], ix[3], color, dot_type, callbackDraw);
            }
        }

    }

    public static class PointObj extends Primitive {
        public int x;
        public int y;

        public PointObj(int x, int y) {

            this.x = x;
            this.y = y;
        }

        public void draw(FunnySurface surface, FunnySurface.CallbackDraw callbackDraw, FunnySurface.DotColor color, FunnySurface.DotType dot_type, Matrix3f transform) {
            if(transform!=null){
                float [] trans=transform.getArray();
                int x00=Math.round( trans[0]*x +trans[3]*y+trans[6]);
                int x10=Math.round( trans[1]*x +trans[4]*y+ trans[7] );
                surface.putDot(x00, x10,   color, dot_type, callbackDraw);

            }else {

                surface.putDot(x,y, color, dot_type, callbackDraw);
            }
        }

    }


    public static class Ellipse extends  Primitive{

        public int[] ix = new int[4];

        public Ellipse(int x, int y, int x2, int y2) {
            this.ix[0] = x;
            this.ix[1] = y;
            this.ix[2] = x2;
            this.ix[3] = y2;
        }
        @Override
        public void draw(FunnySurface surface, FunnySurface.CallbackDraw callbackDraw, FunnySurface.DotColor color, FunnySurface.DotType dot_type, Matrix3f transform) {
            if(transform!=null){
                float [] trans=transform.getArray();
                int x0=Math.round( trans[0]*ix[0] +trans[3]*ix[1]+trans[6]);
                int y0=Math.round( trans[1]*ix[0] +trans[4]*ix[1]+ trans[7] );

                int x1=Math.round( trans[0]*ix[2] +trans[3]*ix[3]+trans[6]);
                int y1=Math.round( trans[1]*ix[2] +trans[4]*ix[3]+trans[7]);
                surface.drawEllipse(x0, y0,  x1, y1
                        , color, dot_type, callbackDraw);

            }else {

                surface.drawEllipse(ix[0], ix[1], ix[2], ix[3], color, dot_type, callbackDraw);
            }
        }
    }



    /* Letters and Numbers */
    private final static List<Primitive> FigA = Arrays.<Primitive>asList(
            new Line(0, 6, 0, 2),
            new Line(0, 2, 2, 0),
            new Line(2, 0, 4, 2),
            new Line(4, 2, 4, 6),
            new Line(0, 4, 4, 4));

    private final static List<Primitive> FigB = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 6),
            new Line(0,0,1 ,0),
            new Line(1, 0, 3, 0),
            new Line( 3, 0,4,1),
            new Line(4, 1, 4, 2),
            new Line(4, 2, 3, 3),
            new Line(4, 2,3,3),
            new Line(3, 3, 0, 3),
            new Line(3, 3, 4, 4),
            new Line(4, 4, 4, 5),
            new Line(4, 5, 3, 6),
            new Line(3, 6, 0, 6));

    private final static List<Primitive> FigC = Arrays.<Primitive>asList(
            new Line(4, 1,3,0),
            new Line(3, 0, 1, 0),
            new Line(1, 0, 0, 1),
            new Line(0, 1, 0, 5),
            new Line(0, 5,1,6),
            new Line(1, 6, 3, 6),
            new Line(3,6,4,5 ));

    private final static List<Primitive> FigCH = Arrays.<Primitive>asList(
            new Line(4, 1,3,0),
            new Line(3, 0, 1, 0),
            new Line(1, 0, 0, 1),
            new Line(0, 1, 0, 5),
            new Line(0, 5,1,6),
            new Line(1, 6, 3, 6),
            new Line(3,6,4,5 ),
            new Line(2, 5,2,7) );

    private final static List<Primitive> FigD = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 6),
            new Line(0, 0, 1, 0),
            new Line(1, 0, 3, 0),
            new Line(3, 0, 4, 1),
            new Line(4, 1, 4, 5),
            new Line(4, 5, 3, 6),
            new Line(3, 6, 0, 6));

    private final static List<Primitive> FigE = Arrays.<Primitive>asList(
            new Line(4, 0, 0, 0),
            new Line(0, 0, 0, 6),
            new Line(0, 6, 4, 6),
            new Line(0, 3, 3, 3));

    private final static List<Primitive> FigEE = Arrays.<Primitive>asList(
            new Line(0, 1, 1, 0),
            new Line(1, 0, 2, 0),
            new Line(2, 0, 3, 0),
            new Line(3, 0, 4, 1),
            new Line(4, 1, 4, 5),
            new Line(4, 5, 3, 6),
            new Line(3, 6, 1, 6),
            new Line(1, 6, 0, 5),
            new Line(0, 5, 0, 3),
            new Line(0, 3, 3, 3));

    private final static List<Primitive> FigG = Arrays.<Primitive>asList(
            new Line(4, 0, 1, 0),
            new Line(1, 0, 0, 1),
            new Line(0, 1, 0, 4),
            new Line(0,4,1, 5),
            new Line(1,5,2, 6),
            new Line(2, 6, 4, 6),
            new Line(4,6,4,5),
            new Line(4, 5, 4, 4),
            new Line(4,4,4,3),
            new Line(4, 3, 3, 3));

    private final static List<Primitive> FigF = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 6),
            new Line(0, 0, 4, 0),
             new Line(0, 3, 3, 3));

    private final static List<Primitive> FigGH = Arrays.<Primitive>asList(
            new Line(4, 0, 1, 0),
            new Line(1, 0, 0, 1),
            new Line(0, 1, 0, 4),
            new Line(0,4,1, 5),
            new Line(1,5,2, 6),
            new Line(2, 6, 4, 6),
            new Line(4,6,4,5),
            new Line(4, 5, 4, 4),
            new Line(4,4,4,3),
            new Line(4, 3, 3, 3),
            new Line(1, -2,2,-1),
            new Line(2, -1,3, -2));

    private final static List<Primitive> FigH = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 6),
            new Line(0, 3, 4, 3),
            new Line(4, 0, 4, 6));

    private final static List<Primitive> FigX = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 1),
            new Line(0, 1,4,5),
            new Line(4, 5,4,6),
            new Line(4, 0, 4, 1),
            new Line(4, 1, 0, 5),
            new Line(0, 5, 0, 6)

    );

    private final static List<Primitive> FigI = Arrays.<Primitive>asList(
            new Line(2, 0, 2, 6),
            new Line(1, 6, 3, 6),
            new Line(1, 0, 3, 0));


    private final static List<Primitive> FigII = Arrays.asList(
            new Line(2, 0, 2, 6),
            new Line(1, 6, 3, 6),
            new Line(1, 0, 3, 0),
            new PointObj(2, -2));

    private final static List<Primitive> FigJ = Arrays.<Primitive>asList(
            new Line(4, 0, 4, 5),
            new Line(4, 5, 3, 6),
            new Line(3, 6, 1, 6),
            new Line(1, 6, 0, 5),
            new Line(0, 5, 0, 4));

    private final static List<Primitive> FigK = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 6),
            new Line(0, 3, 3, 0),
            new Line(0, 3, 3, 6));


    private final static List<Primitive> FigQ = Arrays.<Primitive>asList(

            new Line(4, 1,3,0),
            new Line(3, 0, 1, 0),
            new Line(1, 0, 0, 1),
            new Line(0, 1, 0, 5),
            new Line(0, 5,1,6),
            new Line(1, 6, 3, 6),
            new Line(3,6,4,5 ),
            new Line(4,5,4,1 ),
            new Line(3, 5, 4, 6));

    private final static List<Primitive> FigL = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 6),
            new Line(0, 6, 4, 6)
    );

    private final static List<Primitive> FigM = Arrays.<Primitive>asList(
            new Line(0, 6, 0, 0),
            new Line(0, 0, 2, 2),
            new Line(2, 2, 4, 0),
            new Line(4, 0, 4, 6));

    private final static List<Primitive> FigN = Arrays.<Primitive>asList(
            new Line(0, 6, 0, 0),
            new Line(0, 1, 4, 5),
            new Line(4, 5, 4, 6),
            new Line(4, 5, 4, 0));

    private final static List<Primitive> FigO = Arrays.<Primitive>asList(
            new Line(4, 1,3,0),
            new Line(3, 0, 1, 0),
            new Line(1, 0, 0, 1),
            new Line(0, 1, 0, 5),
            new Line(0, 5,1,6),
            new Line(1, 6, 3, 6),
            new Line(3,6,4,5 ),
            new Line(4,5,4,1 ));

    private final static List<Primitive> FigOO = Arrays.asList(
            new Line(4, 1,3,0),
            new Line(3, 0, 1, 0),
            new Line(1, 0, 0, 1),
            new Line(0, 1, 0, 5),
            new Line(0, 5,1,6),
            new Line(1, 6, 3, 6),
            new Line(3,6,4,5 ),
            new Line(4,5,4,1 ),
            new PointObj(1, -1),
            new PointObj(3, -1));

    private final static List<Primitive> FigP = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 6),
            new Line(0, 0, 3, 0),
            new Line(3, 0, 4, 1),
            new Line(4, 1, 4, 2),
            new Line(  4, 2,3,3),
            new Line(3, 3, 0, 3));

    private final static List<Primitive> FigR = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 6),
            new Line(0, 0, 3, 0),
            new Line(3, 0, 4, 1),
            new Line(4, 1, 4, 2),
            new Line(  4, 2,3,3),
            new Line(3, 3, 0, 3),
            new Line(1, 3, 4, 6));

    private final static List<Primitive> FigS = Arrays.<Primitive>asList(
            new Line(4, 1, 3, 0),
            new Line(3, 0, 1, 0),
            new Line(1, 0, 0, 1),
            new Line(0, 1, 0, 2),
            new Line(0, 2, 1, 3),
            new Line(1, 3, 3, 3),
            new Line(3, 3, 4, 4),
            new Line(4, 4, 4, 5),
            new Line(4, 5, 3, 6),
            new Line(3, 6, 1, 6),
            new Line(1, 6, 0, 5)
    );

    private final static List<Primitive> FigSH = Arrays.<Primitive>asList(
            new Line(4, 1, 3, 0),
            new Line(3, 0, 1, 0),
            new Line(1, 0, 0, 1),
            new Line(0, 1, 0, 2),
            new Line(0, 2, 1, 3),
            new Line(1, 3, 3, 3),
            new Line(3, 3, 4, 4),
            new Line(4, 4, 4, 5),
            new Line(4, 5, 3, 6),
            new Line(3, 6, 1, 6),
            new Line(1, 6, 0, 5),
            new Line(2, 5, 2, 7));

    private final static List<Primitive> FigT = Arrays.<Primitive>asList(
            new Line(2, 0, 2, 6),
            new Line(0, 0, 4, 0));

    private final static List<Primitive> FigU = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 5),
            new Line(0, 5, 1, 6),
            new Line(1, 6, 3, 6),
            new Line(3, 6, 4, 5),
            new Line(4, 5, 4, 0));

    private final static List<Primitive> FigUU = Arrays.asList(
            new Line(0, 0, 0, 5),
            new Line(0, 5, 1, 6),
            new Line(1, 6, 3, 6),
            new Line(3, 6, 4, 5),
            new Line(4, 5, 4, 0),
            new PointObj(3, -2),
            new PointObj(1, -2));

    private final static List<Primitive> FigV = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 4),
            new Line(0, 4, 2, 6),
            new Line(2, 6, 4, 4),
            new Line(4, 4, 4, 0));

    private final static List<Primitive> FigY = Arrays.<Primitive>asList(
            new Line(0, 0, 2, 2),
            new Line(2, 2, 4, 0),
            new Line(2, 2, 2, 6));

    private final static List<Primitive> FigZ = Arrays.<Primitive>asList(
            new Line(0, 0, 4, 0),
            new Line(4, 0, 4, 1),
            new Line(4, 1, 0, 5),
            new Line(0, 5, 0, 6),
            new Line(0, 6, 4, 6));

    private final static List<Primitive> Fig1 = Arrays.<Primitive>asList(
            new Line(1, 1,2,0),
            new Line(2, 0, 2, 6),
            new Line(1, 6, 3, 6));

    private final static List<Primitive> Fig2 = Arrays.<Primitive>asList(
            new Line(0, 1, 1, 0),
            new Line(1, 0, 3, 0),
            new Line(3, 0, 4, 1),
            new Line(4, 1, 4, 2),
            new Line(4, 2, 3, 3),
            new Line(3, 3, 0, 6),
            new Line(0, 6, 1, 6),
            new Line(1, 6, 4, 6));

    private final static List<Primitive> Fig3 = Arrays.<Primitive>asList(
            new Line(0, 1, 1, 0),
            new Line(1, 0, 3, 0),
            new Line(3, 0, 4, 1),
            new Line(4, 1, 4, 2),
            new Line(4, 2, 3, 3),
            new Line(3, 3, 2, 3),
            new Line(3,3,4, 4 ),
            new Line(4, 4, 4, 5),
            new Line(4, 5, 3, 6),
            new Line(3, 6, 2, 6),
            new Line(2, 6, 1, 6),
            new Line(1, 6, 0, 5),
            new Line(2, 0, 3, 0),
            new Line(3, 0, 4, 1),
            new Line(4, 1, 4, 2)  );

    private final static List<Primitive> Fig4 = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 3),
            new Line(0, 3, 4, 3),
            new Line(3, 0, 3, 6) );
    private final static List<Primitive> Fig5 = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 3),
            new Line(0, 3, 1, 2),
            new Line(1, 2, 3, 2),
            new Line(3, 2, 4, 3),
            new Line(4, 3, 4, 5),
            new Line(4, 5, 3, 6),
            new Line(3, 6, 1, 6),
            new Line(1, 6, 0, 5),
            new Line(0, 0, 4, 0));

    private final static List<Primitive> Fig6 = Arrays.<Primitive>asList(
            new Line(3, 0, 1, 0),
            new Line(  1, 0,0,1),
            new Line(0, 1, 0, 5),
            new Line( 0, 5,1,6),
            new Line(1, 6, 3, 6),
            new Line( 3, 6,4,5),
            new Line(4, 5, 4, 4),
            new Line(4, 4, 3, 3),
            new Line(3, 3, 1, 3),
            new Line(1, 3, 0, 4)
    );

    private final static List<Primitive> Fig7 = Arrays.<Primitive>asList(
            new Line(1, 0, 4, 0),
            new Line(4, 0, 4, 2),
            new Line(4, 2, 2, 4),
            new Line(2, 4, 2, 6));

    private final static List<Primitive> Fig8 = Arrays.<Primitive>asList(
            new Line(1, 3, 0, 2),
            new Line(0, 2, 0, 1),
            new Line(0, 1, 1, 0),
            new Line(1, 0, 3, 0),
            new Line( 3, 0,4,1),
            new Line(4, 1, 4, 2),
            new Line(4, 2, 3, 3),
            new Line(3, 3, 1, 3),
            new Line(1, 3, 0, 4),
            new Line(0, 4, 0, 5),
            new Line(0, 5, 1, 6),
            new Line(1, 6, 3, 6),
            new Line(3, 6, 4, 5),
            new Line(4, 5, 4, 4),
            new Line(4, 4, 3, 3)
            );

    private final static List<Primitive> Fig9 = Arrays.<Primitive>asList(
            new Line(4, 1, 3, 0),
            new Line(3, 0, 1, 0),
            new Line( 1, 0,0,1),
            new Line(0, 1, 0, 2),
            new Line(  0, 2,1,3),
            new Line(1, 3, 3, 3),
            new Line(3, 3, 4, 2),
            new Line(4, 1, 4, 5),
            new Line(4, 5, 3, 6),
            new Line(3, 6, 1, 6));

    private final static List<Primitive> Fig0 = Arrays.<Primitive>asList(
            new Line(  1, 0,0,1),
            new Line(0, 1, 0, 5),
            new Line(  0, 5,1,6),
            new Line(1, 6, 3, 6),
            new Line(3, 6, 4, 5),
            new Line(4, 5, 4, 1),
            new Line(4, 1, 3, 0),
            new Line(3, 0, 1, 0));

    private final static List<Primitive> FigCircle = Collections.<Primitive>singletonList(
            new Ellipse(0, 0, 13, 13)
    );


    private final static List<Primitive> FigSquare = Arrays.<Primitive>asList(
            new Line(3, 2, 3, 11),
            new Line(3, 11, 12, 11),
            new Line(12, 11, 12, 2),
            new Line(12, 2, 3, 2)

    );
    private final static List<Primitive> FigTriangle = Arrays.<Primitive>asList(
            new Line(0, 11, 7, 4),
            new Line(7, 4, 14, 11),
            new Line(14, 11, 0, 11)

    );
    private final static List<Primitive> FigRectangle = Arrays.<Primitive>asList(
            new Line(0, 2, 0, 11),
            new Line(0, 11, 14, 11),
            new Line(14, 11, 14, 2),
            new Line(14, 2, 0, 2)

    );
    private final static List<Primitive> FigPentagon = Arrays.<Primitive>asList(
            new Line(0, 8, 7, 1),
            new Line(7, 1, 14, 8),
            new Line(14, 8, 9, 13),
            new Line(9, 13, 5, 13),
            new Line(5, 13, 0, 8)

    );
    private final static List<Primitive> FigHexagon = Arrays.<Primitive>asList(
            new Line(0, 7, 5, 2),
            new Line(5, 2, 9, 2),
            new Line(9, 2, 14, 7),
            new Line(14, 7, 9, 12),
            new Line(9, 12, 5, 12),
            new Line(5, 12, 0, 7)
    );
    private final static List<Primitive> FigEllipse = Collections.<Primitive>singletonList(
            new Ellipse(0, 2, 14, 11));
    private final static List<Primitive> FigHeart = Arrays.<Primitive>asList(
            new Line(7, 5, 9, 3),
            new Line(9, 3, 10, 3),
            new Line(10, 3, 12, 5),
            new Line(12, 5, 12, 7),
            new Line(12, 7, 7, 12),
            new Line(7, 5, 5, 3),
            new Line(5, 3, 4, 3),
            new Line(4, 3, 2, 5),
            new Line(2, 5, 2, 7),
            new Line(2, 7, 7, 12)
    );
    private final static List<Primitive> FigStar = Arrays.<Primitive>asList(
            new Line(7, 2, 7, 7),
            new Line(7, 7, 7, 12),
            new Line(2, 7, 7, 7),
            new Line(7, 7, 12, 7),
            new Line(7, 7, 3, 3),
            new Line(7, 7, 11, 3),
            new Line(7, 7, 3, 11),
            new Line(7, 7, 11, 11)
    );

    private static List<Primitive> FigTrapes = Arrays.<Primitive>asList(
      new Line(0, 3, 0, 12),
            new Line(0, 12, 14, 12),
            new Line(14, 12, 5, 3),
            new Line(5, 3, 0, 3));


    private final static List<Primitive> FigCorrect = Arrays.<Primitive>asList(
            new Line(-2, 3, 1, 6),
            new Line(1, 6, 7, 0)  );

    private final static List<Primitive> FigInCorrect = Arrays.asList(
            new Line(-2, 0, 4, 6),
            new PointObj(-2, 6),
            new Line(4, 0, -2, 6));

    private static final HashMap<Character, List<Primitive>> number_letters = new HashMap<Character, List<Primitive>>() {{
        put('A', FigA);
        put('B', FigB);
        put('C', FigC);
        put('Ç', FigCH);
        put('D', FigD);
        put('E', FigE);
        put('Ə', FigEE);
        put('F', FigF);
        put('G', FigG);
        put('Ğ', FigGH);
        put('H', FigH);
        put('I', FigI);
        put('İ', FigII);
        put('J', FigJ);
        put('K', FigK);
        put('Q', FigQ);
        put('L', FigL);
        put('M', FigM);
        put('N', FigN);
        put('O', FigO);
        put('Ö', FigOO);
        put('P', FigP);
        put('R', FigR);
        put('S', FigS);
        put('Ş', FigSH);
        put('T', FigT);
        put('U', FigU);
        put('Ü', FigUU);
        put('V', FigV);
        put('Y', FigY);
        put('X', FigX);
        put('Z', FigZ);
        put('0', Fig0);
        put('1', Fig1);
        put('2', Fig2);
        put('3', Fig3);
        put('4', Fig4);
        put('5', Fig5);
        put('6', Fig6);
        put('7', Fig7);
        put('8', Fig8);
        put('9', Fig9);
        put('$', FigCorrect);
        put('!',FigInCorrect);
    }};


    private static final HashMap<FunnyButton.InnerShapeType, List<Primitive>> shapes = new HashMap<FunnyButton.InnerShapeType, List<Primitive>>() {{
        put(Square, FigSquare);
        put(Triangle, FigTriangle);
        put(Rectangle, FigRectangle);
        put(Trapes, FigTrapes);
        put(Heart, FigHeart);
        put(Star, FigStar);
        put(Pentagon, FigPentagon);
        put(Ellipse, FigEllipse);
        put(Hexagon, FigHexagon);
        put(Circle, FigCircle);
    }};





    public static void drawFigure(FunnySurface surface, int x, int y, FunnyButton.InnerShapeType innerShapeType, FunnySurface.DotColor color, FunnySurface.DotType figure, boolean center) {
        drawFigure(surface, x, y, innerShapeType, color, figure, center, null);
    }

    public static void drawFigure(FunnySurface surface, int x, int y, FunnyButton.InnerShapeType innerShapeType, FunnySurface.DotColor color, FunnySurface.DotType figure, boolean center, FunnySurface.CallbackDraw clbk) {

        if (center){
            x = x - scaleX * standardFigWidth / 2;
            y= y- scaleY * standardFigHeight /2;
        }
        Matrix3f matrix3f=new Matrix3f();
        matrix3f.translate(x+scaleX * standardFigWidth / 2, y+scaleY * standardFigHeight /2);
        matrix3f.scale(scaleX,scaleY);
        matrix3f.translate(-standardFigWidth / 2 ,-standardFigHeight /2  );
        List<Primitive> obj = shapes.get(innerShapeType);
        if (obj != null) {
            for (Primitive prim : obj) {
                prim.draw(surface, clbk, color, figure, matrix3f);
            }
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


        if (center){
            x = x - scaleX * standardCharWidth / 2;
            y= y- scaleY * standardCharHeight /2;
        }
        Matrix3f matrix3f=new Matrix3f();
        matrix3f.translate(x+scaleX * standardCharWidth / 2, y+scaleY * standardCharHeight /2);
        matrix3f.scale(scaleX,scaleY);
        matrix3f.translate(-standardCharWidth / 2 ,-standardCharHeight /2  );
        List<Primitive> obj = number_letters.get(Character.toUpperCase(Letter));
        if (obj != null) {
            for (Primitive prim : obj) {
                prim.draw(surface, clbk, color, figure, matrix3f);
            }
        }

    }
}
