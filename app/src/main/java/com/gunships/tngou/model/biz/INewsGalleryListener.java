package com.gunships.tngou.model.biz;

import com.gunships.tngou.model.entity.Gallery;

import java.util.List;

/**
 * Created by 甘书须 on 2016/9/30.
 */
public interface INewsGalleryListener {

        void LoadSuccess(String list);
        void LoadFailed();

}
