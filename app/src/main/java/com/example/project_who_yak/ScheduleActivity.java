package com.example.project_who_yak;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ScheduleActivity extends AppCompatActivity {
    CalendarView calendarView;
    Button btnvoice;
    Button btnhome;
    ListView lvCal;
    ListView lvCal2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        btnvoice = findViewById(R.id.btnvoice);
        btnhome = findViewById(R.id.btnhome);


        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                setContentView(R.layout.activity_home);
            }
        });
    }
}
