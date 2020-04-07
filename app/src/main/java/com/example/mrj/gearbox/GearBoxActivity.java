package com.example.mrj.gearbox;

import android.opengl.GLSurfaceView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class GearBoxActivity extends ActionBarActivity {
    private GLSurfaceView glSurfaceView;
    private static final GearBoxRenderer gearBoxRenderer = new GearBoxRenderer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gear_box);
        glSurfaceView = (GLSurfaceView)findViewById(R.id.GearBoxGLView);
        glSurfaceView.setRenderer(gearBoxRenderer);
    }
}
