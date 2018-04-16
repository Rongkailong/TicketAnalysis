package com.rkl.ticket.ticket_analysis.firstfragment.view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.rkl.common_library.base.BaseFragment;
import com.rkl.common_library.constant.ConfigConstant;
import com.rkl.common_library.imageloader.GlideLoader;
import com.rkl.common_library.model.AllTicketsModel;
import com.rkl.common_library.recyclerview.adapter.CommonAdapter;
import com.rkl.common_library.recyclerview.base.ViewHolder;
import com.rkl.common_library.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.rkl.ticket.ticket_analysis.R;
import com.rkl.ticket.ticket_analysis.firstfragment.contact.FirstFragmentContact;
import com.rkl.ticket.ticket_analysis.firstfragment.presenter.FirstFragmentPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;

/**
 * 主要功能：
 * Created by rkl on 2018/4/2.
 * 修改历史：
 */

public class FirstFragment extends BaseFragment<FirstFragmentPresenter> implements FirstFragmentContact.IView{
    private SmartRefreshLayout refreshLayout;
    private Banner banner;
    private RecyclerView recyclerView;
    private CommonAdapter<AllTicketsModel.ShowapiResBodyBean.ResultBean> adapter;
    private View v;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_first;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initWidgetView(view);
        initAdapter();
        presenter.initBanner(banner);
        presenter.initRefreshLayout(refreshLayout);
        presenter.getAllTicketInfo();

    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false));
        adapter=new CommonAdapter<AllTicketsModel.ShowapiResBodyBean.ResultBean>(getContext(),R.layout.item_all_tickets) {
            @Override
            protected void convert(ViewHolder holder, final AllTicketsModel.ShowapiResBodyBean.ResultBean resultBean, int position) {
                holder.setText(R.id.name, resultBean.getDescr());
                holder.setText(R.id.des, resultBean.getNotes());
                ImageView imageView = holder.getView(R.id.image);
                final String iconUrl = ConfigConstant.getICONURL()[position - 1];
                GlideLoader.getInstance().displayImage(getContext(),iconUrl,imageView ,R.mipmap.anhuikuaisan,R.mipmap.anhuikuaisan);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("code",resultBean.getCode());
                        bundle.putString("iconUrl",iconUrl);
                        goToActivity(TicketOpenInfoActivity.class,bundle);
                    }
                });
            }
        };
        HeaderAndFooterWrapper<AllTicketsModel.ShowapiResBodyBean.ResultBean> wrapperAdapter = new HeaderAndFooterWrapper<>(adapter);
        wrapperAdapter.addHeaderView(v);
        recyclerView.setAdapter(wrapperAdapter);
    }

    private void initWidgetView(View view) {
        refreshLayout = view.findViewById(R.id.refresh_layout);
        recyclerView = view.findViewById(R.id.recycler);
        v = LayoutInflater.from(getContext()).inflate(R.layout.banner_view, null);
        banner = v.findViewById(R.id.banner);
    }

    @Override
    protected FirstFragmentPresenter createPresenter() {
        return new FirstFragmentPresenter();
    }

    @Override
    public void failed(String msg) {

    }

    @Override
    public void setAllTicketInfo(AllTicketsModel allTicketsModel) {
        adapter.replaceDataAll(allTicketsModel.getShowapi_res_body().getResult().subList(0,20));

    }
}
