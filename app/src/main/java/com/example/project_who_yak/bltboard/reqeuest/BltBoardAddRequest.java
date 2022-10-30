package com.example.project_who_yak.bltboard.reqeuest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
//일단 testBoard를 만들어서 추가 시켜봄 testBoard에는 title이랑 content밖에 없는데 넣고 싶은것은 추가하면됨 모르겠으면 물어보기 php파일도 확인바람
public class BltBoardAddRequest extends StringRequest {
    final static private String URL ="http://whoyak.dothome.co.kr/BltBoardAdd.php";
    private Map<String, String> parametrs;

    public BltBoardAddRequest(String title, String writer, String detail, String category, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parametrs = new HashMap<>();
        parametrs.put("title",title); //Notice에 title 입력
        parametrs.put("writer",writer); //Notice에 writer 입력
        parametrs.put("detail",detail); //Notice에 detail 입력
        parametrs.put("category",category); //Notice에 category 입력
    }

    @Override
    public Map<String,String> getParams(){
        return parametrs;
    }
}
