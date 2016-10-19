package com.gunships.tngou.view;

import com.gunships.tngou.model.entity.GalleryPicture;

/**
 * Created by 甘书须 on 2016/10/13.
 */
public interface IGalleryListView {

    void requestGallery(int id);
    void showGallery(GalleryPicture galleryPicture);
    void showPopupWindow(int position);

}
