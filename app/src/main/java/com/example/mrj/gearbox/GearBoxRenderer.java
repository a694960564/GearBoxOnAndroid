package com.example.mrj.gearbox;

/**
 * Created by Mr.J on 2020/4/7.
 */
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GearBoxRenderer implements GLSurfaceView.Renderer{

    public void GearBoxRenderer(){}
    public void onSurfaceCreated(GL10 gl, EGLConfig config){
        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
    }
    public void onSurfaceChanged(GL10 gl, int width, int height){
        GLES20.glViewport(0, 0, width, height);
    }
    public void onDrawFrame(GL10 gl){
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }
}
