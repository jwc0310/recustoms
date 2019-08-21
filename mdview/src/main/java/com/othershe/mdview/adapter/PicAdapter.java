package com.othershe.mdview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.othershe.mdview.R;
import com.othershe.mdview.bean.PicBean;
import com.othershe.mdview.util.PicsUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PicAdapter extends RecyclerView.Adapter<PicAdapter.ViewHolder> {

    private ArrayList<PicBean> mDatas;
    private List<Integer> mHeights;
    private OnItemClickListener mListner;

    private String[] picUrls = new String[] {
            "http://img4.imgtn.bdimg.com/it/u=1522331472,1315798876&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=1081093855,1690217787&fm=26&gp=0.jpg",
            "http://dik.img.kttpdq.com/pic/27/18299/f31c1951c3218dfe_1366x768.jpg",
            "http://gss0.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/f3d3572c11dfa9ec447a380362d0f703918fc14a.jpg",
            "http://dik.img.kttpdq.com/pic/27/18251/62e8f6a773227879_1680x1050.jpg",
            "http://img4.imgtn.bdimg.com/it/u=1644049009,3950559757&fm=26&gp=0.jpg",
            "http://img.mp.itc.cn/upload/20160605/ec83519c7d454e08803e2c27bb21b260_th.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1237834921,2207182615&fm=26&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=3361297039,4212084735&fm=26&gp=0.jpg",
            "http://jiaxiaozhijia.image.mucang.cn/jiaxiaozhijia/2017/04/28/12/76b46f1152154082985c4f0852c8cdb5.png",
            "http://img4.imgtn.bdimg.com/it/u=3479273725,2856322297&fm=26&gp=0.jpg",
            "http://e.hiphotos.baidu.com/zhidao/pic/item/1e30e924b899a901e0621e251c950a7b0208f54a.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2490886752,633085793&fm=26&gp=0.jpg"
    };

    public PicAdapter() {
        mDatas = new ArrayList<>();
        int size = picUrls.length;
        for (int i = 0; i < size; i++) {
            PicBean bean = new PicBean();
            bean.setUrl(picUrls[i]);
            mDatas.add(bean);
        }

        mHeights = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            mHeights.add((int) (260 + Math.random() * 100));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ViewGroup.LayoutParams params = holder.itemIv.getLayoutParams();
        params.height = mHeights.get(position);
        holder.itemIv.setLayoutParams(params);

        final String url = mDatas.get(position).getUrl();
        if (position == 0) //normal
            PicsUtil.getInstance().loadPic(holder.itemIv.getContext(), url, holder.itemIv);
        else if (position == 1) {   //圆形图片
            PicsUtil.getInstance().loadRoundPic(holder.itemIv.getContext(), url, holder.itemIv);
        } else if (position == 2) {   //圆角
            PicsUtil.getInstance().loadRoundCornerPic(holder.itemIv.getContext(), url, holder.itemIv);
        } else if (position == 3) {   //模糊
            PicsUtil.getInstance().loadBlurPic(holder.itemIv.getContext(), url, holder.itemIv);
        } else
            PicsUtil.getInstance().loadPic(holder.itemIv.getContext(), url, holder.itemIv);
        if (mListner != null) {
            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListner.onItemClick(mDatas.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_iv)
        ImageView itemIv;
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
        void onItemClick(PicBean data, int position);
    }
}
