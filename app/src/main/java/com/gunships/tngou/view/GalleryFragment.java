package com.gunships.tngou.view;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.gunships.tngou.R;
import com.gunships.tngou.model.entity.Gallery;
import com.gunships.tngou.model.entity.GalleryPicture;
import com.gunships.tngou.model.entity.Picture;
import com.gunships.tngou.util.GalleryAdapter;
import com.gunships.tngou.util.GalleryViewPagerAdapter;
import com.gunships.tngou.util.MViewPager;
import com.gunships.tngou.util.PinchImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GalleryFragment extends Fragment{

    private View view;
    private int position;
    private List<Picture> data;
    private MViewPager viewpager;
    private GalleryViewPagerAdapter adapter;
    List<View> listView = new ArrayList<>();
    private final static String headerUrl = "http://tnfs.tngou.net/img";
    public GalleryFragment() {
    }

    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            data = (List<Picture>) getArguments().getSerializable("data");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gallery, container, false);
        Window window = getActivity().getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewpager = (MViewPager) view.findViewById(R.id.viewpager_picture_content);



        init();
        return view;
    }

    private void init() {
        for(int i=0;i<data.size();i++){
           View view = LayoutInflater.from(getActivity()).inflate(R.layout.viewpager_picture,null);
            ImageView iv = (ImageView) view.findViewById(R.id.viewpager_pictrue_imageview);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });
            Picasso.with(getActivity()).load(headerUrl +data.get(i).getSrc()).placeholder(R.mipmap.ic_loading_large).into(iv);
            listView.add(view);
        }
        if(adapter==null) {
            adapter = new GalleryViewPagerAdapter(listView);
        }
        viewpager.setAdapter(adapter);
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                viewpager.setCurrentItem(position);
            }
        });
        //viewpager.setCurrentItem(position);

    }

    @Override
    public void onResume(){
        super.onResume();
    }
    @Override
    public void onDestroyView() {
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(view !=null){
            ((ViewGroup)view.getParent()).removeView(view);
        }
        super.onDestroyView();
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
