package concert.marquee.xiaoniu.com.viewpagertest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author xiaoniu
 * @date 2019/4/22.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment>  fragments;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment>  list) {
        super(fm);
        this.fragments = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
