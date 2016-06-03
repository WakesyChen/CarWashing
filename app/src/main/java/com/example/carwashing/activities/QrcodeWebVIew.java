package com.example.carwashing.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.carwashing.R;

/**
 * Created by Wakesy on 2016/6/2.
 */
public class QrcodeWebVIew extends Activity {

    private WebView webview;
    private ProgressDialog dialog;
    private String url;
    private TextView tv_qrc;
    private static final String TAG = "QrcodeWebVIew";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_webview);
        webview= (WebView) findViewById(R.id.list_item_webView);
        tv_qrc= (TextView) findViewById(R.id.tv_qrc);
        url="http://www.baidu.com";
        //得到二维码扫描的结果

        String result=getIntent().getStringExtra("result");
        Log.i(TAG, "onCreate: "+result);
        checkResult(result);//判断是否是链接
//覆盖WebView通过第三方浏览器访问网页的行为，使得网页在WebView中打开
        webview.loadUrl(url);
        webview.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制网页在webview中打开,false则通过第三方打开
                //return super.shouldOverrideUrlLoading(view, url);
                view.loadUrl(url);
                return true;
            }

        });


//                三、在webView中使用Javascript

//        在WebView中启用JavaScript，通过WebView带有的WebSttings,；

//启用支持JavaScript

        WebSettings websetting=webview.getSettings();

        websetting.setJavaScriptEnabled(true);
        //WebView加载页面优先使用缓存加载

        websetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);


//        五、添加页面加载进度条
//显示webview跳转页面的进度条
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // newProgress 1-100的整数，表示进度
                if (newProgress == 100) {
                    closeDialog(newProgress);
                } else {
                    openDialog(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            private void openDialog(int newProgress) {
                if (dialog == null) {
                    dialog = new ProgressDialog(QrcodeWebVIew.this);
                    dialog.setTitle("玩命加载中.....");
                    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    dialog.setProgress(newProgress);
                    dialog.show();
                } else {
                    dialog.setProgress(newProgress);

                }
                // TODO Auto-generated method stub

            }

            private void closeDialog(int newProgress) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

            }
        });




    }
//判断是否是链接
    private void checkResult(String result) {

        if(result.startsWith("http://")){
            url=result;
        }
        else{
            webview.setVisibility(View.GONE);
            tv_qrc.setVisibility(View.VISIBLE);
            tv_qrc.setText(result);
        }

    }


    //        四、改写物理按键一次返回就退出应用的逻辑
//在protected void onCreate(Bundle savedInstanceState) {}外定义
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode== KeyEvent.KEYCODE_BACK){
            if(webview.canGoBack()){//判断前面是否能返回
                webview.goBack();

                return true;//直接返回
            }
            else{
               finish();//退出当前Activity
            }

        }
        return super.onKeyDown(keyCode, event);
    }


}
