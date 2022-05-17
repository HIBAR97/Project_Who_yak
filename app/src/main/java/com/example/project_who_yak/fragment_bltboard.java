package com.example.project_who_yak;

import static com.example.project_who_yak.R.id.gz_name;
import static com.example.project_who_yak.R.id.listView_Category;
import static com.example.project_who_yak.R.id.listView_Notice;
import static com.example.project_who_yak.R.id.listView_Popular;
import static com.example.project_who_yak.R.id.ppl_name;
import static com.example.project_who_yak.R.id.rdo1;
import static com.example.project_who_yak.R.id.spn_main;
import static com.example.project_who_yak.R.id.spn_name;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

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

public class fragment_bltboard extends Fragment {

    private ListView noticeListView;
    private ArrayList<Notice> noticeList;

    private  ListView popularListView;
    private  ArrayList<Popular> PopularList;

    private ListView categoryListView;
    private  ArrayList<Category> CategoryList;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //선언
        view = inflater.inflate(R.layout.fragment_bltboard, container, false);
        noticeListView = (ListView) view.findViewById(listView_Notice);
        popularListView = (ListView) view.findViewById(listView_Popular);
        categoryListView = (ListView) view.findViewById(listView_Category);

        TextView tv_notice = view.findViewById(gz_name);
        TextView tv_ppl = view.findViewById(ppl_name);
        TextView tv_spn = view.findViewById(spn_name);
        Spinner spinner = view.findViewById(spn_main);

        RadioGroup Radio = view.findViewById(rdo1);
        int checkedRadioButtonId = Radio.getCheckedRadioButtonId();

        //------스피너에 카테고리 추가------//
        //String Spinner_category = spinner.getSelectedItem().toString();

        //혹시 몰라서 남겨두는 코드들
//        noticeList = new ArrayList<Notice>();
//        adapter = new NoticeListAdapter(getActivity().getApplicationContext(), noticeList);
//        noticeListView.setAdapter(adapter);

//        noticeList2 = new ArrayList<Notice>();
//        final NoticeListAdapter NoticeAdapter = new NoticeListAdapter(getActivity().getApplicationContext(), noticeList2);
//        noticeListView.setAdapter(NoticeAdapter);

        new BackgrounTask_Notice().execute();
        new BackgrounTask_Popular().execute();
        new BackgrounTask_Category().execute();

        //리스트뷰에 데이터 표현
        //this.InitializeNoticeData();



        //---------리스너 파트 ----------//
        tv_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tv_ppl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tv_spn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        Radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedRadioButtonId == R.id.rdo_btn1){
                    String Radio_select = "최신순";
                    tv_notice.setText(Radio_select);
                }
                else if (checkedRadioButtonId == R.id.rdo_btn2) {
                    String Radio_select = "인기순";
                    tv_notice.setText(Radio_select);
                }
            }
        });

        return view;
    }

    //더미 데이터 넣는 함수
    public void InitializeNoticeData(){
        noticeList = new ArrayList<Notice>();
        noticeList.add(new Notice("공지사항", "미션임파서블","15세 이상관람가"));
        final NoticeListAdapter NoticeAdapter = new NoticeListAdapter(getActivity().getApplicationContext(), noticeList);
        noticeListView.setAdapter(NoticeAdapter);
    }


    //------공지사항 DB연동------//
    class BackgrounTask_Notice extends AsyncTask<Void, Void, String>
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
                noticeList = new ArrayList<Notice>();
               //while(count < jsonArray.length())
                while(count < 2)
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    Notice notice = new Notice(noticeContent, noticeName, noticeDate);
                    noticeList.add(notice);
                    final NoticeListAdapter NoticeAdapter = new NoticeListAdapter(getActivity().getApplicationContext(), noticeList);
                    noticeListView.setAdapter(NoticeAdapter);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //------인기글 DB연동------//
    class BackgrounTask_Popular extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://whoyak.dothome.co.kr/BltboardNotice_Popular.php";
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
                String noticeContent, noticeName, noticeDate, noticeRate;
                PopularList = new ArrayList<Popular>();
                //while(count < jsonArray.length())
                while(count < 3)
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    noticeRate = object.getString("noticePopularity");
                    Popular popular = new Popular(noticeContent, noticeName, noticeDate, noticeRate);
                    PopularList.add(popular);
                    final PopularListAdapter PopularAdapter = new PopularListAdapter(getActivity().getApplicationContext(), PopularList);
                    popularListView.setAdapter(PopularAdapter);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //------카테고리 DB연동------//
    class BackgrounTask_Category extends AsyncTask<Void, Void, String>
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
                CategoryList = new ArrayList<Category>();
                //while(count < jsonArray.length())
                while(count < 3)
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    Category category = new Category(noticeContent, noticeName, noticeDate);
                    CategoryList.add(category);
                    final CategoryListAdapter CategoryAdapter = new CategoryListAdapter(getActivity().getApplicationContext(), CategoryList);
                    categoryListView.setAdapter(CategoryAdapter);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}