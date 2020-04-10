package com.example.mrj.gearbox;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.example.mrj.gearbox.util.ShaderBuilder;
import com.example.mrj.gearbox.util.TextReader;

public class GearBoxActivity extends ActionBarActivity {
    private GLSurfaceView glSurfaceView;
    private GearBoxRenderer gearBoxRenderer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gear_box);

        gearBoxRenderer =  new GearBoxRenderer(this);
        glSurfaceView = (GLSurfaceView)findViewById(R.id.GearBoxGLView);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(gearBoxRenderer);
    }
}
