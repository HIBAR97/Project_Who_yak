package com.example.project_who_yak;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class activity_bltboard extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private fragment_bltboard fragment_bltboard;
    private fragment_wrtboard fragment_wrtboard;
    private fragment_rmbboard fragment_rmbboard;

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bltboard);

        noticeListView = (ListView) findViewById(R.id.listView);
        noticeList = new ArrayList<Notice>();
        adapter = new NoticeListAdapter(getApplicationContext(), noticeList);
        noticeListView.setAdapter(adapter);

        Button btn_blt_home;
        Button btn_bltboard;
        Button btn_wrtboard;
        Button btn_rmbboard;

        btn_blt_home = (Button)findViewById(R.id.btn_blt_home);
        btn_bltboard = (Button)findViewById(R.id.blt_brd);
        btn_wrtboard = (Button)findViewById(R.id.blt_wrt);
        btn_rmbboard = (Button)findViewById(R.id.blt_rmb);

        fragmentManager = getSupportFragmentManager();

        fragment_bltboard = new fragment_bltboard();
        fragment_rmbboard = new fragment_rmbboard();
        fragment_wrtboard = new fragment_wrtboard();


        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.FrameLayout_bltscreen, fragment_bltboard).commitAllowingStateLoss();

        btn_blt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                startActivity(intent);
            }
        });

        btn_bltboard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FrameLayout_bltscreen, fragment_bltboard).commitAllowingStateLoss();
            }
        });

        btn_rmbboard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FrameLayout_bltscreen, fragment_rmbboard).commitAllowingStateLoss();
            }
        });

        btn_wrtboard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FrameLayout_bltscreen, fragment_wrtboard).commitAllowingStateLoss();
            }
        });

        new BackgrounTask() .execute();
    }

    class BackgrounTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://whoyak.dothome.co.kr/BltboardNotice.php";
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
                String noticeContent, noticeName, noticeDate;
                while(count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    Notice notice = new Notice(noticeContent, noticeName, noticeDate);
                    noticeList.add(notice);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
