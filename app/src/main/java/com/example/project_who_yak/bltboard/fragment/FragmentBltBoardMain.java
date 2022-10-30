package com.example.project_who_yak.bltboard.fragment;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.project_who_yak.bltboard.category.Category;
import com.example.project_who_yak.bltboard.category.CategoryListAdapter;
import com.example.project_who_yak.bltboard.notice.Notice;
import com.example.project_who_yak.bltboard.notice.NoticeListAdapter;
import com.example.project_who_yak.bltboard.popular.Popular;
import com.example.project_who_yak.bltboard.popular.PopularListAdapter;
import com.example.project_who_yak.R;
import com.example.project_who_yak.bltboard.BltBoardActivity;

public class FragmentBltBoardMain extends Fragment {

    private ListView noticeListView;
    private ArrayList<Notice> noticeList;

    private  ListView popularListView;
    private  ArrayList<Popular> PopularList;

    private ListView categoryListView;
    private  ArrayList<Category> categoryList;


    private View view;
    private String spinnerText; //스피너에 선택된 값
    public static String boardNo;

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



        //리스트뷰에 데이터 표현
        //this.InitializeNoticeData();

        //------스피너에 카테고리 삽입------//
        ArrayAdapter Category_ada = ArrayAdapter.createFromResource(getActivity(), R.array.Category, android.R.layout.simple_spinner_dropdown_item);
        Category_ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(Category_ada);
        spinnerText = spinner.getSelectedItem().toString(); //초기화

        //------페이지가 켜지면 정보 표현------//
        new BackgrounTask_Notice().execute();
        new BackgrounTask_Popular().execute();
        new BackgrounTask_Category().execute();

        //---------리스너 파트 ----------//
        noticeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boardNo = noticeList.get(position).getBoardNo().toString();
                //임시 xml 표시
                ((BltBoardActivity)getActivity()).replaceFragment();

            }
        });
        popularListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boardNo = PopularList.get(position).getBoardNo().toString();
                //임시 xml 표시
                ((BltBoardActivity)getActivity()).replaceFragment();

            }
        });

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boardNo = categoryList.get(position).getBoardNo().toString();
                //임시 xml 표시
                ((BltBoardActivity)getActivity()).replaceFragment();

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //스피너에 있는 정보를 가져옴
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity().getApplicationContext(), spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                if (Radio.getCheckedRadioButtonId() == R.id.rdo_btn1){
                    String Radio_select = "최신순";
                    spinnerText = spinner.getSelectedItem().toString();
                    new BackgrounTask_Category().execute();

//                    //어떤 카테고리가 선택되었는지 확인 --> 함수에서 변수로 어떤카테고리인지 확인
//                    if (spinnerText .equals("자유")){
//                        new BackgrounTask_Category().execute();
//                    }else if (spinnerText .equals("질문")){
//                        new BackgrounTask_Category().execute();
//                    }else if (spinnerText .equals("정보")){
//                        new BackgrounTask_Category().execute();
//                    }
                } else if (Radio.getCheckedRadioButtonId() == R.id.rdo_btn2) {
                    //인기순 선택
                    String Radio_select = "인기순";
                    spinnerText = spinner.getSelectedItem().toString();
                    new BackgrounTask_Popular_ByCategory().execute();
//                    tv_notice.setText(spinner_Text);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //최신순 선택
                if (checkedId == R.id.rdo_btn1){
                    String Radio_select = "최신순";
                    spinnerText = spinner.getSelectedItem().toString();
                    new BackgrounTask_Category().execute();
//                    //어떤 카테고리가 선택되었는지 확인
//                    if (spinnerText .equals("자유")){
//                        new BackgrounTask_Category().execute();
//                    }else if (spinnerText .equals("질문")){
//                        new BackgrounTask_Category().execute();
//                    }else if (spinnerText .equals("정보")){
//                        new BackgrounTask_Category().execute();
//                    }
                } else if (checkedId == R.id.rdo_btn2) {
                    //인기순 선택
                    String Radio_select = "인기순";
                    spinnerText = spinner.getSelectedItem().toString();
                    new BackgrounTask_Popular_ByCategory().execute();
//                    //어떤 카테고리가 선택되었는지 확인
//                    if (spinnerText .equals("자유")){
//                        new BackgrounTask_Popular_ByCategory().execute();
//                    }else if (spinnerText .equals("질문")){
//                        new BackgrounTask_Popular_ByCategory().execute();
//                    }else if (spinnerText .equals("정보")){
//                        new BackgrounTask_Popular_ByCategory().execute();
//                    }
//                    tv_notice.setText(spinner_Text);
                }
            }
        });

        return view;
    }



    //리스트 뷰 더미 데이터 넣는 함수
