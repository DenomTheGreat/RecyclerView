package com.example.naveen.recyclerview.Utility;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


public class PaintView extends View {

    private Canvas drawCanvas;
    private Path path = new Path();
    private Paint brush = new Paint();
    private List<Path> moveList=null;
    private List<Path> undoList=null;
    private List<Path> currentMoveList=null;


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
                path.moveTo(pointX, pointY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(pointX, pointY);
                currentMoveList.add(path);
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(pointX,pointY);
                drawCanvas.drawPath(path,brush);
                moveList.add(path);
                currentMoveList.clear();
                break;
            default:
                return false;

        }
        invalidate();
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for(Path path : currentMoveList){
            canvas.drawPath(path, brush);
        }
        for (Path  path:moveList){
            canvas.drawPath(path,brush);
        }
    }

    public Bitmap saveView(){

        Bitmap  bitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        this.draw(canvas);

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/Camera/imglolepic.png");

        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
            Toast.makeText(getContext(),"Hello",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }
    public void undo(){
        if(moveList.size()>0){
            undoList.add(moveList.remove(moveList.size()-1));
            invalidate();
        }
    }


}

