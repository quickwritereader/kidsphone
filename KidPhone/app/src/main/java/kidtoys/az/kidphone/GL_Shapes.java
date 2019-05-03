package kidtoys.az.kidphone;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;
/**
 * Created by Abdelrauf on 5/2/2019.
 */

public class GL_Shapes {

    public static final String U_MVP_MATRIX = "uMVPMatrix";
    private static final String A_COLOR = "a_Color";
    public static final String V_POSITION = "vPosition";

    public static class Triangle {

        // number of coordinates per vertex in this array
        static final int COORDS_PER_VERTEX = 3;
        private static final int COLOR_COMPONENT_COUNT = 3;
        private static final int STRIDE = (COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT) * 4;


        private final String vertexShaderCode =
                // This matrix member variable provides a hook to manipulate
                // the coordinates of the objects that use this vertex vertex_shader
                "uniform mat4 uMVPMatrix;" +
                        "attribute vec4 vPosition;" +
                        "attribute vec4 a_Color; varying vec4 v_Color;" +
                        "void main() {" +
                        "v_Color = a_Color;" +
                        // the matrix must be included as a modifier of gl_Position
                        // Note that the uMVPMatrix factor *must be first* in order
                        // for the matrix multiplication product to be correct.
                        "  gl_Position = uMVPMatrix * vPosition;" +
                        "}";
        private final String fragmentShaderCode =
                "precision mediump float;" +
                        "varying vec4 v_Color;" +
                        "void main() {" +
                        "  gl_FragColor = v_Color;" +
                        "}";
        private final int mProgram;
        float[] tableVerticesWithTriangles = { // Order of coordinates: X, Y, Z,R, G, B
                // Triangle Fan
                0.0f, 1f, 0.0f, 0.5f, 0.5f, 1f,
                -1f, -1f, 0.0f, 0.7f, 0.7f, 0.7f,
                1f, -1f, 0.0f, 0.7f, 0.7f, 0.7f
        };
        private FloatBuffer vertexBuffer;
        // Use to access and set the view transformation
        private int vPMatrixHandle;
        private int positionHandle;
        private int vertexCount = 3;

        public Triangle() {
            // initialize vertex byte buffer for shape coordinates
            ByteBuffer bb = ByteBuffer.allocateDirect(
                    // (number of coordinate values * 4 bytes per float)
                    tableVerticesWithTriangles.length * 4);
            // use the device hardware's native byte order
            bb.order(ByteOrder.nativeOrder());

            // create a floating point buffer from the ByteBuffer
            vertexBuffer = bb.asFloatBuffer();
            // add the coordinates to the FloatBuffer
            vertexBuffer.put(tableVerticesWithTriangles);
            // set the buffer to read the first coordinate
            vertexBuffer.position(0);

            int vertexShader = GL_Helper.compileVertexShader(vertexShaderCode);
            int fragmentShader = GL_Helper.compileFragmentShader(fragmentShaderCode);

            mProgram = GL_Helper.linkProgram(vertexShader, fragmentShader);
            if (BuildConfig.DEBUG) {
                GL_Helper.validateProgram(mProgram);
            }
        }

        public void setColor(FunnySurface.DotColor color) {
            GL_Helper.setColorFromDotColor(color, vertexBuffer, COORDS_PER_VERTEX);
            GL_Helper.setColorFromDotColor(color, vertexBuffer, COORDS_PER_VERTEX + 6);
        }

        public void draw(float[] mvpMatrix) {
            // Add program to OpenGL ES environment
            glUseProgram(mProgram);

            // get handle to vertex vertex_shader's vPosition member
            positionHandle = glGetAttribLocation(mProgram, V_POSITION);

            // Enable a handle to the triangle vertices
            glEnableVertexAttribArray(positionHandle);
            vertexBuffer.position(0);
            // Prepare the triangle coordinate data
            glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                    GL_FLOAT, false,
                    STRIDE, vertexBuffer);

            int aColorLocation = glGetAttribLocation(mProgram, A_COLOR);

