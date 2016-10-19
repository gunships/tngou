package com.gunships.tngou.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gunships.tngou.R;
import com.gunships.tngou.util.ClassAdapter;


public class ClassFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ClassAdapter mAdpter;
    private final static int CLASS =2;
    public ClassFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView_class);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mAdpter = new ClassAdapter(getActivity());
        mAdpter.setOnItemClickListener(new ClassAdapter.OnItemClickListener() {
            @Override
            public void itemClick(View view, int position, int idData) {
                Intent intent = new Intent(getActivity(),ShowActivity.class);
                intent.putExtra("IndexFragment",CLASS);
                intent.putExtra("classId",idData);
                startActivity(intent);
            }

            @Override
            public void itemLongClick(View view, int position, int idData) {

            }
        });
        mRecyclerView.setAdapter(mAdpter);
        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
