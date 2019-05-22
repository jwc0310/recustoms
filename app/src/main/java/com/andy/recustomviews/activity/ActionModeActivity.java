package com.andy.recustomviews.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.recustomviews.R;
import com.andy.recustomviews.interfaces.ClickListeners;

import java.util.ArrayList;
import java.util.List;

/**
 * action_mode
 * Created by Administrator on 2016/12/26.
 */
public class ActionModeActivity extends BaseActivity implements ClickListeners {
    private static final String TAG = ActionModeActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<String> list;
    private RVAdapter rvAdapter;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activtiy_action_mode);
        recyclerView = (RecyclerView) findViewById(R.id.rv_action_mode);
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            list.add("测试用的 "+i);
        }
        rvAdapter = new RVAdapter();
        rvAdapter.setClickListeners(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration vertical = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        vertical.setDrawable(getResources().getDrawable(R.drawable.rv_divider));
        DividerItemDecoration horizontal = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        //horizontal.setDrawable(getResources().getDrawable(R.drawable.rv_divider_width));
        recyclerView.addItemDecoration(vertical);

    }

    @Override
    public boolean onLongClickListener(View view, int position) {
        Log.e("Andy", "long click");
        startActionMode(acCallback);
        view.setSelected(true);
        return true;
    }

    @Override
    public void onClickListener(View view, int position) {
        Log.e("Andy", "click");
    }

    private ActionMode.Callback acCallback = new ActionMode.Callback(){

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            Log.e(TAG, "onCreateActionMode ==> ");
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.actionmenu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            Log.e(TAG, "onPrepareActionMode ==> ");
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            int id = menuItem.getItemId();
            String tmp = "";
            if (R.id.item1 == id){
                tmp = "item1";
            }else if (R.id.item2 == id){
                tmp = "item2";
            }else if (R.id.item3 == id){
                tmp = "item3";
            }else if (R.id.item4 == id){
                tmp = "item4";
            }else {
                return false;
            }
            Toast.makeText(ActionModeActivity.this, tmp, Toast.LENGTH_SHORT).show();

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            Log.e(TAG, "onDestroyActionMode ==> ");
        }
    };

    private class RVAdapter extends RecyclerView.Adapter<RVViewHolder>{
        private ClickListeners clickListeners;

        @Override
        public RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action_mode, null);
            return new RVViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RVViewHolder holder, final int position) {
            holder.item_tv.setText(list.get(position));
            holder.item_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListeners != null){
                        clickListeners.onClickListener(view, position);
                    }
                }
            });
            holder.item_ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (clickListeners != null){
                        return clickListeners.onLongClickListener(view , position);
                    }
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setClickListeners(ClickListeners clickListeners) {
            this.clickListeners = clickListeners;
        }
    }

    private class RVViewHolder extends RecyclerView.ViewHolder{
        private TextView item_tv;
        private View item_ll;
        public RVViewHolder(View itemView) {
            super(itemView);
            item_ll = itemView.findViewById(R.id.item_ll_action_mode);
            item_tv = (TextView) itemView.findViewById(R.id.item_tv_action_mode);
        }
    }

}
