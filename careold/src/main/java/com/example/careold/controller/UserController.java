package com.example.careold.controller;

import com.example.careold.dao.TextDao;
import com.example.careold.dao.UserDao;
import com.example.careold.domain.Text;
import com.example.careold.domain.Users;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/restful/user")
public class UserController {

    @Autowired
    UserDao userDao;

    @ResponseBody
    @RequestMapping("/select")
    public ListOrderedMap selectText(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String userPhone=param.get("userPhone").toString();
        List<Users> usersByPhone2 = userDao.getUsersByPhone2(userPhone);
        System.out.println(usersByPhone2.size());
        result.put("usersByPhone2",usersByPhone2);
        return result;
    }
}
