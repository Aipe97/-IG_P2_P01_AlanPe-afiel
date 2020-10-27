package com.example.murocastillopenafiel;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Castillo {
    //sombreado de vertices de la forma
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";


    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer; //guarda el orden de los vertices

    static final int COORDS_PER_VERTEX = 3; //Esto se refiere a los ejes de cada vertice, X Y Z
    //matriz de coordenadas
    static float poligonCoords[] = { //ok esto la verdadd es un desastre, pero en pantalla se ve decente asi que le dejare asi
                                    //tampoco especifique el orden por que se me hizo mas facil seguir el default
            -0.7f,  -0.5f, 0.0f, //bottom left
            -0.7f,  0.3f, 0.0f,
            -0.75f,  0.4f, 0.0f,
            -0.75f,  0.5f, 0.0f,
            -0.7f,  0.5f, 0.0f,
            -0.7f,  0.45f, 0.0f,
            -0.65f,  0.45f, 0.0f,
            -0.65f,  0.5f, 0.0f,
            -0.6f,  0.5f, 0.0f,
            -0.6f,  0.45f, 0.0f,
            -0.55f,  0.45f, 0.0f,
            -0.55f,  0.5f, 0.0f,
            -0.5f,  0.5f, 0.0f,
            -0.5f,  0.45f, 0.0f,
            -0.45f,  0.45f, 0.0f,
            -0.45f,  0.5f, 0.0f,
            -0.4f,  0.5f, 0.0f,
            -0.4f,  0.4f, 0.0f,
            -0.45f,  0.3f, 0.0f,
            -0.45f,  -0.5f, 0.0f,//bottom right of left turret
            -0.45f,  0.201f, 0.0f, //los extra decimales es por que por alguna razon no se ve con numeros cerrados
            -0.4f,  0.201f, 0.0f,
            -0.4f,  0.151f, 0.0f,
            -0.35f,  0.151f, 0.0f,
            -0.35f,  0.201f, 0.0f,
            -0.3f,  0.201f, 0.0f,
            -0.3f,  0.151f, 0.0f,
            -0.25f,  0.151f, 0.0f,
            -0.25f,  0.201f, 0.0f,
            -0.2f,  0.201f, 0.0f,
            -0.2f,  0.151f, 0.0f,
            -0.15f,  0.151f, 0.0f,
            -0.15f,  0.201f, 0.0f,
            -0.1f,  0.201f, 0.0f,
            -0.1f,  0.151f, 0.0f,
            -0.05f,  0.151f, 0.0f,
            -0.05f,  0.201f, 0.0f,
            -0.0f,  0.201f, 0.0f,
            -0.0f,  0.151f, 0.0f,
            0.05f,  0.151f, 0.0f,
            0.05f,  0.201f, 0.0f,
            0.1f,  0.201f, 0.0f,
            0.1f,  0.151f, 0.0f,
            0.15f,  0.151f, 0.0f,
            0.15f,  0.201f, 0.0f,
            0.2f,  0.201f, 0.0f,
            0.2f,  0.151f, 0.0f,
            0.25f,  0.151f, 0.0f,
            0.25f,  0.201f, 0.0f,
            0.3f,  0.201f, 0.0f,
            0.3f,  0.151f, 0.0f,
            0.35f,  0.151f, 0.0f,
            0.35f,  0.201f, 0.0f,
            0.4f,  0.201f, 0.0f,
            0.4f,  0.151f, 0.0f,
            0.45f,  0.151f, 0.0f, //fin de los merlons de en medio

            0.45f,  -0.5f, 0.0f,
            0.7f,  -0.5f, 0.0f,
            0.7f,  0.3f, 0.0f,//right wall of right turret

            0.75f,  0.4f, 0.0f,
            0.75f,  0.5f, 0.0f,
            0.7f,  0.5f, 0.0f,
            0.7f,  0.45f, 0.0f,
            0.65f,  0.45f, 0.0f,
            0.65f,  0.5f, 0.0f,
            0.6f,  0.5f, 0.0f,
            0.6f,  0.45f, 0.0f,
            0.55f,  0.45f, 0.0f,
            0.55f,  0.5f, 0.0f,
            0.5f,  0.5f, 0.0f,
            0.5f,  0.45f, 0.0f,
            0.45f,  0.45f, 0.0f,
            0.45f,  0.5f, 0.0f,
            0.4f,  0.5f, 0.0f,
            0.4f,  0.4f, 0.0f,
            0.45f,  0.3f, 0.0f,
            0.45f,  -0.5f, 0.0f //bottom left of right turret
           };

    //Vamos a ponerle un color a nuestro tringulo orden de color rojos, verdes, azules y alphas
    float color[] = {1.0f, 1.0f, 1.0f, 0.0f};
    private final int mProgram;

    public Castillo() {
        ByteBuffer bb = ByteBuffer.allocateDirect(
                poligonCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(poligonCoords);
        vertexBuffer.position(0);

        //Carga del sombrado en la generacion del dibujo
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);
        // creamos un programa vacio OpenGL ES
        mProgram = GLES20.glCreateProgram();

        // añadimos el sombreado de vertices al programa
        GLES20.glAttachShader(mProgram, vertexShader);

        // añadimos el sombreado de los fragmentos al programa
        GLES20.glAttachShader(mProgram, fragmentShader);

        // Creamos el programa ejecutable de OpenGL ES
        GLES20.glLinkProgram(mProgram);
    }

    private int positionHandle;
    private int colorHandle;

    private final int vertexCount = poligonCoords.length / COORDS_PER_VERTEX; //contador de vertices
    //Espacio de la matriz para los vertices
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes por vertice

    public void draw() {
        // añadimos el programa al entorno de opengl
        GLES20.glUseProgram(mProgram);

        // obtenemos el identificador de los sombreados del los vertices a vPosition
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // habilitamos el manejo de los vertices del triangulo
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Preparamos los datos de las coordenadas del triangulo
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // Obtenemos el identificador del color del sombreado de los fragmentos
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Establecemos el color para el dibujo del triangulo
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        // SE dibuja el triangulo
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, vertexCount); // Encontre GL_lINE_LOOP en google, al parecer es para hacer un poligono con una linea, no se puede rellenar la figura pero la imagen muestra nunca especifico que la figura tenia que ir rellena de color

        // Deshabilitamos el arreglo de los vertices
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}



