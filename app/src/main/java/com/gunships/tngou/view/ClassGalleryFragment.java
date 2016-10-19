package com.gunships.tngou.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.gunships.tngou.R;
import com.gunships.tngou.model.biz.GetGalleryclass;
import com.gunships.tngou.model.entity.Gallery;
import com.gunships.tngou.presenter.ClassGalleryPresenter;
import com.gunships.tngou.presenter.NewsPresenter;
import com.gunships.tngou.util.NewsAdpter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassGalleryFragment extends Fragment implements IClassView{
    private View view;
    private RecyclerView mRecyclerView;
    private List<Gallery> list;
    private NewsAdpter adapter;
    private PtrClassicFrameLayout mPtrClassicFrameLayout;
    private ClassGalleryPresenter mClassGalleryPresenter = new ClassGalleryPresenter(this);
    private RecyclerAdapterWithHF mRecyclerAdapterWithHF;
    private GalleryListFragment mGalleryListFragment;
    private ClassGalleryFragment mClassGalleryFragment = this;
    private int classId;
    private int page = 1;
    private final static int REFRESH =200;
    private final static int LOAD =300;
    private final static int LIST =0;
    private final static int CLASSIFY =2;
    public ClassGalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        classId=getActivity().getIntent().getIntExtra("classId",1);
        view = inflater.inflate(R.layout.fragment_class_gallery, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.galleryclass_toolbar);
        toolbar.setTitle(GetGalleryclass.galleryclasses[classId].getName());
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView_galleryclass);
//        mRecyclerView.setLayoutManager(gridLayourManager=new GridLayoutManager(getActivity(),2));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.pull_to_refresh_load_class);
        if(cheakNetworkState()) {
            requestNews(classId, page, 0);//加载
        }
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler(){
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if(cheakNetworkState()) {
                    Snackbar.make(view,"已经是最新数据了",Snackbar.LENGTH_LONG).show();
                }
                mPtrClassicFrameLayout.refreshComplete();
            }
        });

        mPtrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                     page=page+1;
                    if(cheakNetworkState()) {
                        requestNews(classId, page, LOAD);
                    }
                }
        });
        return view;
    }

    @Override
    public void requestNews(int id,int row,int model) {
        mClassGalleryPresenter.requestClassGallery(id,row,model);
    }

    @Override
    public void showGallery(List<Gallery> list) {
        adapter = new NewsAdpter(getActivity(), list);
        adapter.setOnItemClickListener(new NewsAdpter.OnItemClickListener() {
            @Override
            public void itemClick(View view, int position, int idData) {
                FragmentTransaction fmt = getActivity().getSupportFragmentManager().beginTransaction();

                if(mGalleryListFragment!=null){
                    //fmt.show(mGalleryFragment);
                    fmt.add(R.id.content_show,mGalleryListFragment,"LIST");
                }else {
                    mGalleryListFragment = new GalleryListFragment();
                    fmt.add(R.id.content_show,mGalleryListFragment,"LIST");
                    Bundle bundle = new Bundle();
                    bundle.putInt("id",idData);
                    mGalleryListFragment.setArguments(bundle);
                }
                fmt.hide(mClassGalleryFragment);
                fmt.addToBackStack(null);
                fmt.commit();

            }
        });
        mRecyclerAdapterWithHF = new RecyclerAdapterWithHF(adapter);
        mRecyclerView.setAdapter(mRecyclerAdapterWithHF);

        mPtrClassicFrameLayout.setLoadMoreEnable(true);
    }

    @Override
    public void NoNews() {
        Toast.makeText(getActivity(),"加载不到图集了",Toast.LENGTH_LONG).show();
    }

    @Override
    public void pullToRefresh(List<Gallery> list) {
        mPtrClassicFrameLayout.refreshComplete();
        //adapter.clear();
    }

    @Override
    public void pullOnLoading(List<Gallery> list) {
        adapter.loadList(list);
        mPtrClassicFrameLayout.loadMoreComplete(true);
        adapter.notifyDataSetChanged();
        //adapter.clear();
    }

    @Override
    public boolean cheakNetworkState() {
        return mClassGalleryPresenter.checkConnection();
    }

    @Override
    public void networkWarn() {
        Snackbar.make(view,"当前没有网络，无法加载图集",Snackbar.LENGTH_LONG).setAction("打开网络", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClassGalleryPresenter.changeConnection();
            }
        }).show();
    }

    @Override
    public void wifiWarn() {
        Snackbar.make(view,"内置大量高清图片请在WIFI下体验！",Snackbar.LENGTH_LONG).setAction("连接WIFI", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClassGalleryPresenter.changeConnection();
            }
        }).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
