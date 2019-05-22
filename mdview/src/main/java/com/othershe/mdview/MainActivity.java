package com.othershe.mdview;

import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.main_content)
    CoordinatorLayout mCoordinatorLayout;

    private Unbinder unbinder;
    private ArrayList<TypeFragment> mFragments;
    private ArrayList<String> mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        setUpDrawer();
        initNavigationView();
        initContent();
    }

    private void initContent() {
        mFragments = new ArrayList<>();
        mFragments.add(TypeFragment.newInstance("热点"));
        mFragments.add(TypeFragment.newInstance("军事"));
        mFragments.add(TypeFragment.newInstance("科技"));
        mFragments.add(TypeFragment.newInstance("娱乐"));

        mTitles = new ArrayList<>();
        mTitles.add("热点");
        mTitles.add("军事");
        mTitles.add("科技");
        mTitles.add("娱乐");

        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager());
        adapter.setArguments(mFragments, mTitles);
        mViewPager.setAdapter(adapter);

        //设置TabLayout可滚动，保证Tab数量过多时也可正常显示
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //两个参数分别对应Tab未选中的文字颜色和选中的文字颜色
        mTabLayout.setTabTextColors(Color.WHITE, Color.RED);
        //绑定ViewPager
        mTabLayout.setupWithViewPager(mViewPager);
        //设置TabLayout的布局方式（GRAVITY_FILL、GRAVITY_CENTER）
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //设置TabLayout的选择监听
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            //重复点击时回调
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                scrollToTop(mFragments.get(tab.getPosition()).getTypeList());
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(mCoordinatorLayout, "点我分享哦！", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 滚动到列表顶部
     *
     * @param recyclerView
     */
    private void scrollToTop(RecyclerView recyclerView) {
        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
        int[] lastPositions = manager.findLastVisibleItemPositions(null);
        if (lastPositions[0] < 15) {
            recyclerView.smoothScrollToPosition(0);
        } else {
            recyclerView.scrollToPosition(0);
        }
    }

    private void setUpDrawer() {
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        //设置左上角显示三道横线
        toggle.syncState();

        mToolbar.setTitle("MdView");

        //设置Toolbar左上角图标
//        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDrawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
    }

    private void initNavigationView() {
        //初始化NavigationView顶部head的icon和name
        ImageView icon = (ImageView) mNavView.getHeaderView(0).findViewById(R.id.nav_head_icon);
        icon.setImageResource(R.mipmap.ic_launcher);
        TextView name = (TextView) mNavView.getHeaderView(0).findViewById(R.id.nav_head_name);
        name.setText("VipOthershe");
        //设置NavigationView对应menu item的点击事情
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_item1:
                        break;
                    case R.id.nav_item2:
                        break;
                    case R.id.nav_set:
                        break;
                    case R.id.menu_share:
                        break;
                    case R.id.nav_about:
                        break;
                }
                //隐藏NavigationView
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public class TabPagerAdapter extends FragmentPagerAdapter {
        private List<TypeFragment> fragments;
        private List<String> titles;

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setArguments(List<TypeFragment> fragments, List<String> titles) {
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        //返回TabLayout对应Tab的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_share:
                break;
            case R.id.menu_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
