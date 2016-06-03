package com.example.carwashing.fragments.news_fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carwashing.R;

/**
 * Created by Wakesy on 2016/5/26.
 */
public class Fragment_part02 extends Fragment {


    private View view;
    private Context context;
    public Fragment_part02(Context context) {
        this.context=context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.news_fragment3_part02,container,false);
        return view;
    }
}
