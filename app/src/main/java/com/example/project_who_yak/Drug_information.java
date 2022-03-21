package com.example.project_who_yak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Drug_information extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Fragment_Drug_info Frag_Drug_info;
    private Fragment_Drug_detail Frag_Drug_detail;
    private Fragment_Drug_side_effect Frag_Drug_side_effect;
    private Fragment_Drug_precaution Frag_Drug_precaution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //화면 이동
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drug_information);

        Button btn_Drug_info;
        Button btn_Drug_detail;
        Button btn_Drug_side_effect;
        Button btn_Drug_precaution;

        btn_Drug_info = (Button)findViewById(R.id.Btn_Drug_info);
        btn_Drug_detail = (Button)findViewById(R.id.Btn_Drug_detail);
        btn_Drug_side_effect = (Button)findViewById(R.id.Btn_Drug_side_effect);
        btn_Drug_precaution = (Button)findViewById(R.id.Btn_Drug_precaution);

        fragmentManager = getSupportFragmentManager();

        Frag_Drug_info = new Fragment_Drug_info();
        Frag_Drug_detail = new Fragment_Drug_detail();
        Frag_Drug_side_effect = new Fragment_Drug_side_effect();
        Frag_Drug_precaution = new Fragment_Drug_precaution();


        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.FrameLayout_Drug, Frag_Drug_info).commitAllowingStateLoss();

        btn_Drug_info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FrameLayout_Drug, Frag_Drug_info).commitAllowingStateLoss();
            }
        });

        btn_Drug_detail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FrameLayout_Drug, Frag_Drug_detail).commitAllowingStateLoss();
            }
        });

        btn_Drug_side_effect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FrameLayout_Drug, Frag_Drug_side_effect).commitAllowingStateLoss();
            }
        });

        btn_Drug_precaution.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FrameLayout_Drug, Frag_Drug_precaution).commitAllowingStateLoss();
            }
        });
    }
}
