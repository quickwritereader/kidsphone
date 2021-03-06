package kidtoys.az.kidphone;


import android.graphics.Point;
import android.renderscript.Matrix3f;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by abdurrauf on 3/5/16.
 * This class will be used as main block for drawings
 */
public class FunnySurface {


    private int pW = 4;
    private int pH = 2;



    public interface CallbackDraw {
        boolean renderStep();
    }


    public static final DotColor[] supportedColors = DotColor.values();
    public static final DotType[] supportedTypes = DotType.values();
    private final static int maxColorSupport = supportedColors.length;
    private final static int maxTypeSupport = supportedTypes.length;
    private final int width;
    private final int height;
    private byte[] mem;


    private final Lock locker = new ReentrantLock();


    private Matrix3f windowToViewPort = new Matrix3f();

    /**
     * Create 1x1 blank surface
     */
    public FunnySurface() {
        width = 1;
        height = 1;
        mem = new byte[width * height];
    }

    /**
     * Creates blank surface
     *
     * @param width
     * @param height
     */
    public FunnySurface(int width, int height) {
        if (width == 0) width = 1;
        if (height == 0) height = 1;
        this.width = width;
        this.height = height;
        mem = new byte[width * height];
    }

    public static int getMaxColorSupport() {
        return maxColorSupport;
    }

    public static int getMaxTypeSupport() {
        return maxTypeSupport;
    }

    /**
     * Creates surface from specific color and dot
     * Can be used to get filled rectangles
     *
     * @param width
     * @param height
     * @param color
     * @param type
     * @return
     */
    static public FunnySurface createSurface(int width, int height, DotColor color, DotType type) {
        FunnySurface newS = new FunnySurface(width, height);
        int cInt = color.ordinal() | (type.ordinal() << 4);
        byte c = (byte) (cInt);
        for (int i = 0; i < newS.mem.length; i++) {
            newS.mem[i] = c;
        }
        return newS;
    }

    public void lock() {
        locker.lock();
    }

    public boolean tryLock() {
        return locker.tryLock();
    }

    public void unlock() {
        locker.unlock();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public byte[] getMem() {
        return mem;
    }

    /**
     * get dot color (Similar get pixel)
     *
     * @param x
     * @param y
     * @return
     */
    public DotColor getDotColor(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            byte a = mem[y * width + x];
            int index = a & 0xF;
            if (index < DotColor.values().length) {
                return supportedColors[index];
            }

        }
        return DotColor.Black;
    }

