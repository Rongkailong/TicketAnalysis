package com.rkl.ticket.ticket_analysis.secondfragment.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.rkl.common_library.base.BaseActivity;
import com.rkl.common_library.constant.ConfigConstant;
import com.rkl.common_library.imageloader.GlideLoader;
import com.rkl.common_library.model.AllTicketsModel;
import com.rkl.common_library.recyclerview.adapter.CommonAdapter;
import com.rkl.common_library.recyclerview.base.ViewHolder;
import com.rkl.ticket.ticket_analysis.R;
import com.rkl.ticket.ticket_analysis.secondfragment.contact.AnalysisContact;
import com.rkl.ticket.ticket_analysis.secondfragment.presenter.AnalysisPresenter;

/**
 * 主要功能：
 * Created by rkl on 2018/4/4.
 * 修改历史：
 */

public class AnalysisActivity extends BaseActivity<AnalysisPresenter> implements AnalysisContact.IVew{
    private RecyclerView recyclerView;
    private CommonAdapter<AllTicketsModel.ShowapiResBodyBean.ResultBean> adapter;
    private String title;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_analysis;
    }

    @Override
    protected AnalysisPresenter createPresenter() {
        return new AnalysisPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title=getData().getString("title");
        setBackVisible(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setTitleName(title);
        initView();
        initAdapter();
        presenter.getAllTicketInfo();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter=new CommonAdapter<AllTicketsModel.ShowapiResBodyBean.ResultBean>(this,R.layout.item_tickets_list) {
            @Override
            protected void convert(ViewHolder holder, AllTicketsModel.ShowapiResBodyBean.ResultBean resultBean, int position) {
                holder.setText(R.id.tv, resultBean.getDescr());
                ImageView imageView = holder.getView(R.id.imageView);
                final String iconUrl = ConfigConstant.getICONURL()[position];
                GlideLoader.getInstance().displayImage(getContext(),iconUrl,imageView ,R.mipmap.anhuikuaisan,R.mipmap.anhuikuaisan);
                final Bundle bundle = new Bundle();
                bundle.putString("code",resultBean.getCode());
                bundle.putString("desc",resultBean.getDescr());
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (title){
                            case "均值分析":
                                goToActivity(AvgAnalysisActivity.class,bundle);

                                break;

                            case "和值分析":
                                goToActivity(SumAnalysisActivity.class,bundle);

                                break;

                            case "频率分析":
                                goToActivity(RateAnalysisActivity.class,bundle);

                                break;

                            case "奇偶分析":
                                goToActivity(ParityTrendAnalysisActivity.class,bundle);

                                break;
                        }
                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler);
    }

    @Override
    public void failed(String msg) {

    }

    @Override
    public void setAllTicketInfo(AllTicketsModel allTicketsModel) {
        adapter.replaceDataAll(allTicketsModel.getShowapi_res_body().getResult().subList(0,20));
    }
}
