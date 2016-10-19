package com.gunships.tngou.model.biz;

import com.gunships.tngou.model.entity.GalleryNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 甘书须 on 2016/10/18.
 */
public interface ILoadClassGallery {

    @GET("list")
    Call<GalleryNews> loadClassGallery(@Query("id") int classId, @Query("page") int page,@Query("rows") int rows);
}
