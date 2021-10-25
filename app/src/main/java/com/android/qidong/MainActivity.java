package com.android.qidong;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.activity_main_getDataText);
        findViewById(R.id.activity_main_qidongBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qidong();
            }
        });
    }

    private void qidong(){
        //这里用到了隐式意图，来启动APP里面的Activity
        Intent intent = new Intent();
        intent.setAction("app.intent.action.watermark.DaKaCamera");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.putExtra("DKCameraAction", "com.android.qidong.MainActivity");
        startActivityForResult(intent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("ceshi", "onActivityResult: requestCode == " + requestCode + ", " +  resultCode);
        if (RESULT_OK == resultCode && requestCode == 1000){
            textView.setText(data.getStringExtra("gisInfo"));
        }

    }
}