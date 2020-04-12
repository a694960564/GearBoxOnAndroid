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

import com.example.mrj.gearbox.WidgetListener.SeekBarListener;
import com.example.mrj.gearbox.object.GearBoxObject;
import com.example.mrj.gearbox.object.Model;
import com.example.mrj.gearbox.util.ShaderBuilder;
import com.example.mrj.gearbox.util.TextReader;
import com.example.mrj.gearbox.R;
import java.io.File;

import android.util.Log;
import android.widget.SeekBar;

public class GearBoxRenderer implements GLSurfaceView.Renderer{
    private static final String TAG = "AirHockeyRenderer";
    //外部控制
    public float scale = 1.0f;
    //当前展示
    private float scale_rember=1.0f;
    //当前固定
    private float scale_now=1.0f;
    public float angleX;
    public float angleY;
    public float positionX = 0f;
    public float positionY = 0f;
    /**
     * 固定缩放比例
     */
    public void setsclae(){
        scale_now=scale_rember;
        scale_rember=1.0f;
        scale=1.0f;
    }

    int width, height;
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewPorjectionMatrix = new float[16];
    private float[] cameraPos = new float[3];
    private int ID;
    private Context context;
    public GearBoxObject gearBox;
    private SeekBar seekBar;
    public GearBoxRenderer(Context context, SeekBar seekBar){
        this.context = context;
        this.seekBar = seekBar;
    }
    public void onSurfaceCreated(GL10 gl, EGLConfig config){
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        ID = ShaderBuilder.buildProgram(
                TextReader.readTextFileFromResource(context, R.raw.vertex_shader),
                TextReader.readTextFileFromResource(context, R.raw.fragment_shader)
        );
        GLES20.glUseProgram(ID);
        gearBox = new GearBoxObject(context, ID);
        seekBar.setOnSeekBarChangeListener(new SeekBarListener(gearBox));
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 50f, 0f, 0f, 0f, 0f, 1f, 0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    }
    public void onSurfaceChanged(GL10 gl, int width, int height){
        GLES20.glViewport(0, 0, width, height);
        this.width = width;
        this.height = height;
    }

    private float a = 0f;
    private float fov = 45.0f;
    public void onDrawFrame(GL10 gl){
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
//        cameraPos[0] = 500f * (float) Math.sin(Math.toRadians(a));
//        cameraPos[1] = 0f;
//        cameraPos[2] = -500f * (float) Math.cos(Math.toRadians(a));
        cameraPos[0] = 300f;
        cameraPos[1] = 0f;
        cameraPos[2] = -400f;
        scale_rember = scale_now * scale;
//        Log.v("onDrawFrame",String.valueOf(scale_rember));
        Matrix.perspectiveM(projectionMatrix, 0, 45 + scale_rember, (float)width / (float)height, 1f, 1000f);
        Matrix.setLookAtM(viewMatrix, 0, cameraPos[0], cameraPos[1], cameraPos[2], 0f, 0f, 0f, 0f, 1f, 0f);
        Matrix.multiplyMM(viewPorjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        gearBox.setViewPos(cameraPos);
        gearBox.draw(viewPorjectionMatrix);
//        a+=1;
    }
}
