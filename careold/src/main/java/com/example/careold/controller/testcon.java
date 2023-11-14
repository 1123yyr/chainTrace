package com.example.careold.controller;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/my")
public class testcon {

    @ResponseBody
    @RequestMapping("/sdk/queryAll")
    public ListOrderedMap sfsddf(@RequestBody ListOrderedMap param){
        System.out.println("111111");
        return null;

    }

//    @ResponseBody
//    @RequestMapping("sdk/queryAll")
//    public ListOrderedMap sfsdf(@RequestBody ListOrderedMap param){
//        System.out.println("22222");
//        return null;
//    }
}
