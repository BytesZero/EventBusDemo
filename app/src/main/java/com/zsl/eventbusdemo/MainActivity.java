package com.zsl.eventbusdemo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity {
    Button bt_simple,bt_service,bt_receiver;
    TextView tv_demo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();

    }

    private void initEvent() {
        bt_simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyThread().start();
            }
        });

        bt_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MyService.class);
                intent.setAction(MyService.ACTION_START);
                startService(intent);
            }
        });

        bt_receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MyBroadcastReceiver.class);
                intent.setAction(MyBroadcastReceiver.ACTION_SEND);
                sendBroadcast(intent);
            }
        });
    }

    private void initView() {
        bt_simple = (Button) findViewById(R.id.main_bt_simple);
        tv_demo = (TextView) findViewById(R.id.main_tv_demo);
        bt_service= (Button) findViewById(R.id.main_bt_service);
        bt_receiver= (Button) findViewById(R.id.main_bt_receiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //注册EventBus
        EventBus.getDefault().register(this);
        Logger.e("EventBus注册");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //注销EventBus
        EventBus.getDefault().unregister(this);

        Logger.e("EventBus注销");
    }

    // 在同一个线程线程中
    public void onEvent(MessageEvent event) {
        Logger.e("onEvent"+event.message);
    }

    //接受消息的地方(在Android的UI线程中)
    public void onEventMainThread(MessageEvent event) {
        Logger.e("onEventMainThread"+event.message);
        tv_demo.setText(event.message);
    }

    //接受消息的地方(在后台线程中)
    public void onEventBackgroundThread(MessageEvent event){
        Logger.e("onEventBackgroundThread"+event.message);

    }

    //在单独的一个线程中
    public void onEventAsync(MessageEvent event){
        Logger.e("onEventAsync" + event.message);
    }


    class MyThread extends Thread{
        @Override
        public void run() {
            //发送消息
            EventBus.getDefault().post(new MessageEvent("这是EventBus发送的消息更新UI"));
        }
    };

}
