package com.example.careold.controller;

import com.example.careold.common.ReturnCodeUtil;
import com.example.careold.dao.CustomerDao;
import com.example.careold.domain.Cost;
import com.example.careold.domain.Customer;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/restful/customer")
public class CustomerController {

    @Autowired
    CustomerDao customerDao;


    @ResponseBody
    @RequestMapping("/findAllCostByCustomerId")
    public  ListOrderedMap findAllCostByCustomerId(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        String id=params.get("customerId").toString();
        List<Cost> allCostByCustomerId = customerDao.findAllCostByCustomerId(Integer.parseInt(id));
        result.put("customers",allCostByCustomerId);
        return result;
    }


    @ResponseBody
    @RequestMapping("/select")
    public ListOrderedMap select(@RequestBody ListOrderedMap params){
        System.out.println("进入");
        ListOrderedMap result=new ListOrderedMap();
        String customerName=params.get("customerName").toString();
        List<Customer> customers=customerDao.getCustomer(customerName);
        result.put("customers",customers);
        return result;
    }

    @ResponseBody
    @RequestMapping("/add")
    public ListOrderedMap add(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        int customerId=customerDao.getMaxId()+1;
        String customerName=params.get("customerName").toString();
        String customerAge=params.get("customerAge").toString();
        String customerSex=params.get("customerSex").toString();
        String customerPhone=params.get("customerPhone").toString();
        String customerAddress=params.get("customerAddress").toString();
        Customer customers=new Customer();
        customers.setCustomerId(customerId);
        customers.setCustomerName(customerName);
        customers.setCustomerAge(Integer.parseInt(customerAge));
        customers.setCustomerSex(customerSex);
        customers.setCustomerPhone(customerPhone);
        customers.setCustomerAddress(customerAddress);
        int row=customerDao.addCustomer(customers);
        if(row==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

    @ResponseBody
    @RequestMapping("/update")
    public ListOrderedMap update(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        String customerId=params.get("customerId").toString();
        String customerName=params.get("customerName").toString();
        String customerAge=params.get("customerAge").toString();
        String customerSex=params.get("customerSex").toString();
        String customerPhone=params.get("customerPhone").toString();
        String customerAddress=params.get("customerAddress").toString();
        Customer customers=new Customer();
        customers.setCustomerId(Integer.parseInt(customerId));
        customers.setCustomerName(customerName);
        customers.setCustomerAge(Integer.parseInt(customerAge));
        customers.setCustomerSex(customerSex);
        customers.setCustomerPhone(customerPhone);
        customers.setCustomerAddress(customerAddress);
        int row=customerDao.updateCustomer(customers);
        if(row==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public ListOrderedMap delete(@RequestBody ListOrderedMap params){
        ListOrderedMap result=new ListOrderedMap();
        String customerId=params.get("customerId").toString();
        int row=customerDao.deleteCustomer(Integer.parseInt(customerId));
        if(row==1){
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }
}
