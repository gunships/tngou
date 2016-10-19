package com.gunships.tngou.presenter;

import com.gunships.tngou.model.biz.ILoadPictureBiz;
import com.gunships.tngou.model.biz.LoadPictureBiz;
import com.gunships.tngou.model.entity.GalleryPicture;
import com.gunships.tngou.view.IGalleryListView;


import java.util.List;

/**
 * Created by 甘书须 on 2016/10/13.
 */
public class GalleryPresenter implements IGalleryPresenter{

    private IGalleryListView iGalleryListView;
    private ILoadPictureBiz iLoadPictureBiz;

    public GalleryPresenter(IGalleryListView iGalleryListView) {
        this.iGalleryListView = iGalleryListView;
        this.iLoadPictureBiz = new LoadPictureBiz();
    }

    @Override
    public void requestGallery(int id) {
        iLoadPictureBiz.loadPicture(id, new LoadPictureBiz.IReturnGalleryListener() {
            @Override
            public void getGallery(Object t) {
                iGalleryListView.showGallery((GalleryPicture)t);
            }

            @Override
            public void NoGallery() {

            }
        });
    }
}