            vertexBuffer.position(COORDS_PER_VERTEX);
            glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexBuffer);
            glEnableVertexAttribArray(aColorLocation);
            vPMatrixHandle = glGetUniformLocation(mProgram, U_MVP_MATRIX);

            // Pass the projection and view transformation to the vertex_shader
            glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0);

            // Draw the triangle
            glDrawArrays(GL_TRIANGLES, 0, vertexCount);

            // Disable vertex array
            glDisableVertexAttribArray(positionHandle);

            glDisableVertexAttribArray(aColorLocation);
        }
    }

    //lets use shaders for circle
    public static class Circle {


        private final String vertexShaderCode =

                "uniform mat4 uMVPMatrix;" +
                        "attribute vec4 vPosition;" +
                        "// transformed position\n" +
                        "varying highp vec4 pos;"+
                        "attribute vec4 a_Color; varying vec4 v_Color;" +
                        "void main() {" +
                        "v_Color = a_Color;" +
                        "  gl_Position = uMVPMatrix * vPosition;" +
                        "pos=vPosition;"+
                        "}";

        private final String fragmentShaderCode =
                "precision mediump float;" +
                        "varying vec4 v_Color;" +
                        "varying highp vec4 pos;"+
                        "void main() {" +
                        "float ab_kv= pos.x*pos.x+pos.y*pos.y;" +
                        "  if ( ab_kv <= 1.00 ){" +
                        " gl_FragColor = (ab_kv>0.25 )? vec4(v_Color[0],v_Color[1],v_Color[2],(1.2-ab_kv)*(1.2-ab_kv)) : v_Color ;" +
                        "} else {" +
                        "gl_FragColor = vec4(0,0,0,0);" +
                        "}"+
                        "}";
        private static final int COORDS_PER_VERTEX = 2;
        private int mProgram;

        private static final int COLOR_COMPONENT_COUNT = 3;
        private static final int STRIDE = (COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT) * 4;
        private float VERTEX_COORDINATES[] = {
                -1f, 1f,1f,1f,1f,   // top left r g b
                -1f, -1f,1f,1f,1f,   // bottom left
                1f, -1f,1f,1f,1f,   // bottom right
                1f, 1f,1f,1f,1f   // top right
        };
        private FloatBuffer vertexBuffer;
        private int positionHandle;
        private int vPMatrixHandle;
        private int vertexCount=4;


        public Circle() {

            int vertexShader = GL_Helper.compileVertexShader(vertexShaderCode);
            int fragmentShader = GL_Helper.compileFragmentShader(fragmentShaderCode);
            mProgram = GL_Helper.linkProgram(vertexShader, fragmentShader);
            if (BuildConfig.DEBUG) {
                GL_Helper.validateProgram(mProgram);
            }


            ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(VERTEX_COORDINATES.length * 4);
            vertexByteBuffer.order(ByteOrder.nativeOrder());
            vertexBuffer = vertexByteBuffer.asFloatBuffer();
            vertexBuffer.put(VERTEX_COORDINATES);
            vertexBuffer.position(0);

        }
        public void setColor(FunnySurface.DotColor color) {
            GL_Helper.setColorFromDotColor(color, vertexBuffer, COORDS_PER_VERTEX);
            GL_Helper.setColorFromDotColor(color, vertexBuffer, COORDS_PER_VERTEX + 5);
            GL_Helper.setColorFromDotColor(color, vertexBuffer, COORDS_PER_VERTEX + 15);
        }

        public void draw(float[] mvpMatrix) {
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
           glUseProgram(mProgram);

            // get handle to vertex vertex_shader's vPosition member
            positionHandle = glGetAttribLocation(mProgram, V_POSITION);

            // Enable a handle to the triangle vertices
            glEnableVertexAttribArray(positionHandle);
            vertexBuffer.position(0);
            // Prepare the triangle coordinate data
            glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                    GL_FLOAT, false,
                    STRIDE, vertexBuffer);

            int aColorLocation = glGetAttribLocation(mProgram, A_COLOR);

            vertexBuffer.position(COORDS_PER_VERTEX);
            glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexBuffer);
            glEnableVertexAttribArray(aColorLocation);
            vPMatrixHandle = glGetUniformLocation(mProgram, U_MVP_MATRIX);

            // Pass the projection and view transformation to the vertex_shader
            glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0);

            // Draw the triangle
            glDrawArrays(GL_TRIANGLE_FAN, 0, vertexCount);

            // Disable vertex array
            glDisableVertexAttribArray(positionHandle);

            glDisableVertexAttribArray(aColorLocation);
        }

    }

    public static class Rectangle {


        private final String vertexShaderCode =

                "uniform mat4 uMVPMatrix;" +
                        "attribute vec4 vPosition;" +
                        "// transformed position\n" +
                        "varying highp vec4 pos;"+
                        "attribute vec4 a_Color; varying vec4 v_Color;" +
                        "void main() {" +
                        "v_Color = a_Color;" +
                        "  gl_Position = uMVPMatrix * vPosition;" +
                        "pos=vPosition;"+
                        "}";

        private final String fragmentShaderCode =
                "precision mediump float;" +
                        "varying vec4 v_Color;" +
                        "varying highp vec4 pos;"+
                        "void main() {" +
                        "  gl_FragColor = v_Color ;" +
                        "}";
        private static final int COORDS_PER_VERTEX = 2;
        private int mProgram;

        private static final int COLOR_COMPONENT_COUNT = 3;
        private static final int STRIDE = (COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT) * 4;
        private float VERTEX_COORDINATES[] = {
                -0.95f, 0.95f,1f,1f,1f,   // top left r g b
                -1f, -0.95f,1f,1f,1f,   // bottom left
                0.95f, -0.95f,1f,1f,1f,   // bottom right
                0.95f, 0.95f,1f,1f,1f   // top right
        };
        private FloatBuffer vertexBuffer;
        private int positionHandle;
        private int vPMatrixHandle;
        private int vertexCount=4;


        public Rectangle() {

            int vertexShader = GL_Helper.compileVertexShader(vertexShaderCode);
            int fragmentShader = GL_Helper.compileFragmentShader(fragmentShaderCode);
            mProgram = GL_Helper.linkProgram(vertexShader, fragmentShader);
            if (BuildConfig.DEBUG) {
                GL_Helper.validateProgram(mProgram);
            }


            ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(VERTEX_COORDINATES.length * 4);
            vertexByteBuffer.order(ByteOrder.nativeOrder());
            vertexBuffer = vertexByteBuffer.asFloatBuffer();
            vertexBuffer.put(VERTEX_COORDINATES);
            vertexBuffer.position(0);

        }
        public void setColor(FunnySurface.DotColor color) {
            GL_Helper.setColorFromDotColor(color, vertexBuffer, COORDS_PER_VERTEX);
            GL_Helper.setColorFromDotColor(color, vertexBuffer, COORDS_PER_VERTEX + 5);
            GL_Helper.setColorFromDotColor(color, vertexBuffer, COORDS_PER_VERTEX + 15);
        }

        public void draw(float[] mvpMatrix) {
//            glEnable(GL_BLEND);
//            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glUseProgram(mProgram);

            // get handle to vertex vertex_shader's vPosition member
            positionHandle = glGetAttribLocation(mProgram, V_POSITION);

            // Enable a handle to the triangle vertices
            glEnableVertexAttribArray(positionHandle);
            vertexBuffer.position(0);
            // Prepare the triangle coordinate data
            glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                    GL_FLOAT, false,
                    STRIDE, vertexBuffer);

            int aColorLocation = glGetAttribLocation(mProgram, A_COLOR);

            vertexBuffer.position(COORDS_PER_VERTEX);
            glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexBuffer);
            glEnableVertexAttribArray(aColorLocation);
            vPMatrixHandle = glGetUniformLocation(mProgram, U_MVP_MATRIX);

            // Pass the projection and view transformation to the vertex_shader
            glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0);

            // Draw the triangle
            glDrawArrays(GL_TRIANGLE_FAN, 0, vertexCount);

            // Disable vertex array
            glDisableVertexAttribArray(positionHandle);

            glDisableVertexAttribArray(aColorLocation);
        }

    }

    public static class Romb {


        private final String vertexShaderCode =

                "uniform mat4 uMVPMatrix;" +
                        "attribute vec4 vPosition;" +
                        "// transformed position\n" +
                        "varying highp vec4 pos;"+
                        "attribute vec4 a_Color; varying vec4 v_Color;" +
                        "void main() {" +
                        "v_Color = a_Color;" +
                        "  gl_Position = uMVPMatrix * vPosition;" +
                        "pos=vPosition;"+
                        "}";

        private final String fragmentShaderCode =
                "precision mediump float;" +
                        "varying vec4 v_Color;" +
                        "varying highp vec4 pos;"+
                        "void main() {" +
                        "  gl_FragColor = v_Color ;" +
                        "}";
        private static final int COORDS_PER_VERTEX = 2;
        private int mProgram;

        private static final int COLOR_COMPONENT_COUNT = 3;
        private static final int STRIDE = (COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT) * 4;
        private float VERTEX_COORDINATES[] = {
                 1f, 0f,1f,1f,1f,
                0f, -1f,1f,1f,1f,
                -1f, 0f,1f,1f,1f,
                0f, 1f,1f,1f,1f
        };
        private FloatBuffer vertexBuffer;
        private int positionHandle;
        private int vPMatrixHandle;
        private int vertexCount=4;


        public Romb() {

            int vertexShader = GL_Helper.compileVertexShader(vertexShaderCode);
            int fragmentShader = GL_Helper.compileFragmentShader(fragmentShaderCode);
            mProgram = GL_Helper.linkProgram(vertexShader, fragmentShader);
            if (BuildConfig.DEBUG) {
                GL_Helper.validateProgram(mProgram);
            }


            ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(VERTEX_COORDINATES.length * 4);
            vertexByteBuffer.order(ByteOrder.nativeOrder());
            vertexBuffer = vertexByteBuffer.asFloatBuffer();
            vertexBuffer.put(VERTEX_COORDINATES);
            vertexBuffer.position(0);

        }
        public void setColor(FunnySurface.DotColor color) {
            GL_Helper.setColorFromDotColor(color, vertexBuffer, COORDS_PER_VERTEX);
            GL_Helper.setColorFromDotColor(color, vertexBuffer, COORDS_PER_VERTEX + 5);
            GL_Helper.setColorFromDotColor(color, vertexBuffer, COORDS_PER_VERTEX + 15);
        }

        public void draw(float[] mvpMatrix) {
//            glEnable(GL_BLEND);
//            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glUseProgram(mProgram);

            // get handle to vertex vertex_shader's vPosition member
            positionHandle = glGetAttribLocation(mProgram, V_POSITION);

            // Enable a handle to the triangle vertices
            glEnableVertexAttribArray(positionHandle);
            vertexBuffer.position(0);
            // Prepare the triangle coordinate data
            glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                    GL_FLOAT, false,
                    STRIDE, vertexBuffer);

            int aColorLocation = glGetAttribLocation(mProgram, A_COLOR);

            vertexBuffer.position(COORDS_PER_VERTEX);
            glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexBuffer);
            glEnableVertexAttribArray(aColorLocation);
            vPMatrixHandle = glGetUniformLocation(mProgram, U_MVP_MATRIX);

            // Pass the projection and view transformation to the vertex_shader
            glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0);

            // Draw the triangle
            glDrawArrays(GL_TRIANGLE_FAN, 0, vertexCount);

            // Disable vertex array
            glDisableVertexAttribArray(positionHandle);

            glDisableVertexAttribArray(aColorLocation);
        }

    }
}
