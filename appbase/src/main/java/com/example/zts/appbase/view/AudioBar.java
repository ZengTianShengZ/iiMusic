package com.example.zts.appbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.zts.appbase.R;

/**
 * Created by ZTS on 2016/1/21.
 */
public class AudioBar extends View {

    /**
     * 画笔对象的引用
     */
    private Paint paint;

    private int updateDuration;
    private int audioBarColor;
    private int barCount;
    private double mrandom;

    public AudioBar(Context context) {
        this(context, null);
    }

    public AudioBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ActionBar);
        updateDuration = mTypedArray.getInteger(R.styleable.MyAudioBar_updateDuration, 300);
        audioBarColor = mTypedArray.getColor(R.styleable.MyAudioBar_audioBarColor, Color.RED);
        barCount = mTypedArray.getInteger(R.styleable.MyAudioBar_barCount, 5);
        mTypedArray.recycle();

        paint = new Paint();
        paint.setColor(audioBarColor); //设置圆环的颜色
        paint.setStyle(Paint.Style.FILL); //设置空心
        paint.setAntiAlias(true);  //消除锯齿

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i = 1;i<barCount;i++) {
            if(heightToRadiomFlage){
                mrandom = 0.8; // false 默认高度为 0.2
            }else{
                mrandom = Math.random();
            }
            canvas.drawRect((float)(getWidth()/barCount*(i-0.5)),(float)(getHeight()*mrandom),getWidth() / barCount * i, getHeight(), paint);

        }
        postInvalidateDelayed(updateDuration);
    }

    protected int dp2px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics());
    }
    private boolean heightToRadiomFlage = false;
    public void setAudioBarHeightToRadiom(boolean tORf){
         heightToRadiomFlage = tORf;
    }
}
