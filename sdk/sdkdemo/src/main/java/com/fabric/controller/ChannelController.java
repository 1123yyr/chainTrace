package com.fabric.controller;

import com.fabric.bean.Path;
import com.fabric.bean.UserContext;
import com.fabric.client.FabricClient;
import com.fabric.utils.UserUtils;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sdk")
public class ChannelController {
    private static final Logger log = LoggerFactory.getLogger(ChannelController.class);
    @Autowired
    private Path path;
    @RequestMapping("/createChannel")
    @ResponseBody
    public String createChannel(String name) throws Exception {

        UserContext userContext=new UserContext();
        userContext.setName("admin");
        userContext.setAffiliation("Org1");
        userContext.setMspId("Org1MSP");
        userContext.setAccount("zdd");
        //使用工具类生成enrollment
        Enrollment enrollment = UserUtils.getEnrollment(path.keyFolderPath,path.keyFileName,path.certFolderPath,path.certFileName);
        userContext.setEnrollment(enrollment);
        FabricClient fabricClient=new FabricClient(userContext);
        //创建channel
            //1、得到order
        Orderer order = fabricClient.getOrder(path.orderName, path.orderGrpc, path.tlsOrderFilePath);
        log.info("order信息："+order);
        Channel channel = fabricClient.createChannel(path.channelName, order, path.txfilePath);
        log.info("=================");
        log.info("channel信息,peer没加入前"+channel);
            //2、得到peer
        Peer peer = fabricClient.getPeer(path.peer0Name, path.peer0Grpc, path.tlsPeerFilePath);
        log.info("peer信息："+peer);
        log.info("=================");
            //3、peer加入通道
        channel.joinPeer(peer);
        log.info("=================");
        log.info("channel信息,peer加入后"+channel);

        //获取channel
//        Channel channel1 = fabricClient.getChannel("test");
//        boolean initialized = channel1.isInitialized();
//        log.info("是否初始化："+initialized);
        return channel.toString();
    }

    @RequestMapping("/createChannel2")
    @ResponseBody
    public String createChannel2(String name) throws Exception {

        UserContext userContext=new UserContext();
        userContext.setName("admin");
        userContext.setAffiliation("Org2");
        userContext.setMspId("Org2MSP");
        userContext.setAccount("zdd");
        //使用工具类生成enrollment
        Enrollment enrollment = UserUtils.getEnrollment(path.keyFolderPath2,path.keyFileName2,path.certFolderPath2,path.certFileName2);
        userContext.setEnrollment(enrollment);
        FabricClient fabricClient=new FabricClient(userContext);
        //创建channel
        //1、得到order
        Orderer order = fabricClient.getOrder(path.orderName, path.orderGrpc, path.tlsOrderFilePath);
        log.info("order信息："+order);
      /*  Channel channel = fabricClient.createChannel(path.channelName, order, path.txfilePath);
        log.info("=================");
        log.info("channel信息,peer没加入前"+channel);*/
        Channel channel = fabricClient.getChannel("test");
        channel.addOrderer(fabricClient.getOrder(path.orderName, path.orderGrpc,path.tlsOrderFilePath));
        //2、得到peer
        Peer peer = fabricClient.getPeer(path.peer0Name2, path.peer0Grpc2, path.tlsPeerFilePathAddition);
        log.info("peer信息："+peer);
        log.info("=================");
        //3、peer加入通道
        channel.joinPeer(peer);
        log.info("=================");
        log.info("channel信息,peer加入后"+channel);

        //获取channel
//        Channel channel1 = fabricClient.getChannel("test");
//        boolean initialized = channel1.isInitialized();
//        log.info("是否初始化："+initialized);
        return channel.toString();
    }


}
