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

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
/**
 * Created by siner on 2017/6/30.
 */
public class zhuce2 extends Activity implements OnClickListener{
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
        setContentView(R.layout.activity_zhuce2);
       // SpeechUtility.createUtility(this, SpeechConstant.APPID +"9affef2a");
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
     * ??????Handler?????????Message????????????????????????????????????
     */
    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -1) {
                //?????????????????????????????????  i ???60??????????????????
                btn_getCord.setText( i+" s");
            } else if (msg.what == -2) {
                //????????????????????????????????????????????????
                btn_getCord.setText("????????????");
                btn_getCord.setClickable(true);
                i = 60;
            } else {
            int event=msg.arg1;
            int result=msg.arg2;
            Object data=msg.obj;
            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                if(result == SMSSDK.RESULT_COMPLETE) {
                    boolean smart = (Boolean)data;
                    if(smart) {
                        Toast.makeText(getApplicationContext(),"?????????????????????????????????????????????",
                                Toast.LENGTH_SHORT).show();
                        edit_phone.requestFocus();
                        return;
                    }
                }
            }
            if(result==SMSSDK.RESULT_COMPLETE)
            {

                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    Log.d("tag","xcnoancn");
                }
            }
            else
            {
                if(flag)
                {
                    btn_getCord.setVisibility(View.VISIBLE);
                    //Toast.makeText(getApplicationContext(),"????????????????????????????????????", Toast.LENGTH_SHORT).show();
                    edit_phone.requestFocus();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"?????????????????????", Toast.LENGTH_SHORT).show();
                }
            }

        }}

    };
    /**
     * ??????id
     */
    private void getId()
    {
        edit_phone=findViewById(R.id.edit2_phone);
        edit_cord=findViewById(R.id.edit2_code);
        btn_getCord=findViewById(R.id.btn2_getcord);
        btn_register=findViewById(R.id.btn2_register2);
        pass=findViewById(R.id.pass2);
        apass=findViewById(R.id.apass2);
        btn_getCord.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }
    /**
     * ??????????????????
     */
    boolean a;
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn2_getcord:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        a=true;
                        Log.d("tag","?????????");
                        try {
                            Connection conn = null;
                            conn =(Connection) DBOpenHelper.getConn();
                            String sql = "select User_phone from User";
                            Statement st;
                            st = (Statement) conn.createStatement();
                            ResultSet rs = st.executeQuery(sql);
                            while (rs.next()){
                                String ps=rs.getString("User_phone");
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
                if(judPhone())//?????????????????????????????????
                {
                    SMSSDK.getVerificationCode("86",phone_number);
                    edit_cord.requestFocus();
                    btn_getCord.setClickable(false);
                    edit_cord.requestFocus();
                    Toast.makeText(getApplicationContext(), "?????????????????????",
                            Toast.LENGTH_SHORT).show();
                    Log.d("tag","dnsfca");
                }
                //???????????????
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
                        //?????????????????????
                        handler.sendEmptyMessage(-2);
                    }
                }).start();
                break;}
                else{
                    Toast.makeText(getApplicationContext(), "????????????????????????????????????????????????",
                            Toast.LENGTH_SHORT).show();
                    edit_cord.requestFocus();
                    break;
                }
            case R.id.btn2_register2:
                new AlertDialog.Builder(zhuce2.this)
                        .setMessage("???????????????????")
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
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
                        String sql = "insert into User (User_phone,User_password) values(?,?)";
                        PreparedStatement pst;
                        ResultSet rs = null ;
                        try {
                            pst = (PreparedStatement) conn.prepareStatement(sql);
                            //????????????edit???????????????????????????????????????
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
                        intent.setClass(zhuce2.this, login2.class);
                        Toast.makeText(getApplicationContext(), "????????????",
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
                        .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }).create().show();

            default:
                break;
        }
    }
    private boolean judPhone()
    { if(TextUtils.isEmpty(edit_phone.getText().toString().trim()))
        {
            Toast.makeText(zhuce2.this,"???????????????????????????",Toast.LENGTH_SHORT).show();
            edit_phone.requestFocus();
            return false;
        }
        else if(edit_phone.getText().toString().trim().length()!=11)
        {
            Toast.makeText(zhuce2.this,"?????????????????????????????????",Toast.LENGTH_SHORT).show();
            edit_phone.requestFocus();
            return false;
        }
        else
        {   Log.d("tag","m");
            phone_number=edit_phone.getText().toString().trim();
                return true;

            }
        }
    private boolean judCord()
    {
        judPhone();
        if(TextUtils.isEmpty(edit_cord.getText().toString().trim()))
        {
            Toast.makeText(zhuce2.this,"????????????????????????",Toast.LENGTH_SHORT).show();
            edit_cord.requestFocus();
            return false;
        }
        else if(edit_cord.getText().toString().trim().length()!=6)
        {
            Toast.makeText(zhuce2.this,"??????????????????????????????",Toast.LENGTH_SHORT).show();
            edit_cord.requestFocus();

            return false;
        }
        else if(!pass.getText().toString().trim().equals(apass.getText().toString().trim()))
        {
            Toast.makeText(zhuce2.this,"???????????????????????????",Toast.LENGTH_SHORT).show();
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
