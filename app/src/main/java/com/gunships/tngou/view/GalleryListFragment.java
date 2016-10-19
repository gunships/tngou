package com.gunships.tngou.view;


import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gunships.tngou.R;
import com.gunships.tngou.model.entity.GalleryPicture;
import com.gunships.tngou.presenter.GalleryPresenter;
import com.gunships.tngou.util.GalleryAdapter;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryListFragment extends Fragment implements IGalleryListView{

    private View view;
    private PopupWindow popupWindow;
    private RecyclerView recyclerView;
    private GalleryAdapter adapter;
    private GalleryFragment mGalleryFragment;
    private GalleryPresenter mGalleryPresenter = new GalleryPresenter(this);
    private GalleryListFragment galleryListFragment = this;
    private TextView saveBitmap;
    private TextView back;
    int i=0;
    private Toolbar toolbar;
    private int id;
    public GalleryListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gallery_list, container, false);
        int temp = getActivity().getIntent().getIntExtra("id",0);
        id = temp!=0?temp:this.getArguments().getInt("id",0);
        init();
        return view;
    }

    private void init() {
        requestGallery(id);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_gallery);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        adapter = new GalleryAdapter(getContext());

    }


    @Override
    public void requestGallery(int id) {
        mGalleryPresenter.requestGallery(id);
    }

    @Override
    public void showGallery(final GalleryPicture data) {
        toolbar.setTitle(data.getTitle());
        adapter.setData(data.getList());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void itemClick(View view, int position) {
                FragmentTransaction fmt = getActivity().getSupportFragmentManager().beginTransaction();

                if(mGalleryFragment!=null){
                    //fmt.show(mGalleryFragment);
                    fmt.add(R.id.content_show,mGalleryFragment,"GRALLERY");
                }else {
                    mGalleryFragment = new GalleryFragment();
                    fmt.add(R.id.content_show,mGalleryFragment,"GRALLERY");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", (Serializable) data.getList());
                    mGalleryFragment.setArguments(bundle);
                }
                mGalleryFragment.setPosition(position);
                fmt.hide(galleryListFragment);
                fmt.addToBackStack(null);
                fmt.commit();
            }
            @Override
            public void itemLongClick(View view, int position) {
                showPopupWindow(position);
            }
        });

    }

    @Override
    public void showPopupWindow(final int position) {

        popupWindow = new PopupWindow(getContext());
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popupwindow_sava_bitmap,null);
        popupWindow.setContentView(contentView);
        saveBitmap = (TextView)contentView.findViewById(R.id.textView_sava_bitmap);
        back = (TextView)contentView.findViewById(R.id.textView_back);
        popupWindow.setWidth(view.getWidth());
        popupWindow.setHeight(300);

        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        saveBitmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.onSaveBitmap(position);
                popupWindow.dismiss();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
    }


    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_share:
                    break;
                case R.id.action_settings:
                    break;
            }
            return true;
        }
    };

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        adapter.closeThreadPool();
    }
}
