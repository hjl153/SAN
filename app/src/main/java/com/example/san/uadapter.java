package com.example.san;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.san.R;

import java.util.List;
import java.util.Map;

public class uadapter extends ArrayAdapter {
    public uadapter(@NonNull Context context, int resource, List<Map<String, Object>> list) {
        super(context, resource,list);
    }
    public View getView(int position, View contentView, ViewGroup parent){
        View itemView=contentView;
        if(itemView==null){
            itemView= LayoutInflater.from(getContext()).inflate(R.layout.activity_u_dinlist,parent,false);
        }
        Map<String,String> map= (Map<String, String>) getItem(position);
        Log.i("wxy",map.get("list_yao"));
        TextView list_time=itemView.findViewById(R.id.u_list1);
        TextView list_yao=itemView.findViewById(R.id.u_list2);
        TextView list_zhuangtai=itemView.findViewById(R.id.u_list3);
        TextView list_ob=itemView.findViewById(R.id.u_list4);
        TextView list_long=itemView.findViewById(R.id.u_list5);
        TextView list_address=itemView.findViewById(R.id.u_list6);
        TextView list_lei=itemView.findViewById(R.id.u_list7);
        TextView list_people=itemView.findViewById(R.id.u_people);
        TextView list_score=itemView.findViewById(R.id.u_score);
        list_time.setText(String.valueOf(map.get("list_time")));
        list_yao.setText(map.get("list_yao"));
        list_zhuangtai.setText(map.get("list_zhuangtai"));
        list_ob.setText(map.get("list_ob"));
        list_long.setText(map.get("list_long"));
        list_address.setText(map.get("list_address"));
        list_lei.setText(map.get("list_lei"));
        list_people.setText(map.get("list_people"));
        list_score.setText(map.get("list_score"));
        return itemView;
    }

}
