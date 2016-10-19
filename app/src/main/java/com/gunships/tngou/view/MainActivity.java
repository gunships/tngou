package com.gunships.tngou.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment; ;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.gunships.tngou.R;
import com.gunships.tngou.util.MyFragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MyFragmentPagerAdapter adapter;
    private NewsFragment newImgFragment;
    private ClassFragment classFragment;
    private static String[] titles ={"最新","分类"};
    private List<Fragment> fragmentList;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        //初始化Fragment
        newImgFragment = new NewsFragment();
        classFragment = new ClassFragment();
        //Fragment添加到list
        fragmentList = new ArrayList<>();
        fragmentList.add(newImgFragment);
        fragmentList.add(classFragment);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList,titles,
                this);

        //viewpager加载adapter
        viewPager.setAdapter(adapter);
        //为tabLayout设置viewPager
        tabLayout.setupWithViewPager(viewPager);

    }



}
