package com.andy.recustomviews.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.andy.recustomviews.R;
import com.andy.recustomviews.VerticalViewPager.directionalviewpager.DirectionalViewPager;
import com.andy.recustomviews.VerticalViewPager.newversion.TestFragmentAdapter;

public class VerticalActivity extends FragmentActivity {
	private DirectionalViewPager mDirectionalViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_vertical);
		// Set up the pager
		mDirectionalViewPager = (DirectionalViewPager) findViewById(R.id.pager);
		mDirectionalViewPager.setAdapter(new TestFragmentAdapter(
				getSupportFragmentManager()));
		mDirectionalViewPager.setOrientation(DirectionalViewPager.VERTICAL);

	}

}
