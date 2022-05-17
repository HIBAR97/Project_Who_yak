package com.example.project_who_yak;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ScheduleDelRequest extends StringRequest {
    final static private String URL ="http://whoyak.dothome.co.kr/ScheduleDel.php";
    private Map<String, String> parametrs;

    public ScheduleDelRequest(String userID, String schedule, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parametrs = new HashMap<>();
        parametrs.put("userID",userID);
        parametrs.put("schedule",schedule);
    }

    @Override
    public Map<String,String> getParams(){
        return parametrs;
    }
}
