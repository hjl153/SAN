package com.example.san;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.smssdk.SMSSDK;

public class Dingdan extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private TextView lei;
    String TY;
    String Na;
    ListView din;
    private List<Map<String ,Object>> listItems;
    Dadapter dadapter;
    String t;
    String id;
    int ID;
    String na="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingdan);
        lei=(TextView) findViewById(R.id.lei);
        SharedPreferences sharedPreferences=getSharedPreferences("sp",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        TY=sharedPreferences.getString("TY","");
        Na=sharedPreferences.getString("volunteer_username","");
        id=sharedPreferences.getString("volunteer_id","");
        t=sharedPreferences.getString("you","");
        ID=Integer.parseInt(id);
        Log.i("wxy","year"+Na+TY);
        lei.setText("——"+TY);
        listItems=new ArrayList<Map<String ,Object>>();
        //listItems=getData();
        final Handler handler=new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                listItems=(ArrayList<Map<String ,Object>>)msg.obj;
                    //修改控件文本进行倒计时  i 以60秒倒计时为例
                    din=(ListView)findViewById(R.id.din);
                    dadapter=new Dadapter(Dingdan.this,R.layout.activity_dingdan,listItems);
                    din.setEmptyView(findViewById(R.id.nodata));
                    din.setAdapter(dadapter);
                    din.setOnItemClickListener(Dingdan.this);
                }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection conn = null;
                    conn =(Connection) DBOpenHelper.getConn();
                    String sql = "select * from Orders where Order_type='"+TY+"' and Order_condition='已发布' ";
                    Statement st;
                    st = (Statement) conn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()){
                        int i=rs.getInt("User_id");
                        String sql1 = "select User_name from User where User_id='"+i+"'";
                        Statement st1;
                        st1 = (Statement) conn.createStatement();
                        ResultSet rs1 = st1.executeQuery(sql1);
                        while (rs1.next()){
                           na=rs1.getString("User_name");
                        }
                        Map<String, Object> map=new HashMap<String, Object>();
                        map.put("id",rs.getInt("Order_id"));
                        map.put("list_time",rs.getString("Order_starttime"));
                        Log.i("wxy","year"+map.get("id"));
                        map.put("list_yao",rs.getString("Order_description"));
                        map.put("list_id",rs.getInt("User_id"));
                        map.put("list_people",na);
                        map.put("list_ob",rs.getString("Order_yao"));
                        map.put("list_long",rs.getString("Order_time"));
                        map.put("list_address",rs.getString("Order_address"));
                        map.put("list_score",rs.getString("Order_evaluation"));
                        listItems.add(map);



                    }
                    st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.obj = listItems;
                handler .sendMessage(message);

            }
        }).start();

    }
    Map<String ,String> map;
    String a,b,c,d,e,f,g;
    int M,x;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
       map= (Map<String, String>) din.getItemAtPosition(position);
         a=map.get("list_time");
         b=map.get("list_yao");
         c=map.get("list_people");
         d=map.get("list_ob");
         e=map.get("list_long");
         f=map.get("list_address");
         g=map.get("list_score");
         String X=String.valueOf(map.get("list_id"));
         x=Integer.parseInt(X);
        String m=String.valueOf(map.get("id"));
        M=Integer.parseInt(m);
        if(!t.equals(map.get("list_ob"))){
            Toast.makeText(getApplicationContext(), "您不符合客户要求",
                    Toast.LENGTH_SHORT).show();
            Log.i("wxy","year"+map.get("list_ob"));
        }
        else{
        new AlertDialog.Builder(Dingdan.this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("注意")
                .setMessage("接受该订单？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try { Connection conn = null;
                                    conn = (Connection) DBOpenHelper.getConn();
                                    int u = 0;
                                    String sql="update Orders set volunteer_id='"+ID+"',Order_condition='已接单',Order_evaluation2='5.00' where Order_id="+M+"";
                                    PreparedStatement pst;
                                        pst = (PreparedStatement) conn.prepareStatement(sql);
                                        u = pst.executeUpdate();
                                        pst.close();
                                        conn.close();

                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }

                            }
                        }).start();
                        dadapter.remove(din.getItemAtPosition(position));
                        Toast.makeText(getApplicationContext(), "接单成功",
                                Toast.LENGTH_SHORT).show();
                        Intent ratecal=new Intent(Dingdan.this,dingdan_xian_v.class);
                        ratecal.putExtra("a",a);
                        ratecal.putExtra("b",b);
                        ratecal.putExtra("c",c);
                        ratecal.putExtra("d",d);
                        ratecal.putExtra("e",e);
                        ratecal.putExtra("f",f);
                        ratecal.putExtra("g",g);
                        ratecal.putExtra("h",TY);
                        ratecal.putExtra("x",x);
                        ratecal.putExtra("i","已接单");
                        ratecal.putExtra("id",M);
                        startActivity(ratecal);
                        dadapter.notifyDataSetChanged();
                        }})
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).create().show();
    }}



}

