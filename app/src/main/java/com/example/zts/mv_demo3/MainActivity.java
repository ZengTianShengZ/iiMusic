package com.example.zts.mv_demo3;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.zts.appbase.BaseFragment.BaseFragment;
import com.example.zts.appbase.activity.BaseActivity;
import com.example.zts.appbase.view.Topbar;
import com.example.zts.mv_demo3.Broadcast.TelephoneCallBroadcast;
import com.example.zts.mv_demo3.adapter.FragmentAdapter;
import com.example.zts.mv_demo3.domain.OnlineMusicBean;
import com.example.zts.mv_demo3.fragment.FragmentMusic;
import com.example.zts.mv_demo3.fragment.FragmentMusicOnline;
import com.example.zts.mv_demo3.fragment.FragmentTool;
import com.example.zts.mv_demo3.fragment.FragmentVideo;
import com.example.zts.mv_demo3.server.MusicServer;
import com.example.zts.mv_demo3.tools.IteratorLrcFile;
import com.example.zts.mv_demo3.tools.ViewAnimationShow;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.yalantis.euclid.library.EuclidListAdapter;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity
public class MainActivity extends BaseActivity implements Topbar.TopbarLeftClickListener,FragmentMusicOnline.ShowOverlayTopbar ,View.OnClickListener {

    // ViewById 有两种写法，一种是需要R.id，
    @ViewById(R.id.mtopbar)
    Topbar mtopbar;

    @ViewById(R.id.common_topbar_menu)
    ImageView common_topbar_menu;

    @ViewById(R.id.common_topbar_musicTv)
    TextView common_topbar_musicTv;

    @ViewById(R.id.common_topbar_videoTv)
    TextView common_topbar_videoTv;

    @ViewById(R.id.relativeLayout_overlay)
    RelativeLayout overlayRel;

    @ViewById(R.id.imagebuttom_overlay)
    ImageView overlayButton;

    @ViewById(R.id.image_overlay)
    ImageView overlayImage;

    @ViewById(R.id.overlay_LinearLayoutt)
    LinearLayout overlayLinearLayout;
    @ViewById(R.id.overlay_singleName)
    TextView overlaySingleTextView;
    @ViewById(R.id.overlay_songName)
    TextView overlaySongTextView;



    //另一种不需要，但 声明的变量要和 R.id的 id一个样
    @ViewById(R.id.viewpager)
    ViewPager viewPager;

    @ViewById(R.id.drawerLayout)
    DrawerLayout drawerLayout;


    private FragmentManager mFragmentManager;
    private FragmentMusic fragmentMusic;
    private FragmentVideo fragmentVideo;
    private FragmentTool fragmentTool;
    private FragmentMusicOnline fragmentMusicOnline;
    private ViewAnimationShow viewAnimationShow;


    private ArrayList<Fragment> fragments;

    private boolean drawerLayoutShow;
    public static int mainPageSelectedPos;
    static int sScreenWidth;
    static int sProfileImageHeight;
    private static boolean overlayAnimationStatic = false;

    private Context context;

