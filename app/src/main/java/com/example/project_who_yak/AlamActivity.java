//package com.example.project_who_yak;
//
//import android.app.TimePickerDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Message;
//import android.view.View;
//import android.widget.Adapter;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.ProgressBar;
//import android.widget.Switch;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.logging.Handler;
//
//public class AlamActivity extends AppCompatActivity {
//    public static final int REQUEST_CODE1 = 1000;
//    public static final int REQUEST_CODE2 = 1001;
//    private AdapterActivity arrayAdapter;
//    private Button tpBtn, removeBtn;
//    private ListView listView;
//    private TextView textView;
//    private int hour, minute;
//    private String month, day, am_pm;
//    private Handler mHandler;
//    private SimpleDateFormat mFormat;
//    private int adapterPosition;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_alam);
//
//        /*스위치를 포함한 커스텀 adapterView 리스트 터치 오류 관련 문제 해결
//        switch.setFocusable(false)
//        switch.setFocusableInTouchMode(false);*/
//
//        arrayAdapter = new AdapterActivity();
//
////        listView = (ListView) findViewById(R.id.list_view);
//        listView.setAdapter(arrayAdapter);
//
//        //List에 있는 항목들 눌렀을 때 시간변경 가능
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                adapterPosition = position;
//                arrayAdapter.removeItem(position);
//                Intent intent = new Intent(AlamActivity.this, TimePickerActivity.class);
//                startActivityForResult(intent,REQUEST_CODE2);
//            }
//        });
//
//        /*long now = System.currentTimeMillis();
//        * Date date = new Date(now);*/
//
//        //쓰레드를 사용해서 실시간으로 시간 출력----------------------------에러
//        mHandler = new Handler() {
//            @Override
//            public void handleMessage(@NonNull Message msg) {   // 실행이 끝난후 확인 가능
//                Calendar cal = Calendar.getInstance();
//
//                mFormat = new SimpleDateFormat("HH:mm:ss");
//                String strTime = mFormat.format(cal.getTime());
//                textView = (TextView) findViewById(R.id.current);
//                textView.setTextSize(30);
//                textView.setText(strTime);
//            }
//        };
//
//        class NewRunnable implements Runnable {
//            @Override
//            public void run() {
//                while(true) {
//                    try {
//                        Thread.sleep(1000);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    mHandler.sendEmptyMessage(0);    // 실행이 끝난후 알림----------------------에러
//                }
//            }
//        }
//
//        NewRunnable runnable = new NewRunnable();
//        Thread thread = new Thread(runnable);
//        thread.start();
//
//        //TimePicker의 시간 셋팅값을 받기 위한 startActivityForResult()
//         tpBtn = (Button) findViewById(R.id.addBtn);
//         tpBtn.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 Intent tpIntent = new Intent(AlamActivity.this,TimePickerActivity.class);
//                 startActivityForResult(tpIntent, REQUEST_CODE1);
//             }
//         });
//
//         removeBtn = (Button) findViewById(R.id.removeBtn);
//         removeBtn.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 arrayAdapter.removeItem();
//                 arrayAdapter.notifyDataSetChanged();
//             }
//         });
//    }
//
//    //TimePicker 셋팅값 받아온 결과를 arrayAdapter에 추가
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //시간 리스트 추가
//        if (requestCode == REQUEST_CODE1 && resultCode == RESULT_OK && data != null) {
//            hour = data.getIntExtra("hour",1);
//            minute = data.getIntExtra("minute",2);
//            am_pm = data.getStringExtra("am_pm");
//            month = data.getStringExtra("month");
//            day = data.getStringExtra("day");
//
//            arrayAdapter.addItem(hour, minute, am_pm, month, day);
//            arrayAdapter.notifyDataSetChanged();
//        }
//        //시간 리스트 터치 시 변경된 시간값 저장
//        if (requestCode == REQUEST_CODE2 && resultCode == RESULT_OK && data != null) {
//            hour = data.getIntExtra("hour",1);
//            minute = data.getIntExtra("minute",2);
//            am_pm = data.getStringExtra("am_pm");
//            month = data.getStringExtra("month");
//            day = data.getStringExtra("day");
//
//            arrayAdapter.addItem(hour, minute, am_pm, month, day);
//            arrayAdapter.notifyDataSetChanged();
//        }
//    }
//}
