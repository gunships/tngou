package com.gunships.tngou.model.biz;

import com.gunships.tngou.model.entity.GalleryNews;
import com.gunships.tngou.util.ResponseListener;
import com.gunships.tngou.util.Retrofit2Util;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Query;

/**
 * Created by 甘书须 on 2016/10/18.
 */
public class LoadClassGalleryBiz implements ILoadClassGalleryBiz {

    @Override
    public void loadClassGallery(int id, int page, final LoadNewsBiz.IReturnNewsListener listener) {
        ILoadClassGallery iLoadClassGallery = Retrofit2Util.retrofit().create(ILoadClassGallery.class);
        Call<GalleryNews> call = iLoadClassGallery.loadClassGallery(id,page,20);
        Retrofit2Util.addOnEnqueue(call, new ResponseListener<GalleryNews>() {
            @Override
            public void onRequestSuccess(int code, Response<GalleryNews> response) {
                if(response.body().getTngou()==null){
                    listener.NoNews();
                }else{
                    listener.getNews(response.body().getTngou());
                }
            }

            @Override
            public void onRequestFail(int code, String msg) {

            }
        });
    }
}
