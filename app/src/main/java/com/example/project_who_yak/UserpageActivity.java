package com.example.project_who_yak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class UserpageActivity extends AppCompatActivity {


    //Variable declaration FragmentManager, Fragment page
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Fragment_Userpage_profile Frag_Userpage_profile;
    private Fragment_Userpage_manual Frag_Userpage_manual;
    private Fragment_Userpage_setting Frag_Userpage_setting;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);



        //varivable btn
        Button btnHome;
        Button btnProfile;
        Button btnManual;
        Button btnSetting;

        //connect btn on layout
        btnHome = (Button)findViewById(R.id.btnhome);
        btnProfile = (Button)findViewById(R.id.btnprofile);
        btnManual = (Button)findViewById(R.id.btnmanual);
        btnSetting = (Button)findViewById(R.id.btnsetting);

        fragmentManager = getSupportFragmentManager();


        //Fragment page variable
        Frag_Userpage_profile = new Fragment_Userpage_profile();
        Frag_Userpage_manual= new Fragment_Userpage_manual();
        Frag_Userpage_setting= new Fragment_Userpage_setting();

        //Default fragment
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.FarmeLayout_Userpage, Frag_Userpage_profile).commitAllowingStateLoss();


        //home Button Listener
        btnHome.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }
    });

        //profile Button Listener
        btnProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FarmeLayout_Userpage, Frag_Userpage_profile).commitAllowingStateLoss();
            }
        });

        //manual Button Listener
        btnManual.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FarmeLayout_Userpage, Frag_Userpage_manual).commitAllowingStateLoss();
            }
        });

        //setting Button Listener
        btnSetting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FarmeLayout_Userpage, Frag_Userpage_setting).commitAllowingStateLoss();
            }
        });

}
}
