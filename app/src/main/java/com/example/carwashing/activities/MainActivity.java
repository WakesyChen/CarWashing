package com.example.carwashing.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.carwashing.fragments.aroundStore_fragment.AroundstoreFragment_2;
import com.example.carwashing.fragments.aroundStore_fragment.ReFlashListView;
import com.example.carwashing.fragments.home_fragmet.Home_fragment_1;
import com.example.carwashing.fragments.me_fragment.Me_fragment_4;
import com.example.carwashing.fragments.news_fragment.News_fragment_3;
import com.example.carwashing.R;

public class MainActivity extends FragmentActivity {

    private RadioGroup radioGroup;
    private RadioButton rbtn_1,rbtn_2,rbtn_3,rbtn_4;

    private long exitTime;

    //主界面ViewPager代码
    private List<Fragment>datafrags;//准备ViewPager数据源
    private MyFragmentPagerAdapter fragmentPagerAdapter;//ViewPager适配器
    private ViewPager main_viewpager;//主界面的ViewPager控件
    private ReFlashListView lv_aroundstore;//附近商店的ListView

    private ListView lv_news1;//第一条资讯的ListView

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initView();

        //判断是否有网络,没有就显示处理无网的布局
        if (!isNetworkConnected(MainActivity.this)){
            Toast.makeText(this,"世界上最遥远的距离就是没有网",Toast.LENGTH_SHORT).show();

        }

/*
* 重要代码，使主界面的fragment预加载数目为3，切换到相隔三个fragment时也不会被销毁
*
* */
        main_viewpager.setOffscreenPageLimit(3);


        setRadioGroup();//设置底部radiobutton

        //Banner相关代码

        initData();



    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    //设置底部radiobutton
    private void setRadioGroup() {

        //  给RadioGroup添加事件切换页面,绑定对应的fragment
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_1:
                        main_viewpager.setCurrentItem(0);//切换到对应页面
                        break;
                    case R.id.rbtn_2:
                        main_viewpager.setCurrentItem(1);
                        break;
                    case R.id.rbtn_3:
                        main_viewpager.setCurrentItem(2);
                        break;
                    case R.id.rbtn_4:
                        main_viewpager.setCurrentItem(3);
                        break;
                }

            }
        });
    }





    /**
     * 初始化数据
     */
    private void initData() {


        //准备main_Viewpager的数据
        datafrags=new ArrayList<Fragment>();
        Home_fragment_1 fragment0=new Home_fragment_1(MainActivity.this);
        AroundstoreFragment_2 fragment1=new AroundstoreFragment_2(MainActivity.this);
        News_fragment_3 fragment2=new News_fragment_3(MainActivity.this);
        Me_fragment_4 fragment3=new Me_fragment_4(MainActivity.this);
        datafrags.add(fragment0);
        datafrags.add(fragment1);
        datafrags.add(fragment2);
        datafrags.add(fragment3);
        fragmentPagerAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),datafrags) {
            @Override
            public Fragment getItem(int position) {
                return datafrags.get(position);
            }

            @Override
            public int getCount() {
                return datafrags.size();
            }
        };
        main_viewpager.setAdapter(fragmentPagerAdapter);



        //给main_ViewPager设置切换事件,绑定对应的RadioButton
        main_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position){

                    case 0:
                        radioGroup.check(R.id.rbtn_1);
                        break;
                    case 1:
                        radioGroup.check(R.id.rbtn_2);
                        break;
                    case 2:
                        radioGroup.check(R.id.rbtn_3);
                        break;
                    case 3:
                        radioGroup.check(R.id.rbtn_4);
                        break;
                }
            }



            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*处理没有网时的操作，隐藏ListView,显示提示内容
        * */


    }



    /**
     * 初始化View操作
     */
    private void initView() {
        radioGroup= (RadioGroup) findViewById(R.id.radiogroup);
        rbtn_1= (RadioButton) findViewById(R.id.rbtn_1);
        rbtn_2= (RadioButton) findViewById(R.id.rbtn_2);
        rbtn_3= (RadioButton) findViewById(R.id.rbtn_3);
        rbtn_4= (RadioButton) findViewById(R.id.rbtn_4);
        lv_news1= (ListView) findViewById(R.id.news_listView01);
        main_viewpager= (ViewPager) findViewById(R.id.main_viewpager);

    }


    /*
    * 设置手机物理键二次确认退出
    * */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(MainActivity.this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}



