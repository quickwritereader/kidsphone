package kidtoys.az.kidphone;

import android.graphics.Matrix;
import android.graphics.Path;

/**
 * Canavs Utils
 */
public class CanvasUtils {
    public static Path StarPath(float cx, float cy, int spikes, float outerRadius, float innerRadius) {
        Path path = new Path();
        float rot = (float) Math.PI / 2 * 3;
        float x;
        float y;
        float step = (float) Math.PI / spikes;
        path.moveTo(cx, cy - outerRadius);
        for (int i = 0; i < spikes; i++) {
            x = cx + (float) Math.cos(rot) * outerRadius;
            y = cy + (float) Math.sin(rot) * outerRadius;
            path.lineTo(x, y);
            rot += step;
            x = cx + (float) Math.cos(rot) * innerRadius;
            y = cy + (float) Math.sin(rot) * innerRadius;
            path.lineTo(x, y);
            rot += step;
        }
        path.lineTo(cx, cy - outerRadius);

        path.close();
        return path;
    }

    public static Path TrapesPath(float left, float top, float right, float bottom) {
        Path path = new Path();
        path.moveTo(left, top);
        path.lineTo(right - (right - left) /2.f, top);
        path.lineTo(right, bottom);
        path.lineTo(left, bottom);
        path.close();
        return path;
    }

    public static Path StandardPolyPath(float cx, float cy, float radius, int sides) {

        float start = -(float) (Math.PI / 2);
         return StandardPolyPath(cx,cy,radius,sides,start);
    }

    public static Path StandardPolyPath(float cx, float cy, float radius, int sides,float start) {
        if (sides < 3) return new Path();
        Path path = new Path();
        float a = (float) (Math.PI * 2) / (float) sides;

        path.moveTo(cx+ radius * (float) Math.cos(start), cy + radius * (float) Math.sin(start));
        //Log.d("p: ", " " + sides + "p: " + cx  + " : " + cy  +" ;" +radius*(float)Math.sin(start));

        for (int i = 1; i < sides; i++) {
            // Log.d("p: ", " "+sides+"p: " + cx +" : " + (float) radius * (float) Math.cos(a * i + start) + " : " + cy + " : " + (float) radius * (float) Math.sin(a * i + start));
            path.lineTo(cx + radius * (float) Math.cos(a * i + start), cy + radius * (float) Math.sin(a * i + start));
        }
        path.close();
        return path;
    }

    public static Path HeartPath(float left, float top, float right, float bottom) {
        Path path = new Path();
        float d = Math.min(right - left, bottom - top);
        path.moveTo(left, top + d / 4);
        path.quadTo(left, top, left + d / 4, top);
        path.quadTo(left + d / 2, top, left + d / 2, top + d / 4);
        path.quadTo(left + d / 2, top, left + d * 3 / 4, top);
        path.quadTo(left + d, top, left + d, top + d / 4);
        path.quadTo(left + d, top + d / 2, left + d * 3 / 4, top + d * 3 / 4);
        path.lineTo(left + d / 2, top + d);
        path.lineTo(left + d / 4, top + d * 3 / 4);
        path.quadTo(left, top + d / 2, left, top + d / 4);
        path.close();
        return path;
    }

    public static Path PhonePath(float left, float top, float right, float bottom, boolean isNo) {
        Path p = new Path();
        float w = right - left;
        float h = bottom - top;
        //make sure that height is half of width
        if (h > w / 2.f) h = w / 2.f;
        p.moveTo(left, top + h / 4);
        p.lineTo(left, top + h);
        p.lineTo(left + w / 4, top + h - h / 4);
        p.lineTo(left + w / 4, top + h / 2);
        p.lineTo(left + w - w / 4, top + h / 2);
        p.lineTo(left + w - w / 4, top + h - h / 4);
        p.lineTo(left + w, top + h);
        p.lineTo(left + w, top + h / 4);
        p.cubicTo(left + w, top, left, top, left, top + h / 4);
        if (isNo) {
            p.moveTo(left + w / 4 + w / 12, top + h - h / 4);
            p.lineTo(left + w - w / 4 - w / 12, top + h - h / 4);
            p.lineTo(left + w - w / 4 - w / 12, top + h);
            p.lineTo(left + w / 4 + w / 12, top + h);
            p.lineTo(left + w / 4 + w / 12, top + h - h / 4);
        }
        p.close();
        return p;
    }

