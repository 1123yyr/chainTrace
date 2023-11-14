package com.example.careold.controller;

import com.example.careold.common.ReturnCodeUtil;
import com.example.careold.dao.SaleJdbcDao;
import com.example.careold.domain.Sale;
import com.example.careold.domain.Transport;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.beans.IntrospectionException;
import java.util.List;

@Controller
@RequestMapping("/restful/sale")
public class SaleController {

    @Autowired
    private SaleJdbcDao saleJdbcDao;

    //添加生产商
    @ResponseBody
    @RequestMapping("/add")
    public ListOrderedMap add(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        String saccount=params.get("saccount").toString();
        String sname=params.get("sname").toString();
        String semail=params.get("semail").toString();
        String stime=params.get("stime").toString();
        Sale sale = new Sale();
        sale.setSaccount(saccount);
        sale.setSname(sname);
        sale.setSemail(semail);
        sale.setStime(stime);
        int row = saleJdbcDao.save(sale);
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
    public ListOrderedMap updateSale(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        String saccount=params.get("saccount").toString();
        String sname=params.get("sname").toString();
        String semail=params.get("semail").toString();
        String stime=params.get("stime").toString();
        Sale sale = new Sale();
        sale.setSaccount(saccount);
        sale.setSname(sname);
        sale.setSemail(semail);
        sale.setStime(stime);
        int row = saleJdbcDao.update(sale);
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
        String saccount=param.get("saccount").toString();
        Sale sale = saleJdbcDao.findByAccount(saccount);
        result.put("sale",sale);
        return result;
    }

    @ResponseBody
    @RequestMapping("/selectAll")
    public ListOrderedMap selectAll(){
        ListOrderedMap result=new ListOrderedMap();
        List<Sale> sales = saleJdbcDao.findAll();
        result.put("sales",sales);
        return result;
    }

    @ResponseBody
    @RequestMapping("/deleteByAccount")
    public ListOrderedMap deleteByAccount(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String saccount=param.get("saccount").toString();
        int row = saleJdbcDao.deleteByAccount(saccount);
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
        String spassword=param.get("spassword").toString();
        String saccount=param.get("saccount").toString();
        Sale sale = new Sale();
        sale.setSaccount(saccount);
        sale.setSpassword(spassword);
        int row = saleJdbcDao.updatePassword(sale);
        if(row==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

}
