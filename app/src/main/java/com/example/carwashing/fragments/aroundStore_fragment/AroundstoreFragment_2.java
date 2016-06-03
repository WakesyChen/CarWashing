package com.example.carwashing.fragments.aroundStore_fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.carwashing.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wakesy on 2016/5/20.
 */
public class AroundstoreFragment_2 extends Fragment implements ReFlashListView.IReflashListener {
    Context context;
    private static final String TAG = "AroundstoreFragment_2";
    private List<List_ItemViews> datalist;
    //准备门店图片
    private int ItemImgs[]={R.mipmap.car_service_shop01,R.mipmap.car_service_shop02,R.mipmap.car_service_shop03
            ,R.mipmap.car_service_shop04, R.mipmap.car_service_shop05,R.mipmap.car_service_shop06,
            R.mipmap.car_service_shop07,R.mipmap.car_service_shop08};
    private ReFlashListView listview_store;
    private StoreAdapter storeAdapter;
    private LinearLayout internet_warning;//附近商店没网提示信息


    public AroundstoreFragment_2(Context context) {
        this.context=context;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.aroundstore_fragment2, container, false);
        Log.i(TAG, "onCreateView: ");
        initViews(view);
        initData();
         showList(datalist);//显示ListView
        listview_store.setInterface(this);
       /* //设置适配器
        storeAdapter=new StoreAdapter(context,datalist);
//        启动适配器
        listview_store.setAdapter(storeAdapter);*/

        //判断是否有网络,没有就显示处理无网的布局
        if (!isNetworkConnected(context)){
            listview_store.setVisibility(View.GONE);
            internet_warning.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "------onCreate: ");

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "------onDestroy: ");
    }
//    判断是否有网
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
        //待处理的代码
    //显示ListView
    private void showList(List<List_ItemViews> datalist) {
        if (storeAdapter== null) {
            listview_store.setInterface(this);
            //设置适配器
            storeAdapter=new StoreAdapter(context,datalist);
//        启动适配器
            listview_store.setAdapter(storeAdapter);
        } else {//有数据时更新


           storeAdapter.onDateChange(datalist);
        }
    }
    //初始化控件
    private void initViews(View view) {
        listview_store= (ReFlashListView) view.findViewById(R.id.listview_store);
        internet_warning= (LinearLayout)view. findViewById(R.id.inter_warning);



    }
        //初始化数据
    private void initData() {

        datalist=new ArrayList<List_ItemViews>();
        //给数据源增添数据
        for (int i = 0; i <ItemImgs.length ; i++) {
           /* List_ItemViews list_itemviews=new List_ItemViews("康桥豪大大汽修连锁店"+(i+1)+"号店",ItemImgs[i],
                            "武汉市洪山区狮子山街道"+i+"号湖北工业大学",(float)(5-i*0.2),
                                (float)(Math.round((5-i*0.2)*100)/100.0)+"分",(10+i)+"条评论",
                            ((i+1)*2.6+10)+"km","普通洗车-5座","¥"+(10+i));*/
            List_ItemViews list_itemviews=new List_ItemViews();
            list_itemviews.setItemStore("康桥豪大大汽修连锁店"+(i+1)+"号店");
            list_itemviews.setItemImg(ItemImgs[i]);
            list_itemviews.setItemAddress("武汉市洪山区狮子山街道" + i + "号湖北工业大学");
            list_itemviews.setItemStars((float) (5 - i * 0.2));
            list_itemviews.setItemScore((float) (Math.round((5 - i * 0.2) * 100) / 100.0) + "分");
            list_itemviews.setItemComments((10 + i) + "条评论");
            list_itemviews.setItemDistance(((i + 1) * 2.6 + 10) + "km");
            list_itemviews.setItemType("普通洗车-5座");
            list_itemviews.setItemPrice("¥"+(10+i));
            datalist.add(list_itemviews);

        }

    }
        //添加数据进行刷新
        private void initReFlashData() {
            //给数据源增添数据
            for (int i = 0; i <2 ; i++) {

                List_ItemViews list_itemviews=new List_ItemViews();
                list_itemviews.setItemStore("康斯坦丁马卡利器"+(i+1)+"号店");
                list_itemviews.setItemImg(ItemImgs[i]);
                list_itemviews.setItemAddress("东土大唐去往西天" + i + "号");
                list_itemviews.setItemStars((float) (5 - i * 0.2));
                list_itemviews.setItemScore((float) (Math.round((5 - i * 0.2) * 100) / 100.0) + "分");
                list_itemviews.setItemComments((10 + i) + "条评论");
                list_itemviews.setItemDistance(((i + 1) * 2.6 + 10) + "km");
                list_itemviews.setItemType("普通洗车-8座");
                list_itemviews.setItemPrice("¥"+(20+i));
                datalist.add(list_itemviews);

            }

        }





    //实现ReFlashListView的接口,延迟两秒完成刷新

    public void onReflash() {
        // TODO Auto-generated method stub
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //获取最新数据
                initReFlashData();
                //通知界面显示
              showList(datalist);
                //通知listview 刷新数据完毕；
              listview_store.reflashComplete();
            }
        }, 2000);

    }

}
