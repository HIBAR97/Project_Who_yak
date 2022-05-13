package com.example.project_who_yak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class activity_bltboard extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private fragment_bltboard fragment_bltboard;
    private fragment_wrtboard fragment_wrtboard;
    private fragment_rmbboard fragment_rmbboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bltboard);


        Button btn_blt_home;
        Button btn_bltboard;
        Button btn_wrtboard;
        Button btn_rmbboard;

        btn_blt_home = (Button)findViewById(R.id.btn_blt_home);
        btn_bltboard = (Button)findViewById(R.id.blt_brd);
        btn_wrtboard = (Button)findViewById(R.id.blt_wrt);
        btn_rmbboard = (Button)findViewById(R.id.blt_rmb);

        fragmentManager = getSupportFragmentManager();

        fragment_bltboard = new fragment_bltboard();
        fragment_rmbboard = new fragment_rmbboard();
        fragment_wrtboard = new fragment_wrtboard();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.FrameLayout_bltscreen, fragment_bltboard).commitAllowingStateLoss();

        //----리스너 파트----//
        btn_blt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        btn_bltboard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FrameLayout_bltscreen, fragment_bltboard).commitAllowingStateLoss();
            }
        });

        btn_rmbboard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FrameLayout_bltscreen, fragment_rmbboard).commitAllowingStateLoss();
            }
        });

        btn_wrtboard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FrameLayout_bltscreen, fragment_wrtboard).commitAllowingStateLoss();
            }
        });

    }


}