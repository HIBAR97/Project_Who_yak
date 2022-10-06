package com.example.project_who_yak;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimePickerActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private Button okBtn, cancelBtn;
    private int hour, minute;
    private String am_pm;
    private Date currentTime;
    private String stMonth, stDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        timePicker = (TimePicker) findViewById(R.id.time_picker);

        currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat day = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat month = new SimpleDateFormat("MM", Locale.getDefault());

        stMonth = month.format(currentTime);
        stDay = day.format(currentTime);

        okBtn = (Button) findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            //안드로이드 버전별로 시간값 세팅을 다르게 해주어야 함. 여기선 API 23
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                }
                else {
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }

                am_pm = AM_PM(hour);
                hour = timeSet(hour);

                Intent sendIntent = new Intent(TimePickerActivity.this, AlamActivity.class);  //알람 액티비티를 주석 처리 하니 에러가 나서 주석 처리

                sendIntent.putExtra("hour",hour);
                sendIntent.putExtra("minute",minute);
                sendIntent.putExtra("am_pm",am_pm);
                sendIntent.putExtra("month",stMonth);
                sendIntent.putExtra("day",stDay);
                setResult(RESULT_OK, sendIntent);

                finish();
            }
        });
        //취소버튼 누를 시 TimePickerActivity 종료
        cancelBtn = (Button) findViewById(R.id.cancleBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    //24시 시간제 바꿔줌
    private int timeSet(int hour) {
        if(hour > 12) {
            hour -=12;
        }
        return hour;
    }
    //오전, 오후 선택
    private String AM_PM(int hour) {
        if (hour >= 12) {
            am_pm = "오후";
        }
        else {
            am_pm = "오전";
        }
        return am_pm;
    }
}
