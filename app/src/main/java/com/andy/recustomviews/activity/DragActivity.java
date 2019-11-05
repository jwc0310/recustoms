package com.andy.recustomviews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.andy.recustomviews.R;
import com.andy.recustomviews.views.DragLayout;

/**
 * Created by Flavien Laurent (flavienlaurent.com) on 23/08/13.
 */
public class DragActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);

        DragLayout dragLayout = (DragLayout) findViewById(R.id.dragLayout);

        if(getIntent().getBooleanExtra("horizontal", false)) {
            dragLayout.setDragHorizontal(true);
            dragLayout.setDragVertical(true);
        }
        if(getIntent().getBooleanExtra("vertical", false)) {
            dragLayout.setDragVertical(true);
        }
        if(getIntent().getBooleanExtra("edge", false)) {
            dragLayout.setDragEdge(true);
        }
        if(getIntent().getBooleanExtra("capture", false)) {
            dragLayout.setDragCapture(true);
        }
    }
}
