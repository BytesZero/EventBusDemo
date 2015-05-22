package com.zsl.eventbusdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import de.greenrobot.event.EventBus;

/**
 * Created by zsl on 15/5/22.
 */
public class MyBroadcastReceiver extends BroadcastReceiver{
    public static final String ACTION_SEND="ACTION_SEND";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (ACTION_SEND.equals(intent.getAction())){
            EventBus.getDefault().post(new MessageEvent("这是在［广播］中给Activity发送消息"));
        }
    }
}
