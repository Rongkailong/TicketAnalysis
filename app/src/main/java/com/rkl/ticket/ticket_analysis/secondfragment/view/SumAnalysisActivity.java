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

public class SumAnalysisActivity extends BaseActivity<ParityTrendPresenter> implements AvgAnalysisContact.IView {
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
        setTitleName(getData().getString("desc")+"和值分析");
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
    public void onGetHistoryRecentTicketListSuccess(final List<QueryMutiOpenInfoModel.ShowapiResBodyBean.ResultBean> list) {
        lcParityTrend = LineChartHelper.getsLineChartHelper().generateLineChartConfig(lcParityTrend);
        LineData lineData;
        List<ILineDataSet> dataSetList = new ArrayList<>();
        if (lcParityTrend.getData() != null &&
                lcParityTrend.getData().getDataSetCount() > 0) {
            lineData = lcParityTrend.getLineData();
            for (int i = 0; i < lineData.getDataSetCount(); i++) {
                LineDataSet lineDataSet = (LineDataSet) lineData.getDataSetByIndex(i);
                lineDataSet.setValues(generateEntry(list));
            }
            lcParityTrend.getData().notifyDataChanged();
            lcParityTrend.notifyDataSetChanged();
        } else {
            dataSetList.add(LineChartHelper.
                    getsLineChartHelper().
                    generateLineDataSet(generateEntry(list),
                            Color.rgb(255, 0, 0),
                            "和值分布"));
            lineData = new LineData(dataSetList);
            lcParityTrend.setData(lineData);
            lcParityTrend.getXAxis().setValueFormatter((value, axis) -> {
                if (value >= 0 && list.size() > value)
                    return list.get((int) value).getExpect() + "期";
                else
                    return "0";
            });
            lcParityTrend.getAxisLeft().setValueFormatter((value, axis) -> value + "");
            lcParityTrend.setMarker(new DataMarkView(this, new DataMarkView.IDataValueFormat() {
                @Override
                public String format(Entry e, Highlight highlight) {
                    if (e.getX() >= 0 && list.size() > e.getX())
                        return list.get((int) e.getX()).getExpect() + "期：" + e.getY();
                    else
                        return "0";
                }
            }));
        }
        lcParityTrend.animateX(3000);
    }

    private List<Entry> generateEntry(List<QueryMutiOpenInfoModel.ShowapiResBodyBean.ResultBean> list) {
        List<Entry> entryList = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            String first[] = translateCodeToList(list.get(j).getOpenCode()).first;
            String second[] = translateCodeToList(list.get(j).getOpenCode()).second;
            String[] values = ArrayUtil.concat(first, second);
            int count = 0;

            for (int i = 0; i < values.length; i++) {
                try {
                    count += Integer.valueOf(values[i]);
                } catch (Exception e) {
                    count += 0;
                }
            }
            entryList.add(new Entry(j, count));
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
