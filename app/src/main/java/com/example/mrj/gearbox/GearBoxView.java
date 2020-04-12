package com.example.mrj.gearbox;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Mr.J on 2020/4/10.
 */
public class GearBoxView extends GLSurfaceView {
    private final String TAG = "GearBoxView";
    private GearBoxRenderer renderer;
    public GearBoxView(Context context, GearBoxRenderer renderer){
        super(context);
        this.renderer = renderer;
    }

    //这里将偏移数值降低
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320/2;
    private float previousX;
    private float previousY;
    private PointF pinchStartPoint = new PointF();
    private static final int TOUCH_NONE = 0;
    private static final int TOUCH_DRAG = 1;
    private static final int TOUCH_ZOOM = 2;
    private int touchMode = TOUCH_NONE;

    private float pinchStartZ = 0.0f;
    private float pinchStartDistance = 0.0f;
    private float pinchMoveX = 0.0f;
    private float pinchMoveY = 0.0f;

    private boolean isRotate = true;

    // zoom rate (larger > 1.0f > smaller)
    private float pinchScale = 1.0f;

    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_POINTER_DOWN:
            {
                Log.v(TAG,"你向下滑动");
                if (event.getPointerCount() >= 2) {
                    pinchStartDistance = getPinchDistance(event);
                    //pinchStartZ = pinchStartDistance;
                    if (pinchStartDistance > 50f) {
                        getPinchCenterPoint(event, pinchStartPoint);
                        previousX = pinchStartPoint.x;
                        previousY = pinchStartPoint.y;
                        touchMode = TOUCH_ZOOM;
                    }
                }
            }
            break;
            case MotionEvent.ACTION_MOVE:
            {
                Log.v(TAG,"ACTION_MOVE");

                if (touchMode == TOUCH_ZOOM && pinchStartDistance > 0) {
                    // on pinch
                    PointF pt = new PointF();

                    getPinchCenterPoint(event, pt);
                    pinchMoveX = pt.x - previousX;
                    pinchMoveY = pt.y - previousY;
                    float dx = pinchMoveX;
                    float dy = pinchMoveY;
                    previousX = pt.x;
                    previousY = pt.y;

                    if (isRotate) {
                        renderer.angleX += dx * TOUCH_SCALE_FACTOR;
                        renderer.angleY += dy * TOUCH_SCALE_FACTOR;
                    } else {
                        // change view point
                        renderer.positionX += dx * TOUCH_SCALE_FACTOR / 5;
                        renderer.positionY += dy * TOUCH_SCALE_FACTOR / 5;
                    }

                    pinchScale = getPinchDistance(event) / pinchStartDistance;
                    changeDistance(pinchScale);
//                    renderer.requestRedraw();
                    invalidate();
                }
            }
            break;
            case MotionEvent.ACTION_POINTER_UP:
            {
                Log.v(TAG,"ACTION_POINTER_UP");
                pinchScale=0;
                pinchStartZ=0;
                if (touchMode == TOUCH_ZOOM) {
                    touchMode = TOUCH_NONE;

                    pinchMoveX = 0.0f;
                    pinchMoveY = 0.0f;
                    pinchScale = 1.0f;
                    pinchStartPoint.x = 0.0f;
                    pinchStartPoint.y = 0.0f;
                    invalidate();
                }
            }
            break;
        }

        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
            {
                Log.v(TAG,"ACTION_DOWN");
                if (touchMode == TOUCH_NONE && event.getPointerCount() == 1) {
                    touchMode = TOUCH_DRAG;
                    previousX = event.getX();
                    previousY = event.getY();
                }
            }
            break;
            case MotionEvent.ACTION_MOVE:
            {
                Log.v(TAG,"ACTION_MOVE2");
                if (touchMode == TOUCH_DRAG) {
                    float x = event.getX();
                    float y = event.getY();

                    float dx = x - previousX;
                    float dy = y - previousY;
                    previousX = x;
                    previousY = y;

                    if (isRotate) {
                        renderer.angleX += dx * TOUCH_SCALE_FACTOR;
                        renderer.angleY += dy * TOUCH_SCALE_FACTOR;
                    } else {
                        // change view point
                        renderer.positionX += dx * TOUCH_SCALE_FACTOR / 5;
                        renderer.positionY += dy * TOUCH_SCALE_FACTOR / 5;
                    }
//                    stlRenderer.requestRedraw();
                    requestRender();
                }
            }
            break;
            case MotionEvent.ACTION_UP:
            {
                Log.v(TAG,"ACTION_UP");
                if (touchMode == TOUCH_DRAG) {
                    touchMode = TOUCH_NONE;
                    break;
                }
                renderer.setsclae();
            }
            break;
        }
        return true;
    }

    private void changeDistance(float scale) {
        Log.v("scale", String.valueOf(scale));
        renderer.scale = scale;
    }

    private float getPinchDistance(MotionEvent event) {
        float x=0;
        float y=0;
        try {
            x = event.getX(0) - event.getX(1);
            y = event.getY(0) - event.getY(1);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return android.util.FloatMath.sqrt(x * x + y * y);
    }

    private void getPinchCenterPoint(MotionEvent event, PointF pt) {
        pt.x = (event.getX(0) + event.getX(1)) * 0.5f;
        pt.y = (event.getY(0) + event.getY(1)) * 0.5f;
    }
}
