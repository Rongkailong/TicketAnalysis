package com.rkl.ticket.ticket_analysis.firstfragment.contact;

import com.rkl.common_library.base.IBaseView;
import com.rkl.common_library.model.QueryMutiOpenInfoModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * 主要功能：
 * Created by rkl on 2018/4/3.
 * 修改历史：
 */

public class TicketOpenInfoContact {
    public interface IView extends IBaseView{
        void setTicketOpenInfo(QueryMutiOpenInfoModel queryMutiOpenInfoModel);

    }

    public interface Presenter {
        void initRefreshLayout(SmartRefreshLayout refreshLayout);
        void getTicketOpenInfo(String code);
    }
}
