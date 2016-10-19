package com.gunships.tngou.model.biz;

import android.content.Context;

import com.gunships.tngou.presenter.INewsPresenter;

/**
 * Created by 甘书须 on 2016/9/30.
 */
public interface ILoadNewsBiz {

    int findLastId(Context context);
    void markLastId(Context context,int lastId);
    void loadGallery(int id ,int row,LoadNewsBiz.IReturnNewsListener listener);



}
