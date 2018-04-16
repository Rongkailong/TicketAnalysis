package com.rkl.ticket.ticket_analysis;

import com.rkl.common_library.base.BaseApplication;
import cn.jpush.android.api.JPushInterface;

/**
 * 主要功能：
 * Created by rkl on 2018/4/4.
 * 修改历史：
 */

public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
//        极光推送服务
        JPushInterface.setDebugMode(true);
        JPushInterface.init(getmAppContext());
    }
}

