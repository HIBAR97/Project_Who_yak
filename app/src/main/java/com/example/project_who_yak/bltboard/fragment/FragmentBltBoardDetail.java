package com.example.project_who_yak.bltboard.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.project_who_yak.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class FragmentBltBoardDetail extends Fragment {

    private View view;
    private TextView spinner;
    private TextView tv_noticeTitle;
    private TextView tv_noticeDetail;
    private TextView tv_noticeWriter;
    private Spinner spinner_noticeCategory;
    private String noticeNo;
    private String[] category={"s"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_bltboard_detail, container, false);


        tv_noticeTitle = (TextView) view.findViewById(R.id.board_title);
        tv_noticeDetail = (TextView) view.findViewById(R.id.board_content);
        tv_noticeWriter = (TextView) view.findViewById(R.id.board_writer);
        spinner_noticeCategory = (Spinner) view.findViewById(R.id.board_category);
        noticeNo =FragmentBltBoardMain.boardNo;


        new BackgrounTaskGetDetail().execute();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_noticeCategory.setAdapter(adapter);
        spinner_noticeCategory.setEnabled(false);
        return view;
    }


    class BackgrounTaskGetDetail extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://whoyak.dothome.co.kr/BltBoardDetail.php?noticeNo=" + URLEncoder.encode(noticeNo, "UTF-8");
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
                while ((temp = bufferedReader.readLine()) != null) {
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
                String noticeTitle, noticeWriter, noticeCategory, noticeDetail;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeTitle = object.getString("noticeTitle");
                    noticeWriter = object.getString("noticeWriter");
                    noticeDetail = object.getString("noticeDetail");
                    noticeCategory = object.getString("noticeCategory");
                    tv_noticeTitle.setText(noticeTitle);
                    tv_noticeWriter.setText("작성자 : "+noticeWriter);
                    tv_noticeDetail.setText(noticeDetail);
                    category[0]=noticeCategory;
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,category);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_noticeCategory.setAdapter(adapter);
                    spinner_noticeCategory.setEnabled(false);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}