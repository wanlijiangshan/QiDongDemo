package com.android.qidong;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private RecyclerView recyclerView;
    private PictureAdapter pictureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.activity_main_getDataText);
        recyclerView = findViewById(R.id.activity_main_recyclerVeiw);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        pictureAdapter = new PictureAdapter(this);
        recyclerView.setAdapter(pictureAdapter);

        findViewById(R.id.activity_main_actionCameraBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionCamera(0);
            }
        });
        findViewById(R.id.activity_main_actionAlbumBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionCamera(1);
            }
        });
    }

    private void actionCamera(int DKCameraType){
        //这里用到了隐式意图，来启动APP里面的Activity
        Intent intent = new Intent();
        intent.setAction("DaKaCamera.intent.action.GET_WATERMARK");
        intent.putExtra("DKCameraParame", getDKCameraParame());
        intent.putExtra("DKCameraType", DKCameraType);//0相机 1 相册
        startActivityForResult(intent, 1000);
    }

    private String getDKCameraParame(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("studioId", "studioId");
            jsonObject.put("address", "北京市海淀区五道口");
            jsonObject.put("remark", "备注内容");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (RESULT_OK == resultCode && requestCode == 1000){
            String dakaImgs = data.getStringExtra("dakaImgs");
            textView.setText(dakaImgs);
            Log.e("ceshi", "onActivityResult: dakaImgs == " + dakaImgs + ", " +  resultCode);
        }

    }
}