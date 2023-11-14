package com.example.careold.controller;

import ch.qos.logback.classic.db.names.TableName;
import com.example.careold.common.ReturnCodeUtil;
import com.example.careold.dao.TraceJdbcDao;
import com.example.careold.domain.Trace;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/restful/trace")
public class TraceController {

    @Autowired
    RestTemplate restTemplate;


    private String http = "http://localhost:8081/sdk";

    @Autowired
    TraceJdbcDao traceJdbcDao;

    //生产商订单记录
    @ResponseBody
    @RequestMapping("/produceAdd")
    public ListOrderedMap produceAdd(@RequestBody ListOrderedMap params){
        ListOrderedMap result = new ListOrderedMap();
        String pname = params.get("pname").toString();
        String paddress = params.get("paddress").toString();
        String ptime = params.get("ptime").toString();
        String pperson = params.get("pperson").toString();
        Trace trace = new Trace();
        trace.setPname(pname);
        trace.setPaddress(paddress);
        trace.setPtime(ptime);
        trace.setPperson(pperson);
        int row = traceJdbcDao.produceAdd(trace);
        if(row>0){
            Trace trace1 = traceJdbcDao.findById(row);
            String s ="\"traid\":\""+row
                    +"\",\"pname\":\""+pname
                    +"\",\"paddress\":\""+paddress
                    +"\",\"ptime\":\""+ptime
                    +"\",\"pperson\":\""+pperson
                    +"\",\"tperson\":\""+trace1.getTperson()
                    +"\",\"tbeginaddress\":\""+trace1.getTbeginaddress()
                    +"\",\"tarriveaddress\":\""+trace1.getTarriveaddress()
                    +"\",\"tname\":\""+trace1.getTname()
                    +"\",\"sperson\":\""+trace1.getSperson()
                    +"\",\"stime\":\""+trace1.getStime()
                    +"\",\"saddress\":\""+trace1.getSaddress()
                    +"\",\"sname\":\""+trace1.getSname()
                    +"\",\"role\":\""+"生产商"+"\"";
            System.out.println("row:"+row);
            String URL_invoke = http+"/invoke?id="+row+"&data="+s;
            ResponseEntity<String> forEntity = restTemplate.getForEntity(URL_invoke, String.class);
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }


    //生产商订单更新
    @ResponseBody
    @RequestMapping("/produceUpdate")
    public ListOrderedMap produceUpdate(@RequestBody ListOrderedMap params){
        ListOrderedMap result = new ListOrderedMap();
        String pname = params.get("pname").toString();
        String paddress = params.get("paddress").toString();
        String ptime = params.get("ptime").toString();
        String pperson = params.get("pperson").toString();
        int traid = Integer.valueOf(params.get("traid").toString());
        Trace trace = new Trace();
        trace.setPname(pname);
        trace.setPaddress(paddress);
        trace.setPtime(ptime);
        trace.setPperson(pperson);
        trace.setTraid(traid);
        int row = traceJdbcDao.produceUpdate(trace);
        if(row==1){

            Trace trace1 = traceJdbcDao.findById(traid);
            String s ="\"traid\":\""+traid
                    +"\",\"pname\":\""+pname
                    +"\",\"paddress\":\""+paddress
                    +"\",\"ptime\":\""+ptime
                    +"\",\"pperson\":\""+pperson
                    +"\",\"tperson\":\""+trace1.getTperson()
                    +"\",\"tbeginaddress\":\""+trace1.getTbeginaddress()
                    +"\",\"tarriveaddress\":\""+trace1.getTarriveaddress()
                    +"\",\"tname\":\""+trace1.getTname()
                    +"\",\"sperson\":\""+trace1.getSperson()
                    +"\",\"stime\":\""+trace1.getStime()
                    +"\",\"saddress\":\""+trace1.getSaddress()
                    +"\",\"sname\":\""+trace1.getSname()
                    +"\",\"role\":\""+"生产商"+"\"";

            String URL_invoke = http+"/invoke?id="+traid+"&data="+s;
            ResponseEntity<String> forEntity = restTemplate.getForEntity(URL_invoke, String.class);
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }


    //运输商订单记录
    @ResponseBody
    @RequestMapping("/transportUpdate")
    public ListOrderedMap transportUpdate(@RequestBody ListOrderedMap params){
        ListOrderedMap result = new ListOrderedMap();
        int traid = Integer.valueOf(params.get("traid").toString());
        String tperson = params.get("tperson").toString();
        String tbeginaddress = params.get("tbeginaddress").toString();
        String tarriveaddress = params.get("tarriveaddress").toString();
        String tname = params.get("tname").toString();
        Trace trace = new Trace();
        trace.setTperson(tperson);
        trace.setTbeginaddress(tbeginaddress);
        trace.setTarriveaddress(tarriveaddress);
        trace.setTname(tname);
        trace.setTraid(traid);
        int row = traceJdbcDao.transportUpdate(trace);
        if(row==1){

            Trace trace1 = traceJdbcDao.findById(traid);
            String s ="\"traid\":\""+traid
                    +"\",\"pname\":\""+trace1.getPname()
                    +"\",\"paddress\":\""+trace1.getPaddress()
                    +"\",\"ptime\":\""+trace1.getPtime()
                    +"\",\"pperson\":\""+trace1.getPperson()
                    +"\",\"tperson\":\""+tperson
                    +"\",\"tbeginaddress\":\""+tbeginaddress
                    +"\",\"tarriveaddress\":\""+tarriveaddress
                    +"\",\"tname\":\""+tname
                    +"\",\"sperson\":\""+trace1.getSperson()
                    +"\",\"stime\":\""+trace1.getStime()
                    +"\",\"saddress\":\""+trace1.getSaddress()
                    +"\",\"sname\":\""+trace1.getSname()
                    +"\",\"role\":\""+"运输商"+"\"";

            String URL_invoke = http+"/invoke?id="+traid+"&data="+s;
            ResponseEntity<String> forEntity = restTemplate.getForEntity(URL_invoke, String.class);

            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }


    //运输商订单记录
    @ResponseBody
    @RequestMapping("/saleUpdate")
    public ListOrderedMap saleUpdate(@RequestBody ListOrderedMap params){
        ListOrderedMap result = new ListOrderedMap();
        int traid = Integer.valueOf(params.get("traid").toString());
        String sperson = params.get("sperson").toString();
        String stime = params.get("stime").toString();
        String saddress = params.get("saddress").toString();
        String sname = params.get("sname").toString();
        Trace trace = new Trace();
        trace.setSperson(sperson);
        trace.setStime(stime);
        trace.setSaddress(saddress);
        trace.setSname(sname);
        trace.setTraid(traid);
        int row = traceJdbcDao.saleUpdate(trace);
        if(row==1){

            Trace trace1 = traceJdbcDao.findById(traid);
            String s ="\"traid\":\""+traid
                    +"\",\"pname\":\""+trace1.getPname()
                    +"\",\"paddress\":\""+trace1.getPaddress()
                    +"\",\"ptime\":\""+trace1.getPtime()
                    +"\",\"pperson\":\""+trace1.getPperson()
                    +"\",\"tperson\":\""+trace1.getTperson()
                    +"\",\"tbeginaddress\":\""+trace1.getTbeginaddress()
                    +"\",\"tarriveaddress\":\""+trace1.getTarriveaddress()
                    +"\",\"tname\":\""+trace1.getTname()
                    +"\",\"sperson\":\""+sperson
                    +"\",\"stime\":\""+stime
                    +"\",\"saddress\":\""+saddress
                    +"\",\"sname\":\""+sname
                    +"\",\"role\":\""+"销售商"+"\"";

            String URL_invoke = http+"/invoke?id="+traid+"&data="+s;
            ResponseEntity<String> forEntity = restTemplate.getForEntity(URL_invoke, String.class);
            result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
            return result;
        }
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }


    //生产商订查找
    @ResponseBody
    @RequestMapping("/findAll")
    public ListOrderedMap findAll(){
        ListOrderedMap result = new ListOrderedMap();
        List<Trace> all = traceJdbcDao.findAll();
        result.put("findAll",all);
        return result;
    }

}
