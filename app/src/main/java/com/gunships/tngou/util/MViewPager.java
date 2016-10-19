package com.gunships.tngou.util;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by 甘书须 on 2016/10/17.
 */
public class MViewPager extends ViewPager {
    private boolean isCanScroll = true;

    public MViewPager(Context context) {
        super(context);
    }

    public MViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (isCanScroll) {
            super.scrollTo(x, y);
        }
    }
}
