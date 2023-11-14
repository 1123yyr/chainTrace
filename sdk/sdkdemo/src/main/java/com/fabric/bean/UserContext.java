package com.fabric.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

import java.io.Serializable;
import java.security.Security;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * @description 用户对象
 */
public class UserContext implements User, Serializable{
    private String name; //用户名称

    private Set<String> roles; //角色

    private String account; //账户

    private String affiliation; //组织机构

    private Enrollment enrollment;

    private String mspId;

    static{
        Security.addProvider(new BouncyCastleProvider());//加密方法
    }
}
