package com.xiaoniu.share.element.share;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xiaoniu.share.element.drager.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_bank,iv_voucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_bank = findViewById(R.id.iv_bank);
        iv_voucher = findViewById(R.id.iv_voucher);
        iv_bank.setOnClickListener(this);
        iv_voucher.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.iv_bank){
            otherActivity(R.mipmap.icon_bank, iv_bank);
        }else if (id == R.id.iv_voucher){
            otherActivity(R.mipmap.voucher_icon, iv_voucher);
        }
    }

    @TargetApi(16)
    private void  otherActivity(int icon_id, ImageView imageView){
        Intent intent = new Intent(this, TargetActivity.class);
        Bundle bundle = ActivityOptionsCompat.
                makeSceneTransitionAnimation(MainActivity.this, imageView, getString(R.string.transition_name))
                .toBundle();
        intent.putExtra("icon_id", icon_id);
        startActivity(intent, bundle);
    }
}
