package com.othershe.mdview.uis;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.othershe.mdview.R;
import com.othershe.mdview.adapter.PicAdapter;
import com.othershe.mdview.bases.BaseFragment;
import com.othershe.mdview.bean.PicBean;

import butterknife.BindView;

public class PicFragment extends BaseFragment implements PicAdapter.OnItemClickListener {
    @BindView(R.id.type_list)
    RecyclerView mTypeList;

    private String mType;
    private Context mContext;

    @Override
    protected int layoutId() {
        return R.layout.fragment_pic;
    }

    @Override
    protected void initView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mTypeList.setLayoutManager(layoutManager);
        PicAdapter adapter = new PicAdapter();
        adapter.setOnItemClickListener(this);
        mTypeList.setAdapter(adapter);
    }

    public PicFragment() {
        // Required empty public constructor
    }

    public static PicFragment newInstance(String type) {
        PicFragment fragment = new PicFragment();
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
        }
    }


    public RecyclerView getTypeList() {
        return mTypeList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemClick(PicBean data, int position) {

    }
}
