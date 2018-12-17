package com.hrl.chaui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hrl.chaui.R;
import com.hrl.chaui.util.LogUtil;
import com.hrl.chaui.util.PermissionUtil;
import com.hrl.chaui.widget.SetPermissionDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        requestPermisson();
    }


    private void requestPermisson(){
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission
                .request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,//存储权限
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                )
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            LogUtil.d("splash----所有权限都被同意");
                            startActivity(new Intent(SplashActivity.this,ChatActivity.class));

                         } else {
                            LogUtil.d("splash----至少一个权限被拒绝");

                            SetPermissionDialog mSetPermissionDialog = new SetPermissionDialog(SplashActivity.this);
                            mSetPermissionDialog.show();
                            mSetPermissionDialog.setConfirmCancelListener(new SetPermissionDialog.OnConfirmCancelClickListener() {
                                @Override
                                public void onLeftClick() {

                                    finish();
                                }

                                @Override
                                public void onRightClick() {

                                    PermissionUtil.startSetPermissionActivity(SplashActivity.this);
                                    finish();
                                }
                            });
                        }
                    }
                });
    }

}