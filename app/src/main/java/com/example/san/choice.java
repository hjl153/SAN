package com.example.san;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.san.R;

public class choice extends AppCompatActivity {
    TextView yong,zhi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        yong=findViewById(R.id.yong);
        zhi=findViewById(R.id.zhi);
        yong.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View arg0) {
                Intent in = new Intent(choice.this, login2.class);
                startActivity(in);
                finish();

            }

        });
        zhi.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View arg0) {
                Intent in = new Intent(choice.this, login.class);
                startActivity(in);
                finish();

            }

        });
    }
}

