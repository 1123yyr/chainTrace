package com.example.careold.controller;

import com.example.careold.common.DateConst;
import com.example.careold.common.DefineData;
import com.example.careold.common.ReturnCodeUtil;
import com.example.careold.common.SmsUtil;
import com.example.careold.dao.FamilyDao;
import com.example.careold.dao.PicDao;
import com.example.careold.dao.TextDao;
import com.example.careold.domain.FamilyDto;
import com.example.careold.domain.Pic;
import com.example.careold.domain.Text;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
//公告
@Controller
@RequestMapping("/restful/text")
public class TextController {

    @Autowired
    TextDao textDao;

    @Autowired
    PicDao picDao;

    @Autowired
    FamilyDao familyDao;

    //公告查询
    @ResponseBody
    @RequestMapping("/select")
    public ListOrderedMap selectText(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
//        String title=param.get("title").toString();
//        List<Text> texts = textDao.selectTextAll(title);
//        result.put("texts",texts);
        return result;
    }

    @ResponseBody
    @RequestMapping("/selectDetail")
    public ListOrderedMap selectTextDetail(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String textId=param.get("textId").toString();
        Text text=textDao.selectTextDetail(Integer.parseInt(textId));
        List<Pic> pics=picDao.getPic(text.getPicId());
        result.put("text",text);
        result.put("pics",pics);
        return result;
    }

    //添加公告文章接口
    @ResponseBody
    @RequestMapping("/add")
    public ListOrderedMap addText(@RequestBody ListOrderedMap param){
        //创建一个ListOrderedMap对象
        ListOrderedMap result=new ListOrderedMap();
        //获取传来参数的titile
        String title=param.get("title").toString();
        String textMain=param.get("textMain").toString();
        List fileList = (List)param.get("fileListName");
        String time= DateConst.dateToString(new Date(),DateConst.DATE_MODEL_1);
        System.out.println(textDao.getMaxPicId());
        int picId=textDao.getMaxPicId()+1;
        Pic pic1=new Pic();
        //遍历公告
        for(int i=0;i<fileList.size();i++){
            pic1.setPicId(picId);
            pic1.setPicName(fileList.get(i).toString());
            picDao.addPic(pic1);
        }
        Text text=new Text();
        text.setTitle(title);
        text.setTextMain(textMain);
        text.setTime(time);
        text.setPicId(picId);
        int rows=textDao.addText(text);
        if(rows==1){
            List<FamilyDto> familyDtos=familyDao.findFamily(null);
            for(int i=0;i<familyDtos.size();i++){
                SmsUtil.sendMassage(familyDtos.get(i).getFamilyPhone());
            }
            List<Text> texts = textDao.selectTextAll(null);
            result.put("texts",texts);
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions(value = {"上传"})
    public ListOrderedMap deleteText(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String textId=param.get("textId").toString();
        String picId=param.get("picId").toString();
        int rows=textDao.deleteText(Integer.parseInt(textId));
        picDao.deletePic(Integer.parseInt(picId));
        if(rows==1){
            List<Text> texts = textDao.selectTextAll(null);
            result.put("texts",texts);
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

    @ResponseBody
    @RequestMapping("/update")
    public ListOrderedMap updateText(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String title=param.get("title").toString();
        String textMain=param.get("textMain").toString();
        String time= DateConst.dateToString(new Date(),DateConst.DATE_MODEL_1);
        String textId=param.get("textId").toString();
        String picId=param.get("picId").toString();
        List fileList=(List)param.get("fileListName");
        picDao.deletePic(Integer.parseInt(picId));
        Pic pic1=new Pic();
        for(int i=0;i<fileList.size();i++){
            pic1.setPicId(Integer.parseInt(picId));
            pic1.setPicName(fileList.get(i).toString());
            picDao.addPic(pic1);
        }
        Text text=new Text();
        text.setTitle(title);
        text.setTextMain(textMain);
        text.setTime(time);
        text.setTextId(Integer.parseInt(textId));
        int rows=textDao.update(text);
        if(rows==1){
            List<Text> texts = textDao.selectTextAll(null);
            result.put("texts",texts);
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }
}
