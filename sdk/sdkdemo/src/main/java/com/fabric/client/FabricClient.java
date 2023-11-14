package com.fabric.client;

import com.fabric.bean.UserContext;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.*;

public class FabricClient {
    private static final Logger log = LoggerFactory.getLogger(FabricClient.class);

    //创建客户端，类似cli
    private final HFClient hfClient;

    public FabricClient(UserContext userContext) throws Exception {
        hfClient=HFClient.createNewInstance();
        //设置加密算法
        CryptoSuite cryptoSuite=CryptoSuite.Factory.getCryptoSuite();
        hfClient.setCryptoSuite(cryptoSuite);
        //设置环境变量
        hfClient.setUserContext(userContext);

    }
    /**
     * @description 创建channel
     * @param channelName channel的名字
     * @param order order的信息
     * @param txPath 创建channel所需的tx文件
     * @return Channel
     * @throws Exception 异常
     */
    public Channel createChannel(String channelName, Orderer order,String txPath) throws Exception{
        ChannelConfiguration channelConfiguration=new ChannelConfiguration(new File(txPath));
        byte[] channelConfigurationSignature = hfClient.getChannelConfigurationSignature(channelConfiguration, hfClient.getUserContext());
        return hfClient.newChannel(channelName,order,channelConfiguration,channelConfigurationSignature);
    }

    /**
     * @description 获取channel
     * @param channelName 通道名字
     * @return channel
     * @throws Exception 异常
     */
    public Channel getChannel(String channelName) throws Exception{
        return hfClient.newChannel(channelName);
    }

    /**
     * @description 获取order节点
     * @param name order名字
     * @param grpcUrl grpcs
     * @param tlsPath tls路径
     * @return Orderer
     * @throws Exception 异常
     */
    public Orderer getOrder(String name,String grpcUrl,String tlsPath) throws Exception{
        Properties properties=new Properties();
        properties.setProperty("pemFile",tlsPath);
        return hfClient.newOrderer(name, grpcUrl, properties);
    }

