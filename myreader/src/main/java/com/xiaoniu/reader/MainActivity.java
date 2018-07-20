package com.xiaoniu.reader;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.xiaoniu.reader.fragment.BookFragment;
import com.xiaoniu.reader.fragment.MeFragment;

/**
 * @author xiaoniu
 */
public class MainActivity extends AppCompatActivity {
    /**book*/
    private static int TYPE_BOOK = 1;
    /**setting*/
    private static int TYPE_ME = 2;

    /**当前选中的fragment*/
    private int currentType = 0;
    private FragmentManager fragmentManager = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_book:
                    switchFragment(TYPE_BOOK);
                    return true;
                case R.id.navigation_me:
                    switchFragment(TYPE_ME);
                    return true;
                    default:
                        break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_book);
    }

    /**
     * 切换fragment
     * @param type
     */
    private void switchFragment(int type){
        if (type == currentType){
            return;
        }
        if (fragmentManager == null){
            fragmentManager = getFragmentManager();
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment home = fragmentManager.findFragmentByTag(BookFragment.TAG);
        Fragment setting = fragmentManager.findFragmentByTag(MeFragment.TAG);
        if (type == TYPE_BOOK){
            showHomeFragment(home, setting, transaction);
        }else if (type == TYPE_ME){
            showMeFragment(home, setting, transaction);
        }
    }

    /**
     * 显示home
     * @param home
     * @param setting
     * @param transaction
     */
    private void showHomeFragment(Fragment home, Fragment setting, FragmentTransaction transaction){
        if (home == null){
            transaction.add(R.id.fl_container, new BookFragment(), BookFragment.TAG);
        }else {
            transaction.show(home);
        }
        if (setting != null){
            transaction.hide(setting);
        }
        transaction.commit();
    }

    /**
     * 显示setting
     * @param home
     * @param me
     * @param transaction
     */
    private void showMeFragment(Fragment home, Fragment me, FragmentTransaction transaction){
        if (home != null){
            transaction.hide(home);
        }
        if (me == null){
            transaction.add(R.id.fl_container, new MeFragment(), MeFragment.TAG);
        }else {
            transaction.show(me);
        }
        transaction.commit();
    }

}
