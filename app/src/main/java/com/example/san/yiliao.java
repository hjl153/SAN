package com.example.san;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class yiliao extends AppCompatActivity {
    ImageButton a3,a4,a12,a13,a14;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yiliao);
        a3=(ImageButton)findViewById(R.id.a3);
        a4=(ImageButton)findViewById(R.id.a4);
        a12=(ImageButton)findViewById(R.id.a12);
        a13=(ImageButton)findViewById(R.id.a13);
        a14=(ImageButton)findViewById(R.id.a14);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_launcher_background);
        requestOptions.circleCropTransform();
        requestOptions.transforms( new RoundedCorners(50));
        Glide.with(this).load(R.mipmap.a3) //图片地址
                .apply(requestOptions)
                .into(a3);
        Glide.with(this).load(R.mipmap.a4) //图片地址
                .apply(requestOptions)
                .into(a4);
        Glide.with(this).load(R.mipmap.a12) //图片地址
                .apply(requestOptions)
                .into(a12);
        Glide.with(this).load(R.mipmap.a13) //图片地址
                .apply(requestOptions)
                .into(a13);
        Glide.with(this).load(R.mipmap.a14) //图片地址
                .apply(requestOptions)
                .into(a14);
        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(yiliao.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
        a4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(yiliao.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
        a12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(yiliao.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
        a13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(yiliao.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
        a14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(yiliao.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
    }
}
