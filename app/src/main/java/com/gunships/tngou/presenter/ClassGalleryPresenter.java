package com.gunships.tngou.presenter;

import android.content.Context;

import com.gunships.tngou.model.biz.ILoadClassGalleryBiz;
import com.gunships.tngou.model.biz.INetWorkState;
import com.gunships.tngou.model.biz.LoadClassGalleryBiz;
import com.gunships.tngou.model.biz.LoadNewsBiz;
import com.gunships.tngou.model.biz.NetWorkState;
import com.gunships.tngou.view.IClassView;

import java.util.List;

/**
 * Created by 甘书须 on 2016/10/18.
 */
public class ClassGalleryPresenter implements IClassGallery {
    private ILoadClassGalleryBiz iLoadClassGalleryBiz;
    private IClassView iClassView;
    private INetWorkState iNetWorkState;
    private final static int REFRESH =200;
    private final static int LOAD =300;
    public ClassGalleryPresenter(IClassView ClassView) {
        this.iLoadClassGalleryBiz = new LoadClassGalleryBiz();
        this.iClassView = ClassView;
        this.iNetWorkState = new NetWorkState();
    }

    @Override
    public void requestClassGallery(int id, int page, final int model) {
        iLoadClassGalleryBiz.loadClassGallery(id, page, new LoadNewsBiz.IReturnNewsListener() {
            @Override
            public void getNews(List list) {
                if(model==REFRESH){
                    //iClassView.pullToRefresh(list);
                }else if(model==LOAD){
                    iClassView.pullOnLoading(list);
                }else {
                    iClassView.showGallery(list);
                }
            }

            @Override
            public void NoNews() {
                iClassView.NoNews();
            }
        });
    }

    @Override
    public boolean checkConnection() {
        if(!iNetWorkState.isConnectionWifi()) {
            if (!iNetWorkState.isConnectionNet()) {
                iClassView.networkWarn();
                return false;
            }else{
                iClassView.wifiWarn();
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
