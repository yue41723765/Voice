package com.chuangying.taohui.voice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.chuangying.taohui.voice.view.activity.CustomActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.home_all)Button allBtn;
    @BindView(R.id.home_custom)Button customBtn;

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.status_bar_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_all:
                intent=new Intent();
                break;
            case R.id.home_custom:
                intent=new Intent(MainActivity.this, CustomActivity.class);
                startActivity(intent);
                break;
        }
    }
}
