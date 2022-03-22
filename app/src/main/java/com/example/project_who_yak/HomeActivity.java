package com.example.project_who_yak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //userpage
        Button btnUserpage;

        btnUserpage = (Button)findViewById(R.id.btnUserpage);

        //userpage Button Listener
        btnUserpage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserpageActivity.class);
                startActivity(intent);
            }
        });


    }
}
