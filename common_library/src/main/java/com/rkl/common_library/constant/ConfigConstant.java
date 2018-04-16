package com.rkl.common_library.constant;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.rkl.common_library.base.BaseApplication;

import java.util.logging.Logger;

import retrofit2.http.Url;


/**
 * Created by rkl on 2017/3/6.
 */

public class ConfigConstant {

    /*运行环境配置*/
    public static final String TAG="Ticket-Analysis";//logger的tag
    public static boolean isRelease=true;//是否是正式版

    /*目录名配置*/
    public static final String ROOT_PATH ="/root/";//根目录
    public static final String IMAGE_PATH = ROOT_PATH + "image/";//图片目录
    public static final String DOWNLOAD_PATH = ROOT_PATH + "download/";//下载目录
    public static final String DOWNLOAD_VIDEO_PATH = ROOT_PATH + "video/";//视频下载目录




    /*Orm数据库配置文件*/
    //数据库缓存的地址和数据库名字
    public static final String DB_NAME_PATH = BaseApplication.getmAppContext().getDir("db", Context.MODE_PRIVATE).getAbsolutePath();
    public static final String DB_NAME = "CommonOrm.db";

    /*oss配置*/
    public static final String OSS_ROOTURL = "http://android-app-sh.oss-cn-shanghai.aliyuncs.com/personal/app001/icon/";
    public static  String[] ICONURL = {"daletou","3d","pailiesan","pailiewu","qilecai","qixingcai","shuangseqiu", "liuchanban","sichangjinqiu",
            "shisichang","anhui11","beijing11","fujian11","guangdong11","gansu11","guangxi11","guizhou11","hebei11","heilongjiang11","hubei11"};
    public static final String[] ANALYSISPIC = {OSS_ROOTURL+"analysis1.png",OSS_ROOTURL+"analysis2.png",OSS_ROOTURL+"analysis3.png",OSS_ROOTURL+"analysis4.png"};

    public static String[] getICONURL(){
        String[] Url = new String[20];
        for (int i = 0; i <ICONURL.length ; i++) {
            Url[i] = OSS_ROOTURL + ICONURL[i] + ".png";
        }
        return Url;
    }

}
