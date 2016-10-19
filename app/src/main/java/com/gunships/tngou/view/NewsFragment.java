package com.gunships.tngou.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.gunships.tngou.R;
import com.gunships.tngou.model.entity.Gallery;
import com.gunships.tngou.presenter.NewsPresenter;
import com.gunships.tngou.util.NewsAdpter;


import java.util.List;


public class NewsFragment extends Fragment implements INewsView {

    private View view;
    //private LoadFinishCallBack loadFinishCallBack;
    private RecyclerView mRecyclerView;
    private List<Gallery> list;
    private GridLayoutManager gridLayourManager;
    private NewsAdpter adapter;
    private PtrClassicFrameLayout mPtrClassicFrameLayout;
    private NewsPresenter mNewsPresenter = new NewsPresenter(this);
    private RecyclerAdapterWithHF mRecyclerAdapterWithHF;
    private int id = 20;
    private int row = 10;
    private final static int REFRESH =200;
    private final static int LOAD =300;
    private final static int LIST =0;




    public NewsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.id = requsetLastId();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView_one);
//        mRecyclerView.setLayoutManager(gridLayourManager=new GridLayoutManager(getActivity(),2));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.pull_to_refresh_load_news);
        if(cheakNetworkState()) {
            requestNews(id,20, 0);//加载
        }
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler(){
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                id = id+row;
                if(cheakNetworkState()) {
                    requestNews(id, row, REFRESH);
                }else {
                    mPtrClassicFrameLayout.refreshComplete();
                }
            }
        });

        mPtrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                if(id<row){
                    NoNews();
                }else {
                    if(cheakNetworkState()) {
                        id = id - row;
                        requestNews(id, row, LOAD);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public int requsetLastId() {
        return mNewsPresenter.getLastId(getContext());

    }

    @Override
    public void saveLastId() {
        mNewsPresenter.setLastId(getContext(),id);
    }

    @Override
    public void requestNews(int id,int row,int model) {
//        this.id = id;
//        this.row = row;
        mNewsPresenter.requestNews(id,row,model);
    }

    @Override
    public void showGallery(List<Gallery> list) {
        adapter = new NewsAdpter(getActivity(), list);
        adapter.setOnItemClickListener(new NewsAdpter.OnItemClickListener() {
            @Override
            public void itemClick(View view, int position, int idData) {
                Intent intent = new Intent(getActivity(),ShowActivity.class);
                intent.putExtra("IndexFragment",LIST);
                intent.putExtra("id",idData);
                startActivity(intent);
            }
        });
        mRecyclerAdapterWithHF = new RecyclerAdapterWithHF(adapter);
        mRecyclerView.setAdapter(mRecyclerAdapterWithHF);

        mPtrClassicFrameLayout.setLoadMoreEnable(true);
    }

    @Override
    public void NoNews() {
        Toast.makeText(getActivity(),"没有更多的图集了",Toast.LENGTH_LONG).show();
    }

    @Override
    public void pullToRefresh(List<Gallery> list) {
        adapter.refreshList(list);
        mPtrClassicFrameLayout.refreshComplete();
        adapter.notifyDataSetChanged();
        saveLastId();
        //adapter.clear();
    }

    @Override
    public void pullOnLoading(List<Gallery> list) {
        adapter.loadList(list);
        mPtrClassicFrameLayout.loadMoreComplete(true);
        adapter.notifyDataSetChanged();
        saveLastId();
        //adapter.clear();
    }

    @Override
    public boolean cheakNetworkState() {
        return mNewsPresenter.checkConnection();
    }

    @Override
    public void networkWarn() {
        Snackbar.make(view,"当前没有网络，无法加载图集",Snackbar.LENGTH_LONG).setAction("打开网络", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewsPresenter.changeConnection();
            }
        }).show();
    }

    @Override
    public void wifiWarn() {
        Snackbar.make(view,"内置大量高清图片请在WIFI下体验！",Snackbar.LENGTH_LONG).setAction("连接WIFI", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewsPresenter.changeConnection();
            }
        }).show();
    }

    private int getMaxElem(int[] arr) {
        int size = arr.length;
        int maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (arr[i]>maxVal)
                maxVal = arr[i];
        }
        return maxVal;
    }
}
