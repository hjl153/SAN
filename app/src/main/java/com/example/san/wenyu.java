package com.example.san;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class wenyu extends AppCompatActivity {
    ImageButton a5,a6,a18,a19,a20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wenyu);
        a5=(ImageButton)findViewById(R.id.a5);
        a6=(ImageButton)findViewById(R.id.a6);
        a18=(ImageButton)findViewById(R.id.a18);
        a19=(ImageButton)findViewById(R.id.a19);
        a20=(ImageButton)findViewById(R.id.a20);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_launcher_background);
        requestOptions.circleCropTransform();
        requestOptions.transforms( new RoundedCorners(50));
        Glide.with(this).load(R.mipmap.a5) //图片地址
                .apply(requestOptions)
                .into(a5);
        Glide.with(this).load(R.mipmap.a6) //图片地址
                .apply(requestOptions)
                .into(a6);
        Glide.with(this).load(R.mipmap.a18) //图片地址
                .apply(requestOptions)
                .into(a18);
        Glide.with(this).load(R.mipmap.a19) //图片地址
                .apply(requestOptions)
                .into(a19);
        Glide.with(this).load(R.mipmap.a20) //图片地址
                .apply(requestOptions)
                .into(a20);
        a5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(wenyu.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
        a6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(wenyu.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
        a18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(wenyu.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
        a19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(wenyu.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
        a20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(wenyu.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
    }
}