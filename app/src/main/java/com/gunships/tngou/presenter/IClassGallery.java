package com.gunships.tngou.presenter;

/**
 * Created by 甘书须 on 2016/10/18.
 */
public interface IClassGallery {

    void requestClassGallery(int id,int row,final int model);
    boolean checkConnection();
    void changeConnection();
}
