package com.rkl.ticket.ticket_analysis.secondfragment.presenter;

import android.util.Log;

import com.rkl.common_library.base.BasePresenter;
import com.rkl.common_library.constant.ServerException;
import com.rkl.common_library.model.AllTicketsModel;
import com.rkl.common_library.net.ApiFactory;
import com.rkl.common_library.net.RxSubscribe;
import com.rkl.common_library.util.ToastUtils;
import com.rkl.ticket.ticket_analysis.secondfragment.contact.AnalysisContact;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 主要功能：
 * Created by rkl on 2018/4/4.
 * 修改历史：
 */

public class AnalysisPresenter extends BasePresenter<AnalysisContact.IVew> implements AnalysisContact.Presenter {
    @Override
    public void getAllTicketInfo() {
        Subscription subscription= ApiFactory.INSTANCE.getApiService().getTicketList(null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscribe<AllTicketsModel>() {
                    @Override
                    protected void _onNext(AllTicketsModel allTicketsModel) {
                        Log.i("tmd", "_onNext: "+allTicketsModel);
                        if (allTicketsModel.getShowapi_res_code()==0){
                            getView().setAllTicketInfo(allTicketsModel);
                        }else {
                            ToastUtils.showShortToast(getView().getContext(),"服务器异常！！！！");
                        }
                    }

                    @Override
                    protected void _onError(ServerException e) {

                    }

                });
        addSubscription(subscription);

    }



}
