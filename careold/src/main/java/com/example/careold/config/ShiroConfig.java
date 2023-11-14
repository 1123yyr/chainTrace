package com.example.careold.config;

import com.example.careold.config.MyRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro 配置  一个安全框架
 */
//@Configuration
public class ShiroConfig {

    private final static Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    // 下面两个方法对 注解权限起作用有很大的关系，请把这两个方法，放在配置的最上面
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        autoProxyCreator.setProxyTargetClass(true);
        return autoProxyCreator;
    }

    //将自己的验证方式加入容器
    @Bean
    public MyRealm myRealm() {
        MyRealm myRealm = new MyRealm();
        return myRealm;
    }

    @Bean(name="sessionDAO")
    public MemorySessionDAO getMemorySessionDAO(){
        MemorySessionDAO sessionDAO = new MemorySessionDAO();
        return sessionDAO;
    }

    //配置shiro session 的一个管理器
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager getDefaultWebSessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 设置session过期时间
        sessionManager.setGlobalSessionTimeout(60*60*1000);
        sessionManager.setSessionDAO(getMemorySessionDAO());
        return sessionManager;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm( myRealm() );
        defaultWebSecurityManager.setSessionManager( getDefaultWebSessionManager() );
        return defaultWebSecurityManager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        System.out.println( "shiro 过滤器" );
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();

        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/userLogin/loginTwo", "anon");
//        filterChainDefinitionMap.put("/family/**", "anon");
        filterChainDefinitionMap.put("/backstage/css/**", "anon");
        filterChainDefinitionMap.put("/backstage/js/**", "anon");
        filterChainDefinitionMap.put("/backstage/images/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/backstage/login.html");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/backstage/index.html");

        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403.html");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

}
