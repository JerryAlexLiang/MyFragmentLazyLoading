package com.liang.myfragmentlazyloading.retrofit;


import com.liang.myfragmentlazyloading.entity.CommonUseBean;
import com.liang.myfragmentlazyloading.entity.ListArticalBean;
import com.liang.myfragmentlazyloading.entity.ListProjectBean;
import com.liang.myfragmentlazyloading.entity.WxArtucleBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 创建日期：2018/12/24 on 11:13
 * 描述: 定义retrofit2请求的接口
 * 作者: liangyang
 */
public interface MyService {

    //微信公众号
    @GET("wxarticle/chapters/json")
    Observable<WxArtucleBean> getWxArtucleBean();

    //最新项目
    @GET("article/listproject/0/json")
    Observable<ListProjectBean> getListProjectBean();

    //最新文章
    @GET("article/list/1/json")
    Observable<ListArticalBean> getListArticalBean();

    //常用网站
    @GET("friend/json")
    Observable<CommonUseBean> getCommonUseBean();

}
