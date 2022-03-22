package com.example.project_who_yak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserpageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_profile);


    //home
    Button btnHome;


    btnHome = (Button)findViewById(R.id.btnhome);

    //home Button Listener
        btnHome.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }
    });
}
}
