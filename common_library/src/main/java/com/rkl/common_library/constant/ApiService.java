package com.rkl.common_library.constant;

import com.rkl.common_library.model.AllTicketsModel;
import com.rkl.common_library.model.NewsestOpenTicketModel;
import com.rkl.common_library.model.QueryMutiOpenInfoModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**Retrofit网络请求接口
 * Created by rkl on 2018/1/12.
 */
public interface ApiService {
    /**
     * 查询支持彩票类别
     *
     * @param extra
     * @return
     */
    @FormUrlEncoded
    @POST("44-6")
    Observable<AllTicketsModel> getTicketList(@Field("extra") String extra);

    /**
     * 最新开奖参数
     *
     * @param code （非必须）彩票类型  彩票类型，查询多个以“|”分隔，无则返回全部彩票最新开奖信息
     * @return
     */
    @FormUrlEncoded
    @POST("44-1")
    Observable<NewsestOpenTicketModel> getNewestOpen(@Field("code") String code);

    /**
     * 多期开奖查询
     *
     * @param code    彩票类型
     * @param endTime （非必须） 截止时间，格式为“2015-02-26 21:35:00”的形式。该参数为过滤条件，
     *                表示只查询该时间前的开奖信息，无则使用服务器当前时间
     * @param count   （非必须） 取多少条开奖数据,最多支持每次50条
     * @return
     */
    @FormUrlEncoded
    @POST("44-2")
    Observable<QueryMutiOpenInfoModel> getMutiPeriodCheck(@Field("code") String code,
                                                          @Field("endTime") String endTime,
                                                          @Field("count") String count);

    /**
     * 单期开奖查询
     *
     * @param code   彩票类型
     * @param expect (非必须)第几期彩票。无则表示查询最新一期
     * @return
     */
    @FormUrlEncoded
    @POST("44-2")
    Observable<QueryMutiOpenInfoModel> getSinglePeroidCheck(@Field("code") String code,
                                                                        @Field("expect") String expect);

}



