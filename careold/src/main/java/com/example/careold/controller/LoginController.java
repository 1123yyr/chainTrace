package com.example.careold.controller;

//import com.example.careold.common.MD5Util;
import com.example.careold.common.ReturnCodeUtil;
import com.example.careold.dao.*;
import com.example.careold.domain.*;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Controller
@RequestMapping("/restful/userLogin")
public class LoginController {

    @Autowired
    FamilyDao familyDao;

    @Autowired
    UserDao userDao;

    @Autowired
    CustomerJdbcDao customerJdbcDao;

    @Autowired
    ProduceJdbcDao produceJdbcDao;

    @Autowired
    TransportJdbcDao transportJdbcDao;

    @Autowired
    SaleJdbcDao saleJdbcDao;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    //登陆
    @ResponseBody
    @RequestMapping("/login")
    public ListOrderedMap select(@RequestBody ListOrderedMap param){
        ListOrderedMap result=new ListOrderedMap();

        String phone=param.get("phone").toString();
        String password=param.get("password").toString();
        List<Users> users = userDao.findById(null);//获取到所有user
        for(int i=0;i<users.size();i++){
            if(users.get(i).getUserPhone().equals(phone)){//比对电话号码
                if(users.get(i).getPassword().equals(password)){
                    Users users1=userDao.getUsersByPhone(phone);
                    System.out.println(users1);
                    result.put("users1",users1);

                    result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
                    return result;
                }else {
                    result.put(ReturnCodeUtil.returnCode,"密码不正确");
                    return result;
                }
            }
        }
        System.out.println("=======");
        List<Produce> produces=produceJdbcDao.findAll();
        for(int i=0;i<produces.size();i++){
            if(produces.get(i).getPaccount().equals(phone)){
                System.out.println(produces.get(i).getPpassword().equals(password));
                if(produces.get(i).getPpassword().equals(password)){
                    Produce produce=produceJdbcDao.findByAccount(phone);
                    result.put("produce",produce);
                    System.out.println("produce");
                    result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
                    return result;
                }else {
                    result.put(ReturnCodeUtil.returnCode,"密码不正确");
                    return result;
                }
            }
        }
        System.out.println("=======");
        List<Transport> transports=transportJdbcDao.findAll();
        for(int i=0;i<transports.size();i++){
            if(transports.get(i).getTaccount().equals(phone)){
                System.out.println(transports.get(i).getTpassword().equals(password));
                if(transports.get(i).getTpassword().equals(password)){
                    Transport transport=transportJdbcDao.findByAccount(phone);
                    result.put("transport",transport);
                    System.out.println("transport");
                    result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
                    return result;
                }else {
                    result.put(ReturnCodeUtil.returnCode,"密码不正确");
                    return result;
                }
            }
        }
        System.out.println("=======");
        List<Sale> sales=saleJdbcDao.findAll();
        for(int i=0;i<sales.size();i++){
            if(sales.get(i).getSaccount().equals(phone)){
                System.out.println(sales.get(i).getSpassword().equals(password));
                if(sales.get(i).getSpassword().equals(password)){
                    Sale sale=saleJdbcDao.findByAccount(phone);
                    result.put("sale",sale);
                    System.out.println("sale");
                    result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.successCode);
                    return result;
                }else {
                    result.put(ReturnCodeUtil.returnCode,"密码不正确");
                    return result;
                }
            }
        }
        System.out.println("=======");
        result.put(ReturnCodeUtil.returnCode,ReturnCodeUtil.falseCode);
        return result;
    }
//
//    @ResponseBody
//    @RequestMapping("/loginTwo")
//    public ListOrderedMap selectTwo(@RequestBody ListOrderedMap param){
//        ListOrderedMap result=new ListOrderedMap();
//
//        String username=param.get("username").toString();
//        String password=param.get("password").toString();
////        List<Users> users = userDao.findById(null);
//
//        Subject sub = SecurityUtils.getSubject();
//
//        UsernamePasswordToken token = new UsernamePasswordToken(username, MD5Util.MD5(password));
//        try {
//            sub.login(token);
//        } catch (UnknownAccountException e) {
//            logger.error("对用户[{}]进行登录验证,验证未通过,用户不存在", username);
//            token.clear();
//            return result;
//        } catch (LockedAccountException lae) {
//            logger.error("对用户[{}]进行登录验证,验证未通过,账户已锁定", username);
//            token.clear();
//            return result;
//        } catch (ExcessiveAttemptsException e) {
//            logger.error("对用户[{}]进行登录验证,验证未通过,错误次数过多", username);
//            token.clear();
//            return result;
//        } catch (AuthenticationException e) {
//            logger.error("对用户[{}]进行登录验证,验证未通过,堆栈轨迹如下", username, e);
//            token.clear();
//            return result;
//        }
//
//        // 从session中获取 user 对象
//        Session session = SecurityUtils.getSubject().getSession();
//        SysUser user = (SysUser) session.getAttribute("USER_SESSION");
//
//        result.put("user",user);
//        return result;
//    }
}