    /**
     * @description 获取peer节点
     * @param name peer
     * @param grpcUrl  grpcs
     * @param tlsFilePath tls路径
     * @return  Peer
     * @throws Exception 异常
     */
    public Peer getPeer(String name, String grpcUrl, String tlsFilePath) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("pemFile",tlsFilePath);
        return hfClient.newPeer(name,grpcUrl,properties);

    }

    /**
     * @description 安装合约
     * @param lang 合约开发语言
     * @param chainCodeName 合约名称
     * @param chainCodeVersion 合约版本
     * @param chainCodeLocation 合约的目录路径
     * @param chainCodePath 合约的文件夹
     * @param peers 安装的peers 节点
     * @throws Exception 异常
     */
    public void installChainCode(TransactionRequest.Type lang, String chainCodeName, String chainCodeVersion
                                ,String chainCodeLocation,String chainCodePath, List<Peer> peers) throws Exception {
        //创建安装提案
        InstallProposalRequest installProposalRequest = hfClient.newInstallProposalRequest();
        ChaincodeID.Builder builder = ChaincodeID.newBuilder().setName(chainCodeName).setVersion(chainCodeVersion);
        installProposalRequest.setChaincodeLanguage(lang);
        installProposalRequest.setChaincodeID(builder.build());
        installProposalRequest.setChaincodeSourceLocation(new File(chainCodeLocation));
        installProposalRequest.setChaincodePath(chainCodePath);
        //向节点发送安装智能合约提案
        Collection<ProposalResponse> responses =  hfClient.sendInstallProposal(installProposalRequest,peers);
        for(ProposalResponse response:responses){
            if(response.getStatus().getStatus()==200){
                log.info("{} installed sucess",response.getPeer().getName());
            }else{
                log.error("{} installed fail",response.getMessage());
            }
        }
    }

    /**
     * @description 合约的实例化
     * @param channelName 通道名字
     * @param lang  编码语音
     * @param chaincodeName 链码名字
     * @param chaincodeVersion 链码版本
     * @param order orderer
     * @param peer peer
     * @param funcName 合约实例化执行的函数
     * @param args  合约实例化执行的参数
     * @throws Exception 异常
     */
    public void initChainCode(String channelName, TransactionRequest.Type lang, String chaincodeName, String chaincodeVersion, Orderer order, Peer peer, String funcName, String args[]) throws Exception {

        Channel channel = getChannel(channelName);
        channel.addPeer(peer);
        channel.addOrderer(order);
        channel.initialize();
        InstantiateProposalRequest instantiateProposalRequest = hfClient.newInstantiationProposalRequest();
        instantiateProposalRequest.setArgs(args);
        instantiateProposalRequest.setFcn(funcName);
        instantiateProposalRequest.setChaincodeLanguage(lang);
        ChaincodeID.Builder builder = ChaincodeID.newBuilder().setName(chaincodeName).setVersion(chaincodeVersion);
        instantiateProposalRequest.setChaincodeID(builder.build());
        Collection<ProposalResponse> responses =  channel.sendInstantiationProposal(instantiateProposalRequest);
        for(ProposalResponse response:responses){
            if(response.getStatus().getStatus()==200){
                log.info("{} init sucess",response.getPeer().getName());
            }else{
                log.error("{} init fail",response.getMessage());
            }
        }
        channel.sendTransaction(responses);
    }

    /**
     * @description 合约的升级
     * @param channelName 通道名字
     * @param lang  编码语音
     * @param chaincodeName 链码名字
     * @param chaincodeVersion 链码版本
     * @param order orderer
     * @param peer peer
     * @param funcName 合约实例化执行的函数
     * @param args  合约实例化执行的参数
     * @throws Exception 异常
     */
    public void upgradeChainCode(String channelName, TransactionRequest.Type lang, String chaincodeName, String chaincodeVersion, Orderer order, Peer peer, String funcName, String args[]) throws Exception {
        Channel channel = getChannel(channelName);
        channel.addPeer(peer);
        channel.addOrderer(order);
        channel.initialize();
        UpgradeProposalRequest upgradeProposalRequest = hfClient.newUpgradeProposalRequest();
        upgradeProposalRequest.setArgs(args);
        upgradeProposalRequest.setFcn(funcName);
        upgradeProposalRequest.setChaincodeLanguage(lang);
        ChaincodeEndorsementPolicy chaincodeEndorsementPolicy = new ChaincodeEndorsementPolicy();
        chaincodeEndorsementPolicy.fromYamlFile(new File("C:\\codeSource\\sp\\sdk\\chaincode\\src\\basicInfo\\chaincodeendorsementpolicy.yaml"));
        upgradeProposalRequest.setChaincodeEndorsementPolicy(chaincodeEndorsementPolicy);
        ChaincodeID.Builder builder = ChaincodeID.newBuilder().setName(chaincodeName).setVersion(chaincodeVersion);
        upgradeProposalRequest.setChaincodeID(builder.build());
        Collection<ProposalResponse> responses =  channel.sendUpgradeProposal(upgradeProposalRequest);
        for(ProposalResponse response:responses){
            if(response.getStatus().getStatus()==200){
                log.info("{} upgrade sucess",response.getPeer().getName());
            }else{
                log.error("{} upgrade fail",response.getMessage());
            }
        }
        channel.sendTransaction(responses);
    }

    /**
     * @description 合约的调用
     * @param channelName 通道名字
     * @param lang 编码语音
     * @param chaincodeName 链码名字
     * @param order orderer
     * @param peers peer
     * @param funcName 合约调用执行的函数名称
     * @param args 合约调用执行的参数
     * @throws Exception 异常
     */
    public void invoke(String channelName, TransactionRequest.Type lang, String chaincodeName, Orderer order, List<Peer> peers, String funcName, String args[]) throws Exception {
        Channel channel = getChannel(channelName);
        channel.addOrderer(order);
        for(Peer p : peers) {
            channel.addPeer(p);
        }
        channel.initialize();
        TransactionProposalRequest transactionProposalRequest = hfClient.newTransactionProposalRequest();
        transactionProposalRequest.setChaincodeLanguage(lang);
        transactionProposalRequest.setArgs(args);
        transactionProposalRequest.setFcn(funcName);
        ChaincodeID.Builder builder = ChaincodeID.newBuilder().setName(chaincodeName);
        transactionProposalRequest.setChaincodeID(builder.build());
        Collection<ProposalResponse> responses = channel.sendTransactionProposal(transactionProposalRequest,peers);
        for(ProposalResponse response:responses){
            if(response.getStatus().getStatus()==200){
                log.info("{} invoke proposal {} sucess",response.getPeer().getName(),funcName);
            }else{
                String[] logArgs = {response.getMessage(),funcName,response.getPeer().getName()};
                log.error("{} invoke proposal {} fail on {}",logArgs);
            }
        }
        channel.sendTransaction(responses);
    }

    /**
     * @description 合约的查询
     * @param peers peer
     * @param channelName 通道名字
     * @param lang 编码语音
     * @param chaincodeName 链码名字
     * @param funcName 合约调用执行的函数名称
     * @param args 合约调用执行的参数
     * @throws Exception 异常
     */
