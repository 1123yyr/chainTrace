package com.example.careold.dao;

import com.example.careold.domain.Users;

import java.util.List;

public interface UserDao extends CrudRepository<Users,String>{

    Users getUsersByPhone(String phone);
    List<Users> getUsersByPhone2(String phone);
}
