package com.rkl.ticket.ticket_analysis.secondfragment.presenter;


import com.rkl.common_library.base.BasePresenter;
import com.rkl.common_library.constant.ServerException;
import com.rkl.common_library.model.QueryMutiOpenInfoModel;
import com.rkl.common_library.net.ApiFactory;
import com.rkl.common_library.net.RxSubscribe;
import com.rkl.common_library.util.TimeUtils;
import com.rkl.ticket.ticket_analysis.secondfragment.contact.AvgAnalysisContact;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/14 14:58
 */

public class ParityTrendPresenter extends BasePresenter<AvgAnalysisContact.IView> implements AvgAnalysisContact.Presenter{

    @Override
    public void getRecentOpenDatas(String ticketCode, String count) {
        Subscription subscription = ApiFactory.INSTANCE.getApiService().getMutiPeriodCheck(ticketCode, TimeUtils.getCurTimeString(), count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscribe<QueryMutiOpenInfoModel>() {
                    @Override
                    protected void _onNext(QueryMutiOpenInfoModel queryMutiOpenInfoModel) {
                        if (queryMutiOpenInfoModel.getShowapi_res_code()==0){
                            getView().onGetHistoryRecentTicketListSuccess(queryMutiOpenInfoModel.getShowapi_res_body().getResult());
                        }
                    }

                    @Override
                    protected void _onError(ServerException e) {

                    }
                });
        addSubscription(subscription);

    }
}
