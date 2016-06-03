package com.example.carwashing.fragments.home_fragmet;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.carwashing.R;
import com.example.carwashing.activities.QrcodeWebVIew;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//导入banner

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * Created by Wakesy on 2016/5/20.
 */
public class Home_fragment_1 extends Fragment {
    private int[] imgs={R.drawable.car_1,R.drawable.car_repair,R.drawable.protection_1,R.drawable.query
            ,R.drawable.driver,R.drawable.secuity};
    private  String[] infor={"上门洗车","紧急支援","汽车保养","违章查询","司机交流","车险推荐"};
    private Context context;
    private List<Map<String ,Object>> datalist;
    private SimpleAdapter adapter;
    private GridView gridView;
    private View view;
    private static final String TAG = "Home_fragment_1";
    //banner所需控件
    // 声明banner控件
    private ViewPager mViewPager;
    private List<ImageView> mlist;
    private TextView mTextView;
    private LinearLayout mLinearLayout;
    private Button btn_qrcode;//二维码
    // 广告图素材
    private int[] bannerImages = { R.drawable.wash_car01, R.drawable.wash_car02, R.drawable.wash_car03, R.drawable.wash_car04 };
    // 广告语
    private String[] bannerTexts = { "","", "", "" };

    // ViewPager适配器与监听器
    private BannerAdapter mAdapter;
    private BannerListener bannerListener;

    // 圆圈标志位
    private int pointIndex = 0;
    // 线程标志
    private boolean isStop = false;

    //启动图片轮播异步任务
    private BannerAsynctack bannerAsynctack;

    public Home_fragment_1(){

    }
    public Home_fragment_1(Context context) {
        this.context=context;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.home_fragment1,container,false);
        setGridView(view);
        //banner相关方法
        initView();
        initData();
        initAction();

//        非UI中不能更改UI---------------------------------------（待升级）
        /*
        // 开启新线程，2秒一次更新Banner
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (!isStop) {
                    SystemClock.sleep(2000);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }).start();*/
        return view;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Bug出现点，异步任务必须在Oncreate执行
        //开启异步任务更改UI，实现图片轮播
        bannerAsynctack=new BannerAsynctack();
        bannerAsynctack.execute();
    }

    //Banner相关方法
    /**
     * 初始化事件
     */
    private void initAction() {
        bannerListener = new BannerListener();
        mViewPager.setOnPageChangeListener(bannerListener);
        //取中间数来作为起始位置
        int index = (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2 % mlist.size());
        //用来出发监听器
        mViewPager.setCurrentItem(index);
        mLinearLayout.getChildAt(pointIndex).setEnabled(true);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mlist = new ArrayList<ImageView>();
        View view;
        LayoutParams params;
        for (int i = 0; i < bannerImages.length; i++) {
            // 设置广告图
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            imageView.setBackgroundResource(bannerImages[i]);
            mlist.add(imageView);
            // 设置圆圈点
            view = new View(context);
            params = new LayoutParams(5, 5);
            params.leftMargin = 10;
            view.setBackgroundResource(R.drawable.circle_point_background);
            view.setLayoutParams(params);
            view.setEnabled(false);

            mLinearLayout.addView(view);
        }
        mAdapter = new BannerAdapter(mlist);
        mViewPager.setAdapter(mAdapter);
    }



    /**
     * 初始化View操作
     */
    private void initView() {
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mTextView = (TextView) view.findViewById(R.id.tv_bannertext);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.points);

//        扫描二维码
        btn_qrcode= (Button) view.findViewById(R.id.btn_qrcode);
        btn_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(context, CaptureActivity.class), 0);

            }
        });

    }

    //扫描之后的回调方法，跳转到链接
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==-1)  {
        Bundle bundle=data.getExtras();
            String result=bundle.getString("result");
            Log.i(TAG, "onActivityResult: "+result);
            Intent intent=new Intent(context,QrcodeWebVIew.class);
                intent.putExtra("result",result);
            startActivity(intent);
        }
    }

    //实现VierPager监听器接口
    class BannerListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            int newPosition = position % bannerImages.length;
//            设置轮播图片的Text
//            mTextView.setText(bannerTexts[newPosition]+(newPosition+1));
            mLinearLayout.getChildAt(newPosition).setEnabled(true);
            mLinearLayout.getChildAt(pointIndex).setEnabled(false);
            // 更新标志位
            pointIndex = newPosition;

        }

    }
    //通过异步任务修改UI
    public class BannerAsynctack extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            while (!isStop) {
                SystemClock.sleep(2000);
                //获取到当前的Activity

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    }
                });
            }


            return null;
        }
    }


    public  void onDestroy() {
        // 关闭定时器
        isStop = true;
        Log.i("destroy", "fragment1");
        super.onDestroy();
        bannerAsynctack.cancel(true);//退出时取消异步任务
    }



    //设置GridView
    private void setGridView(View view) {
        gridView= (GridView) view.findViewById(R.id.gridview);
//        准备数据源
        datalist=new ArrayList<Map<String,Object>>();//初始化
        for (int i = 0; i <imgs.length ; i++) {
            Map<String,Object>map=new HashMap<String ,Object>();
            map.put("img",imgs[i]);
            map.put("infor",infor[i]);
            datalist.add(map);
        }
        //设置适配器
        adapter=new SimpleAdapter(context,datalist,R.layout.home_fragment1_gridview_item,new String[]{"img","infor"},
                new int[]{R.id.img_item,R.id.tv_item});

        gridView.setAdapter(adapter);

    }

}
