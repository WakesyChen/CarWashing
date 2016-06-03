package com.example.carwashing.fragments.me_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.carwashing.R;
import com.example.carwashing.activities.LoginActivity;

/**
 * Created by Wakesy on 2016/5/20.
 */
public class Me_fragment_4 extends Fragment implements View.OnClickListener {
    private Context context;
    private Button btn_option, btn_logimNow;
    private LinearLayout ll_ticket, ll_carroom;
    private LinearLayout ll_myorder, ll_saleAfter, ll_record, ll_safelist, ll_shopping, ll_address,
                        ll_invite,ll_waiterOline,ll_commend;
    private LinearLayout ll_waitForPay, ll_waitForSend, ll_waitForReceive, ll_waitForCheck, ll_waitForCommend;



    public Me_fragment_4(Context context) {
        this.context = context;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment4, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btn_option = (Button) view.findViewById(R.id.btn_option);
        btn_logimNow = (Button) view.findViewById(R.id.btn_loginNow);

        ll_ticket = (LinearLayout) view.findViewById(R.id.ll_tickets);
        ll_carroom = (LinearLayout) view.findViewById(R.id.ll_carroom);

        ll_myorder = (LinearLayout) view.findViewById(R.id.ll_myorder);
        ll_saleAfter = (LinearLayout) view.findViewById(R.id.ll_saleAfter);
        ll_record = (LinearLayout) view.findViewById(R.id.ll_records);
        ll_safelist = (LinearLayout) view.findViewById(R.id.ll_safelist);
        ll_shopping = (LinearLayout) view.findViewById(R.id.ll_shoppingcar);
        ll_address = (LinearLayout) view.findViewById(R.id.ll_address);
        ll_invite = (LinearLayout) view.findViewById(R.id.ll_invate);
        ll_waiterOline = (LinearLayout) view.findViewById(R.id.ll_waiter_online);
        ll_commend = (LinearLayout) view.findViewById(R.id.ll_suggestion);

        ll_waitForPay = (LinearLayout) view.findViewById(R.id.ll_waitForPay);
        ll_waitForSend = (LinearLayout) view.findViewById(R.id.ll_waitForSend);
        ll_waitForReceive = (LinearLayout) view.findViewById(R.id.ll_waitForReceive);
        ll_waitForCheck = (LinearLayout) view.findViewById(R.id.ll_waitForCheck);
        ll_waitForCommend = (LinearLayout) view.findViewById(R.id.ll_waitForCommend);

        btn_option.setOnClickListener(this);
        btn_logimNow.setOnClickListener(this);
        ll_ticket.setOnClickListener(this);
        ll_carroom.setOnClickListener(this);
        ll_myorder.setOnClickListener(this);
        ll_saleAfter.setOnClickListener(this);
        ll_record.setOnClickListener(this);
        ll_safelist.setOnClickListener(this);
        ll_shopping.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        ll_commend.setOnClickListener(this);
        ll_waitForPay.setOnClickListener(this);
        ll_waitForSend.setOnClickListener(this);
        ll_waitForReceive.setOnClickListener(this);
        ll_waitForCheck.setOnClickListener(this);
        ll_waitForCommend.setOnClickListener(this);
        ll_invite.setOnClickListener(this);
        ll_waiterOline .setOnClickListener(this);
    }


    private void jumpToLogin() {
        Log.i("Intent_activity",""+context);
        Intent intent = new Intent(context, LoginActivity.class);

        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_option:
                jumpToLogin();
                break;
            case R.id.btn_loginNow:
                jumpToLogin();
                break;
            case R.id.ll_tickets:
                jumpToLogin();
                break;
            case R.id.ll_carroom:
                jumpToLogin();
                break;
            case R.id.ll_myorder:
                jumpToLogin();
                break;
            case R.id.ll_saleAfter:
                jumpToLogin();
                break;
            case R.id.ll_records:
                jumpToLogin();
                break;
            case R.id.ll_safelist:
                jumpToLogin();
                break;
            case R.id.ll_shoppingcar:
                jumpToLogin();
                break;
            case R.id.ll_address:
                jumpToLogin();
                break;
            case R.id.ll_invate:
                jumpToLogin();
                break;
            case R.id.ll_waiter_online:
                jumpToLogin();
                break;
            case R.id.ll_suggestion:
                jumpToLogin();
                break;
            case R.id.ll_waitForPay:
                jumpToLogin();
                break;
            case R.id.ll_waitForSend:
                jumpToLogin();
                break;
            case R.id.ll_waitForReceive:
                jumpToLogin();
                break;
            case R.id.ll_waitForCheck:
                jumpToLogin();
                break;
            case R.id.ll_waitForCommend:
                jumpToLogin();
                break;


        }

    }
}
