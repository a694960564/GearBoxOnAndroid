package com.example.mrj.gearbox.object;

/**
 * Created by Mr.J on 2020/4/7.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.mrj.gearbox.R;
import com.example.mrj.gearbox.util.TextReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private String TAG = "Model:";
    private int ID;
    private String path;
    private float[] color = new float[3];
    private float[] vertex = null;
    private float[] normal = null;
    private FloatBuffer vertexBuffer;
    private int triangle_size = 0;
    private float[] modelMatrix = new float[16];
    private float[] modelViewProjectionMatrix = new float[16];
    private float maxX;
    private float maxY;
    private float maxZ;
    private float minX;
    private float minY;
    private float minZ;
    int a_Position;
    int u_MVPMatrix;
    public boolean show;
    public Model(String name, int stlID,  Context context, int program){
        this.ID = program;
//        this.path = path;
//        this.color = color;
        show = false;
        readerSTL(TextReader.readTextFileFromResource(context, stlID), context);
        TAG += name;
        Matrix.setIdentityM(modelMatrix, 0);
        a_Position = GLES20.glGetAttribLocation(program, "a_Position");
        u_MVPMatrix = GLES20.glGetUniformLocation(program, "u_MVPMatrix");
    }
    public  void initialize(float[] modelMatrix){
        this.modelMatrix = modelMatrix;
    }
    public void draw(float[] viewProjectionMatrix){
        if(show){
            vertexBuffer.position(0);
            GLES20.glVertexAttribPointer(a_Position, 3, GLES20.GL_FLOAT, false, 12, vertexBuffer);
            GLES20.glEnableVertexAttribArray(0);
            Matrix.multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0);
            GLES20.glUniformMatrix4fv(u_MVPMatrix, 1, false, modelViewProjectionMatrix, 0);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, triangle_size * 3);
        }
    }
    public void rotate(float degree, float[] axis){

    }
    public void translate(float[] translate){

    }

    private boolean readerSTL(final String stlResource, final Context context){
        maxX = Float.MIN_VALUE;
        maxY = Float.MIN_VALUE;
        maxZ = Float.MIN_VALUE;
        minX = Float.MAX_VALUE;
        minY = Float.MAX_VALUE;
        minZ = Float.MAX_VALUE;
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(R.string.stl_load_progress_title);
        progressDialog.setMax(0);
        progressDialog.setMessage(context.getString(R.string.stl_load_progress_message));
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.show();
        final AsyncTask<String, Integer, float[]> task = new AsyncTask<String, Integer, float[]>() {
            float[] processText(String stlText) throws Exception{
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
            protected float[] doInBackground(String... stl) {
                float[] processResult = null;
                try{
                    Log.v(TAG,"读取中");
                    processResult = processText(stl[0]);
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
                if(normal.length<1 || vertex.length<1){
                    Toast.makeText(context, context.getString(R.string.error_fetch_data), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                vertexBuffer = ByteBuffer
                        .allocateDirect(vertex.length * 4)
                        .order(ByteOrder.nativeOrder())
                        .asFloatBuffer()
                        .put(vertex);
                Log.v(TAG,"读取完成!");
                show = true;
            }
        };

        try{
            task.execute(stlResource);
        }catch(Exception e){
            return false;
        }
        return true;
    }

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
}
