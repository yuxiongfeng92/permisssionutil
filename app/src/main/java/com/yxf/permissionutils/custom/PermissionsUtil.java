package com.yxf.permissionutils.custom;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.content.pm.PackageManager.PERMISSION_DENIED;


/**
 * Created by yuxiongfeng.
 * Date: 2019/5/16
 */
public class PermissionsUtil {

    private String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static final int REQUEST_CODE=0x1000;

    private Context mCtx;

    public PermissionsUtil(Context context) {
        this.mCtx = context;
    }

    /**
     * 检查某个权限是否确实
     *
     * @param permission true  表示缺失
     * @return
     */
    private boolean lackPermission(String permission) {
        return ContextCompat.checkSelfPermission(mCtx, permission) == PERMISSION_DENIED;
    }

    /**
     * 检查是否缺少权限
     * @return
     */
    public boolean isLackPermissions() {
        for (String permission : permissions) {
            boolean lackPermission = lackPermission(permission);
            if (lackPermission) {
                return true;
            }
        }
        return false;
    }

    /**
     * 缺少权限的时候授权
     */
    public void requestPermission(){
        ActivityCompat.requestPermissions((Activity) mCtx,permissions,REQUEST_CODE);
    }

}
