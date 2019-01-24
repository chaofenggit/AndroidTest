package com.xiaoniu.fragmentactivityresult;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class NextActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
    }

    public void dealSuccess(View view){
        setResult(RESULT_OK);
        finish();
    }
}
