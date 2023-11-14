package com.fabric.controller;

import com.fabric.bean.Path;
import com.fabric.bean.UserContext;
import com.fabric.client.FabricClient;
import com.fabric.po.Cost;
import com.fabric.po.Trace;
import com.fabric.utils.MyUtils;
import com.fabric.utils.MyUtils1;
import com.fabric.utils.UserUtils;


import net.sf.json.JSONObject;
import org.apache.commons.collections.map.ListOrderedMap;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.TransactionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping("/sdk")
public class QueryChainCodeController {
    private static final Logger log = LoggerFactory.getLogger(ChannelController.class);
    @Autowired
    private Path path;

    @Autowired
    private MyUtils1 myUtils;
//    @RequestMapping("/query")
//    @ResponseBody
//    public String queryChainCode(String id) throws Exception {
//
//        UserContext userContext = new UserContext();
//        userContext.setAffiliation("Org2");
//        userContext.setMspId("Org2MSP");
//        userContext.setAccount("zdd");
//        userContext.setName("admin");
//        Enrollment enrollment =  UserUtils.getEnrollment(path.keyFolderPath2,path.keyFileName2,path.certFolderPath2,path.certFileName2);
//        userContext.setEnrollment(enrollment);
//        FabricClient fabricClient = new FabricClient(userContext);
//        Peer peer0 = fabricClient.getPeer(path.peer0Name,path.peer0Grpc,path.tlsPeerFilePath);
//        Peer peer1 = fabricClient.getPeer(path.peer0Name2,path.peer0Grpc2,path.tlsPeerFilePathAddition);
//        List<Peer> peers = new ArrayList<>();
//        peers.add(peer0);
//        peers.add(peer1);
//        String[] initArgs = {id};
//        Map<Object, String> map = fabricClient.queryChainCode(peers, path.channelName, TransactionRequest.Type.GO_LANG, path.chainCodeName, "query", initArgs);
////        Map<Object, String> map = fabricClient.queryChainCode(peers, path.channelName, TransactionRequest.Type.GO_LANG, path.chainCodeName, "query", initArgs);
//        log.info("查询到的信息："+map);
//        return "query ok"+map.toString();
//    }




    @RequestMapping("/queryAll")
    @ResponseBody
    public ListOrderedMap queryChainCode1(@RequestBody ListOrderedMap param) throws Exception {
//        public String queryChainCode1(String id) throws Exception {
        String id = param.get("familyName").toString();
        UserContext userContext = new UserContext();
        userContext.setAffiliation("Org2");
        userContext.setMspId("Org2MSP");
        userContext.setAccount("zdd");
        userContext.setName("admin");
        Enrollment enrollment =  UserUtils.getEnrollment(path.keyFolderPath2,path.keyFileName2,path.certFolderPath2,path.certFileName2);
        userContext.setEnrollment(enrollment);
        FabricClient fabricClient = new FabricClient(userContext);
        Peer peer0 = fabricClient.getPeer(path.peer0Name,path.peer0Grpc,path.tlsPeerFilePath);
        Peer peer1 = fabricClient.getPeer(path.peer0Name2,path.peer0Grpc2,path.tlsPeerFilePathAddition);
        List<Peer> peers = new ArrayList<>();
        peers.add(peer0);
        peers.add(peer1);
        String[] initArgs = {id};
        String s = fabricClient.queryChainCode(peers, path.channelName, TransactionRequest.Type.GO_LANG, path.chainCodeName, "queryAll", initArgs);
        List<Trace> traces = myUtils.toListMap(s);
//        Map<Object, String> map = fabricClient.queryChainCode(peers, path.channelName, TransactionRequest.Type.GO_LANG, path.chainCodeName, "queryAll", initArgs);
//            JSONObject jsonObject = JSONObject.fromObject(map);
//            String result = jsonObject.toString();
//        log.info("查询到的信息："+map);
//        return map.toString();
        ListOrderedMap listOrderedMap = new ListOrderedMap();
        listOrderedMap.put("traces",traces);
        System.out.println(listOrderedMap.toString());
        return listOrderedMap;
    }



}
