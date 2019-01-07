package kidtoys.az.kidphone;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by abdurrauf on 3/5/16.
 * This class will be used as main block for drawings
 */
public class FunnySurface {

    public interface CallbackDraw {
        boolean renderStep();
    }

    public static final DotColor[] supportedColors = DotColor.values();
    public static final DotType[] supportedTypes = DotType.values();
    private final static int maxColorSupport = supportedColors.length;
    private final static int maxTypeSupport = supportedTypes.length;
    private final int width;
    private final int height;
    private byte[] mem = null;


    private final Lock locker = new ReentrantLock();

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
            if (index >= 0 && index < DotColor.values().length) {
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

        for (int j = 0; j < drawHeight; j++) {
            for (int i = 0; i < drawWidth; i++) {
                int ind = (y + j) * width + x + i;
                int surfInd = (j + offsetSurfY) * surface.width + offsetSurfX + i;
                this.mem[ind] = surface.mem[surfInd];
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

    public void drawLine(int x1, int y1, int x2, int y2, DotColor color, DotType type) {
        drawLine(x1, y1, x2, y2, color, type, null);
    }

    /**
     * Draw line  {@see https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm}
     * for Octants  Bresenham's algorithm
     * http://rosettacode.org/wiki/Bitmap/Bresenham%27s_line_algorithm
     * for vertical,horizontal and diagonal simple algorithm
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param color
     * @param type
     */
    public void drawLine(int x1, int y1, int x2, int y2, DotColor color, DotType type, CallbackDraw clbk) {

        int deltaX = Math.abs(x2 - x1);
        int deltaY = Math.abs(y2 - y1);
        int y = y1;
        int x = x1;
        int cInt = color.ordinal() | type.ordinal() << 4;
        byte c = (byte) (cInt);

        if (deltaX == 0) {
            //horizontal
            int sign = y2 > y1 ? 1 : -1;
            for (; y != y2 + sign; y += sign) {
                if (x >= 0 && x < width && y >= 0 && y < height) {
                    mem[y * width + x] = c;
                    if (clbk != null) {
                        if (!clbk.renderStep()) {
                            return;
                        }
                    }
                }
            }

        } else if (deltaY == 0) {
            //vertical
            int sign = x2 > x1 ? 1 : -1;
            for (; x != x2 + sign; x += sign) {
                if (x >= 0 && x < width && y >= 0 && y < height) {
                    mem[y * width + x] = c;
                    if (clbk != null && !clbk.renderStep()) {
                            return;
                    }
                }
            }
        } else if (deltaX == deltaY) {
            //diagonal;
            int signX = x2 > x1 ? 1 : -1;
            int signY = y2 > y1 ? 1 : -1;
            for (; y != y2 + signY; y += signY, x += signX) {
                if (x >= 0 && x < width && y >= 0 && y < height) {
                    mem[y * width + x] = c;
                    if (clbk != null && !clbk.renderStep()) {
                            return;
                    }
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
                    if (x1 >= 0 && x1 < width && y1 >= 0 && y1 < height) {
                        mem[y1 * width + x1] = c;
                        if (clbk != null && !clbk.renderStep()) {
                                return;
                        }
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
                    if (x1 >= 0 && x1 < width && y1 >= 0 && y1 < height) {
                        mem[y1 * width + x1] = c;
                        if (clbk != null && !clbk.renderStep()) {
                                return;
                        }
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

    }


    public void drawEllipseFast(int xc, int yc, int widthE, int heightE, DotColor color, DotType type, CallbackDraw clbk) {


        int a2 = widthE * widthE;
        int b2 = heightE * heightE;
        int fa2 = 4 * a2, fb2 = 4 * b2;
        int x, y, sigma;
        int cInt = color.ordinal() | type.ordinal() << 4;
        byte c = (byte) (cInt);
        int px;
        int py;
    /* first half */
        for (x = 0, y = heightE, sigma = 2 * b2 + a2 * (1 - 2 * heightE); b2 * x <= a2 * y; x++) {
            //DrawPixel (xc + x, yc + y);
            px = xc + x;
            py = yc + y;
            if (px >= 0 && px < width && py >= 0 && py < height) {
                mem[py * width + px] = c;
            }

            //DrawPixel (xc - x, yc + y);
            px = xc - x;
            py = yc + y;
            if (px >= 0 && px < width && py >= 0 && py < height) {
                mem[py * width + px] = c;
            }
            //DrawPixel (xc + x, yc - y);
            px = xc + x;
            py = yc - y;
            if (px >= 0 && px < width && py >= 0 && py < height) {
                mem[py * width + px] = c;
            }
            // DrawPixel (xc - x, yc - y);
            px = xc - x;
            py = yc - y;
            if (px >= 0 && px < width && py >= 0 && py < height) {
                mem[py * width + px] = c;
            }
            if (sigma >= 0) {
                sigma += fa2 * (1 - y);
                y--;
            }
            sigma += b2 * ((4 * x) + 6);
        }

    /* second half */
        for (x = widthE, y = 0, sigma = 2 * a2 + b2 * (1 - 2 * widthE); a2 * y <= b2 * x; y++) {
            //DrawPixel (xc + x, yc + y);
            px = xc + x;
            py = yc + y;
            if (px >= 0 && px < width && py >= 0 && py < height) {
                mem[py * width + px] = c;
            }
            //DrawPixel (xc - x, yc + y);
            px = xc - x;
            py = yc + y;
            if (px >= 0 && px < width && py >= 0 && py < height) {
                mem[py * width + px] = c;
            }
            //DrawPixel (xc + x, yc - y);
            px = xc + x;
            py = yc - y;
            if (px >= 0 && px < width && py >= 0 && py < height) {
                mem[py * width + px] = c;
                if (clbk != null && !clbk.renderStep()) {
                        return;
                }
            }
            // DrawPixel (xc - x, yc - y);
            px = xc - x;
            py = yc - y;
            if (px >= 0 && px < width && py >= 0 && py < height) {
                mem[py * width + px] = c;
            }
            if (sigma >= 0) {
                sigma += fb2 * (1 - x);
                x--;
            }
            sigma += a2 * ((4 * y) + 6);
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
        White, Red, Blue, Green, Yellow, Orange, Pink, Magenta, Black
    }


}
