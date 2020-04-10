package com.example.mrj.gearbox;

/**
 * Created by Mr.J on 2020/4/7.
 */
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;

import com.example.mrj.gearbox.object.Model;
import com.example.mrj.gearbox.util.ShaderBuilder;
import com.example.mrj.gearbox.util.TextReader;
import com.example.mrj.gearbox.R;
import java.io.File;

public class GearBoxRenderer implements GLSurfaceView.Renderer{
    private static final String TAG = "AirHockeyRenderer";
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewPorjectionMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];
    private int ID;
    private Context context;
    private Model gear;
    public GearBoxRenderer(Context context){
        this.context = context;
    }
    public void onSurfaceCreated(GL10 gl, EGLConfig config){
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        ID = ShaderBuilder.buildProgram(
                TextReader.readTextFileFromResource(context, R.raw.vertex_shader),
                TextReader.readTextFileFromResource(context, R.raw.fragment_shader)
        );
        GLES20.glUseProgram(ID);
        gear = new Model("Gear",R.raw.center_gear,context, ID);
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 50f, 0f, 0f, 0f, 0f, 1f, 0f);
    }
    public void onSurfaceChanged(GL10 gl, int width, int height){
        GLES20.glViewport(0, 0, width, height);
        Matrix.perspectiveM(projectionMatrix, 0, 45, (float)width / (float)height, 1f, 1000f);
    }
    private float a = 0f;
    public void onDrawFrame(GL10 gl){
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        Matrix.setLookAtM(viewMatrix, 0, 200f * (float)Math.sin(Math.toRadians(a)), 0f, 200f * (float)Math.cos(Math.toRadians(a)), 0f, 0f, 0f, 0f, 1f, 0f);
        Matrix.multiplyMM(viewPorjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        gear.draw(viewPorjectionMatrix);
        a+=0.1;
    }
}
