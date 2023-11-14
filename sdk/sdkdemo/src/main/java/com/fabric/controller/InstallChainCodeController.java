package com.fabric.controller;

import com.fabric.bean.Path;
import com.fabric.bean.UserContext;
import com.fabric.client.FabricClient;
import com.fabric.utils.UserUtils;
import org.hyperledger.fabric.sdk.Enrollment;
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
public class InstallChainCodeController {
    private static final Logger log = LoggerFactory.getLogger(ChannelController.class);
    @Autowired
    private Path path;
    @RequestMapping("/chainCodeInstall")
    @ResponseBody
    public String chainCodeInstall() throws Exception {
        UserContext userContext=new UserContext();
        userContext.setName("admin");
        userContext.setAffiliation("Org1");
        userContext.setMspId("Org1MSP");
        userContext.setAccount("zdd");
        //使用工具类生成enrollment
        Enrollment enrollment = UserUtils.getEnrollment(path.keyFolderPath,path.keyFileName,path.certFolderPath,path.certFileName);
        userContext.setEnrollment(enrollment);
        FabricClient fabricClient=new FabricClient(userContext);
        //获取peer
        Peer peer0 = fabricClient.getPeer(path.peer0Name, path.peer0Grpc, path.tlsPeerFilePath);
        Peer peer1 = fabricClient.getPeer(path.peer1Name, path.peer1Grpc, path.tlsPeerFilePath);
        List<Peer> peers = new ArrayList<>();
        peers.add(peer0);
        peers.add(peer1);
        //在peer中安装合约
        fabricClient.installChainCode(TransactionRequest.Type.GO_LANG,path.chainCodeName,
                                        path.chainCodeVersion,path.chainCodeLocation,
                                        path.chainCodePath,peers);
        return "install ok 1";
    }
}
