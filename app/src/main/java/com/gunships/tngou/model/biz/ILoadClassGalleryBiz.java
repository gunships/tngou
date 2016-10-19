package com.gunships.tngou.model.biz;

/**
 * Created by 甘书须 on 2016/10/18.
 */
public interface ILoadClassGalleryBiz {
    void loadClassGallery(int id ,int row,LoadNewsBiz.IReturnNewsListener listener);
}
