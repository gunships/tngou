package com.gunships.tngou.model.biz;

/**
 * Created by 甘书须 on 2016/10/13.
 */
public interface ILoadPictureBiz {

    void loadPicture(int id,LoadPictureBiz.IReturnGalleryListener listener);
}
