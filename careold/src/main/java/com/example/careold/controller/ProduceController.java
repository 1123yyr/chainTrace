package com.example.careold.controller;

import com.example.careold.common.ReturnCodeUtil;
import com.example.careold.dao.ProduceJdbcDao;
import com.example.careold.domain.Produce;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/restful/produce")
public class ProduceController {

    @Autowired
    private ProduceJdbcDao produceJdbcDao;

    //添加生产商
    @ResponseBody
    @RequestMapping("/add")
    public ListOrderedMap add(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        String paccount=params.get("paccount").toString();
        String pname=params.get("pname").toString();
        String pemail=params.get("pemail").toString();
        String ptime=params.get("ptime").toString();
        Produce produce = new Produce();
        produce.setPaccount(paccount);
        produce.setPname(pname);
        produce.setPemail(pemail);
        produce.setPtime(ptime);
        int row = produceJdbcDao.save(produce);
        if(row==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

    //根据账户更新生产商
    @ResponseBody
    @RequestMapping("/update")
    public ListOrderedMap updateProduce(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        String paccount=params.get("paccount").toString();
        String pname=params.get("pname").toString();
        String pemail=params.get("pemail").toString();
        String ptime=params.get("ptime").toString();
        Produce produce = new Produce();
        produce.setPaccount(paccount);
        produce.setPname(pname);
        produce.setPemail(pemail);
        produce.setPtime(ptime);
        int row = produceJdbcDao.update(produce);
        if(row==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }


    @ResponseBody
    @RequestMapping("/selectByAccount")
    public ListOrderedMap select(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String paccount=param.get("paccount").toString();
        Produce produce = produceJdbcDao.findByAccount(paccount);
        result.put("produce",produce);
        return result;
    }

    @ResponseBody
    @RequestMapping("/selectAll")
    public ListOrderedMap selectAll(){
        ListOrderedMap result=new ListOrderedMap();
        List<Produce> produces = produceJdbcDao.findAll();
        result.put("produces",produces);
        return result;
    }

    @ResponseBody
    @RequestMapping("/deleteByAccount")
    public ListOrderedMap deleteByAccount(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String paccount=param.get("paccount").toString();
        int row = produceJdbcDao.deleteByAccount(paccount);
        if(row==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

    @ResponseBody
    @RequestMapping("/updatePassword")
    public ListOrderedMap updatePassword(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String ppassword=param.get("ppassword").toString();
        String paccount=param.get("paccount").toString();
        Produce produce = new Produce();
        produce.setPaccount(paccount);
        produce.setPpassword(ppassword);
        int row = produceJdbcDao.updatePassword(produce);
        if(row==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

}
