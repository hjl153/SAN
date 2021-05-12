package com.example.san;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dingdan_xian extends AppCompatActivity {
    private TextView u_name,u_score,u_type,u_long,u_address,u_yao,u_time,u_lei;
    private Button cancer,del;
    int M;
    String i,c;
    int V_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingdan_xian);
        u_name=(TextView) findViewById(R.id.u_name);
        u_score=(TextView) findViewById(R.id.u_score);
        u_type=(TextView) findViewById(R.id.u_type);
        u_long=(TextView) findViewById(R.id.u_long);
        u_address=(TextView) findViewById(R.id.u_address);
        u_yao=(TextView) findViewById(R.id.u_yao);
        u_time=(TextView) findViewById(R.id.u_time);
        u_lei=(TextView) findViewById(R.id.u_lei);
        String a=getIntent().getStringExtra("a");
        String b=getIntent().getStringExtra("b");
        c=getIntent().getStringExtra("c");
        String d=getIntent().getStringExtra("d");
        String e=getIntent().getStringExtra("e");
        String f=getIntent().getStringExtra("f");
        String g=getIntent().getStringExtra("g");
        String h=getIntent().getStringExtra("h");
        V_id=getIntent().getIntExtra("x",0);
        i=getIntent().getStringExtra("i");
        M=getIntent().getIntExtra("id",0);
        u_name.setText(c);
        u_score.setText(g);
        u_type.setText(d);
        u_long.setText(e);
        u_address.setText(f);
        u_yao.setText(b);
        u_time.setText(a);
        u_lei.setText(h);
        cancer=findViewById(R.id.u_cancer);
        del=findViewById(R.id.del);
        ImageView v=(ImageView)findViewById(R.id.beij1);
        RequestOptions requestOptions = new RequestOptions();
        //requestOptions.placeholder(R.drawable.ic_launcher_background);
        requestOptions.circleCropTransform();
        requestOptions.transforms( new RoundedCorners(50));
        Glide.with(this).load(R.mipmap.u1) //图片地址
                .apply(requestOptions)
                .into(v);
        cancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(dingdan_xian.this)
                        .setMessage("是否取消订单?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try { Connection conn = null;
                                            conn = (Connection) DBOpenHelper.getConn();
                                            int u = 0;
                                            if(i.equals("已接单")){
                                            String sql="update Orders set volunteer_id='',Order_condition='已发布' where Order_id="+M+"";
                                                PreparedStatement pst;
                                                pst = (PreparedStatement) conn.prepareStatement(sql);
                                                u = pst.executeUpdate();
                                                pst.close();
                                                conn.close();
                                                Looper.prepare();
                                                Intent intent = new Intent();
                                                intent.setClass(dingdan_xian.this, U_din.class);
                                                Toast.makeText(getApplicationContext(), "订单取消成功",
                                                        Toast.LENGTH_SHORT).show();
                                                startActivity(intent);
                                                finish();
                                                Looper.loop();
                                            }
                                            else if(i.equals("已发布")){
                                                String sql="delete from  Orders where Order_id="+M+"";
                                                PreparedStatement pst;
                                                pst = (PreparedStatement) conn.prepareStatement(sql);
                                                u = pst.executeUpdate();
                                                pst.close();
                                                conn.close();
                                                Looper.prepare();
                                                Intent intent = new Intent();
                                                intent.setClass(dingdan_xian.this, U_din.class);
                                                Toast.makeText(getApplicationContext(), "订单取消成功",
                                                        Toast.LENGTH_SHORT).show();
                                                startActivity(intent);
                                                finish();
                                                Looper.loop();
                                            }
                                           else{
                                                Looper.prepare();
                                                Toast.makeText(getApplicationContext(), "该订单不可取消",
                                                        Toast.LENGTH_SHORT).show();
                                                Looper.loop();
                                            }

                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }).start();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .create().show();

            }  }  );
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(dingdan_xian.this)
                        .setMessage("是否拨打接单者电话?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Connection conn = null;
                                        conn = (Connection) DBOpenHelper.getConn();
                                        int u = 0;
                                        if(i.equals("已接单")||i.equals("正在进行")){
                                            String sql = "select Volunteer_phone from Volunteer where Volunteer_id='"+V_id+"'";
                                            Statement st;
                                            try{
                                            st = (Statement) conn.createStatement();
                                            ResultSet rs = st.executeQuery(sql);
                                            while (rs.next()){
                                                String ps=rs.getString("Volunteer_phone");
                                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +ps));
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                            st.close();
                                            conn.close();
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }


                                        }
                                        else if(i.equals("已发布")){
                                            Looper.prepare();
                                            Toast.makeText(getApplicationContext(), "尚未有接单者",
                                                    Toast.LENGTH_SHORT).show();
                                            Looper.loop();
                                        }
                                        else{
                                            Looper.prepare();
                                            Toast.makeText(getApplicationContext(), "该订单不可取消",
                                                    Toast.LENGTH_SHORT).show();
                                            Looper.loop();
                                        }

                                    }
                                }).start();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .create().show();

            }  }  );

    }

     }
