package com.fabric;


import com.alibaba.fastjson.JSON;
import com.fabric.po.Cost;
import com.fabric.utils.MyUtils;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.*;

public class mytest {

    public static List<Cost> toListMap(String json){
        List<Object> list = JSON.parseArray(json);

        List< Map<String,Object>> listw = new ArrayList<Map<String,Object>>();
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
        }
        for(int i = 0;i<costs.size();i++){
            System.out.println(costs.get(i));
        }
        return costs;
    }


    @Test
    public void tes00(){
        String ss ="[{\"TID\":\"cf3512ae545d1d2edb9fbbdd64cb0a4b4f9301ada5ee134d3a7f450415a84d83\", \"THistory\":{\"orderid\":\"25\",\"oldid\":\"3\",\"name\":\"田风\",\"thing\":\"为我爸做一次体检\",\"time\":\"2020-12-12 3:12\",\"money\":\"0\",\"orderstatus\":\"未完成\",\"role\":\"家属\"}, \"TTime\":\"2021-04-27 02:54:54.199 +0000 UTC\", \"IsDelete\":\"false\"},{\"TID\":\"e230c79d377c3e564070955db2df648217c817b8ed46fd16f66479a547e7ca1a\", \"THistory\":{\"orderid\":\"25\",\"oldid\":\"3\",\"name\":\"田风\",\"thing\":\"为我爸做一次体检\",\"time\":\"2020-12-12 3:12\",\"money\":\"200\",\"orderstatus\":\"已完成\",\"role\":\"护士\"}, \"TTime\":\"2021-04-27 02:58:28.247 +0000 UTC\", \"IsDelete\":\"false\"}]";
            String s ="[{\"id\":\"1\",\"name\":\"Json技术\"},{\"id\":\"2\"}]";
//            toListMap(ss);
//        String jsonString = "{\"role\":\"护士\",\"money\":\"200\",\"orderstatus\":\"已完成\",\"orderid\":\"25\",\"name\":\"田风\",\"time\":\"2020-12-12 3:12\",\"oldid\":\"3\",\"thing\":\"为我爸做一次体检\"}";
//        Gson gson = new Gson();
//         Map<String, Object> map = new HashMap<String, Object>();
//         map = gson.fromJson(jsonString, map.getClass());
//        String goodsid=(String) map.get("role");
//        System.out.println("map的值为:"+goodsid);
//        MyUtils.toListMap(ss);

    }
}
