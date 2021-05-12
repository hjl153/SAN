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
import java.text.SimpleDateFormat;
import java.util.Date;

public class dingdan_xian_v extends AppCompatActivity {
    private TextView v_name,v_score,v_type,v_long,v_address,v_yao,v_time,v_lei;
    private Button start,end,del;
    int M;
    String i,t,x,c;
    int U_id;
    Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingdan_xian_v);
        v_name=(TextView) findViewById(R.id.v_name);
        v_score=(TextView) findViewById(R.id.v_score);
        v_type=(TextView) findViewById(R.id.v_type);
        v_long=(TextView) findViewById(R.id.v_long);
        v_address=(TextView) findViewById(R.id.v_address);
        v_yao=(TextView) findViewById(R.id.v_yao);
        v_time=(TextView) findViewById(R.id.v_time);
        v_lei=(TextView) findViewById(R.id.v_lei);
        String a=getIntent().getStringExtra("a");
        String b=getIntent().getStringExtra("b");
        c=getIntent().getStringExtra("c");
        String d=getIntent().getStringExtra("d");
        String e=getIntent().getStringExtra("e");
        String f=getIntent().getStringExtra("f");
        String g=getIntent().getStringExtra("g");
        String h=getIntent().getStringExtra("h");
        U_id=getIntent().getIntExtra("x",0);
        i=getIntent().getStringExtra("i");
        M=getIntent().getIntExtra("id",0);
        v_name.setText(c);
        v_score.setText(g);
        v_type.setText(d);
        v_long.setText(e);
        v_address.setText(f);
        v_yao.setText(b);
        v_time.setText(a);
        v_lei.setText(h);
        start=findViewById(R.id.v_start);
        end=findViewById(R.id.v_end);
        del=findViewById(R.id.del2);
        ImageView v=(ImageView)findViewById(R.id.beij2);
        RequestOptions requestOptions = new RequestOptions();
        //requestOptions.placeholder(R.drawable.ic_launcher_background);
        requestOptions.circleCropTransform();
        requestOptions.transforms( new RoundedCorners(50));
        Glide.with(this).load(R.mipmap.u2) //图片地址
                .apply(requestOptions)
                .into(v);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        date = new Date(System.currentTimeMillis());
        t=simpleDateFormat.format(date);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(dingdan_xian_v.this)
                        .setMessage("是否开始服务?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try { Connection conn = null;
                                            conn = (Connection) DBOpenHelper.getConn();
                                            int u = 0;
                                            String sql = "select Order_condition from Orders where Order_id='"+M+"'";
                                            Statement st;
                                            st = (Statement) conn.createStatement();
                                            ResultSet rs = st.executeQuery(sql);
                                            while (rs.next()){
                                                x=rs.getString("Order_condition");
                                            }
                                            st.close();

                                            if(x.equals("已接单")){
                                                String sql2="update Orders set Order_start='"+t+"',Order_condition='正在进行' where Order_id="+M+"";
                                                PreparedStatement pst;
                                                pst = (PreparedStatement) conn.prepareStatement(sql2);
                                                u = pst.executeUpdate();
                                                pst.close();
                                                conn.close();
                                                Looper.prepare();
                                                Toast.makeText(getApplicationContext(), "服务开始，祝您愉快",
                                                        Toast.LENGTH_SHORT).show();
                                                Looper.loop();
                                            }
                                            else if(x.equals("正在进行")){
                                                Looper.prepare();
                                                Toast.makeText(getApplicationContext(), "您已开始服务，请等待结束",
                                                        Toast.LENGTH_SHORT).show();
                                                Looper.loop();
                                            }
                                            else{
                                                Looper.prepare();
                                                Toast.makeText(getApplicationContext(), "该订单已销毁",
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
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(dingdan_xian_v.this)
                        .setMessage("是否结束服务?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try { Connection conn = null;
                                            conn = (Connection) DBOpenHelper.getConn();
                                            int u = 0;
                                            String sql = "select Order_condition from Orders where Order_id='"+M+"'";
                                            Statement st;
                                            st = (Statement) conn.createStatement();
                                            ResultSet rs = st.executeQuery(sql);
                                            while (rs.next()){
                                                x=rs.getString("Order_condition");
                                            }
                                            st.close();
                                            if(x.equals("正在进行")){
                                                String sql3="update Orders set Order_end='"+t+"',Order_condition='已结束' where Order_id="+M+"";
                                                PreparedStatement pst;
                                                pst = (PreparedStatement) conn.prepareStatement(sql3);
                                                u = pst.executeUpdate();
                                                pst.close();
                                                conn.close();
                                                Looper.prepare();
                                                Toast.makeText(getApplicationContext(), "服务结束，辛苦了",
                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent();
                                                intent.setClass(dingdan_xian_v.this, Score_volunteer.class);
                                                intent.putExtra("id",M);
                                                startActivity(intent);
                                                Looper.loop();
                                            }
                                            else if(x.equals("已接单")){
                                                Looper.prepare();
                                                Toast.makeText(getApplicationContext(), "请先开始服务",
                                                        Toast.LENGTH_SHORT).show();
                                                Looper.loop();
                                            }
                                            else{
                                                Looper.prepare();
                                                Toast.makeText(getApplicationContext(), "该订单已销毁",
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
                new AlertDialog.Builder(dingdan_xian_v.this)
                        .setMessage("是否拨打老人电话?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Connection conn = null;
                                        conn = (Connection) DBOpenHelper.getConn();
                                        int u = 0;
                                            String sql = "select User_phone from User where User_id='"+U_id+"'";
                                            Statement st;
                                            try{
                                                st = (Statement) conn.createStatement();
                                                ResultSet rs = st.executeQuery(sql);
                                                while (rs.next()){
                                                    String ps=rs.getString("User_phone");
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
