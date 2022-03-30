package com.example.project_who_yak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    String userID;
    Button btnSearch;
    Button btnScan;
    Button btnSchedule;
    Button btnBoard;
    Button btnUserPage;
    //Button btnVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnUserPage = findViewById(R.id.btnUserpage);
        btnSearch = findViewById(R.id.btnSearch);
        btnScan = findViewById(R.id.btnScan);
        btnSchedule = findViewById(R.id.btnSchedule);
        btnBoard = findViewById(R.id.btnBoard);
        //btnVoice = findViewById(R.id.btnvoice);
         //Intent intentmain = getIntent();
        //String userID = intentmain.getStringExtra("userID");
        //btnVoice.setText(userID);



        btnUserPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserpageActivity.class);
                //intent.putExtra("userID",userID);
                startActivity(intent);


            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Drug_information.class);
                startActivity(intent);
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), activity_Camara_scan.class);
            startActivity(intent);
            }
        });

        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScheduleActivity.class);
                startActivity(intent);
            }
        });

        btnBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_bltboard.class);
                startActivity(intent);
            }
        });
    }

}
