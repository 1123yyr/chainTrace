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

@Controller
@RequestMapping("/sdk")
public class InitChainCodeController {
    private static final Logger log = LoggerFactory.getLogger(ChannelController.class);
    @Autowired
    private Path path;
    @RequestMapping("/init")
    @ResponseBody
    public String initChainCode() throws Exception {
        UserContext userContext=new UserContext();
        userContext.setName("admin");
        userContext.setAffiliation("Org1");
        userContext.setMspId("Org1MSP");
        userContext.setAccount("zdd");
        //使用工具类生成enrollment
        Enrollment enrollment = UserUtils.getEnrollment(path.keyFolderPath,path.keyFileName,path.certFolderPath,path.certFileName);
        userContext.setEnrollment(enrollment);
        FabricClient fabricClient=new FabricClient(userContext);
        Peer peer = fabricClient.getPeer(path.peer0Name, path.peer0Grpc, path.tlsPeerFilePath);
        Orderer order = fabricClient.getOrder(path.orderName, path.orderGrpc, path.tlsOrderFilePath);
        String initArgs[] = {""};
        fabricClient.initChainCode(path.channelName, TransactionRequest.Type.GO_LANG,
                path.chainCodeName,path.chainCodeVersion,order,peer,"init",initArgs);
        return "init";
    }
}
