package com.example.murocastillopenafiel;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Castillo mCastillo;
    private Ventana1 mVentana1;
    private Ventana2 mVentana2;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //Se asigna el color del fondo de la palicacion
        GLES20.glClearColor(0.0f, 51/255f, 102/255f, 0.0f);

        mCastillo = new Castillo();
        mVentana1 = new Ventana1();
        mVentana2 = new Ventana2();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //dibujar el color del fondo
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        mCastillo.draw();
        mVentana1.draw();
        mVentana2.draw();
    }

    //funcion para cargar el sombreado de una figura
    public static int loadShader(int type, String shaderCode){

        // creamos el tipo de sombreado de los vertices (GLES20.GL_VERTEX_SHADER)
        // o los tipos de sombreado de los fragmentos (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // Añadimos el codigo fuente a los sombreados y lo compilamos
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        //La funcion regresara el valor de los sombreados generados
        return shader;
    }
}
