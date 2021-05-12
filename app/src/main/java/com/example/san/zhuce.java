package com.example.san;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import java.sql.*;
/**
 * Created by siner on 2017/6/30.
 */

public class zhuce extends Activity implements OnClickListener{

    private EditText edit_phone;
    private EditText edit_cord;
    private EditText pass;
    private EditText apass;
    private TextView now;
    private Button btn_getCord;
    private ImageButton btn_register;
    private String phone_number;
    private String cord_number;
    EventHandler eventHandler;
    private int i=60;
    private boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);

        getId();


        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg=new Message();
                msg.arg1=event;
                msg.arg2=result;
                msg.obj=data;
                handler.sendMessage(msg);
            }
        };

        SMSSDK.registerEventHandler(eventHandler);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    /**
     * 使用Handler来分发Message对象到主线程中，处理事件
     */
    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -1) {
                //修改控件文本进行倒计时  i 以60秒倒计时为例
                btn_getCord.setText( i+" s");
            } else if (msg.what == -2) {
                //修改控件文本，进行重新发送验证码
                btn_getCord.setText("重新发送");
                btn_getCord.setClickable(true);
                i = 60;
            }
            else {
                int event=msg.arg1;
                int result=msg.arg2;
                Object data=msg.obj;
            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                if(result == SMSSDK.RESULT_COMPLETE) {
                    boolean smart = (Boolean)data;
                    if(smart) {
                        Toast.makeText(getApplicationContext(),"该手机号已经注册过，请重新输入",
                                Toast.LENGTH_SHORT).show();
                        edit_phone.requestFocus();
                        return;
                    }

                }
            }
            if(result==SMSSDK.RESULT_COMPLETE)
            {

                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {

                }
            }
            else
            {
                if(flag)
                {
                    btn_getCord.setVisibility(View.VISIBLE);
                    //Toast.makeText(getApplicationContext(),"验证码获取失败请重新获取", Toast.LENGTH_SHORT).show();
                    edit_phone.requestFocus();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"验证码输入错误", Toast.LENGTH_SHORT).show();
                }
            }

        }}

    };

    /**
     * 获取id
     */
    private void getId()
    {
        edit_phone=findViewById(R.id.edit_phone);
        edit_cord=findViewById(R.id.edit_code);
        btn_getCord=findViewById(R.id.btn_getcord);
        btn_register=findViewById(R.id.btn_register2);
        pass=findViewById(R.id.pass);
        apass=findViewById(R.id.apass);
        btn_getCord.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    /**
     * 按钮点击事件
     */
    boolean a;

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_getcord:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        a=true;
                        try {
                            Connection conn = null;
                            conn =(Connection) DBOpenHelper.getConn();
                            String sql = "select Volunteer_phone from Volunteer";
                            Statement st;
                            st = (Statement) conn.createStatement();
                            ResultSet rs = st.executeQuery(sql);
                            while (rs.next()){
                                String ps=rs.getString("Volunteer_phone");
                                if(edit_phone.getText().toString().equals(ps)){
                                    Message msg2 = new Message ();
                                    msg2.what = -3;
                                    msg2.obj =false;
                                    handler.sendMessage (msg2);
                                    a=false;
                                    Log.d("tag",ps+edit_phone.getText());
                                }
                            }
                            st.close();
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                if(a){
                if(judPhone())//去掉左右空格获取字符串
                {
                    SMSSDK.getVerificationCode("86",phone_number);
                    btn_getCord.setClickable(false);
                    edit_cord.requestFocus();

                    //开始倒计时
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (; i > 0; i--) {
                                handler.sendEmptyMessage(-1);
                                if (i <= 0) {
                                    break;
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            //倒计时结束执行
                            handler.sendEmptyMessage(-2);
                        }
                    }).start();
                }
                break;}
                else{
                    Toast.makeText(getApplicationContext(), "该手机号已注册，请更换注册手机号",
                            Toast.LENGTH_SHORT).show();
                    edit_cord.requestFocus();
                    break;
                }

            case R.id.btn_register2:
                new AlertDialog.Builder(zhuce.this)
                        .setMessage("确定要注册么?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if(judCord()){
                                    SMSSDK.submitVerificationCode("86",phone_number,cord_number);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d("tag","xm");
                                            Connection conn = null;
                                            int u = 0;
                                            conn =(Connection) DBOpenHelper.getConn();
                                            String sql = "insert into Volunteer (Volunteer_phone,Volunteer_password) values(?,?)";
                                            PreparedStatement pst;
                                            try {
                                                pst = (PreparedStatement) conn.prepareStatement(sql);
                                                //将输入的edit框的值获取并插入到数据库中
                                                pst.setString(1,edit_phone.getText().toString());
                                                pst.setString(2,apass.getText().toString());
                                                u = pst.executeUpdate();
                                                pst.close();
                                                conn.close();
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                            Looper.prepare();
                                            Intent intent = new Intent();
                                            intent.setClass(zhuce.this, login.class);
                                            Toast.makeText(getApplicationContext(), "注册成功",
                                                    Toast.LENGTH_SHORT).show();
                                            startActivity(intent);
                                            finish();
                                            Looper.loop();
                                        }
                                    }).start();

                                }
                                else{
                                    flag=false;}
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }).create().show();

            break;
            default:
                break;
        }
    }

    private boolean judPhone()
    {


        if(TextUtils.isEmpty(edit_phone.getText().toString().trim()))
        {
            Toast.makeText(zhuce.this,"请输入您的电话号码",Toast.LENGTH_SHORT).show();
            edit_phone.requestFocus();
            return false;
        }
        else if(edit_phone.getText().toString().trim().length()!=11)
        {
            Toast.makeText(zhuce.this,"您的电话号码位数不正确",Toast.LENGTH_SHORT).show();
            edit_phone.requestFocus();
            return false;
        }
        else
        {
            phone_number=edit_phone.getText().toString().trim();
            Log.d("tag","buibn");
            return true;
        }
    }

    private boolean judCord()
    {
        judPhone();
        if(TextUtils.isEmpty(edit_cord.getText().toString().trim()))
        {
            Toast.makeText(zhuce.this,"请输入您的验证码",Toast.LENGTH_SHORT).show();
            edit_cord.requestFocus();
            return false;
        }
        else if(edit_cord.getText().toString().trim().length()!=6)
        {
            Toast.makeText(zhuce.this,"您的验证码位数不正确",Toast.LENGTH_SHORT).show();
            edit_cord.requestFocus();

            return false;
        }
        else if(!pass.getText().toString().trim().equals(apass.getText().toString().trim()))
        {
            Toast.makeText(zhuce.this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
            String a=pass.getText().toString().trim();
            Log.d("tag",a);
            return false;
        }
        else
        {
            cord_number=edit_cord.getText().toString().trim();
            return true;
        }

    }



}

