package com.example.zts.mv_demo3.fragment;

import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.zts.appbase.BaseFragment.BaseFragment;
import com.example.zts.appbase.adapter.ImageLoader;
import com.example.zts.mv_demo3.R;
import com.example.zts.mv_demo3.adapter.TimeSelectAdapter;
import com.example.zts.mv_demo3.domain.AudioMediaBean;

import java.util.ArrayList;

/**
 * Created by ZTS on 2015/12/30.
 */
public class FragmentTool extends BaseFragment {

    private Button tool_out_bt;
    private TextView minunt_number,sec_number;
    private ImageView timeImageView;
    private ImageView timeImageViewFlag;
    private LinearLayout time_lin;
    private ListView listView;

    private TimeSelectAdapter timeSelectAdapter;

    private int minunt_num,sec_num = 59;

    private Chronometer chronometer;
    private AlertDialog builder;
    private NumberPicker hour_numberPicker,minunt_numberPicker;

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_tool;
    }

    @Override
    public void initView(View rootview) {
        tool_out_bt = (Button) rootview.findViewById(R.id.tool_out_bt);
        minunt_number = (TextView) rootview.findViewById(R.id.minunt_number);
        sec_number = (TextView) rootview.findViewById(R.id.sec_number);
        chronometer = (Chronometer) rootview.findViewById(R.id.tool_chronometer);
        time_lin = (LinearLayout) rootview.findViewById(R.id.time_lin);
        listView = (ListView) rootview.findViewById(R.id.timeSelect_list);
    }

    @Override
    public void initData() {
        ArrayList<String> lisTime= new ArrayList<String>();
        String[] timeItem = getResources().getStringArray(R.array.array_time_item);
        for(int i=0;i<timeItem.length;i++){
            lisTime.add(timeItem[i]);
        }

        timeSelectAdapter = new TimeSelectAdapter(getContext(),lisTime,R.layout.time_select_list);
        listView.setAdapter(timeSelectAdapter);
    }

    @Override
    public void initEvent() {

        LinearLayout dialogLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.dialo_sleeppick,null);
        hour_numberPicker = (NumberPicker) dialogLayout.findViewById(R.id.hour_numberPicker);
        minunt_numberPicker = (NumberPicker) dialogLayout.findViewById(R.id.minunt_numberPicker);

        hour_numberPicker.setMinValue(0);
        hour_numberPicker.setMaxValue(12);
        minunt_numberPicker.setMinValue(1);
        minunt_numberPicker.setMaxValue(60);

        builder = new AlertDialog.Builder(getContext())
                .setTitle("自定义时间")
                .setView(dialogLayout)
                .setPositiveButton("确定", new  DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        sec_num = 0;
                        minunt_num = 0;
                        minunt_num = hour_numberPicker.getValue() * 60+ minunt_numberPicker.getValue();
                        time_lin.setVisibility(View.VISIBLE);
                        if(hour_numberPicker.getValue() * 60 == 0){
                            minunt_number.setText("" + 0);
                        }else {
                            minunt_number.setText("" + minunt_num);
                        }
                        sec_num = 59;
                        sec_number.setText(""+sec_num);

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();

        tool_out_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                switchTime(position);
                if(timeImageViewFlag != null){
                    timeImageViewFlag.setImageResource(R.drawable.time_no);
                }
                timeImageView = (ImageView) view.findViewById(R.id.time_Img);
                timeImageView.setImageResource(R.drawable.time_yes);
                timeImageViewFlag = timeImageView;
            }
        });
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (SystemClock.elapsedRealtime() - chronometer.getBase() > 1000) {
                    sec_num--;
                    if (sec_num == 0) {

                        if (minunt_num == 0) {
                            chronometer.stop();
                            getActivity().finish();
                            //...
                        } else {
                            sec_num = 59;
                            minunt_num--;
                            minunt_number.setText("" + minunt_num);
                        }
                    }
                    sec_number.setText("" + sec_num);
                }
            }
        });
    }

    private void switchTime(int position){
        switch (position) {
            case 0:
                chronometer.stop();
                time_lin.setVisibility(View.GONE);
                break;
            case 7:
                builder.show();
                chronometer.start();
                time_lin.setVisibility(View.VISIBLE);
                break;
            default:
                minunt_num = position * 10 - 1;
                chronometer.start();
                time_lin.setVisibility(View.VISIBLE);
                break;

        }
        sec_num = 59;
        minunt_number.setText("" + minunt_num);
    }
}
