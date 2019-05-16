package com.yxf.permissionutils;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.yxf.permissionutils.custom.PermissionsUtil;
import com.yxf.permissionutils.rxpermission.RxPermissionUtil;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE=0x30;

    private PermissionsUtil util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        util=new PermissionsUtil(this);

        findViewById(R.id.txt_granted_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grantAll();
            }
        });

        findViewById(R.id.txt_granted_each).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grantEach();
            }
        });

        findViewById(R.id.txt_granted_custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customGrant();
            }
        });
    }

    public void grantAll(){
        new RxPermissionUtil()
                .init(this)
                .grantAllPermisison(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void grantEach(){
        new RxPermissionUtil()
                .init(this)
                .grantedEachPermisison(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


    public void customGrant(){
        if (util.isLackPermissions()) {
            PermissionActivity.startActivityForResult(this,REQUEST_CODE);
        }
    }
}
