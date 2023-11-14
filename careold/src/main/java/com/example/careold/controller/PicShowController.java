package com.example.careold.controller;

import com.example.careold.common.ReturnCodeUtil;
import com.example.careold.dao.NiceCountDao;
import com.example.careold.dao.PicShowDao;
import com.example.careold.domain.PicShow;
import com.example.careold.domain.Said2;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/restful/picshow")
public class  PicShowController {

    @Autowired
    PicShowDao picShowDao;

    @ResponseBody
    @RequestMapping("/add")
    public ListOrderedMap add(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        int rows=0;
        picShowDao.delete(null);
        List fileList = (List)param.get("fileListName");
        PicShow picShow=new PicShow();
        for(int i=0;i<fileList.size();i++){
            picShow.setPicName(fileList.get(i).toString());
            rows=picShowDao.save(picShow);
        }
        if(rows>1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;

    }

    @ResponseBody
    @RequestMapping("/select")
    public ListOrderedMap select(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        List<PicShow> picShows= picShowDao.findById(null);
        System.out.println(picShows);
        result.put("picShows",picShows);
        return result;

    }

}
