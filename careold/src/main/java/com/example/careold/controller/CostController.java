package com.example.careold.controller;

import com.example.careold.common.ReturnCodeUtil;
import com.example.careold.dao.CostJdbcDao;
import com.example.careold.domain.Cost;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/restful/cost")
public class CostController {

    private String http = "http://localhost:8081/sdk";



    @Autowired
    private RestTemplate restTemplate;

    @Resource
    CostJdbcDao costJdbcDao;


    @ResponseBody
    @RequestMapping("/select")
    public ListOrderedMap select(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String familyName=param.get("familyName").toString();
        List<Cost> costs= costJdbcDao.findFamily(familyName);
        result.put("costs",costs);
        return result;
    }

    @ResponseBody
    @RequestMapping("/add")
    public ListOrderedMap add(@RequestBody ListOrderedMap params) {
        ListOrderedMap result = new ListOrderedMap();
        int oldid = Integer.parseInt(params.get("oldid").toString());
        String thing = params.get("thing").toString();
        String time = params.get("time").toString();
        int money= Integer.parseInt(params.get("money").toString());
        String name= params.get("name").toString();
        String orderstatus = params.get("orderstatus").toString();
        Cost cost = new Cost();
        cost.setOldid(oldid);
        cost.setThing(thing);
        cost.setTime(time);
        cost.setMoney(money);
        cost.setName(name);
        cost.setOrderstatus(orderstatus);


        try {
            //此时返回的row是olderid
                int row = costJdbcDao.save(cost);
                System.out.println("----cost");
                if(row>0) {
//                    List<Cost> data = costJdbcDao.findFamily(params.get("name").toString());
//                    System.out.println(data);

                    String role="管理员";
                    String URL_invoke = http+"/invoke?id="+row+"&data=\"orderid\":\""+row+"\",\"oldid\":\""+params.get("oldid")+"\",\"name\":\""+params.get("name")+"\",\"thing\":\""+params.get("thing")+"\",\"time\":\""+params.get("time")+"\",\"money\":\""+params.get("money")+"\",\"orderstatus\":\""+params.get("orderstatus")+"\",\"role\":\""+role+"\"";
                    ResponseEntity<String> forEntity = restTemplate.getForEntity(URL_invoke, String.class);
                    System.out.println(forEntity);
                    result.put(ReturnCodeUtil.returnCode, ReturnCodeUtil.successCode);
                    return result;
                }
            }catch(Exception e){
                System.out.println(e);
            }



        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

    @ResponseBody
    @RequestMapping("/familyadd")
    public ListOrderedMap familyadd(@RequestBody ListOrderedMap params) {
        ListOrderedMap result = new ListOrderedMap();
        int oldid = Integer.parseInt(params.get("oldid").toString());
        String thing = params.get("thing").toString();
        String time = params.get("time").toString();
        int money= Integer.parseInt(params.get("money").toString());
        String name= params.get("name").toString();
        String orderstatus = params.get("orderstatus").toString();
        Cost cost = new Cost();
        cost.setOldid(oldid);
        cost.setThing(thing);
        cost.setTime(time);
        cost.setMoney(money);
        cost.setName(name);
        cost.setOrderstatus(orderstatus);


        try {
            //此时返回的row是olderid
            int row = costJdbcDao.save(cost);
            System.out.println("----cost");
            if(row>0) {
//                    List<Cost> data = costJdbcDao.findFamily(params.get("name").toString());
//                    System.out.println(data);

                String role="家属";
                String URL_invoke = http+"/invoke?id="+row+"&data=\"orderid\":\""+row+"\",\"oldid\":\""+params.get("oldid")+"\",\"name\":\""+params.get("name")+"\",\"thing\":\""+params.get("thing")+"\",\"time\":\""+params.get("time")+"\",\"money\":\""+params.get("money")+"\",\"orderstatus\":\""+params.get("orderstatus")+"\",\"role\":\""+role+"\"";
                ResponseEntity<String> forEntity = restTemplate.getForEntity(URL_invoke, String.class);
                System.out.println(forEntity);
                result.put(ReturnCodeUtil.returnCode, ReturnCodeUtil.successCode);
                return result;
            }
        }catch(Exception e){
            System.out.println(e);
        }



        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

    @ResponseBody
    @RequestMapping("/updateCost")
    public ListOrderedMap updateCost(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        Cost cost = new Cost();
        cost.setName(params.get("name").toString());
        cost.setMoney(Integer.parseInt(params.get("money").toString()));
        cost.setTime(params.get("time").toString());
        cost.setThing(params.get("thing").toString());
        cost.setOldid(Integer.parseInt(params.get("oldid").toString()));
        cost.setOrderid(Integer.parseInt(params.get("orderid").toString()));
        cost.setOrderstatus(params.get("orderstatus").toString());
        System.out.println(cost);
        try {
            int row = costJdbcDao.update(cost);
            if (row == 1) {
                result.put(ReturnCodeUtil.returnCode, ReturnCodeUtil.successCode);
                String role="管理员";
                String URL_invoke = http+"/invoke?id="+params.get("orderid")+"&data=\"orderid\":\""+params.get("orderid")+"\",\"oldid\":\""+params.get("oldid")+"\",\"name\":\""+params.get("name")+"\",\"thing\":\""+params.get("thing")+"\",\"time\":\""+params.get("time")+"\",\"money\":\""+params.get("money")+"\",\"orderstatus\":\""+params.get("orderstatus")+"\",\"role\":\""+role+"\"";
                ResponseEntity<String> forEntity = restTemplate.getForEntity(URL_invoke, String.class);
                System.out.println(forEntity.toString());
                return result;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }
    @ResponseBody
    @RequestMapping("/familyupdateCost")
    public ListOrderedMap familyupdateCost(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        Cost cost = new Cost();
        cost.setName(params.get("name").toString());
        cost.setMoney(Integer.parseInt(params.get("money").toString()));
        cost.setTime(params.get("time").toString());
        cost.setThing(params.get("thing").toString());
        cost.setOldid(Integer.parseInt(params.get("oldid").toString()));
        cost.setOrderid(Integer.parseInt(params.get("orderid").toString()));
        cost.setOrderstatus(params.get("orderstatus").toString());
        System.out.println(cost);
        try {
            int row = costJdbcDao.update(cost);
            if (row == 1) {
                result.put(ReturnCodeUtil.returnCode, ReturnCodeUtil.successCode);
                String role="家属";
                String URL_invoke = http+"/invoke?id="+params.get("orderid")+"&data=\"orderid\":\""+params.get("orderid")+"\",\"oldid\":\""+params.get("oldid")+"\",\"name\":\""+params.get("name")+"\",\"thing\":\""+params.get("thing")+"\",\"time\":\""+params.get("time")+"\",\"money\":\""+params.get("money")+"\",\"orderstatus\":\""+params.get("orderstatus")+"\",\"role\":\""+role+"\"";
                ResponseEntity<String> forEntity = restTemplate.getForEntity(URL_invoke, String.class);
                System.out.println(forEntity.toString());
                return result;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }
    @ResponseBody
    @RequestMapping("/customerupdateCost")
    public ListOrderedMap customerupdateCost(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        Cost cost = new Cost();
        cost.setName(params.get("name").toString());
        cost.setMoney(Integer.parseInt(params.get("money").toString()));
        cost.setTime(params.get("time").toString());
        cost.setThing(params.get("thing").toString());
        cost.setOldid(Integer.parseInt(params.get("oldid").toString()));
        cost.setOrderid(Integer.parseInt(params.get("orderid").toString()));
        cost.setOrderstatus(params.get("orderstatus").toString());
        System.out.println(cost);
        try {
            int row = costJdbcDao.update(cost);
            if (row == 1) {
                result.put(ReturnCodeUtil.returnCode, ReturnCodeUtil.successCode);
                String role="护士";
                String URL_invoke = http+"/invoke?id="+params.get("orderid")+"&data=\"orderid\":\""+params.get("orderid")+"\",\"oldid\":\""+params.get("oldid")+"\",\"name\":\""+params.get("name")+"\",\"thing\":\""+params.get("thing")+"\",\"time\":\""+params.get("time")+"\",\"money\":\""+params.get("money")+"\",\"orderstatus\":\""+params.get("orderstatus")+"\",\"role\":\""+role+"\"";
                ResponseEntity<String> forEntity = restTemplate.getForEntity(URL_invoke, String.class);
                System.out.println(forEntity.toString());
                return result;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }
    @ResponseBody
    @RequestMapping("/delete")
    public ListOrderedMap delete(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        String id = params.get("orderid").toString();

        int row = costJdbcDao.deleted(Integer.parseInt(id));
        if(row==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            String URL_delete = http+"/delete?id="+id;
            ResponseEntity<String> forEntity = restTemplate.getForEntity(URL_delete, String.class);
            System.out.println(forEntity.toString());
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

    //家属页面订单显示
    @ResponseBody
    @RequestMapping("/familyOrdershow")
    public ListOrderedMap familyOrdershow(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        String id=params.get("oldId").toString();
        System.out.println("---old");
        List<Cost> selectfamilyOrder = costJdbcDao.familyselect(Integer.parseInt(id));
        result.put("order",selectfamilyOrder);
        return result;
    }




}
