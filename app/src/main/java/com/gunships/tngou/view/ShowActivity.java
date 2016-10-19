package com.gunships.tngou.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.gunships.tngou.R;


public class ShowActivity extends AppCompatActivity {


    private final static int LIST =0;
    private final static int GRALLERY =1;
    private final static int CLASSIFY =2;
    private int index=0;

    private Fragment mGalleryFragment;
    private Fragment mGalleryListFragment;
    private Fragment mClassGallerysFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        init();
    }

    private void init() {
        int index = getIntent().getIntExtra("IndexFragment",LIST);
        showFragmentByTag(index);
        mGalleryFragment = new GalleryFragment();

    }

    private void showFragmentByTag(int index) {
        FragmentTransaction fmt = getSupportFragmentManager().beginTransaction();
        hideOtherFragment(fmt);
        switch (index){
            case LIST:
                if(mGalleryListFragment!=null){
                    fmt.show(mGalleryListFragment);
                }else{
                    mGalleryListFragment = new GalleryListFragment();
                    fmt.add(R.id.content_show,mGalleryListFragment,"LIST");
                }
                break;
            case GRALLERY:
                if(mGalleryFragment!=null){
                    fmt.show(mGalleryFragment);
                }else {
                    mGalleryFragment = new GalleryFragment();
                    fmt.add(R.id.content_show,mGalleryFragment,"GRALLERY");
                }
                break;
            case CLASSIFY:
                if(mClassGallerysFragment!=null){
                    fmt.show(mClassGallerysFragment);
                }else{
                    mClassGallerysFragment = new ClassGalleryFragment();
                    fmt.add(R.id.content_show,mClassGallerysFragment,"CLASSIFY");
                }

        }
        fmt.commit();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private void hideOtherFragment(FragmentTransaction mFragmentTransaction) {
        if(mGalleryListFragment!=null){
            mFragmentTransaction.hide(mGalleryListFragment);
        }
        if(mGalleryFragment!=null){
            mFragmentTransaction.hide(mGalleryFragment);
        }
    }
}
