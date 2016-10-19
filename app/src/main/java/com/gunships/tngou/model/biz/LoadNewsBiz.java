package com.gunships.tngou.model.biz;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.gunships.tngou.model.entity.Gallery;
import com.gunships.tngou.model.entity.GalleryNews;
import com.gunships.tngou.presenter.INewsPresenter;
import com.gunships.tngou.util.ImageLoader;
import com.gunships.tngou.util.ResponseListener;
import com.gunships.tngou.util.Retrofit2Util;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 甘书须 on 2016/9/30.
 */
public class LoadNewsBiz implements ILoadNewsBiz {

    public interface IReturnNewsListener<T> {
        void getNews(List<T> list);
        void NoNews();
    }
    private final static String TAG ="LoadNewsBiz";

    @Override
    public int findLastId(Context context) {
        SharedPreferences sp = context.getSharedPreferences("tngou",Context.MODE_PRIVATE);
        return sp.getInt("lastId",0);
    }

    @Override
    public void markLastId(Context context,int lastId) {
        Log.i("eeeeeeeeeeeeee","");
        SharedPreferences sp = context.getSharedPreferences("tngou",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("lastId",lastId);
        Log.i("11111111111111111111",lastId+"");
        editor.commit();

    }

    @Override
    public void loadGallery(int id,int row,final IReturnNewsListener listener) {

        ILoadNews iLoadNewGallery = Retrofit2Util.retrofit().create(ILoadNews.class);
        Call<GalleryNews> call = iLoadNewGallery.loadNewsGallery(id,row);
        Retrofit2Util.addOnEnqueue(call, new ResponseListener<GalleryNews>() {

            @Override
            public void onRequestSuccess(int code, Response<GalleryNews> response) {
                //Log.i(TAG,"result:"+response.body().toString());
                if(!isNewest(response.body().getTotal())) {
                    //Log.i(TAG,sortDesc(response.body().getTngou()).toString());
                    listener.getNews(sortDesc(response.body().getTngou()));
                }else {
                    listener.NoNews();
                }
            }
            @Override
            public void onRequestFail(int code, String msg) {
                listener.NoNews();
                Log.i(TAG,"没有拿到Request");
            }
        });
    }
    public boolean isNewest(int count){
        if(count==0){
            return true;
        }else {
            return false;
        }
    }
    public List<Gallery> sortDesc(List<Gallery> list){
//        List<Gallery> temp;
//        temp = list;
        Collections.sort(list, new Comparator<Gallery>() {
            @Override
            public int compare(Gallery o1, Gallery o2) {
                return o1.getId()>o2.getId()?-1:1;
            }
        });
        return list;
    }


}
