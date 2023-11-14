package com.example.careold.config;

import com.alibaba.fastjson.JSONObject;
//import com.example.careold.common.HttpClientUtils;
import com.example.careold.domain.SysUser;
//import com.zzq.usercenter.po.SysUser;
//import com.zzq.usercenter.service.SysUserService;
//import com.zzq.util.MD5Util;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * shiro中自定义realm一般继承AuthorizingRealm 来完成登录和权限的验证。
 */
public class MyRealm extends AuthorizingRealm {

//    @Autowired
//    private SysUserService sysUserService;

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String getName() {
        return "myRealm";
    }

    //该方法是进行用户验证的。调用currUser.login(token)方法时会调用doGetAuthenticationInfo方法。
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 从session中获取 user 对象
        Session session = SecurityUtils.getSubject().getSession();
        SysUser user = (SysUser)session.getAttribute("USER_SESSION");
        System.out.println( "执行了================>" );
        // 权限信息对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles( user.getRoles() );
        info.addStringPermissions( user.getPermission() );

        Set<String> roles = info.getRoles();
        for (String str  : roles) {
            System.out.println( "角色："+ str );
        }
        Set<String> permissions = info.getStringPermissions();
        for (String str  : permissions) {
            System.out.println( "权限："+ str );
        }


        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        String username = (String)authenticationToken.getPrincipal();
        String password = new String((char[])authenticationToken.getCredentials());
        System.out.println( "username="+username+",password="+password );
        String url="http://127.0.0.1:8995/users/selectUserPerm2";
        String url2="http://127.0.0.1:8995/users/select";
        ListOrderedMap map=new ListOrderedMap();
        map.put("username",username);
        SysUser user=new SysUser();
        user.setUsername(username);
        System.out.println("开始");

        ResponseEntity<Map> forObject = restTemplate.postForEntity(url,map,Map.class);
        ResponseEntity<Map> forObject2 = restTemplate.postForEntity(url2,map,Map.class);
        System.out.println(forObject);
        System.out.println(forObject2);
        List<Map<String,Object>> listuser=(List<Map<String,Object>>)forObject2.getBody().get("users");
        String password2=(String) listuser.get(0).get("password");
        System.out.println("密码2"+password2);
        user.setPassword(password2);

        System.out.println("返回"+forObject.getBody().get("listMapUserPerm"));
        List<Map<String,Object>> list1=(List<Map<String,Object>>)forObject.getBody().get("listMapUserPerm");
        user.setRoles((List<String>) list1.get(0).get("roleName"));
        user.setPermission((List<String>) list1.get(0).get("permName"));


        if( username.equals( user.getUsername() ) && password.equals( user.getPassword() ) ){
            // 获取所有session
            Collection<Session> sessions = sessionDAO.getActiveSessions();
            for (Session session: sessions) {
                SysUser sysUser = (SysUser)session.getAttribute("USER_SESSION");
                // 如果session里面有当前登陆的，则证明是重复登陆的，则将其剔除
                if( sysUser!=null ){
                    if( username.equals( sysUser.getUsername() ) ){
                        session.setTimeout(0);
                    }
                }
            }
            // 验证通过了 setSession
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("USER_SESSION", user);

        }

        // 从数据库查询出来的用户名密码，进行验证
        // 用户名，密码，密码盐值，realm 名称
        // 登陆的时候直接调用 subject.login() 即可自动调用该方法
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                authenticationToken.getPrincipal() , user.getPassword() , getName()
        );


        return info;
    }
}
