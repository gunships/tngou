package com.gunships.tngou.model.biz;

import android.util.Log;

import com.gunships.tngou.model.entity.Gallery;
import com.gunships.tngou.model.entity.GalleryNews;
import com.gunships.tngou.model.entity.GalleryPicture;
import com.gunships.tngou.util.ResponseListener;
import com.gunships.tngou.util.Retrofit2Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Query;

/**
 * Created by 甘书须 on 2016/10/13.
 */
public class LoadPictureBiz implements ILoadPictureBiz {
    public interface IReturnGalleryListener<T>{
        void getGallery(T t);
        void NoGallery();
    }

    private final static String TAG ="LoadPictureBiz";
    @Override
    public void loadPicture(int id,final IReturnGalleryListener listener){
        ILoadPicture iLoadPicture = Retrofit2Util.retrofit().create(ILoadPicture.class);
        Call<GalleryPicture> call = iLoadPicture.loadNewsGallery(id);
        Retrofit2Util.addOnEnqueue(call, new ResponseListener<GalleryPicture>() {
            @Override
            public void onRequestSuccess(int code, Response<GalleryPicture> response) {
                Log.i(TAG,"get the response");
                listener.getGallery(response.body());
            }
            @Override
            public void onRequestFail(int code, String msg) {
                listener.NoGallery();
            }
        });

    }


}
