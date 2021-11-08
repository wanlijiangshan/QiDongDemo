package com.android.qidong;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

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
                PermissionUtil.checkWriteStoragePermission(MainActivity.this, new PermissionUtil.CallLisenter() {
                    @Override
                    public void onCall(boolean isAccept) {
                        if (isAccept)
                            actionCamera(0);
                    }
                });
            }
        });
        findViewById(R.id.activity_main_actionAlbumBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionUtil.checkWriteStoragePermission(MainActivity.this, new PermissionUtil.CallLisenter() {
                    @Override
                    public void onCall(boolean isAccept) {
                        if (isAccept)
                            actionCamera(1);
                    }
                });
            }
        });
    }

    private void actionCamera(int DKCameraType){
        Intent intent = new Intent();
        intent.setAction("DaKaCamera.intent.action.GET_WATERMARK");
//        intent.setAction("app.intent.action.watermark.DaKaCamera");
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
            String dakaPictures = data.getStringExtra("dakaPictures");
            //textView.setText(dakaPictures);
            Gson gson=new Gson();
            Type type = new TypeToken<ArrayList<PictureBean>>(){}.getType();
            List<PictureBean> list = gson.fromJson(dakaPictures, type);
            pictureAdapter.setData(list);
        }
    }
}