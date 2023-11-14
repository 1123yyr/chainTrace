package com.example.careold.controller;


import com.example.careold.common.DateConst;
import com.example.careold.common.ReturnCodeUtil;
import com.example.careold.dao.OldPersonDao;
//import com.example.careold.dao.PositionDao;
import com.example.careold.domain.OldPerson;
//import com.example.careold.domain.PositionDto;
import javafx.beans.property.IntegerProperty;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/restful/oldPerson")
public class OldPersonController {

    @Autowired
    OldPersonDao oldPersonDao;


    //家属页面对老人的信息查询
    @ResponseBody
    @RequestMapping("/lifeselect")
    public ListOrderedMap selecto(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String id=param.get("oldId").toString();
        System.out.println("---old");
        List<OldPerson> selectfamilylife = oldPersonDao.selectfamilylife(Integer.parseInt(id));
        result.put("oldPeople",selectfamilylife);
        return result;
    }


    //对老人的查询
    @ResponseBody
    @RequestMapping("/select")
    public ListOrderedMap select(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String name=param.get("name").toString();
        List<OldPerson> oldPeople=oldPersonDao.getOldPerson(name);
        result.put("oldPeople",oldPeople);
        return result;
    }

    @ResponseBody
    @RequestMapping("/add")
    public ListOrderedMap add(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String deviceId=param.get("deviceId").toString();
//        List<WatchDto> watchInfo2 = watchInfoDao.getWatchInfo2(Integer.parseInt(deviceId));
//        if(watchInfo2.size()==1){
            String name=param.get("name").toString();
            String bothdate=param.get("bothdate").toString();
            String bothnian1=bothdate.substring(0,4);
            String sex=param.get("sex").toString();
            String phone=param.get("phone").toString();
            String room=param.get("room").toString();
            String beTime= DateConst.dateToString(new Date(),DateConst.DATE_MODEL_1);
            String bothnian2=beTime.substring(0,4);
            String illness=param.get("illness").toString();
            String warn=param.get("warn").toString();
            String foodLike=param.get("foodLike").toString();
            String foodHate=param.get("foodHate").toString();
            String drag=param.get("drag").toString();
            String customerId=param.get("customerId").toString();
            OldPerson oldPerson=new OldPerson();
            oldPerson.setName(name);
            oldPerson.setAge(Integer.parseInt(bothnian2)-Integer.parseInt(bothnian1));
            oldPerson.setBeTime(beTime);
            oldPerson.setSex(sex);
            oldPerson.setPhone(phone);
            oldPerson.setRoom(room);
            oldPerson.setIllness(illness);
            oldPerson.setWarn(warn);
            oldPerson.setFoodLike(foodLike);
            oldPerson.setFoodHate(foodHate);
            oldPerson.setDrag(drag);
            oldPerson.setBothdate(bothdate);
//            oldPerson.setDeviceId(Integer.parseInt(deviceId));
            oldPerson.setCustomerId(Integer.parseInt(customerId));
            int rows=oldPersonDao.addOldPerson(oldPerson);
            if(rows==1){
                result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
                return result;
            }
//        }

        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

    @ResponseBody
    @RequestMapping("/update")
    public ListOrderedMap update(@RequestBody ListOrderedMap param){
        int familyid = 1000;
        try{
            String  s = param.get("familyId").toString();
            int s1 = Integer.parseInt(s);
            familyid = s1;
        }catch (Exception e){
            System.out.println(e+"---");
            familyid = 999;
        }
        System.out.println(familyid);
        ListOrderedMap result=new ListOrderedMap();
        String name=param.get("name").toString();
        String bothdate=param.get("bothdate").toString();
        String bothnian1=bothdate.substring(0,4);
        String sex=param.get("sex").toString();
        String phone=param.get("phone").toString();
        String room=param.get("room").toString();
        String beTime= DateConst.dateToString(new Date(),DateConst.DATE_MODEL_1);
        String bothnian2=beTime.substring(0,4);
        String illness=param.get("illness").toString();
        String warn=param.get("warn").toString();
        String foodLike=param.get("foodLike").toString();
        String foodHate=param.get("foodHate").toString();
        String drag=param.get("drag").toString();
        String oldId=param.get("oldId").toString();
//        String deviceId=param.get("deviceId").toString();
        String customerId=param.get("customerName").toString();
        if(!ReturnCodeUtil.isNumeric(customerId)){
            customerId=param.get("customerId").toString();
        }
        OldPerson oldPerson=new OldPerson();
        oldPerson.setFamilyId(familyid);
        oldPerson.setName(name);
        oldPerson.setAge(Integer.parseInt(bothnian2)-Integer.parseInt(bothnian1));
//        oldPerson.setBeTime(beTime);
        oldPerson.setSex(sex);
        oldPerson.setPhone(phone);
        oldPerson.setRoom(room);
        oldPerson.setIllness(illness);
        oldPerson.setWarn(warn);
        oldPerson.setFoodLike(foodLike);
        oldPerson.setFoodHate(foodHate);
        oldPerson.setDrag(drag);
        oldPerson.setBothdate(bothdate);
        oldPerson.setOldId(Integer.parseInt(oldId));
//        oldPerson.setDeviceId(Integer.parseInt(deviceId));
        oldPerson.setCustomerId(Integer.parseInt(customerId));
        int rows=oldPersonDao.updateOldPerson(oldPerson);
        if(rows==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public ListOrderedMap delete(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String oldId=param.get("oldId").toString();
        int rows=oldPersonDao.deleteOldPerson(Integer.parseInt(oldId));
        if(rows==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

}