    @Override
    public int getActivityLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Context context) {

        this.context = context;
        //  z注意上面的 baseactivity 要继承 fragmentactivity ，getSupportFragmentManager才有用！！！
        mFragmentManager = getSupportFragmentManager();
        //mTransaction = mFragmentManager.beginTransaction();
        fragments = new ArrayList<Fragment>();
        fragments.add(new FragmentMusic());
        fragments.add(new FragmentVideo());
        //fragments.add(new FragmentTool());

        FragmentAdapter fragmentAdapter = new FragmentAdapter(mFragmentManager, fragments);

        viewPager.setAdapter(fragmentAdapter);

        common_topbar_musicTv.setTextColor(getResources().getColor(R.color.color_white_tv));


        common_topbar_menu.setOnClickListener(this);
        common_topbar_musicTv.setOnClickListener(this);
        common_topbar_videoTv.setOnClickListener(this);
        //...this...com.example.zts.mv_demo3.MainActivity_@528299fc ：this 就是包名+类名


/*
        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment1 hello = new fragment1();
        fragmentTransaction.add(R.id.fragment_container, hello, "HELLO");
        fragmentTransaction.commit();*/

    }

    @Override
    public void initData() {

        sProfileImageHeight = getResources().getDimensionPixelSize(com.yalantis.euclid.library.R.dimen.height_profile_image);
        sScreenWidth =sProfileImageHeight;

        //fragmentMusicOnline = new FragmentMusicOnline();
        FragmentMusicOnline.setShowOverlayTopbar(this);
        viewAnimationShow = new ViewAnimationShow(context,overlayRel,overlayImage,overlayLinearLayout);

        mtopbar.setOnTopbarLeftClickListener(this);
        mtopbar.setleftButtonBackground(R.drawable.draw_time_item);

        overlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("overlayButton");
                viewAnimationShow.animateCloseProfileDetails(180);
                overlayAnimationStatic =false;
            }
        });

        overlayImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showToast("overlayImage");
                return true;
            }
        });
        overlayLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showToast("overlayLinearLayout");
                return true;
            }
        });

    }

    //back键默认把当前Activity给finish，Activity 所在的进程和Task被销毁,home键只是把Activity给onStop.
    @Override
    public void onBackPressed() {
        if(overlayAnimationStatic){
            overlayAnimationStatic = false;
            viewAnimationShow.animateCloseProfileDetails(180);
        }else{
            this.moveTaskToBack(true);
            Log.i("activitylive", "onBackPressed");//1
            super.onBackPressed();
        }

        //不要加super.onBackPressed()，就可以不退出activity，执行自己的代码啦~
       // super.onBackPressed();
     }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("activitylive","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("activitylive", "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("activitylive", "onStop");//4
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("activitylive", "onPause");//3
    }

    @Override
    public void finish() {
        super.finish();
        Log.i("activitylive", "finish");//2
    }

    @Override
    protected void onDestroy() {
            super.onDestroy();//5
        Log.i("bugDestory", "stopService");
        Intent intent = new Intent(context, MusicServer.class);
        stopService(intent);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void initEvent() {


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                Log.i("position","..A.."+position+".B.."+positionOffset+"..C.."+
                        positionOffsetPixels+"..D."+FragmentMusic.musicMageSelectedPos+"..E."+drawerLayoutShow);

                if(mainPageSelectedPos == 0 && FragmentMusic.musicMageSelectedPos==0&& drawerLayoutShow) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
                drawerLayoutShow = false;
            }
            @Override
            public void onPageSelected(int position) {
                mainPageSelectedPos = position;
                switch (position) {
                    case 0:
                        common_topbar_videoTv.setTextColor(getResources().getColor(R.color.color_gray_tv));
                        common_topbar_musicTv.setTextColor(getResources().getColor(R.color.color_white_tv));
                        break;
                    case 1:
                        common_topbar_videoTv.setTextColor(getResources().getColor(R.color.color_white_tv));
                        common_topbar_musicTv.setTextColor(getResources().getColor(R.color.color_gray_tv));
                        break;
                    case 2:
                        mtopbar.setTitle("工具");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int getX = 0;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        getX = (int) event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        drawerLayoutShow = (event.getX() - getX > 300);

                        break;
                }

                return false;
            }
        });



    }

    private void testTost() {
        showToast("testTost");
    }

    @Override
    public void leftClick() {
        drawerLayout.openDrawer(Gravity.LEFT);

    }


    @Override
    public void showTopbar(OnlineMusicBean onlineMusicBean) {

        overlayAnimationStatic = true;
        showToast("testTost");
        overlaySingleTextView.setText("歌手： " + onlineMusicBean.getName() +" "+ "歌曲： " + onlineMusicBean.getMusicName());
        overlaySongTextView.setText(onlineMusicBean.getSongDetails());
        ImageLoader.getInstance().displayImage(onlineMusicBean.getPictureUrl().getFileUrl(context), overlayImage);
        viewAnimationShow.animateOpenTopProfileDetails(300);
        viewAnimationShow.animateOpenProfileDetails(400);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.common_topbar_menu:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;

            case R.id.common_topbar_musicTv:
                common_topbar_musicTv.setTextColor(getResources().getColor(R.color.color_white_tv));
                common_topbar_videoTv.setTextColor(getResources().getColor(R.color.color_gray_tv));
                viewPager.setCurrentItem(0);
                break;

            case R.id.common_topbar_videoTv:
                common_topbar_videoTv.setTextColor(getResources().getColor(R.color.color_white_tv));
                common_topbar_musicTv.setTextColor(getResources().getColor(R.color.color_gray_tv));
                viewPager.setCurrentItem(1);
                break;
        }
    }

}
/**
 * 问题有点严重了 针对viewAnimationShow的问题
 * 虽然fragment 操作activity 的 控件利用监听可以解决
 * 那 activity 操作 fragmment 的控件是否也用监听解决呢
 * 在写代码之前完全没考虑周全，所以遇到问题就一卡一卡的
 * 效率也不高，
 */