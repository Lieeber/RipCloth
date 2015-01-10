package com.example.bing.simeimvyifu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity implements View.OnTouchListener {

    private ImageView top;

    private Paint mPaint;
    private Canvas mCanvas;
    private Bitmap blank;
    private Bitmap topBitmap;
    private Bitmap buttomBitmap;
    private Path mPath;
    private ImageView buttom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        top = (ImageView) findViewById(R.id.top);
        buttom = (ImageView)findViewById(R.id.buttom);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        topBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a6);
        buttomBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.b6);
        int width = topBitmap.getWidth();
        int height = topBitmap.getHeight();

        buttom.setImageBitmap(buttomBitmap);

        blank = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        initPaint();
        mCanvas = new Canvas(blank);
        mCanvas.drawBitmap(topBitmap,0,0,null);
         top.setImageBitmap(blank);
        top.setOnTouchListener(this);

    }

    private void initPaint() {
      //  mPaint.setColor(Color.parseColor("#c0c0c0"));
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //默认的时候需要他为fill,但是绘制的时候要求他为stroke.需要在drawPath当中配置
        mPaint.setStyle(Paint.Style.STROKE);
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, this.getResources().getDisplayMetrics());
        mPaint.setStrokeWidth(px);
    }

    private int mLastX;
    private int mLastY;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mPath.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs(mLastX-x);
                int dy = Math.abs(mLastY-y);
                if(dx>3||dy>3){
                    mPath.lineTo(x,y);
                    mCanvas.drawPath(mPath, mPaint);
                    top.setImageBitmap(blank);
                }
                mLastX = x;
                mLastY = y;
                break;
        }
        return true;
    }

}
