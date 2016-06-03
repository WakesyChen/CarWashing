package com.example.carwashing.fragments.news_fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.carwashing.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

/**
 * Created by Wakesy on 2016/5/29.
 */
public class ImageLoader {

    private ImageView mImageView;//得到通过方法传过来的ImageView和URL
    private String mUrl;
    private LruCache<String ,Bitmap>lruCache;//使用缓存,url做key,bitmap为值

    private Set<ImageAsyncTask> mTasks;//存放当前的异步任务

    private ListView news_listView01;
    public ImageLoader(ListView news_listView01) {


        this.news_listView01=news_listView01;
        mTasks=new HashSet<>();
        int maxMemory= (int) Runtime.getRuntime().maxMemory();//得到手机最大内存
        int cacheSize=maxMemory/4;//用于缓存的内存
        //创建内存
        lruCache=new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //每次存入缓存时调用，返回存入的对象大小
                return value.getByteCount();
            }
        };

    }
    //添加Bitmap到缓存
    public void addBitmapToCache(String url,Bitmap bitmap){

        if(getBitmapFromCache(url)==null){//如果cache中没有就添加到Cache中
            lruCache.put(url,bitmap);
        }



    }
    //得到缓存中的数据
    public Bitmap getBitmapFromCache(String url){//返回对应的Bitmap值

        return  lruCache.get(url);
    }



    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            if(mImageView.getTag().equals(mUrl))
            {

                Bitmap bitmap = (Bitmap) msg.obj;
                mImageView.setImageBitmap(bitmap);
            }
            }

    };

    //第一种方法，通过Handler实现更新UI加载图片
    public void LoaderImageByHandler(ImageView imageView, final String url) {
        mImageView = imageView;
        mUrl = url;


        new Thread(new Runnable() {
            @Override
            public void run() {

                Bitmap bitmap = getBitmapByUrl(url);
                //把bitmap装入message的Obj中发送消息给handler
                Message message = Message.obtain();
                message.obj = bitmap;
                handler.sendMessage(message);


            }
        }).start();


    }

    //方法二：通过AsyncTask加载图片

    public void LoaderImageByAsyncTask(ImageView imageView ,String url){


        Bitmap bitmap=getBitmapFromCache(url);
        if(bitmap==null){
//            默认全部加载时
//            new ImageAsyncTask(url).executeOnExecutor(Executors.newCachedThreadPool(), url);
//            滑动时停止任务，设置默认的图片，静止时加载
            imageView.setImageResource(R.drawable.news_car);


        }

       else{
            imageView.setImageBitmap(bitmap);
        }





    }

    //加载部分可见的图片
    public void loadPartImageview(int mStart, int mEnd) {
            String url;
        for (int i = mStart; i < mEnd; i++) {
            url=NewsAdapter.URLS[i];//获取到对应可见项的URL

            Bitmap bitmap=getBitmapFromCache(url);
            if(bitmap==null){



                ImageAsyncTask task= new ImageAsyncTask(url);
                task.executeOnExecutor(Executors.newCachedThreadPool(),url );
                mTasks.add(task);

            }

            else{
            //通过Tag 值为url找到ListView中对应的ImageView
                ImageView imageView= (ImageView) news_listView01.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);
            }

        }





    }



    public void CancelAllTask() {
        if(mTasks!=null){
            for (ImageAsyncTask task :
                    mTasks) {
                task.cancel(false);

            }


        }


    }

    public  class ImageAsyncTask  extends  AsyncTask<String ,Void,Bitmap>{

//       private ImageView imageView_as;
       private String url_as;

       public ImageAsyncTask(String url) {
//           imageView_as = imageView;
           url_as= url;
       }

       @Override
       protected Bitmap doInBackground(String... params) {

        Bitmap bitmap=getBitmapByUrl(params[0]);
           if(bitmap!=null){
               //避免下次使用还要下载,下载后就存入缓存,
               addBitmapToCache(params[0],bitmap);
           }

           return bitmap;
       }

       @Override
       protected void onPostExecute(Bitmap bitmap) {
           super.onPostExecute(bitmap);
/*
           if(mImageView.getTag().equals(url_as)){//防止重复加载
               mImageView.setImageBitmap(bitmap);
           }*/
           ImageView imageView= (ImageView) news_listView01.findViewWithTag(url_as);
           if(imageView!=null&&bitmap!=null){
                imageView.setImageBitmap(bitmap);
           }

       }
   }

    public Bitmap getBitmapByUrl(String url) {
        Bitmap bitmap = null;
        try {
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            InputStream is = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);

            connection.disconnect();
            is.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return bitmap;
    }

}
