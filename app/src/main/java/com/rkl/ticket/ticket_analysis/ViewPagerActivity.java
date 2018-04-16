package com.rkl.ticket.ticket_analysis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rkl.common_library.base.BaseActivity;
import com.rkl.common_library.base.BasePresenter;
import com.rkl.common_library.util.SharedPrefrencesUtil;
import com.rkl.common_library.util.TimeUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * 主要功能：
 * Created by rkl on 2018/4/10.
 * 修改历史：
 */

public class ViewPagerActivity extends BaseActivity {
    private List<ImageView> imageViews;
    private int[] img = {R.mipmap.pager0,R.mipmap.pager1,R.mipmap.pager2,R.mipmap.pager3};
    private ViewPager viewPager;
    private boolean isFirst;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_viewpager;
    }

    @Override
    protected void setStyleForStatusBarScreenRoate() {
        isAllowFullScreen=true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentVisible(false);
        isFirst=SharedPrefrencesUtil.getData(this, "sharedata", "isFirst", true);
        if (isFirst&&TimeUtils.getCurDay()==13){
            viewPager = findViewById(R.id.ViewPager);
            initData();
            initAdapter();
            SharedPrefrencesUtil.saveData(this, "sharedata", "isFirst",false);
        }else {
            goToActivity(SplashActivity.class);
            finish();
        }
    }




    private void initAdapter() {
        MyPagerAdapter adapter=new MyPagerAdapter();
        viewPager.setAdapter(adapter);
    }

    private void initData() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < img.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(img[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViews.add(imageView);
            if (i==3){
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToActivity(SplashActivity.class);
                        finish();
                    }
                });
            }
        }

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    private  class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageViews==null?0:imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews.get(position));
            return imageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position));
        }
    }

}
