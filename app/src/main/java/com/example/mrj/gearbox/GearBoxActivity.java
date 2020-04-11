package com.example.mrj.gearbox;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.mrj.gearbox.util.ShaderBuilder;
import com.example.mrj.gearbox.util.TextReader;

public class GearBoxActivity extends ActionBarActivity {
    private GearBoxView glSurfaceView;
    private GearBoxRenderer gearBoxRenderer;
    private FrameLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gear_box);

        gearBoxRenderer =  new GearBoxRenderer(this);
        relativeLayout = (FrameLayout)findViewById(R.id.GearBoxGLView);
        glSurfaceView = new GearBoxView(this);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(gearBoxRenderer);
        relativeLayout.addView(glSurfaceView);
    }
    protected void onResume(){
        super.onResume();
        if(glSurfaceView != null){
            glSurfaceView.onResume();
        }
    }
    protected void onPause(){
        super.onPause();
        if(glSurfaceView != null){
            glSurfaceView.onPause();
        }
    }
}
