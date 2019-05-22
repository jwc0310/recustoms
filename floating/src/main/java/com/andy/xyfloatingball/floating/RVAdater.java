package com.andy.xyfloatingball.floating;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.andy.xyfloatingball.R;

import java.util.List;

/**
 * Created by Administrator on 2017/1/12.
 */
public class RVAdater extends RecyclerView.Adapter<RVAdater.RVHolder> {

    private Context context;
    private List<AppBean.AppInfo> list;
    private OnItemClickListener onItemClickListener;
    private LayoutInflater inflater;

    public RVAdater(Context context, List<AppBean.AppInfo> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RVAdater.RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVHolder(inflater.inflate(R.layout.item_pic, parent, false));
    }

    @Override
    public void onBindViewHolder(final RVAdater.RVHolder holder, final int position) {
        Glide.with(context)
                .load(list.get(position).getIconUrl())
                .placeholder(R.drawable.app_loading_icon)
                .into(holder.iv_icon);
        holder.tv_name.setText(list.get(position).getName());
        holder.ll_pic_item.setClickable(true);
        holder.ll_pic_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ll_pic_item.setClickable(false);
                holder.tv_name.setText("准备下载");
                if (onItemClickListener != null && position < list.size()) {
                    onItemClickListener.onItemClick(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size() > 5 ? 5 : list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class RVHolder extends RecyclerView.ViewHolder{
        public ImageView iv_icon;
        public TextView tv_name;
        public LinearLayout ll_pic_item;
        public RVHolder(View itemView){
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            ll_pic_item = (LinearLayout) itemView.findViewById(R.id.ll_pic_item);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

}
