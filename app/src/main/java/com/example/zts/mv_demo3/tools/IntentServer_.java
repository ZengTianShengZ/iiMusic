package com.example.zts.mv_demo3.tools;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by ZTS on 2016/1/6.
 */
public class IntentServer_ extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public IntentServer_(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
