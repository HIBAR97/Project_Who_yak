package com.example.project_who_yak;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;


public class Fragment_Drug_info extends Fragment {

    private EditText edt_druginfo;
    private View view;
    private String DrugName_info3;
    private List<Drug> drugList;
    private String drugInfo;

    @Nullable
    //Connect xml file using container
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_drug_info, container, false);
               //아이디값을 가져오기위한 bundle
        view = inflater.inflate(R.layout.fragment_drug_info, container, false);
        Bundle bundle = getArguments();
        drugInfo = bundle.getString("drugInfo");
        edt_druginfo = view.findViewById(R.id.Frag_info_text);
        edt_druginfo.setText(drugInfo);
        return view;

    }



//    class BackgroudTaskInfo extends AsyncTask<Void, Void, String>{
//
//        String target;
//        @Override
//        protected void onPreExecute() {
//            try {
//                target = "http://whoyak.dothome.co.kr/DrugInfo.php?drugName="+ URLEncoder.encode(DrugName_info2,"UTF-8");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        @Override
//        protected String doInBackground(Void... voids) {
//
//            try {
//                URL url = new URL(target);
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                String temp;
//                StringBuilder stringBuilder = new StringBuilder();
//                while((temp = bufferedReader.readLine()) != null)
//                {
//                    stringBuilder.append(temp + "\n");
//                }
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return stringBuilder.toString().trim();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        public void onProgressUpdate(Void... values){
//            super.onProgressUpdate();
//        }
//
//        @Override
//        public void onPostExecute(String result){
//            try{
//                JSONObject jsonObject = new JSONObject(result);
//                JSONArray jsonArray = jsonObject.getJSONArray("response");
//                int count=0;
//                String drugInfo;
//                while(count < jsonArray.length()){
//                    JSONObject object = jsonArray.getJSONObject(count);
//                    drugInfo = object.getString("drugInfo");
//                    edt_druginfo.setText(drugInfo);
//                    Toast.makeText(getActivity(),drugInfo,Toast.LENGTH_SHORT).show();
//                    count++;
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }

}
