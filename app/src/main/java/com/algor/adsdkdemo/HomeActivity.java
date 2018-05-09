package com.algor.adsdkdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.algor.adsdk.AlgorSdk;
import com.algor.adsdk.DataManager;
import com.algor.adsdk.Interface.AdPrepareListener;
import com.algor.adsdk.Utils.DeviceUtils;
import com.algor.adsdk.Utils.IntenetUtil;
import com.algor.adsdk.Utils.LocationUtils;
import com.algor.adsdk.Utils.LogUtils;
import com.algor.adsdk.modul.AdResoucesBean;
import com.algor.iconad.AdIconAPI;
import com.algor.iconad.bean.Adsense;
import com.algor.iconad.widgets.AdIconView;

import java.util.List;
import java.util.Random;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class HomeActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, View.OnClickListener {

    private static final int PERMISSION_READ_EXTERNAL_STORAGE = 888;

    private Button btn_show_ad;
    private Button btn_show_ad_h;
    private Button btn_show_ad_auto;
    private Button btn_prepare_ad_h;
    private Button btn_prepare_ad;
    private Button btn_prepare_ad_auto;
    private Button btn_prepare_icon;
    private Button btn_prepare_icon_h;
    private Button btn_prepare_icon_auto;

    private AdPrepareListener adPrepareListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_show_ad = findViewById(R.id.btn_show_ad);
        btn_show_ad_h = findViewById(R.id.btn_show_ad_h);
        btn_show_ad_auto = findViewById(R.id.btn_show_ad_auto);
        btn_prepare_ad_h = findViewById(R.id.btn_prepare_ad_h);
        btn_prepare_ad = findViewById(R.id.btn_prepare_ad);
        btn_prepare_ad_auto = findViewById(R.id.btn_prepare_ad_auto);
        btn_prepare_icon = findViewById(R.id.btn_prepare_icon);
        btn_prepare_icon_h = findViewById(R.id.btn_prepare_icon_h);
        btn_prepare_icon_auto = findViewById(R.id.btn_prepare_icon_auto);

        btn_show_ad.setOnClickListener(this);
        btn_show_ad_h.setOnClickListener(this);
        btn_show_ad_auto.setOnClickListener(this);
        btn_prepare_ad_h.setOnClickListener(this);
        btn_prepare_ad.setOnClickListener(this);
        btn_prepare_ad_auto.setOnClickListener(this);
        btn_prepare_icon.setOnClickListener(this);
        btn_prepare_icon_h.setOnClickListener(this);
        btn_prepare_icon_auto.setOnClickListener(this);


        // AlgorSdk init
        AlgorSdk.getInstance().init(HomeActivity.this.getApplication());


        adPrepareListener = new AdPrepareListener() {
            @Override
            public void onIconPrepareOver(int code, Adsense adsense, List<AdResoucesBean> adResoucesBean) {
                LogUtils.e("AlgorSdk", "onPrepareOver-----code=" + code + "adResoucesBean.size()=" + adResoucesBean.size());
                switch (code) {
                    case 0:
                        Toast.makeText(HomeActivity.this, "ICON广告素材准备成功", Toast.LENGTH_SHORT).show();
                        int viewType = new Random().nextInt(4);
                        AdIconView adIconView = AlgorSdk.getInstance().createIconView(HomeActivity.this, adsense.getPlacement_id(), adResoucesBean, viewType);
                        adIconView.setCta_text(adIconView.getCta_btn_text() + ":" + viewType);
                        ((LinearLayout) findViewById(R.id.linearlayout)).addView(adIconView, 0);
                        break;
                    case 1000:
                        Toast.makeText(HomeActivity.this, "ICON广告素材准备失败", Toast.LENGTH_SHORT).show();
                        break;
                }

            }

            @Override
            public void onPrepareOver(int code, Adsense adsense) {
                LogUtils.e("AlgorSdk", "onPrepareOver-----code=" + code + "adsense=" + adsense);
                switch (code) {
                    case 0:
                        if (adsense.getPlacement_orientation() == 1) {
                            Toast.makeText(HomeActivity.this, "横屏广告素材准备成功", Toast.LENGTH_SHORT).show();
                        } else if (adsense.getPlacement_orientation() == 2) {
                            Toast.makeText(HomeActivity.this, "竖屏广告素材准备成功", Toast.LENGTH_SHORT).show();
                        } else if (adsense.getPlacement_orientation() == -1) {
                            Toast.makeText(HomeActivity.this, "广告素材准备成功", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };

        if (EasyPermissions.hasPermissions(this, AlgorSdk.permissions)) {
            LogUtils.e("AlgorSdk", "已拥有文件存储权限");
        } else {
            LogUtils.e("AlgorSdk", "申请文件存储权限");
            EasyPermissions.requestPermissions(this, "", PERMISSION_READ_EXTERNAL_STORAGE, AlgorSdk.permissions);
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_show_ad) {
            boolean prepared = AlgorSdk.getInstance().hasPreparedAD(HomeActivity.this, "4");
            LogUtils.e("AlgorSdk", "pid 4 hasPreparedAD " + prepared);
            AlgorSdk.getInstance().showInterstitialAD(HomeActivity.this, "4");
        } else if (view.getId() == R.id.btn_show_ad_h) {
            boolean prepared = AlgorSdk.getInstance().hasPreparedAD(HomeActivity.this, "3");
            LogUtils.e("AlgorSdk", "pid 3 hasPreparedAD " + prepared);
            AlgorSdk.getInstance().showInterstitialAD(HomeActivity.this, "3");
        } else if (view.getId() == R.id.btn_show_ad_auto) {
            boolean prepared = AlgorSdk.getInstance().hasPreparedAD(HomeActivity.this, "6");
            LogUtils.e("AlgorSdk", "pid 5 hasPreparedAD " + prepared);
            AlgorSdk.getInstance().showInterstitialAD(HomeActivity.this, "6");
        } else if (view.getId() == R.id.btn_prepare_ad) {
            AlgorSdk.getInstance().prepareAD(HomeActivity.this, "4", adPrepareListener);
        } else if (view.getId() == R.id.btn_prepare_ad_h) {
            AlgorSdk.getInstance().prepareAD(HomeActivity.this, "3", adPrepareListener);
        } else if (view.getId() == R.id.btn_prepare_icon) {
            AlgorSdk.getInstance().prepareAD(HomeActivity.this, "2", adPrepareListener);
        } else if (view.getId() == R.id.btn_prepare_icon_h) {
            AlgorSdk.getInstance().prepareAD(HomeActivity.this, "1", adPrepareListener);
        } else if (view.getId() == R.id.btn_prepare_ad_auto) {
            AlgorSdk.getInstance().prepareAD(HomeActivity.this, "6", adPrepareListener);
        } else if (view.getId() == R.id.btn_prepare_icon_auto) {
            AlgorSdk.getInstance().prepareAD(HomeActivity.this, "5", adPrepareListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) { //某些权限已被授予
        if (requestCode == PERMISSION_READ_EXTERNAL_STORAGE) {
            LogUtils.e("kkk", "用户允许了文件存储权限");
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) { //某些权限已被拒绝
        if (requestCode == PERMISSION_READ_EXTERNAL_STORAGE) {
            //显示dialog来提示用户去设置
            new AppSettingsDialog.Builder(this)
                    .setRationale("没有该权限，此应用程序可能无法正常工作。打开应用设置屏幕以修改应用权限")
                    .setTitle("必需权限")
                    .build()
                    .show();
        }
    }

}
