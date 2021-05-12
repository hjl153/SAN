package com.example.san;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.*;
public class User_xinxi extends AppCompatActivity {
    private EditText tv_name;
    private EditText tv_dianwei;
    private EditText tv_dianhua;
    private EditText tv_youxiang;
    private Button baocun;
    RadioGroup rg1;
    RadioButton a1;
    RadioButton a2;
    RadioGroup rg2;
    RadioButton b1;
    RadioButton b2;
    String sex="";
    String iF="";
    boolean flag;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_xinxi);
        rg1=(RadioGroup)findViewById(R.id.rg1);
        a1=(RadioButton)findViewById(R.id.nan);
        a2=(RadioButton)findViewById(R.id.nv);
        rg2=(RadioGroup)findViewById(R.id.rg2);
        b1=(RadioButton)findViewById(R.id.yes);
        b2=(RadioButton)findViewById(R.id.no);
        baocun =  findViewById(R.id.baocun);
        tv_name =  findViewById(R.id.tv_name);
        tv_dianwei =  findViewById(R.id.tv_dianwei);
        tv_dianhua =  findViewById(R.id.tv_dianhua);
        tv_youxiang =  findViewById(R.id.tv_youxiang);
        SharedPreferences sharedPreferences=getSharedPreferences("sp",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        phone=sharedPreferences.getString("user_phone","");
        Log.d("tag",phone);
        tv_dianhua.setText(phone);
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (a1.getId() == checkedId) {
                    sex = a1.getText().toString();
                } else if (a2.getId() == checkedId) {
                    sex = a2.getText().toString();
                }
            }
        });
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (b1.getId() == checkedId) {
                    iF = b1.getText().toString();
                } else if (b2.getId() == checkedId) {
                    iF = b2.getText().toString();
                }
            }
        });


 baocun.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
    new AlertDialog.Builder(User_xinxi.this)
            .setMessage("是否确认提交?")
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String a = tv_name.getText().toString();
                            String c = tv_dianwei.getText().toString();
                            String d = tv_dianhua.getText().toString();
                            String E = tv_youxiang.getText().toString();

                            if (a.length() == 0) {
                                flag = false;
                            } else if (c.length() == 0) {
                                flag = false;
                            } else if (d.length() == 0) {
                                flag = false;
                            } else if (E.length() == 0) {
                                flag = false;
                            } else if (sex.length() == 0) {
                                flag = false;
                            } else if (iF.length() == 0) {
                                flag = false;
                            }
                            else{
                                flag = true;
                            }
                            if (flag == false) {
                                Looper.prepare();
                                Toast.makeText(getApplicationContext(), "请输入完整信息",
                                        Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                            else {
                                Log.d("tag",phone);
                                Connection conn = null;
                                int u = 0;
                                conn = (Connection) DBOpenHelper.getConn();
                                String sql="update User set user_name='"+a+"',user_age='"+c+"',user_gender='"+sex+"',user_choice='"+iF+"',user_address='"+E+"',user_phone='"+d+"' where user_phone='"+phone+"'";
                                PreparedStatement pst;
                                try {
                                    pst = (PreparedStatement) conn.prepareStatement(sql);
                                    u = pst.executeUpdate();
                                    pst.close();
                                    conn.close();
                                    Looper.prepare();
                                    Toast.makeText(getApplicationContext(), "修改成功",
                                            Toast.LENGTH_SHORT).show();
                                } catch (SQLException e) {
                                    Log.d("tag",e.toString());
                                    e.printStackTrace();
                                }
                                Intent intent = new Intent();
                                intent.setClass(User_xinxi.this, MainActivity.class);
                                SharedPreferences sharedPreferences=getSharedPreferences("sp",MODE_PRIVATE);
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString("type", "user_xm");//键值对存储
                                editor.putString("user_username", a);//键值对存储
                                editor.commit();
                                startActivity(intent);
                                finish();
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

   }  }  );}}


