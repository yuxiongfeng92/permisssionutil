package com.yxf.permissionutils.rxpermission;

import android.annotation.SuppressLint;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import androidx.fragment.app.FragmentActivity;
import io.reactivex.functions.Consumer;

/**
 * Created by yuxiongfeng.
 * Date: 2019/5/16
 */
public class RxPermissionUtil {

    private RxPermissions rxPermissions;

    public RxPermissionUtil init(FragmentActivity activity) {
        rxPermissions = new RxPermissions(activity);
        return this;
    }

    @SuppressLint("CheckResult")
    public void grantAllPermisison(String... permisisons) {
        rxPermissions
                .request(permisisons)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) {
                        if (granted) {
                            // All requested permissions are granted
                        } else {
                            // At least one permission is denied
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void grantedEachPermisison(String... permissions) {
        rxPermissions.requestEach(permissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) {
                        if (permission.granted) {
                            // `permission.name` is granted !
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // Denied permission without ask never again
                        } else {
                            // Denied permission with ask never again
                        }
                    }
                });
    }

}


