package com.example.careold.controller;

import com.example.careold.common.ReturnCodeUtil;
import com.example.careold.dao.FamilyDao;
import com.example.careold.dao.OldPersonDao;
import com.example.careold.dao.UserDao;
import com.example.careold.domain.*;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/restful/family")
public class FamilyController {

    @Autowired
    FamilyDao familyDao;

    @Autowired
    OldPersonDao oldPersonDao;

    @ResponseBody
    @RequestMapping("/select")
    public ListOrderedMap select(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String familyName=param.get("familyName").toString();
        List<FamilyDto> familyDtos= familyDao.findFamily(familyName);
        result.put("familyDtos",familyDtos);
        return result;
    }


    @ResponseBody
    @RequestMapping("/add")
    public ListOrderedMap add(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        String familyName=params.get("familyName").toString();
        String familyRelation=params.get("familyRelation").toString();
        String familySex=params.get("familySex").toString();
        String familyPhone=params.get("familyPhone").toString();
        String oldPhone=params.get("oldPhone").toString();
        String familyAddress=params.get("familyAddress").toString();
        Family family=new Family();
        family.setFamilyAddress(familyAddress);
        family.setFamilyPhone(familyPhone);
        family.setFamilySex(familySex);
        family.setFamilyName(familyName);
        family.setFamilyRelation(familyRelation);
        int row=familyDao.save(family);
        OldDto2 oldDto2=familyDao.findOldDto2ByPhone(oldPhone);
        String familyId=familyDao.findFamilyIdByPhone(familyPhone);
        oldPersonDao.updateOldPerson2(oldDto2.getOldId(),Integer.parseInt(familyId));
        if(row==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

    @ResponseBody
    @RequestMapping("/updateFamily")
    public ListOrderedMap updateFamily(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        String familyName=params.get("familyName").toString();
        String familyRelation=params.get("familyRelation").toString();
        String familySex=params.get("familySex").toString();
        String familyPhone=params.get("familyPhone").toString();
        String familyAddress=params.get("familyAddress").toString();
        String familyId=params.get("familyId").toString();
        Family family=new Family();
        family.setFamilyAddress(familyAddress);
        family.setFamilyPhone(familyPhone);
        family.setFamilySex(familySex);
        family.setFamilyName(familyName);
        family.setFamilyRelation(familyRelation);
        family.setFamilyId(Integer.parseInt(familyId));
        int row=familyDao.update(family);
        if(row==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

    @ResponseBody
    @RequestMapping("/deleteFamily")
    public ListOrderedMap deleteFamily(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        String familyId=params.get("familyId").toString();
        int row=familyDao.delete(familyId);
        if(row==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

    @ResponseBody
    @RequestMapping("/selectTwo")
    public ListOrderedMap selectTwo(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        String familyPhone=params.get("familyPhone").toString();
        FamilyDto2 familyDto2 =familyDao.getFamilyByPhoneForFamily(familyPhone);

        result.put("familyDto2",familyDto2);
        return result;
    }

    @ResponseBody
    @RequestMapping("/updateTwo")
    public ListOrderedMap updateTwo(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        String familyPhone=params.get("familyPhone").toString();
        String familyName=params.get("familyName").toString();
        String familySex=params.get("familySex").toString();
        String familyId=params.get("familyId").toString();
        String familyAddress=params.get("familyAddress").toString();
        FamilyDto2 familyDto2=new FamilyDto2();
        familyDto2.setFamilyAddress(familyAddress);
        familyDto2.setFamilyName(familyName);
        familyDto2.setFamilySex(familySex);
        familyDto2.setFamilyPhone(familyPhone);
        familyDto2.setFamilyId(Integer.parseInt(familyId));
        int rows=familyDao.updateFamilyByPhoneForFamily(familyDto2);

        if(rows==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

    @ResponseBody
    @RequestMapping("/updateTouPic")
    public ListOrderedMap updateTouPic(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        String familyPhone=params.get("familyPhone").toString();
        String familyTouPic=params.get("familyTouPic").toString();
        int rows=familyDao.updateFamilyTouPic(familyTouPic,familyPhone);
        if(rows==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

    @ResponseBody
    @RequestMapping("/updatePassword")
    public ListOrderedMap updatePassword(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        String familyPhone=params.get("familyPhone").toString();
        String familyPassword=params.get("familyPassword").toString();
        int rows=familyDao.updateFamilyPasswordByPhoneForFamily(familyPassword,familyPhone);
        if(rows==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

}
