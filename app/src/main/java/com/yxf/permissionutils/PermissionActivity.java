package com.yxf.permissionutils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.pedant.SweetAlert.Type;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import com.yxf.permissionutils.custom.PermissionsUtil;

public class PermissionActivity extends AppCompatActivity {

    public static final int PERMISSIONS_GRANTED = 0x10;
    public static final int PERMISSIONS_DENIED = 0x10;

    private static final String PACKAGE_URL_SCHEME = "package:"; // 方案
    private boolean isRequstCheck = true;//是否需要检测权限
    private PermissionsUtil permissionsUtil;
    private Context mContext;

    public static void startActivityForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, PermissionActivity.class);
        ActivityCompat.startActivityForResult(activity,intent, requestCode,null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        mContext=this;
        permissionsUtil = new PermissionsUtil(this);

        if (isRequstCheck) {
            boolean lackPermissions = permissionsUtil.isLackPermissions();
            if (lackPermissions) {
                permissionsUtil.requestPermission();
            } else {
                setResult(PERMISSIONS_GRANTED);
                finish();
            }
        } else {
            isRequstCheck = true;
        }
    }

    /**
     * 回调处理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionsUtil.REQUEST_CODE && !permissionsUtil.isLackPermissions()) {
            allPermissionGranted();
            isRequstCheck=false;
        }else {
            isRequstCheck=true;
            showSweetdialog();
        }
    }

    /**
     * 提示用户在设置中打开
     */
    private void showSweetdialog() {
        final SweetAlertDialog dialog = new SweetAlertDialog(mContext, Type.NORMAL_TYPE);
        dialog.setTitleText(getString(R.string.string_help));
        dialog.setCancelText(getString(R.string.string_quit));
        dialog.setConfirmText(getString(R.string.string_settings));
        dialog.setContentText(getString(R.string.string_help_text));
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                startAppSetting();
                dialog.dismiss();
            }
        });
        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                setResult(PERMISSIONS_DENIED);
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    /**
     * 所有权限都已授权的处理
     */
    public void allPermissionGranted() {
        setResult(PERMISSIONS_GRANTED);
        finish();
    }

    /**
     * 打开设置界面
     */
    private void startAppSetting(){
        Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        intent.setData(Uri.parse("package："+getPackageName()));
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }
}
