package com.rkl.ticket.ticket_analysis.firstfragment.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rkl.common_library.base.BaseActivity;
import com.rkl.common_library.imageloader.GlideLoader;
import com.rkl.common_library.model.QueryMutiOpenInfoModel;
import com.rkl.common_library.recyclerview.adapter.CommonAdapter;
import com.rkl.common_library.recyclerview.base.ViewHolder;
import com.rkl.ticket.ticket_analysis.R;
import com.rkl.ticket.ticket_analysis.firstfragment.contact.TicketOpenInfoContact;
import com.rkl.ticket.ticket_analysis.firstfragment.presenter.TicketOpenInfoPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * 主要功能：
 * Created by rkl on 2018/4/3.
 * 修改历史：
 */

public class TicketOpenInfoActivity extends BaseActivity<TicketOpenInfoPresenter> implements TicketOpenInfoContact.IView {
    private String code;
    private String iconUrl;
    private RecyclerView mRecycler;
    private SmartRefreshLayout mRefreshLayout;
    private CommonAdapter<QueryMutiOpenInfoModel.ShowapiResBodyBean.ResultBean> adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_ticketopeninfo;
    }

    @Override
    protected TicketOpenInfoPresenter createPresenter() {
        return new TicketOpenInfoPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackVisible(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        code = getData().getString("code");
        iconUrl = getData().getString("iconUrl");
        initView();
        initAdapter();
        presenter.getTicketOpenInfo(code);

    }

    private void initAdapter() {
        mRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
       adapter=new CommonAdapter<QueryMutiOpenInfoModel.ShowapiResBodyBean.ResultBean>(this,R.layout.item_open_info) {
           @Override
           protected void convert(ViewHolder holder, QueryMutiOpenInfoModel.ShowapiResBodyBean.ResultBean resultBean, int position) {
               ImageView imageView = holder.getView(R.id.imageView);
               GlideLoader.getInstance().displayImage(getContext(),iconUrl,imageView ,R.mipmap.anhuikuaisan,R.mipmap.anhuikuaisan);
               holder.setText(R.id.tv, "第" + resultBean.getExpect() + "期开奖结果为：");
               String[] openCode=resultBean.getOpenCode().split("\\+");
               String[] blue = openCode[0].split(",");
               LinearLayout linearLayout = holder.getConvertView().findViewById(R.id.linear);
               linearLayout.removeAllViews();
               LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(60,60);
               params.leftMargin=5;
               for (int i = 0; i <blue.length ; i++) {
                   TextView textView=new TextView(context);
                   textView.setTextColor(context.getResources().getColor(R.color.white));
                   textView.setBackground(context.getResources().getDrawable(R.drawable.circle_blue));
                   textView.setGravity(Gravity.CENTER);
                   textView.setText(blue[i]);
                   linearLayout.addView(textView,params);
               }
               if (openCode.length>1){
                   String[] red = openCode[1].split(",");
                   for (int i = 0; i <red.length ; i++) {
                       TextView textView=new TextView(context);
                       textView.setTextColor(context.getResources().getColor(R.color.white));
                       textView.setBackground(context.getResources().getDrawable(R.drawable.circle_red));
                       textView.setGravity(Gravity.CENTER);
                       textView.setText(red[i]);
                       linearLayout.addView(textView,params);
                   }

               }
           }
       };
        mRecycler.setAdapter(adapter);
    }

    @Override
    public void failed(String msg) {

    }

    private void initView() {
        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
    }

    @Override
    public void setTicketOpenInfo(QueryMutiOpenInfoModel queryMutiOpenInfoModel) {
        if (queryMutiOpenInfoModel.getShowapi_res_body()==null||queryMutiOpenInfoModel.getShowapi_res_body().getResult()==null||queryMutiOpenInfoModel.getShowapi_res_body().getResult().get(0).getName()==null){
            presenter.getTicketOpenInfo(code);
        }else {
            setTitleName(queryMutiOpenInfoModel.getShowapi_res_body().getResult().get(0).getName());
            adapter.replaceDataAll(queryMutiOpenInfoModel.getShowapi_res_body().getResult());

        }
    }
}
