package com.rkl.ticket.ticket_analysis.firstfragment.presenter;

import android.util.Log;

import com.rkl.common_library.base.BasePresenter;
import com.rkl.common_library.constant.ServerException;
import com.rkl.common_library.model.QueryMutiOpenInfoModel;
import com.rkl.common_library.net.ApiFactory;
import com.rkl.common_library.net.RxSubscribe;
import com.rkl.ticket.ticket_analysis.firstfragment.contact.TicketOpenInfoContact;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 主要功能：
 * Created by rkl on 2018/4/3.
 * 修改历史：
 */

public class TicketOpenInfoPresenter extends BasePresenter<TicketOpenInfoContact.IView> implements TicketOpenInfoContact.Presenter{
    private String code;
    private SmartRefreshLayout refreshLayout;

    @Override
    public void initRefreshLayout(SmartRefreshLayout refreshLayout) {
        this.refreshLayout=refreshLayout;
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getTicketOpenInfo(code);

            }
        });
    }

    @Override
    public void getTicketOpenInfo(String code) {
        this.code=code;
         Subscription subscription = ApiFactory.INSTANCE.getApiService().getMutiPeriodCheck(code, null, null)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new RxSubscribe<QueryMutiOpenInfoModel>() {
                     @Override
                     protected void _onNext(QueryMutiOpenInfoModel queryMutiOpenInfoModel) {
                         if (queryMutiOpenInfoModel.getShowapi_res_code()==0){
                             Log.i("tmd", "_onNext: "+queryMutiOpenInfoModel);
                             if (refreshLayout!=null)
                                 refreshLayout.finishRefresh();
                             getView().setTicketOpenInfo(queryMutiOpenInfoModel);
                         }
                     }

                     @Override
                     protected void _onError(ServerException e) {

                     }
                 });
         addSubscription(subscription);

    }
}
