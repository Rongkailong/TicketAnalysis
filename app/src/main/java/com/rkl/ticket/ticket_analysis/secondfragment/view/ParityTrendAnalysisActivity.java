package com.rkl.ticket.ticket_analysis.secondfragment.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.rkl.common_library.base.BaseActivity;
import com.rkl.common_library.model.QueryMutiOpenInfoModel;
import com.rkl.ticket.ticket_analysis.R;
import com.rkl.ticket.ticket_analysis.secondfragment.chartconfig.DataMarkView;
import com.rkl.ticket.ticket_analysis.secondfragment.chartconfig.LineChartHelper;
import com.rkl.ticket.ticket_analysis.secondfragment.contact.AvgAnalysisContact;
import com.rkl.ticket.ticket_analysis.secondfragment.presenter.ParityTrendPresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author xiaolong
 * @version v1.0
 * @function <均值分析>
 * @date: 2017/9/15 11:38
 */

public class ParityTrendAnalysisActivity extends BaseActivity<ParityTrendPresenter> implements AvgAnalysisContact.IView {
    private TextView tvParityTrendAnalysis;
    private LineChart lcParityTrend;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_parity_trend;
    }

    @Override
    protected ParityTrendPresenter createPresenter() {
        return new ParityTrendPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName(getData().getString("desc")+"奇偶分析");
        setBackVisible(true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initView();
        presenter.getRecentOpenDatas(getData().getString("code"),"100");
    }

    private void initView() {
        tvParityTrendAnalysis = findViewById(R.id.tvParityTrendAnalysis);
        lcParityTrend = findViewById(R.id.lcParityTrend);
    }

    @Override
    public void onGetHistoryRecentTicketListSuccess(List<QueryMutiOpenInfoModel.ShowapiResBodyBean.ResultBean> list) {
        lcParityTrend = LineChartHelper.getsLineChartHelper().generateLineChartConfig(lcParityTrend);
        LineData lineData;
        List<ILineDataSet> dataSetList = new ArrayList<>();
        if (lcParityTrend.getData() != null &&
                lcParityTrend.getData().getDataSetCount() > 0) {
            lineData = lcParityTrend.getLineData();
            for (int i = 0; i < lineData.getDataSetCount(); i++) {
                LineDataSet lineDataSet = (LineDataSet) lineData.getDataSetByIndex(i);
                lineDataSet.setValues(generateEntry(list, i));
            }
            lcParityTrend.getData().notifyDataChanged();
            lcParityTrend.notifyDataSetChanged();
        } else {
            int codeLength = translateCodeToList(list.get(0).getOpenCode()).first.length + translateCodeToList(list.get(0).getOpenCode()).second.length;
            int colorDivider = 255 / codeLength;
            for (int i = 0; i < codeLength; i++) {
                dataSetList.add(LineChartHelper.
                        getsLineChartHelper().
                        generateLineDataSet(generateEntry(list, i),
                                Color.rgb(255 - i * colorDivider, 0, 0 + i * colorDivider),
                                "号码" + (i + 1)));

            }
            lineData = new LineData(dataSetList);
            lcParityTrend.setData(lineData);
            lcParityTrend.getXAxis().setValueFormatter((value, axis) -> {
                if (value >= 0 && list.size() > value)
                    return list.get((int) value).getExpect() + "期";
                else
                    return "0";
            });
            lcParityTrend.setMarker(new DataMarkView(this, (e, highlight) -> {
                if (e.getX() >= 0 && list.size() > e.getX())
                    return list.get((int) e.getX()).getExpect() + "期：" + (e.getY() % 2 == 1 ? "奇" : "偶");
                else
                    return "0";

            }));
            lcParityTrend.getAxisLeft().setValueFormatter((value, axis) -> value % 2 == 1 ? "奇" : "偶");
        }
        lcParityTrend.animateX(3000);
    }

    private List<Entry> generateEntry(List<QueryMutiOpenInfoModel.ShowapiResBodyBean.ResultBean> list, int i) {
        List<Entry> entryList = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            String first[] = translateCodeToList(list.get(j).getOpenCode()).first;
            String second[] = translateCodeToList(list.get(j).getOpenCode()).second;
            try {
                if (i < first.length) {
                    entryList.add(new Entry(j, Float.valueOf(first[i]) % 2 + (i * 2)));
                } else {
                    entryList.add(new Entry(j, Float.valueOf(second[i - first.length]) % 2 + (i * 2)));
                }
            } catch (Exception e) {
                e.printStackTrace();
                entryList.add(new Entry(j, 0 + (i * 2)));
            }

        }
        return entryList;
    }

    private Pair<String[], String[]> translateCodeToList(String openCode) {
        String[] splitString = openCode.split("\\+");
        String[] openNumbers = splitString[0].split(",");
        String[] specialNumbers = new String[]{};
        if (splitString.length > 1) {
            //说明是带特别码的彩种
            specialNumbers = splitString[1].split(",");
        }
        return new Pair<>(openNumbers, specialNumbers);
    }


    @Override
    public void failed(String msg) {

    }

}
