package com.example.san;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.PushService;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext(), PushService.class);
        // com.getui.demo.com.example.san.DemoIntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);
        SharedPreferences sharedPreferences=getSharedPreferences("sp",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String volunteer_name=sharedPreferences.getString("volunteer_username","");
        String user_name=sharedPreferences.getString("user_username","");
        String type=sharedPreferences.getString("type","");
        if (volunteer_name.length()==0&&user_name.length()==0) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, choice.class);
            startActivity(intent);
            finish();
        }
        else if(type.equals("user")){
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("请填写资料")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, User_xinxi.class);
            startActivity(intent);}})
                                    .create().show();
        }
        else if(type.equals("volunteer")){
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("请填写资料")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

            Intent intent = new Intent();
            intent.setClass(MainActivity.this, volunteer_xinxi.class);
            startActivity(intent);}})
                                    .create().show();
        }
        else {
            setContentView(R.layout.activity_main);
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            MyPageAdapter pageAdapter = new MyPageAdapter(getSupportFragmentManager());
            viewPager.setAdapter(pageAdapter);
            TabLayout tabLayout = findViewById(R.id.sliding_tabs);
            tabLayout.setupWithViewPager(viewPager);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.yonghu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.see) {
            Intent config = new Intent(this, Zhongxin.class);
            startActivity(config);
        }
        return true;
    }
}
