package com.othershe.mdview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TypeListAdapter extends RecyclerView.Adapter<TypeListAdapter.ViewHolder> {

    private ArrayList<String> mDatas;
    private List<Integer> mHeights;
    private OnItemClickListener mListner;

    public TypeListAdapter(ArrayList<String> datas) {
        if (datas != null) {
            mDatas = datas;
        } else {
            mDatas = new ArrayList<>();
        }
        mHeights = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            mHeights.add((int) (260 + Math.random() * 100));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ViewGroup.LayoutParams params = holder.itemTv.getLayoutParams();
        params.height = mHeights.get(position);
        holder.itemTv.setLayoutParams(params);
        holder.itemTv.setText(mDatas.get(position));
        if (mListner != null) {
            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListner.onItemClick(mDatas.get(position), position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_tv)
        TextView itemTv;
        @BindView(R.id.item_layout)
        View itemLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListner = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String data, int position);
    }
}
