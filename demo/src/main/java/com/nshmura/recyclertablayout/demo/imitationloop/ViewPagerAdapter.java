package com.nshmura.recyclertablayout.demo.imitationloop;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by huangmeng1 on 2018/5/7.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static final int NUMBER_OF_LOOPS = 10000;
    //保存每个Fragment的Tag，刷新页面的依据
    protected SparseArray<String> tags = new SparseArray<>();
    private List<Fragment> mFragmentList;
    private String[] mTitleList;
    private FragmentManager mFragmentManager;


    public ViewPagerAdapter(FragmentManager fm, List<Fragment> mFragmentList, String[] mTitleList) {
        super(fm);
        this.mFragmentList = mFragmentList;
        this.mTitleList = mTitleList;
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position % mTitleList.length);
    }

    @Override
    public long getItemId(int position) {
        return mFragmentList.get(position % mTitleList.length).hashCode();
    }

    @Override
    public int getItemPosition(Object object) {
        Fragment fragment = (Fragment) object;
        //如果Item对应的Tag存在，则不进行刷新
        if (tags.indexOfValue(fragment.getTag()) > -1) {
            return super.getItemPosition(object);
        }
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //得到缓存的fragment
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        String tag = fragment.getTag();
        //保存每个Fragment的Tag
        tags.put(position, tag);
        return fragment;
    }

    //拿到指定位置的Fragment
    public Fragment getFragmentByPosition(int position) {
        return mFragmentManager.findFragmentByTag(tags.get(position));
    }

    public List<Fragment> getFragments(){
        return mFragmentManager.getFragments();
    }

    //刷新指定位置的Fragment
    public void notifyFragmentByPosition(int position) {
        tags.removeAt(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFragmentList.size() * NUMBER_OF_LOOPS;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return getValueAt(position);
    }

    public String getValueAt(int position) {
        if (mTitleList.length== 0) {
            return null;
        }
        return mTitleList[position % mTitleList.length];
    }
    public int getCenterPosition(int position) {
        return mFragmentList.size() * NUMBER_OF_LOOPS / 2 + position;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}
