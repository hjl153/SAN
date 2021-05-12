package com.example.san;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.*;
public class Zhongxin extends AppCompatActivity {
    TextView name,time,chan,dingdan,tui,ping,shu,ji,ke;
    String type,Na;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhongxin);
        SharedPreferences sharedPreferences=getSharedPreferences("sp",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        type=sharedPreferences.getString("type","");
        ImageView pay = (ImageView)findViewById(R.id.tou);
        if(type.equals("user_xm")){
            Na=sharedPreferences.getString("user_username","");
            String ID=sharedPreferences.getString("user_id","");
            id=Integer.parseInt(ID);
            pay.setImageResource(R.mipmap.u11);
        }
        else{
            Na=sharedPreferences.getString("volunteer_username","");
            String ID=sharedPreferences.getString("volunteer_id","");
            id=Integer.parseInt(ID);
            pay.setImageResource(R.mipmap.u64);
        }
        name=findViewById(R.id.yonghuxingming);
        time=findViewById(R.id.dengruyonghu);
        chan=findViewById(R.id.chan);
        dingdan=findViewById(R.id.dingdan);
        tui=findViewById(R.id.tui);
        ping=findViewById(R.id.ping);
        shu=findViewById(R.id.wo_diandan_shu);
        ji=findViewById(R.id.wo_jifen_shu);
        name.setText(Na);
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    //修改控件文本进行倒计时  i 以60秒倒计时为例
                    time.setText( msg.obj+"");
                }
                if (msg.what == -1) {
                    //修改控件文本进行倒计时  i 以60秒倒计时为例
                    ping.setText( "个人评分： "+msg.obj+"分");
                }
                if (msg.what == -2) {
                    int a=(Integer) msg.obj;
                    //修改控件文本进行倒计时  i 以60秒倒计时为例
                    shu.setText( ""+msg.obj+"单");
                    ji.setText( ""+a*5+"分");
                }
            }
            };

        chan.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View arg0) {

                if(type.equals("user_xm")){
                    Intent intent = new Intent();
                    intent.setClass(Zhongxin.this, User_xinxi.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent();
                    intent.setClass(Zhongxin.this,volunteer_xinxi.class);
                    startActivity(intent);
                }

            }

        });
        dingdan.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View arg0) {

                if(type.equals("user_xm")){
                    Intent intent = new Intent();
                    intent.setClass(Zhongxin.this, U_din.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent();
                    intent.setClass(Zhongxin.this,v_din.class);
                    startActivity(intent);
                }

            }

        });
        tui.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Zhongxin.this);
                builder.setTitle("提示");
                builder.setMessage("是否确定退出");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getSharedPreferences("sp", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent=new Intent();
                        intent.setClass(Zhongxin.this, choice.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection conn = null;
                    conn =(Connection) DBOpenHelper.getConn();
                    String sql;
                    String start,end;
                    Float ping;
                    if(type.equals("user_xm")){
                        sql = "select Order_start,Order_end,Order_evaluation from Orders where user_id='"+id+"'";
                        Statement st;
                        st = (Statement) conn.createStatement();
                        ResultSet rs = st.executeQuery(sql);
                        long t=0;
                        float d=0f;
                        int s=0,j=0;
                        while (rs.next()){
                            start=rs.getString("Order_start");
                            end=rs.getString("Order_end");
                            ping=rs.getFloat("Order_evaluation");
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            if(ping!=null){
                            s=s+1;}
                            j=j+1;
                            if(start!=null||end!=null){
                                try {
                                    Date F = format.parse(start);
                                    Date E = format.parse(end);
                                    long A = E.getTime() - F.getTime();
                                    t=t+A;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                            d=ping+d;
                        }
                            d=d/s;
                            long longHours = t / (60 * 60 * 1000); //根据时间差来计算小时数
                            long longMinutes = (t - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数
                            String a=longHours+"小时"+longMinutes+"分钟";
                            Message message = new Message ();
                            message.what = 1;
                            message.obj =a;
                            handler.sendMessage (message);
                        DecimalFormat df = new DecimalFormat("#.00");
                        String D=df.format(d);
                            Message msg = new Message ();
                            msg.what = -1;
                            msg.obj =D;
                            handler.sendMessage (msg);
                        Message msg2 = new Message ();
                        msg2.what = -2;
                        msg2.obj =j;
                        handler.sendMessage (msg2);
                            st.close();
                            conn.close();
                    }
                    else{
                        sql = "select Order_start,Order_end,Order_evaluation2 from Orders where Volunteer_id='"+id+"'";
                        Statement st;
                        st = (Statement) conn.createStatement();
                        ResultSet rs = st.executeQuery(sql);
                        long t=0;
                        float d=0f;
                        int s=0,j=0;
                        while (rs.next()){
                            start=rs.getString("Order_start");
                            end=rs.getString("Order_end");
                            ping=rs.getFloat("Order_evaluation2");
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            if(ping!=null){
                            s=s+1;}
                            j=j+1;
                            if(start!=null||end!=null){
                                try {
                                    Date F = format.parse(start);
                                    Date E = format.parse(end);
                                    long A = E.getTime() - F.getTime();
                                    t=t+A;
                                    Log.d("tag",String.valueOf(s)+String.valueOf(ping));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }}
                            d=ping+d;
                            }
                            d=d/s;
                            long longHours = t / (60 * 60 * 1000); //根据时间差来计算小时数
                            long longMinutes = (t - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数
                            String a=longHours+"小时"+longMinutes+"分钟";
                            Message message = new Message ();
                            message.what = 1;
                            message.obj =a;
                        DecimalFormat df = new DecimalFormat("#.00");
                        String D=df.format(d);
                        handler.sendMessage (message);
                        Message msg = new Message ();
                        msg.what = -1;
                        msg.obj =D;
                        Log.d("tag",String.valueOf(D));
                        handler.sendMessage (msg);
                        Message msg2 = new Message ();
                        msg2.what = -2;
                        msg2.obj =j;
                        handler.sendMessage (msg2);
                            st.close();
                            conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
