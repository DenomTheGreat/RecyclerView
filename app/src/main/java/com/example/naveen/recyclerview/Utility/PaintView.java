package com.example.naveen.recyclerview.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.naveen.recyclerview.Activity.MainActivity;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;


public class PaintView extends View {


    private Path path = new Path();
    private Paint brush = new Paint();
    private ArrayList<Path> path_list = new ArrayList<Path>();
    private ArrayList<float[]> pathPoints = new ArrayList<>();
    private ArrayList<ArrayList<float[]>> pathPoints_list = new ArrayList<>();
    File file;
    private String myData="";


    public PaintView(Context context) {
        super(context);
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        brush.setAntiAlias(true);
        brush.setColor(Color.RED);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(4f);
    }

    public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();

        float[] points = new float[2];

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.reset();
                pathPoints.clear();
                points[0]=pointX;
                points[1]=pointY;
                path.moveTo(pointX, pointY);
                pathPoints.add(points);
                return true;
            case MotionEvent.ACTION_MOVE:
                points[0]=pointX;
                points[1]=pointY;
                path.lineTo(pointX, pointY);
                pathPoints.add(points);
                break;
            case MotionEvent.ACTION_UP:
                points[0]=pointX;
                points[1]=pointY;
                path.lineTo(pointX,pointY);
                pathPoints.add(points);
                path_list.add(path);
                pathPoints_list.add(pathPoints);
                path = new Path();
                break;

            default:
                return false;

        }
        postInvalidate();
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(Path p : path_list){
            canvas.drawPath(p,brush);
        }
        canvas.drawPath(path,brush);
    }


    public void saveView(Context context){
        /*
        Bitmap  bitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        this.draw(canvas);

        File file = new File(context.getExternalFilesDir(null).getAbsolutePath()+"imglolepic.png");

        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
            Toast.makeText(getContext(),"Hello",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;*/
        Gson gson = new Gson();
        String json = gson.toJson(pathPoints_list);

         file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"yoyo.txt");
        try{
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(json.getBytes());
            fos.close();
            Toast.makeText(getContext(),"Saved!",Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(getContext(),"File not saved!",Toast.LENGTH_LONG).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getContext(),"File not saved!",Toast.LENGTH_LONG).show();
        }
        MainActivity.AddListItem("title",Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"yoyo.txt");
       }
    public void onClickUndo(){
        if(path_list.size()>0){
            path_list.remove(path_list.size()-1);
            invalidate();
        }else {
            Toast.makeText(getContext(),"Draw smth first!!",Toast.LENGTH_SHORT).show();
        }
    }

    public void readView(Context applicationContext){
        try {
            file = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)),"yoyo.txt");
            FileInputStream fis = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while((strLine=br.readLine())!=null){
                myData=myData+strLine;
            }
            Gson gson= new Gson();
            pathPoints_list= (ArrayList<ArrayList<float[]>>) gson.fromJson(myData,ArrayList.class);
            in.close();
            Toast.makeText(getContext(),"Saved!",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"Not Saved!",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"Not Saved!",Toast.LENGTH_LONG).show();
        }
    }
    public void Reconstruct(){
        ArrayList<ArrayList<float[]>> saved_pathPoints_list = pathPoints_list;
        for(int i=0;i<saved_pathPoints_list.size();i++){
            ArrayList<float[]> saved_pathPoints=saved_pathPoints_list.get(i);
            Path saved_path=new Path();
            saved_path.moveTo(saved_pathPoints.get(0)[0],saved_pathPoints.get(0)[1]);
            for(int j=1;j<saved_pathPoints.size();j++){
                saved_path.lineTo(saved_pathPoints.get(i)[0],saved_pathPoints.get(i)[1]);
            }
        }
    }
}

