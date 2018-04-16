package com.rkl.common_library.model;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 主要功能：
 * Created by rkl on 2018/3/9.
 * 修改历史：
 */

public abstract class AsyncTaskUtil extends AsyncTask<String,Void,AsyncTaskUtil.VersionInfo> {

    public static final String VERSIONURL = "http://1256app.com:8080/biz/getAppConfig?appid=jy063";

    @Override
    protected VersionInfo doInBackground(String... strings) {
        Log.i("tmd", "doInBackground: ");
        InputStream is=null;
        try {
            HttpURLConnection conn= (HttpURLConnection) new URL(VERSIONURL).openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.connect();
            if (conn.getResponseCode()==200){
                StringBuffer sb = new StringBuffer();
                int len=0;
                is=conn.getInputStream();
                byte[] buffer = new byte[1024];
                while ((len=is.read(buffer))!=-1){
                    sb.append(new String(buffer, 0, len));
                }
                String json = sb.toString();
                Log.i("tmd", "doInBackground: "+json);
                VersionInfo info = new Gson().fromJson(json, VersionInfo.class);
                return info;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(VersionInfo versionInfo) {
        super.onPostExecute(versionInfo);

        onResult(versionInfo);
    }

    public abstract void onResult(VersionInfo versionInfo);

    public static class VersionInfo{


        /**
         * success : true
         * AppConfig : {"PushKey":null,"AcceptCount":0,"AppId":"ceshi01","ShowWeb":"1","Del":"0","Url":"http://wefalpew-jk.nepallvyou.com/wap/","Remark":"接口说明：appid为唯一标示APP的字符串，调用前需要配置好。需要用到的返回值：【success】：布尔值，true 调用成功，false 请求失败，出错的情况一般就是appid传错了。【ShowWeb】：字符串，\"0\"不跳转， \"1\"跳转【PushKey】：字符串，推送用的key【Url】：字符串 跳转的url地址。"}
         */

        private boolean success;
        private AppConfigBean AppConfig;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public AppConfigBean getAppConfig() {
            return AppConfig;
        }

        public void setAppConfig(AppConfigBean AppConfig) {
            this.AppConfig = AppConfig;
        }

        public static class AppConfigBean {
            /**
             * PushKey : null
             * AcceptCount : 0
             * AppId : ceshi01
             * ShowWeb : 1
             * Del : 0
             * Url : http://wefalpew-jk.nepallvyou.com/wap/
             * Remark : 接口说明：appid为唯一标示APP的字符串，调用前需要配置好。需要用到的返回值：【success】：布尔值，true 调用成功，false 请求失败，出错的情况一般就是appid传错了。【ShowWeb】：字符串，"0"不跳转， "1"跳转【PushKey】：字符串，推送用的key【Url】：字符串 跳转的url地址。
             */

            private Object PushKey;
            private int AcceptCount;
            private String AppId;
            private String ShowWeb;
            private String Del;
            private String Url;
            private String Remark;

            public Object getPushKey() {
                return PushKey;
            }

            public void setPushKey(Object PushKey) {
                this.PushKey = PushKey;
            }

            public int getAcceptCount() {
                return AcceptCount;
            }

            public void setAcceptCount(int AcceptCount) {
                this.AcceptCount = AcceptCount;
            }

            public String getAppId() {
                return AppId;
            }

            public void setAppId(String AppId) {
                this.AppId = AppId;
            }

            public String getShowWeb() {
                return ShowWeb;
            }

            public void setShowWeb(String ShowWeb) {
                this.ShowWeb = ShowWeb;
            }

            public String getDel() {
                return Del;
            }

            public void setDel(String Del) {
                this.Del = Del;
            }

            public String getUrl() {
                return Url;
            }

            public void setUrl(String Url) {
                this.Url = Url;
            }

            public String getRemark() {
                return Remark;
            }

            public void setRemark(String Remark) {
                this.Remark = Remark;
            }
        }
    }
}
