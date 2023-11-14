package com.example.careold.controller;

import com.example.careold.dao.OldPersonDao;
import com.example.careold.dao.OldPersonDetailDao;
import com.example.careold.domain.OldPerson;
import com.example.careold.domain.OldPersonDetail;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/restful/oldPerson")
public class OldPersonDetailController {

    @Autowired
    OldPersonDetailDao oldPersonDetailDao;

    @ResponseBody
    @RequestMapping("/selectDetail")
    public ListOrderedMap select(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();
        String name=param.get("name").toString();
        List<OldPersonDetail> oldPersonDetails=oldPersonDetailDao.getOldPersonDetail(name);
        System.out.println(oldPersonDetails);
        result.put("oldPersonDetails",oldPersonDetails);
        return result;

    }
}