//    public String queryChainCode(List<Peer> peers, String channelName, TransactionRequest.Type lang, String chaincodeName, String funcName, String args[]) throws Exception {
//        Channel channel = getChannel(channelName);
//        for(Peer p : peers) {
//            channel.addPeer(p);
//        }
//        channel.initialize();
//        HashMap<Object, String> map = new HashMap();
//        QueryByChaincodeRequest queryByChaincodeRequest = hfClient.newQueryProposalRequest();
//        ChaincodeID.Builder builder = ChaincodeID.newBuilder().setName(chaincodeName);
//        queryByChaincodeRequest.setChaincodeID(builder.build());
//        queryByChaincodeRequest.setArgs(args);
//        queryByChaincodeRequest.setFcn(funcName);
//        queryByChaincodeRequest.setChaincodeLanguage(lang);
//        Collection<ProposalResponse> responses = channel.queryByChaincode(queryByChaincodeRequest);
//        String s = null;
//        for (ProposalResponse response : responses) {
//
//            if (response.getStatus().getStatus() == 200) {
//                s =  new String(response.getProposalResponse().getResponse().getPayload().toByteArray());
//                log.info("data is {}", response.getProposalResponse().getResponse().getPayload());
////                map.put(String.valueOf(response.getStatus().getStatus()),new String(response.getProposalResponse().getResponse().getPayload().toByteArray()));
//                map.put(String.valueOf(response.getStatus().getStatus()),s);
//
//                System.out.println("----"+s);
//            } else {
//                log.error("data get error {}", response.getMessage());
////                 response.getStatus().getStatus(),response.getMessage();
//                map.put(response.getStatus().getStatus(),response.getMessage());
//
//            }
//            return s;
////            return map;
////            return "fail";
//        }
//        map.put("code","404");
//        return s;
//    }
//
////
//    public Map<Object, String> queryChainCode(List<Peer> peers, String channelName, TransactionRequest.Type lang, String chaincodeName, String funcName, String args[]) throws Exception {
//        Channel channel = getChannel(channelName);
//        for(Peer p : peers) {
//            channel.addPeer(p);
//        }
//        channel.initialize();
//        HashMap<Object, String> map = new HashMap();
//        QueryByChaincodeRequest queryByChaincodeRequest = hfClient.newQueryProposalRequest();
//        ChaincodeID.Builder builder = ChaincodeID.newBuilder().setName(chaincodeName);
//        queryByChaincodeRequest.setChaincodeID(builder.build());
//        queryByChaincodeRequest.setArgs(args);
//        queryByChaincodeRequest.setFcn(funcName);
//        queryByChaincodeRequest.setChaincodeLanguage(lang);
//        Collection<ProposalResponse> responses = channel.queryByChaincode(queryByChaincodeRequest);
//        for (ProposalResponse response : responses) {
//
//            if (response.getStatus().getStatus() == 200) {
//                log.info("data is {}", response.getProposalResponse().getResponse().getPayload());
//                map.put(String.valueOf(response.getStatus().getStatus()),new String(response.getProposalResponse().getResponse().getPayload().toByteArray()));
//                String s =  String.valueOf(response.getStatus().getStatus());
//                 System.out.println(map.get(s));
//            } else {
//                log.error("data get error {}", response.getMessage());
//                map.put(response.getStatus().getStatus(),response.getMessage());
//            }
//            return map;
//        }
//        map.put("code","404");
//        return map;
//    }


//    public Map<Object, String> queryChainCode(List<Peer> peers, String channelName, TransactionRequest.Type lang, String chaincodeName, String funcName, String args[]) throws Exception {
        public String queryChainCode(List<Peer> peers, String channelName, TransactionRequest.Type lang,
                                     String chaincodeName, String funcName, String args[]) throws Exception {
        Channel channel = getChannel(channelName);
        for(Peer p : peers) {
            channel.addPeer(p);
        }
        channel.initialize();
        HashMap<Object, String> map = new HashMap();
        QueryByChaincodeRequest queryByChaincodeRequest = hfClient.newQueryProposalRequest();
        ChaincodeID.Builder builder = ChaincodeID.newBuilder().setName(chaincodeName);
        queryByChaincodeRequest.setChaincodeID(builder.build());
        queryByChaincodeRequest.setArgs(args);
        queryByChaincodeRequest.setFcn(funcName);
        queryByChaincodeRequest.setChaincodeLanguage(lang);
        Collection<ProposalResponse> responses = channel.queryByChaincode(queryByChaincodeRequest);
        String s="";
        for (ProposalResponse response : responses) {
            if (response.getStatus().getStatus() == 200) {
                log.info("data is {}", response.getProposalResponse().getResponse().getPayload());
                 s = new String(response.getProposalResponse().getResponse().getPayload().toByteArray());
                map.put(String.valueOf(response.getStatus().getStatus()),s);
                System.out.println(s);

            } else {
                log.error("data get error {}", response.getMessage());
                map.put(response.getStatus().getStatus(),response.getMessage());
            }
            return s;
        }
        map.put("code","404");
            return s;
    }

}
