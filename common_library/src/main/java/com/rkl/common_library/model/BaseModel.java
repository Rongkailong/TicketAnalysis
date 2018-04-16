package com.rkl.common_library.model;

/**
 * 主要功能：
 * Created by rkl on 2018/4/2.
 * 修改历史：
 */

public class BaseModel {

    /**
     * showapi_res_code : -6
     * showapi_res_error : 此服务不存在!
     * showapi_res_body : {}
     */

    private int showapi_res_code;
    private String showapi_res_error;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "showapi_res_code=" + showapi_res_code +
                ", showapi_res_error='" + showapi_res_error + '\'' +
                '}';
    }
}
