package com.example.project_who_yak;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_who_yak.bltboard.BltBoardActivity;
import com.example.project_who_yak.bltboard.notice.Notice;
import com.example.project_who_yak.bltboard.notice.NoticeListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<Notice> noticeList;
    private AlertDialog dialog;

    private ScheduleListAdapter Adapter;
    private List<Schedule> scheduleList;
    private String userID;
    Button btnSearch;
    Button btnScan;
    Button btnSchedule;
    Button btnBoard;
    Button btnUserPage;
    Button btnVoice;

    ListView lv_notice;
    ListView lv_calendar;
    //날짜
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String schedule_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //------- 선언 ----------//
        btnUserPage = findViewById(R.id.btnUserpage);
        btnSearch = findViewById(R.id.btnSearch);
        btnScan = findViewById(R.id.btnScan);
        btnSchedule = findViewById(R.id.btnSchedule);
        btnBoard = findViewById(R.id.btnBoard);
        btnVoice = findViewById(R.id.btnvoice);
        lv_calendar = (ListView) findViewById(R.id.lv_HOMECalendar);
        scheduleList = new ArrayList<Schedule>();
        Adapter = new ScheduleListAdapter(getApplicationContext(), scheduleList);
        lv_calendar.setAdapter(Adapter);
        Intent intentmain = getIntent();
        userID = intentmain.getStringExtra("userID");
        //btnScan.setText(userID);
        //날짜
        schedule_date = getTime();


        lv_notice = findViewById(R.id.lv_HomeNotice);

        //스크린 위에 accessibility가 있는지 확인
        AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(ACCESSIBILITY_SERVICE);
        boolean isScreenReaderEnabled = accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled();

        btnUserPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserpageActivity.class);
                intent.putExtra("userID",userID);
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
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        btnBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BltBoardActivity.class);
                startActivity(intent);
            }
        });

        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //Acc이 켜있을경우
                    if (isScreenReaderEnabled) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                        dialog = builder.setMessage("이미 ACC가 켜져있습니다.")
                                .setNegativeButton("확인", null)
                                .create();
                        dialog.show();
                        //꺼져있을 경우
                    } else {
                        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(intent);
                        startActivityForResult(intent, 0);
                    }
                }
        });

        new HomeActivity.BackgrounTask().execute();
        new BackgroudTaskschedule().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_home);

        //------- 선언 ----------//
        btnUserPage = findViewById(R.id.btnUserpage);
        btnSearch = findViewById(R.id.btnSearch);
        btnScan = findViewById(R.id.btnScan);
        btnSchedule = findViewById(R.id.btnSchedule);
        btnBoard = findViewById(R.id.btnBoard);
        btnVoice = findViewById(R.id.btnvoice);
        lv_calendar = (ListView) findViewById(R.id.lv_HOMECalendar);
        scheduleList = new ArrayList<Schedule>();
        Adapter = new ScheduleListAdapter(getApplicationContext(), scheduleList);
        lv_calendar.setAdapter(Adapter);
        Intent intentmain = getIntent();
        userID = intentmain.getStringExtra("userID");
        //btnScan.setText(userID);
        //날짜
        schedule_date = getTime();


        lv_notice = findViewById(R.id.lv_HomeNotice);

        //스크린 위에 accessibility가 있는지 확인
        AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(ACCESSIBILITY_SERVICE);
        boolean isScreenReaderEnabled = accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled();

        btnUserPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserpageActivity.class);
                intent.putExtra("userID",userID);
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
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        btnBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BltBoardActivity.class);
                startActivity(intent);
            }
        });

        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Acc이 켜있을경우
                if (isScreenReaderEnabled) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    dialog = builder.setMessage("이미 ACC가 켜져있습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    //꺼져있을 경우
                } else {
                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(intent);
                    startActivityForResult(intent, 0);
                }
            }
        });

        new HomeActivity.BackgrounTask().execute();
        new BackgroudTaskschedule().execute();
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }


    //--------공지사항 연동------------//
    class BackgrounTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://whoyak.dothome.co.kr/BltBoardNoticeList.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String noticeNo, noticeTitle, noticeWriter,noticeDate,noticeCategory,noticePopularity;
                noticeList = new ArrayList<Notice>();
                while(count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeNo = object.getString("noticeNo");
                    noticeTitle = object.getString("noticeTitle");
                    noticeWriter = object.getString("noticeWriter");
                    noticeDate = object.getString("noticeDate");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(noticeDate);
                    noticeDate = sdf.format(date);
                    noticeCategory = object.getString("noticeCategory");
                    noticePopularity = object.getString("noticePopularity");
                    Notice notice = new Notice(noticeNo, noticeTitle, noticeWriter, noticeDate, noticeCategory,noticePopularity);
                    noticeList.add(notice);
                    final NoticeListAdapter NoticeAdapter = new NoticeListAdapter(getApplicationContext(), noticeList);
                    lv_notice.setAdapter(NoticeAdapter);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class BackgroudTaskschedule extends AsyncTask<Void, Void, String> {


        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://whoyak.dothome.co.kr/ScheduleList.php?userID=" +URLEncoder.encode(userID,"UTF-8")
                        +"&schedule_date=" +URLEncoder.encode(schedule_date,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result){
            try{
                scheduleList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count=0;
                String schedule_id;
                String scheduleName;
                String scheduledate;
                Adapter.notifyDataSetChanged();
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    schedule_id = object.getString("schedule_id");
                    scheduleName = object.getString("schedule");
                    scheduledate = object.getString("schedule_date");
                    scheduledate= scheduledate.substring(5,scheduledate.length());
                    Schedule schedule = new Schedule(Integer.parseInt(schedule_id),scheduleName,scheduledate);
                    scheduleList.add(schedule);
                    Adapter.notifyDataSetChanged();
                    count++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
