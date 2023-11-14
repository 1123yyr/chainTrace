package com.fabric.utils;

import com.alibaba.fastjson.JSON;
import com.fabric.po.Cost;
import com.fabric.po.Trace;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MyUtils1 {

    public  List<Trace> toListMap(String json){
        List<Object> list = JSON.parseArray(json);

        List<Map<String,Object>> listw = new ArrayList<Map<String,Object>>();
        for (Object object : list){
            Map<String,Object> ageMap = new HashMap<String,Object>();
            Map <String,Object> ret = (Map<String, Object>) object;//取出list里面的值转为map
            listw.add(ret);
        }
        System.out.println("listw:"+listw);
        List<Trace> traces = new ArrayList<>();
        for(int i = 0;i<listw.size();i++){
            Trace trace = new Trace();
            Map<String, Object> s  = (Map<String, Object>) list.get(i);
            String tid = (String) s.get("TID");
            String ttime = (String) s.get("TTime");
            String IsDelete = (String) s.get("IsDelete");
            Object obj = s.get("THistory");
            trace.setTID(tid);
            trace.setIsDelete(IsDelete);
            trace.setTTime(ttime);
            if(null !=obj){
                String sss = obj.toString();
                Gson gson = new Gson();
                Map<String, Object> map = new HashMap<String, Object>();
                map = gson.fromJson(sss, map.getClass());
                trace.setTraid((String) map.get("traid"));
                trace.setPname((String) map.get("pname"));
                trace.setPaddress((String) map.get("paddress"));
                trace.setPperson((String) map.get("pperson"));
                trace.setPtime((String) map.get("ptime"));
                trace.setTarriveaddress((String) map.get("tarriveaddress"));
                trace.setTbeginaddress((String) map.get("tbeginaddress"));
                trace.setTname((String) map.get("tname"));
                trace.setTperson((String) map.get("tperson"));
                trace.setSaddress((String) map.get("saddress"));
                trace.setSname((String) map.get("sname"));
                trace.setSperson((String) map.get("sperson"));
                trace.setStime((String) map.get("stime"));
                trace.setRole((String) map.get("role"));
                traces.add(trace);
            }else {
                trace.setRole("管理员");
                traces.add(trace);
            }

        }
        for(int i = 0;i<traces.size();i++){
            System.out.println(traces.get(i));
        }
        return traces;
    }

}
