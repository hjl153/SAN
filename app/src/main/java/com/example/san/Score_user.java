package com.example.san;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Score_user extends AppCompatActivity {
    Float rate;
    int M;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_user);
        Button submit = findViewById(R.id.sub);
        ratingBar = findViewById(R.id.star);
        M=getIntent().getIntExtra("id",0);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try { Connection conn = null;
                            conn = (Connection) DBOpenHelper.getConn();
                            int u = 0;
                                String sql="update Orders set Order_evaluation2="+ratingBar.getRating()+" where Order_id="+M+"";
                                PreparedStatement pst;
                                pst = (PreparedStatement) conn.prepareStatement(sql);
                                u = pst.executeUpdate();
                                pst.close();
                                conn.close();
                            Looper.prepare();
                            Toast.makeText(Score_user.this, "评分完成", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setClass(Score_user.this, U_din.class);
                            startActivity(intent);
                            finish();
                            Looper.loop();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });
    }
}
