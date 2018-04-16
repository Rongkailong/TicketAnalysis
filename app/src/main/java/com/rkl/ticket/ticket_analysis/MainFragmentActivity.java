package com.rkl.ticket.ticket_analysis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.TabHost;
import com.rkl.common_library.base.BaseFragmentActivity;
import com.rkl.common_library.base.BasePresenter;
import com.rkl.common_library.util.ToastUtils;
import com.rkl.common_library.widgets.UnrefreshFragmentTabHost;
import com.rkl.ticket.ticket_analysis.firstfragment.view.FirstFragment;
import com.rkl.ticket.ticket_analysis.secondfragment.view.SecondFragment;
import com.rkl.ticket.ticket_analysis.thirdfragment.view.ThirdFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * 主要功能：
 * Created by rkl on 2018/4/2.
 * 修改历史：
 */

public class MainFragmentActivity extends BaseFragmentActivity {
    private long lastClick;

    @Override
    protected List<UnrefreshFragmentTabHost.Tab> getTabs() {
        UnrefreshFragmentTabHost.Tab tab1=new UnrefreshFragmentTabHost.Tab(FirstFragment.class,R.string.tab_title1,R.drawable.selector_first_page);
        UnrefreshFragmentTabHost.Tab tab2=new UnrefreshFragmentTabHost.Tab(SecondFragment.class,R.string.tab_title2,R.drawable.selector_second_page);
        UnrefreshFragmentTabHost.Tab tab3 = new UnrefreshFragmentTabHost.Tab(ThirdFragment.class, R.string.tab_title3, R.drawable.selector_third_page);
        List<UnrefreshFragmentTabHost.Tab> list = new ArrayList<>();
        list.add(tab1);
        list.add(tab2);
        list.add(tab3);
        return list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackVisible(false,null);
        setTitleName(R.string.tab_title1);
        mTabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                setTitleName(tabId);
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    public void onBackPressed() {
        long currentTime= System.currentTimeMillis();
        if (currentTime-lastClick<2000){
            finish();
        }else {
            ToastUtils.showShortToast(this,"再按一次退出");

        }
        lastClick=currentTime;
    }
}
