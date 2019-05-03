package kidtoys.az.kidphone;

import android.renderscript.Matrix3f;
import java.util.Arrays;
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

    public abstract static class Primitive {

        public abstract void draw(FunnySurface surface, FunnySurface.CallbackDraw callbackDraw, FunnySurface.DotColor color, FunnySurface.DotType dot_type, Matrix3f transform);
    }

    public static class Line extends Primitive {
        public int ix[]=new int[4];

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

        public int ix[]=new int[4];

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
                int x00=Math.round( trans[0]*ix[0] +trans[3]*ix[1]+trans[6]);
                int x10=Math.round( trans[1]*ix[0] +trans[4]*ix[1]+ trans[7] );

                int x01=Math.round( trans[0]*ix[2] +trans[3]*ix[3]+trans[6]);
                int x11=Math.round( trans[1]*ix[2] +trans[4]*ix[3]+trans[7]);
                surface.drawEllipseFast(x00, x10, x01-x00, x11-x10
                        , color, dot_type, callbackDraw);

            }else {

                surface.drawEllipseFast(ix[0], ix[1], ix[2]-ix[0], ix[3]-ix[1], color, dot_type, callbackDraw);
            }
        }
    }



    /* Letters and Numbers */
    private final static List<Primitive> FigA = Arrays.<Primitive>asList(
            new Line(0, 6, 0, 2),
            new Line(0, 2, 2, 0),
            new Line(2, 0, 3, 0),
            new Line(3, 0, 5, 2),
            new Line(5, 2, 5, 6),
            new Line(0, 4, 5, 4));

    private final static List<Primitive> FigB = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 6),
            new Line(0,0,1 ,0),
            new Line(1, 0, 4, 0),
            new Line( 4, 0,5,1),
            new Line(5, 1, 5, 2),
            new Line(5, 2, 4, 3),
            new Line(5, 2,4,3),
            new Line(4, 3, 0, 3),
            new Line(4, 3, 5, 4),
            new Line(5, 4, 5, 5),
            new Line(5, 5, 4, 6),
            new Line(4, 6, 0, 6));

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
            new Line(1, 0, 4, 0),
            new Line(4, 0, 5, 1),
            new Line(5, 1, 5, 5),
            new Line(5, 5, 4, 6),
            new Line(4, 6, 0, 6));

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
            new Line(5, 0, 1, 0),
            new Line(1, 0, 0, 1),
            new Line(0, 1, 0, 4),
            new Line(0,4,1, 5),
            new Line(1,5,2, 6),
            new Line(2, 6, 5, 6),
            new Line(5,6,5,5),
            new Line(5, 5, 5, 4),
            new Line(5,4,5,3),
            new Line(5, 3, 3, 3));

    private final static List<Primitive> FigF = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 6),
            new Line(0, 0, 4, 0),
             new Line(0, 3, 3, 3));

    private final static List<Primitive> FigGH = Arrays.<Primitive>asList(
            new Line(5, 0, 1, 0),
            new Line(1, 0, 0, 1),
            new Line(0, 1, 0, 4),
            new Line(0,4,1, 5),
            new Line(1,5,2, 6),
            new Line(2, 6, 5, 6),
            new Line(5,6,5,5),
            new Line(5, 5, 5, 4),
            new Line(5,4,5,3),
            new Line(5, 3, 3, 3),
            new Line(1, -2,2,-1),
            new Line(2, -1,3, -2));

    private final static List<Primitive> FigH = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 6),
            new Line(0, 3, 4, 3),
            new Line(4, 0, 4, 6));

    private final static List<Primitive> FigX = Arrays.asList(
            new Line(0, 0, 6, 6),
            new PointObj(6, 6),
            new Line(6, 0, 0, 6));

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
            new Line(3, 5, 5, 7));

    private final static List<Primitive> FigL = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 6),
            new Line(0, 6, 4, 6));

    private final static List<Primitive> FigM = Arrays.<Primitive>asList(
            new Line(0, 6, 0, 0),
            new Line(0, 0, 3, 3),
            new Line(3, 3, 6, 0),
            new Line(6, 0, 6, 6));

    private final static List<Primitive> FigN = Arrays.<Primitive>asList(
            new Line(0, 6, 0, 0),
            new Line(0, 0, 6, 6),
            new Line(6, 6, 6, 0));

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
            new PointObj(2, -1));

    private final static List<Primitive> FigP = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 6),
            new Line(0, 0, 1, 0),
            new Line(1, 0, 4, 0),
            new Line(4, 0, 5, 1),
            new Line(5, 1, 5, 2),
            new Line(  5, 2,4,3),
            new Line(4, 3, 0, 3));

    private final static List<Primitive> FigR = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 6),
            new Line(0, 0, 1, 0),
            new Line(1, 0, 4, 0),
            new Line(4, 0, 5, 1),
            new Line(5, 1, 5, 2),
            new Line(  5, 2,4,3),
            new Line(4, 3, 0, 3),
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
            new Line(3, 0, 3, 6),
            new Line(1, 0, 5, 0));

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
            new PointObj(2, -2),
            new PointObj(1, -2));

    private final static List<Primitive> FigV = Arrays.<Primitive>asList(
            new Line(0, 0, 0, 3),
            new Line(0, 3, 3, 6),
            new Line(3, 6, 6, 3),
            new Line(6, 3, 6, 0));

    private final static List<Primitive> FigY = Arrays.<Primitive>asList(
            new Line(0, 0, 3, 3),
            new Line(3, 3, 6, 0),
            new Line(3, 3, 3, 6));

    private final static List<Primitive> FigZ = Arrays.<Primitive>asList(
            new Line(0, 0, 6, 0),
            new Line(6, 0, 0, 6),
            new Line(0, 6, 6, 6));

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


    private final static List<Primitive> FigCircle = Arrays.<Primitive>asList(
            new Line(10, 2, 12, 2),
            new Line(13, 3, 14, 3),
            new Line(15, 4, 15, 5),
            new Line(16, 6, 16, 9),
            new Line(15, 10, 15, 11),
            new Line(14, 12, 13, 12),
            new Line(12, 13, 8, 13),
            new Line(7, 12, 6, 12),
            new Line(5, 11, 5, 10),
            new Line(4, 9, 4, 6),
            new Line(5, 5, 5, 4),
            new Line(6, 3, 7, 3),
            new Line(8, 2, 9, 2)
    );
    private final static List<Primitive> FigSquare = Arrays.<Primitive>asList(
            new Line(5, 3, 5, 12),
            new Line(5, 12, 14, 12),
            new Line(14, 12, 14, 3),
            new Line(14, 3, 5, 3)

    );
    private final static List<Primitive> FigTriangle = Arrays.<Primitive>asList(
            new Line(3, 11, 10, 4),
            new Line(10, 4, 17, 11),
            new Line(16, 11, 4, 11)

    );
    private final static List<Primitive> FigRectangle = Arrays.<Primitive>asList(
            new Line(3, 4, 3, 11),
            new Line(3, 11, 16, 11),
            new Line(16, 11, 16, 4),
            new Line(16, 4, 3, 4)

    );
    private final static List<Primitive> FigPentagon = Arrays.<Primitive>asList(
            new Line(3, 8, 10, 1),
            new Line(10, 1, 17, 8),
            new Line(16, 9, 12, 13),
            new Line(11, 13, 9, 13),
            new Line(8, 13, 3, 8)

    );
    private final static List<Primitive> FigHexagon = Arrays.<Primitive>asList(
            new Line(3, 7, 8, 2),
            new Line(9, 2, 11, 2),
            new Line(12, 2, 17, 7),
            new Line(16, 8, 12, 12),
            new Line(11, 12, 9, 12),
            new Line(8, 12, 3, 7)
    );
    private final static List<Primitive> FigEllipse = Arrays.<Primitive>asList(
            new Line(10, 2, 14, 2),
            new Line(15, 3, 16, 3),
            new Line(17, 4, 17, 5),
            new Line(18, 6, 18, 9),
            new Line(17, 10, 17, 11),
            new Line(16, 12, 15, 12),
            new Line(14, 13, 6, 13),
            new Line(5, 12, 4, 12),
            new Line(3, 11, 3, 10),
            new Line(2, 9, 2, 6),
            new Line(3, 5, 3, 4),
            new Line(4, 3, 5, 3),
            new Line(6, 2, 9, 2));
    private final static List<Primitive> FigHeart = Arrays.<Primitive>asList(
            new Line(10, 5, 12, 3),
            new Line(13, 3, 15, 4),
            new Line(15, 5, 15, 6),
            new Line(15, 7, 10, 12),
            new Line(10, 5, 8, 3),
            new Line(7, 3, 5, 4),
            new Line(5, 5, 5, 6),
            new Line(5, 7, 10, 12)
    );
    private final static List<Primitive> FigStar = Arrays.<Primitive>asList(
            new Line(10, 3, 10, 8),
            new Line(10, 8, 10, 13),
            new Line(5, 8, 10, 8),
            new Line(10, 8, 15, 8),
            new Line(10, 8, 6, 4),
            new Line(10, 8, 14, 4),
            new Line(10, 8, 6, 12),
            new Line(10, 8, 14, 12)
    );

    private static List<Primitive> FigTrapes = Arrays.<Primitive>asList(
            new Line(2, 4, 9, 4),
            new Line(10, 5, 17, 12),
            new Line(16, 12, 2, 12),
            new Line(2, 11, 2, 5));


    private final static List<Primitive> FigCorrect = Arrays.<Primitive>asList(
            new Line(0, 7, 3, 10),
            new Line(3, 10, 9, 4)  );

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

        int standardWidth = 20;
        int standardHeight = 18;
        if (center){
            x = x - scaleX*standardWidth / 2  ;
            y=y-scaleY*standardHeight/2;
        }
        Matrix3f matrix3f=new Matrix3f();
        matrix3f.translate(12+x,12+y);
        matrix3f.scale(scaleX,scaleY);
        matrix3f.translate(-standardWidth/2 ,-standardHeight/2  );
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
        int standardWidth = 5;
        int standardHeight = 7;

        if (center){
            x = x - scaleX *standardWidth / 2;
            y= y- scaleY *standardHeight/2;
        }
        Matrix3f matrix3f=new Matrix3f();
        matrix3f.translate(3+x,4+y);
        matrix3f.scale(scaleX,scaleY);
        matrix3f.translate(-3 ,-4  );
        List<Primitive> obj = number_letters.get(Character.toUpperCase(Letter));
        if (obj != null) {
            for (Primitive prim : obj) {
                prim.draw(surface, clbk, color, figure, matrix3f);
            }
        }

    }
}
