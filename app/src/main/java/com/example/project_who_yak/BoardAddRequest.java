package com.example.project_who_yak;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
//일단 testBoard를 만들어서 추가 시켜봄 testBoard에는 title이랑 content밖에 없는데 넣고 싶은것은 추가하면됨 모르겠으면 물어보기 php파일도 확인바람
public class BoardAddRequest extends StringRequest {
    final static private String URL ="http://whoyak.dothome.co.kr/BoardAdd.php";
    private Map<String, String> parametrs;

    public BoardAddRequest(String title, String content, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parametrs = new HashMap<>();
        parametrs.put("title",title); //testBoard에 title에 입력
        parametrs.put("content",content); //testBoard에 content에 입력
    }

    @Override
    public Map<String,String> getParams(){
        return parametrs;
    }
}
