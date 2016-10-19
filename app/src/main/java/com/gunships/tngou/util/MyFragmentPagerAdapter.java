package com.gunships.tngou.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.List;

/**
 * Created by 甘书须 on 2016/9/30.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    //public final int COUNT = 2;
    private String[] titles;
    private List<Fragment> fragmentsList;
    private Context context;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentsList,String[] titles, Context context){
        super(fm);
        this.fragmentsList = fragmentsList;
        this.titles = titles;
        this.context = context;
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();

    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles[position];
    }
}
