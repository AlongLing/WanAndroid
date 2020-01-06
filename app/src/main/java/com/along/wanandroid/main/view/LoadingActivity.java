package com.along.wanandroid.main.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.along.wanandroid.R;
import com.along.wanandroid.basemvp.impl.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class LoadingActivity extends BaseActivity {

    private static final String TAG = "LoadingActivity";

    private String[] permissions = new String[] {Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private List<String> noPermissionList = new ArrayList<>();

    @Override
    protected int getXMLId() {
        return R.layout.activity_loading;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        requestPermission();
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (requestCode == 1) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            if (hasPermissionDismiss) {
                //有权限没有被允许.
                Toast.makeText(this, "权限被拒绝，有些功能会无法使用", Toast.LENGTH_LONG).show();
            }
            turnToMainActivity();
        }
    }

    //动态申请一些必要的权限。
    private void requestPermission() {
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                noPermissionList.add(permissions[i]);
            }
        }
        if (noPermissionList.size() > 0) {  //有权限没有通过，需要申请。
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {                            //所有权限都已经申请通过了。
            turnToMainActivity();
        }
    }

    private void turnToMainActivity() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(LoadingActivity.this, MainActivity.class));
        finish();
    }
}
