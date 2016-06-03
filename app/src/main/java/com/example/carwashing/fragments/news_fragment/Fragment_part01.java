package com.example.carwashing.fragments.news_fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.carwashing.R;
import com.example.carwashing.activities.ItemWebVIew;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by Wakesy on 2016/5/26.
 */
public class Fragment_part01 extends Fragment {

    private static final String TAG = "Fragment_part01";
    private ImageLoader mImageLoader;
    private Context context;
    private List<NewsBean>datalist;
    private String httpUrl = "http://apis.baidu.com/showapi_open_bus/channel_news/search_news";//汽车新闻api接口
   //所有参数,汽车频道
    private String httpArg = "channelId=5572a109b3cdc86cf39001e5&" +
            "channelName=%E5%9B%BD%E5%86%85%E6%9C%80%E6%96%B0&title=%E4%B8%8A%E5%B8%82&" +
            "page=1&needContent=0&needHtml=1";
    private String apikey = "6b956c46fa58a90ab9bdb8c55c1b70f1";//apikey值
    private String JsonString;

    private ListView news_ListView01;
    private LinearLayout internet_warning1;//第一条资讯提示信息
    private NewsAsyncTask newsAsyncTask;

    public Fragment_part01(Context context) {
        this.context = context;
    }

    //得到Json数据
    private String getJsonString(String httpUrl1) {
        BufferedReader br = null;
        String result = null;
        StringBuffer sb = new StringBuffer();
        String line = "";


        try {
            URL url = new URL(httpUrl1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");//设置请求方式
            // 填入apikey到HTTP header
            conn.setRequestProperty("apikey", apikey);
            conn.connect();
            InputStream is = conn.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "utf-8"));

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");

            }
            result = sb.toString();
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news_fragment3_part01, container, false);

        news_ListView01 = (ListView) view.findViewById(R.id.news_listView01);
        internet_warning1= (LinearLayout) view.findViewById(R.id.inter_warning1);
        datalist=new ArrayList<>();
        //设置ListView的点击事件，跳转到对应的链接
        news_ListView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url=datalist.get(position).getNewsLink();
                Intent intent=new Intent(context, ItemWebVIew.class);
                intent.putExtra("url",url);
                startActivity(intent);

            }
        });
        httpUrl = httpUrl + "?" + httpArg;
    /*
    * 天坑！！！！！！！！！
    * 线程多的话会出现阻塞，貌似只能有4、5个线程，解决方法是不能调用excute方法，
    * 调用task.executeOnExecutor(Executors.newCachedThreadPool());
    *
    * */
        //执行异步任务前判断当前网络是否可用

        if(isNetworkConnected(context)){
            newsAsyncTask = new NewsAsyncTask();
            newsAsyncTask.executeOnExecutor(Executors.newCachedThreadPool(), httpUrl);
        }else{
            internet_warning1.setVisibility(View.VISIBLE);
        }

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(newsAsyncTask!=null)
        newsAsyncTask.cancel(true);//退出时停止异步任务

    }

    //访问网络获取Json数据，启动异步任务
    class NewsAsyncTask extends AsyncTask<String, Void, List<NewsBean>> {

        public NewsAsyncTask() {

        }

        @Override
        protected List<NewsBean> doInBackground(final String... params) {
            datalist=getJsonData(params[0]);
            Log.i(TAG, "---doInBackground: "+datalist.size());

            return datalist;

        }


        //得到通过解析Json返回的List<NewsBean>
        @Override
        protected void onPostExecute(List<NewsBean> newsBeans) {
            NewsAdapter newsAdapter = new NewsAdapter(context, newsBeans, news_ListView01);

            news_ListView01.setAdapter(newsAdapter);


        }
    }


    //通过URL解析Json到List<NewsBean>中
    public List<NewsBean> getJsonData(String httpUrl) {
        List<NewsBean> datalist = new ArrayList<>();
        try {
            JsonString = getJsonString(httpUrl);//得到Json数据
            Log.i(TAG, "getJsonData: " + JsonString);

            JSONObject jsonObject = new JSONObject(JsonString);
            JSONObject jsonObject1 = jsonObject.getJSONObject("showapi_res_body");
            JSONObject jsonObject2=jsonObject1.getJSONObject("pagebean");
            JSONArray jsonArray = jsonObject2.getJSONArray("contentlist");
            NewsBean newsBean;
            Log.i(TAG, "---jsonArray.length()"+jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonItem = jsonArray.getJSONObject(i);//得到当前Json对象
                newsBean = new NewsBean();
                newsBean.setNewsTitle(jsonItem.getString("title"));//得到标题
                newsBean.setNewsTime(jsonItem.getString("pubDate"));//得到时间
                newsBean.setNewsSource(jsonItem.getString("source"));//得到来源
                newsBean.setNewsLink(jsonItem.getString("link"));//得到链接
                JSONArray ImgArra = jsonItem.getJSONArray("imageurls");//得到图片数组实体，图片在内部实体中
                if (ImgArra.length() > 0) {
                    JSONObject ImgJson = ImgArra.getJSONObject(0);
                    newsBean.setNewsIconUrl(ImgJson.getString("url"));//得到图片
                }
                else{
                    newsBean.setNewsIconUrl("");//没有图片就设为空
                }

                datalist.add(newsBean);
                Log.i(TAG, "getJsonData: datalist"+datalist.size());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "datalist.size"+datalist.size());
        return datalist;
    }


}
