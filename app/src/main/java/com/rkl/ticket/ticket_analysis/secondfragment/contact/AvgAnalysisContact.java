package com.rkl.ticket.ticket_analysis.secondfragment.contact;

import com.rkl.common_library.base.IBaseView;
import com.rkl.common_library.model.QueryMutiOpenInfoModel;

import java.util.List;

/**
 * 主要功能：
 * Created by rkl on 2018/4/4.
 * 修改历史：
 */

public class AvgAnalysisContact {
    public interface IView extends IBaseView {
        void onGetHistoryRecentTicketListSuccess(List<QueryMutiOpenInfoModel.ShowapiResBodyBean.ResultBean> list);
    }

    public interface Presenter{
        void getRecentOpenDatas(String ticketCode, String count);
    }
}
