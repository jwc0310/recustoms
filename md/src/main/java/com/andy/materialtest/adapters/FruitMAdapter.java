package com.andy.materialtest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.materialtest.bean.Fruit;
import com.andy.materialtest.R;
import com.andy.materialtest.uis.FruitActivity;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 */
public class FruitMAdapter extends BaseAdapter {

    private Context context;
    private List<Fruit> list;

    public FruitMAdapter(Context context, List<Fruit> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.fruit_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        Fruit fruit = list.get(i);
        holder.fruitName.setText(fruit.getName());
        Glide.with(context).load(fruit.getImageId()).into(holder.fruitImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = i;
                Fruit fruit = list.get(pos);
                Intent intent = new Intent(context, FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME, fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, fruit.getImageId());
                context.startActivity(intent);
            }
        });
        return view;
    }

    static class ViewHolder {
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View itemView) {
            cardView = (CardView) itemView;
            fruitImage = (ImageView) itemView.findViewById(R.id.fruit_image);
            fruitName = (TextView) itemView.findViewById(R.id.fruit_name);
        }

    }
}
