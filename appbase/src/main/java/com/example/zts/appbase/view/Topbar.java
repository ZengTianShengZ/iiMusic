package com.example.zts.appbase.view;

/**
 * Created by ZTS on 2015/12/25.
 */

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zts.appbase.R;

/**
 * 自定义的Topbar
 * 左右两边各有一个button
 * 中间有一个title
 * @author honeyYYr
 * 下午9:29:20
 */
public class Topbar extends RelativeLayout {
    /**
     * 按钮模式
     *  -1:没有button
     *  0 :有两个button
     *  1 :只有leftButton
     *  2 :只有rightButton
     */
    private int buttonMode;

    private ImageView leftButton, rightButton;
    private TextView tvTitle;
    /**
     * topbar 标题属性
     */
    private float titleTextSize;
    private int titleTextColor;
    private String title;
    /**
     * topbar左边button属性
     */
    private float leftMargin;
    private ColorStateList leftTextColor;
    private float leftTextSize;
    private Drawable leftBackground;
    private String leftText;
    private float leftWidth;
    private float leftHeight;
    /**
     * topbar 右边标题属性
     */
    private float rightMargin;
    private ColorStateList rightTextColor;
    private float rightTextSize;
    private Drawable rightBackground;
    private String rightText;
    private float rightWidth;
    private float rightHeight;



    private LayoutParams leftParams, rightParams, titleParams;

    /**
     * topbar 的背景
     */
    private Drawable background;

    private TopbarLeftClickListener leftListener;

    private TopbarRightClickListener rightListener;

    public interface TopbarLeftClickListener {
        public void leftClick();
    }


    public interface TopbarRightClickListener {
        public void rightClick();
    }
    public interface setTopbarDetel {
        public void setTopbarDetel(String title,int color,int liftButtonResour,int rightButtonResour);
    }


    /**
     * Topbar左边button 的监听事件
     * @param listener
     */
    public void setOnTopbarLeftClickListener(TopbarLeftClickListener listener) {
        Log.i("leftClick", "...listener..."+listener);
        this.leftListener = listener;
    }


    /**
     * Topbar右边button 的监听事件
     * @param listener
     */
    public void setOnTopbarRightClickListener(TopbarRightClickListener listener) {
        this.rightListener = listener;
    }

    public Topbar(Context context) {
        this(context, null);
    }

