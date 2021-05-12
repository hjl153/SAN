package com.example.san;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class zhiyuan extends AppCompatActivity {
    ImageButton a7,a8,a15,a16,a17;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhiyuan);
        a7=(ImageButton)findViewById(R.id.a7);
        a8=(ImageButton)findViewById(R.id.a8);
        a15=(ImageButton)findViewById(R.id.a15);
        a16=(ImageButton)findViewById(R.id.a16);
        a17=(ImageButton)findViewById(R.id.a17);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_launcher_background);
        requestOptions.circleCropTransform();
        requestOptions.transforms( new RoundedCorners(50));
        Glide.with(this).load(R.mipmap.a7) //图片地址
                .apply(requestOptions)
                .into(a7);
        Glide.with(this).load(R.mipmap.a8) //图片地址
                .apply(requestOptions)
                .into(a8);
        Glide.with(this).load(R.mipmap.a15) //图片地址
                .apply(requestOptions)
                .into(a15);
        Glide.with(this).load(R.mipmap.a16) //图片地址
                .apply(requestOptions)
                .into(a16);
        Glide.with(this).load(R.mipmap.a17) //图片地址
                .apply(requestOptions)
                .into(a17);
        a7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(zhiyuan.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
        a8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(zhiyuan.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
        a15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(zhiyuan.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
        a16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(zhiyuan.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
        a17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(zhiyuan.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
    }
}