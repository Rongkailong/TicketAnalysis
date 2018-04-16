package com.rkl.ticket.ticket_analysis.firstfragment.contact;

import com.rkl.common_library.base.IBaseView;
import com.rkl.common_library.model.AllTicketsModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;

/**
 * 主要功能：
 * Created by rkl on 2018/4/2.
 * 修改历史：
 */

public class FirstFragmentContact {
    public interface IView extends IBaseView{
        void setAllTicketInfo(AllTicketsModel allTicketsModel);
    }

    public interface Persenter {

         void initBanner(Banner banner);

        void initRefreshLayout(SmartRefreshLayout refreshLayout);

        //获取所有的彩票信息
        void getAllTicketInfo();

    }
}
