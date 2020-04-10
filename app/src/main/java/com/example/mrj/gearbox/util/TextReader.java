package com.example.mrj.gearbox.util;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Mr.J on 2020/4/10.
 */
public class TextReader {
    public static String readTextFileFromResource(Context context,
                                                  int resourceId){
        StringBuilder body = new StringBuilder();
        try{
            InputStream inputStream =
                    context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream);
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            String nextLine;
            while((nextLine = bufferReader.readLine()) != null){
                body.append(nextLine);
                body.append('\n');
            }
        }catch (IOException e){
            throw new RuntimeException("Could not open resource:"
                    + resourceId,e);
        }catch (Resources.NotFoundException nfe){
            throw new RuntimeException("Resource not found:"+
                    resourceId,nfe);
        }
        return body.toString();
    }
}
