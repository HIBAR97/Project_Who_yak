package com.example.project_who_yak;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class fragment_bltboard extends Fragment {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;

    private ArrayList<Notice> noticeList2;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //선언
        view = inflater.inflate(R.layout.fragment_bltboard, container, false);
        noticeListView = (ListView) view.findViewById(R.id.listView);

        //혹시 몰라서 남겨두는 코드들
//        noticeList = new ArrayList<Notice>();
//        adapter = new NoticeListAdapter(getActivity().getApplicationContext(), noticeList);
//        noticeListView.setAdapter(adapter);

//        noticeList2 = new ArrayList<Notice>();
//        final NoticeListAdapter NoticeAdapter = new NoticeListAdapter(getActivity().getApplicationContext(), noticeList2);
//        noticeListView.setAdapter(NoticeAdapter);

        new BackgrounTask().execute();

        //리스트뷰에 데이터 표현
        //this.InitializeNoticeData();

        return view;
    }

    //더미 데이터 넣는 함수
    public void InitializeNoticeData(){
        noticeList2 = new ArrayList<Notice>();
        noticeList2.add(new Notice("공지사항", "미션임파서블","15세 이상관람가"));
        final NoticeListAdapter NoticeAdapter = new NoticeListAdapter(getActivity().getApplicationContext(), noticeList2);
        noticeListView.setAdapter(NoticeAdapter);
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
                noticeList2 = new ArrayList<Notice>();
                while(count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");

                    Notice notice = new Notice(noticeContent, noticeName, noticeDate);
                    noticeList2.add(notice);
                    final NoticeListAdapter NoticeAdapter = new NoticeListAdapter(getActivity().getApplicationContext(), noticeList2);
                    noticeListView.setAdapter(NoticeAdapter);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}