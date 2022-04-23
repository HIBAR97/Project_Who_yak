package com.example.project_who_yak;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Drug_information extends AppCompatActivity {

    //Variable declaration FragmentManager, Fragment page
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    //private Fragment_Drug_info Frag_Drug_info;
    //private Fragment_Drug_detail Frag_Drug_detail;
    //private Fragment_Drug_side_effect Frag_Drug_side_effect;
    //private Fragment_Drug_precaution Frag_Drug_precaution;

    private String Drug_name;
    private AlertDialog dialog;
    private ListView drugListView;
    private DrugListAdapter Adapter;
    public List<Drug> drugList;
    public String Drug_name2;
    public String Drug_Search_name;
    public String drugInfo;
    public String drugDetail;
    public String drugSideEffect;
    public String drugPrecaution;
    public Drug drug2;
    // When activity started
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //화면 이동
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drug_information);


        LinearLayout layout_drug_btn = (LinearLayout) findViewById(R.id.Layout_Drug_Button);
        drugListView = (ListView) findViewById(R.id.Drug_ListView);
        drugList = new ArrayList<Drug>();
        Adapter = new DrugListAdapter(getApplicationContext(), drugList);
        drugListView.setAdapter(Adapter);
        EditText edt_Drug_search_name;

        //Varivable btn
        Button btn_Drug_info_home;
        Button btn_Drug_info;
        Button btn_Drug_detail;
        Button btn_Drug_side_effect;
        Button btn_Drug_precaution;
        Button btn_Drug_search;

        //Conect btn on layout
        btn_Drug_info_home = (Button)findViewById(R.id.Btn_Drug_info_Home);
        btn_Drug_info = (Button)findViewById(R.id.Btn_Drug_info);
        btn_Drug_detail = (Button)findViewById(R.id.Btn_Drug_detail);
        btn_Drug_side_effect = (Button)findViewById(R.id.Btn_Drug_side_effect);
        btn_Drug_precaution = (Button)findViewById(R.id.Btn_Drug_precaution);
        btn_Drug_search = (Button)findViewById(R.id.Btn_Drug_search);

        //edt
        edt_Drug_search_name = (EditText)findViewById(R.id.Edittext_Drug_Search);
        fragmentManager = getSupportFragmentManager();

        //Fragment page variable
        /*
        Frag_Drug_info = new Fragment_Drug_info();
        Frag_Drug_detail = new Fragment_Drug_detail();
        Frag_Drug_side_effect = new Fragment_Drug_side_effect();
        Frag_Drug_precaution = new Fragment_Drug_precaution();
        */
        //Default fragment
        //transaction = fragmentManager.beginTransaction();
        //transaction.replace(R.id.FrameLayout_Drug, Frag_Drug_info).commitAllowingStateLoss();



        layout_drug_btn.setVisibility(View.GONE);

        //btn Listener
        drugListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                layout_drug_btn.setVisibility(View.VISIBLE);
                drugListView.setVisibility(View.GONE);
                Drug_name2 = drugList.get(i).getDrugname().toString();
                //Toast.makeText(getApplicationContext(),Drug_name2,Toast.LENGTH_SHORT).show();
                new BackgroudTaskInfo().execute();

//                Fragment fragment = new Fragment_Drug_info();
//
//                Bundle bundle = new Bundle();
//                bundle.putString("drugName",Drug_name2);
//
//                bundle.putString("drugInfo",drugInfo);
//                fragment.setArguments(bundle);
//                //Frag_Drug_info.setArguments(bundle);
//
//                transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.FrameLayout_Drug, fragment).commitAllowingStateLoss();


