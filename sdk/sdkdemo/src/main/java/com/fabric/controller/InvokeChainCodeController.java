package com.fabric.controller;

import com.fabric.bean.Path;
import com.fabric.bean.UserContext;
import com.fabric.client.FabricClient;
import com.fabric.utils.UserUtils;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.TransactionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/sdk")
public class InvokeChainCodeController {
    /**
     * 1、先安装链码再peer1中，peer0在测试安装方法时已安装
     * 2、调用合约
     */
    private static final Logger log = LoggerFactory.getLogger(ChannelController.class);
    @Autowired
    private Path path;
    @RequestMapping("/chainCodeInstall2")
    @ResponseBody
    public String chainCodeInstall() throws Exception {
        UserContext userContext=new UserContext();
        userContext.setName("admin");
        userContext.setAffiliation("Org2");
        userContext.setMspId("Org2MSP");
        userContext.setAccount("zdd");
        //使用工具类生成enrollment
        Enrollment enrollment = UserUtils.getEnrollment(path.keyFolderPath2,path.keyFileName2,path.certFolderPath2,path.certFileName2);
        userContext.setEnrollment(enrollment);
        FabricClient fabricClient=new FabricClient(userContext);
        //获取peer
        Peer peer0 = fabricClient.getPeer(path.peer0Name2, path.peer0Grpc2, path.tlsPeerFilePathAddition);
        Peer peer1 = fabricClient.getPeer(path.peer1Name2, path.peer1Grpc2, path.tlsPeerFilePathAddition);
        List<Peer> peers = new ArrayList<>();
        peers.add(peer0);
        peers.add(peer1);
        //在peer中安装合约
        fabricClient.installChainCode(TransactionRequest.Type.GO_LANG,path.chainCodeName,
                path.chainCodeVersion,path.chainCodeLocation,
                path.chainCodePath,peers);
        return "install ok 2";
    }

    @RequestMapping("/invoke")
    @ResponseBody
    public String chainCodeInvoke(String id,String data) throws Exception {

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
        Orderer order = fabricClient.getOrder(path.orderName, path.orderGrpc, path.tlsOrderFilePath);
        String[] initArgs = {id,"{"+data+"}"};

//        String[] initArgs1 = {"99999","{\"orderid\":\"9999\",\"oldid\":\"10000\",\"name\":\"张十三\",\"thing\":\"医药费\",\"time\":\"2019-12-12\",\"money\":\"100\",\"orderstatus\":\"未支付\"}"};

        fabricClient.invoke(path.channelName, TransactionRequest.Type.GO_LANG,path.chainCodeName,order,peers,"save",initArgs);
//        System.out.println(initArgs.length+initArgs[0]+initArgs[1]);
//        System.out.println(initArgs1.length+initArgs1[0]+initArgs1[1]);

        return "invoke ok";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String chainCodeDelete(String id) throws Exception {
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
        Orderer order = fabricClient.getOrder(path.orderName, path.orderGrpc, path.tlsOrderFilePath);
        String[] initArgs = {id};
//        String[] initArgs = {"110114","{\"name\":\"zhangsan\",\"identity\":\"110114\",\"mobile\":\"18910012222\"}"};
        fabricClient.invoke(path.channelName, TransactionRequest.Type.GO_LANG,path.chainCodeName,order,peers,"delete",initArgs);

        return "invoke :"+id+"  ok";
    }

}
