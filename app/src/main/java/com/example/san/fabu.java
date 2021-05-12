package com.example.san;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextPaint;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class fabu extends AppCompatActivity {
    private List<String> list = new ArrayList<String>();
    private TextView tvShowDialog;
    private TextView lei,getadd;
    private EditText d_miaoshu;
    private EditText d_address;
    private Button fa;
    private Spinner mySpinner;
    private TextView d_t;
    private ArrayAdapter<String> adapter;
    private Calendar cal;
    private int year,month,day;
    RadioGroup rg1;
    RadioButton a1;
    RadioButton a2;
    RadioGroup rg2;
    RadioButton b1;
    RadioButton b2;
    String type="";
    String iF="";
    String Ti="";
    boolean flag;
    String TY;
    String Na;
    String id;
    String Map;
    int ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabu);
        TextView tv = (TextView)findViewById(R.id.xnj);
        TextPaint tp = tv.getPaint();
        tp.setFakeBoldText(true);
        list.add("1小时以内");
        list.add("1-2小时");
        list.add("2-3小时");
        list.add("3-5小时");
        list.add("5小时以上");
        mySpinner = (Spinner)findViewById(R.id.Spinner_time);
        d_t = (TextView)findViewById(R.id.d_t);
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选mySpinner 的值带入myTextView 中*/
                adapter.getItem(arg2);
                /* 将mySpinner 显示*/
                arg0.setVisibility(View.VISIBLE);
                Ti=adapter.getItem(arg2);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                d_t.setText("请选择订单时长");
                arg0.setVisibility(View.VISIBLE);
            }
        });
        /*下拉菜单弹出的内容选项触屏事件处理*/
        mySpinner.setOnTouchListener(new Spinner.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                /**
                 *
                 */
                return false;
            }
        });
        /*下拉菜单弹出的内容选项焦点改变事件处理*/
        mySpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener(){
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

            }
        });
        getDate();

        tvShowDialog=(TextView) findViewById(R.id.d_time);
        getadd=(TextView) findViewById(R.id.getadd);
        lei=(TextView) findViewById(R.id.lei);
        rg1=(RadioGroup)findViewById(R.id.rg4);
        a1=(RadioButton)findViewById(R.id.zhuanye_4);
        a2=(RadioButton)findViewById(R.id.putong_4);
        rg2=(RadioGroup)findViewById(R.id.rg5);
        b1=(RadioButton)findViewById(R.id.yes_5);
        b2=(RadioButton)findViewById(R.id.no_5);
        fa =  findViewById(R.id.fa);
        d_miaoshu =  findViewById(R.id.d_miaoshu);
        d_address =  findViewById(R.id.d_address);
        SharedPreferences sharedPreferences=getSharedPreferences("sp",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        TY=sharedPreferences.getString("TY","");
        Na=sharedPreferences.getString("user_username","");
        id=sharedPreferences.getString("user_id","");
        Map=sharedPreferences.getString("map","");
        d_address.setText(Map);
        ID=Integer.parseInt(id);
        Log.i("wxy","year"+Na+TY);
        lei.setText(TY);
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (a1.getId() == checkedId) {
                    type = a1.getText().toString();
                } else if (a2.getId() == checkedId) {
                    type = a2.getText().toString();
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
        tvShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.d_time:
                        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker arg0, int year, int month, int day) {
                                tvShowDialog.setText(year+"-"+(++month)+"-"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                            }
                        };
                        DatePickerDialog dialog=new DatePickerDialog(fabu.this, DatePickerDialog.THEME_HOLO_LIGHT,listener,year,month,day);//主题在这里！后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                        dialog.show();
                        break;

                    default:
                        break;
                }


            }  }  );
        getadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(fabu.this,Map.class);
                startActivity(intent);
                finish();
            }  }  );

        fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(fabu.this)
                        .setMessage("是否确认发布订单?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String a =tvShowDialog.getText().toString();
                                        String c = d_miaoshu.getText().toString();
                                        String d = d_address.getText().toString();
                                        String e = d_t.getText().toString();
                                        if (a.length() == 0) {
                                            flag = false;
                                        } else if (c.length() == 0) {
                                            flag = false;
                                        } else if (d.length() == 0) {
                                            flag = false;
                                        }else if (e.length() == 0) {
                                            flag = false;
                                        }  else if (type.length() == 0) {
                                            flag = false;
                                        } else if (iF.length() == 0) {
                                            flag = false;
                                        }
                                        else{
                                            flag = true;
                                        }
                                        if (flag == false) {
                                            Looper.prepare();
                                            Toast.makeText(getApplicationContext(), "请输入订单完整信息",
                                                    Toast.LENGTH_SHORT).show();
                                            Looper.loop();
                                        }
                                        else {
                                            Connection conn = null;
                                            int u = 0;
                                            conn =(Connection) DBOpenHelper.getConn();
                                            String sql = "insert into orders (Order_time,Order_type,Order_condition,Order_description,Order_address,Order_yao,Order_starttime,User_id,Order_evaluation) values(?,?,?,?,?,?,?,?,?)";
                                            PreparedStatement pst;
                                            try {
                                                pst = (PreparedStatement) conn.prepareStatement(sql);
                                                //将输入的edit框的值获取并插入到数据库中
                                                pst.setString(1,Ti);
                                                pst.setString(2,TY);
                                                pst.setString(3,"已发布");
                                                pst.setString(4,c);
                                                pst.setString(5,d);
                                                pst.setString(6,type);
                                                pst.setString(7,a);
                                                pst.setInt(8,ID);
                                                pst.setFloat(9,5.00f);
                                                u = pst.executeUpdate();
                                                pst.close();
                                                conn.close();
                                                Looper.prepare();
                                                Intent intent = new Intent();
                                                intent.setClass(fabu.this, U_din.class);
                                                Toast.makeText(getApplicationContext(), "发布完成",
                                                        Toast.LENGTH_SHORT).show();
                                                startActivity(intent);
                                                finish();
                                                Looper.loop();
                                            } catch (SQLException z) {
                                                z.printStackTrace();
                                            }
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

            }  }  );}
    private void getDate() {
        cal= Calendar.getInstance();
        year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        Log.i("wxy","year"+year);
        month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cha_din,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.cha) {
            Intent config = new Intent(this, U_din.class);
            startActivityForResult(config,1);
            finish();
        }
        return true;
    }


}
