package com.example.carwashing.fragments.news_fragment;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.carwashing.R;

import java.util.List;

/**
 * Created by Wakesy on 2016/5/29.
 */


public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener {

    private LayoutInflater mInflater;
    private List<NewsBean> datalist;
    private ImageLoader imageLoader;
    private static final String TAG = "NewsAdapter";
    private int mStart, mEnd;//可见项的起始位置
    public static String[] URLS;//用来存放所有图片的URL；
    private boolean mFirstIn;//是否是第一次加载，显示首页图片
    //存储所有的图片url
    public NewsAdapter(Context context, List<NewsBean> datalist, ListView news_listView01) {
        this.datalist = datalist;
        URLS = new String[datalist.size()];
        for (int i = 0; i < URLS.length; i++) {
            URLS[i] = datalist.get(i).getNewsIconUrl();
        }

        imageLoader = new ImageLoader(news_listView01);//防止每次都创建新的AsyncTsk,构造时就实例化一个ImageLoader对象
        mInflater = LayoutInflater.from(context);
        //实现listview滑动监听接口
        news_listView01.setOnScrollListener(this);
        mFirstIn=true;
    }

    @Override
    public int getCount() {
        Log.i(TAG, "---getCount: "+datalist.size());
        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.news_fragment_listview01_item, null);
            holder.bindView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_img.setImageResource(R.drawable.news_car);
        String item_url = datalist.get(position).getNewsIconUrl();

       if(item_url==null){
           holder.item_img.setImageResource(R.drawable.news_car);
       }
        else {
           //通过Hanlder加载图片
//        imageLoader .LoaderImageByHandler(holder.item_img, item_url);
//        通过AsyncTask加载图片,防止每次都创建新的AsyncTsk,构造时就实例化一个ImageLoader对象
           holder.item_img.setTag(item_url);//用tag将url传递过去防止反复加载
           imageLoader.LoaderImageByAsyncTask(holder.item_img, item_url);
       }
        holder.item_title.setText(datalist.get(position).getNewsTitle());
        holder.item_time.setText(datalist.get(position).getNewsTime());
        holder.item_source.setText(datalist.get(position).getNewsSource());
        Log.i(TAG, "----getView: ");

        return convertView;
    }



    /*
    * 从getView中触发下载任务转变为滑动停止时开始下载
    * */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (scrollState == SCROLL_STATE_IDLE) {//滑动停止时，开始加载
            imageLoader.loadPartImageview(mStart, mEnd);


        } else {//停止任务
            imageLoader.CancelAllTask();

        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        //可见项的起始位置
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
        if(mFirstIn&&visibleItemCount>0){
            imageLoader.loadPartImageview(mStart, mEnd);
            mFirstIn=false;
        }

    }


    public class ViewHolder {

        public ImageView item_img;
        public TextView item_title;
        public TextView item_time;
        private TextView item_source;

        //绑定控件的方法
        public void bindView(View view) {
            item_img = (ImageView) view.findViewById(R.id.item_iv);
            item_title = (TextView) view.findViewById(R.id.item_title);
            item_time = (TextView) view.findViewById(R.id.item_time);
            item_source = (TextView) view.findViewById(R.id.item_source);


        }


    }
}
