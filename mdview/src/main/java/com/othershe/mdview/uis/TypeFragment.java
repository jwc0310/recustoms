package com.othershe.mdview.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.othershe.mdview.R;
import com.othershe.mdview.TypeListAdapter;
import com.othershe.mdview.bases.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TypeFragment extends BaseFragment {
    @BindView(R.id.type_list)
    RecyclerView mTypeList;

    private String mType;
    private Context mContext;
    private ArrayList<String> datas = new ArrayList<>();

    @Override
    protected int layoutId() {
        return R.layout.fragment_type;
    }

    @Override
    protected void initView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mTypeList.setLayoutManager(layoutManager);
        TypeListAdapter adapter = new TypeListAdapter(datas);
        adapter.setOnItemClickListener(new TypeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                startActivity(new Intent(mContext, DetailActivity.class));
            }
        });
        mTypeList.setAdapter(adapter);
    }

    public TypeFragment() {
        // Required empty public constructor
    }

    public static TypeFragment newInstance(String type) {
        TypeFragment fragment = new TypeFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString("type");

            for (int i = 0; i < 35; i++) {
                datas.add(mType + (i + 1));
            }
        }
    }


    public RecyclerView getTypeList() {
        return mTypeList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
