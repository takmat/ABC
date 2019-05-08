package com.example.sql_conn.drawapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.example.sql_conn.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PaintView extends View {
    public static int BRUSH_SIZE = 10;
    public static final int DEF_COL = Color.BLACK;
    public static final int DEF_BG_COL = Color.WHITE;
    private static final float touch_tolarance = 4;
    private float mX,mY;
    private Path mPath;
    private Paint mPaint;
    private ArrayList<FingerPath> paths = new ArrayList<>();
    private int currentcolor;
    private int strokewidth;
    private int bgcolor = DEF_BG_COL;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    public String file;




    public PaintView(Context context) {
        super(context,null);
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(DEF_COL);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);



    }
    public void getFileName(String fileName){
        file = fileName;
    }
    public void init(DisplayMetrics metrics){
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        mBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentcolor = DEF_COL;
        strokewidth = BRUSH_SIZE;
        //files();
    }
    public void clear(){
        bgcolor = DEF_BG_COL;
        paths.clear();
        invalidate();
    }

    public Bitmap changeBitmap() {
        int resId = getResources().getIdentifier(file, "drawable", "com.example.sql_conn" );
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);



        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mCanvas.drawColor(bgcolor);
        Bitmap image = changeBitmap();
        canvas.save();
        for (FingerPath fp : paths)
        {
            mPaint.setColor(fp.color);
            mPaint.setStrokeWidth(fp.strokeWidth);

            mCanvas.drawPath(fp.path,mPaint);
        }

        canvas.drawBitmap(mBitmap,0,0, mBitmapPaint);
        canvas.drawBitmap(image,0,0, null);


        canvas.restore();
    }
    private void touchStart (float x , float y){
        mPath = new Path();
        FingerPath fp = new FingerPath(currentcolor, strokewidth,mPath);
        paths.add(fp);

        mPath.reset();
        mPath.moveTo(x,y);
        mX = x;
        mY = y;

    }
    private void touchMove(float x, float y){
        float dx = Math.abs(x-mX);
        float dy = Math.abs(y-mY);

        if(dx >= touch_tolarance || dy >= touch_tolarance) {
            mPath.quadTo(mX,mY,(x+mX)/2, (y+mY)/2);
            mX = x;
            mY = y;
        }
    }
    private void touchUp(){
        mPath.lineTo(mX,mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN :
                touchStart(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE :
                touchMove(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP :
                touchUp();
                invalidate();
                break;

        }
    return true;
    }
}