    /**
     * 使用自定义的属性时调用的构造函数
     * @param context
     * @param attrs
     */
    public Topbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.Topbar);

        titleTextSize = ta.getDimension(R.styleable.Topbar_titleTextSize, 10);
        titleTextColor = ta.getColor(R.styleable.Topbar_titleTextColorz, 10);
        title = ta.getString(R.styleable.Topbar_titlez);

        buttonMode = ta.getInteger(R.styleable.Topbar_buttonMode, -1);
        background = ta.getDrawable(R.styleable.Topbar_backgroundz);
        //  buttonMode 是上面 attr.xml 定义了 三个 枚举值
        switch (buttonMode) {
            case 0:// 0表示有两个button
                initLeftButton(context, ta);
                initRightButton(context, ta);
                break;
            case 1:// 1表示只有leftButton
                initLeftButton(context, ta);
                break;
            case 2:// 2表示只有rightButton
                initRightButton(context, ta);
                break;
            default:// 表示没有button
                break;
        }

        ta.recycle();

        setBackgroundDrawable(background);// 设置Topbar背景
        tvTitle = new TextView(context);

        tvTitle.setTextSize(titleTextSize);
        tvTitle.setTextColor(titleTextColor);
        tvTitle.setText(title);
        tvTitle.setGravity(Gravity.CENTER);

        titleParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(tvTitle, titleParams);
    }

    /**
     * 构造topbar左边button的各种属性
     * @param context
     * @param ta
     */
    @SuppressWarnings("deprecation")
    private void initLeftButton(Context context, TypedArray ta) {
        leftTextColor = ta.getColorStateList(R.styleable.Topbar_leftTextColor);
        leftTextSize = ta.getDimension(R.styleable.Topbar_leftTextSize, 10);
        leftBackground = ta.getDrawable(R.styleable.Topbar_leftBackground);
        leftText = ta.getString(R.styleable.Topbar_leftText);
        leftWidth = ta.getDimension(R.styleable.Topbar_leftWidth, 10);
        leftHeight = ta.getDimension(R.styleable.Topbar_leftHeight, 10);
        leftMargin = ta.getDimension(R.styleable.Topbar_leftMargin, 10);

        leftButton = new ImageView(context);

/*        if (leftTextColor != null)
            leftButton.setTextColor(leftTextColor);
        leftButton.setBackgroundDrawable(leftBackground);
        leftButton.setText(leftText);
        if (0 != leftTextSize)
            leftButton.setTextSize(leftTextSize);*/

        leftParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        leftParams.addRule(RelativeLayout.CENTER_VERTICAL);
        if (0 != leftWidth) {
            leftParams.width = (int) leftWidth;
        }
        if (0 != leftHeight) {
            leftParams.height = (int) leftHeight;
        }
        if (0 != leftMargin) {
            leftParams.setMargins((int) leftMargin, 0, 0, 0);
        }

        addView(leftButton, leftParams);

        leftButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("leftClick","...leftListener..."+leftListener);
                if (leftListener != null)
                    leftListener.leftClick();
            }
        });
    }

    /**
     * 构造Topbar右边Button的各种属性
     * @param context
     * @param ta
     */
    @SuppressWarnings("deprecation")
    private void initRightButton(Context context, TypedArray ta) {
        rightTextColor = ta
                .getColorStateList(R.styleable.Topbar_rightTextColor);
        rightTextSize = ta.getDimension(R.styleable.Topbar_rightTextSize, 10);
        rightBackground = ta.getDrawable(R.styleable.Topbar_rightBackground);
        rightText = ta.getString(R.styleable.Topbar_rightText);
        rightWidth = ta.getDimension(R.styleable.Topbar_rightWidth, 10);
        rightHeight = ta.getDimension(R.styleable.Topbar_rightHeight, 10);
        rightMargin = ta.getDimension(R.styleable.Topbar_rightMargin, 10);

        rightButton = new ImageView(context);

/*        if (rightTextColor != null)
            rightButton.setTextColor(rightTextColor);
        rightButton.setBackgroundDrawable(rightBackground);
        rightButton.setText(rightText);
        if (0 != rightTextSize)
            rightButton.setTextSize(rightTextSize);*/

        rightParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        rightParams.addRule(RelativeLayout.CENTER_VERTICAL);
        if (0 != rightWidth) {
            rightParams.width = (int) rightWidth;
        }
        if (0 != rightHeight) {
            rightParams.height = (int) rightHeight;
        }
        if (0 != rightMargin) {
            rightParams.setMargins(0, 0, (int) rightMargin, 0);
        }

        addView(rightButton, rightParams);

        rightButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (rightListener != null)
                    rightListener.rightClick();
            }
        });
    }

    /**
     *  设置标题
     *
     */
    public void setTitle(String title) {
        tvTitle.setText(title);
    }
    /**
     * 设置标题
     *
     */
    public void setTitle(int resId) {
        tvTitle.setText(resId);
    }

    public void setleftButtonBackground(int id){
        leftButton.setBackgroundResource(id);
    }

    public void setrightButtonBackground(int id){
        rightButton.setBackgroundResource(id);
    }

    public void setTopbarDetel(String title,int color,int liftButtonResour,int rightButtonResour){

        tvTitle.setText(title);
        setBackgroundResource(color);
        leftButton.setBackgroundResource(liftButtonResour);
        rightButton.setBackgroundResource(rightButtonResour);
    }
}