    /**
     * get dot type (Similar get pixel)
     *
     * @param x
     * @param y
     * @return
     */
    public DotType getDotType(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            byte a = mem[y * width + x];
            int index = (a & 0xF0) >> 4;
            return supportedTypes[index];

        }
        return DotType.None;
    }

    /**
     * putDotColor
     *
     * @param x
     * @param y
     * @param color
     */
    public void putDotColor(int x, int y, DotColor color) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            mem[y * width + x] = (byte) ((mem[y * width + x] & 0xF0) | color.ordinal());
        }
    }


    public void putDot(int x, int y, DotColor color, DotType type, CallbackDraw clbk) {

        if (x >= 0 && x < width && y >= 0 && y < height) {
            mem[y * width + x] = (byte) (color.ordinal() | (type.ordinal() << 4));
            if (clbk != null) {
                clbk.renderStep();
            }
        }
    }

    public void putDot(int x, int y, int pWidth, int pHeight, byte val) {
        int startX = x - pWidth / 2;
        int startY = y - pHeight / 2;
        for (int localX = startX; localX < startX + pWidth; localX++) {
            for (int localY = startY; localY < startY + pHeight; localY++) {
                if (localX >= 0 && localX < width && localY >= 0 && localY < height) {
                    mem[localY * width + localX] = val;
                }
            }
        }
    }

    public void putDot(int x, int y, int pWidth, int pHeight, DotColor color, DotType type, CallbackDraw clbk) {
        putDot(x, y, pWidth, pHeight, (byte) (color.ordinal() | (type.ordinal() << 4)));
        if (clbk != null) {
            clbk.renderStep();
        }
    }

    public void putDot(int x, int y, int pWidth, int pHeight, DotColor color, DotType type) {
        putDot(x, y, pWidth, pHeight, (byte) (color.ordinal() | (type.ordinal() << 4)));
    }

    /**
     * Put Dot data into surface
     *
     * @param x
     * @param y
     * @param color
     * @param type
     */
    public void putDot(int x, int y, DotColor color, DotType type) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            mem[y * width + x] = (byte) (color.ordinal() | (type.ordinal() << 4));
        }
    }

    /**
     * Clear Dot data into surface
     *
     * @param x
     * @param y
     */
    public void clearDot(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            mem[y * width + x] = 0;
        }
    }

    public void clearDot(int x, int y, int pWidth, int pHeight) {
        putDot(x, y, pWidth, pHeight, (byte) 0);
    }

    /**
     * Blt surface on the surface
     *
     * @param surface
     * @param x
     * @param y
     */
    public void putSurface(FunnySurface surface, int x, int y) {

        //find copyable areas
        //if above surface area
        if (x > this.width) return;
        if (y > this.height) return;
        int offsetSurfX = 0;
        int offsetSurfY = 0;
        int drawWidth = surface.width;
        int drawHeight = surface.height;
        if (x < 0) {
            offsetSurfX = -x;
            drawWidth = drawWidth - offsetSurfX;
        }
        if (y < 0) {
            offsetSurfY = -y;
            drawHeight = drawHeight - offsetSurfY;
        }
        //lets blt naive way
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        int remain = this.width - x;
        drawWidth = remain > drawWidth ? drawWidth : remain;
        remain = this.height - y;
        drawHeight = remain > drawHeight ? drawHeight : remain;
        byte[] copy;
        if (this == surface) {
            copy = Arrays.copyOf(surface.mem, surface.mem.length);
        } else {
            copy = surface.mem;
        }
        for (int j = 0; j < drawHeight; j++) {
            for (int i = 0; i < drawWidth; i++) {
                int ind = (y + j) * width + x + i;
                int surfInd = (j + offsetSurfY) * surface.width + offsetSurfX + i;
                this.mem[ind] = copy[surfInd];
            }
        }


    }

    /**
     * Blt surface on the surface
     *
     * @param surface
     * @param x
     * @param y
     */
    public void putSurfaceOverlay(FunnySurface surface, int x, int y) {

        //find copyable areas
        //if above surface area
        if (x > this.width) return;
        if (y > this.height) return;
        int offsetSurfX = 0;
        int offsetSurfY = 0;
        int drawWidth = surface.width;
        int drawHeight = surface.height;
        if (x < 0) {
            offsetSurfX = -x;
            drawWidth = drawWidth - offsetSurfX;
        }
        if (y < 0) {
            offsetSurfY = -y;
            drawHeight = drawHeight - offsetSurfY;
        }
        //lets blt naive way
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        int remain = this.width - x;
        drawWidth = remain > drawWidth ? drawWidth : remain;
        remain = this.height - y;
        drawHeight = remain > drawHeight ? drawHeight : remain;
        byte[] copy;
        if (this == surface) {
            copy = Arrays.copyOf(surface.mem, surface.mem.length);
        } else {
            copy = surface.mem;
        }
        for (int j = 0; j < drawHeight; j++) {
            for (int i = 0; i < drawWidth; i++) {
                int ind = (y + j) * width + x + i;
                int surfInd = (j + offsetSurfY) * surface.width + offsetSurfX + i;
                if (copy[surfInd] != 0) this.mem[ind] = copy[surfInd];
            }
        }


    }

    /***
     * Blt surface
     *
     * @param surface
     * @param x
     * @param y
     * @param offsetx
     * @param offsety
     */
    public void putSurface(FunnySurface surface, int x, int y, int offsetx, int offsety) {
        throw new UnsupportedOperationException();
    }

    /**
     * Clear screen
     */
    public void clear() {
        for (int i = 0; i < mem.length; i++) {
            mem[i] = 0;
        }
    }

    public void clear(int x, int y, int w, int h) {
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        int remain = this.width - x;
        w = remain > w ? w : remain;
        remain = this.height - y;
        h = remain > h ? h : remain;

        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                int ind = (y + j) * width + x + i;
                this.mem[ind] = 0;
            }
        }
    }

    public void CopyFrom(FunnySurface funnySurface) {

        int min_w = Math.min(funnySurface.width, width);
        int min_h = Math.min(funnySurface.height, height);
        for (int i = 0; i < min_w; i++) {
            for (int j = 0; j < min_h; j++) {
                mem[j * width + i] = funnySurface.mem[j * funnySurface.width + i];
            }
        }
    }

    /**
     *  Draw line  {@see https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm}
     * for Octants  Bresenham's algorithm
     * http://rosettacode.org/wiki/Bitmap/Bresenham%27s_line_algorithm
     * for vertical,horizontal and diagonal simple algorithm
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param pW
     * @param pH
     * @param color
     * @param type
     * @param clbk
     * @return
     */
    public Point drawLine(int x1, int y1, int x2, int y2, int pW, int pH, DotColor color, DotType type, CallbackDraw clbk) {
        int deltaX = Math.abs(x2 - x1);
        int deltaY = Math.abs(y2 - y1);
        int y = y1;
        int x = x1;
        Point endp = new Point(x2, y2);
        int cInt = color.ordinal() | type.ordinal() << 4;
        byte c = (byte) (cInt);

        if (deltaX == 0) {
            //horizontal
            int sign = y2 > y1 ? 1 : -1;
            for (; y != y2 + sign; y += sign) {
                putDot(x, y, pW, pH, c);
                if (clbk != null) {
                    if (!clbk.renderStep()) {
                        return endp;
                    }
                }
            }

        } else if (deltaY == 0) {
            //vertical
            int sign = x2 > x1 ? 1 : -1;
            for (; x != x2 + sign; x += sign) {
                putDot(x, y, pW, pH, c);
                if (clbk != null && !clbk.renderStep()) {
                    return endp;
                }
            }
        } else if (deltaX == deltaY) {
            //diagonal;
            int signX = x2 > x1 ? 1 : -1;
            int signY = y2 > y1 ? 1 : -1;
            for (; y != y2 + signY; y += signY, x += signX) {
                putDot(x, y, pW, pH, c);
                if (clbk != null && !clbk.renderStep()) {
                    return endp;
                }
            }
        } else {
            // delta of exact value and rounded value of the dependant variable
            int d = 0;

            int dy2 = (deltaY << 1); // slope scaling factors to avoid floating
            int dx2 = (deltaX << 1); // point

            int ix = x1 < x2 ? 1 : -1; // increment direction
            int iy = y1 < y2 ? 1 : -1;

            if (deltaY <= deltaX) {
                for (; ; ) {
                    putDot(x1, y1, pW, pH, c);
                    if (clbk != null && !clbk.renderStep()) {
                        return endp;
                    }
                    if (x1 == x2)
                        break;
                    x1 += ix;
                    d += dy2;
                    if (d > deltaX) {
                        y1 += iy;
                        d -= dx2;
                    }
                }
            } else {
                for (; ; ) {
                    //plot(g, x1, y1);
                    putDot(x1, y1, pW, pH, c);
                    if (clbk != null && !clbk.renderStep()) {
                        return endp;
                    }
                    if (y1 == y2)
                        break;
                    y1 += iy;
                    d += dx2;
                    if (d > deltaY) {
                        x1 += ix;
                        d -= dy2;
                    }
                }
            }
        }//end if else
        return endp;
    }

    public Point drawLine(int x1, int y1, int x2, int y2, DotColor color, DotType type, CallbackDraw clbk) {
      return drawLine(x1,y1,x2,y2,this.pW,this.pH,color,type,clbk);
    }

    public Point drawLine(int x1, int y1, int x2, int y2, DotColor color, DotType type) {
        return drawLine(x1, y1, x2, y2,this.pW,this.pH, color, type, null);
    }

    public void drawEllipse(int x0, int y0, int x1, int y1, int pW, int pH, DotColor color, DotType type, CallbackDraw clbk) {

        { /* rectangular parameter enclosing the ellipse */
            int a = Math.abs(x1 - x0);
            int b = Math.abs(y1 - y0);
            int b1 = b & 1; /* diameter */
            double dx = 4 * (1.0 - a) * b * b;
            double dy = 4 * (b1 + 1) * a * a; /* error increment */
            double err = dx + dy + b1 * a * a;
            double e2; /* error of 1.step */
            int cInt = color.ordinal() | type.ordinal() << 4;
            byte c = (byte) (cInt);
            if (x0 > x1) {
                x0 = x1;
                x1 += a;
            } /* if called with swapped points */
            if (y0 > y1) y0 = y1; /* .. exchange them */
            y0 += (b + 1) / 2;
            y1 = y0 - b1; /* starting pixel */
            a = 8 * a * a;
            b1 = 8 * b * b;
            do {
                //(x1, y0); /* I. Quadrant */

                putDot(x1, y0, pW, pH, c);
                if (clbk != null && !clbk.renderStep()) {
                    return;
                }
                //(x0, y0); /* II. Quadrant */
                //mem[y0 * width + x0] = c;
                putDot(x0, y0, pW, pH, c);
                if (clbk != null && !clbk.renderStep()) {
                    return;
                }
                //(x0, y1); /* III. Quadrant */
                //  mem[y1 * width + x0] = c;
                putDot(x0, y1, pW, pH, c);
                if (clbk != null && !clbk.renderStep()) {
                    return;
                }
                //(x1, y1); /* IV. Quadrant */
                //mem[y1 * width + x1] = c;
                putDot(x1, y1, pW, pH, c);
                if (clbk != null && !clbk.renderStep()) {
                    return;
                }
                e2 = 2 * err;
                if (e2 <= dy) {
                    y0++;
                    y1--;
                    err += dy += a;
                } /* y step */
                if (e2 >= dx || 2 * err > dy) {
                    x0++;
                    x1--;
                    err += dx += b1;
                } /* x */
            } while (x0 <= x1);
            while (y0 - y1 <= b) { /* to early stop of flat ellipses a=1 */
                //(x0 - 1, y0); /* -> finish tip of ellipse */
                //mem[y0 * width + x0 - 1] = c;
                putDot(x0 - 1, y0, pW, pH, c);
                if (clbk != null && !clbk.renderStep()) {
                    return;
                }
                //(x1 + 1, y0++);
                // mem[y0 * width + x1 + 1] = c;
                putDot(x1 + 1, y0, pW, pH, c);
                if (clbk != null && !clbk.renderStep()) {
                    return;
                }
                y0 += 1;
                //(x0 - 1, y1);
                //mem[y1 * width + x0 - 1] = c;
                putDot(x0 - 1, y1, pW, pH, c);
                if (clbk != null && !clbk.renderStep()) {
                    return;
                }
                //(x1 + 1, y1--);
                // mem[y1 * width + x1 + 1] = c;
                putDot(x1 + 1, y1, pW, pH, c);
                if (clbk != null && !clbk.renderStep()) {
                    return;
                }
                y1 -= 1;
            }
        }
    }

    public void drawEllipse(int x0, int y0, int x1, int y1, DotColor color, DotType type, CallbackDraw clbk) {
        drawEllipse(x0, y0, x1, y1, this.pW, this.pH, color, type, clbk);
    }


    public void drawFigure(FunnyButton.InnerShapeType innerShapeType) {
        locker.lock();
        try {
            this.clear();
            int figureRandom = (int) (Math.random() * (maxTypeSupport - 1)) + 1;
            int colorRandom = (int) (Math.random() * (maxColorSupport - 2)) + 1;//exclude white and black
            FunnySurfaceUtils.drawFigure(this, width / 2, height / 2, innerShapeType, FunnySurface.supportedColors[colorRandom],
                    FunnySurface.DotType.Circle, true);

        } finally {
            locker.unlock();
        }
    }

    public void drawChar(char l) {
        //Log.d("letter", "letter: " + l);
        locker.lock();
        try {
            this.clear();
            int figureRandom = (int) (Math.random() * (maxTypeSupport - 1)) + 1;
            int colorRandom = (int) (Math.random() * (maxColorSupport - 2)) + 1;//exclude white and black
        /*FunnySurfaceUtils.drawChar(mainSurface, mainSurface.getWidth() / 2, 4, l, FunnySurface.supportedColors[colorRandom],
                FunnySurface.supportedTypes[figureRandom], true);*/
            FunnySurfaceUtils.drawChar(this, width / 2, height / 2, l, FunnySurface.supportedColors[colorRandom],
                    FunnySurface.DotType.Circle, true);
        } finally {
            locker.unlock();
        }
    }

    /**
     * used for drawing dot. for example: square will draw dot as square
     */
    public enum DotType {
        None, Square, Circle, Triangle, Romb, Pentagon, Hexagon, Star, Heart
    }

    /**
     * used to determine color of dot
     */
    public enum DotColor {
        Black, Red, Blue, Green, Yellow, Orange, Pink,  White
    }


}
