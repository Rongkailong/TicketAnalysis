package com.rkl.ticket.ticket_analysis.secondfragment.contact;

import com.rkl.common_library.base.IBaseView;
import com.rkl.common_library.model.AllTicketsModel;

/**
 * 主要功能：
 * Created by rkl on 2018/4/4.
 * 修改历史：
 */

public class AnalysisContact {
    public interface IVew extends IBaseView{
        void setAllTicketInfo(AllTicketsModel allTicketsModel);

    }

    public interface Presenter{
        void getAllTicketInfo();

    }
}
