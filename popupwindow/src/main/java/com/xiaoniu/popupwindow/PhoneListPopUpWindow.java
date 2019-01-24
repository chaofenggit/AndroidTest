package com.xiaoniu.popupwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoniu
 * @date 2019/1/15.
 */

public class PhoneListPopUpWindow extends PopupWindow {

    private static final String TAG = "PhoneListPopUpWindow";

    private View contentView;
    private ListView phoneListView;
    private ArrayList<String> list = new ArrayList<>();
    private PhoneNumberListAdapter phoneNumberListAdapter;
    private Context context;
    private AdapterView.OnItemClickListener onItemClickListener = null;

    public PhoneListPopUpWindow(Context context) {
        this.context = context;
        contentView = LayoutInflater.from(context).inflate(R.layout.ahdi_layout_phone_popup, null, false);

        setContentView(contentView);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(false);
        setTouchable(true);
        initView();
    }

    private void initView(){
        phoneListView = contentView.findViewById(R.id.phone_list);
        phoneNumberListAdapter = new PhoneNumberListAdapter(context, list);
        phoneListView.setAdapter(phoneNumberListAdapter);
        phoneListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(parent, view, position, id);
                }
            }
        });
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 更新数据
     * @param data
     */
    public void setData(List<String> data){
        list.clear();
        for (int i = 0; i <data.size(); i++) {
            list.add(data.get(i));
        }
        phoneNumberListAdapter.notifyDataSetChanged();
    }

    public void showAtLocation(View parent){
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        setWidth(parent.getWidth());
        setHeight(getPopupWindowHeight(parent, location));
        showAtLocation(parent, Gravity.NO_GRAVITY, location[0], location[1] + parent.getHeight());
    }

    /**
     * 计算popup的显示高度
     * @param parent
     * @param location
     * @return
     */
    public int getPopupWindowHeight(View parent, int[] location) {
        try{
            //屏幕高度减去控件的位置，剩下的就是popup可以展示的最大高度
            int deviceHeight = context.getResources().getDisplayMetrics().heightPixels;
            int availableHeight = deviceHeight - location[1] - parent.getHeight();
            //计算listview item的高度（UI说最大展示5个item）
            View listItem = phoneNumberListAdapter.getView(0, null, phoneListView);
            listItem.measure(0, 0);
            int measuredHeight = listItem.getMeasuredHeight();
            Log.d(TAG, "可用高度 = " + availableHeight + " , itemHeight = " + measuredHeight);
            //判断列表的代销是否大于5（UI说最大展示5个item）
            int size = list.size();
            if (size > 5){
                size = 5;
            }
            if (availableHeight >= measuredHeight * size){
                return measuredHeight * size;
            }else {
                return availableHeight;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "计算popup的显示高度时报错误：" + e.getMessage());
        }
        return 0;
    }
}
