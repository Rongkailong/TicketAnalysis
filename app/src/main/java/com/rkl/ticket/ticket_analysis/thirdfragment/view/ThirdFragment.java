package com.rkl.ticket.ticket_analysis.thirdfragment.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.rkl.common_library.base.BaseFragment;
import com.rkl.common_library.base.BasePresenter;
import com.rkl.common_library.constant.ConfigConstant;
import com.rkl.common_library.imageloader.GlideLoader;
import com.rkl.common_library.model.AllTicketsModel;
import com.rkl.common_library.recyclerview.adapter.CommonAdapter;
import com.rkl.common_library.recyclerview.base.ViewHolder;
import com.rkl.ticket.ticket_analysis.R;
import com.rkl.ticket.ticket_analysis.secondfragment.contact.AnalysisContact;
import com.rkl.ticket.ticket_analysis.secondfragment.presenter.AnalysisPresenter;
import com.rkl.ticket.ticket_analysis.secondfragment.view.AvgAnalysisActivity;
import com.rkl.ticket.ticket_analysis.secondfragment.view.ParityTrendAnalysisActivity;
import com.rkl.ticket.ticket_analysis.secondfragment.view.RateAnalysisActivity;
import com.rkl.ticket.ticket_analysis.secondfragment.view.SumAnalysisActivity;

/**
 * 主要功能：
 * Created by rkl on 2018/4/2.
 * 修改历史：
 */

public class ThirdFragment extends BaseFragment<AnalysisPresenter> implements AnalysisContact.IVew{
    private RecyclerView recyclerView;
    private CommonAdapter<AllTicketsModel.ShowapiResBodyBean.ResultBean> adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_analysis;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler);
        initAdapter();
        presenter.getAllTicketInfo();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adapter=new CommonAdapter<AllTicketsModel.ShowapiResBodyBean.ResultBean>(getContext(),R.layout.item_tickets_list) {
            @Override
            protected void convert(ViewHolder holder, AllTicketsModel.ShowapiResBodyBean.ResultBean resultBean, int position) {
                holder.setText(R.id.tv, resultBean.getDescr());
                ImageView imageView = holder.getView(R.id.imageView);
                final String iconUrl = ConfigConstant.getICONURL()[position];
                GlideLoader.getInstance().displayImage(getContext(),iconUrl,imageView ,R.mipmap.anhuikuaisan,R.mipmap.anhuikuaisan);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("code",resultBean.getCode());
                        bundle.putString("iconUrl",iconUrl);
                        goToActivity(CodeForcastActivity.class,bundle);

                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected AnalysisPresenter createPresenter() {
        return new AnalysisPresenter();
    }

    @Override
    public void failed(String msg) {

    }

    @Override
    public void setAllTicketInfo(AllTicketsModel allTicketsModel) {
        adapter.replaceDataAll(allTicketsModel.getShowapi_res_body().getResult().subList(0,20));

    }
}
