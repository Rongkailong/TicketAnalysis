package com.rkl.ticket.ticket_analysis.firstfragment.presenter;

import android.util.Log;

import com.rkl.common_library.base.BasePresenter;
import com.rkl.common_library.constant.ConfigConstant;
import com.rkl.common_library.constant.ServerException;
import com.rkl.common_library.model.AllTicketsModel;
import com.rkl.common_library.net.ApiFactory;
import com.rkl.common_library.net.RxSubscribe;
import com.rkl.common_library.util.ToastUtils;
import com.rkl.ticket.ticket_analysis.firstfragment.contact.FirstFragmentContact;
import com.rkl.ticket.ticket_analysis.firstfragment.view.GlideImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 主要功能：
 * Created by rkl on 2018/4/2.
 * 修改历史：
 */

public class FirstFragmentPresenter extends BasePresenter<FirstFragmentContact.IView> implements FirstFragmentContact.Persenter{
    private SmartRefreshLayout refreshLayout;
    @Override
    public void initBanner(Banner banner) {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<String> images = new ArrayList<>();
        images.add(ConfigConstant.OSS_ROOTURL+"banner1.png");
        images.add(ConfigConstant.OSS_ROOTURL+"banner2.png");
        images.add(ConfigConstant.OSS_ROOTURL+"banner3.png");
        images.add(ConfigConstant.OSS_ROOTURL+"banner4.png");
        images.add(ConfigConstant.OSS_ROOTURL+"banner5.png");
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Accordion);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void initRefreshLayout(SmartRefreshLayout refreshLayout) {
        this.refreshLayout=refreshLayout;
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getAllTicketInfo();
            }
        });

    }

    @Override
    public void getAllTicketInfo() {
        Subscription subscription= ApiFactory.INSTANCE.getApiService().getTicketList(null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscribe<AllTicketsModel>() {
                    @Override
                    protected void _onNext(AllTicketsModel allTicketsModel) {
                        Log.i("tmd", "_onNext: "+allTicketsModel);
                        refreshLayout.finishRefresh();
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
