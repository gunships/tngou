package com.gunships.tngou.util;

import android.content.Context;
import android.util.Log;

import com.gunships.tngou.application.App;
import com.gunships.tngou.model.entity.GalleryNews;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 甘书须 on 2016/9/29.
 */
public class Retrofit2Util {

    private static final String TAG = "Retrofit2Util";
    private static Retrofit mRetrofit;
    private static WeakReference<Context> mContextRef;
    private static String AppURL="http://www.tngou.net/tnfs/api/";

    public static Retrofit retrofit(){
        if(mRetrofit == null) {
            mRetrofit = new Retrofit.Builder().baseUrl(AppURL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return mRetrofit;
    }

    public static <T> void addOnEnqueue(Call<T> call, final ResponseListener<T> listener){

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {

                if(response.body()!=null){
                    if(response.code()==200){
                        Log.i(TAG,"Success response");
                        listener.onRequestSuccess(response.code(),response);
                    }else {
                        Log.i(TAG,"Fail response code:"+response.code());
                        listener.onRequestFail(response.code(),response.message());
                    }
                }else {
                    Log.i(TAG,"Fail response");
                    listener.onRequestFail(404,response.message());
                }

            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.w(TAG,"Fail response onFailure");
                listener.onRequestFail(404,null);
            }
        });
    }

}
