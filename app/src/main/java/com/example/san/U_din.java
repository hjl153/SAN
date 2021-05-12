package com.example.san;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class U_din extends AppCompatActivity implements AdapterView.OnItemClickListener{
    String TY;
    String Na;
    ListView din;
    private List<Map<String ,Object>> listItems;
    uadapter uadapter;
    String t;
    String id;
    int ID;
    String na="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_din);
        SharedPreferences sharedPreferences=getSharedPreferences("sp",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        TY=sharedPreferences.getString("TY","");
        Na=sharedPreferences.getString("user_username","");
        id=sharedPreferences.getString("user_id","");
        ID=Integer.parseInt(id);
        t=sharedPreferences.getString("you","");
        Log.i("wxy","year"+Na+TY);
        listItems=new ArrayList<Map<String ,Object>>();
        //listItems=getData();
        final Handler handler=new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                listItems=(ArrayList<Map<String ,Object>>)msg.obj;
                //修改控件文本进行倒计时  i 以60秒倒计时为例
                din=(ListView)findViewById(R.id.dinuser);
                uadapter=new uadapter(U_din.this,R.layout.activity_u_din,listItems);
                din.setEmptyView(findViewById(R.id.unodata));
                din.setAdapter(uadapter);
                din.setOnItemClickListener(U_din.this);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection conn = null;
                    conn =(Connection) DBOpenHelper.getConn();
                    String sql = "select * from Orders where user_id='"+ID+"' ";
                    Statement st;
                    st = (Statement) conn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()){
                        Map<String, Object> map=new HashMap<String, Object>();
                        map.put("id",rs.getInt("Order_id"));
                        map.put("list_time",rs.getString("Order_starttime"));
                        Log.i("wxy","year"+map.get("id"));
                        map.put("list_yao",rs.getString("Order_description"));
                        map.put("list_zhuangtai",rs.getString("Order_condition"));
                        String a="",b="";
                        int i=0;
                        if(rs.getString("Order_condition").equals("已发布")){
                            a="尚未接单";
                            b="";
                        }
                        else{
                            i=rs.getInt("volunteer_id");
                            String sql1 = "select volunteer_name from volunteer where volunteer_id='"+i+"'";
                            Statement st1;
                            st1 = (Statement) conn.createStatement();
                            ResultSet rs1 = st1.executeQuery(sql1);
                            while (rs1.next()){
                                na=rs1.getString("volunteer_name");
                            }
                            a=na;
                            b=rs.getString("Order_evaluation2");
                        }
                        map.put("list_ob",rs.getString("Order_yao"));
                        map.put("list_score",b);
                        map.put("list_people",a);
                        map.put("list_id",i);
                        map.put("list_long",rs.getString("Order_time"));
                        map.put("list_address",rs.getString("Order_address"));
                        map.put("list_lei",rs.getString("Order_type"));
                        listItems.add(map);
                        Log.i("wxy","year"+map.get("list_zhuangtai"));
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
    String a,b,c,d,e,f,g,h,i;

    int M,x;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        map = (Map<String, String>) din.getItemAtPosition(position);
        a=map.get("list_time");
        b=map.get("list_yao");
        c=map.get("list_people");
        d=map.get("list_ob");
        e=map.get("list_long");
        f=map.get("list_address");
        g=map.get("list_score");
        h=map.get("list_lei");
        i=map.get("list_zhuangtai");
        String X=String.valueOf(map.get("list_id"));
        String m=String.valueOf(map.get("id"));
        x=Integer.parseInt(X);
        M=Integer.parseInt(m);
        if(i.equals("已结束")){
            new AlertDialog.Builder(U_din.this)
                    .setMessage("是否进行评价")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            Intent intent = new Intent();
                            intent.setClass(U_din.this, Score_user.class);
                            intent.putExtra("id",M);
                            startActivity(intent);}})
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    })
                    .create().show();
        }
        else{
            Intent ratecal=new Intent(U_din.this,dingdan_xian.class);
            ratecal.putExtra("a",a);
            ratecal.putExtra("b",b);
            ratecal.putExtra("c",c);
            ratecal.putExtra("d",d);
            ratecal.putExtra("e",e);
            ratecal.putExtra("f",f);
            ratecal.putExtra("g",g);
            ratecal.putExtra("h",h);
            ratecal.putExtra("i",i);
            ratecal.putExtra("id",M);
            ratecal.putExtra("x",x);
            startActivity(ratecal);
        }
    }


}