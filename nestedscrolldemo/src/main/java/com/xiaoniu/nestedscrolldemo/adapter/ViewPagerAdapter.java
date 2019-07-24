package com.xiaoniu.nestedscrolldemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author xiaoniu
 * @date 2019/7/23.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = null;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        fragments = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 :fragments.size();
    }


}
