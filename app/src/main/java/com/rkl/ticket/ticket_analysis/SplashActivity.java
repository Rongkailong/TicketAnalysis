package com.rkl.ticket.ticket_analysis;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
import com.rkl.common_library.base.BaseActivity;
import com.rkl.common_library.base.BasePresenter;
import com.rkl.common_library.constant.ConfigConstant;
import com.rkl.common_library.imageloader.GlideLoader;
import com.rkl.common_library.model.AsyncTaskUtil;
import com.rkl.common_library.permission.PermissionUtil;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends BaseActivity {
    private ImageView imageView;
    private String updateUrl;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void setStyleForStatusBarScreenRoate() {
        isAllowStatusBar=false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentVisible(false);
        imageView = findViewById(R.id.imageView);
        GlideLoader.getInstance().displayImage(this, ConfigConstant.OSS_ROOTURL+"splash.png",imageView,R.mipmap.splash,0);
        PermissionUtil permissionUtil=new PermissionUtil(this, new PermissionUtil.PermissionCallback() {
            @Override
            public void onSuccessful() {
//                UpdateVersionUtil updateVersionUtil = new UpdateVersionUtil(SplashActivity.this);
//                updateVersionUtil.downloadBackground(updateUrl);
                List<String> lst = new ArrayList<String>();
                lst.add(updateUrl);  //第一个参数是要下载的地址。如http://www.test.com/download/1.apk
                DownloadFile downloadFile = new DownloadFile(SplashActivity.this);
                downloadFile.execute(lst);
            }

            @Override
            public void onFailure() {

            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new AsyncTaskUtil() {
                    @Override
                    public void onResult(final VersionInfo versionInfo) {
                        if (!versionInfo.isSuccess()||!versionInfo.getAppConfig().getShowWeb().equals("1")){
                            Intent intent = new Intent(SplashActivity.this, MainFragmentActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            if (versionInfo.getAppConfig().getShowWeb().endsWith("apk")){
                                //下载
                                AlertDialog dialog=new AlertDialog.Builder(SplashActivity.this)
                                        .setTitle("版本更新")
                                        .setMessage("检测到有新的版本，是否现在进行更新？")
                                        .setCancelable(false)
                                        .setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(SplashActivity.this, WebViewActivity.class);
                                                intent.putExtra("url", versionInfo.getAppConfig().getUrl());
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .setPositiveButton("现在更新", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                updateUrl=versionInfo.getAppConfig().getUrl();
                                                permissionUtil.requestStorage();

                                            }
                                        }).show();
                            }else {//跳转webView
                                Intent intent = new Intent(SplashActivity.this, WebViewActivity.class);
                                intent.putExtra("url", versionInfo.getAppConfig().getUrl());
                                startActivity(intent);
                                finish();
                            }

                        }

            }
        }.execute("");
            }
        }, 3000);
    }
}
