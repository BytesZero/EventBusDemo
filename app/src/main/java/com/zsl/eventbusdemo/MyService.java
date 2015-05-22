package com.zsl.eventbusdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.orhanobut.logger.Logger;

import de.greenrobot.event.EventBus;

/**
 * Created by zsl on 15/5/22.
 */
public class MyService extends Service {

    public static final String ACTION_START="ACTION_START";

    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(ACTION_START.equals(intent.getAction())){
            new MyServerThread().start();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    class MyServerThread extends Thread{
        @Override
        public void run() {
            EventBus.getDefault().post(new MessageEvent("这是在MyService的MyServerThread线程中Post的消息"));
        }
    }
}
