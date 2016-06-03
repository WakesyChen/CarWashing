package com.example.carwashing.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.carwashing.R;
import com.facebook.shimmer.ShimmerFrameLayout;

/**
 * Created by Wakesy on 2016/5/24.
 */
public class LaunchActivity extends Activity {


    private ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_activity);
        shimmerFrameLayout= (ShimmerFrameLayout) findViewById(R.id.ShimmerFrameLayout);
        shimmerFrameLayout.startShimmerAnimation();//开启文字颜色滚动的动画

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(LaunchActivity.this,MainActivity.class);
                startActivity(intent);
                finish();//把当前的启动页面结束，清出Activity任务栈

            }
        },2000);
    }
}
