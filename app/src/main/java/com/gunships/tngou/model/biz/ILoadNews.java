package com.gunships.tngou.model.biz;

import com.gunships.tngou.model.entity.GalleryNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 甘书须 on 2016/10/2.
 */
public interface ILoadNews {

    @GET("news")
    Call<GalleryNews> loadNewsGallery(@Query("id") int id,@Query("rows") int rows);
}
