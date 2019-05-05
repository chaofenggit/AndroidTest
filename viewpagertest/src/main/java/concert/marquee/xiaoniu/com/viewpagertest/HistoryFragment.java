package concert.marquee.xiaoniu.com.viewpagertest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * @author xiaoniu
 * @date 2019/4/22.
 */

public class HistoryFragment extends Fragment {

    private static final String TAG = "HistoryFragment";

    private boolean isNeedLoadData = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_history, container, false);
        Log.d(TAG, TAG + "onCreateView");
        isNeedLoadData = true;
        return contentView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, TAG + "isVisibleToUser = " + isVisibleToUser);
        if (isVisibleToUser && isNeedLoadData){
            //开始加载数据
            Toast.makeText(getActivity(), "历史券开始加载",Toast.LENGTH_SHORT).show();
            Log.d(TAG,"历史券开始加载");
            isNeedLoadData = false;
        }
    }
}
