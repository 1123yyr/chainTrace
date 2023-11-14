package com.fabric.test;

import com.fabric.bean.UserContext;
import com.fabric.client.FabricClient;
import com.fabric.utils.UserUtils;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.Orderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class SdkMain {
    private static final Logger log = LoggerFactory.getLogger(SdkMain.class);
    /**
     * peer私钥的路径
     * peerOrganization-org1(2)-users-Admin-msp-keystore
     */
    private static final String keyFolderPath="D:\\JavaProject\\Hyperledger\\sdk\\sdkdemo\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\users\\Admin@org1.example.com\\msp\\keystore";

    /**
     * peer私钥的值 
     * 就是上面路径的值 xxx_sk
     */
    private static final String keyFileName="2f1c31c3e5d178d75db138951aa6ac863fef67e4ba90985064fa2e44831d6e69_sk";
    /**
     * peer证书的路径
     * peerOrganization-org1(2)-users-Admin-msp-admincerts
     */
    private static final String certFolderPath="D:\\JavaProject\\Hyperledger\\sdk\\sdkdemo\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\users\\Admin@org1.example.com\\msp\\admincerts";
    /**
     * peer证书的值
     * 就是上面路径的值 xxx_pem
     */
    private static final String certFileName="Admin@org1.example.com-cert.pem";

    /**
     * order的tls的证书路径
     * orderOrganization-tlsca-xxx.pem
     */
    private static  final String tlsOrderFilePath ="D:\\JavaProject\\Hyperledger\\sdk\\sdkdemo\\src\\main\\resources\\crypto-config\\ordererOrganizations\\example.com\\tlsca\\tlsca.example.com-cert.pem";

    /**
     * 命令创建channel时的tx文件路径
     */
    private static final String txfilePath ="D:\\JavaProject\\Hyperledger\\sdk\\sdkdemo\\src\\main\\resources\\test.tx";

    /**
     * peer的tls的证书路径
     * peerOrganization-org1-peers-peer0-msp-tlscacerts-com-xxx.pem
     */
    private static  final String tlsPeerFilePath="D:\\JavaProject\\Hyperledger\\sdk\\sdkdemo\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\peers\\peer0.org1.example.com\\msp\\tlscacerts\\tlsca.org1.example.com-cert.pem";

    public static void main(String[] args) throws Exception{
        UserContext userContext=new UserContext();
        userContext.setName("admin");
        userContext.setAffiliation("Org1");
        userContext.setMspId("Org1MSP");
        userContext.setAccount("zdd");
        //使用工具类生成enrollment
        Enrollment enrollment = UserUtils.getEnrollment(keyFolderPath,keyFileName,certFolderPath,certFileName);
        userContext.setEnrollment(enrollment);
        FabricClient fabricClient=new FabricClient(userContext);
        //创建通道
//        Orderer order = fabricClient.getOrder("orderer.example.com", "grpcs://orderer.example.com:7050", tlsOrderFilePath);
//        Channel channel = fabricClient.createChannel("test", order, txfilePath);
        //peer加入通道
        //channel.joinPeer(fabricClient.getPeer("peer0.org1.example.com","grpcs://peer0.org1.example.com:7051",tlsPeerFilePath));
//        Orderer order = fabricClient.getOrder("orderer.example.com", "grpcs://orderer.example.com:7050", tlsOrderFilePath);
//        fabricClient.createChannel("channel001",order,txfilePath);
        Channel channel1 = fabricClient.getChannel("mychannel");
        System.out.println("====="+channel1);
    }
}
