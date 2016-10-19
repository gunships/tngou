package com.gunships.tngou.util;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gunships.tngou.model.entity.Picture;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by 甘书须 on 2016/10/16.
 */
public class GalleryViewPagerAdapter extends PagerAdapter{

    private List<View> list;

    public GalleryViewPagerAdapter(List list){
        this.list = list;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {

        view.removeView(list.get(position));
    }
    // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        view.addView(list.get(position));
        return list.get(position);
    }
}
