package com.example.mrj.gearbox.object;

import android.content.Context;
import android.opengl.GLES20;

import com.example.mrj.gearbox.R;

/**
 * Created by Mr.J on 2020/4/10.
 */
public class GearBoxObject {
    private int u_ViewPos, u_LightPos;
    private Model centerGear, planetGear, bearing, gearRing, box;
    private float[] viewPos;
    public GearBoxObject(Context context, int ID){
        centerGear = new Model("centerGear", R.raw.center_gear,context, ID, 1, 0, 0);
        planetGear = new Model("planetGear",R.raw.planet_gear,context, ID, 0, 1, 0);
        planetGear.translate(56.57f, -36.82f, 0f);
        bearing = new Model("bearing",R.raw.bearing,context, ID, 0, 0, 1);
        bearing.translate(56.57f, -36.82f, 20f);
        box = new Model("box",R.raw.box,context, ID, 0, 1, 0);
        gearRing = new Model("gearRing", R.raw.gear_ring,context, ID, 0.5f, 0.5f, 0.5f);
        gearRing.translate(0,0,10);
        u_ViewPos = GLES20.glGetUniformLocation(ID, "u_ViewPos");
        u_LightPos = GLES20.glGetUniformLocation(ID, "u_LightPos");
    }
    public void setViewPos(float[] camera){
        viewPos = camera;
    }
    //45:90:225
    //36:72:180
    private float step = 2f, totalStep = 1f;
    public void draw(float[] viewPorjectionMatrix){
        GLES20.glUniform3f(u_ViewPos, viewPos[0], viewPos[1], viewPos[2]);
        GLES20.glUniform3f(u_LightPos,100.0f*(float)Math.sin(Math.toRadians(totalStep)), 0, 100.0f*(float)Math.cos(Math.toRadians(totalStep)));

        box.draw(viewPorjectionMatrix);
        gearRing.draw(viewPorjectionMatrix);

        centerGear.rotate(step, 0, 0, 1);
        centerGear.draw(viewPorjectionMatrix);

        planetGear.IdentityTranslate(56.57f, -36.82f, 0f);
        planetGear.rotate(-totalStep, 0, 0, 1);
        planetGear.draw(viewPorjectionMatrix);
        planetGear.IdentityTranslate(-56.57f, -36.82f, 0f);
        planetGear.rotate(-totalStep, 0, 0, 1);
        planetGear.draw(viewPorjectionMatrix);
        planetGear.IdentityTranslate(0, 67.50f, 0);
        planetGear.rotate(5 - totalStep, 0, 0, 1);
        planetGear.draw(viewPorjectionMatrix);

        bearing.IdentityTranslate(56.57f, -36.82f, 10f);
        bearing.rotate(-totalStep, 0, 0, 1);
        bearing.draw(viewPorjectionMatrix);
        bearing.IdentityTranslate(-56.57f, -36.82f, 10f);
        bearing.rotate(-totalStep, 0, 0, 1);
        bearing.draw(viewPorjectionMatrix);
        bearing.IdentityTranslate(0, 67.50f, 10f);
        bearing.rotate(5 - totalStep, 0, 0, 1);
        bearing.draw(viewPorjectionMatrix);
        ++totalStep;
    }
}
