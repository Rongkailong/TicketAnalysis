package com.rkl.ticket.ticket_analysis.secondfragment.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.rkl.common_library.base.BaseActivity;
import com.rkl.common_library.model.QueryMutiOpenInfoModel;
import com.rkl.ticket.ticket_analysis.R;
import com.rkl.ticket.ticket_analysis.secondfragment.chartconfig.BarChartHelper;
import com.rkl.ticket.ticket_analysis.secondfragment.chartconfig.DataMarkView;
import com.rkl.ticket.ticket_analysis.secondfragment.contact.AvgAnalysisContact;
import com.rkl.ticket.ticket_analysis.secondfragment.presenter.ParityTrendPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author xiaolong
 * @version v1.0
 * @function <均值分析>
 * @date: 2017/9/15 11:38
 */

public class RateAnalysisActivity extends BaseActivity<ParityTrendPresenter> implements AvgAnalysisContact.IView {
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
        setTitleName(getData().getString("desc")+"频率分析");
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
                barDataSet.setValues(generateEntry(list));
            }
            bcAvgAnalysis.getData().notifyDataChanged();
            bcAvgAnalysis.notifyDataSetChanged();
        } else {
            List<Integer> colorList = new ArrayList<>();
            colorList.add(getResources().getColor(R.color.main_red_color));
            colorList.add(getResources().getColor(R.color.main_blue_color_4c65ed));
            IBarDataSet barDataSet = BarChartHelper.getBarChartHelper().generateBarDataSet(generateEntry(list), new String[]{"普通码", "特别码"}, colorList);
            barData = new BarData(barDataSet);
            bcAvgAnalysis.setData(barData);
            bcAvgAnalysis.getXAxis().setValueFormatter((value, axis) -> (int) value + "号");
            bcAvgAnalysis.setMarker(new DataMarkView(this, (e, highlight) -> ((int) e.getX()) + "号：" + e.getY() + "次"));
        }

        bcAvgAnalysis.animateY(3000);
    }

    private List<BarEntry> generateEntry(List<QueryMutiOpenInfoModel.ShowapiResBodyBean.ResultBean> list) {
        List<Integer> keyList = new ArrayList<>();
        List<Integer> specialKeyList = new ArrayList<>();
        ArrayMap<Integer, Integer> numberMap = new ArrayMap<>();
        ArrayMap<Integer, Integer> specialNumberMap = new ArrayMap<>();
        for (int j = 0; j < list.size(); j++) {
            Pair<String[], String[]> sPair = translateCodeToList(list.get(j).getOpenCode());
            for (int k = 0; k < sPair.first.length; k++) {
                try {
                    int number = Integer.valueOf(sPair.first[k]);
                    if (keyList.contains(number)) {
                        numberMap.put(number, numberMap.get(number) + 1);
                    } else {
                        keyList.add(number);
                        numberMap.put(number, 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (int k = 0; k < sPair.second.length; k++) {
                try {
                    int number = Integer.valueOf(sPair.second[k]);
                    if (specialKeyList.contains(number)) {
                        specialNumberMap.put(number, specialNumberMap.get(number) + 1);
                    } else {
                        specialKeyList.add(number);
                        specialNumberMap.put(number, 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Collections.sort(keyList);
        Collections.sort(specialKeyList);
        List<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < keyList.size(); i++) {
            barEntries.add(new BarEntry(keyList.get(i), new float[]{numberMap.get(keyList.get(i)),
                    specialNumberMap.get(keyList.get(i)) == null ? 0 : specialNumberMap.get(keyList.get(i))}));
        }
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
