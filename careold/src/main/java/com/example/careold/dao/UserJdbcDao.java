package com.example.careold.dao;

import com.example.careold.domain.Family;
import com.example.careold.domain.Users;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJdbcDao extends JdbcCom implements UserDao {
    @Override
    public List<Users> findById(String s) {
        String sql="";
        List<Users> users=null;
        if(s==null || "".equals(s)){
            sql="select * from users";
            users=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Users.class));
        }else{
            sql="select * from users where user_name like ?";
            users=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Users.class),"%"+s+"%");
        }
        return users;
    }

    @Override
    public int save(Users users) {
        return 0;
    }

    @Override
    public int update(Users users) {
        return 0;
    }

    @Override
    public int delete(String s) {
        return 0;
    }

    @Override
    public Users getUsersByPhone(String phone) {

        String sql="Select user_id,user_name,password,role,user_phone from users where user_phone = ?";
        Users users=jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Users.class),phone);
        return users;
    }

    @Override
    public List<Users> getUsersByPhone2(String phone) {

        String sql="Select user_id,user_name,password,role,user_phone from users where user_phone = ?";
        List<Users> users=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Users.class),phone);
        return users;
    }
}
