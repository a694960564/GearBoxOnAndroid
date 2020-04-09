package com.example.mrj.gearbox.object;

/**
 * Created by Mr.J on 2020/4/7.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mrj.gearbox.R;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private String TAG = "Model:";
    private int ID;
    private int numofvertex;
    private String path;
    private float[] color;
    private float[] vertex = null;
    private float[] normal = null;
    private int triangle_size = 0;
    private float[] modelMatrix = new float[16];
    private float maxX;
    private float maxY;
    private float maxZ;
    private float minX;
    private float minY;
    private float minZ;
    private List<Float> normalList;
    private void adjustMaxMin(float x, float y, float z) {
        if (x > maxX) {
            maxX = x;
        }
        if (y > maxY) {
            maxY = y;
        }
        if (z > maxZ) {
            maxZ = z;
        }
        if (x < minX) {
            minX = x;
        }
        if (y < minY) {
            minY = y;
        }
        if (z < minZ) {
            minZ = z;
        }
    }
    private boolean readerSTL(final byte[] stlBytes, final Context context){
        maxX = Float.MIN_VALUE;
        maxY = Float.MIN_VALUE;
        maxZ = Float.MIN_VALUE;
        minX = Float.MAX_VALUE;
        minY = Float.MAX_VALUE;
        minZ = Float.MAX_VALUE;
        normalList = new ArrayList<Float>();
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(R.string.stl_load_progress_title);
        progressDialog.setMax(0);
        progressDialog.setMessage(context.getString(R.string.stl_load_progress_message));
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.show();
        final AsyncTask<byte[], Integer, float[]> task = new AsyncTask<byte[], Integer, float[]>() {
            float[] processText(String stlText) throws Exception{
                normalList.clear();
                String[] stlLines = stlText.split("\n");
                triangle_size = (stlLines.length - 2) / 7;
                vertex = new float[triangle_size * 9];
                normal = new float[triangle_size * 9];
                progressDialog.setMax(stlLines.length);
                int normal_num = 0;
                int vertex_num = 0;
                for (int i = 0;i < stlLines.length; i++){
                    String string = stlLines[i].trim();//清除空格
                    if(string.startsWith("facet normal ")){
                        string = string.replaceFirst("facet normal ", "");
                        String[] normalValue = string.split(" ");
                        for (int n=0;n<3;n++){
                            normal[normal_num++] = Float.parseFloat(normalValue[0]);
                            normal[normal_num++] = Float.parseFloat(normalValue[1]);
                            normal[normal_num++] = Float.parseFloat(normalValue[2]);
                        }
                    }
                    if(string.startsWith("vertex ")){
                        string = string.replaceFirst("vertex ", "");
                        String[] vertexValue = string.split(" ");
                        float x = Float.parseFloat(vertexValue[0]);
                        float y = Float.parseFloat(vertexValue[1]);
                        float z = Float.parseFloat(vertexValue[2]);
                        adjustMaxMin(x, y, z);
                        vertex[vertex_num++] = x;
                        vertex[vertex_num++] = y;
                        vertex[vertex_num++] = z;
                    }
                    if(i % (stlLines.length / 50) == 0){
                        publishProgress(i);
                    }
                }
                return vertex;
            }
            @Override
            protected float[] doInBackground(byte[]... stlBytes) {
                float[] processResult = null;
                try{
                    Log.v(TAG,"读取中");
                    processResult = processText(new String(stlBytes[0]));
                }catch(Exception e){
                }
                if(processResult != null && processResult.length > 0
                        && normal !=null && normal.length > 0){
                    return processResult;
                }
                return processResult;
            }

            @Override
            protected void onProgressUpdate(Integer...values){
                progressDialog.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(float[] vertexList){

            }
        };

        try{
            task.execute(stlBytes);
        }catch(Exception e){
            return false;
        }
        return true;
    }
    public Model(int id,  String name, float[] color){
        this.ID = id;
        this.path = path;
        this.color = color;
        TAG.concat(name);
    }
    public  void initialize(float[] modelMatrix){

    }
    public void draw(float[] draw){

    }
    public void rotate(float degree, float[] axis){

    }
    public void translate(float[] translate){

    }
}
