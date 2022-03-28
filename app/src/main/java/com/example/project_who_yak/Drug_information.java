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

    //Variable declaration FragmentManager, Fragment page
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Fragment_Drug_info Frag_Drug_info;
    private Fragment_Drug_detail Frag_Drug_detail;
    private Fragment_Drug_side_effect Frag_Drug_side_effect;
    private Fragment_Drug_precaution Frag_Drug_precaution;

    // When activity started
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //화면 이동
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drug_information);

        //Varivable btn
        Button btn_Drug_info_home;
        Button btn_Drug_info;
        Button btn_Drug_detail;
        Button btn_Drug_side_effect;
        Button btn_Drug_precaution;

        //Conect btn on layout
        btn_Drug_info_home = (Button)findViewById(R.id.Btn_Drug_info_Home);
        btn_Drug_info = (Button)findViewById(R.id.Btn_Drug_info);
        btn_Drug_detail = (Button)findViewById(R.id.Btn_Drug_detail);
        btn_Drug_side_effect = (Button)findViewById(R.id.Btn_Drug_side_effect);
        btn_Drug_precaution = (Button)findViewById(R.id.Btn_Drug_precaution);

        fragmentManager = getSupportFragmentManager();

        //Fragment page variable
        Frag_Drug_info = new Fragment_Drug_info();
        Frag_Drug_detail = new Fragment_Drug_detail();
        Frag_Drug_side_effect = new Fragment_Drug_side_effect();
        Frag_Drug_precaution = new Fragment_Drug_precaution();

        //Default fragment
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.FrameLayout_Drug, Frag_Drug_info).commitAllowingStateLoss();

        //btn Listener
        btn_Drug_info_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                startActivity(intent);
            }
        });

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
