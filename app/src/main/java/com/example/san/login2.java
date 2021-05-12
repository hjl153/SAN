package com.example.san;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class login2 extends AppCompatActivity {
    private ImageView img_yzm;
    private EditText ed_uname, ed_pwd, ed_yzm;
    private CodeUtils codeUtils;
    private Button btn_cz,Zhuce;
    private ImageButton btn_login;
    private String codeStr, uname, upwd;
    private String name, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);


        initView();
        initData();
    }

    private void initView() {
        img_yzm =  findViewById(R.id.imgy_yzm);
        ed_uname =  findViewById(R.id.y_uname);
        ed_pwd =  findViewById(R.id.y_pwd);
        ed_yzm =  findViewById(R.id.y_yzm);
        btn_login =  findViewById(R.id.btny_login);
        Zhuce=findViewById(R.id.zhucey);
    }

    private void initData() {
//      设置验证码，验证码项目需要SDK降级
        codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
        img_yzm.setImageBitmap(bitmap);
        img_yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeUtils = CodeUtils.getInstance();
                Bitmap bitmap = codeUtils.createBitmap();
                img_yzm.setImageBitmap(bitmap);
                String code = codeUtils.getCode().toLowerCase();
                System.out.println("code------------:" + code);
            }
        });


//        设置登录按钮
        btn_login.setOnClickListener(new View.OnClickListener() {
            String ps="";
            int id=0;
                @Override
                public void onClick(View view) {
                    codeStr = ed_yzm.getText().toString().trim().toLowerCase();
                    uname = ed_uname.getText().toString().trim();
                    upwd = ed_pwd.getText().toString().trim();
                    final  String code = codeUtils.getCode().toLowerCase();
                    if (null == codeStr || TextUtils.isEmpty(codeStr)||!code.equalsIgnoreCase(codeStr)) {
                        Toast.makeText(login2.this, "请输入正确验证码", Toast.LENGTH_SHORT).show();
                    }
//                将验证码变成小写字母

                    System.out.println("code------------:" + code);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Connection conn = null;
                                conn =(Connection) DBOpenHelper.getConn();
                                String sql = "select User_id,User_password from User where User_phone='"+uname+"'";
                                Statement st;
                                st = (Statement) conn.createStatement();
                                ResultSet rs = st.executeQuery(sql);
                                while (rs.next()){
                                    ps=rs.getString("User_password");
                                    id=rs.getInt("User_id");
                                    if (code.equalsIgnoreCase(codeStr)) {
                                        if (!upwd.equals(ps)) {
                                            Looper.prepare();
                                            Toast.makeText(login2.this, "您的密码不正确", Toast.LENGTH_SHORT).show();
                                            Looper.loop();
                                        }
                                        else{
                                            Log.d("tag",String.valueOf(id));
                                            Looper.prepare();
                                            Toast.makeText(login2.this, "登录成功", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent();
                                            intent.setClass(login2.this, MainActivity.class);
                                            SharedPreferences sharedPreferences=getSharedPreferences("sp",MODE_PRIVATE);
                                            SharedPreferences.Editor editor=sharedPreferences.edit();
                                            editor.putString("user_username", uname);//键值对存储
                                            editor.putString("user_phone", uname);//键值对存储
                                            editor.putString("user_id", String.valueOf(id));//键值对存储
                                            editor.putString("type", "user");//键值对存储
                                            editor.commit();
                                            startActivity(intent);
                                            finish();
                                            Looper.loop();
                                        }
                                    }
                                }
                                st.close();
                                conn.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
            }
        });
        Zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(login2.this, zhuce2.class);
                startActivity(intent);
            }
        });


    }
}
