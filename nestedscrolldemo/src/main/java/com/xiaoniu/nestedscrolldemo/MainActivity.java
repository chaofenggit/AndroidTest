package com.xiaoniu.nestedscrolldemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }


    private void test(){
        Bundle bundle = new Bundle();
        bundle.putInt("key", 12);

        Toast.makeText(getApplicationContext(), bundle.getString("key"), Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        UMengUtil.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UMengUtil.onPause(this);
    }
}
