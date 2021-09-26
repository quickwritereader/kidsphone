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
                0.0f, 1f, 0f, 0.5f, 0.5f, 1f,
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
            for( int i=0;i<vertexCount;i++){
                GL_Helper.setColorFromDotColor(color, vertexBuffer,
                        COORDS_PER_VERTEX +  i*(COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT));

            }
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


        private static final String vertexShaderCode =

                "uniform mat4 uMVPMatrix;" +
                        "attribute vec4 vPosition;" +
                        "varying highp vec4 cpos;"+
                        "attribute vec4 a_Color; " +
                        "varying vec4 v_Color;" +
                        "void main() {" +
                        "v_Color = a_Color;" +
                        "  gl_Position = uMVPMatrix * vPosition;" +
                        "cpos=vPosition;"+
                        "}";

        private static final String fragmentShaderCode =
                "precision mediump float;" +
                        "varying vec4 v_Color;" +
                        "varying vec4 cpos;" +
                        "void main() {\n"+
                        "vec2 pos=cpos.xy;"+
                        "float d = length(pos);\n"+
                        "float r=0.85;\n"+
                        "float b=0.15;\n"+
                        "float c= smoothstep(r+b,r-b,d);\n"+
                        "gl_FragColor =vec4(c * v_Color.xyz,1.0)  ;\n"+
                        "}";
        private static final int COORDS_PER_VERTEX = 3;
        private int mProgram;

        private static final int COLOR_COMPONENT_COUNT = 3;
        private static final int STRIDE = (COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT) * 4;
        private float[] VERTEX_COORDINATES = {
                -1f, 1f, 0f, 1f, 1f, 1f,   // top left r g b
                -1f, -1f,0f, 1f, 1f, 1f,   // bottom left
                1f, -1f,0f, 1f, 1f, 1f,   // bottom right
                1f, 1f, 0f,1f, 1f, 1f   // top right
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
            for( int i=0;i<vertexCount;i++){
                GL_Helper.setColorFromDotColor(color, vertexBuffer,
                        COORDS_PER_VERTEX +  i*(COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT));

            }
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


        private static final String vertexShaderCode =

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

        private static final String fragmentShaderCode =
                "precision mediump float;" +
                        "varying vec4 v_Color;" +
                        "varying highp vec4 pos;"+
                        "void main() {" +
                        "  gl_FragColor = v_Color ;" +
                        "}";
        private static final int COORDS_PER_VERTEX = 3;
        private int mProgram;

        private static final int COLOR_COMPONENT_COUNT = 3;
        private static final int STRIDE = (COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT) * 4;
        private float[] VERTEX_COORDINATES = {
                -0.95f, 0.95f,0f, 1f, 1f, 1f,   // top left r g b
                -1f, -0.95f,0f, 1f, 1f, 1f,   // bottom left
                0.95f, -0.95f,0f, 1f, 1f, 1f,   // bottom right
                0.95f, 0.95f, 0f, 1f, 1f, 1f   // top right
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
            for( int i=0;i<vertexCount;i++){
                GL_Helper.setColorFromDotColor(color, vertexBuffer,
                        COORDS_PER_VERTEX +  i*(COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT));

            }
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


        private static final String vertexShaderCode =

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

        private static final String fragmentShaderCode =
                "precision mediump float;" +
                        "varying vec4 v_Color;" +
                        "varying highp vec4 pos;"+
                        "void main() {" +
                        "  gl_FragColor = v_Color ;" +
                        "}";
        private static final int COORDS_PER_VERTEX = 3;
        private int mProgram;

        private static final int COLOR_COMPONENT_COUNT = 3;
        private static final int STRIDE = (COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT) * 4;
        private float[] VERTEX_COORDINATES = {
                0f, 1f, 0f, 1f, 1f, 1f,
                -1f, 0f, 0f, 1f, 1f, 1f,
                0f, -1f, 0f, 1f, 1f, 1f,
                1f, 0f, 0f, 1f, 1f, 1f
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
            for( int i=0;i<vertexCount;i++){
                GL_Helper.setColorFromDotColor(color, vertexBuffer,
                        COORDS_PER_VERTEX +  i*(COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT));

            }
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


    public static class Poly {


        private static final String vertexShaderCode =
                "uniform mat4 uMVPMatrix;" +
                        "attribute vec4 vPosition;" +
                        "attribute vec4 a_Color; varying vec4 v_Color;" +
                        "void main() {" +
                        "v_Color = a_Color;" +
                        "  gl_Position = uMVPMatrix * vPosition;" +
                        "}";

        private static final String fragmentShaderCode =
                "precision mediump float;" +
                        "varying vec4 v_Color;" +
                        "void main() {" +
                        "  gl_FragColor = v_Color ;" +
                        "}";
        private static final int COORDS_PER_VERTEX = 3;
        private int mProgram;

        private static final int COLOR_COMPONENT_COUNT = 3;
        private static final int STRIDE = (COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT) * 4;
        private float[] VERTEX_COORDINATES;
        private FloatBuffer vertexBuffer;
        private int positionHandle;
        private int vPMatrixHandle;
        private int vertexCount;


        public Poly ( int n_sides ) {
            vertexCount=n_sides;//+1;
            VERTEX_COORDINATES = new float[vertexCount*(COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT)] ;

            float start=-(float) (Math.PI );
            if(n_sides%2==1){
                start= (float) (Math.PI / 2);
            }
            float a = (float) (Math.PI * 2) / (float) n_sides;

            for (int i = 0; i < n_sides; i++) {
                int t=i*(COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT);
                VERTEX_COORDINATES[t]=(float) Math.cos(a * i + start);
                VERTEX_COORDINATES[t+1]=(float) Math.sin(a * i + start) ;
                VERTEX_COORDINATES[t+2]= 0f ;
                VERTEX_COORDINATES[t+3]=1;
                VERTEX_COORDINATES[t+4]=1;
                VERTEX_COORDINATES[t+5]=1;
            }
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
            for( int i=0;i<vertexCount;i++){
                GL_Helper.setColorFromDotColor(color, vertexBuffer,
                        COORDS_PER_VERTEX +  i*(COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT));

            }
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

    public static class Heart {


        private static final String vertexShaderCode =

                "uniform mat4 uMVPMatrix;" +
                        "attribute vec4 vPosition;" +
                        "varying highp vec4 cpos;"+
                        "attribute vec4 a_Color; " +
                        "varying vec4 v_Color;" +
                        "void main() {" +
                        "v_Color = a_Color;" +
                        "  gl_Position = uMVPMatrix * vPosition;" +
                        "cpos=vPosition;"+
                        "}";

        private static final String fragmentShaderCode =
                "precision mediump float;\n"+
                        "varying vec4 v_Color;\n"+
                        "varying highp vec4 cpos;" +
                        "void main() {\n"+
                        "vec2 pos=cpos.xy;"+
                        "pos.x*=0.7;"+
                        "pos.y-= sqrt(abs(pos.x))* 0.7;"+
                        "float d = length(pos);\n"+
                        "float r=0.65;\n"+
                        "float b=0.05;\n"+
                        "float c= smoothstep(r+b,r-b,d);\n"+
                        "gl_FragColor =vec4(c * v_Color.xyz,1.0)  ;\n"+
                        "} ";
        private static final int COORDS_PER_VERTEX = 3;
        private int mProgram;

        private static final int COLOR_COMPONENT_COUNT = 3;
        private static final int STRIDE = (COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT) * 4;
        private float[] VERTEX_COORDINATES = {
                -1f, 1f, 0f,1f, 1f, 1f,   // top left r g b
                -1f, -1f,0f, 1f, 1f, 1f,   // bottom left
                1f, -1f,0f, 1f, 1f, 1f,   // bottom right
                1f, 1f, 0f, 1f, 1f, 1f   // top right
        };
        private FloatBuffer vertexBuffer;
        private int positionHandle;
        private int vPMatrixHandle;
        private int vertexCount=4;


        public Heart() {

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
            for( int i=0;i<vertexCount;i++){
                GL_Helper.setColorFromDotColor(color, vertexBuffer,
                        COORDS_PER_VERTEX +  i*(COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT));

            }
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

    public static class Star {


        private static final String vertexShaderCode =
                "uniform mat4 uMVPMatrix;" +
                        "attribute vec4 vPosition;" +
                        "attribute vec4 a_Color; varying vec4 v_Color;" +
                        "void main() {" +
                        "v_Color = a_Color;" +
                        "  gl_Position = uMVPMatrix * vPosition;" +
                        "}";

        private static final String fragmentShaderCode =
                "precision mediump float;" +
                        "varying vec4 v_Color;" +
                        "void main() {" +
                        "  gl_FragColor = v_Color ;" +
                        "}";
        private static final int COORDS_PER_VERTEX = 3;
        private int mProgram;

        private static final int COLOR_COMPONENT_COUNT = 3;
        private static final int STRIDE = (COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT) * 4;
        private float[] VERTEX_COORDINATES;
        private FloatBuffer vertexBuffer;
        private int positionHandle;
        private int vPMatrixHandle;
        private int vertexCount;


        public Star ( int spikes ) {
            vertexCount=spikes*2;//+1;
            VERTEX_COORDINATES = new float[vertexCount*(COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT)] ;

            float rot = (float) Math.PI / 2 * 3;
            float outerRadius=1.0f;
            float innerRadius=0.6f;
            float y;
            float step = (float) Math.PI / spikes;
            int t=0;
            for (int i = 0; i < spikes*2; i+=2) {

                VERTEX_COORDINATES[t]=(float) Math.cos(rot) * innerRadius;
                VERTEX_COORDINATES[t+1]=(float) Math.sin(rot) * innerRadius;
                VERTEX_COORDINATES[t+2]=1;
                VERTEX_COORDINATES[t+3]=0;
                VERTEX_COORDINATES[t+4]=1;
                VERTEX_COORDINATES[t+5]=1;
                rot += step;
                t=t+COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT;
                VERTEX_COORDINATES[t]=(float) Math.cos(rot) * outerRadius;
                VERTEX_COORDINATES[t+1]=(float) Math.sin(rot) * outerRadius;
                VERTEX_COORDINATES[t+3]=0;
                VERTEX_COORDINATES[t+4]=1;
                VERTEX_COORDINATES[t+5]=1;
                rot += step;
                t=t+COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT;
            }

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
            int t=COORDS_PER_VERTEX;
            for( int i=0;i<vertexCount;i++){
                GL_Helper.setColorFromDotColor(color, vertexBuffer,t);
                t+=   (COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT);
            }
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
    public static class GridDot {


        private static final String vertexShaderCode =

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

        private static final String fragmentShaderCode =
                "precision mediump float;" +
                        "varying vec4 v_Color;" +
                        "uniform vec2 u_tileXY;"+
                        "varying highp vec4 pos;"+
                        "void main() {" +
                        "vec2 ppos=pos.xy*vec2(0.5,0.5)*u_tileXY;"+
                        "vec2 grid = abs(fract(ppos) )  ;"+
                        "float line = min(grid.x, grid.y);" +

                        " gl_FragColor = vec4(  min(line, 0.55)* v_Color.xyz, 1.0);"+

                        "}";
        private static final int COORDS_PER_VERTEX = 2;
        private int mProgram;
        private static final String U_TILE_XY ="u_tileXY" ;
        private static final int COLOR_COMPONENT_COUNT = 3;
        private static final int STRIDE = (COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT) * 4;
        private float[] VERTEX_COORDINATES = {
                -1f, 1f, 1f, 1f, 1f,   // top left r g b
                -1f, -1f, 1f, 1f, 1f,   // bottom left
                1f, -1f, 1f, 1f, 1f,   // bottom right
                1f, 1f, 1f, 1f, 1f   // top right
        };
        private float  [] vecXY ={FunnyDisplay_GL.surfaceWidth,FunnyDisplay_GL.surfaceHeight};
        private FloatBuffer vertexBuffer;
        private int positionHandle;
        private int vPMatrixHandle;
        private int vertexCount=4;
        private int vecXYHandle;


        public GridDot() {

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

        public void setColorRgb(int r ,int g, int b){
            float rr=r/255.f;
            float gg=g/255.f;
            float bb=b/255.f;
            GL_Helper.setColorFromRGB(rr,gg,bb, vertexBuffer, COORDS_PER_VERTEX);
            GL_Helper.setColorFromRGB(rr,gg,bb, vertexBuffer, COORDS_PER_VERTEX + 5);
            GL_Helper.setColorFromRGB(rr,gg,bb, vertexBuffer, COORDS_PER_VERTEX + 10);
        }

        public void setColorRgbAll(int r ,int g, int b){
            float rr=r/255.f;
            float gg=g/255.f;
            float bb=b/255.f;
            GL_Helper.setColorFromRGB(rr,gg,bb, vertexBuffer, COORDS_PER_VERTEX);
            GL_Helper.setColorFromRGB(rr,gg,bb, vertexBuffer, COORDS_PER_VERTEX + 5);
            GL_Helper.setColorFromRGB(rr,gg,bb, vertexBuffer, COORDS_PER_VERTEX + 10);
            GL_Helper.setColorFromRGB(rr,gg,bb, vertexBuffer, COORDS_PER_VERTEX + 15);
        }

        public void setColor(FunnySurface.DotColor color) {
            GL_Helper.setColorFromDotColor(color, vertexBuffer, COORDS_PER_VERTEX);
            GL_Helper.setColorFromDotColor(color, vertexBuffer, COORDS_PER_VERTEX + 5);
            GL_Helper.setColorFromDotColor(color, vertexBuffer, COORDS_PER_VERTEX + 10);
            //GL_Helper.setColorFromDotColor(color, vertexBuffer, COORDS_PER_VERTEX + 15);
        }

        public void setGrid(int xCount,int yCount){
            vecXY[0]=xCount;
            vecXY[1]=yCount;
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

            vecXYHandle = glGetUniformLocation(mProgram, U_TILE_XY);

            //
            glUniform2fv(vecXYHandle, 1, vecXY, 0);
            // Draw the triangle
            glDrawArrays(GL_TRIANGLE_FAN, 0, vertexCount);

            // Disable vertex array
            glDisableVertexAttribArray(positionHandle);

            glDisableVertexAttribArray(aColorLocation);
        }

    }



    public static class GridDot2 {


        private static final String vertexShaderCode =

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

        private static final String fragmentShaderCode =
                "precision mediump float;" +
                        "varying vec4 v_Color;" +
                        "uniform vec2 u_tileXY;"+
                        "varying highp vec4 pos;"+
                        "vec2 tile(vec2 _st, vec2 _zoom){\n" +
                        "    _st *= _zoom;\n" +
                        "    return fract(_st);\n" +
                        "}" +
                        "float box(vec2 _st, vec2 _size, float _smoothEdges){\n" +
                        "    _size = vec2(0.5)-_size*0.5;\n" +
                        "    vec2 aa = vec2(_smoothEdges*0.5);\n" +
                        "    vec2 uv = smoothstep(_size,_size+aa,_st);\n" +
                        "    uv *= smoothstep(_size,_size+aa,vec2(1.0)-_st);\n" +
                        "    return uv.x*uv.y;\n" +
                        "}"+
                        "void main() {" +
                        "vec2 st = tile(pos.xy, vec2(0.5,0.5) * u_tileXY)  ;"+
                        //"float line = min(grid.x, grid.y);" +
                        " gl_FragColor = vec4(  vec3(box(st,vec2(0.8),0.01))* v_Color.xyz, 1.0);"+

                        "}";
        private static final int COORDS_PER_VERTEX = 3;
        private int mProgram;
        private static final String U_TILE_XY ="u_tileXY" ;
        private static final int COLOR_COMPONENT_COUNT = 3;
        private static final int STRIDE = (COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT) * 4;
        private float[] VERTEX_COORDINATES = {
                -1f, 1f, 0f,1f, 1f, 1f,   // top left r g b
                -1f, -1f,0f, 1f, 1f, 1f,   // bottom left
                1f, -1f,0f, 1f, 1f, 1f,   // bottom right
                1f, 1f, 0f,1f, 1f, 1f   // top right
        };
        private float  [] vecXY ={FunnyDisplay_GL.surfaceWidth,FunnyDisplay_GL.surfaceHeight};
        private FloatBuffer vertexBuffer;
        private int positionHandle;
        private int vPMatrixHandle;
        private int vertexCount=4;
        private int vecXYHandle;


        public GridDot2() {

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

        public void setColorRgb(int r ,int g, int b){
            float rr=r/255.f;
            float gg=g/255.f;
            float bb=b/255.f;
            int t=COORDS_PER_VERTEX;
            GL_Helper.setColorFromRGB(rr,gg,bb, vertexBuffer, COORDS_PER_VERTEX);
            GL_Helper.setColorFromRGB(rr,gg,bb, vertexBuffer, COORDS_PER_VERTEX +6);
            GL_Helper.setColorFromRGB(rr,gg,bb, vertexBuffer, COORDS_PER_VERTEX + 12);
            GL_Helper.setColorFromRGB(0,0,0, vertexBuffer, COORDS_PER_VERTEX + 18);
        }

        public void setColorRgbAll(int r ,int g, int b){
            float rr=r/255.f;
            float gg=g/255.f;
            float bb=b/255.f;
            int t=COORDS_PER_VERTEX;
            for( int i=0;i<vertexCount;i+=1){
                GL_Helper.setColorFromRGB(r,g, b, vertexBuffer,t);
                t+=   (COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT);
            }
        }

        public void setColor(FunnySurface.DotColor color) {
            int t=COORDS_PER_VERTEX;
            for( int i=0;i<vertexCount-1;i+=1){
                GL_Helper.setColorFromDotColor(color, vertexBuffer,t);
                t+=   (COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT);
            }
        }

        public void setGrid(int xCount,int yCount){
            vecXY[0]=xCount;
            vecXY[1]=yCount;
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

            vecXYHandle = glGetUniformLocation(mProgram, U_TILE_XY);

            //
            glUniform2fv(vecXYHandle, 1, vecXY, 0);
            // Draw the triangle
            glDrawArrays(GL_TRIANGLE_FAN, 0, vertexCount);

            // Disable vertex array
            glDisableVertexAttribArray(positionHandle);

            glDisableVertexAttribArray(aColorLocation);
        }

    }


}
