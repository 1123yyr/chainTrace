package com.fabric.utils;

import com.alibaba.fastjson.JSON;
import com.fabric.po.Cost;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MyUtils {

    public  List<Cost> toListMap(String json){
        List<Object> list = JSON.parseArray(json);

        List<Map<String,Object>> listw = new ArrayList<Map<String,Object>>();
        for (Object object : list){
            Map<String,Object> ageMap = new HashMap<String,Object>();
            Map <String,Object> ret = (Map<String, Object>) object;//取出list里面的值转为map
            listw.add(ret);
        }

        List<Cost> costs = new ArrayList<>();
        for(int i = 0;i<listw.size();i++){
            Cost cost = new Cost();
            Map<String, Object> s  = (Map<String, Object>) list.get(i);
            String tid = (String) s.get("TID");
            String ttime = (String) s.get("TTime");
            String IsDelete = (String) s.get("IsDelete");
            Object obj = s.get("THistory");
            cost.setTID(tid);
            cost.setIsDelete(IsDelete);
            cost.setTTime(ttime);
            if(null !=obj){
                String sss = obj.toString();
                Gson gson = new Gson();
                Map<String, Object> map = new HashMap<String, Object>();
                map = gson.fromJson(sss, map.getClass());
                cost.setOrderid((String) map.get("orderid"));
                cost.setRole((String) map.get("role"));
                cost.setMoney((String) map.get("money"));
                cost.setThing((String) map.get("thing"));
                cost.setName((String) map.get("name"));
                cost.setOldid((String) map.get("oldid"));
                cost.setOrderstatus((String) map.get("orderstatus"));
                cost.setTime((String) map.get("time"));
                costs.add(cost);
            }else {
                cost.setRole("管理员");
                cost.setOrderstatus("已删除");
                costs.add(cost);
            }

        }
        for(int i = 0;i<costs.size();i++){
            System.out.println(costs.get(i));
        }
        return costs;
    }

}
