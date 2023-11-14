package com.example.careold.dao;

import com.example.careold.domain.FamilyDto;
import com.example.careold.domain.FamilyDto2;
import com.example.careold.domain.Produce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ProduceJdbcDao implements ProduceDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public int save(Produce produce) {
        String sql="insert into produce(paccount,ppassword,pname,pemail,ptime) VALUES(?,?,?,?,?)";
        int row= jdbcTemplate.update(sql,produce.getPaccount(),"123456",produce.getPname(),produce.getPemail(),produce.getPtime());
        return row;
    }

    @Override
    public int update(Produce produce) {
        String sql="UPDATE produce SET pname=?,pemail=?,ptime=? WHERE paccount=?";
        int rows=jdbcTemplate.update(sql,produce.getPname(),produce.getPemail(),produce.getPtime(),produce.getPaccount());
        return rows;
    }

    @Override
    public int deleteByAccount(String paccount) {
        String sql="DELETE FROM produce WHERE paccount=?";
        int row =jdbcTemplate.update(sql,paccount);
        return row;
    }

    @Override
    public Produce findByAccount(String account) {
        String sql="SELECT * FROM produce WHERE paccount=?";
        Produce produce = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Produce.class), account);
        return produce;
    }

    @Override
    public List<Produce> findAll() {
        String sql="SELECT * FROM produce ";
        List<Produce> produces = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Produce.class));
        return produces;
    }

    @Override
    public int updatePassword(Produce produce) {
        String sql="UPDATE produce SET ppassword=? WHERE paccount=?";
        int rows=jdbcTemplate.update(sql,produce.getPpassword(),produce.getPaccount());
        return rows;

    }
}
