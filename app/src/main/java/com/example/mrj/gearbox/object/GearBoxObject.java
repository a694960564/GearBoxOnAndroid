package com.example.mrj.gearbox.object;

import android.content.Context;
import android.opengl.GLES20;

import com.example.mrj.gearbox.R;

/**
 * Created by Mr.J on 2020/4/10.
 */
public class GearBoxObject {
    private int u_ViewPos, u_LightPos;
    private Model centerGear, planetGear, bearing, gearRing, box, planetAxis, outPut;
    private float[] viewPos;
    private final float a = 58.47f, b = 33.75f, c = 67.5f;
    private float centerStep = 0.6f, globalStep = 0.1f, planetStep = 0.15f;
    private float globalSum = 0.0f, planetSum = 0.0f;

    public GearBoxObject(Context context, int ID){
        centerGear = new Model("centerGear", R.raw.center_gear,context, ID, 1, 0, 0);
        planetGear = new Model("planetGear",R.raw.planet_gear,context, ID, 0, 1, 0);
        planetGear.translate(a, -b, 0f);
        bearing = new Model("bearing",R.raw.bearing,context, ID, 0.5f, 0.5f, 0.5f);
        bearing.translate(a, -b, 30f);
        planetAxis = new Model("planetAxis", R.raw.planet_axis, context, ID,0, 1f, 0);
        outPut = new Model("outPut", R.raw.out_put, context, ID, 0.75f, 0.75f, 0.75f);
        outPut.translate(0, 0, 56.8f);
        box = new Model("box",R.raw.box,context, ID, 0.75f, 0.75f, 0.75f);
        gearRing = new Model("gearRing", R.raw.gear_ring,context, ID, 0.5f, 0.5f, 0.5f);
        gearRing.translate(0,0,30);
        u_ViewPos = GLES20.glGetUniformLocation(ID, "u_ViewPos");
        u_LightPos = GLES20.glGetUniformLocation(ID, "u_LightPos");
        final float v = 8;//调速
        centerStep *= v;
        globalStep *= v;
        planetStep *= v;
    }
    public void SetValue(int v){
        centerStep =   0.6f * v;
        globalStep =   0.1f * v;
        planetStep =   0.15f * v;
    }
    public void setViewPos(float[] camera){
        viewPos = camera;
    }
    //45:90:225
    //36:72:180
    private float aa = 0.0f;
    public void draw(float[] viewProjectionMatrix){
        GLES20.glUniform3f(u_ViewPos, viewPos[0], viewPos[1], viewPos[2]);
        GLES20.glUniform3f(u_LightPos, 100.0f * (float) Math.sin(Math.toRadians(aa)), 0, 100.0f * (float) Math.cos(Math.toRadians(aa)));
        aa+=0.5;
        box.draw(viewProjectionMatrix);
        gearRing.draw(viewProjectionMatrix);

        centerGear.rotate(centerStep, 0, 0, 1);
        centerGear.draw(viewProjectionMatrix);

        outPut.rotate(globalStep, 0, 0, 1);
        outPut.draw(viewProjectionMatrix);

        float x1 = c * (float)Math.sin(Math.toRadians(60 + globalSum)) - a;
        float y1 = b - c * (float)Math.cos(Math.toRadians(60 + globalSum));
        planetGear.IdentityTranslate(a, -b, 0f);
        planetGear.translate(x1, y1, 0);
        planetGear.rotate(5 - planetSum, 0, 0, 1);
        planetGear.draw(viewProjectionMatrix);
        float x2 =  a - c * (float)Math.sin(Math.toRadians(60 - globalSum));
        float y2 =  b - c * (float)Math.cos(Math.toRadians(60 - globalSum));
        planetGear.IdentityTranslate(-a, -b, 0f);
        planetGear.translate(x2, y2, 0);
        planetGear.rotate(5 - planetSum, 0, 0, 1);
        planetGear.draw(viewProjectionMatrix);
        float x3 =  -c * (float)Math.sin(Math.toRadians(globalSum));
        float y3 =  c * (float)Math.cos(Math.toRadians(globalSum)) - c;
        planetGear.IdentityTranslate(0, c, 0);
        planetGear.translate(x3, y3, 0);
        planetGear.rotate(5 - planetSum, 0, 0, 1);
        planetGear.draw(viewProjectionMatrix);

        planetAxis.IdentityTranslate(a, -b, 10);//1
        planetAxis.translate(x1, y1, 0);
        planetAxis.draw(viewProjectionMatrix);
        planetAxis.IdentityTranslate(-a, -b, 10);//2
        planetAxis.translate(x2, y2, 0);
        planetAxis.draw(viewProjectionMatrix);
        planetAxis.IdentityTranslate(0, c, 10);//3
        planetAxis.translate(x3, y3, 0);
        planetAxis.draw(viewProjectionMatrix);

        bearing.IdentityTranslate(a, -b, 10f);
        bearing.translate(x1, y1, 0);
        bearing.rotate(-planetSum, 0, 0, 1);
        bearing.draw(viewProjectionMatrix);
        bearing.IdentityTranslate(-a, -b, 10f);
        bearing.translate(x2, y2, 0);
        bearing.rotate(-planetSum, 0, 0, 1);
        bearing.draw(viewProjectionMatrix);
        bearing.IdentityTranslate(0, c, 10f);
        bearing.translate(x3, y3, 0);
        bearing.rotate(planetSum, 0, 0, 1);
        bearing.draw(viewProjectionMatrix);
        planetSum += planetStep;
        globalSum += globalStep;
    }
}
