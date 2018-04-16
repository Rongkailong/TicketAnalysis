package com.rkl.ticket.ticket_analysis.secondfragment.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rkl.common_library.base.BaseFragment;
import com.rkl.common_library.base.BasePresenter;
import com.rkl.common_library.constant.ConfigConstant;
import com.rkl.common_library.imageloader.GlideLoader;
import com.rkl.common_library.recyclerview.adapter.CommonAdapter;
import com.rkl.common_library.recyclerview.base.ViewHolder;
import com.rkl.ticket.ticket_analysis.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 主要功能：
 * Created by rkl on 2018/4/2.
 * 修改历史：
 */

public class SecondFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private CommonAdapter<String> adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_second;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initWidgetView(view);
        initAdapter();

    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false));
        final List<String> list = new ArrayList<>();
        list.add("均值分析");
        list.add("和值分析");
        list.add("频率分析");
        list.add("奇偶分析");
        adapter=new CommonAdapter<String>(getContext(),R.layout.item_analysis,list) {
            @Override
            protected void convert(ViewHolder holder, String s, final int position) {
                holder.setText(R.id.tv, s);
                ImageView imageView = holder.getConvertView().findViewById(R.id.imageView);
                Log.i("tmd", "convert: "+ConfigConstant.ANALYSISPIC[position]);
                GlideLoader.getInstance().displayImage(getContext(), ConfigConstant.ANALYSISPIC[position],imageView,0,0);

                //点击事件
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("title",list.get(position));
                        goToActivity(AnalysisActivity.class,bundle);

                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void initWidgetView(View view) {
        recyclerView=view.findViewById(R.id.recycler);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