//    public void InitializeNoticeData(){
//        noticeList = new ArrayList<Notice>();
//        noticeList.add(new Notice("공지사항", "미션임파서블","15세 이상관람가"));
//        final NoticeListAdapter NoticeAdapter = new NoticeListAdapter(getActivity().getApplicationContext(), noticeList);
//        noticeListView.setAdapter(NoticeAdapter);
//    }

    //------공지사항 DB연동------//
    class BackgrounTask_Notice extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://whoyak.dothome.co.kr/BltBoardNoticeList.php";
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
                String noticeNo, noticeTitle, noticeWriter, noticeDate, noticeCategory, noticePopularity;
                noticeList = new ArrayList<Notice>();
               //while(count < jsonArray.length())
                while(count < 2)
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeNo = object.getString("noticeNo");
                    noticeTitle = object.getString("noticeTitle");
                    noticeWriter = object.getString("noticeWriter");
                    noticeDate = object.getString("noticeDate");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//날짜 변환 형식
                    Date date = sdf.parse(noticeDate); //sdf 쓰기위해 Date 클래스로 변경
                    noticeDate = sdf.format(date); //날짜 변환
                    noticeCategory = object.getString("noticeCategory");
                    noticePopularity = object.getString("noticePopularity");
                    Notice notice = new Notice(noticeNo, noticeTitle, noticeWriter, noticeDate, noticeCategory,noticePopularity);
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
            target = "http://whoyak.dothome.co.kr/BltBoardPopularList.php";
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
                String noticeNo, noticeTitle, noticeWriter, noticeDate,noticeCategory,noticePopularity;
                PopularList = new ArrayList<Popular>();
                //while(count < jsonArray.length())
                while(count < 3)
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeNo = object.getString("noticeNo");
                    noticeTitle = object.getString("noticeTitle");
                    noticeWriter = object.getString("noticeWriter");
                    noticeDate = object.getString("noticeDate");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//날짜 변환 형식
                    Date date = sdf.parse(noticeDate); //sdf 쓰기위해 Date 클래스로 변경
                    noticeDate = sdf.format(date); //날짜 변환
                    noticeCategory = object.getString("noticeCategory");
                    noticePopularity = object.getString("noticePopularity");
                    Popular popular = new Popular(noticeNo, noticeTitle, noticeWriter, noticeDate,noticeCategory,noticePopularity);
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
            try {
                target = "http://whoyak.dothome.co.kr/BltBoardCategoryList.php?noticeCategory="+ URLEncoder.encode(spinnerText,"UTF-8");
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
                String noticeNo, noticeTitle, noticeWriter, noticeDate, noticeCategory, noticePopularity;
                //위에서 선언된거 가져와야됨
                categoryList = new ArrayList<Category>();
                while(count < 3)
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeNo = object.getString("noticeNo");
                    noticeTitle = object.getString("noticeTitle");
                    noticeWriter = object.getString("noticeWriter");
                    noticeDate = object.getString("noticeDate");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//날짜 변환 형식
                    Date date = sdf.parse(noticeDate); //sdf 쓰기위해 Date 클래스로 변경
                    noticeDate = sdf.format(date); //날짜 변환
                    noticeCategory = object.getString("noticeCategory");
                    noticePopularity = object.getString("noticePopularity");
                    //리스트 선언 + arr 리스트 이름 가져오기
                    Category category = new Category(noticeNo, noticeTitle, noticeWriter, noticeDate, noticeCategory ,noticePopularity);
                    categoryList.add(category);
                    //어뎁터 선언
                    CategoryListAdapter CategoryListAdapter = new CategoryListAdapter(getActivity().getApplicationContext(), categoryList);
                    //위에서 선언된 리스트뷰
                    categoryListView.setAdapter(CategoryListAdapter);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //------인기글 카테고리별 DB연동------//
    class BackgrounTask_Popular_ByCategory extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://whoyak.dothome.co.kr/BltBoardPopularListByCategory.php?noticeCategory="+ URLEncoder.encode(spinnerText,"UTF-8");
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
                String noticeNo, noticeTitle, noticeWriter, noticeDate,noticeCategory,noticePopularity;
                categoryList = new ArrayList<Category>();
                //while(count < jsonArray.length())
                while(count < 3)
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeNo = object.getString("noticeNo");
                    noticeTitle = object.getString("noticeTitle");
                    noticeWriter = object.getString("noticeWriter");
                    noticeDate = object.getString("noticeDate");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//날짜 변환 형식
                    Date date = sdf.parse(noticeDate); //sdf 쓰기위해 Date 클래스로 변경
                    noticeDate = sdf.format(date); //날짜 변환
                    noticeCategory = object.getString("noticeCategory");
                    noticePopularity = object.getString("noticePopularity");
                    Category popular = new Category(noticeNo, noticeTitle, noticeWriter, noticeDate,noticeCategory, noticePopularity);
                    categoryList.add(popular);
                    final CategoryListAdapter categoryListAdapter = new CategoryListAdapter(getActivity().getApplicationContext(), categoryList);
                    categoryListView.setAdapter(categoryListAdapter);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

/*
    //------카테고리1 최신순------//
    class BackgrounTask_Category1L extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://whoyak.dothome.co.kr/BltboardNotice_Category1(Date).php";
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
                String noticeNo, noticeTitle, noticeWriter, noticeDate,noticeCategory;
                //위에서 선언된거 가져와야됨
                category1_Latest = new ArrayList<Category1_Latest>();
                while(count < 3)
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeNo = object.getString("noticeNo");
                    noticeTitle = object.getString("noticeTitle");
                    noticeWriter = object.getString("noticeWriter");
                    noticeDate = object.getString("noticeDate");
                    noticeCategory = object.getString("noticeCategory");
                    //리스트 선언 + arr 리스트 이름 가져오기
                    Category1_Latest category1L = new Category1_Latest(noticeNo, noticeTitle, noticeWriter, noticeDate, noticeCategory);
                    category1_Latest.add(category1L);
                    //어뎁터 선언
                    Category1_Latest_Adapter CategoryAdapter1L = new Category1_Latest_Adapter(getActivity().getApplicationContext(), category1_Latest);
                    //위에서 선언된 리스트뷰
                    categoryListView.setAdapter(CategoryAdapter1L);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //------카테고리2 최신순------//
    class BackgrounTask_Category2L extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://whoyak.dothome.co.kr/BltboardNotice_Category2(Date).php";
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
                String noticeNo, noticeTitle, noticeWriter, noticeDate,noticeCategory;
                //위에서 선언된거 가져와야됨
                category1_Latest = new ArrayList<Category1_Latest>();
                while(count < 3)
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeNo = object.getString("noticeNo");
                    noticeTitle = object.getString("noticeTitle");
                    noticeWriter = object.getString("noticeWriter");
                    noticeDate = object.getString("noticeDate");
                    noticeCategory = object.getString("noticeCategory");
                    Toast.makeText(getActivity().getApplicationContext(), "AA", Toast.LENGTH_SHORT).show();
                    //리스트 선언 + arr 리스트 이름 가져오기
                    Category1_Latest category2L = new Category1_Latest(noticeNo, noticeTitle, noticeWriter, noticeDate, noticeCategory);
                    category1_Latest.add(category2L);
                    //어뎁터 선언
                    Category1_Latest_Adapter CategoryAdapter2L = new Category1_Latest_Adapter(getActivity().getApplicationContext(), category1_Latest);
                    //위에서 선언된 리스트뷰
                    categoryListView.setAdapter(CategoryAdapter2L);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //------카테고리3 최신순------//
    class BackgrounTask_Category3L extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://whoyak.dothome.co.kr/BltboardNotice_Category3(Date).php";
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
                String noticeContent, noticeName, noticeDate, noticeRate, noticeCat;
                //위에서 선언된거 가져와야됨
                category3_Latest = new ArrayList<Category3_Latest>();
                while(count < 3)
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    noticeRate = object.getString("noticeRate");
                    noticeCat = object.getString("noticeCategory");
                    //리스트 선언 + arr 리스트 이름 가져오기
                    Category3_Latest category3L = new Category3_Latest(noticeContent, noticeName, noticeDate, noticeRate, noticeCat);
                    category3_Latest.add(category3L);
                    //어뎁터 선언
                    final Category3_Latest_Adapter CategoryAdapter3L = new Category3_Latest_Adapter(getActivity().getApplicationContext(), category3_Latest);
                    //위에서 선언된 리스트뷰
                    categoryListView.setAdapter(CategoryAdapter3L);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //------카테고리1 인기순------//
    class BackgrounTask_Category1P extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://whoyak.dothome.co.kr/BltboardNotice_Category1(Popular).php";
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
                String noticeContent, noticeName, noticeDate, noticeRate, noticeCat;
                //위에서 선언된거 가져와야됨
                category1_populars = new ArrayList<Category1_Popular>();
                while(count < 3)
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    noticeRate = object.getString("noticeRate");
                    noticeCat = object.getString("noticeCategory");
                    //리스트 선언 + arr 리스트 이름 가져오기
                    Category1_Popular category1P = new Category1_Popular(noticeContent, noticeName, noticeDate, noticeRate, noticeCat);
                    category1_populars.add(category1P);
                    //어뎁터 선언
                    final Category1_Popular_Adapter CategoryAdapter3L = new Category1_Popular_Adapter(getActivity().getApplicationContext(), category1_populars);
                    //위에서 선언된 리스트뷰
                    categoryListView.setAdapter(CategoryAdapter3L);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
*/

}