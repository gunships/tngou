package com.gunships.tngou.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 甘书须 on 2016/9/30.
 */
public interface INewsPresenter {

    int getLastId(Context context);
    void setLastId(Context context,int lastId);
    void requestNews(int id,int row,final int model);
    boolean checkConnection();
    void changeConnection();
}
