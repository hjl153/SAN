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
public class volunteer_xinxi extends AppCompatActivity {
    private EditText tv_name;
    private EditText tv_dianwei;
    private EditText tv_dianhua;
    private EditText tv_youxiang;
    private EditText tv_miaoshu;
    private Button baocun;
    RadioGroup rg1;
    RadioButton a1;
    RadioButton a2;
    RadioGroup rg3;
    RadioButton c1;
    RadioButton c2;
    String sex="";
    String type="";
    boolean flag;
    String phone;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_xinxi);
        rg1=(RadioGroup)findViewById(R.id.Rg1);
        a1=(RadioButton)findViewById(R.id.Nan);
        a2=(RadioButton)findViewById(R.id.Nv);
        rg3=(RadioGroup)findViewById(R.id.Rg3);
        c1=(RadioButton)findViewById(R.id.zhuanye);
        c2=(RadioButton)findViewById(R.id.putong);
        tv_miaoshu=  findViewById(R.id.say);
        baocun=findViewById(R.id.baocun);
        tv_name =  findViewById(R.id.tv_name2);
        tv_dianwei =  findViewById(R.id.tv_dianwei2);
        tv_dianhua =  findViewById(R.id.tv_dianhua2);
        tv_youxiang =  findViewById(R.id.tv_youxiang2);
        SharedPreferences sharedPreferences=getSharedPreferences("sp",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        phone=sharedPreferences.getString("volunteer_phone","");
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
        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (c1.getId() == checkedId) {
                    type = c1.getText().toString();
                } else if (c2.getId() == checkedId) {
                    type = c2.getText().toString();
                }
            }
        });


        baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(volunteer_xinxi.this)
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
                        String f = tv_miaoshu.getText().toString();
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
                        } else if (f.length() == 0) {
                            flag = false;
                        }else if (type.length() == 0) {
                            flag = false;
                        }
                        else{
                            flag = true;
                        }
                        if (flag ==false) {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "请输入完整信息",
                                    Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else {
                            Log.d("tag",a);
                            Connection conn = null;
                            int u = 0;
                            conn = (Connection) DBOpenHelper.getConn();
                            String sql="update Volunteer set Volunteer_name='"+a+"',Volunteer_age='"+c+"',Volunteer_gender='"+sex+"',Volunteer_skill='"+f+"',Volunteer_address='"+E+"',Volunteer_phone='"+d+"',Volunteer_type='"+type+"' where Volunteer_phone='"+phone+"'";
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
                            intent.setClass(volunteer_xinxi.this, MainActivity.class);
                            SharedPreferences sharedPreferences=getSharedPreferences("sp",MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("type", "volunteer_xm");//键值对存储
                            editor.putString("volunteer_username", a);//键值对存储
                            editor.putString("you", type);//键值对存储
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


