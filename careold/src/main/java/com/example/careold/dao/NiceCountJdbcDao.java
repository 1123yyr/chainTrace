package com.example.careold.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NiceCountJdbcDao implements NiceCountDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<String> getCount(int saidId, String phone) {
        String sql="select phone from nice_count where said_id=? and phone=?";
        List<String> phoneList=jdbcTemplate.queryForList(sql,String.class,saidId,phone);
        return phoneList;
    }

    @Override
    public int deleteCount(int saidId, String phone) {
        String sql="delete from nice_count where said_id=? and phone=?";
        int row=jdbcTemplate.update(sql,saidId,phone);
        return 0;
    }

    @Override
    public int addCount(int saidId, String phone) {
        String sql="insert into nice_count(said_id,phone) values(?,?)";
        int row=jdbcTemplate.update(sql,saidId,phone);
        return row;
    }
}
