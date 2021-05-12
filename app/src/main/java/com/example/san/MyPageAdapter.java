package com.example.san;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPageAdapter extends FragmentPagerAdapter {
    public MyPageAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new zhuye();
        }else if(position==1){
            return new zixun();
        }else{
            return new shangcheng();
        }
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "首页";
        }else if(position==1){
            return "资讯";
        }else{
            return "商城";
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
