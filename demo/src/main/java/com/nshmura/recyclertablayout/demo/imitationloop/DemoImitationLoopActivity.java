package com.nshmura.recyclertablayout.demo.imitationloop;

import com.nshmura.recyclertablayout.RecyclerTabLayout;
import com.nshmura.recyclertablayout.demo.Demo;
import com.nshmura.recyclertablayout.demo.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class DemoImitationLoopActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener {

    protected static final String KEY_DEMO = "demo";

    private int mScrollState;
    private DemoImitationLoopPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private ArrayList<String> mItems;

    public static void startActivity(Context context, Demo demo) {
        Intent intent = new Intent(context, DemoImitationLoopActivity.class);
        intent.putExtra(KEY_DEMO, demo.name());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_basic);

        Demo demo = Demo.valueOf(getIntent().getStringExtra(KEY_DEMO));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(demo.titleResId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mItems = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            mItems.add(String.valueOf(i));
        }

        mAdapter = new DemoImitationLoopPagerAdapter();
        mAdapter.addAll(mItems);

        mViewPager = findViewById(R.id.view_pager);
        //mViewPager.setAdapter(mAdapter);
        //mViewPager.setCurrentItem(mAdapter.getCenterPosition(0));
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TestFragment1());
        fragments.add(new TestFragment2());
        fragments.add(new TestFragment3());
        fragments.add(new TestFragment4());
        String[] title =new String[4];
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments, mItems.toArray(title)));
        mViewPager.addOnPageChangeListener(this);


        RecyclerTabLayout recyclerTabLayout = findViewById(R.id.recycler_tab_layout);
        recyclerTabLayout.setUpWithViewPager(mViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //got to center
        boolean nearLeftEdge = (position <= mItems.size());
        boolean nearRightEdge = (position >= mAdapter.getCount() - mItems.size());
        if (nearLeftEdge || nearRightEdge) {
            //mViewPager.setCurrentItem(mAdapter.getCenterPosition(0), false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mScrollState = state;
    }
}