package com.rkl.common_library.model;

import java.util.List;

/**
 * 主要功能：
 * Created by rkl on 2018/4/2.
 * 修改历史：
 */

public class QueryMutiOpenInfoModel extends BaseModel {

    /**
     * showapi_res_body : {"result":[{"timestamp":1522499600,"expect":"2018036","time":"2018-03-31 20:33:20","name":"超级大乐透","code":"dlt","openCode":"11,17,18,21,27+07,08"},{"timestamp":1522240400,"expect":"2018035","time":"2018-03-28 20:33:20","name":"超级大乐透","code":"dlt","openCode":"05,07,10,12,24+07,11"},{"timestamp":1522067600,"expect":"2018034","time":"2018-03-26 20:33:20","name":"超级大乐透","code":"dlt","openCode":"08,11,22,27,32+03,07"},{"timestamp":1521894800,"expect":"2018033","time":"2018-03-24 20:33:20","name":"超级大乐透","code":"dlt","openCode":"02,03,19,23,34+04,09"},{"timestamp":1521635600,"expect":"2018032","time":"2018-03-21 20:33:20","name":"超级大乐透","code":"dlt","openCode":"07,10,17,32,35+05,12"},{"timestamp":1521462800,"expect":"2018031","time":"2018-03-19 20:33:20","name":"超级大乐透","code":"dlt","openCode":"03,08,13,17,23+05,11"},{"timestamp":1521290000,"expect":"2018030","time":"2018-03-17 20:33:20","name":"超级大乐透","code":"dlt","openCode":"05,18,21,28,32+09,11"},{"timestamp":1521030800,"expect":"2018029","time":"2018-03-14 20:33:20","name":"超级大乐透","code":"dlt","openCode":"03,09,21,25,29+01,08"},{"timestamp":1520858000,"expect":"2018028","time":"2018-03-12 20:33:20","name":"超级大乐透","code":"dlt","openCode":"04,19,25,30,35+04,07"},{"timestamp":1520685200,"expect":"2018027","time":"2018-03-10 20:33:20","name":"超级大乐透","code":"dlt","openCode":"01,18,20,21,26+04,11"}],"ret_code":0}
     */

    private ShowapiResBodyBean showapi_res_body;

    @Override
    public String toString() {
        return "QueryMutiOpenInfoModel{" +
                "showapi_res_body=" + showapi_res_body +
                '}';
    }

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {
        /**
         * result : [{"timestamp":1522499600,"expect":"2018036","time":"2018-03-31 20:33:20","name":"超级大乐透","code":"dlt","openCode":"11,17,18,21,27+07,08"},{"timestamp":1522240400,"expect":"2018035","time":"2018-03-28 20:33:20","name":"超级大乐透","code":"dlt","openCode":"05,07,10,12,24+07,11"},{"timestamp":1522067600,"expect":"2018034","time":"2018-03-26 20:33:20","name":"超级大乐透","code":"dlt","openCode":"08,11,22,27,32+03,07"},{"timestamp":1521894800,"expect":"2018033","time":"2018-03-24 20:33:20","name":"超级大乐透","code":"dlt","openCode":"02,03,19,23,34+04,09"},{"timestamp":1521635600,"expect":"2018032","time":"2018-03-21 20:33:20","name":"超级大乐透","code":"dlt","openCode":"07,10,17,32,35+05,12"},{"timestamp":1521462800,"expect":"2018031","time":"2018-03-19 20:33:20","name":"超级大乐透","code":"dlt","openCode":"03,08,13,17,23+05,11"},{"timestamp":1521290000,"expect":"2018030","time":"2018-03-17 20:33:20","name":"超级大乐透","code":"dlt","openCode":"05,18,21,28,32+09,11"},{"timestamp":1521030800,"expect":"2018029","time":"2018-03-14 20:33:20","name":"超级大乐透","code":"dlt","openCode":"03,09,21,25,29+01,08"},{"timestamp":1520858000,"expect":"2018028","time":"2018-03-12 20:33:20","name":"超级大乐透","code":"dlt","openCode":"04,19,25,30,35+04,07"},{"timestamp":1520685200,"expect":"2018027","time":"2018-03-10 20:33:20","name":"超级大乐透","code":"dlt","openCode":"01,18,20,21,26+04,11"}]
         * ret_code : 0
         */

        private int ret_code;
        private List<ResultBean> result;

        @Override
        public String toString() {
            return "ShowapiResBodyBean{" +
                    "ret_code=" + ret_code +
                    ", result=" + result +
                    '}';
        }

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

            @Override
            public String toString() {
                return "ResultBean{" +
                        "timestamp=" + timestamp +
                        ", expect='" + expect + '\'' +
                        ", time='" + time + '\'' +
                        ", name='" + name + '\'' +
                        ", code='" + code + '\'' +
                        ", openCode='" + openCode + '\'' +
                        '}';
            }

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
