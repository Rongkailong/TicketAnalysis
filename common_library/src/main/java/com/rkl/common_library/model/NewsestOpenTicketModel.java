package com.rkl.common_library.model;

import java.util.List;

/**
 * 主要功能：
 * Created by rkl on 2018/4/2.
 * 修改历史：
 */

public class NewsestOpenTicketModel extends BaseModel {

    /**
     * showapi_res_body : {"result":[{"timestamp":1522499600,"expect":"2018036","time":"2018-03-31 20:33:20","name":"超级大乐透","code":"dlt","openCode":"11,17,18,21,27+07,08"}],"ret_code":0}
     */

    private ShowapiResBodyBean showapi_res_body;

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {
        /**
         * result : [{"timestamp":1522499600,"expect":"2018036","time":"2018-03-31 20:33:20","name":"超级大乐透","code":"dlt","openCode":"11,17,18,21,27+07,08"}]
         * ret_code : 0
         */

        private int ret_code;
        private List<ResultBean> result;

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * timestamp : 1522499600
             * expect : 2018036
             * time : 2018-03-31 20:33:20
             * name : 超级大乐透
             * code : dlt
             * openCode : 11,17,18,21,27+07,08
             */

            private int timestamp;
            private String expect;
            private String time;
            private String name;
            private String code;
            private String openCode;

            public int getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }

            public String getExpect() {
                return expect;
            }

            public void setExpect(String expect) {
                this.expect = expect;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getOpenCode() {
                return openCode;
            }

            public void setOpenCode(String openCode) {
                this.openCode = openCode;
            }
        }
    }
}
