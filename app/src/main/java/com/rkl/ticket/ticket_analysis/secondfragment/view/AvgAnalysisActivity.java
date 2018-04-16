package com.rkl.ticket.ticket_analysis.secondfragment.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.rkl.common_library.base.BaseActivity;
import com.rkl.common_library.model.QueryMutiOpenInfoModel;
import com.rkl.ticket.ticket_analysis.R;
import com.rkl.ticket.ticket_analysis.secondfragment.chartconfig.BarChartHelper;
import com.rkl.ticket.ticket_analysis.secondfragment.chartconfig.DataMarkView;
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

public class AvgAnalysisActivity extends BaseActivity<ParityTrendPresenter> implements AvgAnalysisContact.IView {
    private TextView tvAnalysisResult;
    private BarChart bcAvgAnalysis;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_avg_analysis;
    }

    @Override
    protected ParityTrendPresenter createPresenter() {
        return new ParityTrendPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName(getData().getString("desc")+"均值分析");
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
        tvAnalysisResult = findViewById(R.id.tvAnalysisResult);
        bcAvgAnalysis = findViewById(R.id.bcAvgAnalysis);
    }

    @Override
    public void onGetHistoryRecentTicketListSuccess(List<QueryMutiOpenInfoModel.ShowapiResBodyBean.ResultBean> list) {
        bcAvgAnalysis = BarChartHelper.getBarChartHelper().generateBarChartConfig(bcAvgAnalysis);
        BarData barData;
        if (bcAvgAnalysis.getData() != null &&
                bcAvgAnalysis.getData().getDataSetCount() > 0) {
            barData = bcAvgAnalysis.getBarData();
            for (int i = 0; i < barData.getDataSetCount(); i++) {
                BarDataSet barDataSet = (BarDataSet) barData.getDataSetByIndex(i);
                barDataSet.setValues(generateEntry(list, bcAvgAnalysis.getData().getDataSetCount()));
            }
            bcAvgAnalysis.getData().notifyDataChanged();
            bcAvgAnalysis.notifyDataSetChanged();
        } else {
            final int codeLength = translateCodeToList(list.get(0).getOpenCode()).first.length + translateCodeToList(list.get(0).getOpenCode()).second.length + 1;
            int divide = 255 / codeLength;
            List<Integer> colorList = new ArrayList<>();
            for (int i = 0; i < codeLength; i++) {
                colorList.add(Color.rgb(255 - divide * i, 0, 0 + divide * i));
            }
            IBarDataSet barDataSet = BarChartHelper.getBarChartHelper().generateBarDataSet(generateEntry(list, codeLength), new String[]{"均值图"}, colorList);
            barData = new BarData(barDataSet);
            bcAvgAnalysis.setData(barData);
//            bcAvgAnalysis.getXAxis().setValueFormatter((value, axis) -> {
//                if (value == codeLength - 1) {
//                    return "和平均";
//                } else {
//                    return (1 + (int) value) + "号";
//                }
//            });
            bcAvgAnalysis.getXAxis().setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    if (value == codeLength - 1) {
                    return "和平均";
                } else {
                    return (1 + (int) value) + "号";
                }
                }
            });
//            bcAvgAnalysis.getAxisLeft().setValueFormatter((value, axis) -> (value));
            bcAvgAnalysis.setMarker(new DataMarkView(this, new DataMarkView.IDataValueFormat() {
                @Override
                public String format(Entry e, Highlight highlight) {
                    if (e.getX() == codeLength - 1) {
                        return "和平均：" + e.getY();
                    } else {
                        return (1 + (int) e.getX()) + "号：" + e.getY();
                    }
                }
            }));
        }
        bcAvgAnalysis.animateY(3000);

    }

    private List<BarEntry> generateEntry(List<QueryMutiOpenInfoModel.ShowapiResBodyBean.ResultBean> list, int codeLength) {
        float[] valuesCount = new float[codeLength];
        for (int j = 0; j < list.size(); j++) {
            Pair<String[], String[]> sPair = translateCodeToList(list.get(j).getOpenCode());
            String[] values = ArrayUtil.concat(sPair.first, sPair.second);
            for (int k = 0; k < codeLength; k++) {
                try {
                    valuesCount[k] += Float.valueOf(values[k]);//非数字直接抛异常
                } catch (Exception e) {
                    e.printStackTrace();
                    valuesCount[k] += 0;
                }
            }
        }
        List<BarEntry> barEntries = new ArrayList<>();
        float count = 0;
        for (int i = 0; i < codeLength - 1; i++) {
            count += valuesCount[i] / list.size();
            barEntries.add(new BarEntry(i, valuesCount[i] / list.size()));
        }
        barEntries.add(new BarEntry(codeLength - 1, count));
        return barEntries;
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
