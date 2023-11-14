package com.example.careold.controller;

import com.example.careold.common.ReturnCodeUtil;
import com.example.careold.dao.TransportJdbcDao;
import com.example.careold.domain.Produce;
import com.example.careold.domain.Transport;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/restful/transport")
public class TransportController {

    @Autowired
    private TransportJdbcDao transportJdbcDao;

    //添加生产商
    @ResponseBody
    @RequestMapping("/add")
    public ListOrderedMap add(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        String taccount=params.get("taccount").toString();
        String tname=params.get("tname").toString();
        String temail=params.get("temail").toString();
        String ttime=params.get("ttime").toString();
        Transport transport = new Transport();
        transport.setTaccount(taccount);
        transport.setTname(tname);
        transport.setTemail(temail);
        transport.setTtime(ttime);
        int row = transportJdbcDao.save(transport);
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
    public ListOrderedMap updateTransport(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        String taccount=params.get("taccount").toString();
        String tname=params.get("tname").toString();
        String temail=params.get("temail").toString();
        String ttime=params.get("ttime").toString();
        Transport transport = new Transport();
        transport.setTaccount(taccount);
        transport.setTname(tname);
        transport.setTemail(temail);
        transport.setTtime(ttime);
        int row = transportJdbcDao.update(transport);
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
        String taccount=param.get("taccount").toString();
        Transport transport = transportJdbcDao.findByAccount(taccount);
        result.put("transport",transport);
        return result;
    }

    @ResponseBody
    @RequestMapping("/selectAll")
    public ListOrderedMap selectAll(){
        ListOrderedMap result=new ListOrderedMap();
        List<Transport> transports = transportJdbcDao.findAll();
        result.put("transports",transports);
        return result;
    }

    @ResponseBody
    @RequestMapping("/deleteByAccount")
    public ListOrderedMap deleteByAccount(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String taccount=param.get("taccount").toString();
        int row = transportJdbcDao.deleteByAccount(taccount);
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
        String tpassword=param.get("tpassword").toString();
        String taccount=param.get("taccount").toString();
        Transport transport = new Transport();
        transport.setTaccount(taccount);
        transport.setTpassword(tpassword);
        int row = transportJdbcDao.updatePassword(transport);
        if(row==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }
}