//                transaction.replace(R.id.FrameLayout_Drug, Frag_Drug_info);
//               transaction.commit();
//                Frag_Drug_info.setArguments(bundle);
//                transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.FrameLayout_Drug, Frag_Drug_info).commitAllowingStateLoss();

            }
        });
        btn_Drug_info_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                startActivity(intent);
            }
        });

        btn_Drug_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Drug_Search_name = edt_Drug_search_name.getText().toString();

                if(Drug_Search_name.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"빈칸입니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                new BackgroudTaskSerch().execute();

//                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                startActivity(intent);
            }
        });

        btn_Drug_info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //버튼 눌러도 정보 보여줄 수 있도록 세팅
                new BackgroudTaskInfo().execute();

                //Bundle bundle = new Bundle();
               // bundle.putString("drugName",Drug_name2);
                //Frag_Drug_info.setArguments(bundle);
                //transaction = fragmentManager.beginTransaction();
                //transaction.replace(R.id.FrameLayout_Drug, Frag_Drug_info).commitAllowingStateLoss();
            }
        });

        btn_Drug_detail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                new BackgroudTaskDetail().execute();
                /*
                Bundle bundle = new Bundle();
                bundle.putString("drugName",Drug_name2);
                Frag_Drug_detail.setArguments(bundle);
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FrameLayout_Drug, Frag_Drug_detail).commitAllowingStateLoss();*/
            }
        });

        btn_Drug_side_effect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new BackgroudTaskSideEffect().execute();
                //Bundle bundle = new Bundle();
                //bundle.putString("drugName",Drug_name2);
                //Frag_Drug_side_effect.setArguments(bundle);
                //transaction = fragmentManager.beginTransaction();
                //transaction.replace(R.id.FrameLayout_Drug, Frag_Drug_side_effect).commitAllowingStateLoss();
            }
        });

        btn_Drug_precaution.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new BackgroudTaskPrecaution().execute();
                /*
                Bundle bundle = new Bundle();
                bundle.putString("drugName",Drug_name2);
                Frag_Drug_precaution.setArguments(bundle);
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FrameLayout_Drug, Frag_Drug_precaution).commitAllowingStateLoss();*/
            }
        });


        new BackgroudTask().execute();

    }


    class BackgroudTaskInfo extends AsyncTask<Void, Void, String>{

        String target;
        @Override
        protected void onPreExecute() {
            try {
                target = "http://whoyak.dothome.co.kr/DrugInfo.php?drugName="+ URLEncoder.encode(Drug_name2,"UTF-8");
            } catch (Exception e) {
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
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count=0;
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    drugInfo = object.getString("drugInfo");
                    drugDetail = "drugDetail";
                    drug2 = new Drug(drugInfo);
                    drugList.clear();
                    drugList.add(drug2);
                    Adapter.notifyDataSetChanged();
                    //확인용
                    //Toast.makeText(getApplicationContext(),drugInfo,Toast.LENGTH_SHORT).show();
                    count++;
                    Fragment fragment = new Fragment_Drug_info();

                    Bundle bundle = new Bundle();
                    bundle.putString("drugName",Drug_name2);
                    bundle.putString("drugDetail",drugDetail);
                    bundle.putString("drugInfo",drugInfo);
                    fragment.setArguments(bundle);
                    //Frag_Drug_info.setArguments(bundle);

                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.FrameLayout_Drug, fragment).commitAllowingStateLoss();
                    return;
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }


    class BackgroudTaskDetail extends AsyncTask<Void, Void, String>{

        String target;
        @Override
        protected void onPreExecute() {
            try {
                target = "http://whoyak.dothome.co.kr/DrugDetail.php?drugName="+ URLEncoder.encode(Drug_name2,"UTF-8");
            } catch (Exception e) {
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
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count=0;
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    drugDetail = object.getString("drugDetail");
                    drug2 = new Drug(drugDetail);
                    drugList.clear();
                    drugList.add(drug2);
                    Adapter.notifyDataSetChanged();
                    //확인용
                    //Toast.makeText(getApplicationContext(),drugInfo,Toast.LENGTH_SHORT).show();
                    count++;
                    Fragment fragment_detail = new Fragment_Drug_detail();

                    Bundle bundle = new Bundle();
                    bundle.putString("drugDetail",drugDetail);
                    fragment_detail.setArguments(bundle);
                    //Frag_Drug_info.setArguments(bundle);

                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.FrameLayout_Drug, fragment_detail).commitAllowingStateLoss();
                    return;
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }


    class BackgroudTaskSideEffect extends AsyncTask<Void, Void, String>{

        String target;
        @Override
        protected void onPreExecute() {
            try {
                target = "http://whoyak.dothome.co.kr/DrugSideEffect.php?drugName="+ URLEncoder.encode(Drug_name2,"UTF-8");
            } catch (Exception e) {
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
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count=0;
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    drugSideEffect = object.getString("drugSideEffect");
                    //drug2 = new Drug(drugSideEffect);
                    //drugList.clear();
                    //drugList.add(drug2);
                    //Adapter.notifyDataSetChanged();
                    //확인용
                    //Toast.makeText(getApplicationContext(),drugInfo,Toast.LENGTH_SHORT).show();
                    count++;
                    Fragment fragment_side_effect = new Fragment_Drug_side_effect();
                    Bundle bundle = new Bundle();
                    bundle.putString("drugSideEffect",drugSideEffect);
                    fragment_side_effect.setArguments(bundle);
                    //Frag_Drug_info.setArguments(bundle);
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.FrameLayout_Drug, fragment_side_effect).commitAllowingStateLoss();
                    return;
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }


    class BackgroudTaskPrecaution extends AsyncTask<Void, Void, String>{

        String target;
        @Override
        protected void onPreExecute() {
            try {
                target = "http://whoyak.dothome.co.kr/DrugPrecaution.php?drugName="+ URLEncoder.encode(Drug_name2,"UTF-8");
            } catch (Exception e) {
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
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count=0;
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    drugPrecaution = object.getString("drugPrecaution");
                    //drug2 = new Drug(drugSideEffect);
                    //drugList.clear();
                    //drugList.add(drug2);
                    //Adapter.notifyDataSetChanged();
                    //확인용
                    //Toast.makeText(getApplicationContext(),drugInfo,Toast.LENGTH_SHORT).show();
                    count++;
                    Fragment fragment_precaution = new Fragment_Drug_precaution();
                    Bundle bundle = new Bundle();
                    bundle.putString("drugPrecaution",drugPrecaution);
                    fragment_precaution.setArguments(bundle);
                    //Frag_Drug_info.setArguments(bundle);
                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.FrameLayout_Drug, fragment_precaution).commitAllowingStateLoss();
                    return;
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }


    class BackgroudTaskSerch extends AsyncTask<Void, Void, String>{

        String target;
        @Override
        protected void onPreExecute() {
            try {
                target = "http://whoyak.dothome.co.kr/DrugSearch.php?drugName="+ URLEncoder.encode(Drug_Search_name,"UTF-8");
            } catch (Exception e) {
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
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count=0;
                String drugName;
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    drugName = object.getString("drugName");
                    Drug drug = new Drug(drugName);
                    drugList.clear();
                    drugList.add(drug);
                    Adapter.notifyDataSetChanged();
                    count++;
                }
                if(count==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Drug_information.this);
                    dialog = builder.setMessage("조회된 내용이 없습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    class BackgroudTask extends AsyncTask<Void, Void, String>{


        String target;

        @Override
        protected void onPreExecute() {
            target = "http://whoyak.dothome.co.kr/DrugList.php";
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
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count=0;
                String drugName;
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    drugName = object.getString("drugName");
                    Drug drug = new Drug(drugName);
                    drugList.add(drug);
                    Adapter.notifyDataSetChanged();
                    count++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
