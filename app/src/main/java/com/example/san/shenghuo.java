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

public class shenghuo extends AppCompatActivity {
    ImageButton a1,a2,a9,a10,a11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shenghuo);
        a1=(ImageButton)findViewById(R.id.a1);
        a2=(ImageButton)findViewById(R.id.a2);
        a9=(ImageButton)findViewById(R.id.a9);
        a10=(ImageButton)findViewById(R.id.a10);
        a11=(ImageButton)findViewById(R.id.a11);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_launcher_background);
        requestOptions.circleCropTransform();
        requestOptions.transforms( new RoundedCorners(50));
        Glide.with(this).load(R.mipmap.a1) //图片地址
                .apply(requestOptions)
                .into(a1);
        Glide.with(this).load(R.mipmap.a2) //图片地址
                .apply(requestOptions)
                .into(a2);
        Glide.with(this).load(R.mipmap.a9) //图片地址
                .apply(requestOptions)
                .into(a9);
        Glide.with(this).load(R.mipmap.a11) //图片地址
                .apply(requestOptions)
                .into(a11);
        Glide.with(this).load(R.mipmap.a10) //图片地址
                .apply(requestOptions)
                .into(a10);
        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                    Intent intent = new Intent();
                    intent.setClass(shenghuo.this,fabu.class);
                    startActivity(intent);
                finish();

            }

        });
        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(shenghuo.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
        a9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(shenghuo.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
        a10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(shenghuo.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
        a11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(shenghuo.this,fabu.class);
                startActivity(intent);
                finish();

            }

        });
    }
}
