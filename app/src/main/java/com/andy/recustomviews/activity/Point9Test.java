package com.andy.recustomviews.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.andy.recustomviews.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/4/10.
 */
public class Point9Test extends Base2Activity {

    @BindView(R.id.input)
    EditText input;
    @BindView(R.id.content1)
    TextView content1;
    @BindView(R.id.content2)
    TextView content2;
    @Override
    public int getLayoutId() {
        return R.layout.point9;
    }

    @Override
    public void init(Bundle bundle) {
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                content1.setText(charSequence.toString());
                content2.setText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
