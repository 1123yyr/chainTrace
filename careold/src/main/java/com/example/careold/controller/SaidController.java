package com.example.careold.controller;

import com.example.careold.common.DateConst;
import com.example.careold.common.ReturnCodeUtil;
import com.example.careold.dao.NiceCountDao;
import com.example.careold.dao.SaidDao;
import com.example.careold.domain.Said;
import com.example.careold.domain.Said2;
import com.example.careold.domain.SaidDto;
import com.kenai.jaffl.annotations.In;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/restful/said")
public class SaidController {

    @Autowired
    SaidDao saidDao;

    @Autowired
    NiceCountDao niceCountDao;

    @ResponseBody
    @RequestMapping("/select")
    public ListOrderedMap select(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String textId=param.get("textId").toString();
        String familyPhone=param.get("familyPhone").toString();
        List<Said2> s2=saidDao.getSaid(Integer.parseInt(textId));//获取到所有评论
        for(int i=0;i<s2.size();i++){
            if(saidDao.isSaid(s2.get(i).getSaidId(),familyPhone).size()>0){//
                s2.get(i).setGood("1");
            }
            else{
                s2.get(i).setGood("0");
            }
        }
        result.put("said2s",s2);
        return result;
    }

    @ResponseBody
    @RequestMapping("/selectByUser")
    public ListOrderedMap selectByUser(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        List<SaidDto> saidDtos=saidDao.getSaidForAdmin();
        result.put("saidDtos",saidDtos);
        return result;

    }


    @ResponseBody
    @RequestMapping("/delete")
    public ListOrderedMap delete(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String saidId=param.get("saidId").toString();
        int rows=saidDao.deleteSaid(Integer.parseInt(saidId));
        if(rows==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;

    }

    @ResponseBody
    @RequestMapping("/insert")
    public ListOrderedMap insert(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String said=param.get("said").toString();
        String textId=param.get("textId").toString();
        String personName=param.get("personName").toString();
        String phone=param.get("phone").toString();
        String touPic=param.get("touPic").toString();
        String time= DateConst.dateToString(new Date(),DateConst.DATE_MODEL_1);
        Said said1=new Said();
        said1.setSaid(said);
        said1.setTextId(Integer.parseInt(textId));
        said1.setPersonName(personName);
        said1.setSaidPhone(phone);
        said1.setSaidTime(time);
        said1.setTouPic(touPic);
        int rows=saidDao.addSaid(said1);
        if(rows==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;

    }


    @ResponseBody
    @RequestMapping("/update")
    public ListOrderedMap update(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String back=param.get("back").toString();
        String saidId=param.get("saidId").toString();
        Said said1=new Said();
        said1.setSaidId(Integer.parseInt(saidId));
        said1.setBack(back);
        String backTime= DateConst.dateToString(new Date(),DateConst.DATE_MODEL_1);
        said1.setBackTime(backTime);
        int rows=saidDao.updateSaid(said1);
        if(rows==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;

    }



    @ResponseBody
    @RequestMapping("/goodCount")
    public ListOrderedMap goodCount(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        int rows=0;
        String saidId=param.get("saidId").toString();
        String phone=param.get("phone").toString();
        List<String> saidList=niceCountDao.getCount(Integer.parseInt(saidId),phone);
        if(saidList.size()>0){
            rows=niceCountDao.deleteCount(Integer.parseInt(saidId),phone);
            saidDao.updateSaid(saidId,false);
        }else{
            rows=niceCountDao.addCount(Integer.parseInt(saidId),phone);
            saidDao.updateSaid(saidId,true);
        }

        if(rows==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;

    }


}
