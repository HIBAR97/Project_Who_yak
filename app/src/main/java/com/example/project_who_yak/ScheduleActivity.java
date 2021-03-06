package com.example.project_who_yak;

import static android.os.SystemClock.sleep;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private MaterialCalendarView calendarView;
    private ListView scheduleListView;
    private ScheduleListAdapter Adapter;
    public List<Schedule> scheduleList;
    public String select_schedule;
    Button btnvoice;
    Button btnhome;
    Button btnadd;
    Button btndel;
    Button btnmod;
    TextView tv_today;
    EditText contextEditText;
    String userID= "test1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        btnvoice = findViewById(R.id.btnvoice);
        btnhome = findViewById(R.id.btnhome);
        btnadd = findViewById(R.id.btnAdd);
        btnmod = findViewById(R.id.btnFix);
        btndel = findViewById(R.id.btnDel);

        scheduleListView = (ListView) findViewById(R.id.lvCal2);
        scheduleList = new ArrayList<Schedule>();
        Adapter = new ScheduleListAdapter(getApplicationContext(), scheduleList);
        scheduleListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        scheduleListView.setAdapter(Adapter);

        tv_today = findViewById(R.id.tv_today);
        contextEditText = findViewById(R.id.contextEditText);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setSelectedDate(CalendarDay.today());
        //달력에 점 나타내기
        calendarView.addDecorator(new EventDecorator(Color.RED, calendarView.getSelectedDates()));
        //달력에 토,일 색상 넣기
        calendarView.addDecorators(new SaturdayDecorator(),new SundayDecorator());


        //달력에 날짜 보이기
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
//              tv_today.setText(calendarView.getSelectedDates().toString());\
                String[] array=date.toString().substring(12, date.toString().length()-1).split("-");
                int month = Integer.parseInt(array[1])+1 ;
                String date2 = array[0]+"-"+ month + "-" +array[2];
                tv_today.setText(date2); //2022-xx-xx-xx

            }
        });

        //홈으로 가는 버튼
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                setContentView(R.layout.activity_home);
            }
        });

        scheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                select_schedule = scheduleList.get(i).getschedule();

            }
        });


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addSchedule = contextEditText.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                Toast.makeText(getApplicationContext(),"일정을 추가했습니다.",Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"일정 추가에 실패했습니다.",Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(),"json에러",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                };
                ScheduleAddRequest scheduleAddRequest = new ScheduleAddRequest(userID, addSchedule, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ScheduleActivity.this);
                queue.add(scheduleAddRequest);

                sleep(200);
                new BackgroudTaskschedule().execute();
            }
        });



        btnmod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String before_schedule = select_schedule.toString();
                String after_schedule = contextEditText.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                Toast.makeText(getApplicationContext(),"일정을 수정했습니다.",Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"일정 스장에 실패했습니다.",Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(),"json에러",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                };
                ScheduleModRequest scheduleModRequestt = new ScheduleModRequest(after_schedule,userID, before_schedule, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ScheduleActivity.this);
                queue.add(scheduleModRequestt);

                sleep(200);
                new BackgroudTaskschedule().execute();
            }
        });



        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                Toast.makeText(getApplicationContext(),"일정을 삭제했습니다.",Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"일정 삭제에 실패했습니다.",Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(),"json에러",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                };
                ScheduleDelRequest scheduleDelRequest = new ScheduleDelRequest(userID, select_schedule, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ScheduleActivity.this);
                queue.add(scheduleDelRequest);
                sleep(200);
                new BackgroudTaskschedule().execute();

            }
        });




        // 첫 시작 요일이 일요일이 되도록 설정
        calendarView.state()
                .edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .commit();

        // 월, 요일을 한글로 보이게 설정 (MonthArrayTitleFormatter의 작동을 확인하려면 밑의 setTitleFormatter()를 지운다)
        calendarView.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
        calendarView.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));

        // 좌우 화살표 사이 연, 월의 폰트 스타일 설정
        calendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader);

        // 요일 선택 시 내가 정의한 드로어블이 적용되도록 함
        calendarView.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
                // 아래 로그를 통해 시작일, 종료일이 어떻게 찍히는지 확인하고 본인이 필요한 방식에 따라 바꿔 사용한다
                // UTC 시간을 구하려는 경우 이 라이브러리에서 제공하지 않으니 별도의 로직을 짜서 만들어내 써야 한다
                String startDay = dates.get(0).getDate().toString();
                String endDay = dates.get(dates.size() - 1).getDate().toString();
                Log.e(TAG, "시작일 : " + startDay + ", 종료일 : " + endDay);
            }
        });

        // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
        calendarView.addDecorators(new DayDecorator(this));

        // 좌우 화살표 가운데의 연/월이 보이는 방식 커스텀
        calendarView.setTitleFormatter(new  TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                // CalendarDay라는 클래스는 LocalDate 클래스를 기반으로 만들어진 클래스다
                // 때문에 MaterialCalendarView에서 연/월 보여주기를 커스텀하려면 CalendarDay 객체의 getDate()로 연/월을 구한 다음 LocalDate 객체에 넣어서
                // LocalDate로 변환하는 처리가 필요하다
                Date date = day.getDate();
                LocalDate inputText = date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                String[] calendarHeaderElements = inputText.toString().split("-");
                StringBuilder calendarHeaderBuilder = new StringBuilder();
                calendarHeaderBuilder.append(calendarHeaderElements[0])
                        .append(" ")
                        .append(calendarHeaderElements[1]);
                return calendarHeaderBuilder.toString();
            }
        });


        //일정보이기



        new BackgroudTaskschedule().execute();

    }

    /* 선택된 요일의 background를 설정하는 Decorator 클래스 */
    private static class DayDecorator implements DayViewDecorator {

        private final Drawable drawable;

        public DayDecorator(Context context) {
            drawable = ContextCompat.getDrawable(context, R.drawable.calendar_selector);
        }

        // true를 리턴 시 모든 요일에 내가 설정한 드로어블이 적용된다
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return true;
        }

        // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
        @Override
        public void decorate(DayViewFacade view) {
            view.setSelectionDrawable(drawable);
//            view.addSpan(new StyleSpan(Typeface.BOLD));   // 달력 안의 모든 숫자들이 볼드 처리됨
        }
    }


    //backgroudtask

    class BackgroudTaskschedule extends AsyncTask<Void, Void, String> {


        String target;

        @Override
        protected void onPreExecute() {
            target = "http://whoyak.dothome.co.kr/ScheduleList.php";
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
                String scheduleName;

                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    scheduleName = object.getString("schedule");
                    Schedule schedule = new Schedule(scheduleName);
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



