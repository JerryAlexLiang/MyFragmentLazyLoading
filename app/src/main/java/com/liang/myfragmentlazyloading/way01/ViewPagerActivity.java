package com.liang.myfragmentlazyloading.way01;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.liang.myfragmentlazyloading.R;
import com.liang.myfragmentlazyloading.adapter.FragmentViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建日期：2019/7/30 on 9:45
 * 描述: ViewPager + Fragment
 * 作者: liangyang
 */
public class ViewPagerActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private List<String> stringList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ButterKnife.bind(this);
        //初始化资源
        initData();
        //初始化适配器
        FragmentViewPagerAdapter fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragmentList, stringList);
        //设置至少4个fragment，防止重复创建和销毁，造成内存溢出
        viewPager.setOffscreenPageLimit(4);
        //绑定适配器
        viewPager.setAdapter(fragmentViewPagerAdapter);
        //TabLayout属性设置
        //设置下划线指示器圆角
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(dp2px(2));
        tabLayout.setSelectedTabIndicator(gradientDrawable);
        //TabLayout同步fragment
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initData() {
        stringList.add("公众号");
        stringList.add("最新项目");
        stringList.add("最新文章");
        stringList.add("常用网站");

        fragmentList.add(ContainerFragment.newInstance(1, false));
        fragmentList.add(ContainerFragment.newInstance(2, true));
        fragmentList.add(ContainerFragment.newInstance(3, false));
        fragmentList.add(ContainerFragment.newInstance(4, true));

    }

    private int dp2px(float dp) {
        float density = this.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

}
