package com.example.carwashing.fragments.aroundStore_fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.carwashing.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Wakesy on 2016/5/24.
 */

/*
* 自定义一个可以实现下拉刷新的ListView，Item前面要添加一个Header布局
* */
public class ReFlashListView extends ListView implements AbsListView.OnScrollListener {

    View header;// 顶部布局文件；
    int HeaderHeight;// 顶部布局文件的高度；
    int firstVisibleItem;// 当前第一个可见的item的位置；
    int scrollstate;//ListView当前滚动状态
    boolean isRemark;//当前是在ListView最顶端按下的
    int startY;//按下时Y的值
    int state ;//当前的状态
    final int NONE=0;// 正常状态
    final int PULL=1;//提示下拉状态
    final int RELEASE=2;//提示释放状态
    final int REFLASHING=3;//提示刷新状态

    private static final String TAG = "ReFlashListView";
    private IReflashListener iReflashListener;

    public ReFlashListView(Context context) {
        super(context);
        initView();

    }


    public ReFlashListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ReFlashListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    /*初始化ListView，添加Header头布局*/
    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        header = inflater.inflate(R.layout.aroundstore_listview_header, null);
        measureView(header);//测量header
       HeaderHeight= header.getMeasuredHeight();
        Log.i(TAG, "initView: "+HeaderHeight);
        topPadding(-HeaderHeight); /*设置header的上边距-----隐藏*/
        this.addHeaderView(header);//添加Header头布局
        this.setOnScrollListener(this);//设置界面滑动监听事件

    }
    /**
     * 通知父布局，占用的宽，高；
     *
     * @param view
     */
    private void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHeight = p.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight,
                    MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
    }


    /*设置header的上边距-----隐藏*/
    public void topPadding(int topPadding) {
        header.setPadding(header.getPaddingLeft(), topPadding, header.getPaddingRight(), header.getPaddingBottom());


    }


//   实现 ListView 的OnScrollListener滚动监听接口的方法
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
            this.scrollstate=scrollState;//判断当前的滚动状态

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem=firstVisibleItem;//当前第一个View的值，header=0，其他Item为1,2,3,4.。。

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(firstVisibleItem==0){
                    isRemark=true;
                    startY= (int) ev.getY();
                }


                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if (state == RELEASE) {
                    state = REFLASHING;
                    // 加载最新数据；
                    reflashViewByState();
                    iReflashListener.onReflash();
                } else if (state == PULL) {
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }


                break;



        }

        return super.onTouchEvent(ev);
    }

    private void onMove(MotionEvent ev) {
        if(!isRemark){
            return ;
        }
        else{
            int tempY= (int) ev.getY();
            int space=tempY-startY;//滑动的距离
            int topPadding=space+HeaderHeight;//给顶部header设置高度

            //判断下拉状态
            switch(state){
                case NONE:
                    if(space>0){
                        state=PULL;
                        reflashViewByState();//根据当前的状态改变ListView
                    }

                    break;
                case PULL:
                    topPadding(topPadding);//动态改变ListView顶部高度（初始化时为隐藏）
                    //判断下拉距离为多少转化为RELEASE状态，并且此时ListView也在滚动
                    if(space>HeaderHeight+10&&scrollstate==SCROLL_STATE_TOUCH_SCROLL){
                        state=RELEASE;
                        reflashViewByState();
                    }
                    break;
                case RELEASE:
                    topPadding(topPadding);//动态改变ListView顶部高度
                    topPadding(topPadding);
                    if (space < HeaderHeight + 10) {
                        state = PULL;
                        reflashViewByState();
                    } else if (space <= 0) {
                        state = NONE;
                        isRemark = false;
                        reflashViewByState();
                    }
                    break;
                case REFLASHING:
                    break;



            }

        }


    }
        //根据当前的状态改变ListView
    private void reflashViewByState() {
        TextView tip = (TextView) header.findViewById(R.id.tip);
        ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
        ProgressBar progress = (ProgressBar) header.findViewById(R.id.progress);
        RotateAnimation anim = new RotateAnimation(0, 180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(500);
        anim.setFillAfter(true);
        RotateAnimation anim1 = new RotateAnimation(180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        anim1.setDuration(500);
        anim1.setFillAfter(true);
        switch (state) {
            case NONE:
                arrow.clearAnimation();
                topPadding(-HeaderHeight);
                break;

            case PULL:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("下拉可以刷新！");
                arrow.clearAnimation();
                arrow.setAnimation(anim1);
                break;
            case RELEASE:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("松开可以刷新！");
                arrow.clearAnimation();
                arrow.setAnimation(anim);
                break;
            case REFLASHING:
                topPadding(50);
                arrow.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                tip.setText("正在刷新...");
                arrow.clearAnimation();
                break;
        }
    }
    /**
     * 获取完数据；
     */
    public void reflashComplete() {
        state = NONE;
        isRemark = false;
        reflashViewByState();
        TextView lastupdatetime = (TextView) header
                .findViewById(R.id.lastupdate_time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String time = format.format(date);
        lastupdatetime.setText(time);
    }

    public void setInterface(IReflashListener iReflashListener){
        this.iReflashListener = iReflashListener;
    }
    /**
     * 刷新数据接口
     * @author Administrator
     */
    public interface IReflashListener{
        public void onReflash();
    }

}
