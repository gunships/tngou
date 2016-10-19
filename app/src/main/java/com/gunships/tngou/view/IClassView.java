package com.gunships.tngou.view;

import com.gunships.tngou.model.entity.Gallery;

import java.util.List;

/**
 * Created by 甘书须 on 2016/10/18.
 */
public interface IClassView {

    void requestNews(int id,int row,int model);

    void showGallery(List<Gallery> list);

    void NoNews();

    void pullToRefresh(List<Gallery> list);

    void pullOnLoading(List<Gallery> list);

    boolean cheakNetworkState();

    void networkWarn();

    void wifiWarn();
}
