package com.example.san;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.san.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.san.R;

import java.time.Month;
import java.time.Year;

import static com.google.android.gms.common.internal.ImagesContract.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class zhuye extends Fragment {
    String T="";
    GlideRoundTransform GlideRoundTransform;
    public zhuye() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.zhuye_fragment, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageButton button1 = (ImageButton) getActivity().findViewById(R.id.type1);
        ImageButton button2 = (ImageButton) getActivity().findViewById(R.id.type2);
        ImageButton button3 = (ImageButton) getActivity().findViewById(R.id.type3);
        ImageButton button4 = (ImageButton) getActivity().findViewById(R.id.type4);
        TextView del = (TextView) getActivity().findViewById(R.id.del);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        T=sharedPreferences.getString("type","");
        RequestOptions requestOptions = new RequestOptions();
        //requestOptions.placeholder(R.drawable.ic_launcher_background);
        requestOptions.circleCropTransform();
        requestOptions.transforms( new RoundedCorners(50));
        Glide.with(getActivity()).load(R.mipmap.x4) //图片地址
                .apply(requestOptions)
                .into(button1);
        Glide.with(getActivity()).load(R.mipmap.x3) //图片地址
                .apply(requestOptions)
                .into(button2);
        Glide.with(getActivity()).load(R.mipmap.x2) //图片地址
                .apply(requestOptions)
                .into(button3);
        Glide.with(getActivity()).load(R.mipmap.x1) //图片地址
                .apply(requestOptions)
                .into(button4);
        ImageView v=(ImageView) getActivity().findViewById(R.id.tit);
        Glide.with(getActivity()).load(R.mipmap.shou) //图片地址
                .apply(requestOptions)
                .into(v);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(T.equals("user_xm")){
                    SharedPreferences sharedPreferences= getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("TY", "生活照料");//键值对存储
                    editor.commit();
                Intent config = new Intent(getActivity(), shenghuo.class);
                startActivity(config);}
                else if (T.equals("volunteer_xm")){
                    SharedPreferences sharedPreferences= getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("TY", "生活照料");//键值对存储
                    editor.commit();
                    Intent config = new Intent(getActivity(),Dingdan.class);
                    startActivity(config);
                }

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(T.equals("user_xm")){
                    SharedPreferences sharedPreferences= getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("TY", "医疗护理");//键值对存储
                    editor.commit();
                    Intent config = new Intent(getActivity(), yiliao.class);
                    startActivity(config);}
                else if (T.equals("volunteer_xm")){
                    SharedPreferences sharedPreferences= getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("TY", "医疗护理");//键值对存储
                    editor.commit();
                    Intent config = new Intent(getActivity(), Dingdan.class);
                    startActivity(config);
                }

            }


        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(T.equals("user_xm")){
                    SharedPreferences sharedPreferences= getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("TY", "文娱社交");//
                    editor.commit();
                    Intent config = new Intent(getActivity(), wenyu.class);
                    startActivity(config);}
                else if (T.equals("volunteer_xm")){
                    SharedPreferences sharedPreferences= getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("TY", "文娱社交");//
                    editor.commit();
                    Intent config = new Intent(getActivity(), Dingdan.class);
                    startActivity(config);
                }

            }


        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(T.equals("user_xm")){
                    SharedPreferences sharedPreferences= getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("TY", "志愿服务");//键值对存储
                    editor.commit();
                    Intent config = new Intent(getActivity(), zhiyuan.class);
                    startActivity(config);}
                else if (T.equals("volunteer_xm")){SharedPreferences sharedPreferences= getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("TY", "志愿服务");//键值对存储
                    editor.commit();
                    Intent config = new Intent(getActivity(), Dingdan.class);
                    startActivity(config);}

            }


        });
        del.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View arg0) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("提示");
                builder.setMessage("请拨打客服电话 13731891296");
                builder.setPositiveButton("点击拨号", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +"13731891296"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }

        });
    }}
