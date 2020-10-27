package com.example.murocastillopenafiel;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer renderer;

    public MyGLSurfaceView(Context context) {
        super(context);

        //Creacion de la visualizacion de OpenGL
        setEGLContextClientVersion(2);

        renderer = new MyGLRenderer();

        //Se asigna en renderizado para que se dibujen las figuras en GLSurfaceVIew
        setRenderer(renderer);

    }
}
