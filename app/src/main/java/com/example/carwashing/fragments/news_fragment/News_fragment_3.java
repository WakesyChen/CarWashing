package com.example.carwashing.fragments.news_fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carwashing.R;

/**
 * Created by Wakesy on 2016/5/20.
 */
public class News_fragment_3 extends Fragment implements View.OnClickListener{


    private Context context;
    public News_fragment_3(Context context) {
        this.context=context;
    }
    private View view;
        //定义该页的四个Fragments
    private Fragment_part01 fragment_part01;
    private Fragment_part02 fragment_part02;
    private Fragment_part03 fragment_part03;
    private Fragment_part04 fragment_part04;
    private FragmentManager fragmentManager;
    //界面的四个Tab选项布局
    private LinearLayout layout_commend,layout_carAbout,layout_carNeeds,layout_driveSkill;
    //各个Tab中的TextView与底部下划线
    private TextView tv_01,tv_02,tv_03,tv_04;
    private ImageView underline_01,underline_02,underline_03,underline_04;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.news_fragment3,container,false);
        initView();
        //得到Fragment管理者
        fragmentManager=getFragmentManager();
        setTabSelection(0);
        return view;
    }


    //第一次创建时就调用
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
        //选择Tab对应fragment
    private void setTabSelection(int index) {
        //每次选中时清除上次的选中状态
        clearSelections();
        //开启一个Fragment事务
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        //先隐藏掉所有Fragment
        hideFragment(fragmentTransaction);
        switch (index){
            case 0:

                tv_01.setTextColor(0xffff0000);
                underline_01.setBackgroundColor(0xffff0000);
                if(fragment_part01==null){
                    fragment_part01=new Fragment_part01(context);
                    //把选中的Fragment装入容器
                    fragmentTransaction.add(R.id.layout_content,fragment_part01);
                }
                else{
                   fragmentTransaction.show(fragment_part01);


                }

                break;
            case 1:

                tv_02.setTextColor(0xffff0000);
                underline_02.setBackgroundColor(0xffff0000);
                if(fragment_part02==null){
                    fragment_part02=new Fragment_part02(context);
                    //把选中的Fragment装入容器
                    fragmentTransaction.add(R.id.layout_content,fragment_part02);
                }
                else{
                    fragmentTransaction.show(fragment_part02);


                }

                break;
            case 2:

                tv_03.setTextColor(0xffff0000);
                underline_03.setBackgroundColor(0xffff0000);
                if(fragment_part03==null){
                    fragment_part03=new Fragment_part03(context);
                    //把选中的Fragment装入容器
                    fragmentTransaction.add(R.id.layout_content,fragment_part03);
                }
                else{
                    fragmentTransaction.show(fragment_part03);


                }

                break;
            case 3:

                tv_04.setTextColor(0xffff0000);
                underline_04.setBackgroundColor(0xffff0000);
                if(fragment_part04==null){
                    fragment_part04=new Fragment_part04(context);
                    //把选中的Fragment装入容器
                    fragmentTransaction.add(R.id.layout_content,fragment_part04);
                }
                else{
                    fragmentTransaction.show(fragment_part04);


                }


                break;



        }
        fragmentTransaction.commit();//提交事务


    }

    //隐藏掉所有Fragment
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (fragment_part01!=null){

            fragmentTransaction.hide(fragment_part01);
        }
        if (fragment_part02!=null){

            fragmentTransaction.hide(fragment_part02);
        }
        if (fragment_part03!=null){

            fragmentTransaction.hide(fragment_part03);
        }
        if (fragment_part04!=null){

            fragmentTransaction.hide(fragment_part04);
        }

    }


    //清除当前所有选择的fragment选项
    private void clearSelections() {
        //文字初始化为黑色，底部线为白色
        tv_01.setTextColor(Color.BLACK);
        tv_02.setTextColor(Color.BLACK);
        tv_03.setTextColor(Color.BLACK);
        tv_04.setTextColor(Color.BLACK);
        underline_01.setBackgroundColor(0xffffffff);
        underline_02.setBackgroundColor(0xffffffff);
        underline_03.setBackgroundColor(0xffffffff);
        underline_04.setBackgroundColor(0xffffffff);

    }


    private void initView() {
        layout_commend= (LinearLayout) view.findViewById(R.id.layoutId_commend);
        layout_carAbout= (LinearLayout) view.findViewById(R.id.layoutId_carAbout);
        layout_carNeeds= (LinearLayout) view.findViewById(R.id.layoutId_carNeeds);
        layout_driveSkill= (LinearLayout) view.findViewById(R.id.layoutId_driveSkill);
        tv_01= (TextView) view.findViewById(R.id.tv_01);
        tv_02= (TextView) view.findViewById(R.id.tv_02);
        tv_03= (TextView) view.findViewById(R.id.tv_03);
        tv_04= (TextView) view.findViewById(R.id.tv_04);
        underline_01= (ImageView) view.findViewById(R.id.underline_1);
        underline_02= (ImageView) view.findViewById(R.id.underline_2);
        underline_03= (ImageView) view.findViewById(R.id.underline_3);
        underline_04= (ImageView) view.findViewById(R.id.underline_4);
        //设置点击事件
        layout_commend.setOnClickListener(this);
        layout_carAbout.setOnClickListener(this);
        layout_carNeeds.setOnClickListener(this);
        layout_driveSkill.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layoutId_commend:

                setTabSelection(0);

                break;
            case R.id.layoutId_carAbout:
                setTabSelection(1);

                break;
            case R.id.layoutId_carNeeds:
                setTabSelection(2);

                break;
            case R.id.layoutId_driveSkill:
                setTabSelection(3);

                break;


        }

    }
}
