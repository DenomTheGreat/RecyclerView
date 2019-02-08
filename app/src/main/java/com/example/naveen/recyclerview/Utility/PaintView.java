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

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class PaintView extends View {


    private Path path = new Path();
    private Paint brush = new Paint();
    private ArrayList<Path> paths = new ArrayList<Path>();
    private ArrayList<Path> undonePaths = new ArrayList<Path>();


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

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                undonePaths.clear();
                path.reset();
                path.moveTo(pointX, pointY);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(pointX, pointY);
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(pointX,pointY);
                paths.add(path);
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
        for(Path p : paths){
            canvas.drawPath(p,brush);
        }
        canvas.drawPath(path,brush);
    }


    public void saveView(Context context){

        /*Bitmap  bitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
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

       }
    public void onClickUndo(){
        if(paths.size()>0){
            undonePaths.add(paths.remove(paths.size()-1));
            invalidate();
        }else {
            Toast.makeText(getContext(),"Draw smth first!!",Toast.LENGTH_SHORT).show();
        }
    }


}

