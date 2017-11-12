package com.sample.ui.activity.util;

import android.Manifest;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sample.R;
import com.sample.ui.activity.BaseActivity;
import com.siberiadante.lib.manager.PermissionManager;
import com.siberiadante.lib.util.SDToastUtil;

public class PermissionManagerActivity extends BaseActivity {
    private Button record;
    private Button sd;

    @Override
    public int setLayoutId() {
        return R.layout.activity_permission_manager;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initPermission();
        initView();
        initListener();
    }

    @Override
    public void initData() {

    }

    private void initPermission() {
        //同时申请多个权限
        PermissionManager.getInstance(getApplicationContext()).execute(this,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //请求单个，显示对话框的方式
        PermissionManager.getInstance(getApplicationContext()).executeDialog(this, Manifest.permission.RECORD_AUDIO,
                PermissionManager.getInstance(getApplicationContext()).new Builder(this)
                        .setMessage("应用需要获取您的录音权限，是否授权？")
                        .setTitle(getString(R.string.app_name))
                        .setIcon(R.mipmap.ic_launcher)
                        .setOk("OK")
                        .setCancel("CANCEL"));
    }

    private void initView() {
        record = (Button) findViewById(R.id.btn_record);
        sd = (Button) findViewById(R.id.btn_SD);
    }

    private void initListener() {
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionManager.getInstance(getApplicationContext()).getGrantedInfo(Manifest.permission.RECORD_AUDIO)) {
                    SDToastUtil.toast("录音权限已经获取");
                } else {
                    SDToastUtil.toast("你还没有获取录音权限");
                }
            }
        });
        sd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionManager.getInstance(getApplicationContext()).getGrantedInfo(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    SDToastUtil.toast("SD权限已经获取");
                } else {
                    SDToastUtil.toast("你还没有获取SD权限");
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.getInstance(getApplicationContext()).onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
