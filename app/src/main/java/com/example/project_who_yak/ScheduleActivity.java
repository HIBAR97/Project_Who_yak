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
        //????????? ??? ????????????
        calendarView.addDecorator(new EventDecorator(Color.RED, calendarView.getSelectedDates()));
        //????????? ???,??? ?????? ??????
        calendarView.addDecorators(new SaturdayDecorator(),new SundayDecorator());


        //????????? ?????? ?????????
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

        //????????? ?????? ??????
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
                                Toast.makeText(getApplicationContext(),"????????? ??????????????????.",Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"?????? ????????? ??????????????????.",Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(),"json??????",Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(getApplicationContext(),"????????? ??????????????????.",Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"?????? ????????? ??????????????????.",Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(),"json??????",Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(getApplicationContext(),"????????? ??????????????????.",Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"?????? ????????? ??????????????????.",Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(),"json??????",Toast.LENGTH_SHORT).show();
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




        // ??? ?????? ????????? ???????????? ????????? ??????
        calendarView.state()
                .edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .commit();

        // ???, ????????? ????????? ????????? ?????? (MonthArrayTitleFormatter??? ????????? ??????????????? ?????? setTitleFormatter()??? ?????????)
        calendarView.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
        calendarView.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));

        // ?????? ????????? ?????? ???, ?????? ?????? ????????? ??????
        calendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader);

        // ?????? ?????? ??? ?????? ????????? ??????????????? ??????????????? ???
        calendarView.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
                // ?????? ????????? ?????? ?????????, ???????????? ????????? ???????????? ???????????? ????????? ????????? ????????? ?????? ?????? ????????????
                // UTC ????????? ???????????? ?????? ??? ????????????????????? ???????????? ????????? ????????? ????????? ?????? ???????????? ?????? ??????
                String startDay = dates.get(0).getDate().toString();
                String endDay = dates.get(dates.size() - 1).getDate().toString();
                Log.e(TAG, "????????? : " + startDay + ", ????????? : " + endDay);
            }
        });

        // ?????? ?????? ??? ?????? ????????? ??????????????? ??????????????? ??????
        calendarView.addDecorators(new DayDecorator(this));

        // ?????? ????????? ???????????? ???/?????? ????????? ?????? ?????????
        calendarView.setTitleFormatter(new  TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                // CalendarDay?????? ???????????? LocalDate ???????????? ???????????? ???????????? ????????????
                // ????????? MaterialCalendarView?????? ???/??? ??????????????? ?????????????????? CalendarDay ????????? getDate()??? ???/?????? ?????? ?????? LocalDate ????????? ?????????
                // LocalDate??? ???????????? ????????? ????????????
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


        //???????????????



        new BackgroudTaskschedule().execute();

    }

    /* ????????? ????????? background??? ???????????? Decorator ????????? */
    private static class DayDecorator implements DayViewDecorator {

        private final Drawable drawable;

        public DayDecorator(Context context) {
            drawable = ContextCompat.getDrawable(context, R.drawable.calendar_selector);
        }

        // true??? ?????? ??? ?????? ????????? ?????? ????????? ??????????????? ????????????
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return true;
        }

        // ?????? ?????? ??? ?????? ????????? ??????????????? ??????????????? ??????
        @Override
        public void decorate(DayViewFacade view) {
            view.setSelectionDrawable(drawable);
//            view.addSpan(new StyleSpan(Typeface.BOLD));   // ?????? ?????? ?????? ???????????? ?????? ?????????
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



