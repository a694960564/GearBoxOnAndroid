package com.example.mrj.gearbox;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Mr.J on 2020/4/10.
 */
public class GearBoxView extends GLSurfaceView {
    private final String TAG = "GearBoxView";
    public GearBoxView(Context context){
        super(context);
    }
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_POINTER_DOWN:
            {
                Log.v(TAG,"你向下滑动");
            }
            break;
            case MotionEvent.ACTION_MOVE:
            {
                Log.v(TAG,"ACTION_MOVE");
            }
            break;
            case MotionEvent.ACTION_POINTER_UP:
            {
                Log.v(TAG,"ACTION_POINTER_UP");
            }
            break;
        }

        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
            {
                Log.v(TAG,"ACTION_DOWN");
            }
            break;
            case MotionEvent.ACTION_MOVE:
            {
                Log.v(TAG,"ACTION_MOVE2");
            }
            break;
            case MotionEvent.ACTION_UP:
            {
                Log.v(TAG,"ACTION_UP");
            }
            break;
        }
        return true;
    }
}
