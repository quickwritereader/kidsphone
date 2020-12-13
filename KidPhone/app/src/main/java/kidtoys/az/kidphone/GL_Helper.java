package kidtoys.az.kidphone;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import static android.content.ContentValues.TAG;
import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glValidateProgram;

/**
 * Created by abdelrauf on 5/2/2019.
 */

public class GL_Helper {

    private final static String TAG="GL_I";


    public static String readTextFileFromResource(Context context, int resourceId) {
        StringBuilder body = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not open resource: " + resourceId, e);
        } catch (Resources.NotFoundException nfe) {
            throw new RuntimeException("Resource not found: " + resourceId, nfe);
        }
        return body.toString();
    }

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type, String shaderCode) {
        final int shaderObjectId = glCreateShader(type);
        if (shaderObjectId == 0) {
            return 0;
        }
        glShaderSource(shaderObjectId, shaderCode);
        glCompileShader(shaderObjectId);
         int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            // If it failed, delete the shader object.

            Log.e(TAG,  glGetShaderInfoLog(shaderObjectId));
            glDeleteShader(shaderObjectId);
            return 0;
        }
        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        final int programObjectId = glCreateProgram();
        if (programObjectId == 0) {
            Log.e(TAG, "Could not create new program");
            return 0;
        }
        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);
        glLinkProgram(programObjectId);
        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);

        if (linkStatus[0]  != GLES20.GL_TRUE) {

            if (BuildConfig.DEBUG) {

            Log.e(TAG, "Could not link program: ");
            Log.e(TAG, GLES20.glGetProgramInfoLog(programObjectId));
            }
            GLES20.glDeleteProgram(programObjectId);
            return 0;
        }
        return programObjectId;
    }

    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);
        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);

            Log.e(TAG, "Results of validating program: " + validateStatus[0]  );
            //Object ret=glGetProgramInfoLog(programObjectId);

              //  Log.e(TAG, ret.toString());
              //android ndk modified utf-8 bug
              //ignored
        return validateStatus[0] != 0;
    }

    public static void setColorFromDotColor(FunnySurface.DotColor color, FloatBuffer setArr, int start_index) {

        switch (color) {
            case Red:
                setArr.put(start_index, 1f);
                setArr.put(start_index + 1, 0f);
                setArr.put(start_index + 2, 0);
                break;
            case Blue:
                setArr.put(start_index, 0);
                setArr.put(start_index + 1, 0);
                setArr.put(start_index + 2, 1f);
                break;
            case White:
                setArr.put(start_index, 1f);
                setArr.put(start_index + 1, 1f);
                setArr.put(start_index + 2, 1f);
                break;
            case Green:
                setArr.put(start_index, 0);
                setArr.put(start_index + 1, 1f);
                setArr.put(start_index + 2, 0);
                break;
            case Yellow:
                setArr.put(start_index, 1f);
                setArr.put(start_index + 1, 1f);
                setArr.put(start_index + 2, 0);
                break;
            case Pink:
                setArr.put(start_index, 1f);
                setArr.put(start_index + 1, 0x6e / 255f);
                setArr.put(start_index + 2, 0xc7 / 255f);
                break;
            case Orange:
                setArr.put(start_index, 1f);
                setArr.put(start_index + 1, 0x45 / 255f);
                setArr.put(start_index + 2, 0);
                break;
            case Black:
                setArr.put(start_index, 0);
                setArr.put(start_index + 1, 0);
                setArr.put(start_index + 2, 0);
                break;
//            case Magenta:
//                setArr.put(start_index, 1f);
//                setArr.put(start_index + 1, 0);
//                setArr.put(start_index + 2, 1f);
//                break;
            default:
        }
    }



    public static void setColorFromRGB(float r,float g,float b, FloatBuffer setArr, int start_index) {

                setArr.put(start_index, r);
                setArr.put(start_index + 1, g);
                setArr.put(start_index + 2, b);
    }

}
