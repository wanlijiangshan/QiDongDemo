package com.android.qidong;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class PermissionUtil {

    public static final String KEY_READ_PHONE_STATE_PERMISSION = "key_read_phone_state_permission";

    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0x1000;

    public static boolean isAndroidVersonCode19() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return true;
        }

        return false;
    }

    public static boolean isAndroidVersonCode23() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true;
        }

        return false;
    }

    public static boolean isAndroidVersonCode26() {
        if (Build.VERSION.SDK_INT >= 26) {
            return true;
        }

        return false;
    }

    /**
     * 判断权限集合
     * permissions 权限数组
     * return true-表示没有改权限  false-表示权限已开启
     */
    public static boolean lacksPermissions(Context context) {
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.READ_PHONE_STATE};
        for (String permission : permissions) {
            if (lacksPermission(context, permission)) {
                Log.e("TAG", "-------没有开启权限");
                return true;
            }
        }
        Log.e("TAG", "-------权限已开启");
        return false;
    }

    /**
     * 判断是否缺少权限
     */
    private static boolean lacksPermission(Context mContexts, String permission) {
        return ContextCompat.checkSelfPermission(mContexts, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    public static void checkPermission(FragmentActivity activity, final CallLisenter callLisenter) {
        new RxPermissions(activity)
                .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.READ_PHONE_STATE
                )
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        //申请的权限全部允许
                        if (callLisenter != null) {
                            callLisenter.onCall(aBoolean);
                        }

                        if (aBoolean) {
                            //申请的权限全部允许

                        } else {
                            //只要有一个权限被拒绝，就会执行
                        }
                    }
                });
    }

    public static void checkWriteStoragePermission(FragmentActivity activity, final CallLisenter callLisenter) {
        new RxPermissions(activity)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        //申请的权限全部允许
                        if (callLisenter != null) {
                            callLisenter.onCall(aBoolean);
                        }

                        if (aBoolean) {

                        } else {
                            //只要有一个权限被拒绝，就会执行
                        }
                    }
                });
    }

    public static void checkAudioPermission(FragmentActivity activity, final CallLisenter callLisenter) {
        new RxPermissions(activity)
                .request(Manifest.permission.RECORD_AUDIO
                )
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        //申请的权限全部允许
                        if (callLisenter != null) {
                            callLisenter.onCall(aBoolean);
                        }

                        if (aBoolean) {

                        } else {
                            //只要有一个权限被拒绝，就会执行
                        }
                    }
                });
    }


    public interface CallLisenter {
        void onCall(boolean isAccept);
    }
}
