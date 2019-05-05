package concert.marquee.xiaoniu.com.viewpagertest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private MyViewPager viewPager;

    private TextView tv_valid, tv_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        findViewById(R.id.ll_available).setOnClickListener(this);
        findViewById(R.id.ll_history).setOnClickListener(this);
        viewPager = findViewById(R.id.viewpager);
        List<Fragment> list = new ArrayList<>();
        list.add(new AvailableFragment());
        list.add(new HistoryFragment());
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), list));
        viewPager.setCanScroll(false);

        tv_history = findViewById(R.id.tv_history);
        tv_valid = findViewById(R.id.tv_valid);
        tv_valid.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_available){
            tv_valid.setSelected(true);
            tv_history.setSelected(false);
            viewPager.setCurrentItem(0, true);
        }else if (id == R.id.ll_history){
            tv_valid.setSelected(false);
            tv_history.setSelected(true);
            viewPager.setCurrentItem(1, true);
        }
    }
}
