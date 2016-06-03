package com.example.carwashing.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;

import com.example.carwashing.R;

/**
 * Created by Wakesy on 2016/5/26.
 */
public class LoginActivity extends Activity {

    private ImageView btn_back;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        btn_back= (ImageView) findViewById(R.id.btn_lastpager);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
}
