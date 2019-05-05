package com.xiaoniu.share.element.share;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.xiaoniu.share.element.drager.DragCloserHelper;
import com.xiaoniu.share.element.drager.R;

public class TargetActivity extends AppCompatActivity {
    private final String TAG = "TargetActivity";

    private ConstraintLayout cl_main;
    private ImageView imageView;

    private DragCloserHelper dragCloserHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        cl_main = findViewById(R.id.cl_main);
        imageView = findViewById(R.id.iv_target);

        Intent intent = getIntent();
        if (intent != null){
            int icon_id = intent.getIntExtra("icon_id", -1);
            if (icon_id > 0){
                imageView.setImageResource(icon_id);
            }
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dragCloserHelper = new DragCloserHelper(this);
        dragCloserHelper.setShareElementMode(true);
        dragCloserHelper.setDragCloseViews(cl_main, imageView);
        dragCloserHelper.setDragCloseListener(new DragCloserHelper.DragCloseListener() {
            @Override
            public boolean intercept() {
                return false;
            }

            @Override
            public void startDrag() {
                Log.d(TAG, "dragStart");
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel");
            }

            @Override
            public void onClose() {
                Log.d(TAG, "dragClose");
                onBackPressed();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (dragCloserHelper.handleTouchEvent(ev)){
            return true;
        }else {
            return super.dispatchTouchEvent(ev);
        }
    }
}
