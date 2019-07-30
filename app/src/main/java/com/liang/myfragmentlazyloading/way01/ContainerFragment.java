package com.liang.myfragmentlazyloading.way01;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liang.myfragmentlazyloading.R;
import com.liang.myfragmentlazyloading.entity.CommonUseBean;
import com.liang.myfragmentlazyloading.entity.ListArticalBean;
import com.liang.myfragmentlazyloading.entity.ListProjectBean;
import com.liang.myfragmentlazyloading.entity.WxArtucleBean;
import com.liang.myfragmentlazyloading.retrofit.BaseObserver;
import com.liang.myfragmentlazyloading.retrofit.RetrofitHelper;
import com.liang.myfragmentlazyloading.retrofit.UrlConstants;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContainerFragment extends LazyFragment {

    private int tabIndex;
    public static final String INTENT_INT_INDEX = "index";
    private TextView tvContent;
    private ScrollView scrollView;
    private TextView tvLoading;

    public static ContainerFragment newInstance(int tabIndex, boolean isLazyLoad) {
        Bundle args = new Bundle();
        args.putInt(INTENT_INT_INDEX, tabIndex);
        args.putBoolean(LazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        ContainerFragment fragment = new ContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_container);
        //获取传递数据
        tabIndex = getArguments().getInt(INTENT_INT_INDEX);
        //初始化视图
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvLoading = (TextView) findViewById(R.id.tv_loading);
        //请求数据源
        getData();

    }

    private void getData() {
        switch (tabIndex) {
            case 1:
                //微信公众号
                getWxArtucleBean();
//                tvLoading.setText(tabIndex+"");
                break;

            case 2:
                //最新项目
                getListProjectBean();
//                tvLoading.setText(tabIndex + "");
                break;

            case 3:
                //最新文章
                getListArticalBean();
//                tvLoading.setText(tabIndex + "");
                break;

            case 4:
                //常用网站
                getCommonUseBean();
//                tvLoading.setText(tabIndex + "");
                break;
        }
    }

    /**
     * 常用网站
     */
    private void getCommonUseBean() {
        RetrofitHelper
                .getInstance(UrlConstants.BaseUrl)
                .getMyService()
                .getCommonUseBean()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<CommonUseBean>() {
                    @Override
                    public void onNext(CommonUseBean commonUseBean) {
                        super.onNext(commonUseBean);
                        tvLoading.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                        tvContent.setText(new Gson().toJson(commonUseBean.getData()));
                        Log.d("TAG", "常用网站: " + new Gson().toJson(commonUseBean.getData()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     * 最新文章
     */
    private void getListArticalBean() {
        RetrofitHelper
                .getInstance(UrlConstants.BaseUrl)
                .getMyService()
                .getListArticalBean()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ListArticalBean>() {
                    @Override
                    public void onNext(ListArticalBean listArticalBean) {
                        super.onNext(listArticalBean);
                        tvLoading.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                        tvContent.setText(new Gson().toJson(listArticalBean.getData()));
                        Log.d("TAG", "最新文章: " + new Gson().toJson(listArticalBean.getData()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     * 最新项目
     */
    private void getListProjectBean() {
        RetrofitHelper
                .getInstance(UrlConstants.BaseUrl)
                .getMyService()
                .getListProjectBean()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ListProjectBean>() {
                    @Override
                    public void onNext(ListProjectBean listProjectBean) {
                        super.onNext(listProjectBean);
                        tvLoading.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                        tvContent.setText(new Gson().toJson(listProjectBean.getData()));
                        Log.d("TAG", "最新项目: " + new Gson().toJson(listProjectBean.getData()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     * 微信公众号
     */
    private void getWxArtucleBean() {
        RetrofitHelper
                .getInstance(UrlConstants.BaseUrl)
                .getMyService()
                .getWxArtucleBean()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<WxArtucleBean>() {
                    @Override
                    public void onNext(WxArtucleBean wxArtucleBean) {
                        super.onNext(wxArtucleBean);
                        tvLoading.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                        tvContent.setText(new Gson().toJson(wxArtucleBean.getData()));
                        Log.d("TAG", "微信公众号: " + new Gson().toJson(wxArtucleBean.getData()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }


    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        RetrofitHelper.deleteInstance();
    }
}
