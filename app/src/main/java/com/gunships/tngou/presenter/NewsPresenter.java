package com.gunships.tngou.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.gunships.tngou.model.biz.ILoadNewsBiz;
import com.gunships.tngou.model.biz.INetWorkState;
import com.gunships.tngou.model.biz.LoadNewsBiz;
import com.gunships.tngou.model.biz.NetWorkState;
import com.gunships.tngou.model.entity.Gallery;
import com.gunships.tngou.view.INewsView;

import java.util.List;

/**
 * Created by 甘书须 on 2016/9/30.
 */
public class NewsPresenter implements INewsPresenter{


    private INewsView iNewsView;
    private ILoadNewsBiz iLoadNewsBiz;
    private INetWorkState iNetWorkState;
    private List<Gallery> list;
    private View view;
    private final static int REFRESH =200;
    private final static int LOAD =300;

    public NewsPresenter(INewsView iNewsView) {
        this.iLoadNewsBiz = new LoadNewsBiz();
        this.iNetWorkState = new NetWorkState();
        this.iNewsView = iNewsView;
    }

    @Override
    public int getLastId(Context context) {
        return iLoadNewsBiz.findLastId(context);
    }

    @Override
    public void setLastId(Context context, int lastId) {
        iLoadNewsBiz.markLastId(context,lastId);
    }

    @Override
    public void requestNews(int id, int row, final int model) {
        iLoadNewsBiz.loadGallery(id,row,new LoadNewsBiz.IReturnNewsListener() {
            @Override
            public void getNews(List list) {
                if(model==REFRESH) {
                    iNewsView.pullToRefresh(list);
                }else if(model==LOAD){
                    iNewsView.pullOnLoading(list);
                }else {
                    iNewsView.showGallery(list);
                }
            }
            @Override
            public void NoNews() {
                iNewsView.NoNews();
            }
        });
    }

    @Override
    public boolean checkConnection() {
        if(!iNetWorkState.isConnectionWifi()) {
            if (!iNetWorkState.isConnectionNet()) {
                iNewsView.networkWarn();
                return false;
            }else{
                iNewsView.wifiWarn();
                return true;
            }
        }
        return true;
    }

    @Override
    public void changeConnection() {
        iNetWorkState.connectionWifi();
    }
}
