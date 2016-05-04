package com.example.zts.mv_demo3.activity;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.zts.appbase.activity.BaseActivity;
import com.example.zts.appbase.view.CircleImageView;
import com.example.zts.mv_demo3.R;

/**
 * Created by Administrator on 2016/5/4.
 */
public class ShowNotLrcActivity extends BaseActivity {

    private ImageView not_lrc_back_img;
    private CircleImageView mCircleImageView;
    private  Animation operatingAnim;

    @Override
    public int getActivityLayout() {
        return R.layout.activity_show_not_lrc;
    }

    @Override
    public void initView(Context context) {

        not_lrc_back_img = (ImageView) findViewById(R.id.not_lrc_back_img);
        mCircleImageView = (CircleImageView) findViewById(R.id.not_lrc_img);


    }


    @Override
    public void initData() {

        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_view);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);

    }

    @Override
    public void initEvent() {
        not_lrc_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(operatingAnim != null){
            mCircleImageView.setAnimation(operatingAnim);
        }
    }
}
