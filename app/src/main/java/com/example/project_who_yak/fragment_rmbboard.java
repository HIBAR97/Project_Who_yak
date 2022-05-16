package com.example.project_who_yak;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
public class fragment_rmbboard extends Fragment {

    private ListView rememberListView;
    private ArrayList<Remember> rememberList;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //선언
        view = inflater.inflate(R.layout.fragment_rmbboard, container, false);
        rememberListView = (ListView) view.findViewById(R.id.rmb_listview);

        TextView tv_remember = view.findViewById(R.id.rmb_name);

        new BackgrounTask_Remember().execute();

        //---------리스너 파트 ----------//
        tv_remember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
    }
    //더미 데이터 넣는 함수
    public void InitializeRememberData(){
        rememberList = new ArrayList<Remember>();
        rememberList.add(new Remember("작성글", "홍길동","2022-01-01"));
        final RememberListAdapter RememberAdapter = new RememberListAdapter(getActivity().getApplicationContext(), rememberList);
        rememberListView.setAdapter(RememberAdapter);
    }

    class BackgrounTask_Remember extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://whoyak.dothome.co.kr/BltboardRemember.php";
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
                String rememberTitle, rememberName, rememberDate;
                rememberList = new ArrayList<Remember>();
                while(count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    rememberTitle = object.getString("rememberTitle");
                    rememberName = object.getString("rememberName");
                    rememberDate = object.getString("rememberDate");
                    Remember remember = new Remember(rememberTitle, rememberName, rememberDate);
                    rememberList.add(remember);
                    final RememberListAdapter RememberAdapter = new RememberListAdapter(getActivity().getApplicationContext(), rememberList);
                    rememberListView.setAdapter(RememberAdapter);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}