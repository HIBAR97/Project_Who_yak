package com.example.project_who_yak;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ScheduleModRequest extends StringRequest {
    final static private String URL ="http://whoyak.dothome.co.kr/ScheduleMod.php";
    private Map<String, String> parametrs;

    public ScheduleModRequest(String after_schedule,String userID, String before_schedule,  Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parametrs = new HashMap<>();
        parametrs.put("after_schedule",after_schedule);
        parametrs.put("userID",userID);
        parametrs.put("before_schedule",before_schedule);
    }

    @Override
    public Map<String,String> getParams(){
        return parametrs;
    }
}
