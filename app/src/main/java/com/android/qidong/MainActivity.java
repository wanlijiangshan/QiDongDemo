package com.android.qidong;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private RelativeLayout configRel;
    private TextView textView;
    private RecyclerView recyclerView, originrRecyclerView;
    private PictureAdapter pictureAdapter, originPictureAdapter;
    private EditText waterMarkIdEdit, addressEdit, remarkEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.activity_main_getDataText);
        recyclerView = findViewById(R.id.activity_main_recyclerVeiw);
        originrRecyclerView = findViewById(R.id.activity_main_originrRecyclerView);
        waterMarkIdEdit = findViewById(R.id.activity_main_waterMarkIdEdit);
        addressEdit = findViewById(R.id.activity_main_addressEdit);
        remarkEdit = findViewById(R.id.activity_main_remarkEdit);
        configRel = findViewById(R.id.activity_main_configRel);
        configRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configRel.setVisibility(View.GONE);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        pictureAdapter = new PictureAdapter(this, 0);
        recyclerView.setAdapter(pictureAdapter);

        GridLayoutManager originGridLayoutManager = new GridLayoutManager(this, 3);
        originrRecyclerView.setLayoutManager(originGridLayoutManager);
        originPictureAdapter = new PictureAdapter(this, 1);
        originrRecyclerView.setAdapter(originPictureAdapter);

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
        findViewById(R.id.activity_main_actionConfigBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configRel.setVisibility(View.VISIBLE);
            }
        });
    }

    private void actionCamera(int DKCameraType) {
        Intent intent = new Intent();
        intent.setAction("DaKaCamera.intent.action.GET_WATERMARK");
        intent.putExtra("DKCameraParame", getDKCameraParame());
        intent.putExtra("DKCameraType", DKCameraType);//0相机 1 相册
        startActivityForResult(intent, 1000);
    }

    private String getDKCameraParame() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("watermarkId", waterMarkIdEdit.getText().toString());//6189ff78e733f156e4421a33
            jsonObject.put("address", addressEdit.getText().toString());
            jsonObject.put("remark", remarkEdit.getText().toString());
            jsonObject.put("pictureWidth", 700);//如果不传此参数默认为原始宽度。
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode && requestCode == 1000) {
            String dakaPictures = data.getStringExtra("dakaPictures");
            textView.setText(dakaPictures);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<PictureBean>>() {
            }.getType();
            List<PictureBean> list = gson.fromJson(dakaPictures, type);
            pictureAdapter.setData(list);
            originPictureAdapter.setData(list);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (configRel.getVisibility() == View.VISIBLE) {
                configRel.setVisibility(View.GONE);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}