package com.gunships.tngou.model.biz;

import com.gunships.tngou.model.entity.GalleryNews;
import com.gunships.tngou.model.entity.GalleryPicture;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 甘书须 on 2016/10/13.
 */
public interface ILoadPicture {
    @GET("show")
    Call<GalleryPicture> loadNewsGallery(@Query("id") int id);
}
