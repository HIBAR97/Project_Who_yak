package com.example.project_who_yak.bltboard.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    private TextView tv_boardTitle;
    private TextView tv_boardContent;
    private TextView tv_boardWriter;
    private Spinner spinner_boardCategory;
    private String boardNo;
    private String[] category={"dummy"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_bltboard_detail, container, false);


        tv_boardTitle = (TextView) view.findViewById(R.id.board_title);
        tv_boardContent = (TextView) view.findViewById(R.id.board_content);
        tv_boardWriter = (TextView) view.findViewById(R.id.board_writer);
        spinner_boardCategory = (Spinner) view.findViewById(R.id.board_category);
        boardNo =FragmentBltBoardMain.boardNo;


        new BackgrounTaskGetDetail().execute();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_boardCategory.setAdapter(adapter);
        spinner_boardCategory.setEnabled(false);
        return view;

    }



    class BackgrounTaskGetDetail extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://whoyak.dothome.co.kr/BltBoardDetail.php?noticeNo=" + URLEncoder.encode(boardNo, "UTF-8");
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
                    tv_boardTitle.setText(noticeTitle);
                    tv_boardWriter.setText("작성자 : "+noticeWriter);
                    tv_boardContent.setText(noticeDetail);
                    category[0]=noticeCategory;
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,category);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_boardCategory.setAdapter(adapter);
                    spinner_boardCategory.setEnabled(false);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}