    //yes button
    public static  Path YesButtonPath(float left, float top, float width, float height) {
        Path path = new Path();
        float scaleX = width / 360.f;
        float scale = height / 130.f;
        path.moveTo(left + 186 * scaleX, top + 108 * scale);
        path.cubicTo(left + 111 * scaleX, top + 121 * scale, left + 50 * scaleX, top + 130 * scale, left + 19 * scaleX, top + 103 * scale);
        path.cubicTo(left - 7 * scaleX, top + 74 * scale, left + 4.1f * scaleX, top + 32.58f * scale, left + 10.7f * scaleX, top + 25.98f * scale);
        path.cubicTo(left + 47 * scaleX, top - 26 * scale, left + 120.92f * scaleX, top + 25.32f * scale, left + 335 * scaleX, top + 28 * scale);
        path.cubicTo(left + 354 * scaleX, top + 29 * scale, left + 360 * scaleX, top + 91 * scale, left + 322 * scaleX, top + 90 * scale);
        path.cubicTo(left + 200 * scaleX, top + 103 * scale, left + 235 * scaleX, top + 100 * scale, left + 210 * scaleX, top + 104 * scale);
        path.close();
        return path;
    }

    public static   Path BottomRounded(float left, float top, float right, float bottom, float brx, float bry, float rx, float ry) {
        Path path = new Path();
        if (rx < 0) rx = 0;
        if (ry < 0) ry = 0;
        if (brx < 0) brx = 0;
        if (bry < 0) bry = 0;
        float width = right - left;
        float height = bottom - top;
        if (rx > width / 2) rx = width / 2;
        if (ry > height / 2) ry = height / 2;
        if (brx > width / 4) brx = width / 4;
        if (bry > height / 4) bry = height / 4;
        float bwidthMinusCorners = (width - (2 * brx));
        //noinspection UnusedAssignment
        float bheightMinusCorners = (height - (2 * bry));
        float widthMinusCorners = (width - (2 * rx));
        float heightMinusCorners = (height - (2 * ry));
        //r is releative
        path.moveTo(right, top + bry + bry);
        path.rLineTo(0, -bry);
        path.rQuadTo(0, -bry, -brx, -bry);//top-right corner
        path.rLineTo(-bwidthMinusCorners, 0);
        path.rQuadTo(-brx, 0, -brx, bry); //top-left corner
        path.rLineTo(0, bry);
        path.rQuadTo(0, 2 * bry + ry, rx, 2 * bry + ry);//bottom-left corner
        path.rLineTo(widthMinusCorners, 0);
        path.rQuadTo(rx, 0, rx, -(2 * bry + ry)); //bottom-right corner
        path.close();//Given close, last lineTo can be removed.

        return path;
    }

    public static   Path  Rounded(float left, float top, float right, float bottom, float rx, float ry) {
                Path path = new Path();
            if (rx < 0) rx = 0;
            if (ry < 0) ry = 0;
            float width = right - left;
            float height = bottom - top;
            if (rx > width/2) rx = width/2;
            if (ry > height/2) ry = height/2;
            float widthMinusCorners = (width - (2 * rx));
            float heightMinusCorners = (height - (2 * ry));

            path.moveTo(right, top + ry);
            path.rQuadTo(0, -ry, -rx, -ry);//top-right corner
            path.rLineTo(-widthMinusCorners, 0);
            path.rQuadTo(-rx, 0, -rx, ry); //top-left corner
            path.rLineTo(0, heightMinusCorners);
                path.rQuadTo(0, ry, rx, ry);//bottom-left corner
                path.rLineTo(widthMinusCorners, 0);
                path.rQuadTo(rx, 0, rx, -ry); //bottom-right corner

            path.rLineTo(0, -heightMinusCorners);

            path.close();//Given close, last lineTo can be removed.

            return path;
    }

    public static   Path NoButtonPath(float left, float top, float width, float height) {
        Path path = YesButtonPath(left, top, width, height);
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);
        matrix.postTranslate(width + 2 * left, 0);
        path.transform(matrix);
        return path;
    }

}
